package com.ekorn.cryptoaggregate.service;

import com.ekorn.cryptoaggregate.bo.ProductPairBO;
import com.ekorn.cryptoaggregate.bo.ProductPricePairBO;
import com.ekorn.cryptoaggregate.client.CoinbaseClient;
import com.ekorn.cryptoaggregate.dto.ProductPricePairDTO;
import com.ekorn.cryptoaggregate.persistance.ProductPricePairEntity;
import com.ekorn.cryptoaggregate.persistance.ProductPriceRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final CoinbaseClient coinbaseClient;
    private final ProductPriceRepository productPriceRepository;
    private final ProductProviderService productProviderService;

    public ProductPricePairDTO getProductPricePair(String symbol) {
        Optional<ProductPricePairEntity> entity = productPriceRepository.findFirstByProductIdOrderByTimeDesc(symbol);
        ProductPricePairBO bo = ProductPricePairBO.from(entity.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST)));

        return ProductPricePairDTO.from(bo);
    }

    @Scheduled(fixedDelay = 10000)
    public void getAndSaveLatestProductPairPrices() {
        List<ProductPairBO> productPairs = productProviderService.getProductPairs();
        productPairs.forEach(this::fetchAndSaveProductPricePair);
    }

    @Async
    private void fetchAndSaveProductPricePair(ProductPairBO productPairBO) {
        ProductPricePairBO productPricePairBo = coinbaseClient.findByProductId(productPairBO.getId())
                .convertToBO()
                .toBuilder()
                .withProductId(productPairBO.getId())
                .withBaseCurrency(productPairBO.getBaseCurrency())
                .withQuoteCurrency(productPairBO.getQuoteCurrency())
                .build();

        saveProductPricePair(productPricePairBo);
    }

    private void saveProductPricePair(ProductPricePairBO productPricePairBo) {
        productPriceRepository.save(productPricePairBo.convertToEntity());
    }
}

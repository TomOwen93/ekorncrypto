package com.ekorn.cryptoaggregate.service;

import com.ekorn.cryptoaggregate.bo.ProductPairBo;
import com.ekorn.cryptoaggregate.bo.ProductPricePairBo;
import com.ekorn.cryptoaggregate.client.CoinbaseClient;
import com.ekorn.cryptoaggregate.dto.ProductPriceResponseDto;
import com.ekorn.cryptoaggregate.exception.ProductNotFoundException;
import com.ekorn.cryptoaggregate.persistance.ProductPricePairEntity;
import com.ekorn.cryptoaggregate.persistance.ProductPriceRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class ProductService {
    private final CoinbaseClient coinbaseClient;
    private final ProductPriceRepository productPriceRepository;

    public ProductPriceResponseDto getProductPricePair(String symbol) {
        Pattern dualCurrencyPattern = Pattern.compile("[A-Za-z]+-[A-Za-z]+", Pattern.CASE_INSENSITIVE);
        Pattern singleCurrencyPattern = Pattern.compile("[A-Za-z]+", Pattern.CASE_INSENSITIVE);

        Matcher dualMatcher = dualCurrencyPattern.matcher(symbol);
        Matcher singleMatcher = singleCurrencyPattern.matcher(symbol);

        Optional<ProductPricePairEntity> entity;
        if (dualMatcher.matches()) {
            entity = productPriceRepository.findFirstByProductIdOrderByTimeDesc(symbol);
        } else if (singleMatcher.matches()) {
            entity = productPriceRepository.findFirstByBaseCurrencyOrderByTimeDesc(symbol);
        } else entity = Optional.empty();

        ProductPricePairBo bo = ProductPricePairBo.from(entity.orElseThrow(() ->
                new ProductNotFoundException(String.format("Price for symbol %s not found or not tracked yet.", symbol))));

        return ProductPriceResponseDto.from(bo);
    }

    @Async
    public void fetchAndSaveProductPricePair(ProductPairBo productPairBO) {
        ProductPricePairBo productPricePairBo = coinbaseClient.findByProductId(productPairBO.getId())
                .convertToBO()
                .toBuilder()
                .withProductId(productPairBO.getId())
                .withBaseCurrency(productPairBO.getBaseCurrency())
                .withQuoteCurrency(productPairBO.getQuoteCurrency())
                .build();

        saveProductPricePair(productPricePairBo);
    }

    private void saveProductPricePair(ProductPricePairBo productPricePairBo) {
        ProductPricePairEntity entity = productPricePairBo.convertToEntity();

        if (!productPriceRepository.existsById(entity.getTradeId()))
            try {
                productPriceRepository.save(entity);
            } catch (DataIntegrityViolationException e) {
                System.out.printf("Trade ID %o already processed by another thread. Skipping.", entity.getTradeId());
            }
    }
}

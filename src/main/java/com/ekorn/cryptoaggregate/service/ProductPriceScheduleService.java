package com.ekorn.cryptoaggregate.service;

import com.ekorn.cryptoaggregate.bo.ProductPairBo;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductPriceScheduleService {

    private final ProductService productService;
    private final ProductProviderService productProviderService;

    @Scheduled(fixedDelay = 10000)
    public void getAndSaveLatestProductPairPrices() {
        List<ProductPairBo> productPairs = productProviderService.getProductPairs();
        productPairs.forEach(productService::fetchAndSaveProductPricePair);
    }
}

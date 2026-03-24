package com.ekorn.cryptoaggregate.service;

import com.ekorn.cryptoaggregate.bo.ProductPairBO;
import com.ekorn.cryptoaggregate.client.CoinbaseClient;
import com.ekorn.cryptoaggregate.dto.ProductPairDTO;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductProviderService {
    private final CoinbaseClient coinbaseClient;

    @Cacheable
    public List<ProductPairBO> getProductPairs() {
        return coinbaseClient.getProductPairs().stream()
                .filter(productPair -> !"delisted".equals(productPair.status()))
                .map(ProductPairDTO::convertToBO)
                .toList();
    }
}

package com.ekorn.cryptoaggregate.service;

import com.ekorn.cryptoaggregate.bo.ProductPairBo;
import com.ekorn.cryptoaggregate.client.CoinbaseClient;
import com.ekorn.cryptoaggregate.dto.ProductPairDto;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductProviderService {
    private final CoinbaseClient coinbaseClient;

    @Cacheable
    public List<ProductPairBo> getProductPairs() {
        return coinbaseClient.getProductPairs().stream()
                .filter(productPair -> !"delisted".equals(productPair.status()))
                .map(ProductPairDto::convertToBO)
                .toList();
    }
}

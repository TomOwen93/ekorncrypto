package com.ekorn.cryptoaggregate.client;

import com.ekorn.cryptoaggregate.dto.ProductPairDto;
import com.ekorn.cryptoaggregate.dto.ProductPricePairDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "coinbase-service", url = "https://api.exchange.coinbase.com", path = "/products")
@Headers("User-Agent: crypto-aggregate")
public interface CoinbaseClient {

    @GetMapping("/{product_id}/ticker")
    ProductPricePairDto findByProductId(@PathVariable("product_id") String product_id);

    @GetMapping("")
    List<ProductPairDto> getProductPairs();
}

package com.ekorn.cryptoaggregate.controller;


import com.ekorn.cryptoaggregate.bo.ProductPricePairBo;
import com.ekorn.cryptoaggregate.dto.ProductPriceResponseDto;
import com.ekorn.cryptoaggregate.exception.ProductNotFoundException;
import com.ekorn.cryptoaggregate.service.ProductService;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@WebMvcTest(PricesController.class)
public class PricesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    MockMvcTester mockMvcTester;

    @MockitoBean
    private ProductService productService;

    @PostConstruct
    void setUp() {
        mockMvcTester = MockMvcTester.create(mockMvc);
    }

    @Test
    public void givenSymbolExists_whenGetProductPricePair_thenReturnProductPricePairDto() {
        ProductPriceResponseDto mockProductPricePairDto =
                ProductPriceResponseDto.from(getMockProductPricePair());

        when(productService.getProductPricePair("BTC-USD")).thenReturn(mockProductPricePairDto);

        mockMvcTester.get().uri("/prices/BTC-USD")
                .assertThat()
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .satisfies(json -> {
                    // used AI here because I was going in circles...
                    assertThat(json).extractingPath("$.trade_id").isEqualTo(86326522);
                    assertThat(json).extractingPath("$.price").isEqualTo(6268.48);
                });
    }

    @Test
    public void givenSymbolExists_whenGetProductPricePairFails_thenReturn404NotFound() {
        when(productService.getProductPricePair("BTC-USD")).thenThrow(new ProductNotFoundException("Not Found"));
        assertThat(mockMvcTester.get().uri("/prices/BTC-USD"))
                .hasStatus(HttpStatus.NOT_FOUND);
    }

    private static ProductPricePairBo getMockProductPricePair() {
        return ProductPricePairBo.builder()
                .withTradeId(86326522L)
                .withProductId("BTC-USD")
                .withBaseCurrency("BTC")
                .withQuoteCurrency("USD")
                .withPrice(new BigDecimal("6268.48"))
                .withSize(new BigDecimal("0.00698254"))
                .withTime(OffsetDateTime.parse("2020-03-20T00:22:57.833Z"))
                .withBid(new BigDecimal("6265.15"))
                .withAsk(new BigDecimal("6267.71"))
                .withVolume(new BigDecimal("53602.03940154"))
                .withRfqVolume(new BigDecimal("123.122"))
                .withConversionsVolume(new BigDecimal("0.00"))
                .build();
    }
}

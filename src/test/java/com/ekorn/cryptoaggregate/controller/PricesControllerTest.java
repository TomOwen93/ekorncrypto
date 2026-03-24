package com.ekorn.cryptoaggregate.controller;


import com.ekorn.cryptoaggregate.bo.ProductPricePairBO;
import com.ekorn.cryptoaggregate.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@WebMvcTest(PricesController.class)
public class PricesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;
//
//    @Test
//    public void givenSymbolExists_whenGetProductPricePair_thenReturnProductPricePairDto() {
//        ProductPricePairDTO mockProductPricePairDto =
//                ProductPricePairDTO.from(getMockProductPricePair());
//
//        when(productService.getProductPricePair("BTC-USD")).thenReturn(mockProductPricePairDto);
//
//        mockMvc.perform(get("/prices/BTC-USD")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .
//
//
//    }

    private static ProductPricePairBO getMockProductPricePair() {
        return ProductPricePairBO.builder()
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

package com.ekorn.cryptoaggregate.service;

import com.ekorn.cryptoaggregate.bo.ProductPairBo;
import com.ekorn.cryptoaggregate.client.CoinbaseClient;
import com.ekorn.cryptoaggregate.dto.ProductPairDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductProviderServiceTest {
    @Mock
    CoinbaseClient coinbaseClient;

    @InjectMocks
    ProductProviderService productProviderService;

    @Test
    void getProductPairs_whenClientReturnsDelistedProduct_thenReturnsEmpty() {
        List<ProductPairDto> mockProductPairs = List.of(ProductPairDto.builder()
                .withId("RAN-DOM")
                .withQuoteCurrency("RAN")
                .withBaseCurrency("DOM")
                .withDisplayName("RANDOM")
                .withStatus("delisted")
                .withStatusMessage("")
                .build());

        when(coinbaseClient.getProductPairs()).thenReturn(mockProductPairs);

        List<ProductPairBo> productPairBos = productProviderService.getProductPairs();
        assertThat(productPairBos).isEmpty();
    }

    @Test
    void getProductPairs_whenClientReturnsEmpty_thenReturnsEmpty() {
        when(coinbaseClient.getProductPairs()).thenReturn(List.of());

        List<ProductPairBo> productPairBos = productProviderService.getProductPairs();
        assertThat(productPairBos).isEmpty();
    }

    @Test
    void getProductPairs_happyPath() {
        List<ProductPairDto> mockProductPairs = getProductPairMock();

        when(coinbaseClient.getProductPairs()).thenReturn(mockProductPairs);

        List<ProductPairBo> productPairBos = productProviderService.getProductPairs();
        assertThat(productPairBos).hasSize(2);

        IntStream.range(0, productPairBos.size())
                .forEach(idx -> {
                    assertThat(productPairBos.get(idx))
                            .usingRecursiveComparison()
                            .isEqualTo(mockProductPairs.get(idx));
                });
    }

    private static List<ProductPairDto> getProductPairMock() {
        return List.of(ProductPairDto.builder()
                        .withId("RAN-DOM")
                        .withQuoteCurrency("RAN")
                        .withBaseCurrency("DOM")
                        .withDisplayName("RANDOM")
                        .withStatus("online")
                        .withStatusMessage("")
                        .build(),
                ProductPairDto.builder()
                        .withId("BTC-USD")
                        .withQuoteCurrency("BTC")
                        .withBaseCurrency("USD")
                        .withDisplayName("BTCUSD")
                        .withStatus("online")
                        .withStatusMessage("")
                        .build()
        );
    }
}
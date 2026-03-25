package com.ekorn.cryptoaggregate.service;

import com.ekorn.cryptoaggregate.bo.ProductPairBo;
import com.ekorn.cryptoaggregate.client.CoinbaseClient;
import com.ekorn.cryptoaggregate.dto.ProductPricePairDto;
import com.ekorn.cryptoaggregate.dto.ProductPriceResponseDto;
import com.ekorn.cryptoaggregate.exception.ProductNotFoundException;
import com.ekorn.cryptoaggregate.persistance.ProductPricePairEntity;
import com.ekorn.cryptoaggregate.persistance.ProductPriceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    CoinbaseClient coinbaseClient;
    @Mock
    ProductProviderService productProviderServiceMock;
    @Mock
    ProductPriceRepository productPriceRepositoryMock;
    @Captor
    ArgumentCaptor<ProductPairBo> productPairCaptor;

    @InjectMocks
    ProductService productService;

    @BeforeEach
    void setup() {
        Mockito.reset();
    }

    @Test
    void getProductPricePair_withDualType_shouldCallProductIdRepository() {
        String currencyId = "BTC-USD";
        ProductPricePairEntity entity = createBtcProductPairEntity(currencyId);

        when(productPriceRepositoryMock.findFirstByProductIdOrderByTimeDesc(currencyId))
                .thenReturn(Optional.of(entity));
        ProductPriceResponseDto result = productService.getProductPricePair(currencyId);

        verify(productPriceRepositoryMock).findFirstByProductIdOrderByTimeDesc(currencyId);
        verifyNoMoreInteractions(productPriceRepositoryMock);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("productId", "baseCurrency", "quoteCurrency")
                .isEqualTo(entity);
    }

    @Test
    void getProductPricePair_withBaseCurrencyType_shouldCallBaseCurrencyRepository() {
        String currencyId = "BTC";
        ProductPricePairEntity entity = createBtcProductPairEntity(currencyId);

        when(productPriceRepositoryMock.findFirstByBaseCurrencyOrderByTimeDesc(currencyId))
                .thenReturn(Optional.of(entity));

        ProductPriceResponseDto result = productService.getProductPricePair(currencyId);

        verify(productPriceRepositoryMock).findFirstByBaseCurrencyOrderByTimeDesc(currencyId);
        verifyNoMoreInteractions(productPriceRepositoryMock);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("productId", "baseCurrency", "quoteCurrency")
                .isEqualTo(entity);
    }

    @Test
    void getProductPricePair_whenInvalidSymbol_thenShouldThrowNotFoundException() {
        String invalidSymbol = "N/A";

        ProductNotFoundException productNotFoundException =
                Assertions.assertThrows(ProductNotFoundException.class, () -> productService.getProductPricePair(invalidSymbol));

        assertTrue(productNotFoundException.getMessage().contains("not found or not tracked yet"));
    }

    @Test
    void getProductPricePair_whenPriceNotTracked_thenShouldThrowNotFoundException() {
        String currencyId = "BTC-USD";
        when(productPriceRepositoryMock.findFirstByProductIdOrderByTimeDesc(currencyId)).thenReturn(Optional.empty());

        ProductNotFoundException productNotFoundException =
                Assertions.assertThrows(ProductNotFoundException.class, () -> productService.getProductPricePair(currencyId));

        assertTrue(productNotFoundException.getMessage().contains("not found or not tracked yet"));
    }


    private static List<ProductPairBo> getProductPairBoList() {
        return List.of(
                ProductPairBo.builder()
                        .withId("BIT-USDT")
                        .withBaseCurrency("BIT")
                        .withQuoteCurrency("USDT")
                        .withDisplayName("BIT-USDT")
                        .withStatus("online")
                        .withStatusMessage("")
                        .build(),
                ProductPairBo.builder()
                        .withId("FLR-USD")
                        .withBaseCurrency("FLR")
                        .withQuoteCurrency("USD")
                        .withDisplayName("FLR-USD")
                        .withStatus("online")
                        .withStatusMessage("")
                        .build()
        );
    }

    private static ProductPricePairDto createProductPricePairDto() {
        return ProductPricePairDto.builder()
                .withTradeId(86326522L)
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

    private static ProductPricePairEntity createBtcProductPairEntity(String currencyId) {
        return ProductPricePairEntity.builder()
                .withTradeId(86326522L)
                .withProductId(currencyId)
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

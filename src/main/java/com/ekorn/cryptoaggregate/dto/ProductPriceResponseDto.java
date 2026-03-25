package com.ekorn.cryptoaggregate.dto;

import com.ekorn.cryptoaggregate.bo.ProductPricePairBo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder(setterPrefix = "with", toBuilder = true)
@Jacksonized
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductPriceResponseDto {
    long tradeId;
    BigDecimal price;
    BigDecimal size;
    OffsetDateTime time;
    OffsetDateTime savedTime;
    BigDecimal bid;
    BigDecimal ask;
    BigDecimal volume;
    BigDecimal rfqVolume;
    BigDecimal conversionsVolume;

    public static ProductPriceResponseDto from(ProductPricePairBo productPricePairBo) {
        return ProductPriceResponseDto.builder()
                .withTradeId(productPricePairBo.getTradeId())
                .withPrice(productPricePairBo.getPrice())
                .withSize(productPricePairBo.getSize())
                .withSavedTime(productPricePairBo.getSavedTime())
                .withTime(productPricePairBo.getTime())
                .withBid(productPricePairBo.getBid())
                .withAsk(productPricePairBo.getAsk())
                .withVolume(productPricePairBo.getVolume())
                .withRfqVolume(productPricePairBo.getRfqVolume())
                .withConversionsVolume(productPricePairBo.getConversionsVolume())
                .build();
    }
}

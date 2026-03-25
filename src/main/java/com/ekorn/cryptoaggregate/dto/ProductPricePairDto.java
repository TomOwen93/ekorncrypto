package com.ekorn.cryptoaggregate.dto;

import com.ekorn.cryptoaggregate.bo.ProductPricePairBo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder(setterPrefix = "with", toBuilder = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductPricePairDto(
        long tradeId,
        BigDecimal price,
        BigDecimal size,
        OffsetDateTime time,
        BigDecimal bid,
        BigDecimal ask,
        BigDecimal volume,
        BigDecimal rfqVolume,
        BigDecimal conversionsVolume
) {
    public ProductPricePairBo convertToBO() {
        return ProductPricePairBo.builder()
                .withTradeId(this.tradeId)
                .withPrice(this.price)
                .withSize(this.size)
                .withTime(this.time)
                .withBid(this.bid)
                .withAsk(this.ask)
                .withVolume(this.volume)
                .withRfqVolume(this.rfqVolume)
                .withConversionsVolume(this.conversionsVolume)
                .build();
    }
}

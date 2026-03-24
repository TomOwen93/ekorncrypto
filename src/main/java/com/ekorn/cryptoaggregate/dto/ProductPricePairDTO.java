package com.ekorn.cryptoaggregate.dto;

import com.ekorn.cryptoaggregate.bo.ProductPricePairBO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


@Builder(setterPrefix = "with", toBuilder = true)
@Jacksonized
public record ProductPricePairDTO(
        @JsonProperty("trade_id") long tradeId,
        @JsonProperty("price") BigDecimal price,
        @JsonProperty("size") BigDecimal size,
        @JsonProperty("time") OffsetDateTime time,
        @JsonProperty("bid") BigDecimal bid,
        @JsonProperty("ask") BigDecimal ask,
        @JsonProperty("volume") BigDecimal volume,
        @JsonProperty("rfq_volume") BigDecimal rfqVolume,
        @JsonProperty("conversions_volume") BigDecimal conversionsVolume
) {
    public static ProductPricePairDTO from(ProductPricePairBO productPricePairBo) {
        return ProductPricePairDTO.builder()
                .withTradeId(productPricePairBo.getTradeId())
                .withPrice(productPricePairBo.getPrice())
                .withSize(productPricePairBo.getSize())
                .withTime(productPricePairBo.getTime())
                .withBid(productPricePairBo.getBid())
                .withAsk(productPricePairBo.getAsk())
                .withVolume(productPricePairBo.getVolume())
                .withRfqVolume(productPricePairBo.getRfqVolume())
                .withConversionsVolume(productPricePairBo.getConversionsVolume())
                .build();
    }

    public ProductPricePairBO convertToBO() {
        return ProductPricePairBO.builder()
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

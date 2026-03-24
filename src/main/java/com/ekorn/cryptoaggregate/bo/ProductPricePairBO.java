package com.ekorn.cryptoaggregate.bo;

import com.ekorn.cryptoaggregate.persistance.ProductPricePairEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Value
@Builder(setterPrefix = "with", toBuilder = true)
public class ProductPricePairBO {
    @JsonProperty("trade_id")
    long tradeId;

    @JsonProperty("product_id")
    String productId;

    @JsonProperty("base_currency")
    String baseCurrency;

    @JsonProperty("quote_currency")
    String quoteCurrency;

    @JsonProperty("price")
    BigDecimal price;

    @JsonProperty("size")
    BigDecimal size;

    @JsonProperty("time")
    OffsetDateTime time;

    @JsonProperty("bid")
    BigDecimal bid;

    @JsonProperty("ask")
    BigDecimal ask;

    @JsonProperty("volume")
    BigDecimal volume;

    @JsonProperty("rfq_volume")
    BigDecimal rfqVolume;

    @JsonProperty("conversions_volume")
    BigDecimal conversionsVolume;

    public ProductPricePairEntity convertToEntity() {
        return ProductPricePairEntity.builder()
                .withTradeId(this.tradeId)
                .withProductId(this.productId)
                .withBaseCurrency(this.baseCurrency)
                .withQuoteCurrency(this.quoteCurrency)
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

    public static ProductPricePairBO from(ProductPricePairEntity productPricePairEntity) {
        return ProductPricePairBO.builder()
                .withTradeId(productPricePairEntity.getTradeId())
                .withProductId(productPricePairEntity.getProductId())
                .withBaseCurrency(productPricePairEntity.getBaseCurrency())
                .withQuoteCurrency(productPricePairEntity.getQuoteCurrency())
                .withPrice(productPricePairEntity.getPrice())
                .withSize(productPricePairEntity.getSize())
                .withTime(productPricePairEntity.getTime())
                .withBid(productPricePairEntity.getBid())
                .withAsk(productPricePairEntity.getAsk())
                .withVolume(productPricePairEntity.getVolume())
                .withRfqVolume(productPricePairEntity.getRfqVolume())
                .withConversionsVolume(productPricePairEntity.getConversionsVolume())
                .build();
    }
}


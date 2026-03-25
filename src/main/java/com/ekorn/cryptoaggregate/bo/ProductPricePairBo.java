package com.ekorn.cryptoaggregate.bo;

import com.ekorn.cryptoaggregate.persistance.ProductPricePairEntity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder(setterPrefix = "with", toBuilder = true)
public class ProductPricePairBo {
    long tradeId;
    String productId;
    String baseCurrency;
    String quoteCurrency;
    BigDecimal price;
    BigDecimal size;
    OffsetDateTime time;
    OffsetDateTime savedTime;
    BigDecimal bid;
    BigDecimal ask;
    BigDecimal volume;
    BigDecimal rfqVolume;
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
                .withSavedTime(OffsetDateTime.now())
                .withBid(this.bid)
                .withAsk(this.ask)
                .withVolume(this.volume)
                .withRfqVolume(this.rfqVolume)
                .withConversionsVolume(this.conversionsVolume)
                .build();
    }

    public static ProductPricePairBo from(ProductPricePairEntity productPricePairEntity) {
        return ProductPricePairBo.builder()
                .withTradeId(productPricePairEntity.getTradeId())
                .withProductId(productPricePairEntity.getProductId())
                .withBaseCurrency(productPricePairEntity.getBaseCurrency())
                .withQuoteCurrency(productPricePairEntity.getQuoteCurrency())
                .withPrice(productPricePairEntity.getPrice())
                .withSize(productPricePairEntity.getSize())
                .withTime(productPricePairEntity.getTime())
                .withSavedTime(productPricePairEntity.getSavedTime())
                .withBid(productPricePairEntity.getBid())
                .withAsk(productPricePairEntity.getAsk())
                .withVolume(productPricePairEntity.getVolume())
                .withRfqVolume(productPricePairEntity.getRfqVolume())
                .withConversionsVolume(productPricePairEntity.getConversionsVolume())
                .build();
    }
}


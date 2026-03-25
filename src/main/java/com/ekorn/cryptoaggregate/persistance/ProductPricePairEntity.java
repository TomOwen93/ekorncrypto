package com.ekorn.cryptoaggregate.persistance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with", toBuilder = true)
@Table(name = "productprice")
public class ProductPricePairEntity {
    @Id
    @Column(name = "tradeId")
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
}

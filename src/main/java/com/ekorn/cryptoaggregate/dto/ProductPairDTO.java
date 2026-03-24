package com.ekorn.cryptoaggregate.dto;

import com.ekorn.cryptoaggregate.bo.ProductPairBO;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;


@Builder(setterPrefix = "with", toBuilder = true)
@Jacksonized
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProductPairDTO(
        String id,
        String baseCurrency,
        String quoteCurrency,
        BigDecimal quoteIncrement,
        BigDecimal baseIncrement,
        String displayName,
        BigDecimal minMarketFunds,
        boolean marginEnabled,
        boolean postOnly,
        boolean limitOnly,
        boolean cancelOnly,
        String status,
        String statusMessage,
        boolean auctionMode
) {
    public ProductPairBO convertToBO() {
        return ProductPairBO.builder()
                .withId(this.id)
                .withBaseCurrency(this.baseCurrency)
                .withQuoteCurrency(this.quoteCurrency)
                .withQuoteIncrement(this.quoteIncrement)
                .withBaseIncrement(this.baseIncrement)
                .withDisplayName(this.displayName)
                .withMinMarketFunds(this.minMarketFunds)
                .withPostOnly(this.postOnly)
                .withLimitOnly(this.limitOnly)
                .withCancelOnly(this.cancelOnly)
                .withStatus(this.status)
                .withStatusMessage(this.statusMessage)
                .withAuctionMode(this.auctionMode)
                .build();
    }
}

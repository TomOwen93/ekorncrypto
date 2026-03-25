package com.ekorn.cryptoaggregate.dto;

import com.ekorn.cryptoaggregate.bo.ProductPairBo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder(setterPrefix = "with", toBuilder = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductPairDto(
        String id,
        String baseCurrency,
        String quoteCurrency,
        String displayName,
        String status,
        String statusMessage
) {
    public ProductPairBo convertToBO() {
        return ProductPairBo.builder()
                .withId(this.id)
                .withBaseCurrency(this.baseCurrency)
                .withQuoteCurrency(this.quoteCurrency)
                .withDisplayName(this.displayName)
                .withStatus(this.status)
                .withStatusMessage(this.statusMessage)
                .build();
    }
}
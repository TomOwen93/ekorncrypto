package com.ekorn.cryptoaggregate.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder(setterPrefix = "with", toBuilder = true)
public class ProductPairBO {
    @JsonProperty("id")
    String id;

    @JsonProperty("base_currency")
    String baseCurrency;

    @JsonProperty("quote_currency")
    String quoteCurrency;

    @JsonProperty("quote_increment")
    BigDecimal quoteIncrement;

    @JsonProperty("base_increment")
    BigDecimal baseIncrement;

    @JsonProperty("display_name")
    String displayName;

    @JsonProperty("min_market_funds")
    BigDecimal minMarketFunds;

    @JsonProperty("margin_enabled")
    boolean marginEnabled;

    @JsonProperty("post_only")
    boolean postOnly;

    @JsonProperty("limit_only")
    boolean limitOnly;

    @JsonProperty("cancel_only")
    boolean cancelOnly;

    @JsonProperty("status")
    String status;

    @JsonProperty("status_message")
    String statusMessage;

    @JsonProperty("auction_mode")
    boolean auctionMode;
}
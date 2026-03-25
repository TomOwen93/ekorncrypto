package com.ekorn.cryptoaggregate.bo;

import lombok.Builder;
import lombok.Data;

@Builder(setterPrefix = "with", toBuilder = true)
@Data
public class ProductPairBo {
    String id;
    String baseCurrency;
    String quoteCurrency;
    String displayName;
    String status;
    String statusMessage;
}
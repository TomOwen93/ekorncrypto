package com.ekorn.cryptoaggregate.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPricePairEntity, Long> {
    Optional<ProductPricePairEntity> findFirstByProductIdOrderByTimeDesc(String symbol);
    Optional<ProductPricePairEntity> findFirstByBaseCurrencyOrderByTimeDesc(String symbol);
}

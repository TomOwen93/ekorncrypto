package com.ekorn.cryptoaggregate.controller;


import com.ekorn.cryptoaggregate.dto.ProductPriceResponseDto;
import com.ekorn.cryptoaggregate.exception.ProductNotFoundException;
import com.ekorn.cryptoaggregate.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ControllerAdvice
@AllArgsConstructor
@RequestMapping("prices")
public class PricesController {
    private final ProductService productService;

    @GetMapping(value = "/{symbol}")
    public ResponseEntity<ProductPriceResponseDto> getSymbolPrice(@PathVariable String symbol) {
        return new ResponseEntity<>(productService.getProductPricePair(symbol), HttpStatus.OK);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(ProductNotFoundException productNotFoundException) {
        return new ResponseEntity<>(productNotFoundException.getMessage(), productNotFoundException.getStatusCode());
    }
}

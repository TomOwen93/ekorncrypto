package com.ekorn.cryptoaggregate.controller;


import com.ekorn.cryptoaggregate.dto.ProductPricePairDTO;
import com.ekorn.cryptoaggregate.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@ControllerAdvice
@AllArgsConstructor
@RequestMapping("prices")
public class PricesController {
    private final ProductService productService;

    @GetMapping(value = "/{symbol}")
    public ResponseEntity<ProductPricePairDTO> getSymbolPrice(@PathVariable String symbol) {
        return new ResponseEntity<>(productService.getProductPricePair(symbol), HttpStatus.OK);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleNotFoundException( ResponseStatusException responseStatusException) {
        return new ResponseEntity<>(responseStatusException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

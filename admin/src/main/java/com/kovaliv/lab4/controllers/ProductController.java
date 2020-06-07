package com.kovaliv.lab4.controllers;

import com.kovaliv.lab4.dtos.AddProductDto;
import com.kovaliv.lab4.dtos.AddQuantityOfProductDto;
import com.kovaliv.lab4.dtos.ProductDto;
import com.kovaliv.lab4.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/getProducts")
    public ResponseEntity<List<ProductDto>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @PostMapping("/addProduct")
    public ResponseEntity<ProductDto> add(@RequestBody AddProductDto addProductDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(addProductDto));
    }

    @PatchMapping("/addQuantity")
    public ResponseEntity<ProductDto> addQuantity(@RequestBody AddQuantityOfProductDto addQuantityOfProductDto) {
        return ResponseEntity.ok(productService.updateProduct(addQuantityOfProductDto));
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);

        return ResponseEntity.ok("Deleted");
    }
}

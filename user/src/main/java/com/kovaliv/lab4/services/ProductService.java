package com.kovaliv.lab4.services;

import com.kovaliv.lab4.dtos.ProductDto;
import com.kovaliv.lab4.entities.Product;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAll();

    Product findById(Long id);
}

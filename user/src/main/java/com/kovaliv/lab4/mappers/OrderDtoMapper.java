package com.kovaliv.lab4.mappers;

import com.kovaliv.lab4.dtos.OrderDto;
import com.kovaliv.lab4.dtos.ProductDto;
import com.kovaliv.lab4.entities.Order;
import com.kovaliv.lab4.entities.Product;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class OrderDtoMapper extends AbstractConverter<Order, OrderDto> {
    @Override
    protected OrderDto convert(Order order) {
        return OrderDto.builder()
                .products(covertProductsToDto(order.getProducts()))
                .price(countPrice(order.getProducts()))
                .paidStatus(order.getPaidStatus())
                .id(order.getId())
                .build();
    }

    private List<ProductDto> covertProductsToDto(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        products.forEach(
                product -> productDtos.add(
                        ProductDto.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .price(product.getPrice())
                                .quantity(product.getQuantity())
                                .build())
        );
        return productDtos;
    }

    private Double countPrice(List<Product> products) {
        AtomicReference<Double> price = new AtomicReference<>(0.0);
        products.forEach(product -> price.updateAndGet(v -> v + product.getPrice()));
        return price.get();
    }
}

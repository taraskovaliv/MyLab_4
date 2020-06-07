package com.kovaliv.lab4.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Min;

@Getter
@Setter
public class AddProductDto {
    @Column(length = 50, nullable = false)
    private String name;

    @Min(0)
    @Column(nullable = false)
    private Double price;

    @Min(0)
    @Column(nullable = false)
    private Integer quantity;
}

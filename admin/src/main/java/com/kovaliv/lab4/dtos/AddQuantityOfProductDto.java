package com.kovaliv.lab4.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class AddQuantityOfProductDto {
    @Min(1)
    private Long productId;

    @Min(1)
    private Integer quantity;
}

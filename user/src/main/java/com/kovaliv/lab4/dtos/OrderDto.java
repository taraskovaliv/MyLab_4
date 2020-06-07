package com.kovaliv.lab4.dtos;

import com.kovaliv.lab4.entities.enums.PaidStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderDto {
    private Long id;

    private Double price;

    @Enumerated(EnumType.STRING)
    private PaidStatus paidStatus;

    private List<ProductDto> products;
}

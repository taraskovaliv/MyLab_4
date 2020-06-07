package com.kovaliv.lab4.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddOrderDto {
    List<Long> productIds;
}

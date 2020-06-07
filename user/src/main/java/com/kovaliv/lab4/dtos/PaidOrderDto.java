package com.kovaliv.lab4.dtos;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;

@Getter
@Setter
public class PaidOrderDto {
    @Min(1)
    private Long orderId;

    private Long cardNumber;

    @Length(min = 100, max = 999)
    private Integer cvv;
}

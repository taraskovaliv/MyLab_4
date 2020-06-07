package com.kovaliv.lab4.services;

import com.kovaliv.lab4.dtos.AddOrderDto;
import com.kovaliv.lab4.dtos.OrderDto;
import com.kovaliv.lab4.dtos.PaidOrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAll(String username);

    OrderDto addOrder(AddOrderDto addOrderDto, String username);

    OrderDto paidOrder(PaidOrderDto paidOrderDto, String username);

}

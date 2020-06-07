package com.kovaliv.lab4.services.impl;

import com.kovaliv.lab4.constants.ErrorConstants;
import com.kovaliv.lab4.dtos.AddOrderDto;
import com.kovaliv.lab4.dtos.OrderDto;
import com.kovaliv.lab4.dtos.PaidOrderDto;
import com.kovaliv.lab4.entities.Order;
import com.kovaliv.lab4.entities.Product;
import com.kovaliv.lab4.entities.enums.PaidStatus;
import com.kovaliv.lab4.repos.OrderRepo;
import com.kovaliv.lab4.repos.ProductRepo;
import com.kovaliv.lab4.security.entities.Role;
import com.kovaliv.lab4.security.entities.User;
import com.kovaliv.lab4.security.repository.RoleRepository;
import com.kovaliv.lab4.security.repository.UserRepository;
import com.kovaliv.lab4.security.services.UserService;
import com.kovaliv.lab4.services.OrderService;
import com.kovaliv.lab4.services.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ProductRepo productRepo;
    private final ProductService productService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public List<OrderDto> getAll(String username) {
        return orderRepo.findAll()
                .stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto addOrder(AddOrderDto addOrderDto, String username) {
        Order order = Order.builder()
                .paidStatus(PaidStatus.NOT_PAID)
                .user(userService.findByUsername(username))
                .products(getProducts(addOrderDto.getProductIds()))
                .build();

        order = orderRepo.save(order);

        return modelMapper.map(order, OrderDto.class);
    }

    private List<Product> getProducts(List<Long> ids) {
        List<Product> products = new ArrayList<>();
        for (Long id : ids) {
            Product product = productService.findById(id);

            if (product.getQuantity() == 0) {
                throw new RuntimeException(ErrorConstants.PRODUCT_IS_EMPTY + id);
            }

            product.setQuantity(product.getQuantity() - 1);
            products.add(productRepo.save(product));
        }
        return products;
    }

    @Override
    public OrderDto paidOrder(PaidOrderDto paidOrderDto, String username) {
        Order order = orderRepo.getOne(paidOrderDto.getOrderId());

        paid(paidOrderDto);

        order.setPaidStatus(PaidStatus.PAID);
        order = orderRepo.save(order);

        checkToRemoveFromBlackList(username);

        return modelMapper.map(order, OrderDto.class);
    }

    private void paid(PaidOrderDto paidOrderDto) {
        String cardNumber = paidOrderDto.getCardNumber().toString();
        if (cardNumber.length() != 16) {
            throw new BadCredentialsException(ErrorConstants.CARDNUMBER_NOT_CORRECT);
        }
    }

    private void checkToRemoveFromBlackList(String username) {
        User user = userService.findByUsername(username);
        if (isInBlackList(user) && isAllOrderPaid(user)) {
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName("user"));
            user.setRoles(roles);
            userRepository.save(user);
        }
    }

    private boolean isAllOrderPaid(User user) {
        return user.getOrders().stream().noneMatch(order -> order.getPaidStatus() == PaidStatus.NOT_PAID);
    }

    private boolean isInBlackList(User user) {
        return user.getRoles().stream().anyMatch(role -> role.getName().equals("black_list_user"));
    }
}

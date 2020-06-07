package com.kovaliv.lab4.entities;

import com.kovaliv.lab4.entities.enums.PaidStatus;
import com.kovaliv.lab4.security.entities.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaidStatus paidStatus;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Product> products;
}

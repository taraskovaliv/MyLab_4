package com.kovaliv.lab4.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Min(0)
    @Column(nullable = false)
    private Double price;

    @Min(0)
    @Column(nullable = false)
    private Integer quantity;

    @ManyToMany(mappedBy = "products")
    private List<Order> orders;
}

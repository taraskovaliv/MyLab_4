package com.kovaliv.lab4.security.entities;

import com.kovaliv.lab4.entities.Order;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 4, max = 20)
    private String username;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @ManyToMany
    private Set<Role> roles;
}

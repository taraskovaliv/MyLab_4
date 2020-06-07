package com.kovaliv.lab4.security.repository;

import com.kovaliv.lab4.security.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

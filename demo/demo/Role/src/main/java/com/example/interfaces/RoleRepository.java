package com.example.interfaces;

import com.example.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByNameOfRole(String roleName);
}

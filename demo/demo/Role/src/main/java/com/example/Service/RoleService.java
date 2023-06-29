package com.example.Service;

import com.example.entity.Role;
import com.example.interfaces.RoleInterfaces;
import com.example.interfaces.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleService implements RoleInterfaces {
    private final RoleRepository roleRepository;
    @Override
    public List<Role> getRoles() {
       return roleRepository.findAll();
    }

    @Override
    public Role saveRole(Role role) {
        role.setNameOfRole(role.getNameOfRole());
        log.info("Save role {} to database.\n", role.getNameOfRole());
        return roleRepository.save(role);
    }
}

package com.example.Controller;

import com.example.DTO.RoleDTO;
import com.example.entity.Role;
import com.example.Service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/service")
@Slf4j
public class RoleController {
    private final RoleService roleService;
    @GetMapping("/role")
    public ResponseEntity<List<RoleDTO>> getUser(){
        ModelMapper modelMapper = new ModelMapper();
        List<RoleDTO> dtoList = new ArrayList<>();
        List<Role> roleList = null;
        roleList = roleService.getRoles();

        List<RoleDTO> roleDTOS = roleList.stream().map(song -> modelMapper.map(song, RoleDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(roleDTOS);
    }
    @PostMapping("/role/save")
    public ResponseEntity<Role>saveRole(@RequestBody Role role){
        URI uri  = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("role/save").toUriString());
        return ResponseEntity.created(uri).body(roleService.saveRole(role));
    }
}//TODO move all in userService


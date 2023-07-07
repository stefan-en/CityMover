package com.example.Controller;

import com.example.DTO.UserDTO;
import com.example.entity.User;
import com.example.Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/service")
@Slf4j
public class UserController {
    private final UserService userService;
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUser(){
        ModelMapper modelMapper = new ModelMapper();
        List<UserDTO> dtoList = new ArrayList<>();
        List<User> userList = null;
        userList = userService.getSongs();

        List<UserDTO> userDTOS = userList.stream().map(song -> modelMapper.map(song, UserDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(userDTOS);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Map<String, String>> getUserId(@PathVariable("username")  String username){
        String id = userService.getUserId(username);
        Map<String, String> response = new HashMap<>();
        response.put("id", id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/users/save")
    public ResponseEntity<User>saveUser(@RequestBody User artist){
        URI uri  = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(artist));
    }

    @PostMapping("/users/role")
    public ResponseEntity<Void> addRoleUser(@RequestParam("nameOfUser") String nameOfUser, @RequestBody String role) throws JsonProcessingException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/role").toUriString());
        userService.addRoleToUser(nameOfUser, role);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/users/updatePassword")
    public ResponseEntity<Void> updatePassword(@RequestParam("email") String email,
                                 @RequestParam("newPassword") String newPassword) {
        userService.updatePassword(email, newPassword);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/users/emailcode")
    public ResponseEntity<Map<String, Integer>> getCode(@RequestParam("email") String email){
        Integer cod = userService.getCodeRset(email);
        Map<String, Integer> response = new HashMap<>();
        response.put("cod", cod);
        return ResponseEntity.ok().body(response);
    }
}

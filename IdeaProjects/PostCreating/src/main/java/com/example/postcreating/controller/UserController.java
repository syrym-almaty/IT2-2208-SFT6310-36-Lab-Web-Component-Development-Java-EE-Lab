package com.example.postcreating.controller;

import com.example.postcreating.dto.UserDTO;
import com.example.postcreating.service.UserService;
import jakarta.annotation.PreDestroy;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserDTO createUser(@Valid @RequestBody final UserDTO userDTO){
        return userService.addUser(userDTO);
    }

    @PatchMapping("/{userId}")
    public UserDTO updateUser(@PathVariable final Long userId, @RequestBody final UserDTO userDTO){
        return userService. updateUser(userId, userDTO);
    }

/*
    @PreAuthorize("hasAuthority('ADMIN')")
*/
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable final Long userId){
        userService.eraseUser(userId);
    }

    @GetMapping("/{userId}")
    public UserDTO getUser(@PathVariable Long userId){
        return userService.getFromId(userId);
    }
}

package com.mokhs.springvalidation.controller;

import com.mokhs.springvalidation.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@Slf4j
@RestController
@RequestMapping("/v1/user")
public class UserApi {


    // user post
    @PostMapping
    public ResponseEntity<User> post(@RequestBody @Valid User dto) {
        log.info("post user : {}", dto);
        return ResponseEntity.ok(dto);
    }


    // user get
    @GetMapping
    public ResponseEntity<User> getDto(@RequestParam String name,
                                       @RequestParam Integer age,
                                       @RequestParam String email,
                                       @RequestParam String phone) {
        User user = User.builder()
                .name(name)
                .age(age)
                .email(email)
                .phone(phone)
                .build();
        log.info("get user : {}", user);
        return ResponseEntity.ok(user);
    }

}

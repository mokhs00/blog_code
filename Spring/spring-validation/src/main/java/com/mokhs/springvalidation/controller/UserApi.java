package com.mokhs.springvalidation.controller;

import com.mokhs.springvalidation.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

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
    public ResponseEntity<User> get(@RequestParam @NotBlank String name,
                                    @RequestParam @Min(1) Integer age,
                                    @RequestParam @Email String email,
                                    @RequestParam @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다. 01x-xxx(x)-xxxx")
                                            String phone) {
        User user = User.builder()
                .name(name)
                .age(age)
                .email(email)
                .phone(phone)
                .build();
        log.info("get user : {}", user);
        return ResponseEntity.ok(user);
    }


    @GetMapping("{userId}")
    public void findById(@PathVariable("userId") Long id) {

    }

}

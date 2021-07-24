package com.mokhs.springvalidation.controller;

import com.mokhs.springvalidation.annotation.DateTime;
import com.mokhs.springvalidation.dto.Day;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/v1")
public class Api {

    @GetMapping("/items")
    public ResponseEntity items(@RequestParam("createdDate") @DateTime String createdDate,
                                BindingResult bindingResult) {
        return ResponseEntity.ok(createdDate);
    }

    @PostMapping("/day")
    public ResponseEntity day(@RequestBody @Valid Day dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();

            List<ObjectError> errors = bindingResult.getAllErrors();

            errors.forEach(objectError -> {
                FieldError fieldError = (FieldError) objectError;
                String field = fieldError.getField();
                String message = fieldError.getDefaultMessage();
                sb.append("field : " + field + "\n");
                sb.append("message : " + message + "\n");
            });

            return ResponseEntity.badRequest()
                    .body(sb.toString());
        }

        return ResponseEntity.ok(dto);
    }



}

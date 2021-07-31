package com.mokhs.springvalidation.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        log.error("handleException", e);
        return ResponseEntity.internalServerError().body("서버 내부 오류");
    }

    /**
     * RequestBody validation 실패 시 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);

        BindingResult bindingResult = e.getBindingResult();

        List<String> messages = bindingResult.getAllErrors().stream()
                .map(oe -> {
                    FieldError fieldError = (FieldError) oe;
                    return fieldError.getField() + " : " + fieldError.getDefaultMessage();
                })
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(messages);
    }

    /**
     * 서버가 해당 요청을 읽을 수 없을 때 발생
     * ex) json 형식이 잘못됨
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException", e);
        return ResponseEntity.badRequest().body("잘못된 요청입니다.");
    }


    /**
     * RequestParam 바인딩 실패 시 발생
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException", e);

        return ResponseEntity.badRequest().body(e.getParameterName() + " : " + e.getMessage());
    }

    /**
     * 데이터 유효성 검사 실패 시 발생
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException e) {
        log.error("ConstraintViolationException", e);
        List<String> collect = e.getConstraintViolations().stream()
                .map(cv -> {
                    String propertyPath = cv.getPropertyPath().toString();

                    String field = propertyPath.substring(propertyPath.lastIndexOf(".") + 1);
                    String message = cv.getMessage();
                    String invalidValue = cv.getInvalidValue().toString();

                    return field + " : " + message + " : " + invalidValue;
                }).collect(Collectors.toList());

        return ResponseEntity.badRequest().body(collect);
    }
}

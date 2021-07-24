package com.mokhs.springvalidation.dto;

import lombok.Getter;

import javax.validation.constraints.AssertTrue;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class Day {

    String localDate;

    @AssertTrue(message = "yyyy-MM-dd 형식에 맞지 않습니다.")
    public boolean isValidLocalDate() {

        try {
            LocalDate.parse(getLocalDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            return false;
        }

        return true;
    }


}

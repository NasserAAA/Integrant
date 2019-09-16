package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

import com.example.demo.validations.FirstOrder;
import com.example.demo.validations.SecondOrder;
import com.example.demo.validations.ValidEmail;
import com.example.demo.validations.ValidPassword;

import lombok.Data;


@Data
public class UserDto {
    private @NotBlank(message = "Name is mandatory",groups = FirstOrder.class) String name;
     
    @NotBlank(groups = FirstOrder.class)
    @ValidPassword(groups = SecondOrder.class)
    private String password;
    @NotBlank(groups = FirstOrder.class)
    private String matchingPassword;
     
    private @NotBlank(message = "Email is mandatory",groups = FirstOrder.class) @ValidEmail(groups = SecondOrder.class) String email;

}
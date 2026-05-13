package com.peiwan.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class RegisterParam {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String realName;
    @Pattern(regexp = "^1[3-9]\\d{9}$")
    private String phone;
    private String role; // player / master
    // 如果是陪玩师，还需以下字段
    private String gameLevel;
    private Double pricePerHour;
    private String tags;
}
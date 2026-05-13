package com.peiwan.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class PayParam {
    @NotNull
    private Long orderId;
}
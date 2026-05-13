package com.peiwan.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class OrderCreateParam {
    @NotNull
    private Long masterId;  // 选择的陪玩师ID
    @NotNull
    private BigDecimal amount;
}
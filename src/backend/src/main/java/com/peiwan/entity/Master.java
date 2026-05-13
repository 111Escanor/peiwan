package com.peiwan.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;

@Data
@TableName("master")
public class Master {
    @TableId
    private Long userId;
    private String gameLevel;
    private BigDecimal pricePerHour;
    private String tags;
    private Integer totalOrders;
    private Double rating;
    private String authStatus; // pending, approved, rejected
}
package com.peiwan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("orders")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long playerId;
    private Long masterId;
    private BigDecimal amount;
    private String status; // pending, paid, in_service, completed, cancelled
    private Date createTime;
    private Date payTime;
    private Date completeTime;
}
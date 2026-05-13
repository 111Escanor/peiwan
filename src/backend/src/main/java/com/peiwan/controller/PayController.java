package com.peiwan.controller;

import com.peiwan.dto.PayParam;
import com.peiwan.dto.Result;
import com.peiwan.entity.Payment;
import com.peiwan.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/pay")
@CrossOrigin
public class PayController {

    @Autowired
    private PayService payService;

    @PostMapping("/mock")
    public Result<Payment> mockPay(@RequestAttribute Long userId,
                                   @Valid @RequestBody PayParam param) {
        Payment payment = payService.mockPay(param.getOrderId());
        return Result.success(payment);
    }
}
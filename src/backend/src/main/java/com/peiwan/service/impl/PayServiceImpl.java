package com.peiwan.service.impl;

import com.peiwan.entity.Order;
import com.peiwan.entity.Payment;
import com.peiwan.mapper.OrderMapper;
import com.peiwan.mapper.PaymentMapper;
import com.peiwan.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.UUID;

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional
    public Payment mockPay(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || !"pending".equals(order.getStatus())) {
            throw new RuntimeException("订单状态不正确");
        }
        // 创建支付记录
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(order.getAmount());
        payment.setStatus("success");
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setPayTime(new Date());
        paymentMapper.insert(payment);

        // 更新订单状态为已支付（这里注意：在我们的逻辑中，接单同时视为已支付。为简化，可以直接更新订单为paid）
        // 但根据之前的设计，支付成功后订单状态变为 paid。我们这里统一：模拟支付成功后订单变为paid，且不自动分配陪玩师？
        // 实际上我们应该先有订单，然后玩家支付 -> 进入已支付，然后陪玩师接单 -> in_service。
        // 为了流程合理，pay成功后订单状态为paid，masterId仍为空，后面由陪玩师接单。
        order.setStatus("paid");
        order.setPayTime(new Date());
        orderMapper.updateById(order);
        return payment;
    }
}
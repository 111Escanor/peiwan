package com.peiwan.controller;

import com.peiwan.dto.OrderCreateParam;
import com.peiwan.dto.Result;
import com.peiwan.entity.Order;
import com.peiwan.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public Result<Order> createOrder(@RequestAttribute Long userId,
                                     @Valid @RequestBody OrderCreateParam param) {
        Order order = orderService.createOrder(userId, param);
        return Result.success(order);
    }

    @PostMapping("/accept/{orderId}")
    public Result<String> acceptOrder(@RequestAttribute Long userId,
                                      @PathVariable Long orderId) {
        boolean success = orderService.acceptOrder(orderId, userId);
        if (success) {
            return Result.success("接单成功");
        } else {
            return Result.error(400, "接单失败，订单状态异常");
        }
    }

    @PostMapping("/complete/{orderId}")
    public Result<String> completeOrder(@RequestAttribute Long userId,
                                        @PathVariable Long orderId) {
        boolean success = orderService.completeOrder(orderId, userId);
        if (success) {
            return Result.success("服务完成");
        } else {
            return Result.error(400, "操作失败");
        }
    }

    @GetMapping("/player/orders")
    public Result<List<Order>> getPlayerOrders(@RequestAttribute Long userId) {
        return Result.success(orderService.getOrdersByPlayer(userId));
    }

    @GetMapping("/master/orders")
    public Result<List<Order>> getMasterOrders(@RequestAttribute Long userId) {
        return Result.success(orderService.getOrdersByMaster(userId));
    }
}
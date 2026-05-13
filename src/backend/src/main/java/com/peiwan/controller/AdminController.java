package com.peiwan.controller;

import com.peiwan.dto.Result;
import com.peiwan.entity.Master;
import com.peiwan.entity.Order;
import com.peiwan.entity.User;
import com.peiwan.service.MasterService;
import com.peiwan.service.OrderService;
import com.peiwan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private MasterService masterService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/pending-masters")
    public Result<List<Master>> getPendingMasters() {
        // 实际可能需要写一个专门查 pending 的方法，这里简化：全部取出再过滤（生产环境不建议）
        // 为简洁，在 MasterService 中添加新方法更好，此处省略实现细节，可自行扩展。
        return Result.success(null);
    }

    @PostMapping("/approve-master/{userId}")
    public Result<String> approveMaster(@PathVariable Long userId, @RequestParam Boolean approved) {
        masterService.approveMaster(userId, approved);
        return Result.success(approved ? "审核通过" : "已拒绝");
    }

    @GetMapping("/all-orders")
    public Result<List<Order>> getAllOrders() {
        // 需要 OrderService 中提供无条件查询所有订单的方法，自行添加
        return Result.success(null);
    }

    @GetMapping("/statistics")
    public Result<?> getStatistics() {
        // 返回统计信息
        return Result.success(null);
    }
}
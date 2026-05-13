package com.peiwan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.peiwan.dto.OrderCreateParam;
import com.peiwan.entity.Order;
import com.peiwan.entity.Master;
import com.peiwan.mapper.MasterMapper;
import com.peiwan.mapper.OrderMapper;
import com.peiwan.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private MasterMapper masterMapper;

    @Override
    public Order createOrder(Long playerId, OrderCreateParam param) {
        Order order = new Order();
        order.setPlayerId(playerId);
        order.setMasterId(param.getMasterId());
        order.setAmount(param.getAmount());
        order.setStatus("pending");
        order.setCreateTime(new Date());
        orderMapper.insert(order);
        return order;
    }

    @Override
    @Transactional
    public boolean acceptOrder(Long orderId, Long masterId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || !"pending".equals(order.getStatus())) {
            return false;
        }
        order.setMasterId(masterId);
        order.setStatus("paid");
        order.setPayTime(new Date());
        orderMapper.updateById(order);

        // 更新陪玩师接单数
        Master master = masterMapper.selectById(masterId);
        if (master != null) {
            master.setTotalOrders(master.getTotalOrders() + 1);
            masterMapper.updateById(master);
        }
        return true;
    }

    @Override
    public boolean completeOrder(Long orderId, Long masterId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || !"paid".equals(order.getStatus()) || !order.getMasterId().equals(masterId)) {
            return false;
        }
        order.setStatus("completed");
        order.setCompleteTime(new Date());
        return orderMapper.updateById(order) > 0;
    }

    @Override
    public boolean cancelOrder(Long orderId, Long playerId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || !order.getPlayerId().equals(playerId) || !"pending".equals(order.getStatus())) {
            return false;
        }
        order.setStatus("cancelled");
        return orderMapper.updateById(order) > 0;
    }

    @Override
    public List<Order> getOrdersByPlayer(Long playerId) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getPlayerId, playerId);
        return orderMapper.selectList(wrapper);
    }

    @Override
    public List<Order> getOrdersByMaster(Long masterId) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getMasterId, masterId);
        return orderMapper.selectList(wrapper);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderMapper.selectById(id);
    }
}
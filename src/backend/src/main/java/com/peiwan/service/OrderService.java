package com.peiwan.service;

import com.peiwan.dto.OrderCreateParam;
import com.peiwan.entity.Order;
import java.util.List;

public interface OrderService {
    Order createOrder(Long playerId, OrderCreateParam param);
    boolean acceptOrder(Long orderId, Long masterId);
    boolean completeOrder(Long orderId, Long masterId);
    boolean cancelOrder(Long orderId, Long playerId);
    List<Order> getOrdersByPlayer(Long playerId);
    List<Order> getOrdersByMaster(Long masterId);
    Order getOrderById(Long id);
}
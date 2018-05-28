package com.shhxzq.bs.service;

import com.shhxzq.bs.model.Order;

import java.util.List;

/**
 * Created by houjiagang on 2016/12/19.
 */
public interface OrderService {

    List<Order> findOrders(int pageNow, String orderType);

    Order findOrder(Long id);

    int updateOrder(Order order);

    int deleteOrder(Long id);

    int saveOrder(Order order);

    String genFile(String orderType);
}

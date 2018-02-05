package com.minibox.dao;

import com.minibox.po.OrderPo;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper {

    boolean insertOrder(OrderPo orderPo);

    boolean removeOrder(int orderId);

    OrderPo findOrderByOrderId(int orderId);

    OrderPo findOrderByBoxId(int boxId);
}

package com.minibox.service;

import com.minibox.dao.OrderMapper;
import com.minibox.dao.SaleMapper;
import com.minibox.exception.RollbackException;
import com.minibox.po.OrderPo;
import com.minibox.po.SalePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

    @Autowired
    private SaleMapper saleMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Transactional(rollbackFor = Exception.class)
    public void addSaleInfoAndRemoveOrder(int orderId, double cost) {
        SalePo sale = getSaleByOrderIdAndCost(orderId, cost);
        if (!(saleMapper.insertSaleInfo(sale) || !orderMapper.removeOrder(orderId))) {
            throw new RollbackException();
        }
    }

    private SalePo getSaleByOrderIdAndCost(int orderId, double cost){
        OrderPo orderPo = orderMapper.findOrderByOrderId(orderId);
        return SalePo.builder()
                .boxId(orderPo.getBoxId())
                .cost(cost)
                .groupId(orderPo.getGroupId())
                .orderTime(orderPo.getOrderTime())
                .userId(orderPo.getUserId())
                .build();
    }
}

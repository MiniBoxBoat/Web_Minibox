package com.minibox.service.box;

import com.minibox.exception.*;
import com.minibox.po.Box;
import com.minibox.po.Order;
import com.minibox.vo.BoxVo;
import com.minibox.vo.GroupVo;

import java.util.List;

public interface BoxService {

    /**
     * 通过地点得到存放点并计算出最短距离存放点
     * @param destination 搜索位置
     * @param lng 当前位置经度
     * @param lat 当前位置纬度
     * @return 存放点
     */
    List<GroupVo> getGroupByDestination(String destination);

    /**
     * 得到指定经纬度附近5000米之内的存放点
     * @param lat
     * @param lng
     * @return
     */
    List<GroupVo> getGroupArourd(double lat, double lng);

    /**
     * 保存订单
     * @return 是否下单成功
     */
    List<Integer> addOrder(Order order, String size,int boxNum, String taken) throws BoxIsBusyException, TakenVirifyException, RollbackException, ParameterException;

    /**
     *保存销售信息
     * @param orderId 订单id
     * @param cost 消费
     * @return 是否保存成功
     */
    boolean addSaleInfo(int orderId, double cost) throws RollbackException;

    /**
     * 得到用户正在使用的box
     * @param taken taken
     * @return 用户的箱子
     */
    List<BoxVo> getUsingBoxes(String taken) throws ServerException, TakenVirifyException;

    /**
     * 得到用户预约的箱子
     * @param taken taken
     * @return
     */
    List<BoxVo> getReservingBoxes(String taken) throws ServerException, TakenVirifyException;

    /**
     * 删除box
     * @param orderId  箱子id
     * @return 是否删除成功
     */
    boolean deleteOrder(int orderId) throws RollbackException;

/*    *//**
     * 通过groupId得倒所有箱子
     * @param groupId 存放点id
     * @return List<Box>
     *//*
    List<BoxVo> getAllBoxesByGroupId(int groupId);*/

    /**
     * 通过groupId得到group的信息
     * @param groupId
     * @return
     */
    GroupVo getGroup(int groupId);
}

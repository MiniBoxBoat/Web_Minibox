package com.minibox.service.box;

import com.minibox.exception.BoxIsBusyException;
import com.minibox.exception.RollbackException;
import com.minibox.exception.TakenVirifyException;
import com.minibox.po.Box;
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
    List<GroupVo> getGroupByDestination(String destination, double lng, double lat);

    /**
     * 保存订单
     * @param userName 用户名
     * @param groupId 存放点id
     * @param boxId boxId
     * @return 是否下单成功
     */
    boolean addOrder(String userName, int groupId, int boxId, String taken) throws BoxIsBusyException, TakenVirifyException, RollbackException;

    /**
     *保存销售信息
     * @param orderId 订单id
     * @param cost 消费
     * @return 是否保存成功
     */
    boolean addSaleInfo(int orderId, double cost) throws RollbackException;

    /**
     * 得到用户正在使用的box
     * @param userName 用户名
     * @return 用户的箱子
     */
    List<Box> getBoxes(String userName);

    /**
     * 删除box
     * @param orderId  箱子id
     * @return 是否删除成功
     */
    boolean deleteOrder(int orderId) throws RollbackException;

    /**
     * 通过groupId得倒所有箱子
     * @param groupId 存放点id
     * @return List<Box>
     */
    List<Box> getAllBoxesByGroupId(int groupId);
}

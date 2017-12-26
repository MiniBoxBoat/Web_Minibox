package com.minibox.dao;

import com.minibox.po.Box;
import com.minibox.po.GroupPo;
import com.minibox.po.Order;
import com.minibox.po.Sale;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.security.acl.Group;
import java.util.List;

/**
 * @author MEI
 */
@Repository
public interface BoxMapper {

    /**
     * 通过输入地址来得到存放点
     * @param destination 输入地址
     * @return 存放点
     */
    List<GroupPo> findGroupsByDestination(String destination);

    /**
     * 箱子被预定或者被使用后改变可用箱子数量
     * @param position 存放点位置
     * @return 是否修改成功
     */
    boolean reduceGroupBoxNum(String position);

    /**
     * 箱子使用完过后可用箱子数量增加
     * @param groupId 存放点id
     * @param boxNum 增加箱子的数量
     * @return 是否修改成功
     */
    boolean addGroupBoxNum(@Param("groupId") int groupId, @Param("boxNum") int boxNum);

    /**
     * 储存一个订单
     * @param userName 用户名
     * @param groupId 存放点id
     * @param boxId 箱子id
     * @return 是否存储成功
     */
    boolean insertOrder(@Param("userName") String userName,@Param("groupId") int groupId, @Param("boxId") int boxId);

    /**
     * 通过order的全部参数来添加一个order
     * @param order order
     * @return 是否添加成功
     */
    boolean insertOrderByAllParemater(Order order);

    /**
     * 删除订单
     * @param orderId 订单id
     * @return 是否删除成功
     */
    boolean removeOrder(int orderId);


    /**
     * 通过orderId得到order
     * @param orderId
     * @return
     */
    Order findOrderByOrderId(int orderId);

    /**
     * 储存销售信息
     * @param sale 销售信息对象
     * @return 是否储存成功
     */
    boolean insertSaleInfo(Sale sale);

    /**
     * 通过boxId来找到箱子
     * @param boxId 箱子的id
     * @return 箱子
     */
    Box findBoxByBoxId(int boxId);

    /**
     * 得到用户正在使用的box
     * @param userName 用户名
     * @return 用户使用的箱子
     */
    List<Box> findBoxes(String userName);

    /**
     * 得到指定存放点的空小箱子
     * @param groupId
     * @return
     */
    List<Box> findEmptySmallBox(int groupId);

     /**
     * 得到指定存放点的空大箱子
     * @param groupId
     * @return
     */
    List<Box> findEmptyLargeBox(int groupId);

    /**
     * 通过存放点id得到存放点的所有箱子
     * @param groupId 存放点id
     * @return 存放点的所有箱子
     */
    List<Box> findBoxesByGroupId(int groupId);

    /**
     * 得到所有指定大小的空闲箱子的id
     * @param position 存放点
     * @return 所有空闲箱子的id
     */
    List<Box> findEmptyBoxes(@Param("position") String position, @Param("boxSize") String boxSize);

    /**
     * 改变箱子的使用状态
     * @param boxId 箱子id
     * @return 是否更改成功
     */
    boolean updateBoxStatus(int boxId);

    /**
     * 通过地址找到存放点
     * @param position 地址
     * @return 存放点
     */
    GroupPo findGroupByPosition(String position);




}

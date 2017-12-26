package com.minibox.service.box.impl;

import com.minibox.dao.BoxMapper;
import com.minibox.dao.UserMapper;
import com.minibox.dto.UserDto;
import com.minibox.exception.BoxIsBusyException;
import com.minibox.exception.ParameterException;
import com.minibox.exception.RollbackException;
import com.minibox.exception.TakenVirifyException;
import com.minibox.po.Box;
import com.minibox.po.GroupPo;
import com.minibox.po.Order;
import com.minibox.po.Sale;
import com.minibox.service.box.BoxService;
import com.minibox.vo.GroupVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.Distance;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.minibox.constants.BoxSize.LARGE;
import static com.minibox.constants.BoxSize.SMALL;

/**
 * @author MEI
 */
@Service
public class BoxServiceImpl implements BoxService {

    @Resource
    private BoxMapper boxMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public List<GroupVo> getGroupByDestination(String destination) {
        List<GroupPo> groupPos = boxMapper.findGroupsByDestination(destination);
        List<GroupVo> groupVos = new ArrayList<>();
        groupPos.forEach(groupPo -> {
            GroupVo groupVo = new GroupVo();
            groupVo.setPosition(groupPo.getPosition());
            groupVo.setQuantity(groupPo.getQuantity());
            groupVo.setLng(groupPo.getLng());
            groupVo.setLat(groupPo.getLat());
            groupVo.setGroupId(groupPo.getGroupId());
            groupVo.setEmptySmallBoxNum(boxMapper.findEmptySmallBox(groupPo.getGroupId()).size());
            groupVo.setEmptyLargeBoxNum(boxMapper.findEmptyLargeBox(groupPo.getGroupId()).size());
            groupVos.add(groupVo);
        });

        return groupVos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addOrder(String userName, int groupId,String size, String taken) throws BoxIsBusyException, TakenVirifyException, RollbackException, ParameterException {
        if (!taken.equals(userMapper.findUserByUserName(userName).getTaken())) {
            throw new TakenVirifyException();
        }

        if (!size.equals(SMALL.size()) && !size.equals(LARGE.size())){
            throw new ParameterException("size参数错误");
        }
        int boxId;
        if (size.equals(SMALL.size())){
            List<Box> smallBoxes = boxMapper.findEmptySmallBox(groupId);
            if (smallBoxes.size()==0){
                throw new ParameterException("箱子已经用完");
            }
            boxId = smallBoxes.get(0).getBoxId();
        }else {
            List<Box> largeBoxes = boxMapper.findEmptyLargeBox(groupId);
            if (largeBoxes.size()==0){
                throw new ParameterException("箱子已经用完");
            }
            boxId = largeBoxes.get(0).getBoxId();
        }
        UserDto user = userMapper.findUserByUserName(userName);
        if (!(boxMapper.insertOrder(userName, groupId, boxId)
                && boxMapper.updateBoxStatus(boxId)
                && userMapper.updateUseTime(user.getUserId()))) {
            throw new RollbackException();
        }
        return boxId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addSaleInfo(int orderId, double cost) throws RollbackException {
        Order order = boxMapper.findOrderByOrderId(orderId);
        Sale sale = new Sale();
        sale.setBoxId(order.getBoxId());
        sale.setCost(cost);
        sale.setGroupId(order.getGroupId());
        sale.setOrderTime(order.getOrderTime());
        sale.setUserName(order.getUserName());

        if (!(boxMapper.insertSaleInfo(sale) && boxMapper.removeOrder(orderId))) {
            throw new RollbackException();
        }
        return true;
    }


    @Override
    public List<Box> getBoxes(String userName) {
        return boxMapper.findBoxes(userName);
    }

    @Override
    public boolean deleteOrder(int orderId) throws RollbackException {
        Order order = boxMapper.findOrderByOrderId(orderId);
        int boxId = order.getBoxId();
        int groupId = order.getGroupId();
        if (!(boxMapper.removeOrder(orderId) && boxMapper.updateBoxStatus(boxId) && boxMapper.addGroupBoxNum(groupId, 1))) {
            throw new RollbackException();
        }
        return true;
    }

    @Override
    public List<Box> getAllBoxesByGroupId(int groupId) {
        return boxMapper.findBoxesByGroupId(groupId);
    }

    public boolean checkBoxStatus(int boxId) throws BoxIsBusyException {
        Box box = boxMapper.findBoxByBoxId(boxId);
        if (box.getBoxStatus() == 1) {
            throw new BoxIsBusyException();
        }
        return true;
    }
}

package com.minibox.service.box.impl;

import com.minibox.dao.BoxMapper;
import com.minibox.dao.UserMapper;
import com.minibox.dto.UserDto;
import com.minibox.exception.*;
import com.minibox.po.Box;
import com.minibox.po.GroupPo;
import com.minibox.po.Order;
import com.minibox.po.Sale;
import com.minibox.service.box.BoxService;
import com.minibox.vo.BoxVo;
import com.minibox.vo.GroupVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.Distance;
import util.JavaWebTaken;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
            GroupVo groupVo = groupPoConvert(groupPo);
            groupVos.add(groupVo);
        });
        return groupVos;
    }


    @Override
    public List<GroupVo> getGroupArourd(double lat, double lng) {
        List<GroupPo> groupPos = boxMapper.findAllGroup();
        List<GroupVo> groupVos = new ArrayList<>();
        List<GroupPo> groupPos1 = groupPos.stream().filter(groupPo -> Distance.GetDistance(lat,lng, groupPo.getLat(), groupPo.getLng())<5).collect(Collectors.toList());
        groupPos1.forEach(groupPo ->{
            GroupVo groupVo = groupPoConvert(groupPo);
            groupVos.add(groupVo);
        });
        return groupVos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addOrder(int userId, int groupId,String size, String taken) throws BoxIsBusyException, TakenVirifyException, RollbackException, ParameterException {
        if (!taken.equals(userMapper.findUserByUserId(userId).getTaken())) {
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
        UserDto user = userMapper.findUserByUserId(userId);
        if (!(boxMapper.insertOrder(userId, groupId, boxId)
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
        sale.setUserId(order.getUserId());

        if (!(boxMapper.insertSaleInfo(sale) && boxMapper.removeOrder(orderId))) {
            throw new RollbackException();
        }
        return true;
    }


    @Override
    public List<BoxVo> getUsingBoxes(String taken) throws ServerException, TakenVirifyException {
        int userId = JavaWebTaken.getTakenUserId(taken);
        List<Box> boxes = boxMapper.findUsingBoxes(userId);
        List<BoxVo> boxVos = new ArrayList<>();
        boxes.forEach(box -> {
            BoxVo boxVo = boxConvert(box);
            boxVos.add(boxVo);
        });
        return boxVos;
    }

    @Override
    public List<BoxVo> getReservingBoxes(String taken) throws ServerException, TakenVirifyException {
        int userId = JavaWebTaken.getTakenUserId(taken);
        List<Box> boxes = boxMapper.findReservingBoxed(userId);
        List<BoxVo> boxVos = new ArrayList<>();
        boxes.forEach(box -> {
            BoxVo boxVo = boxConvert(box);
            boxVos.add(boxVo);
        });
        return boxVos;
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
    public List<BoxVo> getAllBoxesByGroupId(int groupId) {
        List<Box> boxes = boxMapper.findBoxesByGroupId(groupId);
        List<BoxVo> boxVos = new ArrayList<>();
        boxes.forEach(box -> {
            BoxVo boxVo = boxConvert(box);
            boxVos.add(boxVo);
        });
        return boxVos;
    }

    public boolean checkBoxStatus(int boxId) throws BoxIsBusyException {
        Box box = boxMapper.findBoxByBoxId(boxId);
        if (box.getBoxStatus() == 1) {
            throw new BoxIsBusyException();
        }
        return true;
    }

    public GroupVo groupPoConvert(GroupPo groupPo){
        GroupVo groupVo = new GroupVo();
        groupVo.setPosition(groupPo.getPosition());
        groupVo.setQuantity(groupPo.getQuantity());
        groupVo.setLng(groupPo.getLng());
        groupVo.setLat(groupPo.getLat());
        groupVo.setGroupId(groupPo.getGroupId());
        groupVo.setEmptySmallBoxNum(boxMapper.findEmptySmallBox(groupPo.getGroupId()).size());
        groupVo.setEmptyLargeBoxNum(boxMapper.findEmptyLargeBox(groupPo.getGroupId()).size());
        return groupVo;
    }

    public BoxVo boxConvert(Box box){
        GroupPo group = boxMapper.findGroupByGroupId(box.getGroupId());
        return new BoxVo(box.getBoxId(),box.getBoxSize(),
                box.getBoxStatus(), group.getPosition(),
                group.getLat(), group.getLng());

    }

}

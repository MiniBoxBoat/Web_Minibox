package com.minibox.service.box.impl;

import com.minibox.dao.BoxMapper;
import com.minibox.dao.ReservationMapper;
import com.minibox.dao.UserMapper;
import com.minibox.dto.UserDto;
import com.minibox.exception.*;
import com.minibox.po.*;
import com.minibox.service.box.BoxService;
import com.minibox.vo.BoxVo;
import com.minibox.vo.GroupVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.Distance;
import util.JavaWebTaken;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Resource
    private ReservationMapper reservationMapper;

    @Override
    public List<GroupVo> getGroupByDestination(String destination) {
        StringBuilder builder = new StringBuilder();
        builder.append("%").append(destination).append("%");
        System.out.println(builder.toString());
        List<GroupPo> groupPos = boxMapper.findGroupsByDestination(builder.toString());
        List<GroupVo> groupVos = new ArrayList<>();
        groupPos.forEach(groupPo -> {
            GroupVo groupVo = groupPoConvert(groupPo);
            groupVos.add(groupVo);
        });
        return groupVos;
    }


    @Override
    public List<GroupVo> getGroupArourd(double lat, double lng) {
        long start = System.currentTimeMillis();
        List<GroupPo> groupPos = boxMapper.findAllGroup();
        System.out.println("找到所有存放点 时间： " + (System.currentTimeMillis()-start));
        List<GroupVo> groupVos = new ArrayList<>();
        System.out.println("开始");
        List<GroupPo> groupPos1 = groupPos.stream().filter(groupPo -> Distance.GetDistance(lat,lng, groupPo.getLat(), groupPo.getLng())<5).collect(Collectors.toList());
        System.out.println("过滤掉了符合条件的存放点  时间：" + (System.currentTimeMillis() - start));
        groupPos1.forEach(groupPo ->{
            GroupVo groupVo = groupPoConvert(groupPo);
            groupVos.add(groupVo);
        });
        System.out.println("转换存放点对象  时间：" + (System.currentTimeMillis() - start));
        return groupVos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Integer> addOrder(Order order,String size,int boxNum, String taken) throws BoxIsBusyException, TakenVirifyException, RollbackException, ParameterException {
        if (!taken.equals(userMapper.findUserByUserId(order.getUserId()).getTaken())) {
            throw new TakenVirifyException();
        }
        if (!size.equals(SMALL.size()) && !size.equals(LARGE.size())){
            throw new ParameterException("size参数错误");
        }
        List<Integer> boxIdList = useBox(order.getGroupId(), size, boxNum);
        UserDto user = userMapper.findUserByUserId(order.getUserId());
        if (!boxMapper.insertOrder(order)|| !userMapper.updateUseTime(user.getUserId())
                || !boxMapper.reduceGroupBoxNum(order.getGroupId(), boxNum)
                || !boxIdList.stream().allMatch(boxId->boxMapper.updateBoxStatus(boxId))){
            throw new RollbackException();
        }
        return boxIdList;
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
            Order order = boxMapper.findOrderByBoxId(box.getBoxId());
            BoxVo boxVo = boxConvert(box,order.getOrderTime());
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
            Reservation reservation = reservationMapper.findReservationByBoxId(box.getBoxId());
            BoxVo boxVo = boxConvert(box, reservation.getOpenTime());
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

/*
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
*/

    @Override
    public GroupVo getGroup(int groupId) {
        GroupPo groupPo = boxMapper.findGroupByGroupId(groupId);
        return  groupPoConvert(groupPo);
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
        groupVo.setEmptySmallBoxNum(boxMapper.findEmptySmallBoxCount(groupPo.getGroupId()));
        groupVo.setEmptyLargeBoxNum(boxMapper.findEmptyLargeBoxCount(groupPo.getGroupId()));
        return groupVo;
    }

    public BoxVo boxConvert(Box box, String openTime){
        GroupPo group = boxMapper.findGroupByGroupId(box.getGroupId());
        return new BoxVo(box.getBoxId(),box.getBoxSize(),
                box.getBoxStatus(), group.getPosition(),
                group.getLat(), group.getLng(), openTime);
    }

    public List<Integer> useBox(int groupId, String size, int boxNum) throws ParameterException {
        List<Integer> boxIdList = new ArrayList<>();
        if (size.equals(SMALL.size())){
            List<Box> smallBoxes = boxMapper.findEmptySmallBox(groupId);
            if (smallBoxes.size()==0){
                throw new ParameterException("箱子已经用完");
            }
            for (int i = 0;i<boxNum;i++){
                boxIdList.add(smallBoxes.get(i).getBoxId());
            }
        }else {
            List<Box> largeBoxes = boxMapper.findEmptyLargeBox(groupId);
            if (largeBoxes.size()==0){
                throw new ParameterException("箱子已经用完");
            }
            for (int i = 0;i<boxNum;i++){
                boxIdList.add(largeBoxes.get(i).getBoxId());
            }
        }
        return boxIdList;
    }

}

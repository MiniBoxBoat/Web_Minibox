package com.minibox.service.box.impl;

import com.minibox.dao.BoxMapper;
import com.minibox.dao.UserMapper;
import com.minibox.dto.UserDto;
import com.minibox.exception.BoxIsBusyException;
import com.minibox.exception.RollbackException;
import com.minibox.exception.TakenVirifyException;
import com.minibox.po.*;
import com.minibox.service.box.BoxService;
import com.minibox.vo.GroupVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import util.Distance;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author MEI
 */
@Service
public class BoxServiceImpl implements BoxService{

    @Resource
    private BoxMapper boxMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public List<GroupVo> getGroupByDestination(String destination, double lng, double lat) {
        List<GroupPo> groupPos = boxMapper.findGroupsByDestination(destination);
        int minDistanceGroupId = groupPos.get(0).getGroupId();
        double minDistance = Distance.GetDistance(lat,lng,groupPos.get(0).getLat(), groupPos.get(0).getLng());
        for(GroupPo groupPo : groupPos){
             if (minDistanceGroupId == groupPo.getGroupId()){
                 continue;
             }else{
                 double distance = Distance.GetDistance(lat,lng,groupPo.getLat(),groupPo.getLng());
                 if (minDistance > distance){
                     minDistanceGroupId = groupPo.getGroupId();
                 }
             }
        }
        System.out.println(groupPos.size());

             List<GroupVo> groupVos = new ArrayList<>();
             for (int i =0; i<groupPos.size();i++ ){
                 GroupVo groupVo = new GroupVo();
                 groupVo.setEmpty(groupPos.get(i).getEmpty());
                 groupVo.setPosition(groupPos.get(i).getPosition());
                 groupVo.setQuantity(groupPos.get(i).getQuantity());
                 if (minDistanceGroupId == groupPos.get(i).getGroupId()){
                     groupVo.setIsNear(1);
                 }else{
                     groupVo.setIsNear(0);
                 }
                 groupVos.add(groupVo);
             }
        return groupVos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addOrder(String userName, int groupId, int boxId, String taken) throws BoxIsBusyException, TakenVirifyException, RollbackException {
        if(!taken.equals(userMapper.findUserByUserName(userName).getTaken())){
            throw new TakenVirifyException();
        }

        checkBoxStatus(boxId);
        UserDto user = userMapper.findUserByUserName(userName);
        if (!(boxMapper.insertOrder(userName, groupId, boxId)
                && boxMapper.updateBoxStatus(boxId)
                && userMapper.updateUseTime(user.getUserId()))){
            throw new RollbackException();
        }
        return true;
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
        String now = new SimpleDateFormat("yyyy-MM-dd HH:ss").format(new Date());
        sale.setUserName(order.getUserName());

        if (!(boxMapper.insertSaleInfo(sale) && boxMapper.removeOrder(orderId))){
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
        if (!(boxMapper.removeOrder(orderId) && boxMapper.updateBoxStatus(boxId) && boxMapper.addGroupBoxNum(groupId, 1))){
            throw new RollbackException();
        }
        return true;
    }

    @Override
    public List<Box> getAllBoxesByGroupId(int groupId) {
        return boxMapper.findBoxesByGroupId(groupId);
    }

    public  boolean checkBoxStatus(int boxId) throws BoxIsBusyException {
        Box box = boxMapper.findBoxByBoxId(boxId);
        if (box.getBoxStatus()==1){
            throw new BoxIsBusyException();
        }
        return true;
    }
}

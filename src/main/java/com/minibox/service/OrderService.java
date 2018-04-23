package com.minibox.service;

import com.minibox.dao.BoxMapper;
import com.minibox.dao.GroupMapper;
import com.minibox.dao.OrderMapper;
import com.minibox.dao.UserMapper;
import com.minibox.dto.OrderDto;
import com.minibox.exception.ParameterException;
import com.minibox.exception.RollbackException;
import com.minibox.po.BoxPo;
import com.minibox.po.OrderPo;
import com.minibox.po.UserPo;
import com.minibox.service.util.JavaWebToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.minibox.constants.BoxSize.SMALL;
import static com.minibox.constants.ExceptionMessage.NO_BOX;
import static com.minibox.constants.ExceptionMessage.PARAMETER_IS_NOT_FULL;
import static com.minibox.service.util.ServiceExceptionChecking.checkBoxSizeIsTrue;

@Service
public class OrderService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private BoxMapper boxMapper;

    @Resource
    private GroupMapper groupMapper;

    @Resource
    private OrderMapper orderMapper;


    @Transactional(rollbackFor = Exception.class)
    public void addOrder(OrderDto orderDto, String taken) {
        int userId = JavaWebToken.getUserIdAndVerifyTakenFromTaken(taken);
        checkAddOrderParameters(orderDto);
        List<Integer> canUseBoxesId = getCanUseBoxesId(orderDto);
        UserPo user = userMapper.findUserByUserId(userId);
        OrderPo orderPo = orderDtoToOrderPo(orderDto, userId);
        if (!orderMapper.insertOrder(orderPo) || !userMapper.updateUseTime(user.getUserId())
                || !groupMapper.reduceGroupBoxNum(orderDto.getGroupId(), orderDto.getBoxNum())
                || !canUseBoxesId.stream().allMatch(boxId -> boxMapper.updateBoxStatus(boxId))) {
            throw new RollbackException();
        }
    }

    private List<Integer> getCanUseBoxesId(OrderDto orderDto) {
        List<Integer> boxIdList = new ArrayList<>();
        if (orderDto.getSize().equals(SMALL.size())) {
            List<BoxPo> smallBoxPos = boxMapper.findEmptySmallBoxByGroupId(orderDto.getGroupId());
            if (smallBoxPos.size() == 0) {
                throw new ParameterException(NO_BOX);
            }
            for (int i = 0; i < orderDto.getBoxNum(); i++) {
                boxIdList.add(smallBoxPos.get(i).getBoxId());
            }
        } else {
            List<BoxPo> largeBoxPos = boxMapper.findEmptyLargeBoxByGroupId(orderDto.getGroupId());
            if (largeBoxPos.size() == 0) {
                throw new ParameterException(NO_BOX);
            }
            for (int i = 0; i < orderDto.getBoxNum(); i++) {
                boxIdList.add(largeBoxPos.get(i).getBoxId());
            }
        }
        return boxIdList;
    }

    private void checkAddOrderParameters(OrderDto orderDto) {
        if (orderDto.getGroupId() == null || orderDto.getSize() == null
                || orderDto.getBoxNum() == null) {
            throw new ParameterException(PARAMETER_IS_NOT_FULL);
        }
        checkBoxSizeIsTrue(orderDto.getSize());
    }

    private OrderPo orderDtoToOrderPo(OrderDto orderDto, int userId) {
        return OrderPo.builder()
                .groupId(orderDto.getGroupId())
                .userId(userId)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteOrder(int orderId) {
        OrderPo orderPo = orderMapper.findOrderByOrderId(orderId);
        int boxId = orderPo.getBoxId();
        int groupId = orderPo.getGroupId();
        if (!(orderMapper.removeOrder(orderId) || !boxMapper.updateBoxStatus(boxId) || !groupMapper.increaseGroupBoxNum(groupId, 1))) {
            throw new RollbackException();
        }
    }

}

package com.minibox.service;

import com.minibox.dao.BoxMapper;
import com.minibox.dao.GroupMapper;
import com.minibox.dao.OrderMapper;
import com.minibox.po.BoxPo;
import com.minibox.po.GroupPo;
import com.minibox.po.OrderPo;
import com.minibox.vo.BoxVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.minibox.util.JavaWebToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author MEI
 */
@Service
public class BoxService {

    @Autowired
    private BoxMapper boxMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private OrderMapper orderMapper;

    public List<BoxVo> getUsingBoxes(String taken){
        int userId = JavaWebToken.getUserIdAndVerifyTakenFromTaken(taken);
        List<BoxPo> boxPos = boxMapper.findUsingBoxesByUserId(userId);
        return boxPosConvertBoxVos(boxPos);
    }

    public List<BoxVo> getReservingBoxes(String taken) {
        int userId = JavaWebToken.getUserIdAndVerifyTakenFromTaken(taken);
        List<BoxPo> boxPos = boxMapper.findReservingBoxedByUserId(userId);
        return boxPosConvertBoxVos(boxPos);
    }

    private List<BoxVo> boxPosConvertBoxVos(List<BoxPo> boxPos){
        List<BoxVo> boxVos = new ArrayList<>();
        boxPos.forEach(boxPo -> {
            OrderPo orderPo = orderMapper.findOrderByBoxId(boxPo.getBoxId());
            Objects.requireNonNull(orderPo, "通过boxId找不到order");
            BoxVo boxVo = boxConvert(boxPo, orderPo.getOrderTime());
            boxVos.add(boxVo);
        });
        return boxVos;
    }

    private BoxVo boxConvert(BoxPo boxPo, String openTime){
        GroupPo group = groupMapper.findGroupByGroupId(boxPo.getGroupId());
        return new BoxVo(boxPo.getBoxId(), boxPo.getBoxSize(),
                boxPo.getBoxStatus(), group.getPosition(),
                group.getLat(), group.getLng(), openTime);
    }

}

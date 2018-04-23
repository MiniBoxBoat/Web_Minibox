package com.minibox.service;

import com.minibox.dao.BoxMapper;
import com.minibox.dao.GroupMapper;
import com.minibox.dao.OrderMapper;
import com.minibox.dao.ReservationMapper;
import com.minibox.po.BoxPo;
import com.minibox.po.GroupPo;
import com.minibox.po.OrderPo;
import com.minibox.po.ReservationPo;
import com.minibox.service.util.JavaWebToken;
import com.minibox.vo.BoxVo;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.minibox.constants.ExceptionMessage.RESOURCE_NOT_FOUND;

/**
 * @author MEI
 */
@Service
public class BoxService {

    @Resource
    private BoxMapper boxMapper;

    @Resource
    private GroupMapper groupMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private ReservationMapper reservationMapper;

    public static final String RESERVATION = "reservation";

    public static final String ORDER = "order";

    public List<BoxVo> getUsingBoxes(String taken){
        int userId = JavaWebToken.getUserIdAndVerifyTakenFromTaken(taken);
        List<BoxPo> boxPos = boxMapper.findUsingBoxesByUserId(userId);
        Objects.requireNonNull(boxPos);
        return boxPosConvertBoxVos(boxPos, ORDER);
    }

    public List<BoxVo> getReservingBoxes(String taken) {
        int userId = JavaWebToken.getUserIdAndVerifyTakenFromTaken(taken);
        List<BoxPo> boxPos = boxMapper.findReservingBoxedByUserId(userId);
        Objects.requireNonNull(boxPos);
        return boxPosConvertBoxVos(boxPos, RESERVATION);
    }

    private List<BoxVo> boxPosConvertBoxVos(List<BoxPo> boxPos, String flag){
        List<BoxVo> boxVos = new ArrayList<>();
        boxPos.forEach(boxPo -> {
            if (flag.equals(ORDER)){
                OrderPo orderPo = orderMapper.findOrderByBoxId(boxPo.getBoxId());
                Objects.requireNonNull(orderPo, RESOURCE_NOT_FOUND);
                BoxVo boxVo = boxConvert(boxPo, orderPo.getOrderTime());
                boxVos.add(boxVo);
            }else if (flag.equals(RESERVATION)){
                ReservationPo reservation = reservationMapper.findReservationByBoxId(boxPo.getBoxId());
                Objects.requireNonNull(reservation);
                BoxVo box = boxConvert(boxPo, reservation.getOpenTime());
                boxVos.add(box);
            }
        });
        Objects.requireNonNull(boxVos);
        return boxVos;
    }

    private BoxVo boxConvert(BoxPo boxPo, String openTime){
        GroupPo group = groupMapper.findGroupByGroupId(boxPo.getGroupId());
        return new BoxVo(boxPo.getBoxId(), boxPo.getBoxSize(),
                boxPo.getBoxStatus(), group.getPosition(),
                group.getLat(), group.getLng(), openTime);
    }
}

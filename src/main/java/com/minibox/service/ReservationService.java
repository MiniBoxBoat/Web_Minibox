package com.minibox.service;

import com.minibox.dao.*;
import com.minibox.dto.ReservationDto;
import com.minibox.exception.ParameterException;
import com.minibox.exception.RollbackException;
import com.minibox.exception.ServerException;
import com.minibox.po.BoxPo;
import com.minibox.po.GroupPo;
import com.minibox.po.OrderPo;
import com.minibox.po.ReservationPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.minibox.util.FormatUtil;
import com.minibox.util.JavaWebToken;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.minibox.constants.BoxSize.SMALL;

/**
 * `
 *
 * @author MEI
 */
@Service
public class ReservationService {

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private BoxMapper boxMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Transactional(rollbackFor = Exception.class)
    public void addReservationsAndUpdateBoxesStatusAndReduceGroupBoxNum(ReservationDto reservationDto
            , String taken) {
        int userId = JavaWebToken.getUserIdAndVerifyTakenFromTaken(taken);
        checkAddReservationParameter(reservationDto);
        List<Integer> canUseBoxesId = getCanUseBoxesId(reservationDto);

        boolean successInsert = canUseBoxesId.stream().allMatch(boxId -> {
            ReservationPo reservation = reservationDtoToReservationPo(reservationDto, userId);
            reservation.setBoxId(boxId);
            return reservationMapper.insertReservation(reservation);
        });
        boolean successReduceGroupBoxNum = groupMapper.reduceGroupBoxNum(reservationDto.getGroupId(),
                reservationDto.getBoxNum());
        boolean successUpdateAllUseBoxesStatus = canUseBoxesId.stream()
                .allMatch(boxId -> boxMapper.updateBoxStatus(boxId));
        if (!successInsert || !successReduceGroupBoxNum || !successUpdateAllUseBoxesStatus) {
            throw new RollbackException();
        }
    }

    private List<Integer> getCanUseBoxesId(ReservationDto reservationDto) {
        List<Integer> boxIdList = new ArrayList<>();
        if (reservationDto.getBoxSize().equals(SMALL.size())) {
            List<BoxPo> smallBoxPos = boxMapper.findEmptySmallBoxByGroupId(reservationDto.getGroupId());
            if (smallBoxPos.size() == 0) {
                throw new ParameterException("箱子已经用完", 400);
            }
            for (int i = 0; i < reservationDto.getBoxNum(); i++) {
                boxIdList.add(smallBoxPos.get(i).getBoxId());
            }
        } else {
            List<BoxPo> largeBoxPos = boxMapper.findEmptyLargeBoxByGroupId(reservationDto.getGroupId());
            if (largeBoxPos.size() == 0) {
                throw new ParameterException("箱子已经用完", 400);
            }
            for (int i = 0; i < reservationDto.getBoxNum(); i++) {
                boxIdList.add(largeBoxPos.get(i).getBoxId());
            }
        }
        return boxIdList;
    }

    private ReservationPo reservationDtoToReservationPo(ReservationDto reservationDto, int userId) {
        return ReservationPo.builder()
                .boxSize(reservationDto.getBoxSize())
                .groupId(reservationDto.getGroupId())
                .openTime(reservationDto.getOpenTime())
                .phoneNumber(reservationDto.getPhoneNumber())
                .userId(userId)
                .userName(reservationDto.getUserName())
                .useTime(reservationDto.getUseTime())
                .build();
    }

    private void checkAddReservationParameter(ReservationDto reservationDto) {
        if (reservationDto.getUserName() == null || reservationDto.getOpenTime() == null ||
                reservationDto.getUseTime() == null || reservationDto.getBoxSize() == null
                || reservationDto.getPhoneNumber() == null || reservationDto.getGroupId() == 0) {
            throw new ParameterException("请检查信息是否填写完整", 400);
        }
        if (!FormatUtil.isPhoneNumberLegal(reservationDto.getPhoneNumber())) {
            throw new ParameterException("手机号格式不正确", 400);
        }
        if (!FormatUtil.isTimePattern(reservationDto.getOpenTime())) {
            throw new ParameterException("时间的格式有误， 按照 2018-01-01 12:00:00 这样来", 400);
        }
        if (isTimeIsAfterNow(reservationDto.getOpenTime())) {
            throw new ParameterException("不能填写以前的时间", 400);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteReservationByReservationId(int reservationId) {
        ReservationPo reservation = reservationMapper.findReservationByReservationId(reservationId);
        Objects.requireNonNull(reservation, "输入的reservationId有误");
        boolean successRemoveReservation = reservationMapper.removeReservationByReservationId(reservationId);
        boolean successUpdateBoxStatus = boxMapper.updateBoxStatus(reservation.getBoxId());
        if (!successRemoveReservation || !successUpdateBoxStatus) {
            throw new RollbackException();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteReservationAndAddOrderAndUpdateBoxStatusAndUpdateUseTime(int reservationId, String taken) {
        int userId = JavaWebToken.getUserIdAndVerifyTakenFromTaken(taken);
        ReservationPo reservation = reservationMapper.findReservationByReservationId(reservationId);
        Objects.requireNonNull(reservation, "reservationId有误");
        GroupPo group = groupMapper.findGroupByGroupId(reservation.getGroupId());

        OrderPo orderPo = new OrderPo();
        orderPo.setGroupId(group.getGroupId());
        orderPo.setBoxId(reservation.getBoxId());
        orderPo.setUserId(userId);

        if (!(reservationMapper.removeReservationByReservationId(reservationId)
                && orderMapper.insertOrder(orderPo)
                && boxMapper.updateBoxStatus(reservation.getBoxId())
                && userMapper.updateUseTime(userId))) {
            throw new RollbackException();
        }
    }

    public void updateReservation(ReservationPo reservation) {
        checkUpdateReservationParameter(reservation);
        if (!reservationMapper.updateReservation(reservation)){
            throw new ServerException();
        }
    }

    private boolean isTimeIsAfterNow(String time) {
        LocalDateTime dateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime dateNow = LocalDateTime.now();
        return !dateTime.isAfter(dateNow);
    }

    private void checkUpdateReservationParameter(ReservationPo reservation) {
        if (reservation.getUserName() == null || reservation.getOpenTime() == null || reservation.getUseTime() == 0
                || reservation.getPhoneNumber() == null) {
            throw new ParameterException("请检查信息是否填写完整", 400);
        }
        if (isTimeIsAfterNow(reservation.getOpenTime())){
            throw new ParameterException("开箱时间不能是以前的时间", 400);
        }
        if (!FormatUtil.isPhoneNumberLegal(reservation.getPhoneNumber())) {
            throw new ParameterException("手机号格式不正确", 400);
        }
    }

    public List<ReservationPo> getReservation(String taken) {
        int userId = JavaWebToken.getUserIdAndVerifyTakenFromTaken(taken);
        return reservationMapper.findReservationsByUserId(userId);
    }
}

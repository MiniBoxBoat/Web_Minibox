package com.minibox.service;

import com.minibox.dao.*;
import com.minibox.dto.ReservationDto;
import com.minibox.exception.ParameterException;
import com.minibox.exception.RollbackException;
import com.minibox.po.BoxPo;
import com.minibox.po.GroupPo;
import com.minibox.po.OrderPo;
import com.minibox.po.ReservationPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.minibox.service.util.JavaWebToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.minibox.constants.BoxSize.*;
import static com.minibox.constants.ExceptionMessage.*;
import static com.minibox.service.util.ServiceExceptionChecking.*;

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
        checkSqlExcute(successInsert && successReduceGroupBoxNum && successUpdateAllUseBoxesStatus);
    }

    private List<Integer> getCanUseBoxesId(ReservationDto reservationDto) {
        List<Integer> boxIdList = new ArrayList<>();
        if (reservationDto.getBoxSize().equals(SMALL.size())) {
            List<BoxPo> smallBoxPos = boxMapper.findEmptySmallBoxByGroupId(reservationDto.getGroupId());
            if (smallBoxPos.size() == 0) {
                throw new ParameterException(NO_BOX);
            }
            for (int i = 0; i < reservationDto.getBoxNum(); i++) {
                boxIdList.add(smallBoxPos.get(i).getBoxId());
            }
        } else {
            List<BoxPo> largeBoxPos = boxMapper.findEmptyLargeBoxByGroupId(reservationDto.getGroupId());
            if (largeBoxPos.size() == 0) {
                throw new ParameterException(NO_BOX);
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
            throw new ParameterException(PARAMETER_IS_NOT_FULL);
        }
        checkPhoneNumberIsTrue(reservationDto.getPhoneNumber());
        checkTimeIsInPattern(reservationDto.getOpenTime());
        checkTimeIsAfterNow(reservationDto.getOpenTime());
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteReservationByReservationId(int reservationId) {
        ReservationPo reservation = reservationMapper.findReservationByReservationId(reservationId);
        Objects.requireNonNull(reservation, RESOURCE_NOT_FOUND);
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
        Objects.requireNonNull(reservation, RESOURCE_NOT_FOUND);
        GroupPo group = groupMapper.findGroupByGroupId(reservation.getGroupId());

        OrderPo orderPo = new OrderPo();
        orderPo.setGroupId(group.getGroupId());
        orderPo.setBoxId(reservation.getBoxId());
        orderPo.setUserId(userId);
        checkSqlExcute((reservationMapper.removeReservationByReservationId(reservationId)
                && orderMapper.insertOrder(orderPo)
                && boxMapper.updateBoxStatus(reservation.getBoxId())
                && userMapper.updateUseTime(userId)));
    }

    public void updateReservation(ReservationPo reservation) {
        checkUpdateReservationParameter(reservation);
        checkSqlExcute(reservationMapper.updateReservation(reservation));
    }

    private void checkUpdateReservationParameter(ReservationPo reservation) {
        if (reservation.getUserName() == null || reservation.getOpenTime() == null || reservation.getUseTime() == 0
                || reservation.getPhoneNumber() == null) {
            throw new ParameterException(PARAMETER_IS_NOT_FULL);
        }
        checkTimeIsAfterNow(reservation.getOpenTime());
        checkTimeIsInPattern(reservation.getPhoneNumber());
    }

    public List<ReservationPo> getReservation(String taken) {
        int userId = JavaWebToken.getUserIdAndVerifyTakenFromTaken(taken);
        return reservationMapper.findReservationsByUserId(userId);
    }
}

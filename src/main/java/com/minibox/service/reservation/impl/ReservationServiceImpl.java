package com.minibox.service.reservation.impl;

import com.minibox.dao.BoxMapper;
import com.minibox.dao.ReservationMapper;
import com.minibox.dao.UserMapper;
import com.minibox.dto.UserDto;
import com.minibox.exception.*;
import com.minibox.po.Box;
import com.minibox.po.GroupPo;
import com.minibox.po.Order;
import com.minibox.po.Reservation;
import com.minibox.service.box.impl.BoxServiceImpl;
import com.minibox.service.reservation.ReservationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.FormatUtil;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * `
 *
 * @author MEI
 */
@Service
public class ReservationServiceImpl implements ReservationService {

    @Resource
    private ReservationMapper reservationMapper;

    @Resource
    private BoxMapper boxMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private BoxServiceImpl boxService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addReservation(Reservation reservation, int boxNum,String taken) throws ParameterException, BoxIsBusyException, ParameterIsNullException, RollbackException, TakenVirifyException {
        if (!taken.equals(userMapper.findUserByUserId(reservation.getUserId()).getTaken())) {
            throw new TakenVirifyException();
        }
        if (reservation.getUserName() == null || reservation.getOpenTime() == null || new Integer(reservation.getUseTime()) == null ||
                reservation.getBoxSize() == null || reservation.getPhoneNumber() == null || reservation.getGroupId() == 0) {
            throw new ParameterIsNullException("请检查信息是否填写完整");
        }
        if (!FormatUtil.isPhoneNumberLegal(reservation.getPhoneNumber())) {
            throw new ParameterException("手机号格式不正确");
        }
        String timeStr = reservation.getOpenTime();
        LocalDateTime time = LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime now = LocalDateTime.now();
        if (time.isBefore(now)) {
            throw new ParameterException("请填写正确的时间");
        }
        List<Box> boxes = boxMapper.findEmptyBoxes(reservation.getGroupId(), reservation.getBoxSize());
        List<Integer> boxIds = new ArrayList<>();
        for (int i = 0; i < boxNum; i++) {
            boxIds.add(boxes.get(i).getBoxId());
        }


        if (!boxIds.stream().allMatch(boxId -> {
            reservation.setBoxId(boxId);
            return reservationMapper.insertReservation(reservation);
        })
                || !boxMapper.reduceGroupBoxNum(reservation.getGroupId(), boxNum)
                || !boxIds.stream().allMatch(boxId -> boxMapper.updateBoxStatus(boxId))) {
            throw new RollbackException();
        }
        return true;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteReservation(int reservationId) {
        Reservation reservation = reservationMapper.findReservationByReservationId(reservationId);
        return reservationMapper.removeReservation(reservationId)
                && boxMapper.updateBoxStatus(reservation.getBoxId());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteReservationAndAddOrder(int reservationId, String taken) throws BoxIsBusyException, TakenVirifyException, RollbackException {

        Reservation reservation = reservationMapper.findReservationByReservationId(reservationId);

        UserDto user = userMapper.findUserByUserId(reservation.getUserId());
        GroupPo group = boxMapper.findGroupByGroupId(reservation.getGroupId());

        Order order = new Order();
        order.setGroupId(group.getGroupId());
        order.setBoxId(reservation.getBoxId());
        order.setUserId(reservation.getUserId());

        if (!(reservationMapper.removeReservation(reservationId)
                && boxMapper.insertOrder(order)
                && boxMapper.updateBoxStatus(reservation.getBoxId())
                && userMapper.updateUseTime(reservation.getUserId()))) {
            throw new RollbackException();
        }

        return true;
    }

    @Override
    public boolean updateReservation(Reservation reservation) throws ParameterException, ParameterIsNullException {

        if (reservation.getUserName() == null || reservation.getOpenTime() == null || reservation.getUseTime() == 0 ||
                reservation.getBoxSize() == null || reservation.getPhoneNumber() == null || reservation.getGroupId() == 0) {
            throw new ParameterIsNullException("请检查信息是否填写完整");
        }

        LocalDateTime time = LocalDateTime.parse(reservation.getOpenTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime now = LocalDateTime.now();
        if (time.isBefore(now)) {
            throw new ParameterException("请填写正确的时间");
        }
        if (!FormatUtil.isPhoneNumberLegal(reservation.getPhoneNumber())) {
            throw new ParameterException("手机号格式不正确");
        }
        return reservationMapper.updateReservation(reservation);
    }

    @Override
    public List<Reservation> getReservation(int userId) {
        return reservationMapper.findReservationsByUserId(userId);
    }
}

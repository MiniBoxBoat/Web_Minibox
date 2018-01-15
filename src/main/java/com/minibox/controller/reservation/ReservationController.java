package com.minibox.controller.reservation;

import com.minibox.exception.BoxIsBusyException;
import com.minibox.exception.ParameterException;
import com.minibox.exception.ParameterIsNullException;
import com.minibox.exception.TakenVirifyException;
import com.minibox.po.Reservation;
import com.minibox.service.reservation.ReservationService;
import com.minibox.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import util.JsonUtil;
import util.MapUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author MEI
 */
@Controller
@RequestMapping("reservation")
public class ReservationController {

    @Resource
    ReservationService reservationService;

    @Resource
    UserService userService;

    @RequestMapping(value = "reserve.do", method = RequestMethod.POST)
    public void reserve(Reservation reservation, int boxNum,String taken) {
        Map map;
        try {
            if (!reservationService.addReservation(reservation, boxNum, taken)) {
                throw new Exception();
            }
            map = MapUtil.toMap(200, "预约成功", null);
            JsonUtil.toJSON(map);
        } catch (ParameterException e) {
            map = MapUtil.toMap(500, e.getMessage(), null);
            JsonUtil.toJSON(map);
        } catch (BoxIsBusyException | TakenVirifyException e) {
            map = MapUtil.toMap(400, e.getMessage(), null);
            JsonUtil.toJSON(map);
        } catch (ParameterIsNullException e) {
            map = MapUtil.toMap(403, e.getMessage(), null);
            JsonUtil.toJSON(map);
        } catch (Exception e) {
            e.printStackTrace();
            map = MapUtil.toMap(500, "服务器错误", null);
            JsonUtil.toJSON(map);
        }
    }

    @RequestMapping(value = "/endReserve.do", method = RequestMethod.POST)
    public void endReserve(int reservationId, String taken) {
        Map map;
        try {
            userService.checkTaken(taken);
            if (!reservationService.deleteReservationAndAddOrder(reservationId,taken)) {
                throw new Exception();
            }
            map = MapUtil.toMap(200, "删除预约成功", null);
            JsonUtil.toJSON(map);
        } catch (TakenVirifyException e) {
            e.printStackTrace();
            map = MapUtil.toMap(401, e.getMessage(), null);
            JsonUtil.toJSON(map);
        } catch (BoxIsBusyException e) {
            map = MapUtil.toMap(400, e.getMessage(), null);
            JsonUtil.toJSON(map);
        } catch (Exception e) {
            map = MapUtil.toMap(500, "服务器错误", null);
            JsonUtil.toJSON(map);
        }
    }

    @RequestMapping("/deleteReservation.do")
    public void deleteReservation(int reservationId) {
        Map map;
        try {
            if (!reservationService.deleteReservation(reservationId)) {
                throw new Exception();
            }
            map = MapUtil.toMap(200, "删除成功", null);
            JsonUtil.toJSON(map);
        } catch (Exception e) {
            map = MapUtil.toMap(500, "服务器错误", null);
            JsonUtil.toJSON(map);
        }
    }

    @RequestMapping(value = "UpdateReservation.do", method = RequestMethod.POST)
    public void updateReservation(Reservation reservation) {
        Map map;
        try {
            if (!reservationService.updateReservation(reservation)) {
                throw new Exception();
            }
            map = MapUtil.toMap(200, "修改预约成功", null);
            JsonUtil.toJSON(map);
        }catch (TakenVirifyException e){
            map = MapUtil.toMap(401, e.getMessage(), null);
            JsonUtil.toJSON(map);
        } catch (ParameterException e) {
            map = MapUtil.toMap(500, e.getMessage(), null);
            JsonUtil.toJSON(map);
        } catch (ParameterIsNullException e) {
            map = MapUtil.toMap(403, e.getMessage(), null);
            JsonUtil.toJSON(map);
        } catch (Exception e) {
            e.printStackTrace();
            map = MapUtil.toMap(500, "服务器错误", null);
            JsonUtil.toJSON(map);
        }
    }

    @RequestMapping("getReservations.do")
    public void getReservations(int userId) {
        Map map;
        try {
            List<Reservation> reservations = reservationService.getReservation(userId);
            if (reservations == null) {
                throw new Exception();
            }

            map = MapUtil.toMap(200, "获取数据成功", reservations);
            JsonUtil.toJSON(map);
        } catch (Exception e) {
            e.printStackTrace();
            map = MapUtil.toMap(500, "服务器错误", null);
            JsonUtil.toJSON(map);
        }
    }
}

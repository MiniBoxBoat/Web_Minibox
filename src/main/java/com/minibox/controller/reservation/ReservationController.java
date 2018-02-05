package com.minibox.controller.reservation;

import com.minibox.dto.ReservationDto;
import com.minibox.dto.ResponseEntity;
import com.minibox.po.ReservationPo;
import com.minibox.service.ReservationService;
import com.minibox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.minibox.constants.RequestResult.SUCCESS;

/**
 * @author MEI
 */
@RestController()
@RequestMapping("reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("reserve.do")
    public ResponseEntity<Object> reserve(ReservationDto reservationDto, String taken) {
        reservationService.addReservationsAndUpdateBoxesStatusAndReduceGroupBoxNum(reservationDto, taken);
        return new ResponseEntity<>(200, SUCCESS, null);
    }

    @PostMapping("endReserve.do")
    public ResponseEntity<Object> endReserve(int reservationId, String taken) {
        reservationService.deleteReservationAndAddOrderAndUpdateBoxStatusAndUpdateUseTime(reservationId, taken);
        return new ResponseEntity<>(200, SUCCESS, null);
    }

    @GetMapping("/deleteReservation.do")
    public ResponseEntity<Object> deleteReservation(int reservationId) {
        reservationService.deleteReservationByReservationId(reservationId);
        return new ResponseEntity<>(200, SUCCESS, null);
    }

    @PostMapping("UpdateReservation.do")
    public ResponseEntity<Object> updateReservation(ReservationPo reservation) {
        reservationService.updateReservation(reservation);
        return new ResponseEntity<>(200, SUCCESS, null);
    }

    @PostMapping("getReservations.do")
    public ResponseEntity<List<ReservationPo>> getReservations(String taken) {
        List<ReservationPo> reservations = reservationService.getReservation(taken);
        return new ResponseEntity<>(200, SUCCESS, reservations);
    }
}

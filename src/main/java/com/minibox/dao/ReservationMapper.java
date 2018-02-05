package com.minibox.dao;

import com.minibox.po.ReservationPo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author MEI
 */
@Repository
public interface ReservationMapper {

    boolean insertReservation(ReservationPo reservation);

    boolean removeReservationByReservationId(int reservationId);

    boolean updateReservation(ReservationPo reservation);

    List<ReservationPo> findReservationsByUserId(int userId);

    ReservationPo findReservationByReservationId(int reservationId);

    boolean updateOverdueReservationExpFlag();

    ReservationPo findReservationByBoxId(int boxId);
}

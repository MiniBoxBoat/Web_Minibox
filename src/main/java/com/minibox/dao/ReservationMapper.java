package com.minibox.dao;

import com.minibox.po.Reservation;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author MEI
 */
@Repository
public interface ReservationMapper {

    boolean insertReservation(Reservation reservation);

    boolean removeReservation(int reservationId);

    boolean updateReservation(Reservation reservation);

    List<Reservation> findAllReservations();

    List<Reservation> findReservations(String userName);

    List<Reservation> findReservationsByUserId(int userId);

    Reservation findReservationByReservationId(int reservationId);

    boolean updateReservationExpFlag();
}

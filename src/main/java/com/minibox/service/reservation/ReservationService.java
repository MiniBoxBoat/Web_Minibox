package com.minibox.service.reservation;

import com.minibox.exception.*;
import com.minibox.po.Reservation;

import java.util.List;

public interface ReservationService {

    boolean addReservation(Reservation reservation) throws ParameterException, BoxIsBusyException, ParameterIsNullException, RollbackException;

    boolean deleteReservation(int reservationId);

    boolean updateReservation(Reservation reservation) throws ParameterException, ParameterIsNullException;

    List<Reservation> getReservation(int userId);

    public boolean deleteReservationAndAddOrder(int reservationId, String taken) throws BoxIsBusyException, TakenVirifyException, RollbackException;

}

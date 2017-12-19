package dao;


import com.minibox.dao.ReservationMapper;
import com.minibox.po.Reservation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static util.Print.*;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springConfig.xml")
public class ReservationTest {

    @Resource
    ReservationMapper reservationMapper;


    @Test
    public void addReservationTest(){
        Reservation reservation = new Reservation();
        reservation.setBoxId(102);
        reservation.setOpenTime("2017-10-5 10:05");
        reservation.setPosition("重庆市南岸区南坪万达");
        reservation.setBoxSize("小");
        reservation.setPhoneNumber("15808060138");
        reservation.setUserName("mei");
        reservation.setUseTime(3);
        boolean flag = reservationMapper.insertReservation(reservation);
        Assert.assertEquals(true, flag);

    }

    @Test
    public void removeReservationTest(){
        boolean flag = reservationMapper.removeReservation(15);
        Assert.assertEquals(flag, true);
    }

    @Test
    public void findReservationByReservationId(){
        Reservation reservation = reservationMapper.findReservationByReservationId(32);
        printnb(reservation.getOpenTime());
    }

    @Test
    public void findReservationByUserId(){
        List<Reservation> reservations = reservationMapper.findReservationsByUserId(111);
        Assert.assertEquals(1, reservations.size());
        printnb(reservations.get(0).getOpenTime());
    }
}

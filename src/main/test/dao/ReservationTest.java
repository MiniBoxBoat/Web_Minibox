package dao;


import com.minibox.conf.MvcConfig;
import com.minibox.dao.ReservationMapper;
import com.minibox.po.ReservationPo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MvcConfig.class)
@WebAppConfiguration
@ActiveProfiles("dev")
public class ReservationTest {

    @Autowired
    private ReservationMapper reservationMapper;
    private ReservationPo reservation;

    @Before
    public void before() {
        reservation = new ReservationPo();
        reservation.setBoxId(102);
        reservation.setOpenTime("2018-10-5 10:05:00");
        reservation.setBoxSize("小");
        reservation.setPhoneNumber("15808060138");
        reservation.setUserName("mei");
        reservation.setUseTime(3);
    }

    @Test
    public void addReservationTest() {
        Assert.assertEquals(true, reservationMapper.insertReservation(reservation));
    }

    @Test
    public void removeReservationTest() {
        boolean flag = reservationMapper.removeReservationByReservationId(124);
        assertEquals(true, flag);
    }

    @Test
    public void updateReservation() {
        reservation.setReservationId(124);
        boolean flag = reservationMapper.updateReservation(reservation);
        assertEquals(true, flag);
    }

    @Test
    public void findReservationByReservationId() {
        ReservationPo reservation = reservationMapper.findReservationByReservationId(124);
        assertEquals(133, reservation.getUserId());
    }

    @Test
    public void findReservationByUserId() {
        List<ReservationPo> reservations = reservationMapper.findReservationsByUserId(135);
        Assert.assertEquals(1, reservations.size());
    }

    @Test
    public void findReservationByBoxIdTest() {
        ReservationPo reservation = reservationMapper.findReservationByBoxId(932);
        assertEquals(9, reservation.getGroupId());
    }


}

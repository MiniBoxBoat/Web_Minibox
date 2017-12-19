package service;

import com.minibox.exception.BoxIsBusyException;
import com.minibox.exception.RollbackException;
import com.minibox.exception.TakenVirifyException;
import com.minibox.service.reservation.ReservationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springConfig.xml")
public class ReservationTest {

    @Resource
    private ReservationService rs;

    @Test
    public void deleteReservationAndAddOrderTest() throws TakenVirifyException, BoxIsBusyException, RollbackException {
        rs.deleteReservationAndAddOrder(32,"eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MTI5OTkwNjEwOTIsInVzZXJJZCI6MTExfQ.jVlIxZlRL-YUPWT_FJ7cC8uAjpyYo3YmPNy3HcjOi2o");
    }
}

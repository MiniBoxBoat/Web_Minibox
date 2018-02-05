import com.minibox.conf.MvcConfig;
import com.minibox.dao.ReservationMapper;
import com.minibox.dao.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MvcConfig.class)
@WebAppConfiguration
@ActiveProfiles("pro")
public class SpecialTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ReservationMapper reservationMapper;

    @Test
    public void increaseUserCredibilityPerWeekTest(){
        assertEquals(true, userMapper.increaseUserCredibilityPerWeek());
    }

    @Test
    public void updateReservationExpFlagTest() {
        assertEquals(true, reservationMapper.updateOverdueReservationExpFlag());
    }
}

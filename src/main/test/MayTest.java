import com.minibox.conf.MvcConfig;
import com.minibox.dao.BoxMapper;
import com.minibox.dao.CouponMapper;
import com.minibox.dao.ReservationMapper;
import com.minibox.dao.UserMapper;
import com.minibox.po.CouponPo;
import com.minibox.po.ReservationPo;
import com.minibox.po.UserPo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MvcConfig.class)
@WebAppConfiguration
@ActiveProfiles("pro")
public class MayTest {

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private BoxMapper boxMapper;

    private UserPo user;

//username,password,phone_number,sex,email,credibility,use_time
    @Before
    public void before(){
        user = new UserPo();
        user.setUserName("fsdf");
        user.setPassword("123465");
        user.setPhoneNumber("132132");
        user.setSex("男");
        user.setEmail("105456");
        user.setCredibility(100);
    }


//
    @Test
    public void test(){
        List<ReservationPo> reservationPos = reservationMapper.findReservationsByUserId(0);
        System.out.println(reservationPos.size());
    }
}

package dao;

import com.minibox.conf.MvcConfig;
import com.minibox.dao.CouponMapper;
import com.minibox.po.CouponPo;
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
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MvcConfig.class)
@WebAppConfiguration
@ActiveProfiles("dev")
public class CouponMapperTest {

    @Autowired
    private CouponMapper couponMapper;
    private CouponPo coupon;

    @Before
    public void before(){
        coupon = new CouponPo();
        coupon.setUserId(131);
        coupon.setMoney(10);
        coupon.setDeadlineTime("2018-2-3 12:00:00");
    }

    @Test
    public void insertCouponTest(){
        Assert.assertEquals(true, couponMapper.insertCoupon(coupon));
    }

    @Test
    public void removeCouponTest(){
        Assert.assertEquals(true,  couponMapper.removeCoupon(2));
    }

    @Test
    public void findCouponsByUserIdTest(){
        List<CouponPo> coupons = couponMapper.findCouponsByUserId(2);
        assertEquals(4, coupons.size());
    }
}

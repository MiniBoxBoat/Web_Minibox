package dao;

import com.minibox.dao.CouponMapper;
import com.minibox.po.Coupon;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springConfig.xml")
public class CouponMapperTest {

    @Resource
    CouponMapper couponMapper;

    @Test
    public void insertCouponTest(){
/*        Coupon coupon = new Coupon();
        coupon.setDeadlineTime("2014-02-22 12:23:12");
        coupon.setMoney(5);
        coupon.setUserId(3);*/
        boolean flag = couponMapper.insertCoupon(1, 5,"2014-02-22 12:23:12" );
        Assert.assertEquals(flag, true);
    }

    @Test
    public void removeCouponTest(){
        boolean flag = couponMapper.removeCoupon(1);
        Assert.assertEquals(flag, true);
    }
}

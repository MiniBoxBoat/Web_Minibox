package com.minibox.service;

import com.minibox.dao.CouponMapper;
import com.minibox.po.CouponPo;
import com.minibox.service.util.JavaWebToken;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.minibox.service.util.ServiceExceptionChecking.*;

/**
 * @author MEI
 */
@Service
public class CouponService {

    @Resource
    private CouponMapper couponMapper;


    public void addCoupon(CouponPo coupon, String taken) {
        checkAddCouponParameters(coupon);
        int userId = JavaWebToken.getUserIdAndVerifyTakenFromTaken(taken);
        coupon.setUserId(userId);
        checkSqlExcute(couponMapper.insertCoupon(coupon));
    }

    private void checkAddCouponParameters(CouponPo coupon){
        checkTimeIsInPattern(coupon.getDeadlineTime());
        checkTimeIsAfterNow(coupon.getDeadlineTime());
    }

    public void deleteCoupon(int couponId) {
        checkSqlExcute(couponMapper.removeCoupon(couponId));
    }

    public List<CouponPo> getCouponsByUserId(String taken) {
        int userId = JavaWebToken.getUserIdAndVerifyTakenFromTaken(taken);
        return couponMapper.findCouponsByUserId(userId);
    }
}

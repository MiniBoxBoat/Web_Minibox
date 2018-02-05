package com.minibox.dao;

import com.minibox.po.CouponPo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author MEI
 */
@Repository
public interface CouponMapper {

    boolean insertCoupon(CouponPo coupon);

    boolean removeCoupon(int couponId);

    List<CouponPo> findCouponsByUserId(int userId);
}

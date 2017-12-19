package com.minibox.service.coupon;

import com.minibox.po.Coupon;

import java.util.List;

/**
 * @author MEI
 */
public interface CouponService {

    /**
     * 添加优惠券
     * @param userId 用户id
     * @param money 优惠券面额
     * @param deadlineTime 有效时间
     * @return 是否储存成功
     */
    boolean addCoupon(int userId, int money, String deadlineTime);

    /**
     * 删除优惠券
     * @param couponId 优惠券id
     * @return 是否删除成功
     */
    boolean deleteCoupon(int couponId);

    /**
     * 得到用户的优惠券
     * @param userId 用户id
     * @return 优惠券
     */
    List<Coupon> getCoupons(int userId);
}

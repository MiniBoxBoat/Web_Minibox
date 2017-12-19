package com.minibox.dao;

import com.minibox.po.Coupon;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;

/**
 * @author MEI
 */
@Repository
public interface CouponMapper {


    /**
     * 添加优惠券
     * @param userId 用户id
     * @param money 优惠券金额
     * @param deadlineTime 有效期限
     * @return 是否添加成功
     */
    boolean insertCoupon(@Param("userId") int userId, @Param("money") double money, @Param("deadlineTime") String deadlineTime);

    /**
     * 撤销用户的优惠券
     * @param couponId 优惠券id
     * @return 是否删除成功
     */
    boolean removeCoupon(int couponId);

    /**
     * 得到用户下的所有优惠券
     * @param userId 用户id
     * @return 用户的优惠券
     */
    List<Coupon> findCouponsByUserId(int userId);
}

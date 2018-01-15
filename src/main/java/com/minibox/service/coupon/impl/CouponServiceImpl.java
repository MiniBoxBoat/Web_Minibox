package com.minibox.service.coupon.impl;

import com.minibox.dao.CouponMapper;
import com.minibox.exception.ServerException;
import com.minibox.po.Coupon;
import com.minibox.service.coupon.CouponService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author MEI
 */
@Service
public class CouponServiceImpl implements CouponService{

    @Resource
    CouponMapper couponMapper;

    @Override
    public boolean addCoupon(int userId, int money, String deadlineTime) {
        return couponMapper.insertCoupon(userId,money,deadlineTime);
    }
    @CacheEvict
    @Override
    public boolean deleteCoupon(int couponId) {
        return couponMapper.removeCoupon(couponId);
    }
    @Override
    public List<Coupon> getCoupons(int userId) {
        return couponMapper.findCouponsByUserId(userId);
    }
}

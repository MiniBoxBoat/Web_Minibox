package com.minibox.service;

import com.minibox.dao.CouponMapper;
import com.minibox.exception.ParameterException;
import com.minibox.exception.ServerException;
import com.minibox.po.CouponPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.minibox.util.FormatUtil;
import com.minibox.util.JavaWebTaken;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author MEI
 */
@Service
public class CouponService {

    @Autowired
    private CouponMapper couponMapper;


    public void addCoupon(CouponPo coupon, String taken) {
        checkAddCouponParameters(coupon);
        int userId = JavaWebTaken.getUserIdAndVerifyTakenFromTaken(taken);
        coupon.setUserId(userId);
        if(!couponMapper.insertCoupon(coupon)){
            throw new ServerException();
        }
    }

    private void checkAddCouponParameters(CouponPo coupon){
        if (!FormatUtil.isTimePattern(coupon.getDeadlineTime())){
            throw new ParameterException("时间格式不正确", 400);
        }
        LocalDateTime localDateTime = LocalDateTime.parse(coupon.getDeadlineTime(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (localDateTime.isBefore(LocalDateTime.now())){
            throw new ParameterException("期限不能是以前的时间",400);
        }
    }

    public void deleteCoupon(int couponId) {
        if (!couponMapper.removeCoupon(couponId)){
            throw new ServerException();
        }
    }

    public List<CouponPo> getCouponsByUserId(String taken) {
        int userId = JavaWebTaken.getUserIdAndVerifyTakenFromTaken(taken);
        return couponMapper.findCouponsByUserId(userId);
    }
}

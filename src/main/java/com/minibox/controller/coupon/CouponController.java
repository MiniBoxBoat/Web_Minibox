package com.minibox.controller.coupon;

import com.minibox.dto.ResponseEntity;
import com.minibox.po.CouponPo;
import com.minibox.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.minibox.constants.Constants.*;


/**
 * @author MEI
 */
@RestController()
@RequestMapping("coupon")
public class CouponController {

    @Autowired
    CouponService couponService;

    @PostMapping("addCoupon.do")
    public ResponseEntity<Object> addCoupon(CouponPo coupon, String taken) {
        couponService.addCoupon(coupon, taken);
        return new ResponseEntity<>(200, SUCCESS, null);
    }

    @GetMapping("deleteCoupon.do")
    public ResponseEntity<Object> deleteCoupon(int couponId) {
        couponService.deleteCoupon(couponId);
        return new ResponseEntity<>(200, SUCCESS, null);
    }

    @PostMapping("showCoupon.do")
    public ResponseEntity<List<CouponPo>> showCoupons(String taken) {
        System.out.println(taken);
        List<CouponPo> coupons = couponService.getCouponsByUserId(taken);
        return new ResponseEntity<>(200, SUCCESS, coupons);
    }
}

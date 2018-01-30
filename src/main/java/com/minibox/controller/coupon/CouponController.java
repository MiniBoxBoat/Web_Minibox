package com.minibox.controller.coupon;

import com.minibox.po.Coupon;
import com.minibox.service.coupon.CouponService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import util.JsonUtil;
import util.MapUtil;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author MEI
 */
@Controller
@RequestMapping(value = "/coupon")
public class CouponController {

    @Resource
    CouponService couponService;

    @RequestMapping(value = "addCoupon.do", method = RequestMethod.POST)
    public void addCoupon(int userId, int money, String deadlineTime) {
        Map map;
        try {
            if (!couponService.addCoupon(userId, money, deadlineTime)) {
                throw new Exception();
            }
            map = MapUtil.toMap(200, "操作成功", null);
            JsonUtil.toJSON(map);
        } catch (Exception e) {
            map = MapUtil.toMap(500, "服务器错误", null);
            JsonUtil.toJSON(map);
        }
    }

    @RequestMapping(value = "deleteCoupon.do", method = RequestMethod.GET)
    public void deleteCoupon(int couponId) {
        Map map;
        try {
            if (!couponService.deleteCoupon(couponId)) {
                throw new Exception();
            }
            map = MapUtil.toMap(200, "操作成功", null);
            JsonUtil.toJSON(map);
        } catch (Exception e) {
            map = MapUtil.toMap(500, "服务器错误", null);
            JsonUtil.toJSON(map);
        }
    }

    @RequestMapping(value = "showCoupon.do", method = RequestMethod.GET)
    public void showCoupon(int userId) {
        Map map;
        try {
            List<Coupon> coupons = couponService.getCoupons(userId);
            if (coupons == null) {
                throw new Exception();
            }
            map = MapUtil.toMap(200, "获取数据成功", coupons);
            JsonUtil.toJSON(map);
        } catch (Exception e) {
            map = MapUtil.toMap(500, "服务器错误", null);
            JsonUtil.toJSON(map);
        }
    }

    public static void main(String[] args) {
        CouponController controller = new CouponController();
        Method[] methods = controller.getClass().getDeclaredMethods();
        int i = 0;
        for (Method method : methods) {
            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
            if (requestMapping == null){
                continue;
            }
            System.out.println(Arrays.toString(requestMapping.value()));
        }
    }
}

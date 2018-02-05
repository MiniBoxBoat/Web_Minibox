package com.minibox.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author MEI
 */
@Data
public class CouponPo {
    private int couponId;
    private int userId;
    private double money;
    private String deadlineTime;
}

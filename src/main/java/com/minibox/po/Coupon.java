package com.minibox.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author MEI
 */
@Data
public class Coupon {
    private int couponId;
    private int userId;
    private int money;
    private String deadlineTime;

}

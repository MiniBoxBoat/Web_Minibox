package com.minibox.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author MEI
 */
@Data
public class Sale {
    private int saleInfoId;
    private int userId;
    private int boxId;
    private int groupId;
    private String payTime;
    private String orderTime;
    private double cost;
    private int delFlag;
}

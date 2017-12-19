package com.minibox.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author MEI
 */
@Data
public class Sale {
    private String userName;
    private int boxId;
    private int groupId;
    private String payTime;
    private String orderTime;
    private double cost;
    private int delFlag;
}

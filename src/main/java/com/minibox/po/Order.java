package com.minibox.po;

import lombok.Data;

/**
 * @author MEI
 */
@Data
public class Order {
    private int orderId;
    private int userId;
    private int groupId;
    private int boxId;
    private String orderTime;
    private int delFlag;
}

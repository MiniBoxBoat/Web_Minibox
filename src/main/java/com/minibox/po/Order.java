package com.minibox.po;

import lombok.Data;

/**
 * @author MEI
 */
@Data
public class Order {
    private int orderId;
    private String userName;
    private int groupId;
    private int boxId;
    private String orderTime;
    private int delFlag;
}

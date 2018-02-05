package com.minibox.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author MEI
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderPo {
    private int orderId;
    private int userId;
    private int groupId;
    private int boxId;
    private String orderTime;
    private int delFlag;
}

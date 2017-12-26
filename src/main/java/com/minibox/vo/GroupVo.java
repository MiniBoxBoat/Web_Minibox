package com.minibox.vo;

import lombok.Data;

/**
 * @author MEI
 */
@Data
public class GroupVo {
    private int groupId;
    private int quantity;
    private String position;
    private double lat;
    private double lng;
    private int emptySmallBoxNum;
    private int emptyLargeBoxNum;
}

package com.minibox.vo;

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
public class GroupVo {
    private int groupId;
    private int quantity;
    private String position;
    private double lat;
    private double lng;
    private int emptySmallBoxNum;
    private int emptyLargeBoxNum;
}

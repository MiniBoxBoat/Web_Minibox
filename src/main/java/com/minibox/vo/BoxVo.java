package com.minibox.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoxVo {
    private int boxId;
    private String boxSize;
    private int boxStatus;
    private String groupName;
    private double lat;
    private double lng;
    private String openTime;
}

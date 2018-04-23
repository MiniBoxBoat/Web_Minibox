package com.minibox.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transportation {

    private int transportationId;
    private int userId;
    private String startPlace = "";
    private String endPlace = "";
    private String name = "";
    private String phoneNumber = "";
    private String receiveTime;
    private String goodsType = "";
    private String company = "";
    private String transportationComment = "";
    private float cost;
    private int score;
    private int transportationStatus;
    private int finishedFlag;
    private int delFlag;
}

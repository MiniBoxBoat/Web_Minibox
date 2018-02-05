package com.minibox.dto;

import lombok.Data;

@Data
public class OrderDto {
    private int userId;
    private Integer groupId;
    private String size;
    private Integer boxNum;
}

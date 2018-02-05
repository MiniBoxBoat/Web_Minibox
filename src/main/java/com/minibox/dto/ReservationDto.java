package com.minibox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    private String openTime;
    private Integer useTime;
    private String userName;
    private String phoneNumber;
    private Integer groupId;
    private String boxSize;
    private Integer boxNum;
}

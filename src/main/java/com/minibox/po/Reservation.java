package com.minibox.po;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author MEI
 */

@Data
public class Reservation {
    private int reservationId;
    private int userId;
    private String openTime;
    private int useTime;
    private String userName;
    private String phoneNumber;
    private String position;
    private int boxId;
    private String boxSize;
    private int delFlag;
    private int expFlag;

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", userId=" + userId +
                ", openTime='" + openTime + '\'' +
                ", useTime=" + useTime +
                ", userName='" + userName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", position='" + position + '\'' +
                ", boxId=" + boxId +
                ", boxSize=" + boxSize +
                ", delFlag=" + delFlag +
                ", expFlag=" + expFlag +
                '}';
    }
}

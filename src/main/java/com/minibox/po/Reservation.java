package com.minibox.po;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author MEI
 */

@Data
@ToString
public class Reservation {
    private int reservationId;
    private int userId;
    private String openTime;
    private int useTime;
    private String userName;
    private String phoneNumber;
    private int groupId;
    private int boxId;
    private String boxSize;
    private int delFlag;
    private int expFlag;
}

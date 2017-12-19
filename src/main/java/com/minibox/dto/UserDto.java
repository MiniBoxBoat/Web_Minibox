package com.minibox.dto;

import lombok.Data;

/**
 * @author MEI
 */

@Data
public class UserDto {
    private int userId;
    private String userName="";
    private String phoneNumber="";
    private String sex;
    private String email="";
    private String image="";
    private String trueName="";
    private String taken="";
    private int useTime;
    private int credibility;


    @Override
    public String toString() {
        return "UserDto{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", trueName='" + trueName + '\'' +
                '}';
    }
}

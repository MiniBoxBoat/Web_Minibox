package com.minibox.po;

import lombok.Data;

/**
 * @author MEI
 */
@Data
public class User {
    private int userId;
    private String userName="";
    private String password="";
    private String phoneNumber="";
    private String sex="";
    private String email="";
    private int credibility;
    private int useTime;
    private String image="";
    private String trueName="";
    private String taken="";

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                ", credibility=" + credibility +
                ", useTime=" + useTime +
                ", image='" + image + '\'' +
                ", trueName='" + trueName + '\'' +
                '}';
    }
}

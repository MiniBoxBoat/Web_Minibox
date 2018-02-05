package com.minibox.po;

import lombok.Data;

/**
 * @author MEI
 */
@Data
public class UserPo implements Cloneable{
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
    private String verifyCode="";

    @Override
    public String toString() {
        return "UserPo{" +
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        UserPo userPo = (UserPo) super.clone();
        return userPo;
    }
}

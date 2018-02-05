package com.minibox.vo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author MEI
 */

@Data
@ToString
public class UserVo {
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

    private UserVo(UserVoBuilder builder){
        userId = builder.userId;
        userName = builder.userName;
        phoneNumber = builder.phoneNumber;
        sex = builder.sex;
        email = builder.email;
        image = builder.image;
        trueName = builder.trueName;
        taken = builder.taken;
        useTime = builder.useTime;
        credibility = builder.credibility;
    }

    public static class UserVoBuilder{
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

        public UserVoBuilder userId(int userId){
            this.userId = userId;
            return this;
        }

        public UserVoBuilder userName(String userName){
            this.userName = userName;
            return this;
        }

        public UserVoBuilder phoneNumber(String phoneNumber){
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserVoBuilder sex(String sex){
            this.sex = sex;
            return this;
        }

        public UserVoBuilder email(String email){
            this.email = email;
            return this;
        }

        public UserVoBuilder image(String image){
            this.image = image;
            return this;
        }

        public UserVoBuilder trueName(String trueName){
            this.trueName = trueName;
            return this;
        }

        public UserVoBuilder taken(String taken){
            this.taken = taken;
            return this;
        }

        public UserVoBuilder useTime(int useTime){
            this.useTime = useTime;
            return this;
        }

        public UserVoBuilder credibility(int credibility){
            this.credibility = credibility;
            return this;
        }

        public UserVo build(){
            return new UserVo(this);
        }
    }
}

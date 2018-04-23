package com.minibox.dao.redisDao.verifycode;

public interface IRedisVerifyCodeDao {

    void addVerifyCode(String phoneNumber, String verifyCode);

    void deleteVerifyCode(String phoneNumber);

    String getVerifyCode(String phoneNumber);


}

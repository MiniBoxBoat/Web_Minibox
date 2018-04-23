package com.minibox.dao.redisDao.verifycode;

import com.minibox.dao.redisDao.AbstractBaseRedisDao;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisVerifyCode extends AbstractBaseRedisDao<String, String> implements IRedisVerifyCodeDao {

    @Override
    public void addVerifyCode(String phoneNumber, String verifyCode) {
        redisTemplate.opsForValue().set(phoneNumber, verifyCode);
        redisTemplate.expire(phoneNumber, 30, TimeUnit.SECONDS);
    }

    @Override
    public void deleteVerifyCode(String phoneNumber) {
        redisTemplate.delete(phoneNumber);
    }

    @Override
    public String getVerifyCode(String phoneNumber) {
        return redisTemplate.opsForValue().get(phoneNumber);
    }
}

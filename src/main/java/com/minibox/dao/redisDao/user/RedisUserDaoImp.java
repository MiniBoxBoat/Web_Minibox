package com.minibox.dao.redisDao.user;

import com.minibox.dao.redisDao.AbstractBaseRedisDao;
import com.minibox.vo.UserVo;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class RedisUserDaoImp extends AbstractBaseRedisDao<String, Serializable> implements IRedisUserDao {

    @Override
    public boolean addUser(UserVo user) {
        return redisTemplate.execute(redisConnection -> {
            String key = UserVo.OBJECT_KEY + user.getUserId();
            System.out.println(key);
            redisTemplate.opsForValue().set(key, user);
            return true;
        }, false, true);
    }

    @Override
    public boolean deleteUser(int userId) {
        return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            redisTemplate.delete(UserVo.OBJECT_KEY + userId);
            return true;
        });
    }

    @Override
    public UserVo findUser(int userId) {
        System.out.println(redisTemplate.opsForValue().get(UserVo.OBJECT_KEY + userId));
        return (UserVo) redisTemplate.opsForValue().get(UserVo.OBJECT_KEY + userId);
    }

    @Override
    public boolean updateUser(UserVo user) {
        return redisTemplate.execute(redisConnection -> {
            redisTemplate.opsForValue().set(UserVo.OBJECT_KEY + user.getUserId(), user);
            System.out.println("point2");
            return true;
        },false, true);
    }
}

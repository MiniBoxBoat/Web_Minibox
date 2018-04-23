package com.minibox.redis;

import com.minibox.dao.redisDao.AbstractBaseRedisDao;
import com.minibox.vo.UserVo;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class UserRedisImp extends AbstractBaseRedisDao<String, Serializable> {

    public void addUser(UserVo userVo) {
        /*Objects.requireNonNull(redisTemplate);
        redisTemplate.opsForHash().put(UserVo.OBJECT_KEY, userVo.getUserId(), userVo);*/
/*        boolean result = redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            ValueOperations<String, Serializable> valueOperations
                     = redisTemplate.opsForValue();
            valueOperations.set(UserVo.OBJECT_KEY + userVo.getUserId(), userVo);
            return true;
        }, false, true);*/
        redisTemplate.opsForValue().set(UserVo.OBJECT_KEY + userVo.getUserId(), userVo);
    }

    public void deleteUser(String key) {
        redisTemplate.opsForHash().delete(UserVo.OBJECT_KEY, key);
    }

    public UserVo findUserVo(int uesrId) {
/*        UserVo result = redisTemplate.execute((RedisCallback<UserVo>) redisConnection -> {
            ValueOperations<String, Serializable> operations =
                    redisTemplate.opsForValue();
            UserVo userVo = (UserVo) operations.get(UserVo.OBJECT_KEY + String.valueOf(uesrId));
            return userVo;
        });
        return result;*/

        return (UserVo) redisTemplate.opsForValue().get(UserVo.OBJECT_KEY + uesrId);
    }
}

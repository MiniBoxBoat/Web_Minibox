package com.minibox.dao.redisDao.user;

import com.minibox.vo.UserVo;

public interface IRedisUserDao {
    boolean addUser(UserVo user);

    boolean deleteUser(int userId);

    UserVo findUser(int userId);

    boolean updateUser(UserVo user);
}

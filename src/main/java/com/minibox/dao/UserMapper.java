package com.minibox.dao;

import com.minibox.dto.UserDto;
import com.minibox.po.User;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PatchMapping;

/**
 * @author MEI
 */
@Repository
public interface UserMapper {

    /**
     * 储存用户信息
     * @param user 用户对象
     * * @return 是否存储成功
     */
    int insertUser(User user);

    /**
     * 通过手机号找到用户
     * @param phoneNumber 手机号
     * @return 用户信息
     */
    User findUserByPhoneNumber(String phoneNumber);


    /**
     * 通过用户名和密码得到用户对象
     * @param userName 用户名
     * @param password 密码
     * @return 用户对象
     */
    UserDto findUserByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);

    /**
     * 通过用户id得到用户对象
     * @param userId 用户id
     * @return 用户对象
     */
    UserDto findUserByUserId(int userId);

    /**
     * 通过电话号码和密码找到用户记录
     * @param phoneNumber 电话号码
     * @param password 密码
     * @return 用户记录
     */
    UserDto findUserByPhoneNumberAndPassword(@Param("phoneNumber") String phoneNumber, @Param("password")String password);

    /**
     * 修改用户信息
     * @param user
     * @return 是否修改成功
     */
    boolean updateUser(User user);

    /**
     * 周期性的修改用户的信誉值
     * @return 是否修改成功
     */
    boolean updateUserCredibility();
    /**
     * 增加用户使用次数
     * @param userId 用户id
     * @return 是否修改成功
     */
    boolean updateUseTime(int userId);

    /**
     * 修改用户头像
     * @param avatar 头像链接地址
     * @return 是否修改成功
     */
    boolean updateAvatar(@Param("avatar") String avatar, @Param("userId") int userId);

    /**
     * 修改用户密码
     * @param newPassword 新密码
     * @return 是否修改成功
     */
    boolean updatePassword(@Param("newPassword") String newPassword, @Param("userId") int userId);

    /**
     * 修改用户的taken
     * @param taken taken 字符串
     * @param userId 用户id
     * @return 是否修改成功
     */
    boolean updateTaken(@Param("taken") String taken, @Param("userId") int userId);

    /**
     * 通过用户名找到用户对象
     * @param userName 用户名
     * @return 用户对象
     */
    UserDto findUserByUserName(String userName);


}

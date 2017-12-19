package com.minibox.service.user;

import com.aliyuncs.exceptions.ClientException;
import com.minibox.dto.UserDto;
import com.minibox.exception.*;
import com.minibox.po.User;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    /**
     * 储存用户信息
     * @param user 用户对象
     * * @return 是否存储成功
     */
    UserDto addUser (User user, String verifyCode, HttpServletRequest request) throws ParameterException, ParameterIsNullException, VerifyCodeException;

    /**
     * 登录的时候检查用户名和密码是否错误
     * @param userName 用户名
     * @param password 密码
     * @return 用户对象
     */
    UserDto checkUser(String phoneNumber, String password) throws UserNotExistException, PasswordFailedException, ParameterException, ServerException;

    /**
     * 得到用户的信息
     * @param userId 用户的id
     * @return 用户对象
     */
    UserDto getUserInfo(int userId);

    /**
     * 修改用户信息
     * @param user 用户对象
     * @return 是否修改成功
     */
    boolean updateUser(User user) throws ParameterException, ParameterIsNullException, TakenVirifyException;


    /**
     * 修改用户的使用次数
     * @param userId 用户id
     * @return 是否修改成功
     */
    boolean updateUserUserUseTime(int userId);

    /**
     * 修改用户头像
     * @param avatar 用户头像链接地址
     * @param userId 用户Id
     * @return 是否修改成功
     */
    boolean updateUserAvatar(String avatar, int userId);

    /**
     * 修改用户头像
     * @param newPassword 新密码
     * @param userId 用户id
     * @return 是否修改成功
     */
    boolean updatePassword(String newPassword, int userId,String taken) throws ParameterException, TakenVirifyException;

    /**
     * 验证taken
     * @param taken taken
     * @return 是否验证成功
     */
    void checkTaken(String taken) throws TakenVirifyException, ServerException;

    /**
     * 得到taken中的id
     * @param taken taken
     * @return userId
     */
    int getTakenUserId(String taken) throws ServerException, TakenVirifyException;

    /**
     * 发送阿里云短信验证码
     * @param phoneNumber 手机号
     * @return 是否发送成功
     */
    String sendSms(String phoneNumber, HttpServletRequest request) throws ClientException, SendSmsFailedException, ParameterException;

}

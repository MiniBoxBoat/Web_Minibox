package com.minibox.service.user.impl;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.minibox.dao.UserMapper;
import com.minibox.dto.UserDto;
import com.minibox.exception.*;
import com.minibox.po.User;
import com.minibox.service.user.UserService;
import org.springframework.stereotype.Service;
import util.FormatUtil;
import util.JavaWebTaken;
import util.RamdomNumberUtil;
import util.Sms;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static util.RedirectPrinter.*;
import static util.Print.*;

/**
 * @author MEI
 */
@Service
public class UserServiceImpl implements UserService {

    private static final String EXCEPTION_STR1 = "key 'number'";
    private static final String EXCEPTION_STR2 = "key 'phone_number'";

    @Resource
    UserMapper userMapper;

    @Override
    public UserDto addUser(User user,String verifyCode, HttpServletRequest request) throws ParameterException, ParameterIsNullException, VerifyCodeException {

        if (user.getUserName()==null || user.getEmail()==null || user.getSex()==null||verifyCode==null){
            throw new ParameterIsNullException("前检查信息是否填写完整");
        }

        if (!verifyCode.equals(request.getSession().getAttribute("code"))){
            throw new VerifyCodeException();
        }

        if (user.getUserName().length()>10){
            throw new ParameterException("用户名不要超过十个字符");
        }
        if (!FormatUtil.isPhoneNumberLegal(user.getPhoneNumber())){
            throw new ParameterException("手机号格式不正确");
        }
        if (user.getPassword().length()<5){
            throw new ParameterException("密码不要小于五个字符");
        }
        userMapper.insertUser(user);
        UserDto userDto = userMapper.findUserByUserNameAndPassword(user.getUserName(), user.getPassword());
        return userDto;
    }

    @Override
    public UserDto checkUser(String phoneNumber, String password) throws UserNotExistException, PasswordFailedException, ParameterException, ServerException {
        User user = userMapper.findUserByPhoneNumber(phoneNumber);

        if (!FormatUtil.isPhoneNumberLegal(phoneNumber)){
            throw new ParameterException("手机号格式不正确");
        }
        if (user == null){
            throw new UserNotExistException();
        }
        if (!user.getPassword().equals(password)){
            throw new PasswordFailedException();
        }
        UserDto userDto = userMapper.findUserByPhoneNumberAndPassword(phoneNumber, password);

        Map<String, Object> loginInfo = new HashMap<>();
        loginInfo.put("userId", user.getUserId());
        long time = System.currentTimeMillis() + 24*60*60*1000;
        loginInfo.put("exp",time);
        String taken = JavaWebTaken.createJavaWebToken(loginInfo);
        userDto.setTaken(taken);
        if (!userMapper.updateTaken(taken, user.getUserId())){
            throw new ServerException();
        }
        return userDto;
    }

    @Override
    public UserDto getUserInfo(int userId) {
        UserDto user = userMapper.findUserByUserId(userId);
        return user;
    }

    @Override
    public boolean updateUser(User user) throws ParameterException, ParameterIsNullException, TakenVirifyException {
        if (user.getUserName()==null || user.getEmail()==null || user.getSex()==null){
            throw new ParameterIsNullException("前检查信息是否填写完整");
        }
        if(!user.getTaken().equals(userMapper.findUserByUserId(user.getUserId()).getTaken())){
            throw new TakenVirifyException();
        }
        if (!FormatUtil.isPhoneNumberLegal(user.getPhoneNumber())){
            throw new ParameterException("手机号格式不正确");
        }
        if (user.getUserName().length()>10){
            throw new ParameterException("用户名不要超过十个字符");
        }
        return user!=null && userMapper.updateUser(user);
    }


    @Override
    public boolean updateUserUserUseTime(int userId) {
        return userMapper.updateUseTime(userId);
    }

    @Override
    public boolean updateUserAvatar(String avatar, int userId) {
        return userMapper.updateAvatar(avatar, userId);
    }

    @Override
    public boolean updatePassword(String newPassword, int userId,String taken) throws ParameterException, TakenVirifyException {
        if (newPassword.length()<5){
            throw new ParameterException("密码不要小于五个字符");
        }
        if(!taken.equals(userMapper.findUserByUserId(userId).getTaken())){
            throw new TakenVirifyException();
        }
        return userMapper.updatePassword(newPassword, userId);
    }

    @Override
    public String sendSms(String phoneNumber, HttpServletRequest request) throws ClientException, SendSmsFailedException, ParameterException {
        if (!FormatUtil.isPhoneNumberLegal(phoneNumber)){
            throw new ParameterException("手机号格式不正确");
        }
        String code = RamdomNumberUtil.makeCode();
        SendSmsResponse sendSmsResponse = Sms.sendSms(phoneNumber, code);

        if (sendSmsResponse.getCode() == null || !sendSmsResponse.getCode().equals("OK")) {
            throw new SendSmsFailedException();
        }
        request.getSession().setAttribute("code", code);
        return code;
    }

    @Override
    public void checkTaken(String taken) throws ServerException, TakenVirifyException {
        Map<String, Object> map = JavaWebTaken.verifyJavaWebToken(taken);
        int userId = (int) map.get("userId");
        System.out.println(userId);
        UserDto user = userMapper.findUserByUserId(userId);
        if (!taken.equals(user.getTaken())){
            throw new TakenVirifyException();
        }
    }

    @Override
    public int getTakenUserId(String taken) throws ServerException, TakenVirifyException {
        Map<String, Object> map = JavaWebTaken.verifyJavaWebToken(taken);
        int userId = (int) map.get("userId");
        return userId;
    }
}

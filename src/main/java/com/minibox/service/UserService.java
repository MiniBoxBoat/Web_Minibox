package com.minibox.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.minibox.constants.ExceptionMessage;
import com.minibox.dao.UserMapper;
import com.minibox.dao.redisDao.user.RedisUserDaoImp;
import com.minibox.dao.redisDao.verifycode.RedisVerifyCode;
import com.minibox.exception.ParameterException;
import com.minibox.exception.SendSmsFailedException;
import com.minibox.exception.ServerException;
import com.minibox.exception.VerifyCodeException;
import com.minibox.po.UserPo;
import com.minibox.service.util.JavaWebToken;
import com.minibox.service.util.RamdomNumberUtil;
import com.minibox.service.util.Sms;
import com.minibox.vo.UserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

import static com.minibox.constants.ExceptionMessage.*;
import static com.minibox.service.util.ServiceExceptionChecking.*;

/**
 * @author MEI
 */
@Service
public class UserService {
    private static final String SMS_SUCCESS_CODE = "OK";

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisUserDaoImp redisUserDao;

    @Resource
    private RedisVerifyCode redisVerifyCode;

    public UserVo addUserAndCheckVerifyCode(UserPo user,String verifyCode){
        checkUserParameterAndIfDouble(user);
        checkVerifyCode(user.getPhoneNumber(), verifyCode);
        if (!userMapper.insertUser(user)){
            throw new ServerException();
        }
        redisUserDao.addUser(userPoToUserVo(user));
        return userPoToUserVo(user);
    }

    private void checkUserParameterAndIfDouble(UserPo user){
        if (user.getUserName()==null || user.getEmail()==null || user.getSex()==null){
            throw new ParameterException(PARAMETER_IS_NOT_FULL);
        }
        checkUserNameIsTooLong(user.getUserName());
        checkPhoneNumberIsTrue(user.getPhoneNumber());
        checkPhoneNumberIsUsed(userMapper.findUserByPhoneNumber(user.getPhoneNumber()));

        UserPo userGetByPhoneNumber = userMapper.findUserByPhoneNumber(user.getPhoneNumber());
        checkPhoneNumberIsUsed(userGetByPhoneNumber);
        UserPo userGetByUserName = userMapper.findUserByUserName(user.getUserName());
        checkUserNameIsUsed(userGetByUserName);
    }

    public UserVo checkUser(String phoneNumber, String password){
        checkPhoneNumberIsTrue(phoneNumber);
        UserPo user = userMapper.findUserByPhoneNumber(phoneNumber);
        Objects.requireNonNull(user, RESOURCE_NOT_FOUND);
        if (!user.getPassword().equals(password)){
            throw new ParameterException(PASSWORD_IS_WRONG);
        }

        UserPo userPo = userMapper.findUserByPhoneNumberAndPassword(phoneNumber, password);
        String taken = JavaWebToken.createJavaWebToken(user.getUserId());
        UserVo userVo = userPoToUserVo(userPo);
        userVo.setTaken(taken);

        return userVo;
    }

    public UserVo getUserInfoByUserId(String taken) {
        int userId = JavaWebToken.getUserIdAndVerifyTakenFromTaken(taken);
        UserVo userVo = redisUserDao.findUser(userId);
        if (userVo == null) {
            UserPo userPo = userMapper.findUserByUserId(userId);
            Objects.requireNonNull(userPo,RESOURCE_NOT_FOUND);
            return userPoToUserVo(userPo);
        }
        return userVo;
    }

    public void updateUser(UserPo user,String taken) throws Exception {
        int userId = JavaWebToken.getUserIdAndVerifyTakenFromTaken(taken);
        UserVo userVo = redisUserDao.findUser(userId);
        if (userVo != null){
            redisUserDao.updateUser(userPoToUserVo(user));
        }else{
            throw new Exception(ExceptionMessage.REDIS_NO_SOURCE);
        }
        UserPo userGetByUserId = userMapper.findUserByUserId(userId);
        Objects.requireNonNull(userGetByUserId,RESOURCE_NOT_FOUND);
        checkUpdateUserParameters(user);
        user.setUserId(userId);
        if (!userMapper.updateUser(user)){
            throw new ServerException();
        }
    }


    private void checkUpdateUserParameters(UserPo user){
        if (user.getUserName()==null || user.getEmail()==null || user.getSex()==null){
            throw new ParameterException(PARAMETER_IS_NOT_FULL);
        }
        checkUserNameIsTooLong(user.getUserName());
        checkUserNameIsUsed(userMapper.findUserByUserName(user.getUserName()));
        checkSexIsTrue(user.getSex());
    }

    public void updateAvatar(String taken, String avatarUrl){
        int userId = JavaWebToken.getUserIdAndVerifyTakenFromTaken(taken);

        UserVo user = redisUserDao.findUser(userId);
        user.setImage(avatarUrl);
        redisUserDao.updateUser(user);

        if (!userMapper.updateAvatarByAvatarAndUserId(avatarUrl, userId)){
            throw new ServerException();
        }
    }

    public void updatePasswordAndCheckVerifyCode(String newPassword, String taken, String verifyCode){
        int userId = JavaWebToken.getUserIdAndVerifyTakenFromTaken(taken);

        UserVo user = redisUserDao.findUser(userId);
        Objects.requireNonNull(user, ExceptionMessage.REDIS_NO_SOURCE);
        String phoneNumber = user.getPhoneNumber();
        checkVerifyCode(phoneNumber, verifyCode);

        if (newPassword.length()<5){
            throw new ParameterException(PASSWORD_IS_TOO_SHORT);
        }
        if (!userMapper.updatePasswordByNewPasswordAndUserId(newPassword, userId)){
            throw new ServerException();
        }
    }

    private void checkVerifyCode(String phoneNumber, String code){
        String verifyCode = redisVerifyCode.getVerifyCode(phoneNumber);
        if (!code.equals(verifyCode)){
            throw new VerifyCodeException();
        }
        redisVerifyCode.deleteVerifyCode(phoneNumber);
    }

    public String sendSms(String phoneNumber) throws ClientException {
        String code = RamdomNumberUtil.makeCode();
        redisVerifyCode.addVerifyCode(phoneNumber, code);
        SendSmsResponse sendSmsResponse = Sms.sendSms(phoneNumber, code);

        if (sendSmsResponse.getCode() == null || !sendSmsResponse.getCode().equals(SMS_SUCCESS_CODE)) {
            System.out.println(sendSmsResponse.getCode());
            throw new SendSmsFailedException();
        }
        return code;
    }

    private UserVo userPoToUserVo(UserPo userPo){
        UserVo user = new UserVo.UserVoBuilder()
                .userId(userPo.getUserId())
                .userName(userPo.getUserName())
                .credibility(userPo.getCredibility())
                .email(userPo.getEmail())
                .phoneNumber(userPo.getPhoneNumber())
                .sex(userPo.getSex())
                .useTime(userPo.getUseTime())
                .build();
        if (userPo.getImage() != null){
            user.setImage(userPo.getImage());
        }
        if (userPo.getTrueName() != null) {
            user.setTrueName(userPo.getTrueName());
        }
        return user;
    }
}

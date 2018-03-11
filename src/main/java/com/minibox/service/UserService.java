package com.minibox.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.minibox.dao.UserMapper;
import com.minibox.exception.ParameterException;
import com.minibox.exception.SendSmsFailedException;
import com.minibox.exception.ServerException;
import com.minibox.exception.TokenVerifyException;
import com.minibox.po.UserPo;
import com.minibox.po.VerifyCodePo;
import com.minibox.service.util.JavaWebToken;
import com.minibox.service.util.RamdomNumberUtil;
import com.minibox.service.util.Sms;
import com.minibox.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.minibox.constants.Constants.VERIFYCODE_HASH;
import static com.minibox.constants.ExceptionMessage.*;
import static com.minibox.service.util.ServiceExceptionChecking.*;

/**
 * @author MEI
 */
@Service
public class UserService {
    private static final String SMS_SUCCESS_CODE = "OK";

    @Autowired
    private UserMapper userMapper;

    private RedisTemplate<String, String> redisTemplate;

    private BoundHashOperations<String, String, String> verifycode;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate){
        this.redisTemplate = redisTemplate;
        verifycode = redisTemplate.boundHashOps(VERIFYCODE_HASH);
    }

    public UserVo addUserAndCheckVerifyCode(UserPo user,String verifyCode){
        checkUserIsDouble(user);
        checkAddUserAndCheckVerifyCodeParameters(user, verifyCode);
        String code = verifycode.get(user.getPhoneNumber());
        if (code == null) {
            throw new TokenVerifyException();
        }
        if (userMapper.insertUser(user)){
            verifycode.delete(user.getPhoneNumber());
        }else {
            throw new ServerException();
        }
        UserPo userPo = userMapper.findUserByUserNameAndPassword(user.getUserName(), user.getPassword());
        return userPoToUserVo(userPo);
    }

    private void checkUserIsDouble(UserPo user){
        UserPo userGetByPhoneNumber = userMapper.findUserByPhoneNumber(user.getPhoneNumber());
        checkPhoneNumberIsUsed(userGetByPhoneNumber);
        UserPo userGetByUserName = userMapper.findUserByUserName(user.getUserName());
        checkUserNameIsUsed(userGetByUserName);
    }

    private void checkAddUserAndCheckVerifyCodeParameters(UserPo user, String verifyCode){
        if (user.getUserName()==null || user.getEmail()==null || user.getSex()==null||verifyCode==null){
            throw new ParameterException(PARAMETER_IS_NOT_FULL);
        }
        checkUserNameIsTooLong(user.getUserName());
        checkPhoneNumberIsTrue(user.getPhoneNumber());
        checkPhoneNumberIsUsed(userMapper.findUserByPhoneNumber(user.getPhoneNumber()));
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

    @Cacheable(value = "miniboxCache",
            condition = "T(com.minibox.service.util.JavaWebToken).isTokenTrue(#taken)",
            key = "T(com.minibox.service.util.JavaWebToken).getUserIdAndVerifyTakenFromTaken(#taken)")
    public UserVo getUserInfoByUserId(String taken) {
        int userId = JavaWebToken.getUserIdAndVerifyTakenFromTaken(taken);
        UserPo userPo = userMapper.findUserByUserId(userId);
        Objects.requireNonNull(userPo,RESOURCE_NOT_FOUND);
        return userPoToUserVo(userPo);
    }

    @CacheEvict(value = "miniboxCache",
            key ="T(com.minibox.service.util.JavaWebToken).getUserIdAndVerifyTakenFromTaken(#taken)")
    public void updateUser(UserPo user,String taken) {
        int userId = JavaWebToken.getUserIdAndVerifyTakenFromTaken(taken);
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
        if (!userMapper.updateAvatarByAvatarAndUserId(avatarUrl, userId)){
            throw new ServerException();
        }
    }

    public void updatePassword(String newPassword, String taken, String verifyCode){
        int userId = JavaWebToken.getUserIdAndVerifyTakenFromTaken(taken);
        if (newPassword.length()<5){
            throw new ParameterException(PASSWORD_IS_TOO_SHORT);
        }

        if (!userMapper.updatePasswordByNewPasswordAndUserId(newPassword, userId)){
            throw new ServerException();
        }
    }

    public String sendSms(String phoneNumber) throws ClientException {
        String code = RamdomNumberUtil.makeCode();
        SendSmsResponse sendSmsResponse = Sms.sendSms(phoneNumber, code);
        if (sendSmsResponse.getCode() == null || !sendSmsResponse.getCode().equals(SMS_SUCCESS_CODE)) {
            throw new SendSmsFailedException();
        }
        verifycode.put(phoneNumber, code);
        return code;
    }


    public void addVerifyCodeRe(VerifyCodePo verifyCode) {
        if (!userMapper.insertVerifyCode(verifyCode)){
            throw new ServerException();
        }
    }

    private UserVo userPoToUserVo(UserPo userPo){
        return new UserVo.UserVoBuilder()
                .userId(userPo.getUserId())
                .userName(userPo.getUserName())
                .credibility(userPo.getCredibility())
                .email(userPo.getEmail())
                .image(userPo.getImage())
                .phoneNumber(userPo.getPhoneNumber())
                .sex(userPo.getSex())
                .useTime(userPo.getUseTime())
                .trueName(userPo.getTrueName())
                .build();
    }
}

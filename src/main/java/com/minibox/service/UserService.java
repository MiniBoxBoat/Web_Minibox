package com.minibox.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.minibox.dao.UserMapper;
import com.minibox.exception.ParameterException;
import com.minibox.exception.SendSmsFailedException;
import com.minibox.exception.ServerException;
import com.minibox.po.UserPo;
import com.minibox.po.VerifyCodePo;
import com.minibox.util.FormatUtil;
import com.minibox.util.JavaWebTaken;
import com.minibox.util.RamdomNumberUtil;
import com.minibox.util.Sms;
import com.minibox.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author MEI
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public UserVo addUserAndCheckVerifyCode(UserPo user,String verifyCode){
        checkUserIsDouble(user);
        checkAddUserAndCheckVerifyCodeParameters(user, verifyCode);
        VerifyCodePo verifyCode1 = userMapper.findVerifyCode(user.getPhoneNumber());
        Objects.requireNonNull(verifyCode1, "数据库里面没有电话号码对应的验证码");
        if (!verifyCode1.getVerifyCode().equals(verifyCode)){
            throw new ParameterException("输入的验证码错误", 401);
        }
        if (!userMapper.insertUser(user)){
            throw new ServerException();
        }
        UserPo userPo = userMapper.findUserByUserNameAndPassword(user.getUserName(), user.getPassword());
        return userPoToUserVo(userPo);
    }

    private void checkUserIsDouble(UserPo user){
        UserPo userGetByPhoneNumber = userMapper.findUserByPhoneNumber(user.getPhoneNumber());
        if (userGetByPhoneNumber != null){
            throw new ParameterException("电话号码已经被注册过了", 400);
        }
        UserPo userGetByUserName = userMapper.findUserByUserName(user.getUserName());
        if (userGetByUserName != null){
            throw new ParameterException("用户名已经被注册过了", 400);
        }
    }

    private void checkAddUserAndCheckVerifyCodeParameters(UserPo user, String verifyCode){
        if (user.getUserName()==null || user.getEmail()==null || user.getSex()==null||verifyCode==null){
            throw new ParameterException("前检查信息是否填写完整", 400);
        }
        if (user.getUserName().length()>10){
            throw new ParameterException("用户名不要超过十个字符",400);
        }
        if (!FormatUtil.isPhoneNumberLegal(user.getPhoneNumber())){
            throw new ParameterException("手机号格式不正确", 400);
        }
        if (user.getPassword().length()<5){
            throw new ParameterException("密码不要小于五个字符", 400);
        }
        if (userMapper.findUserByPhoneNumber(user.getPhoneNumber())!= null){
            throw new ParameterException("手机号已经被注册过了", 400);
        }
    }

    @Cacheable("miniboxCache")
    public UserVo checkUser(String phoneNumber, String password){
        checkCheckUserParameters(phoneNumber);
        UserPo user = userMapper.findUserByPhoneNumber(phoneNumber);
        if (user == null){
            throw new ParameterException("该用户不存在", 400);
        }
        if (!user.getPassword().equals(password)){
            throw new ParameterException("密码输入错误", 400);
        }
        UserPo userPo = userMapper.findUserByPhoneNumberAndPassword(phoneNumber, password);
        String taken = JavaWebTaken.createJavaWebToken(user.getUserId());
        UserVo userVo = userPoToUserVo(userPo);
        userVo.setTaken(taken);
        return userVo;
    }

    private void checkCheckUserParameters(String phoneNumber){
        if (!FormatUtil.isPhoneNumberLegal(phoneNumber)){
            throw new ParameterException("手机号格式不正确", 400);
        }
    }

    @Cacheable(value = "miniboxCache", key = "#{T(com.minibox.util.JavaWebTaken).getUserIdAndVerifyTakenFromTaken(#root.args[0])}")
    public UserVo getUserInfoByUserId(String taken) {
        int userId = JavaWebTaken.getUserIdAndVerifyTakenFromTaken(taken);
        UserPo userPo = userMapper.findUserByUserId(userId);
        if (userPo == null){
            throw new ParameterException("没有找到该用户的资源", 404);
        }
        return userPoToUserVo(userPo);
    }

    @CacheEvict(value = "miniboxCache", key ="#{T(com.minibox.util.JavaWebTaken).getUserIdAndVerifyTakenFromTaken(#root.args[1])}")
    public void updateUser(UserPo user,String taken) {
        int userId = JavaWebTaken.getUserIdAndVerifyTakenFromTaken(taken);
        UserPo userGetByUserId = userMapper.findUserByUserId(userId);
        if (userGetByUserId == null) {
            throw new ParameterException("没有找到该用户的资源", 404);
        }
        checkUpdateUserParameters(user);
        user.setUserId(userId);
        if (!userMapper.updateUser(user)){
            throw new ServerException();
        }
    }

    private void checkUpdateUserParameters(UserPo user){
        if (user.getUserName()==null || user.getEmail()==null || user.getSex()==null){
            throw new ParameterException("前检查信息是否填写完整", 400);
        }
        if (!FormatUtil.isPhoneNumberLegal(user.getPhoneNumber())){
            throw new ParameterException("手机号格式不正确", 400);
        }
        if (user.getUserName().length()>10){
            throw new ParameterException("用户名不要超过十个字符", 400);
        }
        if (userMapper.findUserByPhoneNumber(user.getPhoneNumber()) != null){
            throw new ParameterException("手机号已经被使用过", 400);
        }
        if (userMapper.findUserByUserName(user.getUserName()) != null){
            throw new ParameterException("用户名已经被注册过了",400);
        }
        if(!user.getSex().equals("男") && !user.getSex().equals("女")){
            throw new ParameterException("性别只能是男或者女",400 );
        }
    }

    public void updateAvatar(String taken, String avatarUrl){
        int userId = JavaWebTaken.getUserIdAndVerifyTakenFromTaken(taken);
        if (!userMapper.updateAvatarByAvatarAndUserId(avatarUrl, userId)){
            throw new ServerException();
        }
    }

    public void updatePassword(String newPassword, String taken){
        int userId = JavaWebTaken.getUserIdAndVerifyTakenFromTaken(taken);
        if (newPassword.length()<5){
            throw new ParameterException("密码不要小于五个字符", 400);
        }
        if (!userMapper.updatePasswordByNewPasswordAndUserId(newPassword, userId)){
            throw new ServerException();
        }
    }

    public String sendSms(String phoneNumber) throws ClientException {
        checkSendSmsParameters(phoneNumber);
        String code = RamdomNumberUtil.makeCode();
        SendSmsResponse sendSmsResponse = Sms.sendSms(phoneNumber, code);
        if (sendSmsResponse.getCode() == null || !sendSmsResponse.getCode().equals("OK")) {
            throw new SendSmsFailedException();
        }
        return code;
    }

    private void checkSendSmsParameters(String phoneNumber){
        if (!FormatUtil.isPhoneNumberLegal(phoneNumber)){
            throw new ParameterException("手机号格式不正确", 400);
        }
        if (userMapper.findUserByPhoneNumber(phoneNumber)!=null){
            throw new ParameterException("手机号已经被注册过了", 400);
        }
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

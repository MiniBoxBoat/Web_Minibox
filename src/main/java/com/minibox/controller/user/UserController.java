package com.minibox.controller.user;

import com.aliyuncs.exceptions.ClientException;
import com.minibox.dto.ResponseEntity;
import com.minibox.po.UserPo;
import com.minibox.po.VerifyCodePo;
import com.minibox.service.UserService;
import com.minibox.vo.UserVo;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

import static com.minibox.constants.RequestResult.SUCCESS;


/**
 * @author MEI
 */
@RestController()
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("register.do")
    public ResponseEntity<UserVo> register(UserPo user, String verifyCode) {
        UserVo userVo = userService.addUserAndCheckVerifyCode(user, verifyCode);
        return new ResponseEntity<>(200, SUCCESS, userVo);
    }

    @PostMapping("login.do")
    public ResponseEntity<UserVo> login(String phoneNumber, String password) {
        UserVo user = userService.checkUser(phoneNumber, password);
        return new ResponseEntity<>(200, SUCCESS, user);
    }

    @PostMapping("showUserInfo.do")
    public ResponseEntity<UserVo> showUserInfo(String taken) {
        UserVo user = userService.getUserInfoByUserId(taken);
        return new ResponseEntity<>(200, SUCCESS, user);
    }

    @PostMapping("updateUserInfo.do")
    public ResponseEntity<Object> updateUserInfo(UserPo user,String taken) {
        userService.updateUser(user, taken);
        return new ResponseEntity<>(200, SUCCESS, null);
    }

    @PostMapping("sendSms.do")
    public ResponseEntity<String> sendSms(String phoneNumber) throws ClientException {
        String code = userService.sendSms(phoneNumber);
        userService.addVerifyCodeRe(new VerifyCodePo(phoneNumber, code));
        return new ResponseEntity<>(200, SUCCESS, code);
    }

    @PostMapping("updateAvatar.do")
    public ResponseEntity<Object> updateAvatar(String taken, String avatarUrl) {
        userService.updateAvatar(taken, avatarUrl);
        return new ResponseEntity<>(200, SUCCESS, null);
    }

    @PostMapping("updatePassword.do")
    public ResponseEntity<Object> updatePassword(String newPassword, String taken) {
        userService.updatePassword(newPassword, taken);
        return new ResponseEntity<>(200, SUCCESS, null);
    }
}

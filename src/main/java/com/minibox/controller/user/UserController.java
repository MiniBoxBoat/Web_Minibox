package com.minibox.controller.user;

import com.aliyuncs.exceptions.ClientException;
import com.minibox.dto.UserDto;
import com.minibox.exception.*;
import com.minibox.po.User;
import com.minibox.service.user.UserService;
import org.omg.PortableServer.THREAD_POLICY_ID;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import util.JsonUtil;
import util.MapUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author MEI
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final String EXCEPTION_STR2 = "key 'phone_number'";

    @Resource
    private UserService userService;

    @RequestMapping(value = "/register.do", method = RequestMethod.POST)
    public void register(User user, String verifyCode, HttpServletRequest request) {
        Map map;
        try {
            UserDto user1 = userService.addUser(user, verifyCode, request);
            if (user1 == null) {
                throw new Exception();
            }
            map = MapUtil.toMap(200, "注册成功", user1);
            JsonUtil.toJSON(map);
        } catch (DuplicateKeyException e) {
            if (e.getMessage().contains(EXCEPTION_STR2)) {
                map = MapUtil.toMap(409, "手机号已经被注册过了", null);
                JsonUtil.toJSON(map);
            } else {
                map = MapUtil.toMap(409, "用户名已经被注册过了", null);
                JsonUtil.toJSON(map);
            }
        } catch (ParameterException | ParameterIsNullException e) {
            map = MapUtil.toMap(403, e.getMessage(), null);
            JsonUtil.toJSON(map);
        } catch(VerifyCodeException e){
            map = MapUtil.toMap(401, e.getMessage(), null);
            JsonUtil.toJSON(map);
        }
        catch (Exception e) {
            e.printStackTrace();
            map = MapUtil.toMap(500, "服务器错误", null);
            JsonUtil.toJSON(map);
        }
    }

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    public void login(String phoneNumber, String password) {
        Map map;
        try {
            UserDto user = userService.checkUser(phoneNumber, password);
            if (user == null) {
                throw new UserIsNullException();
            }

            map = MapUtil.toMap(200, "登录成功", user);
            JsonUtil.toJSON(map);

        } catch (UserNotExistException | ParameterException | PasswordFailedException e) {
            map = MapUtil.toMap(401, e.getMessage(), null);
            JsonUtil.toJSON(map);
        } catch (UserIsNullException e) {
            map = MapUtil.toMap(500, "服务器错误", null);
            JsonUtil.toJSON(map);
        } catch (Exception e) {
            e.printStackTrace();
            map = MapUtil.toMap(500, "服务器错误", null);
            JsonUtil.toJSON(map);
        }
    }

    @RequestMapping(value = "/showUserInfo.do", method = RequestMethod.GET)
    public void showUserInfo(int userId) {
        Map map;
        try {
            UserDto user = userService.getUserInfo(userId);
            if (user == null) {
                throw new Exception();
            }
            map = MapUtil.toMap(200, "获取数据成功", user);
            JsonUtil.toJSON(map);

        } catch (Exception e) {
            map = MapUtil.toMap(500, "服务器错误", null);
            JsonUtil.toJSON(map);
        }
    }


    @RequestMapping(value = "/updateUserInfo.do", method = RequestMethod.POST)
    public void updateUserInfo(User user) {
        Map map;
        try {
            userService.checkTaken(user.getTaken());
            if (!userService.updateUser(user)) {
                throw new Exception();
            }
            map = MapUtil.toMap(200, "修改信息成功", null);
            JsonUtil.toJSON(map);
        } catch (TakenVirifyException e) {
            map = MapUtil.toMap(401, e.getMessage(), null);
            JsonUtil.toJSON(map);
        } catch (ParameterException | ParameterIsNullException e) {
            map = MapUtil.toMap(403, e.getMessage(), null);
            JsonUtil.toJSON(map);
        } catch (Exception e) {
            e.printStackTrace();
            map = MapUtil.toMap(500, "服务器错误", null);
            JsonUtil.toJSON(map);
        }
    }


    @RequestMapping(value = "/sendSms.do", method = RequestMethod.POST)
    public void sendSms(String phoneNumber, HttpServletRequest request) {
        Map map;
        try {
            String code = userService.sendSms(phoneNumber, request);
            map = MapUtil.toMap(200, "请求验证码成功", code);
            JsonUtil.toJSON(map);
        } catch (ClientException e) {
            map = MapUtil.toMap(400, "客户端请求错误", null);
            JsonUtil.toJSON(map);
        } catch (SendSmsFailedException e) {
            map = MapUtil.toMap(400, "客户端请求失败", null);
        } catch (ParameterException e) {
            map = MapUtil.toMap(401, e.getMessage(), null);
        }
    }

    @RequestMapping(value = "checkVerifyCode.do", method = RequestMethod.POST)
    public void checkVerifyCode(String code, HttpServletRequest request) {
        Map map;
        try {
            if (!code.equals(request.getSession().getAttribute("code"))) {
                throw new VerifyCodeException();
            }
            map = MapUtil.toMap(200, "验证码正确", null);
            JsonUtil.toJSON(map);
        } catch (VerifyCodeException e) {
            map = MapUtil.toMap(400, e.getMessage(), null);
            JsonUtil.toJSON(map);
        }
    }

    @RequestMapping(value = "/updateAvatar.do", method = RequestMethod.POST)
    public void updateAvatar(int userId, @RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) {
        Map map;
        try {
            if (!userService.updateUserAvatar(request, file, userId)){
                throw new Exception();
            }
            map = MapUtil.toMap(200, "修改头像成功", null);
            JsonUtil.toJSON(map);
        } catch (Exception e){
            map = MapUtil.toMap(500, "服务器错误!!!!!!", e.getStackTrace());
            JsonUtil.toJSON(map);
        }
    }

    @RequestMapping(value = "/updatePassword.do", method = RequestMethod.POST)
    public void updatePassword(String newPassword, String taken) {
        Map map;
        try {
            int userId = userService.getTakenUserId(taken);
            userService.checkTaken(taken);
            if (!userService.updatePassword(newPassword, userId, taken)) {
                throw new Exception();
            }

        } catch (ParameterException e) {
            map = MapUtil.toMap(400, e.getMessage(), null);
            JsonUtil.toJSON(map);
        } catch (TakenVirifyException e) {
            map = MapUtil.toMap(401, e.getMessage(), null);
            JsonUtil.toJSON(map);
        } catch (Exception e) {
            map = MapUtil.toMap(500, "服务器错误", null);
            JsonUtil.toJSON(map);
        }
    }


}

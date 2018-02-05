package service;

import com.minibox.conf.MvcConfig;
import com.minibox.po.UserPo;
import com.minibox.service.UserService;
import com.minibox.vo.UserVo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MvcConfig.class)
@WebAppConfiguration
@ActiveProfiles("dev")
public class UserServiceTest {

    @Autowired
    private UserService userService;
    private UserPo user;

    @Before
    public void before(){
        user = new UserPo();
        user.setUserName("yj");
        user.setPassword("1234564");
        user.setPhoneNumber("15808060137");
        user.setSex("男");
        user.setEmail("1058752198@qq.com");
    }

    @Test
    public void addUserAndCheckVerifyCodeTest(){
        UserVo userVo =  userService.addUserAndCheckVerifyCode(user, "111111");
        assertEquals("yj",userVo.getUserName());
    }

    @Test
    public void checkUserTest(){
        UserVo userVo = userService.checkUser("18500944413",
                "e10adc3949ba59abbe56e057f20f883e");
        assertEquals("zzq", userVo.getUserName());
    }

/*
    @Test
    public void getUserInfoByUserIdTest(){
        UserVo userVo = userService.getUserInfoByUserId()
    }
*/



}

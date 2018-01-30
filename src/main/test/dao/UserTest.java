package dao;

import com.minibox.dao.UserMapper;
import com.minibox.dto.UserDto;
import com.minibox.po.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.UserServiceTest;
import util.JavaWebTaken;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springConfig.xml")
public class UserTest {

    @Resource
    UserMapper userMapper;


    @Test
    public void insertUserTest(){
        User user = new User();
        user.setCredibility(100);
        user.setEmail("1058752198@qq.com");
        user.setPassword("123456");
        user.setPhoneNumber("15808060138");
        user.setUserName("mei");
        user.setUseTime(0);
    }

    @Test
    public void findUserByUserNameAndPasswordTest(){
        UserDto user = userMapper.findUserByUserNameAndPassword("yong", "123456");
        System.out.println(user.toString());
    }

    @Test
    public void findUserByUserIdTest(){
        UserDto user = userMapper.findUserByUserNameAndPassword("mei1","123456");
        System.out.println(user.getSex());
    }

    @Test
    public void updateUserTest(){
        User user = new User();
        user.setUserId(93);
        user.setUserName("mei1");
        user.setPhoneNumber("15808060138");
        user.setEmail("1058752198@qq.com");
        user.setTaken("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjExMX0.ZzY4BrEHo37bGWLSZK9LI-glCXHkqtl7zC3aEADXvkU");
        user.setTrueName("梅勇杰诶");
        boolean flag = userMapper.updateUser(user);
         }

    @Test
    public void updateTakenTest(){
        Map<String, Object> loginInfo = new HashMap<>();
        loginInfo.put("userId", 111);
        String taken = JavaWebTaken.createJavaWebToken(loginInfo);
        boolean flag = userMapper.updateTaken(taken,111);
        Assert.assertEquals(flag, true);
    }

    @Test
    public void updateUseTimeTest(){
        boolean flag = userMapper.updateUseTime(111);
        Assert.assertEquals(true, flag);
    }

    @Test
    public void updateAvatar(){
        boolean flag = userMapper.updateAvatar("1111",131);
        Assert.assertEquals(true, flag);
    }



}

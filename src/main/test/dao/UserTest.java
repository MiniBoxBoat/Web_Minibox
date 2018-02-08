package dao;

import com.minibox.conf.MvcConfig;
import com.minibox.dao.UserMapper;
import com.minibox.po.UserPo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import com.minibox.service.util.JavaWebToken;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MvcConfig.class)
@WebAppConfiguration
@ActiveProfiles("pro")
public class UserTest {

    @Autowired
    private UserMapper userMapper;
    private UserPo user;

    @Before
    public void before() {
        user = new UserPo();
        user.setEmail("1058752198@qq.com");
        user.setPhoneNumber("15808060135");
        user.setUserName("yj");
        user.setTrueName("梅勇杰2");
        user.setSex("男");
    }

    @Test
    public void insertUserTest() {
        boolean flag = userMapper.insertUser(user);
        assertEquals(true, flag);
    }

    @Test
    public void findUserByPhoneNumberTest() {
        UserPo user = userMapper.findUserByPhoneNumber("15808060138");
        assertEquals(user.getUserName(), "May");
    }

    @Test
    public void findUserByUserNameAndPasswordTest() {
        UserPo user = userMapper.findUserByUserNameAndPassword("May",
                "e10adc3949ba59abbe56e057f20f883e");
        assertEquals(131, user.getUserId());
    }

    @Test
    public void findUserByUserIdTest() {
        UserPo user = userMapper.findUserByUserId(131);
        assertEquals("May", user.getUserName());
    }

    @Test
    public void findUserByPhoneNumberAndPasswordTest(){
        UserPo user = userMapper.findUserByPhoneNumberAndPassword("15808060138", "e10adc3949ba59abbe56e057f20f883e");
        assertEquals(131,user.getUserId());
    }

    @Test
    public void updateUserTest() throws CloneNotSupportedException {
        UserPo userPo = (UserPo) user.clone();
        userPo.setUserId(135);
        boolean flag = userMapper.updateUser(userPo);
        assertEquals(true, flag);
    }

    @Test
    public void updateTakenTest() {
        String taken = JavaWebToken.createJavaWebToken(132);
        boolean flag = userMapper.updateTakenByTakenAndUserId(taken, 132);
        Assert.assertEquals(flag, true);
    }

    @Test
    public void updateUseTimeTest() {
        boolean flag = userMapper.updateUseTime(131);
        Assert.assertEquals(true, flag);
    }

    @Test
    public void updateAvatar() {
        boolean flag = userMapper.updateAvatarByAvatarAndUserId("1111", 131);
        Assert.assertEquals(true, flag);
    }

    @Test
    public void updatePasswordTest(){
        boolean flag = userMapper.updatePasswordByNewPasswordAndUserId("me.....i",131);
        assertEquals(true, flag);
    }

}

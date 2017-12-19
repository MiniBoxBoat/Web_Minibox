package service;

import com.minibox.dto.UserDto;
import com.minibox.exception.*;
import com.minibox.po.User;
import com.minibox.service.user.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springConfig.xml")
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void updateTakenTest() throws PasswordFailedException, ServerException, UserNotExistException, ParameterException {
        UserDto userDto = userService.checkUser("15808060138","123456");
        Assert.assertEquals(userDto.getUserName(), "mei1");
    }

    @Test
    public void updateUserTest() throws ParameterException, ParameterIsNullException {
        User user = new User();
        user.setUserId(111);
        user.setUserName("mei1");
        user.setPhoneNumber("15808060138");
        user.setEmail("1058752198@qq.com");
        user.setTaken("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjExMX0.ZzY4BrEHo37bGWLSZK9LI-glCXHkqtl7zC3aEADXvkU");
        user.setTrueName("梅勇杰1");
    }

}

package redis;

import com.minibox.conf.CachingConfig;
import com.minibox.conf.MvcConfig;
import com.minibox.conf.RedisConfig;
import com.minibox.redis.UserRedisImp;
import com.minibox.vo.UserVo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CachingConfig.class, MvcConfig.class, RedisConfig.class})
@WebAppConfiguration
@ActiveProfiles("dev")
public class RedisTest {

    private UserVo userVo;

    @Autowired
    private UserRedisImp userRedisImp;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Before
    public void before() {
        userVo = new UserVo.UserVoBuilder()
                .userId(1)
                .userName("mei")
                .credibility(10)
                .email("fdsf")
                .image("fdfs")
                .phoneNumber("fdsfs")
                .sex("女")
                .taken("fdsf")
                .trueName("fdsdfd")
                .useTime(2)
                .build();
    }

    @Test
    public void test() {
/*        userRedisImp.saveUser(userVo);
        UserVo userVo1 = userRedisImp.findUserVo(UserVo.OBJECT_KEY);
        System.out.println(userVo1.getEmail());*/
        System.out.println(redisTemplate.opsForValue().get("user:160"));
    }

    @Test
    public void test2() {
        redisTemplate.delete(UserVo.OBJECT_KEY + 160);
    }
}

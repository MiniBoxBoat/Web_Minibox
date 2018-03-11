package redis;

import com.minibox.conf.MvcConfig;
import com.minibox.conf.RedisConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.minibox.constants.Constants.VERIFYCODE_HASH;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
@WebAppConfiguration
public class RedisTest {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void test(){
        BoundHashOperations<String, String, String> verifycodeHash =
                redisTemplate.boundHashOps(VERIFYCODE_HASH);
        verifycodeHash.put("15808060138","454545");
        String code = verifycodeHash.get("15808060138");
        Assert.assertEquals("454545", code);
    }

    @Test
    public void test2(){
        HashOperations<String, String, String> verifycode = redisTemplate.opsForHash();
        String code = verifycode.get(VERIFYCODE_HASH, "15808060138");
        System.out.println(code);
    }
}

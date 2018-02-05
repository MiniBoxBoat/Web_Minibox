package service;

import com.minibox.conf.MvcConfig;
import com.minibox.po.BoxPo;
import com.minibox.service.BoxService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MvcConfig.class)
@WebAppConfiguration
@ActiveProfiles("dev")
public class BoxServiceTest {
    @Autowired
    private BoxService boxService;

    @Test
    public void getUsingBoxesTest(){
//        List<BoxPo> boxPos = boxService.getUsingBoxes()
    }

}



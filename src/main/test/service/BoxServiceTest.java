package service;

import com.minibox.exception.BoxIsBusyException;
import com.minibox.exception.RollbackException;
import com.minibox.service.box.BoxService;
import com.minibox.vo.GroupVo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springConfig.xml")
public class BoxServiceTest {

    @Resource
    BoxService boxService;

    @Test
    public void getGroupByDestinationTest(){
        List<GroupVo> groupVos = boxService.getGroupByDestination("南岸", 106.615554,29.53893);
        for (GroupVo groupVo : groupVos){
            System.out.println(groupVo.getIsNear());
            if (groupVo.getIsNear() == 1){
                System.out.println(groupVo.getPosition());
            }
        }
    }

    @Test
    public void addSaleInfoTest() throws RollbackException {
        Boolean flag = boxService.addSaleInfo(78, 5.8);
        Assert.assertEquals(flag, true);
    }

    @Test
    public void addOrderTest() throws BoxIsBusyException {

    }

    @Test
    public void updateBoxStatus(){
    }

    @Test
    public void addSaleInfo() throws RollbackException {
        boolean flag = boxService.addSaleInfo(7, 9);
        Assert.assertEquals(flag, true);
    }


}



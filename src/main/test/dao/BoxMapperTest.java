package dao;

import com.minibox.dao.BoxMapper;
import com.minibox.po.Box;
import com.minibox.po.GroupPo;
import com.minibox.po.Order;
import com.minibox.po.Sale;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springConfig.xml")
public class BoxMapperTest {

    @Resource
    BoxMapper boxMapper;

    @Test
    public void findGroupsByDestinationTest(){
        List<GroupPo> groupPos = boxMapper.findGroupsByDestination("南岸");
        Assert.assertEquals(groupPos.size(), 7);
    }

    @Test
    public void insertOrderTest(){
//        boolean flag = boxMapper.insertOrder("mei",1,1);
//        Assert.assertEquals(flag, true);
    }

    @Test
    public void removeOrderTest(){
        boolean flag = boxMapper.removeOrder(77);
        Assert.assertEquals(flag, true);
    }

    @Test
    public void findOrderByOrderIdTest(){
        Order order = boxMapper.findOrderByOrderId(78);
//        Assert.assertEquals(order.getUserName(),"15808060138");
    }

    @Test
    public void insertSaleInfoTest(){
        Sale sale = new Sale();
        sale.setGroupId(1);
        sale.setCost(1);
        sale.setBoxId(1);
//        sale.setUserName("mei");
        boolean flag = boxMapper.insertSaleInfo(sale);
    }

    @Test
    public void findBoxesTest(){
//        List<Box> boxes = boxMapper.findBoxes("mei");
//        Assert.assertEquals(boxes.size(), 1);

    }

    @Test
    public void updateBoxStatus(){
        boolean flag = boxMapper.updateBoxStatus(101);
        Assert.assertEquals(flag, true);
    }

    @Test
    public void reduceGroupBoxNumTest(){
//        boolean flag = boxMapper.reduceGroupBoxNum("重庆市南岸区南坪万达", 3);
//        Assert.assertEquals(flag, true);
    }

    @Test
    public void findEmptyBoxesTest(){
        List<Box> boxes = boxMapper.findEmptyBoxes("重庆市南岸区南坪万达","小");
        System.out.println(boxes.size());
    }

}

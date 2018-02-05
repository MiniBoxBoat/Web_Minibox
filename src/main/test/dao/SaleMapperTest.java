package dao;

import com.minibox.conf.MvcConfig;
import com.minibox.dao.SaleMapper;
import com.minibox.po.SalePo;
import com.minibox.po.SalePo.SalePoBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MvcConfig.class)
@WebAppConfiguration
@ActiveProfiles("dev")
public class SaleMapperTest {

    @Autowired
    private SaleMapper saleMapper;
    private SalePo sale;

    @Before
    public void before(){
        sale = SalePo.builder()
                .userId(131)
                .boxId(101)
                .groupId(1)
                .payTime("2018-2-15 10:00:00")
                .orderTime("2018-2-15 10:00:01")
                .cost(5.00)
                .build();
    }

    @Test
    public void insertSaleInfoTest(){
        boolean flag = saleMapper.insertSaleInfo(sale);
        Assert.assertEquals(true, flag);

    }
}

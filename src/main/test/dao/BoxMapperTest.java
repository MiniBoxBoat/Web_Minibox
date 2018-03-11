package dao;

import com.minibox.conf.MvcConfig;
import com.minibox.dao.BoxMapper;
import com.minibox.po.BoxPo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MvcConfig.class)
@WebAppConfiguration
@ActiveProfiles("dev")
public class BoxMapperTest {

    @Resource
    private BoxMapper boxMapper;

    @Test
    public void updateBoxStatusTest(){
        assertEquals(true, boxMapper.updateBoxStatus(101));
    }

    @Test
    public void findBoxByBoxIdTest(){
        BoxPo boxPo = boxMapper.findBoxByBoxId(101);
        assertEquals(1, boxPo.getGroupId());
    }

    @Test
    public void findUsingBoxesByUserIdTest(){
        List<BoxPo> usingBoxPos = boxMapper.findUsingBoxesByUserId(195);
        assertEquals(1, usingBoxPos.size());
    }

    @Test
    public void findReservingBoxedTest(){
        List<BoxPo> reservingBoxPos = boxMapper.findReservingBoxedByUserId(135);
        assertEquals(1, reservingBoxPos.size());
    }

    @Test
    public void findEmptySmallBoxTest(){
        List<BoxPo> emptySmallBoxPos = boxMapper.findEmptySmallBoxByGroupId(1);
        assertEquals(29, emptySmallBoxPos.size());
    }

    @Test
    public void findEmptySmallBoxCountByGroupIdTest(){
        int emptySmallBoxCount = boxMapper.findEmptySmallBoxCountByGroupId(1);
        assertEquals(29, emptySmallBoxCount);
    }

    @Test
    public void findEmptyLargeBoxByGroupIdTest(){
        List<BoxPo> emptyLargeBoxPos = boxMapper.findEmptyLargeBoxByGroupId(1);
        assertEquals(10, emptyLargeBoxPos.size());
    }

    @Test
    public void findEmptyLargeBoxCountByGroupIdTest(){
        int emptyLargeBoxCount = boxMapper.findEmptyLargeBoxCountByGroupId(1);
        assertEquals(10, emptyLargeBoxCount);
    }
}

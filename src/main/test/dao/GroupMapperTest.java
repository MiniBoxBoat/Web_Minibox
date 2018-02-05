package dao;

import com.minibox.conf.MvcConfig;
import com.minibox.dao.GroupMapper;
import com.minibox.po.GroupPo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.security.acl.Group;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MvcConfig.class)
@WebAppConfiguration
@ActiveProfiles("dev")
public class GroupMapperTest {

    @Autowired
    private GroupMapper groupMapper;

    @Test
    public void findGroupsByDestinationTest(){
        List<GroupPo> groupPos = groupMapper.findGroupsByDestination("%重庆%");
        assertEquals(10, groupPos.size());
    }

    @Test
    public void findGroupByGroupIdTest(){
        GroupPo group = groupMapper.findGroupByGroupId(1);
        assertEquals(30,group.getEmpty());
    }

    @Test
    public void reduceGroupBoxNumTest(){
        assertEquals(true, groupMapper.reduceGroupBoxNum(1,2));
    }

    @Test
    public void increaseGroupBoxNumTest(){
        assertEquals(true, groupMapper.increaseGroupBoxNum(1,2));
    }

}

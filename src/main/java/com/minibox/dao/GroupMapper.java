package com.minibox.dao;

import com.minibox.po.GroupPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMapper {

    List<GroupPo> findAllGroup();

    List<GroupPo> findGroupsByDestination(String destination);

    List<GroupPo> findGroupsByDestination();

    GroupPo findGroupByGroupId(int groupId);

    boolean reduceGroupBoxNum(@Param("groupId") int groupId, @Param("num") int num);

    boolean increaseGroupBoxNum(@Param("groupId") int groupId, @Param("num") int num);

}

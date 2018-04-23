package com.minibox.service;

import com.minibox.dao.BoxMapper;
import com.minibox.dao.GroupMapper;
import com.minibox.po.GroupPo;
import com.minibox.util.Distance;
import com.minibox.vo.GroupVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.minibox.constants.ExceptionMessage.RESOURCE_NOT_FOUND;

@Service
public class GroupService {

    @Resource
    private GroupMapper groupMapper;

    @Resource
    private BoxMapper boxMapper;

    public List<GroupVo> getGroupByDestination(String destination) {
        String destinationSql = "%" + destination + "%";
        List<GroupPo> groupPos = groupMapper.findGroupsByDestination(destinationSql);
        return groupPosToGroupVos(groupPos);
    }

    public List<GroupVo> getGroupAround(double lat, double lng) {
        List<GroupPo> groupPos = groupMapper.findAllGroup();
        List<GroupPo> filerGroupPos = groupPos.stream().filter(groupPo -> Distance.GetDistance(lat,lng, groupPo.getLat(),
                groupPo.getLng())<5).collect(Collectors.toList());
        return groupPosToGroupVos(filerGroupPos);
    }

    public GroupVo getGroupByGroupId(int groupId) {
        GroupPo groupPo = groupMapper.findGroupByGroupId(groupId);
        Objects.requireNonNull(groupPo, RESOURCE_NOT_FOUND);
        return  groupPoToGroupVo(groupPo);
    }

    private List<GroupVo> groupPosToGroupVos(List<GroupPo> groupPos){
        List<GroupVo> groupVos = new ArrayList<>();
        groupPos.forEach(groupPo -> groupVos.add(groupPoToGroupVo(groupPo)));
        return groupVos;
    }

    private GroupVo groupPoToGroupVo(GroupPo groupPo){
        int emptySmallBoxCount = boxMapper.findEmptySmallBoxCountByGroupId(groupPo.getGroupId());
        return GroupVo.builder()
                .position(groupPo.getPosition())
                .quantity(groupPo.getQuantity())
                .lng(groupPo.getLng())
                .lat(groupPo.getLat())
                .groupId(groupPo.getGroupId())
                .emptySmallBoxNum(emptySmallBoxCount)
                .emptyLargeBoxNum(boxMapper.findEmptyLargeBoxCountByGroupId(groupPo.getGroupId()))
                .build();
    }




}

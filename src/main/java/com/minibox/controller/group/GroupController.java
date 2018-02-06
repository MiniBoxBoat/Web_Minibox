package com.minibox.controller.group;

import com.minibox.dto.ResponseEntity;
import com.minibox.service.GroupService;
import com.minibox.vo.GroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.minibox.constants.RequestResult.SUCCESS;

@RestController
@RequestMapping("group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("showBoxGroup.do")
    public ResponseEntity<List<GroupVo>> showBoxGroupByKeyWord(String destination) {
        long start = System.currentTimeMillis();
        List<GroupVo> groupVos = groupService.getGroupByDestination(destination);
        System.out.println(System.currentTimeMillis() - start);
        return new ResponseEntity<>(200, SUCCESS, groupVos);
    }

    @PostMapping("showBoxGroupAround.do")
    public ResponseEntity<List<GroupVo>> showBoxGroupAround(double lat, double lng) {
        List<GroupVo> groupVos = groupService.getGroupAround(lat, lng);
        return new ResponseEntity<>(200, SUCCESS, groupVos);
    }

    @GetMapping("showBoxGroup.do")
    public ResponseEntity<GroupVo> showBoxGroup(int groupId) {
        GroupVo groupVo = groupService.getGroupByGroupId(groupId);
        return new ResponseEntity<>(200, SUCCESS, groupVo);
    }

}

package com.minibox.controller.box;

import com.minibox.exception.BoxIsBusyException;
import com.minibox.exception.ParameterException;
import com.minibox.exception.TakenVirifyException;
import com.minibox.po.Box;
import com.minibox.service.box.BoxService;
import com.minibox.service.user.UserService;
import com.minibox.vo.GroupVo;
import org.aspectj.weaver.ast.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import util.JsonUtil;
import util.MapUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author MEI
 */
@Controller
@RequestMapping(value = "box")
public class BoxController {

    @Resource
    BoxService boxService;

    @Resource
    UserService userService;

    @RequestMapping(value = "/showBoxGroup.do", method = RequestMethod.POST)
    public void showBoxGroup(String destination) {
        Map map;
        try {
            List<GroupVo> groupVos = boxService.getGroupByDestination(destination);
            if (groupVos == null) {
                throw new Exception();
            }
            map = MapUtil.toMap(200, "获取数据成功", groupVos);
            JsonUtil.toJSON(map);
        } catch (Exception e) {
            map = MapUtil.toMap(500, "服务器错误", null);
            JsonUtil.toJSON(map);
        }
    }


    @RequestMapping(value = "/order.do", method = RequestMethod.POST)
    public void order(String userName, int groupId, String boxSize, String taken) {
        Map map;
        try {
            Integer boxId = boxService.addOrder(userName, groupId, boxSize, taken);
            if (boxId == 0) {
                throw new Exception();
            }
            map = MapUtil.toMap(200, "数据存储成功", boxId);
            JsonUtil.toJSON(map);
        } catch (TakenVirifyException e) {
            e.printStackTrace();
            map = MapUtil.toMap(401, e.getMessage(), null);
            JsonUtil.toJSON(map);
        } catch (BoxIsBusyException | ParameterException e) {
            e.printStackTrace();
            map = MapUtil.toMap(400, e.getMessage(), null);
            JsonUtil.toJSON(map);
        } catch (Exception e) {
            e.printStackTrace();
            map = MapUtil.toMap(500, "服务器错误", null);
            JsonUtil.toJSON(map);
        }
    }

    @RequestMapping(value = "/endOrder", method = RequestMethod.POST)
    public void endOrder(int orderId, double cost) {
        Map map;
        try {
            if (!boxService.deleteOrder(orderId) || !boxService.addSaleInfo(orderId, cost)) {
                throw new Exception();
            }
            map = MapUtil.toMap(200, "操作成功", null);
            JsonUtil.toJSON(map);
        } catch (Exception e) {
            e.printStackTrace();
            map = MapUtil.toMap(500, "服务器错误", null);
            JsonUtil.toJSON(map);
        }
    }

    @RequestMapping(value = "/showUserBoxes.do", method = RequestMethod.POST)
    public void showUserBoxes(String userName) {
        Map map;
        try {
            List<Box> boxes = boxService.getBoxes(userName);
            if (boxes == null) {
                throw new Exception();
            }
            map = MapUtil.toMap(200, "获取数据成功", boxes);
            JsonUtil.toJSON(map);
        } catch (Exception e) {
            map = MapUtil.toMap(500, "服务器错误", null);
            JsonUtil.toJSON(map);
        }
    }

    @RequestMapping(value = "/showAllBoxes.do", method = RequestMethod.GET)
    public void showAllBoxes(int groupId) {
        Map map;
        try {
            List<Box> boxes = boxService.getAllBoxesByGroupId(groupId);
            if (boxes.size() == 0 || boxes == null) {
                throw new Exception();
            }
            map = MapUtil.toMap(200, "获取数据成功", boxes);
            JsonUtil.toJSON(map);
        } catch (Exception e) {
            map = MapUtil.toMap(500, "服务器错误", null);
            JsonUtil.toJSON(map);
        }
    }

    @RequestMapping("test.do")
    public void test(HttpServletRequest request){
        System.out.println(request.getServletContext().getRealPath("images"));
    }
}

package com.minibox.controller.box;

import com.minibox.dto.OrderDto;
import com.minibox.dto.ResponseEntity;
import com.minibox.service.BoxService;
import com.minibox.service.GroupService;
import com.minibox.service.OrderService;
import com.minibox.service.SaleService;
import com.minibox.vo.BoxVo;
import com.minibox.vo.GroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.minibox.constants.Constants.*;

/**
 * @author MEI
 */
@RestController()
@RequestMapping("box")
public class BoxController {

    @Autowired
    private BoxService boxService;

    @PostMapping("showUserUsingBoxes.do")
    public ResponseEntity<List<BoxVo>> showUserUsingBoxes(String taken) {
        List<BoxVo> boxes = boxService.getUsingBoxes(taken);
        return new ResponseEntity<>(200, SUCCESS, boxes);
    }

    @PostMapping("showUserReservingBoxes.do")
    public ResponseEntity<List<BoxVo>> showReservingBoxes(String taken) {
        List<BoxVo> boxes = boxService.getReservingBoxes(taken);
        return new ResponseEntity<>(200, SUCCESS, boxes);
    }
}

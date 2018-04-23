package com.minibox.controller.transportation;

import com.minibox.dto.ResponseEntity;
import com.minibox.po.Transportation;
import com.minibox.service.TransportationService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import static com.minibox.constants.Constants.SUCCESS;

@RestController
@RequestMapping("transportationInfos")
public class TransportationController {

    @Resource
    private TransportationService transportationService;

    @PostMapping
    public ResponseEntity<Object> submitTransportationOrder(Transportation transportation, String token){
        transportationService.addTransportation(transportation, token);
        return new ResponseEntity<>(200, SUCCESS, null);
    }

    @PatchMapping("/{transportationId}/finishFlag")
    public ResponseEntity<Object> assureGetGoods(@PathVariable int transportationId){
        transportationService.updateFinishFlag(transportationId);
        return new ResponseEntity<>(200, SUCCESS, null);
    }

    @PatchMapping("/{transportationId}/score")
    public ResponseEntity<Object> serviceAssess(@PathVariable int transportationId, int score){
        transportationService.updateScore(transportationId, score);
        return new ResponseEntity<>(200, SUCCESS, null);
    }

    @PutMapping("/{transportationId}")
    public ResponseEntity<Object> updateTransportationInfo(@PathVariable int transportationId,
            Transportation transportation, String token){
        transportationService.updateTransportation(transportation, token);
        return new ResponseEntity<>(200, SUCCESS, null);
    }

    @GetMapping("{transportationId}")
    public ResponseEntity<Object> showTransportationInfo(@PathVariable int transportationId){
        Transportation transportation = transportationService.getTransportation(transportationId);
        return new ResponseEntity<>(200, SUCCESS, transportation);
    }

    @GetMapping("/using")
    public ResponseEntity<Object> showUsingTransportationInfos(String token){
        List<Transportation> transportationInfos = transportationService.getUsingTransportationInfos(token);
        return new ResponseEntity<>(200, SUCCESS, transportationInfos);
    }

    @GetMapping("/used")
    public  ResponseEntity<Object> showUsedTransporationInfos(String token){
        List<Transportation> transportationInfos = transportationService.getUsedTransportationInfos(token);
        return new ResponseEntity<>(200, SUCCESS, transportationInfos);
    }

}

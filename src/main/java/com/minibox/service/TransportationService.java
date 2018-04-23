package com.minibox.service;

import com.minibox.dao.CouponMapper;
import com.minibox.dao.TransportationMapper;
import com.minibox.exception.ParameterException;
import com.minibox.exception.ServerException;
import com.minibox.po.CouponPo;
import com.minibox.po.Transportation;
import com.minibox.service.util.JavaWebToken;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import static com.minibox.service.util.ServiceExceptionChecking.checkPhoneNumberIsTrue;

@Service
public class TransportationService {

    @Resource
    private TransportationMapper transportationMapper;

    @Resource
    private CouponMapper couponMapper;

    public void addTransportation(Transportation transportation, String token){
        int userId = JavaWebToken.getUserIdAndVerifyTakenFromTaken(token);
        transportation.setUserId(userId);
        checkAddTransportationParameter(transportation);
        if (!transportationMapper.insertTransportation(transportation))
            throw new ServerException();
    }

    private void checkAddTransportationParameter(Transportation transportation){
        if (transportation.getUserId() == 0)
            throw new ParameterException("没有指定转运单的用户id");
        if (transportation.getStartPlace().equals("") || transportation.getEndPlace().equals("")
                || transportation.getName().equals("") || transportation.getPhoneNumber().equals("")
                || transportation.getReceiveTime() == null || transportation.getGoodsType().equals("")
                || transportation.getCompany().equals(""))
            throw new ParameterException("信息没有填写完整");
        checkPhoneNumberIsTrue(transportation.getPhoneNumber());
    }

    // @todo 这里的费用没有算出来，因为没有算出费用的具体规则
    public Expense getCost(Transportation transportation){
        float cost = 0f;
        List<CouponPo> couponPos = couponMapper.findCouponsByUserId(transportation.getUserId());
        if (couponPos.size()>0){
            Collections.sort(couponPos, (o1, o2) -> {
               if (o1.getMoney() < o2.getMoney())
                   return 1;
               else if(o1.getMoney() == o2.getMoney())
                   return 0;
               else
                   return -1;
               });
            return new Expense(cost, couponPos.get(0).getMoney());
        }else
            return new Expense(cost, 0);
    }

    static class Expense{
        private float cost;
        private double couponMoney;

        public Expense(float cost, double couponMoney){
            this.cost = cost;
            this.couponMoney = couponMoney;
        }
    }

    public void updateScore(int transportationId, int score){
        if (!transportationMapper.updateTransportationScore(transportationId, score))
            throw new ServerException();
    }

    public void updateFinishFlag(int transportationId){
        if (!transportationMapper.updateTransportationFinishFlag(transportationId))
            throw new ServerException();
    }

    public void deleteTransportation(int transportationId){
        if (!transportationMapper.removeTransportation(transportationId))
            throw new ServerException();
    }

    public void updateTransportation(Transportation transportation, String token){
        int userId = JavaWebToken.getUserIdAndVerifyTakenFromTaken(token);
        transportation.setUserId(userId);
        if (!transportationMapper.updateTransportation(transportation))
            throw new ServerException();
    }

    public List<Transportation> getUsingTransportationInfos(String token){
        int userId = JavaWebToken.getUserIdAndVerifyTakenFromTaken(token);
        return transportationMapper.findUsingTransportationInfosByUserId(userId);
    }

    public List<Transportation> getUsedTransportationInfos(String token){
        int userId = JavaWebToken.getUserIdAndVerifyTakenFromTaken(token);
        return transportationMapper.findUsedTransportationInfosByUserId(userId);
    }

    public Transportation getTransportation(int transportationId){
        Transportation transportation = transportationMapper.findTransportationByTransportationId(transportationId);
        Objects.requireNonNull(transportation);
        return transportation;
    }


}

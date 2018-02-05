package com.minibox.schedule_task;

import com.minibox.dao.ReservationMapper;
import com.minibox.dao.UserMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.minibox.util.Print.printnb;

/**
 * @author MEI
 */
@Component
public class ScheduleTaskImp implements ScheduleTask{

    @Resource
    private ReservationMapper reservationMapper;

    @Resource
    private UserMapper userMapper;

    @Scheduled(cron = "0 0 0 ? * MON ")
    @Override
    public void addCredibility() {
        userMapper.increaseUserCredibilityPerWeek();
    }

    @Scheduled(fixedDelay = 10000)
    @Override
    public void checkReservation() {
        reservationMapper.updateOverdueReservationExpFlag();
    }
}

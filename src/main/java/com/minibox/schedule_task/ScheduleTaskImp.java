package com.minibox.schedule_task;

import com.minibox.dao.ReservationMapper;
import com.minibox.dao.UserMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static util.Print.printnb;

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
        userMapper.updateUserCredibility();
    }

    @Transactional(rollbackFor = Exception.class)
    @Scheduled(fixedDelay = 10000)
    @Override
    public void checkReservation() {
        reservationMapper.updateReservationExpFlag();
    }
}

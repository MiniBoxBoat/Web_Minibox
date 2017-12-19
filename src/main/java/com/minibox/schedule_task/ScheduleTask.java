package com.minibox.schedule_task;

/**
 * @author MEI
 */
public interface ScheduleTask {

    /**
     * 每隔10秒检查一次数据库里面的reservation是否有过期的
     * 有过期的设置exp_flag并且减少信誉值
     */
    void checkReservation();

    /**
     * 在一周内用户没有违约且信誉值小于100，那么就可以一周恢复5信誉值
     */
    void addCredibility();
}

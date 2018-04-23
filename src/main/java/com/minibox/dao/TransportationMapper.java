package com.minibox.dao;

import com.minibox.po.Transportation;
import org.apache.ibatis.annotations.Param;

import java.sql.JDBCType;
import java.util.List;

public interface TransportationMapper {

    boolean insertTransportation(Transportation transportation);

    boolean removeTransportation(int transportationId);

    boolean updateTransportation(Transportation transportation);

    boolean updateTransportationScore(@Param("transportationId") int transportationId,
                                      @Param("score") int score);

    boolean updateTransportationFinishFlag(int transportationId);

    Transportation findTransportationByTransportationId(int transportationId);

    List<Transportation> findUsingTransportationInfosByUserId(int userId);

    List<Transportation> findUsedTransportationInfosByUserId(int userId);

}

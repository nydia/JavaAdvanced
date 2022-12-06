package com.nydia.modules.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 日志Dao
 */
@Mapper
public interface HealthCheckDao extends Mapper {

    @Select(value = "SELECT 1 from dual")
    int healthChecek();

}

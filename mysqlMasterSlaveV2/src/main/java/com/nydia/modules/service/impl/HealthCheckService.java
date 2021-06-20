package com.nydia.modules.service.impl;


import com.nydia.modules.service.IHealthCheckService;
import com.nydia.modules.mapper.HealthCheckDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 日志接口实现
 */
@Service
public class HealthCheckService implements IHealthCheckService {

    @Autowired
    private HealthCheckDao healthCheckDao;

    @Override
    public int healthChecek() {
        int r = healthCheckDao.healthChecek();
        return r;
    }

}

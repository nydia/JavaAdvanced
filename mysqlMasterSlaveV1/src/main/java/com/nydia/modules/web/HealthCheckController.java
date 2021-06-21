package com.nydia.modules.web;

import com.nydia.modules.service.IHealthCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 健康检查Controller
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class HealthCheckController  {

    @Autowired
    private IHealthCheckService healthCheckService;

    @RequestMapping(value = "/healthCheck")
    public Object health() {
        Integer result = healthCheckService.healthChecek();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("resultCode", 1 == result ? "0" : "1");
        return map;
    }

}

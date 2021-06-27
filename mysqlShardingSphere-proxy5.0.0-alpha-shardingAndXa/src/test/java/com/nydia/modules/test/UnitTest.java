package com.nydia.modules.test;

import com.nydia.modules.service.IOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitTest {
    @Autowired
    private IOrderService orderService;

    @Test
    public void testConfig_readwrite_splitting() {

    }
}

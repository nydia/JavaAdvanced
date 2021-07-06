package org.dromara.hmily.demo.dubbo.order;

import com.alibaba.fastjson.JSON;
import org.dromara.hmily.demo.common.transter.dto.TransferDTO;
import org.dromara.hmily.demo.dubbo.order.service.TransferService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitTest {

    @Autowired
    private TransferService transferService;

    /**
     *  用户 A 的美元账户和人民币账户都在 A 库，使用 1 美元兑换 7 人民币
     */
    @Test
    public void transferA() {

        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setOutAmount(new BigDecimal(-1));
        transferDTO.setOutAcctTye("USD");
        transferDTO.setInAmount(new BigDecimal(7));
        transferDTO.setInAcctTye("RMB");
        transferDTO.setUerId("10000");
        System.out.println(transferService.transferA(transferDTO));

    }

    /**
     * 用户 B 的美元账户和人民币账户都在 B 库，使用 7 人民币兑换 1 美元 ;
     */
    @Test
    public void transferB() {

        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setOutAmount(new BigDecimal(-7));
        transferDTO.setOutAcctTye("RMB");
        transferDTO.setInAmount(new BigDecimal(1));
        transferDTO.setInAcctTye("USD");
        transferDTO.setUerId("20000");
        System.out.println(transferService.transferB(transferDTO));

    }

}

package org.dromara.hmily.demo.dubbo.order.service.impl;

import com.alibaba.fastjson.JSON;
import org.dromara.hmily.demo.common.account.api.AccountService;
import org.dromara.hmily.demo.common.account.dto.AccountDTO;
import org.dromara.hmily.demo.common.account.entity.AccountDO;
import org.dromara.hmily.demo.common.transter.dto.TransferDTO;
import org.dromara.hmily.demo.dubbo.order.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 转账Service
 */
@Service
public class TransferServiceImpl implements TransferService{

    @Resource(name = "accountServiceA")
    private AccountService accountServiceA;

    @Resource(name = "accountServiceB")
    private AccountService accountServiceB;

    @Override
    public AccountDO findAcct(TransferDTO transferDTO) {
        AccountDO accountDO_A = accountServiceA.findByUserIdAndAccountType("10000", "RMB");
        AccountDO accountDO_B = accountServiceB.findByUserIdAndAccountType("20000", "USD");
        System.out.println(JSON.toJSONString(accountDO_A));
        System.out.println(JSON.toJSONString(accountDO_B));
        return accountDO_A;
    }

    @SuppressWarnings("Duplicates")
    @Override
    @Transactional
    public boolean transferA(TransferDTO transferDTO) {
        AccountDTO accountDTOOut = AccountDTO.builder()
                .userId(transferDTO.getUerId())
                .accountType(transferDTO.getOutAcctTye())
                .amount(new BigDecimal(transferDTO.getOutAmount().doubleValue() * (-1)))
                .build();
        boolean out = accountServiceA.transfer(accountDTOOut);

        AccountDTO accountDTOIn = AccountDTO.builder()
                .userId(transferDTO.getUerId())
                .accountType(transferDTO.getInAcctTye())
                .amount(new BigDecimal(transferDTO.getInAmount().doubleValue() * (-1)))
                .build();
        boolean in = accountServiceA.transfer(accountDTOIn);
        System.out.println("转出：" + out);
        System.out.println("转入：" + in);
        return Boolean.TRUE;
    }


    @SuppressWarnings("Duplicates")
    @Override
    @Transactional
    public boolean transferB(TransferDTO transferDTO) {
        AccountDTO accountDTOOut = AccountDTO.builder()
                .userId(transferDTO.getUerId())
                .accountType(transferDTO.getOutAcctTye())
                .amount(new BigDecimal(transferDTO.getOutAmount().doubleValue() * (-1)))
                .build();
        boolean out = accountServiceB.transfer(accountDTOOut);

        AccountDTO accountDTOIn = AccountDTO.builder()
                .userId(transferDTO.getUerId())
                .accountType(transferDTO.getInAcctTye())
                .amount(new BigDecimal(transferDTO.getInAmount().doubleValue() * (-1)))
                .build();
        boolean in = accountServiceB.transfer(accountDTOIn);
        System.out.println("转出：" + out);
        System.out.println("转入：" + in);
        return Boolean.TRUE;
    }


}

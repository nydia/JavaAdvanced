/*
 * Copyright 2017-2021 Dromara.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dromara.hmily.demo.dubbo.account.service;

import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.dromara.hmily.demo.common.account.api.AccountService;
import org.dromara.hmily.demo.common.account.dto.AccountDTO;
import org.dromara.hmily.demo.common.account.entity.AccountDO;
import org.dromara.hmily.demo.common.account.mapper.AccountMapper;
import org.dromara.hmily.demo.common.freeze.dto.FreezeDTO;
import org.dromara.hmily.demo.common.freeze.mapper.FreezeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The type Account service.
 *
 * @author nydia
 */
//@Service("accountService")
@Service("accountServiceB")
public class AccountServiceImpl implements AccountService {

    /**
     * logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    /**
     * The Trycount.
     */
    private static AtomicInteger trycount = new AtomicInteger(0);

    /**
     * The Confrim count.
     */
    private static AtomicInteger confrimCount = new AtomicInteger(0);

    private final AccountMapper accountMapper;

    private final FreezeMapper freezeMapper;

    /**
     * Instantiates a new Account service.
     *
     * @param accountMapper the account mapper
     */
    @Autowired(required = false)
    public AccountServiceImpl(final AccountMapper accountMapper,
                              final FreezeMapper freezeMapper) {
        this.accountMapper = accountMapper;
        this.freezeMapper = freezeMapper;
    }

    /**
     * 获取用户账户信息
     *
     * @param userId 用户id
     * @return AccountDO account do
     */
    @Override
    public AccountDO findByUserIdAndAccountType(String userId, String accountType){
        return accountMapper.findByUserIdAndAccountType(userId, accountType);
    }

    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public boolean transfer(AccountDTO accountDTO) {
        int count =  accountMapper.update(accountDTO);
        if (count <= 0) {
            throw new HmilyRuntimeException("账户扣减异常！");
        }

        FreezeDTO freezeDTO = FreezeDTO.builder()
                .userId(accountDTO.getUserId())
                .accountType(accountDTO.getAccountType())
                .freezeAmt(new BigDecimal(accountDTO.getAmount().doubleValue()).abs())
                .build();
        count = freezeMapper.update(freezeDTO);
        if (count <= 0) {
            throw new HmilyRuntimeException("账户扣减异常！");
        }
        return true;
    }

    /**
     * Confirm boolean.
     *
     * @param accountDTO the account dto
     * @return the boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean confirm(AccountDTO accountDTO) {
        LOGGER.info("============dubbo tcc 执行确认付款接口===============");
        FreezeDTO freezeDTO = FreezeDTO.builder()
                .userId(accountDTO.getUserId())
                .accountType(accountDTO.getAccountType())
                .freezeAmt(new BigDecimal(accountDTO.getAmount().doubleValue()).abs())
                .build();
        freezeMapper.confirm(freezeDTO);
        final int i = confrimCount.incrementAndGet();
        LOGGER.info("调用了account confirm " + i + " 次");
        return Boolean.TRUE;
    }

    /**
     * Cancel boolean.
     *
     * @param accountDTO the account dto
     * @return the boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean cancel(AccountDTO accountDTO) {
        LOGGER.info("============ dubbo tcc 执行取消付款接口===============");
        accountMapper.cancel(accountDTO);

        FreezeDTO freezeDTO = FreezeDTO.builder()
                .userId(accountDTO.getUserId())
                .accountType(accountDTO.getAccountType())
                .freezeAmt(new BigDecimal(accountDTO.getAmount().doubleValue()).abs())
                .build();
        freezeMapper.confirm(freezeDTO);

        return Boolean.TRUE;
    }

}

package org.dromara.hmily.demo.dubbo.order.service;

import org.dromara.hmily.demo.common.account.entity.AccountDO;
import org.dromara.hmily.demo.common.transter.dto.TransferDTO;

/**
 * 转账Service
 */
public interface TransferService {

    AccountDO findAcct(TransferDTO transferDTO);

    boolean transferA(TransferDTO transferDTO);

    boolean transferB(TransferDTO transferDTO);

}

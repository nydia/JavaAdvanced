package org.dromara.hmily.demo.common.transter.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 转账参数DTO
 */
@Data
public class TransferDTO {

    /**
     * 账户
     */
    private String uerId;

    /**
     * 转出金额
     */
    private BigDecimal outAmount;

    /**
     * 转出金额
     */
    private BigDecimal inAmount;

    /**
     * 转出账户类型 RMB - 人民币 / USD - 美元
     */
    private String outAcctTye;

    /**
     * 转入账户类型 RMB - 人民币 / USD - 美元
     */
    private String inAcctTye;

    /**
     * 冻结金额
     */
    private BigDecimal freezeAmt;


}

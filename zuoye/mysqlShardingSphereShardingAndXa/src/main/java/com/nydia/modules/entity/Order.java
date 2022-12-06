package com.nydia.modules.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_order")
public class Order implements Serializable{
    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;
    @TableField(value = "order_no")
    private String orderNo;
    @TableField(value = "amount")
    private BigDecimal amount;
    @TableField(value = "status")
    private String status;
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "create_date")
    private Date createDate;

}

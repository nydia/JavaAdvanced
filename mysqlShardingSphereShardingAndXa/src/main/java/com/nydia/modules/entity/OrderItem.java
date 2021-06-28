package com.nydia.modules.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_order_item")
public class OrderItem implements Serializable{
    @TableId(value = "order_item_id", type = IdType.AUTO)
    private Long orderItemId;

    @TableField(value = "order_id")
    private Long orderId;
    @TableField(value = "good_id")
    private Long goodId;
    @TableField(value = "good_name")
    private String goodName;
    @TableField(value = "price")
    private BigDecimal price;
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "create_date")
    private Date createDate;

}

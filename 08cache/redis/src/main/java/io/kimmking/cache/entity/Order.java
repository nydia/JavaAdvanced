package io.kimmking.cache.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: hqlv
 * @Date: 2021/7/17 18:47
 * @Description: 订单
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String orderNo;
    private BigDecimal amt;
    private String status;
    private Long userId;
    private String createDate;

}

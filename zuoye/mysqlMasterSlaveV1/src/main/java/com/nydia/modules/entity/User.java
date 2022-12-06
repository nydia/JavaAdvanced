package com.nydia.modules.entity;

import lombok.*;

/**
 * @Auther: hqlv
 * @Date: 2021/6/20 01:19
 * @Description:
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private Integer userId;
    private String userName;
    private String password;
    private String nickName;
    private String idCard;

}

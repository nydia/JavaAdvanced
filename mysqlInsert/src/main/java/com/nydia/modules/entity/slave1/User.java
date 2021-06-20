package com.nydia.modules.entity.slave1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Auther: hqlv
 * @Date: 2021/6/20 01:19
 * @Description:
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer userId;
    private String userName;
    private String password;
    private String nickName;
    private String idCard;

}

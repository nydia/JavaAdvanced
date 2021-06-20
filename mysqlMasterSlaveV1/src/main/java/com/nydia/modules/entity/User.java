package com.nydia.modules.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @Auther: hqlv
 * @Date: 2021/6/20 01:19
 * @Description:
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long userId;
    private String userName;
    private String password;
    private String nickName;
    private String idCard;

}

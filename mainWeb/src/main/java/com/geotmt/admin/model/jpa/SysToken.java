package com.geotmt.admin.model.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by geo on 2018/10/11. */
@Entity
@Table(name = "t_sys_token")
@Getter
@Setter
public class SysToken implements Serializable {

    private static final long serialVersionUID = 1L;

    //columns START
    @Id
    @Column(name = "token_id", length = 32)
    private String tokenId;// 令牌ID;
    @Column(name = "user_id", length = 20)
    private Long userId;// 用户ID;
    @Column(name = "user_code", length = 20)
    private java.lang.String userCode;// 账号;
    @Column(name = "password", length = 32)
    private java.lang.String password;// 密码;
    @Column(name = "insert_time", length = 14)
    private java.lang.Long insertTime;// 插入时间;
    @Column(name = "invalid_time", length = 14)
    private java.lang.Long invalidTime;// 失效时间;
    @Column(name = "status", length = 1)
    private java.lang.Boolean status;// 令牌状态;
}

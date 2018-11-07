package com.geotmt.db.jpa.entity;

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
    private Long userId;// 用户ID,区分出openId和userId，主要用于SSO设计
    @Column(name = "open_id")
    private String openId ; // openId，即用户在某个子系统的唯一标识
    @Column(name = "user_name", length = 20)
    private String userName;// 账号;
    @Column(name = "password", length = 32)
    private String password;// 密码;
    @Column(name = "insert_time", length = 14)
    private Long insertTime;// 插入时间;
    @Column(name = "invalid_time", length = 14)
    private Long invalidTime;// 失效时间;
    @Column(name = "status", length = 1)
    private Short status;// 令牌状态;

    @Override
    public String toString() {
        return "SysToken{" +
                "tokenId='" + tokenId + '\'' +
                ", userId=" + userId +
                ", openId='" + openId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", insertTime=" + insertTime +
                ", invalidTime=" + invalidTime +
                ", status=" + status +
                '}';
    }
}

package com.geotmt.db.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "t_sys_sso")
@Getter
@Setter
public class SysSso implements Serializable {

    private static final long serialVersionUID = 1L;

    //columns START
    @Id
    @Column(name = "sso_id", length = 20)
    private String ssoId;
    @Column(name = "insert_time", length = 14)
    private Long insertTime;
    @Column(name = "invalid_time", length = 14)
    private Long invalidTime;
    @Column(name = "access_token", length = 40)
    private String accessToken;
    @Column(name = "platform_flag", length = 40)
    private String platformFlag;
    @Column(name = "user_id", length = 20)
    private String userId;
    @Column(name = "user_name", length = 20)
    private String userName;
    @Column(name = "password", length = 32)
    private String password;
    @Column(name = "desc", length = 20)
    private String desc;

}
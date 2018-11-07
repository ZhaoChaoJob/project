package com.geotmt.db.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_sys_user_user_group")
@Getter
@Setter
public class SysUserUserGroup implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "user_id", length = 20)
    private Long userId;// 用户id;
    @Column(name = "group_id", length = 20)
    private Long groupId;// 用户组id;

}

package com.geotmt.admin.model.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_sys_user_group_role")
@Getter
@Setter
public class SysUserGroupRole implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;// 用户组id;
    @Column(name = "role_id")
    private Long roleId;
}

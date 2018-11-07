package com.geotmt.db.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "t_sys_user_group")
@Getter
@Setter
public class SysUserGroup  implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    //columns START
    @Column(name = "group_id", length = 20)
    private Long groupId;// 用户组id;
    @Column(name = "description", length = 200)
    private String description;// 用户组描述;
    @Column(name = "group_name", length = 100)
    private String groupName;// 用户组名称;
    //columns END
    @ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据
    @JoinTable(name = "t_sys_user_group_role", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "group_id") })
    @Fetch(FetchMode.SUBSELECT)
    private List<SysRole> sysRoles;
    @ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据
    @JoinTable(name = "t_sys_user_user_group", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "group_id") })
    @Fetch(FetchMode.SUBSELECT)
    private List<SysUser> sysUsers;
}

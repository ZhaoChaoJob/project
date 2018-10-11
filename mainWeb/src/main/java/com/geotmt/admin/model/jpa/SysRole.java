package com.geotmt.admin.model.jpa;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "t_sys_role")
@Getter
@Setter
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "role_id", unique = true, nullable = false)
	private long roleId;
	
    /**角色名称*/
	@Column(name="role_name",length=100)
	private String roleName;
	
	/**角色描述,UI界面显示使用*/
	@Column(name="description",length=200)
	private String description;

	/**角色 -- 权限关系：多对多关系*/
	@ManyToMany(fetch= FetchType.EAGER)
	@JoinTable(name="t_sys_role_permission",joinColumns={@JoinColumn(name="role_id")},inverseJoinColumns={@JoinColumn(name="permissionId")})
	@Fetch(FetchMode.SUBSELECT)
	private List<SysPermission> permissions;

	/**用户 - 角色关系定义*/
	@ManyToMany(fetch= FetchType.EAGER)
	@JoinTable(name="t_sys_user_group_role",joinColumns={@JoinColumn(name="role_id")},inverseJoinColumns={@JoinColumn(name="group_id")})
	@Fetch(FetchMode.SUBSELECT)
	private List<SysUserGroup> sysUserGroups;

}

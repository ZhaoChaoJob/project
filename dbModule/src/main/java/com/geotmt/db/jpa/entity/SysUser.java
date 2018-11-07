package com.geotmt.db.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

//用户表
@Entity
@Table(name = "t_sys_user")
@Getter
@Setter
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", length = 20)
	private Long userId;// 用户id;
	@Column(name = "create_name_id", length = 20)
	private java.math.BigDecimal createNameId;// 创建人id;
	@Column(name = "create_time", length = 14)
	private Long createTime;// 创建时间;
	@Column(name = "email", length = 50)
	private String email;// 邮箱|登录帐号;
	@Column(name = "last_login_time", length = 14)
	private Long lastLoginTime;// 最后登录时间;
	@Column(name = "last_update_name_id", length = 20)
	private java.math.BigDecimal lastUpdateNameId;// 最后修改人id;
	@Column(name = "last_update_time", length = 14)
	private Long lastUpdateTime;// 最后修改时间;
	@Column(name = "nickname", length = 120)
	private String nickname;// 用户昵称;
	@Column(name = "password", length = 120)
	private String password;// 密码;
	@Column(name = "status", length = 4)
	private Short status;// 用户状态,0:创建未认证, 1:正常状态,2：用户被锁定;
	
//	@ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据
//	@JoinTable(name = "SysUserRole", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = { @JoinColumn(name = "roleId") })
//	private List<SysRole> roleList;// 一个用户具有多个角色

	@ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据
	@JoinTable(name = "t_sys_user_user_group", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "group_id") })
	@Fetch(FetchMode.SUBSELECT)
	private List<SysUserGroup> sysUserGroups;// 一个用户具有多个角色

}

package com.geotmt.admin.model.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
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
	@Column(name = "uid", length = 20)
	private java.math.BigDecimal uid;// 用户id;
	@Column(name = "create_name_id", length = 20)
	private java.math.BigDecimal createNameId;// 创建人id;
	@Column(name = "create_time", length = 14)
	private java.lang.Long createTime;// 创建时间;
	@Column(name = "email", length = 50)
	private java.lang.String email;// 邮箱|登录帐号;
	@Column(name = "last_login_time", length = 14)
	private java.lang.Long lastLoginTime;// 最后登录时间;
	@Column(name = "last_update_name_id", length = 20)
	private java.math.BigDecimal lastUpdateNameId;// 最后修改人id;
	@Column(name = "last_update_time", length = 14)
	private java.lang.Long lastUpdateTime;// 最后修改时间;
	@Column(name = "nickname", length = 120)
	private java.lang.String nickname;// 用户昵称;
	@Column(name = "password", length = 120)
	private java.lang.String password;// 密码;
	@Column(name = "status", length = 4)
	private java.lang.Short status;// 用户状态,0:创建未认证, 1:正常状态,2：用户被锁定;
	
	@ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据
	@JoinTable(name = "SysUserRole", joinColumns = { @JoinColumn(name = "uid") }, inverseJoinColumns = { @JoinColumn(name = "roleId") })
	private List<SysRole> roleList;// 一个用户具有多个角色

}

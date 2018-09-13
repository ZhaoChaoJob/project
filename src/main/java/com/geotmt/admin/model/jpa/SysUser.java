package com.geotmt.admin.model.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

//用户表
@Entity
@Table(name = "sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "uid", unique = true, nullable = false)
	private long uid;
	
    /**用户昵称*/
	@Column(name = "nickname", length = 120)
	private String nickname;
	
    /**邮箱|登录帐号*/
	@Column(name = "email", length = 50)
	private String email;
	
    /**密码*/
	@Column(name = "password", length = 120)
	private String password;
	
    /**最后登录时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastLoginTime", length = 10)
	private Date lastLoginTime;
	
    /**用户状态,0:创建未认证, 1:正常状态,2：用户被锁定*/
	@Column(name = "status", length = 1)
	private byte status;
		
	/**创建人Id*/
	@Column(name = "createNameId", length = 50)
	private long createNameId;
	
	/**创建时间*/
	@Temporal(TemporalType.DATE)
	@Column(name = "createTime", length = 10)
	private Date createTime;
	
	/**最后修改人Id*/
	@Column(name = "lastUpdateNameId", length = 50)
	private long lastUpdateNameId;
	
	/**最后修改时间*/
	@Temporal(TemporalType.DATE)
	@Column(name = "lastUpdateTime", length = 10)
	private Date lastUpdateTime;
	
	@ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据
	@JoinTable(name = "SysUserRole", joinColumns = { @JoinColumn(name = "uid") }, inverseJoinColumns = { @JoinColumn(name = "roleId") })
	private List<SysRole> roleList;// 一个用户具有多个角色

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public long getCreateNameId() {
		return createNameId;
	}

	public void setCreateNameId(long createNameId) {
		this.createNameId = createNameId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getLastUpdateNameId() {
		return lastUpdateNameId;
	}

	public void setLastUpdateNameId(long lastUpdateNameId) {
		this.lastUpdateNameId = lastUpdateNameId;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public List<SysRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<SysRole> roleList) {
		this.roleList = roleList;
	}
	
}

package com.geotmt.admin.model.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "sys_permission")
@Getter
@Setter
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "permissionId", unique = true, nullable = false)
    private long permissionId;//主键.

    private String name;//名称.

    @Column(columnDefinition="enum('menu','button')")
    private String resourceType;//资源类型，[menu|button]

    private String url;//资源路径.

    private String permissionStr; //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view

    private Long parentId; //父编号

    private String parentIds; //父编号列表

    private Boolean available = Boolean.FALSE;

	@ManyToMany(fetch = FetchType.EAGER)// 立即从数据库中进行加载数据
	@JoinTable(name = "SysRolePermission", joinColumns = { @JoinColumn(name = "permissionId") }, inverseJoinColumns = { @JoinColumn(name = "roleId") })
    private List<SysRole> roles;

}

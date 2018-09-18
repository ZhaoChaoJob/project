package com.geotmt.admin.model.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "t_sys_permission")
@Getter
@Setter
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id", length = 20)
    private java.math.BigDecimal permissionId;// 权限id;
    @Column(name = "available", length = 1)
    private java.lang.Boolean available;// available;
    @Column(name = "name", length = 255)
    private java.lang.String name;// 名称;
    @Column(name = "parent_id", length = 20)
    private java.math.BigDecimal parentId;// 父编号;
    @Column(name = "parent_ids", length = 255)
    private java.lang.String parentIds;// 父编号列表;
    @Column(name = "permission_str", length = 255)
    private java.lang.String permissionStr;// 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view;
    @Column(name = "resource_type", length = 6)
    private java.lang.String resourceType;// 资源类型，[menu|button];
    @Column(name = "url", length = 255)
    private java.lang.String url;// 资源路径;

	@ManyToMany(fetch = FetchType.EAGER)// 立即从数据库中进行加载数据
	@JoinTable(name = "SysRolePermission", joinColumns = { @JoinColumn(name = "permissionId") }, inverseJoinColumns = { @JoinColumn(name = "roleId") })
    private List<SysRole> roles;

}

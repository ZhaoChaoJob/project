package com.geotmt.admin.model.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "t_sys_menu")
@Getter
@Setter
public class SysMenu implements Serializable {
    //columns START
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id", length = 20)
    private java.lang.String menuId;// 菜单编号;
    @Column(name = "title", length = 40)
    private java.lang.String title;// 菜单名称;
    @Column(name = "level", length = 2)
    private Integer level;// 级别;
    @Column(name = "parent_id", length = 32)
    private java.lang.String parentId;// 父菜单编号;
    @Column(name = "desc", length = 40)
    private java.lang.String desc;// 菜单描述;
    @Column(name = "permission_id", length = 20)
    private Long permissionId;// 资源ID;
    @Column(name = "icon", length = 20)
    private String icon;// 图标
    @Transient
    private String key;// 资源ID;
    // 子菜单
    @Transient
    private List<SysMenu> children;
    //columns END
}

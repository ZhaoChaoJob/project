package com.geotmt.admin.dao;

import com.geotmt.admin.model.jpa.SysMenu;
import com.geotmt.admin.model.jpa.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface SysUserRepository extends JpaRepository<SysUser, Long>,JpaSpecificationExecutor<SysUser> {

	@Query("from SysUser p where p.nickname =:nickname and p.password =:password")
    List<SysUser> findByName(@Param(value = "nickname") String nickname, @Param(value = "password") String password);
	
	@Query("from SysUser p where p.nickname =:nickname and p.password =:password")
    SysUser findByNameAndPsw(@Param(value = "nickname") String nickname, @Param(value = "password") String password);

    @Query(value = "select m.menu_id as menuId,m.menu_name as menuName,m.level as level,m.parent_id as parentId,m.desc as `desc`,m.permission_id as permissionId,p.url as url " +
            "from t_sys_user_user_group UP " +
            "LEFT JOIN t_sys_user_group g " +
            "on up.user_id=:userId and UP.group_id=g.group_id " +
            "LEFT JOIN t_sys_user_group_role gr " +
            "on g.group_id=gr.group_id " +
            "LEFT JOIN t_sys_role r " +
            "on GR.role_id=r.role_id " +
            "LEFT JOIN t_sys_role_permission rp " +
            "on r.role_id=rp.role_id " +
            "LEFT JOIN t_sys_permission p " +
            "on RP.permission_id=p.permission_id " +
            "LEFT JOIN t_sys_menu m " +
            "on p.permission_id=m.permission_id", nativeQuery = true)
	List<Map<String,Object>> getMenu(@Param(value = "userId")Long userId);

}

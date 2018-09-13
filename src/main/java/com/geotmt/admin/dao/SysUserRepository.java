package com.geotmt.admin.dao;

import com.geotmt.admin.model.jpa.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SysUserRepository extends JpaRepository<SysUser, Long>,JpaSpecificationExecutor<SysUser> {

	@Query("from SysUser p where p.nickname =:nickname and p.password =:password")
    List<SysUser> findByName(@Param(value = "nickname") String nickname, @Param(value = "password") String password);
	
	@Query("from SysUser p where p.nickname =:nickname and p.password =:password")
    SysUser findByNameAndPsw(@Param(value = "nickname") String nickname, @Param(value = "password") String password);
}

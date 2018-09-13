package com.geotmt.admin.dao;


import com.geotmt.admin.model.jpa.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SysPermissionRepository extends JpaRepository<SysPermission, String>,JpaSpecificationExecutor<SysPermission> {

}

package com.geotmt.admin.dao;

import com.geotmt.admin.model.jpa.SysToken;
import com.geotmt.admin.model.jpa.SysUser;
import com.geotmt.admin.model.jpa.TTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * token
 *
 * Created by chao.zhao on 2018/3/13. */
public interface SysTokenRepository extends JpaRepository<SysToken,String> {

//    @Query("from SysToken t where t.tokenId:tokenId ")
//    SysToken findByTokenId(@Param(value = "tokenId") String tokenId);
}

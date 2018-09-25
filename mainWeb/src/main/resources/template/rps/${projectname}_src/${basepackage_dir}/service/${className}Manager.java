<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.yada.mersvc.model.AaaaTest;
import com.yada.mersvc.model.AccountFile;
import com.yada.security.base.BaseDao;
import com.yada.security.base.BaseService;

<#include "/java_imports.include">
@Service
public class ${className}Manager extends BaseService<${className}, String>{

	@Autowired
	private ${className}DaoImpl ${classNameLower}Dao;
	
	protected BaseDao<${className}, String> getBaseDao() {
		return this.${classNameLower}Dao;
	}
}

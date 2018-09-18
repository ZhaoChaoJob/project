<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.dao.impl;

import com.yada.mersvc.model.*;
import com.yada.security.base.BaseDaoImpl;
import org.springframework.stereotype.Component;

<#include "/java_imports.include">
@Component
public class ${className}DaoImpl extends BaseDaoImpl<${className},java.lang.String>{
	

}

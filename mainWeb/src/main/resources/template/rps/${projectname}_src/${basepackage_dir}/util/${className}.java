<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign sqlName = table.sqlName> 



	
	
	
	//columns START
	"SELECT "+
			<#list table.columns as column>
			"XXX_${column.constantName},"+
			</#list>)
//			"VALUES("+
//			<#list table.columns as column>
//			":${column.columnNameLower},"+
//			</#list>)";
//	



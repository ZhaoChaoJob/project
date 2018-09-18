import javax.persistence.*;

<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.model;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

<#include "/java_imports.include">
@Entity
@Table(name = "${table.sqlName}")
@Getter
@Setter
public class ${className} {
	
	//columns START
	<#list table.columns as column>
	@Column(name = "${column.sqlName}", length = ${column.size})
	private ${column.javaType} ${column.columnNameLower};// ${column.columnAlias};
	</#list>
	//columns END
	
	public ${className}(){ }
	

<@generateJavaColumns/>
<@generateJavaOneToMany/>
<@generateJavaManyToOne/>

	public String toString() {
		return new ToStringBuilder(this)
		<#list table.columns as column>
			.append("${column.columnName}",get${column.columnName}())
		</#list>
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
		<#list table.columns as column>
			.append(get${column.columnName}())
		</#list>
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ${className} == false) return false;
		if(this == obj) return true;
		${className} other = (${className})obj;
		return new EqualsBuilder()
			<#list table.columns as column>
			.append(get${column.columnName}(),other.get${column.columnName}())
			</#list>
			.isEquals();
	}
}

	
	

<#macro generateJavaColumns>
	<#list table.columns as column>
		<#if column.isDateTimeColumn>
	/** ${column.columnAlias} */
	public String get${column.columnName}String() {
		return date2String(get${column.columnName}(), FORMAT_${column.constantName});
	}
	/** ${column.columnAlias} */
	public void set${column.columnName}String(String value) {
		set${column.columnName}(string2Date(value, FORMAT_${column.constantName},${column.javaType}.class));
	}
	</#if>
	/** ${column.columnAlias} */
	public void set${column.columnName}(${column.javaType} value) {
		this.${column.columnNameLower} = value;
	}
	/** ${column.columnAlias} */
	public ${column.javaType} get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	</#list>
</#macro>


<#macro generateJavaOneToMany>
	<#list table.exportedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>

	@ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据
	@JoinTable(name = "${fkPojoClass}", joinColumns = { @JoinColumn(name = "${table.idColumn}") }, inverseJoinColumns = { @JoinColumn(name = "${table.idColumn}") })
	private List<${fkPojoClass}> ${fkPojoClassVar}s = new ArrayList<>();
	public void set${fkPojoClass}s(List<${fkPojoClass}> ${fkPojoClassVar}){
		this.${fkPojoClassVar}s = ${fkPojoClassVar};
	}
	
	public List<${fkPojoClass}> get${fkPojoClass}s() {
		return ${fkPojoClassVar}s;
	}
	</#list>
</#macro>

<#macro generateJavaManyToOne>
	<#list table.importedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	
	private ${fkPojoClass} ${fkPojoClassVar};
	
	public void set${fkPojoClass}(${fkPojoClass} ${fkPojoClassVar}){
		this.${fkPojoClassVar} = ${fkPojoClassVar};
	}
	
	public ${fkPojoClass} get${fkPojoClass}() {
		return ${fkPojoClassVar};
	}
	</#list>
</#macro>


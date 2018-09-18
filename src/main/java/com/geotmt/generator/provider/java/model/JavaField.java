package com.geotmt.generator.provider.java.model;

import com.geotmt.generator.util.ActionScriptDataTypesUtils;

import java.lang.reflect.Field;


/**
 * 说明：java文件对象
 * @author badQiu
 *
 */
public class JavaField {
	private Field field;
	private JavaClass clazz;
	
	/**
	 * 说明：构造函数
	 * @param field 
	 * @param clazz
	 */
	public JavaField(Field field, JavaClass clazz) {
		super();
		this.field = field;
		this.clazz = clazz;
	}

	/**
	 * 说明：
	 * @return
	 */
	public String getFieldName() {
		return field.getName();
	}

	public JavaClass getClazz() {
		return clazz;
	}

	public String getJavaType() {
		return field.getType().getName();
	}

	public String getAsType() {
		return ActionScriptDataTypesUtils.getPreferredAsType(getJavaType());
	}

	public boolean getIsDateTimeField() {
		return  getJavaType().equalsIgnoreCase("java.util.Date")
				|| getJavaType().equalsIgnoreCase("java.sql.Date")
				|| getJavaType().equalsIgnoreCase("java.sql.Timestamp")
				|| getJavaType().equalsIgnoreCase("java.sql.Time");
	}
	
	public String toString() {
		return "JavaClass:"+clazz+" JavaField:"+getFieldName();
	}
}

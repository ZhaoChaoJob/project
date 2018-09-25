package com.geotmt.generator.provider.java.model;

import com.geotmt.generator.util.ActionScriptDataTypesUtils;

import java.beans.PropertyDescriptor;


public class JavaProperty {
	PropertyDescriptor propertyDescriptor;
	JavaClass clazz;
	public JavaProperty(PropertyDescriptor pd, JavaClass javaClass) {
		this.propertyDescriptor = pd;
		this.clazz = javaClass;
	}
	
	public String getName() {
		return propertyDescriptor.getName();
	}
	
	public String getJavaType() {
		return propertyDescriptor.getPropertyType().getName();
	}
	
	public String getAsType() {
		return ActionScriptDataTypesUtils.getPreferredAsType(propertyDescriptor.getPropertyType().getName());
	}

	public String toString() {
		return "JavaClass:"+clazz+" JavaProperty:"+getName();
	}
}

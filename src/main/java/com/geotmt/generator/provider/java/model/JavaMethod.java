/**
 * project:pomer
 * 
 * Copyright 2008 [pomer], Inc. All rights reserved.
 * Website: http://www.pomer.org.cn/
 * 
 */
package com.geotmt.generator.provider.java.model;

import com.geotmt.generator.util.StringHelper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 说明：类方法对象
 * @author badqiu,Linlin Yu
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class JavaMethod {
	private JavaClass clazz;
	private Method method;
	
	
	public JavaMethod(Method method, JavaClass clazz) {
		super();
		this.method = method;
		this.clazz = clazz;
	}

	public JavaClass getClazz() {
		return clazz;
	}

	public String getMethodName() {
		return method.getName();
	}

	public JavaClass getReturnType() {
		return new JavaClass(method.getReturnType());
	}

	public List getParameters() {
		Class[] parameters  = method.getParameterTypes();
		List results = new ArrayList();
		for(int i = 0; i < parameters.length; i++) {
			results.add(new MethodParameter(i+1,new JavaClass(parameters[i])));
		}
		return results;
	}

	public String getMethodNameUpper() {
		return StringHelper.capitalize(getMethodName());
	}
	
	public String toString() {
		return "JavaClass:"+clazz+" JavaMethod:"+getMethodName();
	}
}

package com.geotmt.generator.provider.java.model;

import com.geotmt.generator.util.ActionScriptDataTypesUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * 说明：获取类的信息
 * @author badQiu
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class JavaClass {
	
	private Class clazz;//类
	
	/**
	 * 说明：含参构造
	 * @param clazz
	 */
	public JavaClass(Class clazz) {
		this.clazz = clazz;
	}
	
	/**
	 * 说明：获取类名
	 * @return 类名
	 */
	public String getClassName() {
		return this.clazz.getSimpleName();
	}
	
	/**
	 * 说明：获取包名
	 * @return 包名
	 */
	public String getPackageName() {
		return clazz.getPackage().getName();
	}
	
	/**
	 * 说明：获取父类名
	 * @return 父类名
	 */
	public String getSuperclassName() {
		return clazz.getSuperclass() != null ? clazz.getSuperclass().getName() : null;
	}
	
	/**
	 * 说明:获取类的方法LIST
	 * @return 类的方法LIST
	 */
	public List<JavaMethod> getMethods() {
		Method[] methods = clazz.getDeclaredMethods();
		List result = new ArrayList();
		for(Method m : methods) {
			result.add(new JavaMethod(m,this));
		}
		return result;
	}
	
	/**
	 * 说明：获取配置文件的信息
	 * @return
	 * @throws Exception
	 */
	public List getProperties() throws Exception {
		List result = new ArrayList();
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
		for(PropertyDescriptor pd : pds) {
			result.add(new JavaProperty(pd,this));
		}
		return result;
	}
	
	public List<JavaField> getFields() {
		Field[] fields = clazz.getDeclaredFields();
		List result = new ArrayList();
		for(Field f : fields) {
			result.add(new JavaField(f,this));
		}
		return result;
	}
	
	/**
	 * 说明：获取包的路径
	 * @return 包的路径
	 */
	public String getPackagePath(){
		return getPackageName().replace(".", "/");
	}
	
	/**
	 * 说明：获取父包的名称
	 * @return 父包的名称
	 */
	public String getParentPackageName() {
		return getPackageName().substring(0,getPackageName().lastIndexOf("."));
	}

	/**
	 * 说明：获取父包的路径
	 * @return 父包的路径
	 */
	public String getParentPackagePath() {
		return getParentPackageName().replace(".", "/");
	}
	
	public String getAsType() {
		return ActionScriptDataTypesUtils.getPreferredAsType(clazz.getName());
	}
	
	public String getJavaType() {
		return clazz.getName();
	}
	
	public String toString() {
		return "JavaClass:"+clazz.getName();
	}
}

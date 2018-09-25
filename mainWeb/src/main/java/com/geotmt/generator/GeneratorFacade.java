package com.geotmt.generator;


import com.geotmt.generator.provider.db.DbTableFactory;
import com.geotmt.generator.provider.db.DbTableGeneratorModelProvider;
import com.geotmt.generator.provider.db.model.Table;
import com.geotmt.generator.provider.java.JavaClassGeneratorModelProvider;
import com.geotmt.generator.provider.java.model.JavaClass;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 说明：生成器
 * @author badQiu
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class GeneratorFacade {
	
	//空构造函数
	public GeneratorFacade() {	}
	
	/**
	 * 说明：打印所有表名
	 */
	public void printAllTableNames() throws Exception {
		List tables = DbTableFactory.getInstance().getAllTables();
		System.out.println("\n----打印所有表名称  BEGIN----");
		for(int i = 0; i < tables.size(); i++ ) {
			String sqlName = ((Table)tables.get(i)).getSqlName();
			System.out.println("g.generateTable(\""+sqlName+"\");");
		}
		System.out.println("----打印所有表名称 END----");
	}
	
	/**
	 * 说明：自动搜索数据库中的所有表并生成文件
	 * 
	 * @throws Exception
	 */
	public void generateByAllTable() throws Exception {
		List<Table> tables = DbTableFactory.getInstance().getAllTables();
		for(int i = 0; i < tables.size(); i++ ) {
			generateByTable(tables.get(i).getSqlName());
		}
	}
	
	/**
	 *  说明：通过数据库表生成文件
	 * @param tableName 表名
	 * @throws Exception
	 */
	public void generateByTable(String tableName) throws Exception {
		Generator g = createGeneratorForDbTable();
		Table table = DbTableFactory.getInstance().getTable(tableName);
		g.generateByModelProvider(new DbTableGeneratorModelProvider(table));
	}
	
	/**
	 * 说明：通过数据库表生成文件,并可以自定义类名,通过数据库表生成文件,注意: oracle 需要指定schema及注意表名的大小写.
	 * @param tableName 表明
	 * @param className 类名
	 * @throws Exception
	 */
	public void generateByTable(String tableName,String className) throws Exception {
		Generator g = createGeneratorForDbTable();
		Table table = DbTableFactory.getInstance().getTable(tableName);
		table.setClassName(className);
		g.generateByModelProvider(new DbTableGeneratorModelProvider(table));
	}
	
	/**
	 * 说明：通过数据库视图生成文件,并可以自定义类名,最后参数为视图主键
	 * @param tableName 表名
	 * @param className 类名
	 * @param viewPKColumnID 主键
	 * @throws Exception
	 */
	public void generateByTable(String tableName,String className,String viewPKColumnID) throws Exception {
		Generator g = createGeneratorForDbTable();
		Table table = DbTableFactory.getInstance().getTable(tableName,viewPKColumnID);
	
		table.setClassName(className);
		g.generateByModelProvider(new DbTableGeneratorModelProvider(table));
	}
	
	public void generateByClass(Class clazz) throws Exception {
		Generator g = createGeneratorForJavaClass();
		g.generateByModelProvider(new JavaClassGeneratorModelProvider(new JavaClass(clazz)));
	}
	
	/**
	 * 说明：清理生成过的文件
	 * 
	 * @throws IOException IO异常
	 */
	public void clean() throws IOException {
		Generator g = createGeneratorForDbTable();
		g.clean();
	}
	
	/**
	 * 说明：为数据库创建模板
	 * 
	 * @return 模板
	 */
	public Generator createGeneratorForDbTable() {
		Generator g = new Generator();
//		GeneratorFacade.class.getClassLoader().getResource("generator.properties").getPath();
		g.setTemplateRootDir(new File(GeneratorFacade.class.getClassLoader().getResource("generator.properties").getPath()+"/../template").getAbsoluteFile());
		g.setOutRootDir(GeneratorProperties.getRequiredProperty("outRoot"));
		return g;
	}
	
	/**
	 * 说明：根据java类创建一个模板
	 * 
	 * @return 模板
	 */
	private Generator createGeneratorForJavaClass() {
		Generator g = new Generator();
		g.setTemplateRootDir(new File(GeneratorFacade.class.getClassLoader().getResource("generator.properties").getPath()+"/../template/javaclass").getAbsoluteFile());
		g.setOutRootDir(GeneratorProperties.getRequiredProperty("outRoot"));
		return g;
	}
}

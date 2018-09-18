package com.geotmt.generator;

/**
 * 
 * @author badQiu
 *
 * 如果想独立出去成为一个单独的模块，请自行加入以下jar包
 * commons-beanutils.jar
 * commons-cli-1.0.jar
 * commons-io.jar
 * commons-logging-1.1.jar
 * freemarker.jar
 * mysql-connector-java-5.0.5-bin.jar
 * ojdbc14.jar -- 这里尽量使用ojdbc6.jar ojdbc14.jar 指的是支持jdk1.0 - jdk1.4
 * sqljdbc.jar
 *
 */


public class GeneratorMain {
	/**
	 * 请直接修改以下代码调用不同的方法以执行相关生成任务.
	 */
	public static void main(String[] args) throws Exception {
		GeneratorFacade g = new GeneratorFacade();
	//  g.printAllTableNames();				//打印数据库中的表名称
		g.clean();							//删除生成器的输出目录
//		g.generateByTable("T_C_TASK_INFO","TaskInfo");	//通过数据库表生成文件,并可以自定义类名,通过数据库表生成文件,注意: oracle 需要指定schema及注意表名的大小写.
//		g.generateByTable("T_B_ORG","Org");
//		g.generateByTable("t_r_warn_log".toUpperCase(),"WarnLog");
//		g.generateByTable("T_C_DZSQL","DzSql");
//		g.generateByTable("T_C_EXCUTE_LOG_INFO","ExcuteLogInfo");
//		g.generateByTable("t_c_ftp_info".toUpperCase(),"FtpInfo");
//		g.generateByTable("T_C_RES_COLUMN_INFO".toUpperCase(),"ResColumnInfo");
//		g.generateByTable("t_c_res_file_info".toUpperCase(),"ResFileInfo");
//		g.generateByTable("t_c_task_file".toUpperCase(),"TaskFile");
//		g.generateByTable("t_l_eiss_acc_error_ls".toUpperCase(),"EissAccErrorls");
//		g.generateByTable("t_l_ei_acc_balance_sum".toUpperCase(),"EiAccBalanceSum");
//		g.generateByTable("t_l_ei_acc_error_ls".toUpperCase(),"EiAccErrorLs");
//		g.generateByTable("T_B_SYS_CONFIG","SysConfig"); 
		g.generateByTable("t_sys_user".toUpperCase(),"SysUser");
//		g.generateByTable("t_l_eiss_acc_balance_sum".toUpperCase(),"EissAccBlanceSum"); 
	//  g.generateByTable("V_bankeq_PUNISH_ALL_JSP","TbankeqPunishQuery","ID");//通过数据库视图生成文件,并可以自定义类名,最后参数为视图主键
	//	g.generateByAllTable();				//自动搜索数据库中的所有表并生成文件
    //  g.generateByClass(Blog.class);
	//  g.printAllTableNames() ;//这个方法用不了，因为我不知到catalog这个属性怎么配置
		
		//打开文件夹
		String path = GeneratorProperties.getProperty("outRoot");
		Runtime.getRuntime().exec("cmd.exe /c start " + path);
	}
}

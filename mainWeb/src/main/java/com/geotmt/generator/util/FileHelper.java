package com.geotmt.generator.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * 说明：文件处理
 * @author badQiu
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class FileHelper {

	/**
	 * 说明：获取相对路径
	 * @param baseDir
	 * @param file
	 * @return
	 */
	public static String getRelativePath(File baseDir,File file) {
		if(baseDir.equals(file))
			return "";
		if(baseDir.getParentFile() == null)
			return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length());
		return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length()+1);
	}
	
	/**
	 * 说明：获取文件列表
	 * @param file
	 * @param collector
	 * @throws IOException
	 */
	public static void listFiles(File file,List collector) throws IOException {
		collector.add(file);
		if((!file.isHidden() && file.isDirectory()) && !isIgnoreFile(file)) {
			File[] subFiles = file.listFiles();
			for(int i = 0; i < subFiles.length; i++) {
				listFiles(subFiles[i],collector);
			}
		}
	}
	
	/**
	 * 说明：忽略掉的以指定文件后缀名的文件
	 * @param file File对象
	 * @return 是否忽略掉
	 */
	private static boolean isIgnoreFile(File file) {
		List ignoreList = new ArrayList();
		ignoreList.add(".svn");
		ignoreList.add("CVS");
		ignoreList.add(".cvsignore");
		ignoreList.add("SCCS");
		ignoreList.add("vssver.scc");
		ignoreList.add(".DS_Store");
		for(int i = 0; i < ignoreList.size(); i++) {
			if(file.getName().equals(ignoreList.get(i))) {
				return true;
			}
		}
		return false;
	}
}

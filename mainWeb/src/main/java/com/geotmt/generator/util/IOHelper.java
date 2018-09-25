package com.geotmt.generator.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
/**
 * 说明：IO处理，包含 文本的复制、读取文本、文本存储
 * 
 * @author badQiu
 */
public class IOHelper {

	/**
	 * 说明：文本的复制
	 * @param in java.io.Reader
	 * @param out java.io.Writer
	 * @throws IOException 异常
	 */
	public static void copy(Reader in,Writer out) throws IOException {
		int c = -1;
		while((c = in.read()) != -1) {
			out.write(c);
		}
	}
	
	/**
	 * 说明：读取文本，并返回文本内容
	 * @param file File对象
	 * @return 文本内容
	 * @throws IOException IO异常
	 */
	public static String readFile(File file) throws IOException {
		Reader in = new FileReader(file);
		StringWriter out = new StringWriter();
		copy(in,out);
		return out.toString();
	}
	
	/**
	 * 说明：将字符串保存到文本中
	 * @param file File对象
	 * @param content 字符串内容
	 * @throws IOException IO异常
	 */
	public static void saveFile(File file,String content) throws IOException {
		Writer writer = new FileWriter(file);
		writer.write(content);
		writer.close();
	}
	
}

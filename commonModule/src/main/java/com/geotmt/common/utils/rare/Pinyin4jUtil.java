package com.geotmt.common.utils.rare;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用于把汉字转换为拼音
 */
public class Pinyin4jUtil {

    private static final String PY = "1";
    private static final String PINYIN = "2";
    private static final Pattern P = Pattern.compile("[\u4e00-\u9fa5]");

    /**
     * 是否为中文字符
     * @param s
     * @return
     */
    public static boolean isChineseChar(String s){
        boolean temp = false;
        Matcher m = P.matcher(s);
        if(m.find()){
            temp =  true;
        }
        return temp;
    }

    /**
     * 判断是否含有中文
     * @param py
     * @return
     */
    public static Boolean isContainChinese(String py) {
        Matcher m = P.matcher(py);
        if (m.find()) {
            return true;
        }
        return false;
    }

    private static  Map<String,String> getPinyinMap(String chines,String type){
        StringBuffer pinyin = null;
        StringBuffer py = null;
        if (type == PINYIN) {
            pinyin = new StringBuffer();
        }else if (type == PY){
            py = new StringBuffer();
        }else{
            pinyin = new StringBuffer();
            py = new StringBuffer();
        }
        char[] chinesChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);//小写
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//无声调符号
//        Set set  = new HashSet();//用于检验简拼多音字
        for(char c :chinesChar){
//            set.clear();
            if((c>=48 && c<=57) || ((c>=65 && c<=90))){//数字0-9 A-Z
                if (pinyin !=null){
                    pinyin.append(c);
                }
                if (py != null){
                    py.append(c);
                }
            }else if ((c>=97 && c<=122)){//a-z
                c = (char)(c-32);
                if (pinyin !=null){
                    pinyin.append(c);
                }
                if (py != null){
                    py.append(c);
                }
            }else if(c>128){//汉语
                try {
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(c,defaultFormat);
                    if(strs.length >0){
                        if (pinyin !=null){
                            pinyin.append(strs[0]);
                        }
                        if (py != null){
                            py.append(strs[0].charAt(0));
                        }
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }
        }
        Map<String,String> map = new HashMap<String,String>();
        if (pinyin !=null){
            map.put("pinyin",pinyin.toString());
        }
        if (py !=null){
            map.put("py",py.toString());
        }
        return map;
    }

    /**
     * 获取简拼
     * @param chines
     * @return
     */
    public static String getPy(String chines){
        return getPinyinMap(chines,PY).get("py");
    }

    /**
     * 获取全拼
     * @param chines
     * @return
     */
    public static  String getPinyin(String chines){
        return getPinyinMap(chines,PINYIN).get("pinyin");
    }

    /**
     * 获取拼音及简拼
     * 过滤所有非数字和中文的其他字符
     * 多音字取第一个并给出标识
     * @param chines
     * @return  [pinyin:拼音，py:简拼]
     */
    public static  Map<String,String> getPinyinMap(String chines){
        return getPinyinMap(chines,null);
    }

    /**
     * 汉语转换为拼音首字母，英文字符转大写不变，特殊字符丢失，多音字取第一个拼音
     * 生成格式如：长沙市长 pinyin ：zhangshashichang,changshashichang,zhangshashizhang,changshashizhang
     * @param chines 要转换的汉语
     * @return
     */
    public static String coverterSpell(String chines){
        StringBuffer pinyin = new StringBuffer();
        char[] chinesChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);//大写
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//无声调符号
        for(char c :chinesChar){
            if((c>=48 && c<=57) || ((c>=65 && c<=90))){//数字0-9 A-Z
                pinyin.append(c);
            }else if ((c>=97 && c<=122)){//转大写
                c = (char)(c-32);
                pinyin.append(c);
            }else if(c>128){
                try {
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(c,defaultFormat);
                    if (strs !=null && strs.length>0){
                        for (String str : strs){
                            pinyin.append(str).append(",");
                        }
                        pinyin.deleteCharAt(pinyin.length()-1);
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }
            pinyin.append(" ");
        }
        return parseTheChineseByObject(discountTheChinese(pinyin.toString()));
    }

    /**
     * 去除多音字重复数据
     *
     * @param theStr
     * @return
     */
    private static List<Map<String, Integer>> discountTheChinese(String theStr) {
        // 去除重复拼音后的拼音列表
        List<Map<String, Integer>> mapList = new ArrayList<Map<String, Integer>>();
        // 用于处理每个字的多音字，去掉重复
        Map<String, Integer> onlyOne = null;
        String[] firsts = theStr.split(" ");
        // 读出每个汉字的拼音
        for (String str : firsts) {
            onlyOne = new Hashtable<String, Integer>();
            String[] china = str.split(",");
            // 多音字处理
            for (String s : china) {
                Integer count = onlyOne.get(s);
                if (count == null) {
                    onlyOne.put(s, new Integer(1));
                } else {
                    onlyOne.remove(s);
                    count++;
                    onlyOne.put(s, count);
                }
            }
            mapList.add(onlyOne);
        }
        return mapList;
    }

    /**
     * 解析并组合拼音，对象合并方案(推荐使用)
     *
     * @return
     */
    private static String parseTheChineseByObject(
            List<Map<String, Integer>> list) {
        Map<String, Integer> first = null; // 用于统计每一次,集合组合数据
        // 遍历每一组集合
        for (int i = 0; i < list.size(); i++) {
            // 每一组集合与上一次组合的Map
            Map<String, Integer> temp = new Hashtable<String, Integer>();
            // 第一次循环，first为空
            if (first != null) {
                // 取出上次组合与此次集合的字符，并保存
                for (String s : first.keySet()) {
                    for (String s1 : list.get(i).keySet()) {
                        String str = s + s1;
                        temp.put(str, 1);
                    }
                }
                // 清理上一次组合数据
                if (temp != null && temp.size() > 0) {
                    first.clear();
                }
            } else {
                for (String s : list.get(i).keySet()) {
                    String str = s;
                    temp.put(str, 1);
                }
            }
            // 保存组合数据以便下次循环使用
            if (temp != null && temp.size() > 0) {
                first = temp;
            }
        }
        String returnStr = "";
        if (first != null) {
            if(first.size()>1){//多音字
                return "";
            }
            // 遍历取出组合字符串
            for (String str : first.keySet()) {
                returnStr += (str + ",");
            }
        }
        if (returnStr.length() > 0) {
            returnStr = returnStr.substring(0, returnStr.length() - 1);
        }
        return returnStr;
    }

    public static void main(String[] args) {
        String str = getPinyin("中国");
        String str1 = getPy("中国");
        System.out.println(str);
        System.out.println(str1);
    }

}

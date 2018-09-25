package com.geotmt.common.utils.encrypt;

import java.security.MessageDigest;

/**
 * MD5加密组件
 */
public class MD5Util {

    private static final String hexDigIts[] = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

    /**
     * MD5加密
     * @param origin 字符
     * @return 密文
     */
    public static String MD5Encode(String origin){
        return MD5Encode(origin,"utf8");
    }

    /**
     * MD5加密
     * @param origin 字符
     * @param charsetname 编码
     * @return 密文
     */
    public static String MD5Encode(String origin, String charsetname){
        try{

            MessageDigest md = MessageDigest.getInstance("MD5");
            if(null == charsetname || "".equals(charsetname)){
                origin = byteArrayToHexString(md.digest(origin.getBytes()));
            }else{
                origin = byteArrayToHexString(md.digest(origin.getBytes(charsetname)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return origin;
    }


    private static String byteArrayToHexString(byte b[]){
        StringBuffer resultSb;
        resultSb = new StringBuffer();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b){
        int n = b;
        if(n < 0){
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigIts[d1] + hexDigIts[d2];
    }

//    public static void main(String[] args) {
//        MD5Util md5 = new MD5Util();
//        System.out.println(md5.MD5Encode("加密"));
//    }
}
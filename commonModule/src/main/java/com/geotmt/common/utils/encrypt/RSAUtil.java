package com.geotmt.common.utils.encrypt;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA加密工具类
 * 使用PKCS1_PADDING填充，密钥长度1024
 * 加解密结果在这里测试通过：http://tool.chacuo.net/cryptrsaprikey
 * 参考：http://www.daxiblog.com/2018/05/26/%E9%80%9A%E7%94%A8%E7%9A%84java-rsa%E5%8A%A0%E5%AF%86%E5%B7%A5%E5%85%B7%E7%B1%BB%EF%BC%8C%E5%8F%AF%E5%9C%A8%E7%BA%BF%E9%AA%8C%E8%AF%81%E9%80%9A%E8%BF%87/
 * 注意加密内容的编码要一致，统一UTF-8比较好
 * @author daxi
 */
public class RSAUtil {
    private static final String KEY_ALGORTHM="RSA";
    private static final String SIGNATURE_ALGORITHM="MD5withRSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";//公钥
    private static final String PRIVATE_KEY = "RSAPrivateKey";//私钥

    /**
     * 初始化密钥
     * RSA加密解密的实现，需要有一对公私密钥，公私密钥的初始化如下
     * 非对称加密一般都用于加密对称加密算法的密钥，而不是直接加密内容
     * @return Map
     * @throws Exception 异常
     */
    private static Map<String,Object> initKey()throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORTHM);
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        //公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //私钥
        RSAPrivateKey privateKey =  (RSAPrivateKey) keyPair.getPrivate();

        Map<String,Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);

        return keyMap;
    }

    /**
     * 取得公钥，并转化为String类型
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)throws Exception{
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Coder.encryptBASE64(key.getEncoded());
    }

    /**
     * 取得私钥，并转化为String类型
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception{
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Coder.encryptBASE64(key.getEncoded());
    }

    /**
     * 用私钥加密
     * @param data	加密数据
     * @param key	密钥
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data,String key)throws Exception{
        //解密密钥
        byte[] keyBytes = Coder.decryptBASE64(key);
        //取私钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        //对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 用私钥解密 * @param data 	加密数据
     * @param key	密钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data,String key)throws Exception{
        //对私钥解密
        byte[] keyBytes = Coder.decryptBASE64(key);

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 用公钥加密
     * @param data	加密数据
     * @param key	密钥
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data,String key)throws Exception{
        //对公钥解密
        byte[] keyBytes = Coder.decryptBASE64(key);
        //取公钥
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        //对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 用公钥解密
     * @param data	加密数据
     * @param key	密钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data,String key)throws Exception{
        //对私钥解密
        byte[] keyBytes = Coder.decryptBASE64(key);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        //对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }


    /**************************************************************************
     * 通过RSA加密解密算法，我们可以实现数字签名的功能。我们可以用私钥对信息生
     * 成数字签名，再用公钥来校验数字签名，当然也可以反过来公钥签名，私钥校验。
     * *************************************************************************/

    /**
     *	用私钥对信息生成数字签名
     * @param data	//加密数据
     * @param privateKey	//私钥
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data,String privateKey)throws Exception{
        //解密私钥
        byte[] keyBytes = Coder.decryptBASE64(privateKey);
        //构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        //指定加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        //取私钥匙对象
        PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey2);
        signature.update(data);

        return Coder.encryptBASE64(signature.sign());
    }

    /**
     * 校验数字签名
     * @param data	加密数据
     * @param publicKey	公钥
     * @param sign	数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data,String publicKey,String sign)throws Exception{
        //解密公钥
        byte[] keyBytes = Coder.decryptBASE64(publicKey);
        //构造X509EncodedKeySpec对象
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        //指定加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        //取公钥匙对象
        PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey2);
        signature.update(data);
        //验证签名是否正常
        return signature.verify(Coder.decryptBASE64(sign));

    }

//    public static void main(String[] args) throws Exception {
//        // 生产密钥
//        Map<String, Object> keys = RSAUtil.initKey();
//
//        // 打印私钥
//        System.out.println("↓↓↓↓↓the following is private key↓↓↓↓↓");
//        System.out.println("-----BEGIN PRIVATE KEY-----");
//        System.out.println(RSAUtil.getPrivateKey(keys));
//        System.out.println("-----END PRIVATE KEY-----");
//        System.out.println("↑↑↑↑↑the above is private key↑↑↑↑↑");
//
//        System.out.println();
//
//        // 打印公钥
//        System.out.println("↓↓↓↓↓the following is public key↓↓↓↓↓");
//        System.out.println("-----BEGIN PUBLIC KEY-----");
//        System.out.println(RSAUtil.getPublicKey(keys));
//        System.out.println("-----END PUBLIC KEY-----");
//        System.out.println("↑↑↑↑↑the above is public key↑↑↑↑↑");
//
//        // 加密样例：
////        File file1 = new File("E:\\pub-key.txt");
////        String pub = IOUtils.toString(new FileInputStream(file1));
//        String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxG9VEFQ5ar94W4uiv+OGwLbql24fOAH5MZkJi\n" +
//                "nv48zxLjdaIQEaL/pVUfgj8IGtaHL23FKeVXRzVUVD48zC2RcVtCyznDnHM1HZhx57OaYmWUBOgG\n" +
//                "Dica56Yj05zbCz9MNCBjluzxWdBS41OTg6EPb/mj7o+AMojQ6BhazFXlTQIDAQAB";
//        pub = pub.replace("-----BEGIN PUBLIC KEY-----" + IOUtils.LINE_SEPARATOR, "").replace("-----END PUBLIC KEY-----", "");
//        byte[] helloE = RSAUtil.encryptByPublicKey("hello".getBytes("UTF-8"), pub);
//        String helloBase64 = Coder.encryptBASE64(helloE);
//        System.out.println(helloBase64);
//
//
////        // 解密样例
//        String naughty = "DuuB+ZcqSMTL2PVzOIaHtLDQ5t0r+S2twsAdNt7GNjD1AGVPpiiQz/fVfVJa9+j3IeWzAKTZ/cRyLTH8Wv5JTD6PTMWwiBvg+eGoQZzTi/b34U/Aiv/DovIzm8uMTM2iWk1Vz7KUJK0NMnlKIiviJneCyUVCMFLwT2OPH1QBxq4=";
//        byte[] naughtyBase64 = Coder.decryptBASE64(helloBase64);
////        File file = new File("E:\\pri-key.txt");
////        String priKey = IOUtils.toString(new FileInputStream(file));
//        String priKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALEb1UQVDlqv3hbi6K/44bAtuqXb\n" +
//                "h84AfkxmQmKe/jzPEuN1ohARov+lVR+CPwga1ocvbcUp5VdHNVRUPjzMLZFxW0LLOcOcczUdmHHn\n" +
//                "s5piZZQE6AYOJxrnpiPTnNsLP0w0IGOW7PFZ0FLjU5ODoQ9v+aPuj4AyiNDoGFrMVeVNAgMBAAEC\n" +
//                "gYEApFsh9IZdxcbDIRX9vaAHQMmPFm+9fWxzyE51eLP3V1MLQk5d0O+tBfTWI+FowQq23ski4v9Y\n" +
//                "N0B7uwBaqW0JMwpWBqJaLY9KwqEp8K/RRNa6YfmzQgV7qGicYaSEda6yimVF6tXGJU9EY3JdqGxz\n" +
//                "ZpeHr/8qDvk4+xZPAwWZNgECQQD2HFOGz/8wY5bketqMKuhRFmXFyGmq6/+ZknMFzjzwuUQk1uUo\n" +
//                "pzFWTf7MMglvkKCsRPZIQYuvqahg3JZnszBBAkEAuDmz32mKU+6b9XYuLVVFsgxTblaGvYnDAGdp\n" +
//                "gwQkV1JLefDFLFezyLTrx/3s7+PvqmGuhYW8ZWgtcHPSHN/yDQJACg2MJ+SVIu/eeQS/qpwSE0Xb\n" +
//                "8GFIV6/+J9LAmgsbmCWpL+wLPkbeEA1ti/+7PPBmb8L9YGQ1BX7jntlD14aNQQJBAIFh7STrz32Q\n" +
//                "57FllZq6957LL1EfrbOx6+T9u8mLACbfoih64094LMi535nuiRgVgj2sKtCVkzG5D6iZfyhOU4UC\n" +
//                "QBbgPS9BBIVgTRZ7ZBlPtSgK8IeZeau60SOkweF4x1+VqyfRe6AcyoQtiO9fiHUmFLkcNZmKj1ER\n" +
//                "omqH7/Xn9Xg=";
//        priKey = priKey.replace("-----BEGIN PRIVATE KEY-----" + IOUtils.LINE_SEPARATOR, "").replace("-----END PRIVATE KEY-----", "");
//        String decryptedNaughty = new String(RSAUtil.decryptByPrivateKey(naughtyBase64, priKey));
//        System.out.println(decryptedNaughty);
//    }
}
package com.geotmt.common.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;

/**
 * Created by geo on 2018/10/15.
 * https://blog.csdn.net/cjm1103/article/details/71171842
 */
public class ValidateCodeUtil {
    // 图片的宽度。
    private int width = 160;
    // 图片的高度。
    private int height = 40;
    // 验证码干扰线数
    private int lineCount = 150;
    // 验证码
    private String code = null;
    private Integer codeNum;
    // 验证码图片Buffer
    private BufferedImage buffImg = null;

    // 验证码范围,去掉0(数字)和O(拼音)容易混淆的(小写的1和L也可以去掉,大写不用了)
    private char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * 默认构造函数,设置默认参数
     */
    public ValidateCodeUtil() {

    }

    /**
     * @param width  图片宽
     * @param height 图片高
     */
    public ValidateCodeUtil(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * @param width     图片宽
     * @param height    图片高
     * @param lineCount 干扰线条数
     */
    public ValidateCodeUtil(int width, int height, int lineCount) {
        this.width = width;
        this.height = height;
        this.lineCount = lineCount;
    }

    public String[] createRandomCode(Integer num){
        String[] words = new String[num];
        Random random = new Random();
        for (int i = 0; i < num; i++) {
            words[i] = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
        }
        createCode(words);
        return words;
    }

    /**
     * 生产操作符运算验证码
     *
     * @return 运算结果
     */
    public Integer createOperationCode(){
        Integer result;
        String[] oper = new String[]{"+","-","*"};
        Integer[] num = new Integer[]{0,1,2,3,4,5,6,7,8,9};
        Random random = new Random();
        int a=random.nextInt(10);// 返回[0,10)集合中的整数，注意不包括10
        int b=random.nextInt(3);// 返回[0,10)集合中的整数，注意不包括10
        int c=random.nextInt(10);// 返回[0,10)集合中的整数，注意不包括10

        if(b==0){
            result = num[a] + num[c];
        }else if(b==1){
            result = num[a] - num[c];
        }else {
            result = num[a] * num[c];
        }

        String[] words = new String[]{num[a].toString(),oper[b],num[c].toString(),"="};
        createCode(words);
        codeNum = result;
        return result;
    }

    public void createCode(String[] words) {
        int x = 0, fontHeight = 0, codeY = 0;
        int red = 0, green = 0, blue = 0;

        x = width / (words.length + 2);//每个字符的宽度(左右各空出一个字符)
        fontHeight = height - 2;//字体的高度
        codeY = height - 4;

        // 图像buffer
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        // 生成随机数
        Random random = new Random();
        // 将图像填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 创建字体,可以修改为其它的
        Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
//        Font font = new Font("Times New Roman", Font.ROMAN_BASELINE, fontHeight);
        g.setFont(font);

        for (int i = 0; i < lineCount; i++) {
            // 设置随机开始和结束坐标
            int xs = random.nextInt(width);//x坐标开始
            int ys = random.nextInt(height);//y坐标开始
            int xe = xs + random.nextInt(width / 8);//x坐标结束
            int ye = ys + random.nextInt(height / 8);//y坐标结束

            // 产生随机的颜色值，让输出的每个干扰线的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawLine(xs, ys, xe, ye);
        }

        // randomCode记录随机产生的验证码
        StringBuffer randomCode = new StringBuffer();
        for (int i = 0; i < words.length; i++) {
            // 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawString(words[i], (i + 1) * x, codeY);
            randomCode.append(words[i]);
        }
        // 将四位数字的验证码保存到Session中。
        code = randomCode.toString();
    }

    public void write(String path) throws IOException {
        OutputStream sos = new FileOutputStream(path);
        ImageIO.write(buffImg, "png", sos);
        sos.close();
    }

    public BufferedImage getBuffImg() {
        return buffImg;
    }

    public String getCode() {
        return code;
    }

    public Integer getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(Integer codeNum) {
        this.codeNum = codeNum;
    }

    /**
     * 测试函数,默认生成到d盘
     * @param args
     */
    public static void main(String[] args) {
        String[] words = "2,+,3=".split(",");

        // 生产运算符短信验证码
        {
            ValidateCodeUtil vCode = new ValidateCodeUtil(160,40,100);
            vCode.createOperationCode();
            try {
                String path="D:/"+new Date().getTime()+".png";
                System.out.println(vCode.getCodeNum()+" >"+path);
                vCode.write(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 生产数字字母验证码

        // 生产运算符短信验证码
        {
            ValidateCodeUtil vCode = new ValidateCodeUtil(160,40,100);
            vCode.createRandomCode(4);
            try {
                String path="D:/"+new Date().getTime()+".png";
                System.out.println(vCode.getCode()+" >"+path);
                vCode.write(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

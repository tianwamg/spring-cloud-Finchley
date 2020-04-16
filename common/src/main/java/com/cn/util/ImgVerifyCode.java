package com.cn.util;

import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ImgVerifyCode {

    public static JSONObject createVerifyCode(HttpServletRequest request){
        String png_base64="";
        Integer width = 120;
        Integer height = 40;
        //绘制图片
        BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,0,width,height);//填充背景
        graphics.drawRect(0,0,width-1,height-1);//边框
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setFont(new Font("宋体",Font.BOLD,30));
        String randC = generateRandomNum(4);
        int x= 10;//定义x坐标
        Random random = new Random();
        for(int i =0;i<randC.length();i++){
            graphics2D.setColor(generateRandomColor());
            int degree = random.nextInt(60)-30;//旋转角度
            double theta = degree * Math.PI /100;//旋转弧度
            char c = randC.charAt(i);
            graphics2D.rotate(theta,x,20);//旋转
            graphics2D.drawString(String.valueOf(c),x,20);
            graphics2D.rotate(-theta,x,20);//反向旋转
            x +=30;
        }
        request.getSession().setAttribute("verifycode",randC);//保存随机数
        String uuid = UUID.randomUUID().toString();
        /*for(int i =0;i<randC.length();i++){//画干扰线
            graphics2D.setColor(generateRandomColor());
            graphics2D.drawLine(random.nextInt(width),random.nextInt(height),random.nextInt(width),random.nextInt(height));
        }*/
        for(int i =0;i<30;i++){//添加噪点
            graphics2D.setColor(generateRandomColor());
            graphics2D.fillRect(random.nextInt(width),random.nextInt(height),2,2);
        }
        graphics.dispose();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage,"png",outputStream);//写入流
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = outputStream.toByteArray();//转换成字节
        BASE64Encoder encoder = new BASE64Encoder();
        png_base64 = encoder.encode(bytes).trim();
        png_base64 = png_base64.replaceAll("\n","").replaceAll("\r","");
        JSONObject object = new JSONObject();
        object.put("UUID",uuid);
        object.put("data","data:image/png;base64,"+png_base64);
        return object;
    }

    //生成随机数
    public static String generateRandomNum(int num){
        //String baseChar = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
        String baseChar = "123456789abcdefghijklmnopqrstuvwxyz";
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for(int i = 0;i<num;i++){
            int dot = random.nextInt(baseChar.length());
            builder.append(baseChar.charAt(dot));
        }
        return builder.toString();
    }

    //生成随机色
    public static Color generateRandomColor(){
        Random random = new Random();
        Color color = new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
        return color;
    }
}

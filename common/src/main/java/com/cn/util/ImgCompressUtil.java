package com.cn.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 图片压缩
 */
public class ImgCompressUtil {

    /**
     * 直接指定压缩后的宽高
     * @param oldFile
     * @param width
     * @param height
     * @param quality
     * @param smallIcon
     * @return
     */
    public static String zipImgFile(String oldFile,int width,int height,float quality,String smallIcon){
        if(oldFile == null){
            return null;
        }
        String newImg = null;
        try {
            Image scrFile = ImageIO.read(new File(oldFile));
            //压缩的宽高
            BufferedImage tag = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(scrFile,0,0,width,height,null);
            String filePrex = oldFile.substring(0,oldFile.indexOf("."));
            newImg = filePrex + smallIcon + oldFile.substring(filePrex.length());
            //压缩后的文件存储位置
            FileOutputStream fileOutputStream = new FileOutputStream(newImg);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fileOutputStream);
            JPEGEncodeParam param = JPEGCodec.getDefaultJPEGEncodeParam(tag);
            //压缩质量
            param.setQuality(quality,true);
            encoder.encode(tag,param);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newImg;
    }

    /**
     * 保存文件
     * @param fileName
     * @param inputStream
     * @return
     */
    public static String writeFile(String fileName, InputStream inputStream){
        if(fileName == null || fileName.trim().length()==0){
            return null;
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            byte[] readBytes = new byte[1024];//缓冲大小
            int readed = 0;
            while ((readed = inputStream.read(readBytes))>0){
                outputStream.write(readBytes,0,readed);
            }
            outputStream.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public static void saveMinImg(String srcUrl,String deskUrl,double comBase,double scale) throws IOException {
        File file = new File(srcUrl);
        Image src = ImageIO.read(file);
        int srcHeight = src.getHeight(null);
        int srcWidth = src.getWidth(null);
        int deskHeight = 0;//缩略图高
        int deskWidth = 0;//缩略图宽
        double srcScale = (double) srcHeight/srcWidth;
        if((double) srcHeight  > comBase ||(double) srcWidth >comBase){
            if(srcScale >= scale || 1 >srcScale / scale){
                if(srcScale >= scale){
                    deskHeight = (int) comBase;
                    deskWidth = srcWidth * deskHeight/srcHeight;
                }else {
                    deskWidth = (int) comBase;
                    deskHeight = srcHeight* deskWidth/srcWidth;
                }
            }else {
                if((double)srcHeight > comBase){
                    deskHeight = (int) comBase;
                    deskWidth = srcWidth* deskHeight/srcHeight;
                }else {
                    deskWidth = (int) comBase;
                    deskHeight = srcHeight * deskWidth/srcWidth;
                }
            }
        }else {
            deskHeight = srcHeight;
            deskWidth = srcWidth;
        }
        BufferedImage tag = new BufferedImage(deskWidth,deskHeight,BufferedImage.TYPE_3BYTE_BGR);
        tag.getGraphics().drawImage(src,0,0,deskWidth,deskHeight,null);//绘制缩小后的图
        FileOutputStream deskImage = new FileOutputStream(deskUrl);//输出到文件流
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(deskImage);
        encoder.encode(tag);//JPEG编码
        deskImage.close();
    }
}

package com.cn.util;

import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;

/**
 * 图片压缩工具
 */
public class Thumbnailator {

    public static void scaleZip() throws IOException {
        Thumbnails.of("path")//原图路径
                .scale(1f)//压缩比例
                .outputQuality(0.5f)//压缩图片质量,值越大，压缩质量越高
                .toFile("newPath");//图片传输路径
    }
}

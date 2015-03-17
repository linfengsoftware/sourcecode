package com.lysum.common.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;

/**
 * 图片工具类
 * 
 * @author zhangQ
 * @create date: 2014-12-5
 */
public class ImageUtils {

    private static final String[] IMAGES_SUFFIXES = { "bmp", "jpg", "jpeg", "gif", "png", "tiff"};

    /**
     * 是否是图片附件
     *
     * @param filename
     * @return
     */
    public static boolean isImage(String filename) {
        return ArrayUtils.contains(IMAGES_SUFFIXES,StringUtil.lowerCase(FilenameUtils.getExtension(filename)));
    }
    
    /**
     * 获取图片文件的BufferedImage
     * @param file
     * @return
     */
    public static BufferedImage getBufferedImage(File file){
    	try {
			return ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 获取图片文件的宽度
     * @param file
     * @return
     */
    public static int getWidth(File file){
    	BufferedImage bufImg = getBufferedImage(file);
    	if(bufImg!=null){
    		return bufImg.getWidth();
    	}
    	return 0;
    }
    
    /**
     * 获取图片文件的高度
     * @param file
     * @return
     */
    public static int getHeight(File file){
    	BufferedImage bufImg = getBufferedImage(file);
    	if(bufImg!=null){
    		return bufImg.getHeight();
    	}
    	return 0;
    }

}

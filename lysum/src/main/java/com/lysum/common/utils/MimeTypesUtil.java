package com.lysum.common.utils;

import java.io.File;
import java.io.InputStream;
import java.util.Set;



/**
 * 文件mimeType工具类
 * 
 * @author zhangQ
 * @create date: 2014-12-1
 */
public class MimeTypesUtil {
	private static MimeTypes _mimeTypes ;

	/**
	 * 获取文件的content type
	 * @param file
	 * @return
	 */
	public static String getContentType(File file) {
		return getMimeTypes().getContentType(file);
	}

	/**
	 * 获取文件的content type
	 * @param file
	 * @param fileName
	 * @return
	 */
	public static String getContentType(File file, String fileName) {
		return getMimeTypes().getContentType(file, fileName);
	}

	/**
	 * 从文件的输入流和文件名content type
	 * @param inputStream
	 * @param fileName 文件的全名或者后缀 (e.g., "Test.doc", ".doc")
	 * @return
	 */
	public static String getContentType(InputStream inputStream, String fileName) {

		return getMimeTypes().getContentType(inputStream, fileName);
	}

	/**
	 * 从文件名中检测content type
	 * @param fileName 文件的全名或者后缀 (e.g., "Test.doc", ".doc")
	 * @return
	 */
	public static String getContentType(String fileName) {
		return getMimeTypes().getContentType(fileName);
	}

	/**
	 * 检测给定content type的所有可能的后缀名
	 * @param contentType
	 * @return
	 */
	public static Set<String> getExtensions(String contentType) {
		return getMimeTypes().getExtensions(contentType);
	}

	public static MimeTypes getMimeTypes() {
		if(_mimeTypes == null){
			_mimeTypes = new MimeTypes();
		}
//		PortalRuntimePermission.checkGetBeanProperty(MimeTypesUtil.class);
		return _mimeTypes;
	}

	/*public void setMimeTypes(MimeTypes mimeTypes) {
//		PortalRuntimePermission.checkSetBeanProperty(getClass());
		_mimeTypes = mimeTypes;
	}*/
}

package com.lysum.service.file.exception;

import org.apache.commons.fileupload.FileUploadException;

import java.util.Arrays;

/**
 * 无效扩展名异常
 * 
 * @author zhangQ
 * @create date: 2014-5-6
 */
public class InvalidExtensionException extends FileUploadException {

	private static final long serialVersionUID = -2036086476886786243L;
	
	private String[] allowedExtension;
    private String extension;
    private String filename;

    public InvalidExtensionException(String[] allowedExtension, String extension, String filename) {
        super("filename : [" + filename + "], extension : [" + extension + "], allowed extension : [" + Arrays.toString(allowedExtension) + "]");
        this.allowedExtension = allowedExtension;
        this.extension = extension;
        this.filename = filename;
    }

    public String[] getAllowedExtension() {
        return allowedExtension;
    }

    public String getExtension() {
        return extension;
    }

    public String getFilename() {
        return filename;
    }

    /**
     * 无效图片扩展名
     * @author zhangQ
     * @create date: 2014-5-6
     */
    public static class InvalidImageExtensionException extends InvalidExtensionException {

		private static final long serialVersionUID = -8359238518275827033L;

		public InvalidImageExtensionException(String[] allowedExtension, String extension, String filename) {
            super(allowedExtension, extension, filename);
        }
    }

    /**
     * 无效flash扩展名
     * @author zhangQ
     * @create date: 2014-5-6
     */
    public static class InvalidFlashExtensionException extends InvalidExtensionException {
		private static final long serialVersionUID = -8394758752420454445L;

		public InvalidFlashExtensionException(String[] allowedExtension, String extension, String filename) {
            super(allowedExtension, extension, filename);
        }
    }

    /**
     * 无效Media
     * @author zhangQ
     * @create date: 2014-5-6
     */
    public static class InvalidMediaExtensionException extends InvalidExtensionException {
		private static final long serialVersionUID = 4228651255805982562L;

		public InvalidMediaExtensionException(String[] allowedExtension, String extension, String filename) {
            super(allowedExtension, extension, filename);
        }
    }

}


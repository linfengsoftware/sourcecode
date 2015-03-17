package com.yingu.invest.maintain.upload.exception;

import org.apache.commons.fileupload.FileUploadException;

/**
 * 文件类型和扩展名不匹配异常
 * 
 * @author zhangQ
 * @create date: 2014-12-1
 */
public class MismatchExtensionException extends FileUploadException {

	private static final long serialVersionUID = -2036086476886786243L;
	
	private String contentType;
    private String extension;
    private String filename;

    public MismatchExtensionException(String contentType, String extension, String filename) {
        super("filename : [" + filename + "], extension : [" + extension + "], File Content-Type and extensions do not match : [" +contentType+ "]");
        this.contentType = contentType;
        this.extension = extension;
        this.filename = filename;
    }

    public String getExtension() {
        return extension;
    }

    public String getFilename() {
        return filename;
    }

	public String getContentType() {
		return contentType;
	}

}


package com.lysum.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Set;

import com.lysum.entity.UploadFile;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.lysum.service.file.exception.FileNameLengthLimitExceededException;
import com.lysum.service.file.exception.InvalidExtensionException;
import com.lysum.service.file.exception.MismatchExtensionException;



public class FileUploadUtils {
	/** 默认大小 50M */
	public static final long DEFAULT_MAX_SIZE = 52428800;

	/** 默认上传的地址 */
	private static String defaultBaseDir = "upload";

	/** 默认的文件名最大长度 */
	public static final int DEFAULT_FILE_NAME_LENGTH = 200;

	/** 图片扩展名 */
	public static final String[] IMAGE_EXTENSION = { "bmp", "gif", "jpg", "jpeg", "png" };

	/** Flash扩展名 */
	public static final String[] FLASH_EXTENSION = { "swf", "flv" };

	/** media扩展类型 */
	public static final String[] MEDIA_EXTENSION = { "swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg", "asf",
			"rm", "rmvb" };

	/** 默认允许扩展名 */
	public static final String[] DEFAULT_ALLOWED_EXTENSION = {
			// 图片
			"bmp", "gif", "jpg", "jpeg", "png",
			// word excel powerpoint
			"doc", "docx", "xls", "xlsx", "ppt", "pptx", "css", "js", "html", "htm", "txt",
			// 压缩文件
			"rar", "zip", "gz", "bz2",
			// pdf
			"pdf", "apk" };

	/** 计数器 */
	private static int counter = 0;

	public static void setDefaultBaseDir(String defaultBaseDir) {
		FileUploadUtils.defaultBaseDir = defaultBaseDir;
	}

	public static String getDefaultBaseDir() {
		return defaultBaseDir;
	}

	/**
	 * 以默认配置进行文件上传
	 * 
	 * @param request 当前请求
	 * @param file 上传的文件
	 * @param model 添加出错信息
	 * @return
	 * @throws IOException 
	 * @throws MismatchExtensionException 
	 * @throws FileNameLengthLimitExceededException 
	 * @throws InvalidExtensionException 
	 * @throws FileSizeLimitExceededException 
	 */
	public static final UploadFile upload(HttpServletRequest request, MultipartFile file) throws FileSizeLimitExceededException, InvalidExtensionException, FileNameLengthLimitExceededException, MismatchExtensionException, IOException {
		
		return upload(request, getDefaultBaseDir(), true, file, DEFAULT_ALLOWED_EXTENSION);
	}

	/**
	 * 以默认配置进行文件上传
	 * 
	 * @param request 当前请求
	 * @param uploadPath 上传的路径
	 * @param needDatePathAndRandomName 是否需要日期目录和随机文件名前缀
	 * @param file 上传的文件
	 * @param file 上传的文件
	 * @param model 添加出错信息
	 * @return
	 * @throws IOException 
	 * @throws MismatchExtensionException 
	 * @throws FileNameLengthLimitExceededException 
	 * @throws InvalidExtensionException 
	 * @throws FileSizeLimitExceededException 
	 */
	public static final UploadFile upload(HttpServletRequest request, String uploadPath, boolean needDatePathAndRandomName,
			MultipartFile file) throws FileSizeLimitExceededException, InvalidExtensionException, FileNameLengthLimitExceededException, MismatchExtensionException, IOException {
		if (uploadPath == null || StringUtils.isEmpty(uploadPath)) {
			uploadPath = getDefaultBaseDir();
		}
		return upload(request, uploadPath, needDatePathAndRandomName, file, DEFAULT_ALLOWED_EXTENSION);
	}

	/**
	 * 以默认配置进行文件上传
	 * 
	 * @param request 当前请求
	 * @param file 上传的文件
	 * @param model 添加出错信息
	 * @param allowedExtension 允许上传的文件类型
	 * @return
	 * @throws IOException
	 * @throws MismatchExtensionException
	 * @throws FileNameLengthLimitExceededException
	 * @throws InvalidExtensionException
	 * @throws FileSizeLimitExceededException
	 */
	public static final UploadFile upload(HttpServletRequest request, String uploadPath, boolean needDatePathAndRandomName,
			MultipartFile file, String[] allowedExtension) throws FileSizeLimitExceededException, InvalidExtensionException,
			FileNameLengthLimitExceededException, MismatchExtensionException, IOException {
		return upload(request, uploadPath, file, allowedExtension, DEFAULT_MAX_SIZE, needDatePathAndRandomName);
	}

	/**
	 * 文件上传
	 * 
	 * @param request 当前请求 从请求中提取 应用上下文根
	 * @param baseDir 相对应用的基目录
	 * @param file 上传的文件
	 * @param allowedExtension 允许的文件类型 null 表示允许所有
	 * @param maxSize 最大上传的大小 -1 表示不限制
	 * @param needDatePathAndRandomName 是否需要日期目录和随机文件名前缀
	 * @return 返回上传成功的文件名
	 * @throws InvalidExtensionException 如果MIME类型不允许
	 * @throws FileSizeLimitExceededException 如果超出最大大小
	 * @throws FileNameLengthLimitExceededException 文件名太长
	 * @throws IOException 比如读写文件出错时
	 */
	public static final UploadFile upload(HttpServletRequest request, String baseDir, MultipartFile file,
			String[] allowedExtension, long maxSize, boolean needDatePathAndRandomName) throws InvalidExtensionException,
			FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException, MismatchExtensionException {

		int fileNamelength = file.getOriginalFilename().length();
		if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
			throw new FileNameLengthLimitExceededException(file.getOriginalFilename(), fileNamelength,
					FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
		}

		// 判断文件类型是否和后缀名一致
		String originalFilename = file.getOriginalFilename();
		String extension = FilenameUtils.getExtension(originalFilename);
		String contentType = MimeTypesUtil.getContentType(file.getInputStream(), originalFilename);
		Set<String> extensions = MimeTypesUtil.getExtensions(contentType);
		if (!extensions.contains("."+StringUtils.lowerCase(extension))) {
			throw new MismatchExtensionException(contentType, extension, originalFilename);
		}
		assertAllowed(file, allowedExtension, maxSize); // 判断是否允许文件上传
		String filename = extractFilename(file, baseDir, needDatePathAndRandomName);
		File desc = getAbsoluteFile(extractUploadDir(request), filename);
		file.transferTo(desc); // 将上传文件转存到指定目录的文件
		String relativePath = StringUtil.replace(filename, File.separator, StringPool.FORWARD_SLASH);
		//File uploadFile = new File( file.getOriginalFilename(),desc, relativePath);
		UploadFile uploadFile = new UploadFile();
		
		if(ImageUtils.isImage(filename)){		//图片文件保存图片的宽高到备注中，用json格式
			JSONObject jobj = new JSONObject();
			jobj.put("width", ImageUtils.getWidth(desc));
			jobj.put("height", ImageUtils.getHeight(desc));
			uploadFile.setRemark(jobj.toJSONString());
		}else{//lyf add
			uploadFile.setOrginalName(originalFilename);
			uploadFile.setFilePath(relativePath);
			uploadFile.setSize(file.getSize());
		}
		
		return uploadFile;
	}

	/**
	 * 获取绝对路径的文件
	 * 
	 * @param uploadDir
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	private static final File getAbsoluteFile(String uploadDir, String filename) throws IOException {

		uploadDir = FilenameUtils.normalizeNoEndSeparator(uploadDir);

		File desc = new File(uploadDir + File.separator + filename);

		if (!desc.getParentFile().exists()) {
			desc.getParentFile().mkdirs();
		}
		if (!desc.exists()) {
			desc.createNewFile();
		}
		return desc;
	}

	/**
	 * 提取文件名
	 * 
	 * @param file 上传文件
	 * @param baseDir 保存目录
	 * @param needDatePathAndRandomName 是否需要日期目录和随机文件名前缀
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static final String extractFilename(MultipartFile file, String baseDir, boolean needDatePathAndRandomName)
			throws UnsupportedEncodingException {
		String filename = file.getOriginalFilename();
		int slashIndex = filename.indexOf("/");
		if (slashIndex >= 0) {
			filename = filename.substring(slashIndex + 1);
		}
		if (needDatePathAndRandomName) {
			filename = baseDir + File.separator + datePath() + File.separator + encodingFilename(filename);
		} else {
			filename = baseDir + File.separator + filename;
		}

		return filename;
	}

	/**
	 * 编码文件名,默认格式为： 1、'_'替换为 ' ' 2、hex(md5(filename + now nano time + counter++)) 3、[2]_原始文件名
	 * 
	 * @param filename
	 * @return
	 */
	private static final String encodingFilename(String filename) {
		filename = filename.replace("_", " ");
		filename = DigestUtils.md5Hex(filename + System.nanoTime() + counter++) + StringPool.PERIOD
				+ StringUtil.extractLast(filename, StringPool.PERIOD);
		// filename = DigestUtils.md5Hex(filename + System.nanoTime() + counter++) + "_" + filename;
		return filename;
	}

	/**
	 * 日期路径 即年/月/日 如2013/01/03
	 * 
	 * @return
	 */
	private static final String datePath() {
		Date now = new Date();
		String format = DateFormatUtils.format(now, "yyyy/MM/dd");
		return StringUtil.replace(format, StringPool.FORWARD_SLASH, File.separator);
	}

	/**
	 * 是否允许文件上传
	 * 
	 * @param file 上传的文件
	 * @param allowedExtension 文件类型 null 表示允许所有
	 * @param maxSize 最大大小 字节为单位 -1表示不限制
	 * @return
	 * @throws InvalidExtensionException 如果MIME类型不允许
	 * @throws FileSizeLimitExceededException 如果超出最大大小
	 */
	public static final void assertAllowed(MultipartFile file, String[] allowedExtension, long maxSize)
			throws InvalidExtensionException, FileSizeLimitExceededException {

		String filename = file.getOriginalFilename();
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());

		if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
			if (allowedExtension == IMAGE_EXTENSION) {
				throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension, filename);
			} else if (allowedExtension == FLASH_EXTENSION) {
				throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension, filename);
			} else if (allowedExtension == MEDIA_EXTENSION) {
				throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension, filename);
			} else {
				throw new InvalidExtensionException(allowedExtension, extension, filename);
			}
		}

		long size = file.getSize();
		if (maxSize != -1 && size > maxSize) {
			throw new FileSizeLimitExceededException("not allowed upload upload", size, maxSize);
		}
	}

	/**
	 * 判断MIME类型是否是允许的MIME类型
	 * 
	 * @param extension
	 * @param allowedExtension
	 * @return
	 */
	public static final boolean isAllowedExtension(String extension, String[] allowedExtension) {
		for (String str : allowedExtension) {
			if (str.equalsIgnoreCase(extension)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 提取上传的根目录 默认是应用的根
	 * 
	 * @param request
	 * @return
	 */
	public static final String extractUploadDir(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("/");
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param filename
	 * @throws IOException
	 */
	public static final void delete(HttpServletRequest request, String filename) throws IOException {
		if (StringUtils.isEmpty(filename)) {
			return;
		}
		File desc = getAbsoluteFile(extractUploadDir(request), filename);
		if (desc.exists()) {
			desc.delete();
		}
	}
}

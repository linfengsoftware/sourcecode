package com.lysum.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeTypesReaderMetKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * MimeTypes类型
 * 
 * @author zhangQ
 * @create date: 2014-12-1
 */
public class MimeTypes implements MimeTypesReaderMetKeys {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private Detector detector;
	private Map<String, Set<String>> extensionsMap = Maps.newHashMap();

	public MimeTypes() {
		detector = new DefaultDetector(org.apache.tika.mime.MimeTypes.getDefaultMimeTypes());

		URL url = org.apache.tika.mime.MimeTypes.class.getResource("tika-mimetypes.xml");

		try {
			read(url.openStream());
		} catch (Exception e) {
			logger.error("Unable to populate extensions map", e);
		}
	}

	/**
	 * 获取文件的contentType
	 * @param file
	 * @return
	 */
	public String getContentType(File file) {
		return getContentType(file, file.getName());
	}

	/**
	 * 获取文件的contentType
	 * @param file
	 * @param fileName
	 * @return
	 */
	public String getContentType(File file, String fileName) {
		if ((file == null) || !file.exists()) {
			return getContentType(fileName);
		}

		InputStream is = null;

		try {
			is = TikaInputStream.get(file);
			return getContentType(is, fileName);
		} catch (FileNotFoundException fnfe) {
			return getContentType(fileName);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	/**
	 * 获取流的contentType
	 * @param inputStream
	 * @param fileName
	 * @return
	 */
	public String getContentType(InputStream inputStream, String fileName) {
		if (inputStream == null) {
			return getContentType(fileName);
		}

		String contentType = null;

		try {
			Metadata metadata = new Metadata();

			metadata.set(Metadata.RESOURCE_NAME_KEY, fileName);

			MediaType mediaType = detector.detect(TikaInputStream.get(inputStream), metadata);

			contentType = mediaType.toString();

			if (contentType.contains("tika")) {
				if (logger.isDebugEnabled()) {
					logger.debug("Retrieved invalid content type " + contentType);
				}

				contentType = getContentType(fileName);
			}

			if (contentType.contains("tika")) {
				if (logger.isDebugEnabled()) {
					logger.debug("Retrieved invalid content type " + contentType);
				}

				contentType = ContentTypes.APPLICATION_OCTET_STREAM;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			contentType = ContentTypes.APPLICATION_OCTET_STREAM;
		}

		return contentType;
	}

	/**
	 * 获取文件的ContentType
	 * @param fileName
	 * @return
	 */
	public String getContentType(String fileName) {
		if (Validator.isNull(fileName)) {
			return ContentTypes.APPLICATION_OCTET_STREAM;
		}

		try {
			Metadata metadata = new Metadata();

			metadata.set(Metadata.RESOURCE_NAME_KEY, fileName);

			MediaType mediaType = detector.detect(null, metadata);

			String contentType = mediaType.toString();

			if (!contentType.contains("tika")) {
				return contentType;
			} else if (logger.isDebugEnabled()) {
				logger.debug("Retrieved invalid content type " + contentType);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return ContentTypes.APPLICATION_OCTET_STREAM;
	}

	/**
	 * 根据文件的contentType获取关联的所有后缀名
	 * 
	 * @param contentType
	 * @return
	 */
	public Set<String> getExtensions(String contentType) {
		Set<String> extensions = extensionsMap.get(contentType);

		if (extensions == null) {
			extensions = Collections.emptySet();
		}

		return extensions;
	}

	/**
	 * 获取tika-mimetypes.xml文件并且解析
	 * @param stream
	 * @throws Exception
	 */
	protected void read(InputStream stream) throws Exception {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

		Document document = documentBuilder.parse(new InputSource(stream));

		Element element = document.getDocumentElement();

		if ((element == null) || !MIME_INFO_TAG.equals(element.getTagName())) {
			throw new Exception("Invalid configuration file");
		}

		NodeList nodeList = element.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);

			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			Element childElement = (Element) node;

			if (MIME_TYPE_TAG.equals(childElement.getTagName())) {
				readMimeType(childElement);
			}
		}
	}

	/**
	 * 读取tika的MimeType配置元素
	 * @param element
	 */
	protected void readMimeType(Element element) {
		Set<String> mimeTypes = Sets.newHashSet();		//节点的类型

		Set<String> extensions = Sets.newHashSet();		//扩展名

		String name = element.getAttribute(MIME_TYPE_TYPE_ATTR);

		mimeTypes.add(name);

		NodeList nodeList = element.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);

			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			Element childElement = (Element) node;

			if (ALIAS_TAG.equals(childElement.getTagName())) {	
				String alias = childElement.getAttribute(ALIAS_TYPE_ATTR);	//别名

				mimeTypes.add(alias);	//别名也是mimetype
			} else if (GLOB_TAG.equals(childElement.getTagName())) {
				boolean isRegex = GetterUtil.getBoolean(childElement.getAttribute(ISREGEX_ATTR));

				if (isRegex) {
					continue;
				}

				String pattern = childElement.getAttribute(PATTERN_ATTR);

				if (!pattern.startsWith("*")) {
					continue;
				}

				String extension = pattern.substring(1);

				if (!extension.contains("*") && !extension.contains("?") && !extension.contains("[")) {

					extensions.add(extension);
				}
			}
		}

		for (String mimeType : mimeTypes) {
			extensionsMap.put(mimeType, extensions);	//关联mimeType和文件的后缀
		}
	}

}

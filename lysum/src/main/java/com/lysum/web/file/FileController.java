package com.lysum.web.file;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.lysum.entity.UploadFile;
import com.lysum.service.file.FileService;

/**
 * File管理的Controller, 使用Restful风格的Urls:
 * 
 * List page     : GET /file/
 * Create page   : GET /file/create
 * Create action : POST /file/create
 * Update page   : GET /file/update/{id}
 * Update action : POST /file/update
 * Delete action : GET /file/delete/{id}
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/file")
public class FileController{
	private static final int PAGE_SIZE = 3;

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("fileName", "文件名称");
	}
	
	@Autowired
	public FileService fileService ;
	
	@RequestMapping(value="")
	public String list(@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber, Model model, ServletRequest request){
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Page<UploadFile> files = fileService.getFiles(searchParams, pageNumber, PAGE_SIZE, sortType);
		model.addAttribute("files", files);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		return "file/fileList" ;
	}
	
	@RequestMapping(value="create",method=RequestMethod.GET)
	public String createForm(Model model){
		return "file/single";
	}
	
	
}

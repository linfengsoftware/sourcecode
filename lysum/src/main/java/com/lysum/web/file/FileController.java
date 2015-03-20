package com.lysum.web.file;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.utils.DateProvider;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.lysum.common.utils.DateUtils;
import com.lysum.common.utils.FileUploadUtils;
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
	
	private DateProvider dateProvider = DateProvider.DEFAULT;
	
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
		model.addAttribute("action", "create");
		return "file/single";
	}
	
	
	@RequestMapping(value = "create",method = RequestMethod.POST)
	public String create(@Valid UploadFile uploadFile ,@RequestParam MultipartFile[] upFiles, HttpServletRequest request,RedirectAttributes redirectAttributes){
		boolean success = false ;
		FileUploadUtils fileUploadUtils = new FileUploadUtils();
		UploadFile tmpFile = new UploadFile();
		String uploadPath = request.getSession().getServletContext().getRealPath("/static/upload");
		System.out.println("uploadPath:"+uploadPath);
		for(MultipartFile file : upFiles){
			if(file.isEmpty()){
				System.out.println("文件未上传");
			}else{
				try{
					tmpFile = fileUploadUtils.upload(request, file);
					tmpFile.setFileName(uploadFile.getFileName());
					tmpFile.setRemark(uploadFile.getRemark());
					tmpFile.setUploadTime(dateProvider.getDate());
					fileService.saveFile(tmpFile);
					success = true ;
				}catch(Exception e){
					success = false ;
					e.printStackTrace();
					
				}
			}
		}
		if(success = false){
			redirectAttributes.addFlashAttribute("message", "上传文件失败");
		}else{
			redirectAttributes.addFlashAttribute("message", "上传文件成功");
		}
		return "redirect:/file/";
	}


	public void setDateProvider(DateProvider dateProvider) {
		this.dateProvider = dateProvider;
	}
	
}

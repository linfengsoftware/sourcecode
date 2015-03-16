package com.lysum.web.dept;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.lysum.entity.Dept;
import com.lysum.service.dept.DeptService;

/**
 * Task管理的Controller, 使用Restful风格的Urls:
 * 
 * List page     : GET /dept/
 * Create page   : GET /dept/create
 * Create action : POST /dept/create
 * Update page   : GET /dept/update/{id}
 * Update action : POST /dept/update
 * Delete action : GET /dept/delete/{id}
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/dept")
public class DeptController {

	private static final int PAGE_SIZE = 10;

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("deptName", "标题");
	}

	@Autowired
	private DeptService deptService;

	@RequestMapping(value = "")
	public String list(@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber, Model model, ServletRequest request) {

		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Page<Dept> depts = deptService.getDepts(searchParams, pageNumber, PAGE_SIZE, sortType);
		model.addAttribute("depts", depts);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "dept/deptList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		List<Dept> depts = new ArrayList<Dept>();
		depts = deptService.getAllDepts();
		model.addAttribute("depts", depts);
		model.addAttribute("dept", new Dept());
		model.addAttribute("action", "create");
		return "dept/deptForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Dept newDept, RedirectAttributes redirectAttributes) {
		deptService.saveDept(newDept);
		redirectAttributes.addFlashAttribute("message", "创建部门成功");
		return "redirect:/dept/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		List<Dept> depts = new ArrayList<Dept>();
		depts = deptService.getAllDepts();
		model.addAttribute("depts", depts);
		model.addAttribute("dept", deptService.getDept(id));
		model.addAttribute("action", "update");
		return "dept/deptForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("preloadDept") Dept dept, RedirectAttributes redirectAttributes) {
		deptService.saveDept(dept);
		redirectAttributes.addFlashAttribute("message", "更新部门成功");
		return "redirect:/dept/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		deptService.deleteDept(id);
		redirectAttributes.addFlashAttribute("message", "删除部门成功");
		return "redirect:/dept/";
	}

	/**
	 * 使用@ModelAttribute, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此本方法在该方法中执行.
	 */
	@ModelAttribute("preloadDept")
	public Dept getDept(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			return deptService.getDept(id);
		}
		return null;
	}

	
}

package com.lysum.service.dept;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

import com.lysum.entity.Dept;
import com.lysum.repository.DeptDao;

//Spring Bean的标识.
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class DeptService {

	private DeptDao deptDao ;
    
    public Dept getDept(Long id){
    	return deptDao.findOne(id);
    }
    
    @Transactional(readOnly = false)
    public boolean deleteDept(Long id){
    	boolean success = false ;
    	try{
    		deptDao.delete(id);
    		success = true ;
    	}catch(Exception e ){
    		e.printStackTrace();
    	}
    	return success ;
    }
    
    @Transactional(readOnly = false)
    public boolean saveDept(Dept newDept){
    	boolean success = false ;
    	try{
    		deptDao.save(newDept);
    		success = true ;
    	}catch(Exception e ){
    		e.printStackTrace();
    	}
    	return success ;
    }
    
    @Transactional(readOnly = false)
    public boolean updateDept(Dept newDept){
    	boolean success = false ;
    	try{
    		deptDao.save(newDept);
    		success = true ;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return success ;
    }
    
    public List<Dept> getAllDepts(){
    	List<Dept> deptList = new ArrayList();
    	deptList = (List<Dept>)deptDao.findAll();
    	return deptList;
    }
    
    public Page<Dept>  getDepts(Map<String, Object> filterParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Dept> spec = buildSpecification( filterParams);

		return deptDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("deptName".equals(sortType)) {
			sort = new Sort(Direction.ASC, "deptName");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<Dept> buildSpecification(Map<String, Object> filterParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
		Specification<Dept> spec = DynamicSpecifications.bySearchFilter(filters.values(), Dept.class);
		return spec;
	}

	@Autowired
	public void setDeptDao(DeptDao deptDao) {
		this.deptDao = deptDao;
	}
}

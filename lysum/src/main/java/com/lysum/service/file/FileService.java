package com.lysum.service.file;

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
import com.lysum.entity.UploadFile;
import com.lysum.repository.FileDao;

@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class FileService {
	private FileDao fileDao ;

    @Autowired
	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}
    
    public Page<UploadFile>  getFiles(Map<String, Object> filterParams, int pageNumber, int pageSize,
			String sortType){
    	PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<UploadFile> spec = buildSpecification( filterParams);

		return fileDao.findAll(spec, pageRequest);
    	
    }
    
    @Transactional(readOnly=true)
    public UploadFile getFile(Long id){
    	UploadFile file = new UploadFile();
    	file = fileDao.findOne(id);
    	return file ;
    }
    
    
    @Transactional(readOnly = false )
    public boolean saveFile(UploadFile file){
    	boolean success = false ;
    	try{
    		//upload the file
    		
    		fileDao.save(file);
    		success = true ;
    	}catch(Exception e){
    		success = false ;
    	}
    	return success;
    }
    
    @Transactional(readOnly = false )
    public boolean updateFile(UploadFile file){
    	boolean success = false ;
    	try{
    		//upload the file
    		
    		
    		
    		fileDao.save(file);
    		success = true ;
    	}catch(Exception e){
    		success = false ;
    	}
    	return success ;
    }
    
   @Transactional(readOnly = false)  
    public boolean deleteFile(UploadFile file){
    	boolean success = false ;
    	try{
      		
    		fileDao.delete(file);
    	}catch(Exception e){
    		success = false ;
    		e.printStackTrace();
    	}
    	return success ;
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
	private Specification<UploadFile> buildSpecification(Map<String, Object> filterParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
		Specification<UploadFile> spec = DynamicSpecifications.bySearchFilter(filters.values(), UploadFile.class);
		return spec;
	}

    
}

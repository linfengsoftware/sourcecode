package com.lysum.service.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    
}

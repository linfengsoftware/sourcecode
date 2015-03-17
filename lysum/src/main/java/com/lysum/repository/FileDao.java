package com.lysum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


import com.lysum.entity.UploadFile;


public interface FileDao extends  PagingAndSortingRepository<UploadFile, Long>,
JpaSpecificationExecutor<UploadFile> {
	
	Page<UploadFile> findByFileName(String fileName, Pageable pageRequest);
	
	
}

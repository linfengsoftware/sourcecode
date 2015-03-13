package com.lysum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.lysum.entity.Dept;

public interface DeptDao extends PagingAndSortingRepository<Dept, Long>,JpaSpecificationExecutor<Dept> {
	
	Page<Dept>  findByDeptName(String deptName,Pageable pageRequest);

}

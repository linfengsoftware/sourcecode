package com.lysum.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.lysum.entity.User;


public interface UserDao extends PagingAndSortingRepository<User, Long> {
	User findByLoginName(String loginName);
}

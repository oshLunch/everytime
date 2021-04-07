package com.cos.everytimeandroid.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository 
public interface UserRepository extends JpaRepository<User, Long>{
	
	// 로그인여부 확인 방법
	// 1. namingQuery
	// findBy 까지는 문법. 그뒤에 대문자로 꺾이는 부분부터 where 절로 검색해준다.
	// select * from user where username = ? and password = ?
	User findByUsernameAndPassword(String username, String password);
	
	// select * from user where username = 'test';
	// 로그인에 이용한 id로 user객체를 가져옴
	@Query(value = "SELECT * FROM user WHERE username = :username", nativeQuery = true)
	User findUserByUsername(String username); 
}

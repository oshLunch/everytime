package com.cos.everytimeandroid.domain.reply;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

	// 1. namingQuery
	// findBy 까지는 문법. 그뒤에 대문자로 꺾이는 부분부터 where 절로 검색해준다.
	// select * from user where username = ? and password = ?
	List<Reply> findByBoardId(Long boardId);
}

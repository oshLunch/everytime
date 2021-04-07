package com.cos.everytimeandroid.domain.reply;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.everytimeandroid.domain.board.Board;
import com.cos.everytimeandroid.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // persistence
public class Reply {

	@Id //pk
	@GeneratedValue(strategy = GenerationType.IDENTITY) // autoIncrease
	private Long id;
	
	private String content;
	
	private String nickname;
	
	private Boolean anonymous;
	
	@ManyToOne // board(1) - reply(n)
	@JoinColumn(name = "boardId")
	private Board board;
	
	@ManyToOne // user(1) - reply(n)
	@JoinColumn(name = "userId")
	private User user;
	
	
	@CreationTimestamp
	private Timestamp createDate;
}

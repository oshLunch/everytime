package com.cos.everytimeandroid.web.board.dto;

import com.cos.everytimeandroid.domain.board.Board;

import lombok.Data;

@Data
public class BoardSaveReqDto {
	private String title;
	private String content;
	private String nickname;
	private Boolean anonymous;
	
	public Board toEntity() {
		return Board.builder()
				.title(title)
				.content(content)
				.nickname(nickname)
				.anonymous(anonymous)
				.build();
		}
}

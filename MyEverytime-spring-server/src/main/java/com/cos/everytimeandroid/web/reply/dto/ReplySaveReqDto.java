package com.cos.everytimeandroid.web.reply.dto;



import com.cos.everytimeandroid.domain.reply.Reply;

import lombok.Data;

@Data
public class ReplySaveReqDto {
	private String nickname;
	private String content;
	private Boolean anonymous;
	
	public Reply toEntity() {
		return Reply.builder()
			.nickname(nickname)
			.content(content)
			.anonymous(anonymous)
			.build();
	}
}

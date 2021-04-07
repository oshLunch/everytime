package com.cos.everytimeandroid.web.user.dto;

import com.cos.everytimeandroid.web.dto.CMRespDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginDto {
	private String loginUsername;
	private String loginPw;
}

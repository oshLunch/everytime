package com.cos.everytimeandroid.web.dto;

import com.cos.everytimeandroid.web.dto.CMRespDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CMRespDto<T> {
	private int code;
	private T data;
}

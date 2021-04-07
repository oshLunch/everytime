package com.cos.everytimeandroid.web.user;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.everytimeandroid.domain.user.User;
import com.cos.everytimeandroid.service.user.UserService;
import com.cos.everytimeandroid.web.dto.CMRespDto;
import com.cos.everytimeandroid.web.user.dto.LoginDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserService userService;
//	private final HttpSession session; 불필요
	
	@PostMapping("/user")
	public CMRespDto<?> save(@RequestBody User user){
		if(user == null) {
			return new CMRespDto<>(-1, null);
		}else {
			return new CMRespDto<>(100, userService.저장하기(user));
		}
		
	}
	
	@PostMapping("/login")
	public CMRespDto<?> login(@RequestBody LoginDto loginDto){
		User loginDtoEntity = userService.로그인(loginDto);
		System.out.println("로그인(loginDto) : " + loginDtoEntity);
		if(loginDtoEntity == null) {
			return new CMRespDto<>(-1, null);
		}else {
			loginDtoEntity.setPassword(null);
			// session.setAttribute("principal", loginDtoEntity); 불필요
			return new CMRespDto<>(100, loginDtoEntity);
		}
		//
	}
	
	@GetMapping("/user/{username}")
	public CMRespDto<?> getLoginUser(@PathVariable String username){
		
		return new CMRespDto<>(100, userService.로그인정보가져오기(username));
	}
}

package com.cos.everytimeandroid.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.everytimeandroid.domain.user.User;
import com.cos.everytimeandroid.domain.user.UserRepository;
import com.cos.everytimeandroid.web.user.dto.LoginDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	/**
	 * @POST("/user")
    	Call<SignUpRespDto> signUpTest(@Body HashMap<String, Object> params);
    	에 맞는 유저 저장하기 기능 만들기 , HashMap 말고 코드, 데이터, 메시지로 바꾸자
	 */
	
	@Transactional
	public User 저장하기(User user) {
		return userRepository.save(user);
	}

	public User 로그인(LoginDto loginDto) {
		System.out.println("input값 LoginUsername: " +loginDto.getLoginUsername());
		System.out.println("input값 LoginPw: " +loginDto.getLoginPw());
		
		User loginDtoEntity = userRepository.findByUsernameAndPassword(loginDto.getLoginUsername(), loginDto.getLoginPw());
		return loginDtoEntity;
	}

	public User 로그인정보가져오기(String username) {
		System.out.println("로그인에 사용한 값: " + username);
		
		User userEntity = userRepository.findUserByUsername(username);
		return userEntity;
	}
}

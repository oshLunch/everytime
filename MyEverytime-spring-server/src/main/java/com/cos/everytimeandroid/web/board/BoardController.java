package com.cos.everytimeandroid.web.board;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.everytimeandroid.domain.board.Board;
import com.cos.everytimeandroid.domain.board.BoardRepository;
import com.cos.everytimeandroid.domain.user.UserRepository;
import com.cos.everytimeandroid.service.board.BoardService;
import com.cos.everytimeandroid.web.board.dto.BoardSaveReqDto;
import com.cos.everytimeandroid.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BoardController {

	private final BoardService boardService;
	private final BoardRepository boardRepository;
	private final UserRepository userRepository;
	
	@GetMapping("/board")
	public CMRespDto<?> findAllBoard() {
		return new CMRespDto<>(100, boardService.전체보기());
		// 안드로이드에서 요청했을 때 1이 안나온다? -> 무조건 오류
	}
	
	@PostMapping("/board/{userId}")
	public CMRespDto<?> saveBoard(@PathVariable Long userId ,@RequestBody BoardSaveReqDto boardSaveReqDto){
		Board boardEntity = boardSaveReqDto.toEntity();
		boardEntity.setTitle(boardSaveReqDto.getTitle());
		boardEntity.setContent(boardSaveReqDto.getContent());
		boardEntity.setNickname(boardSaveReqDto.getNickname());
		boardEntity.setAnonymous(boardSaveReqDto.getAnonymous());
		boardEntity.setUser(userRepository.findById(userId).get());
		System.out.println("BoardController 글 저장 통신중 익명상태:" + boardSaveReqDto.getAnonymous());
		
		return new CMRespDto<>(100, boardService.글저장하기(boardEntity));
	}
	
	@GetMapping("/board/{boardId}")
	public CMRespDto<?> findById(@PathVariable Long boardId){
		return new CMRespDto<>(100, boardService.글상세보기(boardId));
	}
	
	@DeleteMapping("/board/{boardId}/user/{userId}")
	public CMRespDto<?> deleteById(@PathVariable Long boardId, @PathVariable Long userId){
		// if( board의 userId == 인자로 받은 userId (안드에서 preference 에서 가져온 loginUserId 임) )
		if(boardRepository.findById(boardId).get().getUser() == userRepository.findById(userId).get()) {
			boardService.글삭제하기(boardId);
			return new CMRespDto<>(100, null);
		}else {
			return new CMRespDto<>(-1 , null);
		}		
		
	}
	
	@PutMapping("/board/{boardId}/user/{userId}")
	public CMRespDto<?> updateById(@PathVariable Long boardId, @PathVariable Long userId, @RequestBody Board board){
		if(boardRepository.findById(boardId).get().getUser() == userRepository.findById(userId).get()) {
			return new CMRespDto<>(100, boardService.글수정하기(boardId, board));
		}else {
			return new CMRespDto<>(-1, null);
		}
	}
	
	@GetMapping("/board/{boardId}/user/{userId}")
	public CMRespDto<?> principalCheck(@PathVariable Long boardId, @PathVariable Long userId){
		if(boardRepository.findById(boardId).get().getUser() == userRepository.findById(userId).get()) {
			return new CMRespDto<>(100, null);
		}else {
			return new CMRespDto<>(-1, null);
		}
		
	}
}

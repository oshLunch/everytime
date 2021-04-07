package com.cos.everytimeandroid.web.reply;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.everytimeandroid.domain.board.BoardRepository;
import com.cos.everytimeandroid.domain.reply.Reply;
import com.cos.everytimeandroid.domain.reply.ReplyRepository;
import com.cos.everytimeandroid.domain.user.UserRepository;
import com.cos.everytimeandroid.service.reply.ReplyService;
import com.cos.everytimeandroid.web.dto.CMRespDto;
import com.cos.everytimeandroid.web.reply.dto.ReplySaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ReplyController {

	private final ReplyService replyService;
	
	private final ReplyRepository replyRepository;
	private final BoardRepository boardRepository;
	private final UserRepository userRepository;
	
	@PostMapping("/reply/{boardId}/save/{userId}")
	public CMRespDto<?> saveReply(@PathVariable Long boardId,@PathVariable Long userId, @RequestBody ReplySaveReqDto replySaveReqDto){
		System.out.println(boardId);
		System.out.println(replySaveReqDto);
		
		Reply replyEntity = replySaveReqDto.toEntity();  // reply 에 content 넣음
		replyEntity.setUser(userRepository.findById(userId).get());
		replyEntity.setBoard(boardRepository.findById(boardId).get());
		replyEntity.setAnonymous(replySaveReqDto.getAnonymous());
		System.out.println("ReplyController 댓글저장통신중 익명상태 : " + replySaveReqDto.getAnonymous());
		if(boardRepository.findById(boardId).get().getUser() == userRepository.getOne(userId)) {
			if(replyEntity.getAnonymous()) {
				replyEntity.setNickname("익명(글쓴이)");
				return new CMRespDto<>(100, replyService.댓글저장하기(replyEntity));
			}else {
				replyEntity.setNickname(userRepository.findById(userId).get().getNickname()+"(글쓴이)");
				return new CMRespDto<>(100, replyService.댓글저장하기(replyEntity));
			}
		}
		
		return new CMRespDto<>(100, replyService.댓글저장하기(replyEntity));
	}
	
	@GetMapping("/reply/{boardId}")
	public CMRespDto<?> findAllReply(@PathVariable Long boardId){
		return new CMRespDto<>(100, replyService.보드댓글전체보기(boardId));
	}
	
	@DeleteMapping("reply/{replyId}/user/{userId}")
	public CMRespDto<?> deleteReply(@PathVariable Long replyId, @PathVariable Long userId){
		// if( reply의 replyId == 인자로 받은 userId (안드에서 preference 에서 가져온 loginUserId 임) )
		if(replyRepository.findById(replyId).get().getUser() == userRepository.findById(userId).get()) {
			replyService.댓글삭제하기(replyId);
			return new CMRespDto<>(100, null);
		}else {
			return new CMRespDto<>(-1 , null);
		}
	}
}

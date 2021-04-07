package com.cos.everytimeandroid.service.reply;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cos.everytimeandroid.domain.board.BoardRepository;
import com.cos.everytimeandroid.domain.reply.Reply;
import com.cos.everytimeandroid.domain.reply.ReplyRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReplyService {
	private final ReplyRepository replyRepository;
	private final BoardRepository boardRepository;
	
	public Reply 댓글저장하기(Reply reply) {
		return replyRepository.save(reply);
	}

	public List<Reply> 보드댓글전체보기(Long boardId) {
		return replyRepository.findByBoardId(boardId);
	}
	
	public void 댓글삭제하기(Long replyId) {
		replyRepository.deleteById(replyId);
	}
}

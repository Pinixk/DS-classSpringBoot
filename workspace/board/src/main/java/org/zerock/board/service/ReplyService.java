package org.zerock.board.service;

import java.util.List;

import org.zerock.board.dto.ReplyDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

public interface ReplyService {
  
  Long register(ReplyDTO replyDTO); // 댓글 등록
  List<ReplyDTO> getList(Long bno); // 특정 게시물의 댓글 목록
  void modify(ReplyDTO replyDTO);   // 댓글 수정
  void remove(Long rno);            // 댓글 삭제

  // ReplyDTO를 Reply객체로 변환 Board객체의 처리가 수반
  default Reply dtoToEntity(ReplyDTO dto){
    Board board = Board.builder().bno(dto.getBno()).build();

    Reply reply = Reply.builder()
                  .rno(dto.getRno())
                  .text(dto.getText())
                  .replyer(dto.getReplyer())
                  .board(board)
                  .build();
    return reply;
  }

  // Reply객체를 ReplyDTO로 변환 Board 객체가 필요치 않으므로 게시물 번호만
  default ReplyDTO entityToDto(Reply reply){
    ReplyDTO replyDto = ReplyDTO.builder()
            .rno(reply.getRno())
            .text(reply.getText())
            .replyer(reply.getReplyer())
            .regDate(reply.getRegDate())
            .modDate(reply.getModDate())
            .build();
    return replyDto;
  }

}

package org.zerock.board.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.zerock.board.dto.ReplyDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;
import org.zerock.board.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
  
  private final ReplyRepository repository;

  @Override
  public Long register(ReplyDTO replyDTO) {
    
    Reply reply = dtoToEntity(replyDTO);
    repository.save(reply);
    return reply.getRno();

  }

  @Override
  public List<ReplyDTO> getList(Long bno) {

    List<Reply> result = repository.getRepliesByBoardOrderByRno(Board.builder().bno(bno).build());
    return result.stream().map(reply -> entityToDto(reply)).collect(Collectors.toList());

  }

  @Override
  public void modify(ReplyDTO replyDTO) {
    
    Reply reply = dtoToEntity(replyDTO);
    repository.save(reply);

  }

  @Override
  public void remove(Long rno) {
    
    repository.deleteById(rno);
    
  }

}

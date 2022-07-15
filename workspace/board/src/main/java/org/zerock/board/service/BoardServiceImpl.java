package org.zerock.board.service;

import java.util.Optional;
import java.util.function.Function;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.repository.BoardRepository;
import org.zerock.board.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{
  private final BoardRepository repository;
  private final ReplyRepository replyRepository;

  @Override
  public Long register(BoardDTO dto) {
    log.info("register..."+dto);

    Board board = dtoToEntity(dto);
    repository.save(board);

    return board.getBno();
  }

  @Override
  public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO requestDTO) {
    log.info("BoardServiceImpl getList..."+requestDTO);

    Page<Object[]> result = repository.getBoardWithReplyCount(
      requestDTO.getPageable(Sort.by("bno").descending()));
    
    Function<Object[], BoardDTO> fn = 
      en -> entityToDto((Board)en[0], (Member)en[1], (Long)en[2]);

    return new PageResultDTO<>(result, fn);
  }

  @Override
  public BoardDTO get(Long bno) {
    Object result = repository.getBoardByBno(bno);
    Object[] arr = (Object[])result;

    return entityToDto((Board)arr[0], (Member)arr[1], (Long)arr[2]);
  }

  @Transactional
  @Override
  public void removeWithReplies(Long bno) {
    replyRepository.deleteByBno(bno);
    repository.deleteById(bno);
  }

  @Override
  public void modify(BoardDTO dto) {
    Optional<Board> result = repository.findById(dto.getBno());
    if(result.isPresent()){
      Board board = result.get();

      board.changeTitle(dto.getTitle());
      board.changeContent(dto.getContent());

      repository.save(board);
    }
  }

  @Override
  public void remove(Long gno) {
    log.info("remove.................." + gno);    
    repository.deleteById(gno);
  }

}

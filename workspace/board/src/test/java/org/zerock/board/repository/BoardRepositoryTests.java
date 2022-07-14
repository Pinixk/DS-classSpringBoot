package org.zerock.board.repository;

import java.util.Optional;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;

@SpringBootTest
public class BoardRepositoryTests{
  @Autowired
  BoardRepository repository;

  @Test
  public void insertBoard(){
    
    IntStream.rangeClosed(1, 100).forEach(i->{

      Member member = Member.builder().email("user"+i+"@aaa.com").build();

      Board board = Board.builder()
            .title("Title..."+i)
            .content("Content..."+i)
            .writer(member)
            .build();

      repository.save(board);
    });
    
  }

  // eager loading
  // 연관관계를 가진 모든 엔티티를 같이 로딩
  @Test
  @Transactional  // writer에 lazy loading이 걸려 있기 때문
  public void testRead1(){
    Optional<Board> result = repository.findById(100L);
    Board board = null;
    if(result.isPresent()){
      board = result.get();
      System.out.println(board);
      System.out.println(board.getWriter());
    }  
  }

}

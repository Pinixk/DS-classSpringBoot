package org.zerock.board.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

@SpringBootTest
public class ReplyRepositoryTests {
  @Autowired
  ReplyRepository repository;

  @Test
  public void insertReply(){
    
    IntStream.rangeClosed(1, 300).forEach(i->{

      long bno = (long)(Math.random()*100) + 1;
      Board board = Board.builder().bno(bno).build();

      Reply reply = Reply.builder()
                .text("Reply..."+i)
                .board(board)
                .replyer("guest")
                .build();

      repository.save(reply);
    });

  }

}

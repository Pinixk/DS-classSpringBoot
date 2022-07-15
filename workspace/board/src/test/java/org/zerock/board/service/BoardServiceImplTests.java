package org.zerock.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.BoardDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardServiceImplTests {

  @Autowired
  private BoardService service;

  @Test
  void testRegister() {
    BoardDTO dto = BoardDTO.builder()
                    .title("Test title...")
                    .content("Test content...")
                    .writerEmail("user55@aaa.com")
                    .build();
    Long bno = service.register(dto);
    log.info("service testRegister : "+bno);
  }

}

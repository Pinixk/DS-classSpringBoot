package org.zerock.board.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.ReplyDTO;

@SpringBootTest
public class ReplyServiceTests {

  @Autowired
  ReplyService service;

  @Test
  void testGetList() {
    List<ReplyDTO> result = service.getList(98L);
    result.forEach(dto -> System.out.println(dto));
  }

  @Test
  void testModify() {

  }

  @Test
  void testRegister() {

  }

  @Test
  void testRemove() {

  }
}

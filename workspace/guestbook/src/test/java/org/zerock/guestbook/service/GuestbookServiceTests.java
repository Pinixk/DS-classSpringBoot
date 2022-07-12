package org.zerock.guestbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.dto.GuestbookDTO;

@SpringBootTest
public class GuestbookServiceTests {
  @Autowired
  private GuestbookService service;

  @Test
  void testRegister() {
    GuestbookDTO dto = GuestbookDTO.builder()
                        .title("Sample Title...")
                        .content("Sample Content...")
                        .writer("user0")
                        .build();

    System.out.println("gno:::"+service.register(dto));
  }
}

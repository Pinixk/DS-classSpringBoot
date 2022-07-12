package org.zerock.guestbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;

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

  @Test
  public void testList(){
    PageRequestDTO requestDto = PageRequestDTO.builder()
                          .page(1)
                          .size(10)
                          .build();

    PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(requestDto);

    for (GuestbookDTO dto : resultDTO.getDtoList()) {
      System.out.println(dto);
    }
  }
}

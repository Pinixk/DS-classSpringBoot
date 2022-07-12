package org.zerock.guestbook.service;

import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service // interface를 implement한 class로 자동변환
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {

  private final GuestbookRepository gbRepositoty;
  // 프록시 객체이면서 순환참조를 함
  // final 필수

  @Override
  public Long register(GuestbookDTO dto) {
    log.info("register dto : " + dto);
    Guestbook entity = dtoToEntity(dto);
    gbRepositoty.save(entity);
    return entity.getGno();
  }

}

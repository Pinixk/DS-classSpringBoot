package org.zerock.guestbook.service;

import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service  // interface를 implement한 class로 자동변환
public class GuestbookServiceImpl implements GuestbookService {
  GuestbookRepository gbRepositoty; // 프록시 객체
  @Override
  public Long register(GuestbookDTO dto) {
    Guestbook entity = dtoToEntity(dto);
    return null;
  }
}

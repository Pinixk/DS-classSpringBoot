package org.zerock.guestbook.service;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service // interface를 implement한 class로 자동변환
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {

  private final GuestbookRepository gbRepositoty;
  // 프록시 객체이기 때문에 nullpoint
  // 때문에 값을 받기 위해 순환참조를 함
  // final 필수

  @Override
  public Long register(GuestbookDTO dto) {
    log.info("register dto : " + dto);
    Guestbook entity = dtoToEntity(dto);
    gbRepositoty.save(entity);
    log.info(entity.getGno());
    return entity.getGno();
  }

  @Override
  public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
    Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
    BooleanBuilder booleanBuilder = getSearch(requestDTO);

    Page<Guestbook> result = gbRepositoty.findAll(booleanBuilder ,pageable);
    Function<Guestbook, GuestbookDTO> fn = new Function<Guestbook, GuestbookDTO>() {
      @Override
      public GuestbookDTO apply(Guestbook entity) {
        return entityToDto(entity);
      }
    };
    return new PageResultDTO<>(result, fn);
  }

  @Override
  public GuestbookDTO read(Long gno) {
    Optional<Guestbook> result = gbRepositoty.findById(gno);
    return result.isPresent() ? entityToDto(result.get()) : null;
  }

  @Override
  public void remove(Long gno) {
    log.info("remove.................." + gno);    
    gbRepositoty.deleteById(gno);
  }

  @Override
  public void modify(GuestbookDTO dto) {
    log.info("modify................."+ dto);    
    // Entity를 먼저 찾는 이유 : Entity가 있어야 부분 변경 가능
    Optional<Guestbook> result = gbRepositoty.findById(dto.getGno());
    if(result.isPresent()){
      Guestbook entity = result.get();

      // 부분 변경
      entity.changeTitle(dto.getTitle());
      entity.changeContent(dto.getContent());

      gbRepositoty.save(entity);
    }
  }

  private BooleanBuilder getSearch(PageRequestDTO requestDTO){
    String type = requestDTO.getType();
    String keyword = requestDTO.getKeyword();
    
    // 기본 쿼리
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    QGuestbook qGuestbook = QGuestbook.guestbook; // 관련된 테이블에 대한 쿼리 객체
    BooleanExpression expression = qGuestbook.gno.gt(0L);
    booleanBuilder.and(expression);
    if(type == null || type.trim().length() == 0) return booleanBuilder;  // 검색 조건이 없음

    // 검색 쿼리
    BooleanBuilder conditionBuilder = new BooleanBuilder();
    if(type.contains("t")){conditionBuilder.or(qGuestbook.title.contains(keyword));}
    if(type.contains("c")){conditionBuilder.or(qGuestbook.content.contains(keyword));}
    if(type.contains("w")){conditionBuilder.or(qGuestbook.writer.contains(keyword));}
    booleanBuilder.and(conditionBuilder);

    return booleanBuilder;
  }
}

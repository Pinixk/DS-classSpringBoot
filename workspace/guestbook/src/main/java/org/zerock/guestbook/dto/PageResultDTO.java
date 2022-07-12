package org.zerock.guestbook.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import lombok.Data;

// 두루 두루 쓸 수 있음
@Data
public class PageResultDTO<DTO, EN> {
  private List<DTO> dtoList;

  // Page<EN> result는 복수 개의 데이터가 담김
  public PageResultDTO(Page<EN> result, Function<EN, DTO> fn){
    dtoList = result.stream().map(fn).collect(Collectors.toList());
  }
}

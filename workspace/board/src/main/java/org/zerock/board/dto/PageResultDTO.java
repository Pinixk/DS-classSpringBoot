package org.zerock.board.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Data;

// 두루 두루 쓸 수 있음
@Data
public class PageResultDTO<DTO, EN> {
  private List<DTO> dtoList; // 페이지에 나열되는 목록

  private int totalPage;  // 전체 페이지 수
  private int page;       // 현재 페이지
  private int size;       // 목록 사이즈
  private int start, end; // 페이지 시작, 끝 번호
  private boolean prev, next;     // 이전, 다음
  private List<Integer> pageList; // 페이지 번호 목록

  // Page<EN> result는 복수 개의 데이터가 담김
  public PageResultDTO(Page<EN> result, Function<EN, DTO> fn){
    dtoList = result.stream().map(fn).collect(Collectors.toList());
    totalPage = result.getTotalPages();
    makePageList(result.getPageable());
  }

  private void makePageList(Pageable pageable) {  
    page = pageable.getPageNumber() + 1;              // 현재 페이지
    size = pageable.getPageSize();                    // 페이지 갯수, 사이즈
    int tempEnd = (int)(Math.ceil(page/10.0))*10;     // 계산 되어 끝나는 페이지
    start = tempEnd -9;                               // 시작 페이지
    prev = start > 1;                                 // 이전 페이지
    end = totalPage > tempEnd ? tempEnd : totalPage;  // 실제 끝 페이지
    next = totalPage > tempEnd;                       // 다음 페이지 
    pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList()); // 페이지네이션
  }
}

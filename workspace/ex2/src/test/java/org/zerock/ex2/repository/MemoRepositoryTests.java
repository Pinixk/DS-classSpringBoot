package org.zerock.ex2.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex2.entity.Memo;

@SpringBootTest
public class MemoRepositoryTests {
  @Autowired
  MemoRepository repository; // 프록시 객체(Spring 객체)

  @Test
  public void testClass() {
    System.out.println(repository.getClass().getName()); // 프록시 객체 이름
  }

  @Test // 추가
  public void testInsertDummies() {
    IntStream.rangeClosed(1, 100).forEach(i -> {
      Memo memo = Memo.builder()
          .memoText("Sample......" + i)
          .build();
      repository.save(memo);
    });
  }

  @Test // 찾기
  public void testSelect() {
    Long mno = 100L;
    Optional<Memo> result = repository.findById(mno);
    if (result.isPresent()) {
      Memo memo = result.get();
      System.out.println(memo);
    }
  }

  @Test // 변경
  public void testUpdate() {
    Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();
    System.out.println(repository.save(memo));
  }

  @Test // 삭제
  public void testDelete() {
    Long mno = 100L;
    repository.deleteById(mno);
  }

  @Test // 페이징 처리
  public void testPageDefault() {
    Pageable pageable = PageRequest.of(0, 10); // 0 이상 10 미만을 담겠다
    Page<Memo> result = repository.findAll(pageable);
    System.out.println(result);
    System.out.println(result.getTotalPages()); // 총 페이지 수
    System.out.println(result.getTotalElements()); // 총 데이터 수
    System.out.println(result.getNumber()); // 현재 페이지
    System.out.println(result.getSize()); // 페이지 크기
    System.out.println(result.hasNext()); // 다음 페이지 여부
    System.out.println(result.isFirst()); // 현재 페이지가 처음인지
  }

  @Test // 정렬
  public void testSort() {
    Sort sort1 = Sort.by("mno").descending(); // 내림차순
    Pageable pageable = PageRequest.of(0, 10, sort1);
    Page<Memo> result = repository.findAll(pageable);
    result.get().forEach(t -> System.out.println(t));
  }

  @Test // Query문 정렬
  public void testQueryMethods() {
    List<Memo> list = repository.findByMnoBetweenOrderByMnoDesc(70L, 80L);
    for (Memo memo : list) {
      System.out.println(memo);
    }
  }

  @Test // Query문 페이징
  public void testQueryMethodWithPageable() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
    Page<Memo> result = repository.findByMnoBetween(10L, 50L, pageable);
    result.get().forEach(t -> System.out.println(t));
  }

  @Commit
  @Transactional
  @Test // Query문 삭제
  public void testDeleteQueryMethods() {
    repository.deleteMemoByMnoLessThan(20L);
  }

  @Test // Query @ 정렬
  public void testQuery() {
    List<Memo> list = repository.getListDesc();
    for (var m : list) {
      System.out.println(m);
    }
  }
}

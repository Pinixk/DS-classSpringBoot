package org.zerock.board.repository.search;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
public class SearchBoardRepositoryImplTests {

  @Autowired
  SearchBoardRepository repository;

  @Test
  void testSearchPage() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending().and(Sort.by("title").ascending()));
    Page<Object[]> result = repository.searchPage("t", "1", pageable);
  }
  
}

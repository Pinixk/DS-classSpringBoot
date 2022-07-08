package org.zerock.ex2.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.ex2.entity.Memo;

public interface MemoRepository extends JpaRepository<Memo, Long> {

  // Query Method
  List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

  Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);
  // paging처리 할 때 단일 일 때는 <> 지정 가능
  // 복수일 경우 ObjectArray로 

  void deleteMemoByMnoLessThan(Long mno);

  // Query annotation
  @Query("select m from Memo m order by m.mno desc")
  List<Memo> getListDesc();

}

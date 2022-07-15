package org.zerock.board.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board,Long>{
  
  // Board, Writer
  @Query("select b, w from Board b left join b.writer w where b.bno =:bno ")
  Object getBoardWithWriter(@Param("bno") Long bno);

  // Board, Reply
  @Query("select b, r from Board b left join Reply r on r.board = b where b.bno=:bno")
  List<Object[]> getBoardWithReply(@Param("bno") Long bno);

  // Board, Writer, Reply 갯수
  @Query(value="select b, w, count(r)"
                +"from Board b left join b.writer w "
                +"left join Reply r on r.board = b group by b ",
        countQuery = "select count(b) from Board b")  // 페이징 처리
  Page<Object[]>getBoardWithReplyCount(Pageable pageable);

  // Board, Writer, Reply 갯수
  @Query(value="select b, w, count(r)"
                +"from Board b left join b.writer w "
                +"left outer join Reply r on r.board = b "
                +"where b.bno =:bno")
  Object getBoardByBno(@Param("bno") Long bno);

  
}

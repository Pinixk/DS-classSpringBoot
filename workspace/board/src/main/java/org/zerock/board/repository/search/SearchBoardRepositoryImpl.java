package org.zerock.board.repository.search;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.QBoard;
import org.zerock.board.entity.QMember;
import org.zerock.board.entity.QReply;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
// QuerydslRepositorySupport
// 가변적인 조건처리를 위한 객체
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

  public SearchBoardRepositoryImpl() {
    super(Board.class);
    // Board를 메인으로 하겠다.
  }

  @Override
  public Board search1() {
    log.info("search1...");
    QBoard board = QBoard.board;
    QMember member = QMember.member;
    QReply reply = QReply.reply;

    JPQLQuery<Board> jpqlQuery = from(board); // board를 조건으로 JPQLQuery를 만듬
    jpqlQuery.leftJoin(member).on(board.writer.eq(member)); // board와 member를 join
    jpqlQuery.leftJoin(reply).on(reply.board.eq(board)); // rely를 추가로 join

    // 각 각의 데이터를 추출하는 경우 사용
    JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count());
    tuple.groupBy(board);

    log.info("-----------------------------------");
    log.info(tuple);
    log.info("-----------------------------------");

    List<Board> result = jpqlQuery.fetch(); // 실행
    log.info(result);

    return null;
  }

  @Override
  public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

    log.info("searchPage.....................");

    // 1. 사용하고자 하는 Entit를 QDomain으로 불러온다.
    QBoard board = QBoard.board;
    QMember member = QMember.member;
    QReply reply = QReply.reply;

    // 2. QDomain으로 join
    JPQLQuery<Board> jpqlQuery = from(board); // board를 조건으로 JPQLQuery를 만듬
    jpqlQuery.leftJoin(member).on(board.writer.eq(member)); // board와 member를 join
    jpqlQuery.leftJoin(reply).on(reply.board.eq(board)); // rely를 추가로 join

    // 3. Query 실행
    // Tuple : 동적인 query에 대하여 복합데이터를 추출하는 경우에 사용
    JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());

    // 4. 검색조건 더하기
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    BooleanExpression expression = board.bno.gt(0L);

    booleanBuilder.and(expression);

    // 5. 추가 검색조건 더하기
    if (type != null) {
      String[] typeArr = type.split("");

      BooleanBuilder conditionBuilder = new BooleanBuilder();

      for (String t : typeArr) {
        switch (t) {
          case "t":
            conditionBuilder.or(board.title.contains(keyword));
            break;
          case "w":
            conditionBuilder.or(member.email.contains(keyword));
            break;
          case "c":
            conditionBuilder.or(board.content.contains(keyword));
            break;
        }
      }
      booleanBuilder.and(conditionBuilder);
    }

    // tuple에 조건을 붙임
    tuple.where(booleanBuilder);

    // 정렬
    // 매개변수 pageable로 부터 정렬 파악
    Sort sort = pageable.getSort();
    sort.stream().forEach(new Consumer<Sort.Order>() {

      @Override
      public void accept(Sort.Order order) {
        Order direction = order.isAscending() ? Order.ASC : Order.DESC;
        String prop = order.getProperty(); // bno, title

        PathBuilder orderByExpression = new PathBuilder<>(Board.class, "board");
        tuple.orderBy(new OrderSpecifier<>(direction, orderByExpression.get(prop)));
      }

    });

    // 정렬 후 Board로 그룹핑
    tuple.groupBy(board);

    // page 처리
    tuple.offset(pageable.getOffset());  // 몇 페이지인지
    tuple.limit(pageable.getPageSize()); // 페이지 크기

    // tuple 실행
    List<Tuple> result = tuple.fetch();
    log.info(result);
    Long count = tuple.fetchCount();
    log.info("Count : " + count);

    return new PageImpl<Object[]>(
        result.stream().map(t -> t.toArray()).collect(Collectors.toList()),
        pageable,
        count);

  }
}

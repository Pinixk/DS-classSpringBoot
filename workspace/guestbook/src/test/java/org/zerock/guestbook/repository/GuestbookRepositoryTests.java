package org.zerock.guestbook.repository;

import java.util.Optional;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

@SpringBootTest
public class GuestbookRepositoryTests {
  @Autowired
  private GuestbookRepository repository;

  @Test
  public void insertDummies(){
    IntStream.rangeClosed(1,300).forEach(new IntConsumer() {
      @Override
      public void accept(int i) {
        Guestbook gb = Guestbook.builder()
                        .title("Title..."+i)
                        .content("Content"+i)
                        .writer("user"+(i%10))
                        .build();

        System.out.println(repository.save(gb));
      }
    });
  }

  @Test
  public void updateTest(){
    Optional<Guestbook> result = repository.findById(300L);
    if(result.isPresent()){
      Guestbook gb = result.get();
      gb.changeTitle("Changed Title...");
      gb.changeContent("Changed Content...");

      repository.save(gb);
    }
  }

  @Test
  public void testQuery1(){
    Pageable pageable = PageRequest.of(0,10,Sort.by("gno").descending());

    // 동적인 query를 위해(subquery, join 등)
    QGuestbook qGuestbook = QGuestbook.guestbook;   // 1, Q 도메인을 가져옴
    String keyword = "1";
    BooleanBuilder builder = new BooleanBuilder();  // 2, 질의를 위한 객체
    BooleanExpression expression = qGuestbook.title.contains(keyword);  // 3, 질의 식을 구현
    builder.and(expression);  // 4, 객체가 and로 질의 식을 실행

    Page<Guestbook> result = repository.findAll(builder, pageable);
    result.stream().forEach(gb -> {System.out.println(gb);});
  }
}

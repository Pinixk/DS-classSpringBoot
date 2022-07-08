package org.zerock.ex3.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.LongFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.ex3.dto.SampleDTO;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/sample")
@Log4j2
public class SampleController {
  @GetMapping("/ex1")
  public void ex1(Model model) {
    // void일 때요청된 url과 resource파일이 일치함
    // return 필요하지 않음
    log.info("ex1/.................");

    model.addAttribute("title", "Hello ex1");
  }

  @GetMapping("/ex2")
  public void ex2(Model model) {
    log.info("ex2/.................");
    model.addAttribute("title", "Hello ex2");
    // ("key","value")
    
    List<SampleDTO> list = IntStream.rangeClosed(1, 20)
        .asLongStream()
        .mapToObj(new LongFunction<SampleDTO>() {
          @Override
          public SampleDTO apply(long i) {
            SampleDTO dto = SampleDTO.builder()
                .sno(i)
                .first("First..." + i)
                .last("Last..." + i)
                .regTime(LocalDateTime.now())
                .build();

            return dto;
          }
        }).collect(Collectors.toList());

    model.addAttribute("list", list);
    // return "/sample/ex1"; // 경로 표시
  }
}

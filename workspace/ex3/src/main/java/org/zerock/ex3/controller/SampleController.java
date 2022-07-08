package org.zerock.ex3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/sample")
@Log4j2
public class SampleController {
  @GetMapping("/ex1")
  public void ex1(){  
    // void일 때 Mapping과 똑같이 설정해줘야 함
    // return 필요하지 않음
    log.info("ex1/.................");
  }
  
  @GetMapping("/ex2")
  public String ex2(){
    log.info("ex1/.................");
    return "/sample/ex1"; // 경로 표시
  }
}

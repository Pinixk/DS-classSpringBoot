package org.zerock.ex1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Text, Json 등을 돌려주기 위함
public class SampleController {
  @GetMapping("/hello")
  public String[] hello() {
    return new String[] { "hello", "world" };
  }
}

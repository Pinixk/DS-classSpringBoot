package org.zerock.guestbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.guestbook.service.GuestbookService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("guestbook")
@Log4j2
@AllArgsConstructor // 생성자를 초기화 시킴
public class GuestbookController {
  private GuestbookService gbService;

  @GetMapping({ "/", "/list" })
  public String list() {
    log.info("list.................");
    return "/guestbook/list";
  }
}
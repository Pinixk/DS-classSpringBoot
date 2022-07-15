package org.zerock.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/board/")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
  private final BoardService service;

  @GetMapping({"/list",""})
  public void list(Model model, @ModelAttribute("requestDTO") PageRequestDTO requestDTO) {
    log.info("list..."+requestDTO);
    model.addAttribute("result", service.getList(requestDTO));
  }
}

package org.zerock.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.board.dto.ReplyDTO;
import org.zerock.board.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController // Json 형태로 객체 데이터를 반환
@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

  private final ReplyService service;

  @GetMapping(value = "/board/{bno}", produces = MediaType.APPLICATION_JSON_VALUE) // JSON 파일로 생산함
  public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno") Long bno) {
      // ResponseEntity : JSON을 담을 수 잇는 객체        @PathVariable : 주소로 넘어오는걸 바로 받음

    log.info("bno : " + bno);
    return new ResponseEntity<>(service.getList(bno), HttpStatus.OK);

  }

}

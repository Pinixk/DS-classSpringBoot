package org.zerock.guestbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.service.GuestbookService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Controller
@RequestMapping("guestbook")
@Log4j2
@AllArgsConstructor // 생성자를 초기화 시킴
public class GuestbookController {
  private final GuestbookService gbService;

  @GetMapping({ "/" })
  public String index() {
    log.info("index.................");
    return "redirect:/guestbook/list";
  }

  @GetMapping({ "/list" })
  public void list(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
    log.info("list.................");
    model.addAttribute("result", gbService.getList(requestDTO));
  }

  @GetMapping("/register")
  public void register() {
    log.info("register get..............");}
  @PostMapping("/register")
  public String registPost(GuestbookDTO dto, RedirectAttributes ra) { // 커맨드 객체
    // RedirectAttributes 모델처럼 다음 객체에게 정보를 전달 할 수 있음
    log.info("register post.................");
    Long gno = gbService.register(dto);
    ra.addFlashAttribute("msg", gno+" 등록"); // 중복으로 값이 오는 것을 막기 위해
    return "redirect:/guestbook/list"; // /list에서 받은 값을 간단히 다시 쓰기 위해 resource로 보내지 않고 주소로 보냄
  }

  @GetMapping({"/read", "/modify"})
  public void read(Long gno, Model model,
      @ModelAttribute("requestDTO") PageRequestDTO requestDTO) { // 커맨드 객체
      // @ModelAttribute 변수명을 임의로 지정
      // 커맨드 객체로 받은 것은 다음 페이지에도 넘어감.
      
    log.info("read................ gno : " + gno);
    GuestbookDTO dto = gbService.read(gno);
    model.addAttribute("dto", dto);
  }

  @PostMapping("/remove")
  public String remove(Long gno, RedirectAttributes ra, PageRequestDTO requestDTO){
    log.info("remove.............." + gno);
    gbService.remove(gno);
    ra.addFlashAttribute("msg", gno+" 삭제");
    ra.addAttribute("page", requestDTO.getPage());
    ra.addAttribute("type", requestDTO.getType());
    ra.addAttribute("keyword", requestDTO.getKeyword());
    return "redirect:/guestbook/list";
  }

  @PostMapping("/modify")
  public String modifyPost(GuestbookDTO dto, RedirectAttributes ra, PageRequestDTO requestDTO){
    log.info("modify................" + dto);
    gbService.modify(dto);
    ra.addFlashAttribute("msg", dto.getGno()+" 수정");
    ra.addAttribute("gno", dto.getGno());
    ra.addAttribute("page", requestDTO.getPage());
    ra.addAttribute("type", requestDTO.getType());
    ra.addAttribute("keyword", requestDTO.getKeyword());
    return "redirect:/guestbook/read";
  }
  
}
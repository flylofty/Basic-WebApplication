package simplewebapplication.springwebapplication.web.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import simplewebapplication.springwebapplication.dto.board.ResponseBoard;
import simplewebapplication.springwebapplication.web.form.WriteForm;
import simplewebapplication.springwebapplication.service.board.BoardService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public String findBoards(@RequestParam(required = false) Integer page,
                             @RequestParam(required = false) Integer count,
                             Model model)
    {

        if (page == null) {
            page = 1;
        }

        if (count == null) {
            count = 10;
        }

        log.info("Page Number = {}", page);
        log.info("Count = {}", count);

        // 원하는 page 번호에 count 수 만큼 게시글 목록을 한 페이지에 보임
        // 정렬된 count 개수 만큼의 게시글 목록 정보 리스트
        // 전체 게시글 수를 담은 변수
        List<ResponseBoard> boards = boardService.findBoards(page, count);
        model.addAttribute("boards", boards);
        return "boards/boards";
    }

    @GetMapping("/write")
    public String openWriteForm() {
        log.info("BoardController.openWriteForm");
        return "boards/writeForm";
    }

    @PostMapping("/write")
    public String saveWriteForm(WriteForm write) {
        return "redirect:/boards";
    }

    @GetMapping("/{boardId}")
    public String findBoard(@PathVariable Long boardId, Model model) {
        ResponseBoard board = boardService.findBoard(boardId);
        model.addAttribute("board", board);
        return "boards/board";
    }

    @DeleteMapping("/{boardId}")
    public String deleteBoard(@PathVariable Long boardId) {
        // 게시글 삭제
        return "redirect:/boards";
    }
}

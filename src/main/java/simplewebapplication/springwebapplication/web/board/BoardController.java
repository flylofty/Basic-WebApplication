package simplewebapplication.springwebapplication.web.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import simplewebapplication.springwebapplication.dto.board.ResponseBoard;
import simplewebapplication.springwebapplication.dto.board.WriteFormDTO;
import simplewebapplication.springwebapplication.service.board.BoardService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public String firstVisit(Model model) {
        List<ResponseBoard> boards = boardService.findBoards(0, 0, 0);
        model.addAttribute("boards", boards);
        return "boards/boards";
    }

    @GetMapping("/write")
    public String openWriteForm() {
        System.out.println("BoardController.openWriteForm");
        return "boards/writeForm";
    }

    @PostMapping("/write")
    public String saveWriteForm(WriteFormDTO write) {
        return "redirect:/boards";
    }

    @GetMapping("/{boardId}")
    public String openBoard(@PathVariable Long boardId, Model model) {
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

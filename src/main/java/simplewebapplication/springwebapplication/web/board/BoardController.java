package simplewebapplication.springwebapplication.web.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import simplewebapplication.springwebapplication.dto.board.ResponseBoard;
import simplewebapplication.springwebapplication.dto.board.WriteFormDTO;
import simplewebapplication.springwebapplication.service.board.BoardService;

import java.util.List;

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
        return "boards/write_form";
    }

    @PostMapping("/write")
    public String saveWriteForm(WriteFormDTO write) {
        return "redirect:/boards";
    }

    @GetMapping("/{boardId}")
    public String openBoardPage(@PathVariable Long boardId, Model model) {
        ResponseBoard board = boardService.findBoard(boardId);
        model.addAttribute("board", board);
        return "boards/board";
    }
}

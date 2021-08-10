package simplewebapplication.springwebapplication.web.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import simplewebapplication.springwebapplication.domain.user.User;
import simplewebapplication.springwebapplication.dto.board.ResponseBoard;
import simplewebapplication.springwebapplication.web.SessionConst;
import simplewebapplication.springwebapplication.web.form.WriteForm;
import simplewebapplication.springwebapplication.service.board.BoardService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
        List<ResponseBoard> boards = boardService.findBoardPageList(page);
        model.addAttribute("boards", boards);
        return "boards/boards";
    }

    @GetMapping("/write")
    public String openWriteForm(@ModelAttribute("form") WriteForm form) {
        return "boards/writeForm";
    }

    @PostMapping("/write")
    public String saveWriteForm(@ModelAttribute(name = "form") WriteForm form, BindingResult bindingResult,
                                HttpServletRequest request)
    {
        // 1. 제목과 내용은 공백일 수 없음 == Validation
        if (!StringUtils.hasText(form.getTitle())) {
            bindingResult.addError(new FieldError("form", "title", "제목을 입력해주세요."));
        }

        if (!StringUtils.hasText(form.getContent())) {
            bindingResult.addError(new FieldError("form", "content", "내용을 입력해주세요."));
        }

        if (bindingResult.hasErrors()) {
            return "boards/writeForm";
        }

        // 2. 작성자를 UserService 에서 찾아와야함
        // 세션에 담을 정보를 간소화 해야할 필요가 있음.
        HttpSession session = request.getSession(false);
        User sessionUser = (User) session.getAttribute(SessionConst.LOGIN_USER);

        // 3. Board 를 만들고 BoardService 를 통해 새로운 게시글을 만듦
        Long boardId = boardService.createBoard(form.makeBoard(sessionUser));
        return "redirect:/boards/" + boardId.toString();
    }

    @GetMapping("/{boardId}")
    public String findBoard(@PathVariable Long boardId, Model model) {
        ResponseBoard board = boardService.findBoard(boardId);
        model.addAttribute("board", board);
        //게시글 개행 처리
        String nlString = System.getProperty("line.separator").toString();
        model.addAttribute("nlString", nlString);
        return "boards/board";
    }

    @DeleteMapping("/{boardId}")
    public String deleteBoard(@PathVariable Long boardId) {
        // 게시글 삭제
        log.info("삭제할 게시글 번호 = {}", boardId);
        boardService.deleteBoard(boardId);
        return "redirect:/boards";
    }
}

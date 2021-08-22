package simplewebapplication.springwebapplication.web.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import simplewebapplication.springwebapplication.domain.comment.Comment;
import simplewebapplication.springwebapplication.domain.user.User;
import simplewebapplication.springwebapplication.dto.board.ResponseBoard;
import simplewebapplication.springwebapplication.dto.comment.RequestCommentLevelOne;
import simplewebapplication.springwebapplication.service.comment.CommentService;
import simplewebapplication.springwebapplication.web.SessionConst;
import simplewebapplication.springwebapplication.web.form.CommentForm;
import simplewebapplication.springwebapplication.web.form.WriteForm;
import simplewebapplication.springwebapplication.service.board.BoardService;
import simplewebapplication.springwebapplication.web.pagination.BoardPagination;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping
    public String findBoards(@RequestParam(required = false) Long page,
                             @RequestParam(required = false) Integer number,
                             Model model)
    {
        if (page == null) {
            page = 1L;
        }

        if (number == null) {
            number = 10;
        }

        List<ResponseBoard> boards = boardService.findBoardPageList(page);
        BoardPagination pagination = boardService.createBoardPagination(page, number);

        model.addAttribute("boards", boards);
        model.addAttribute("pagination", pagination);
        return "boards/boards";
    }

    @GetMapping("/write")
    public String openWriteForm(@ModelAttribute("form") WriteForm form) {
        return "boards/writeForm";
    }

    @PostMapping("/write")
    public String saveWriteForm(@ModelAttribute(name = "form") WriteForm form,
                                BindingResult bindingResult,
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
    public String findBoard(@ModelAttribute(name = "commentLevelOne") CommentForm commentLevelOne,
                            BindingResult bindingResult,
                            @PathVariable Long boardId, Model model,
                            HttpServletRequest request)
    {
        // 게시글 객체
        ResponseBoard board = boardService.findBoard(boardId);
        model.addAttribute("board", board);

        // 댓글 Level 1 객체 (Depth 1, 댓글)
        //model.addAttribute("commentLevelOne", new CommentForm(boardId));
        commentLevelOne.setBoardId(boardId);
        // 댓글 Level 2 객체 (Depth 2, 대댓글)
        //model.addAttribute("commentLevelTwo", new CommentForm(boardId));

        // comment 에러 확인
        Boolean isLevelOneErr = (Boolean) model.asMap().get("levelOneErr");
        if (isLevelOneErr != null && isLevelOneErr) {
            //bindingResult.addError(new FieldError("commentValid", "levelOneErr", "내용을 입력해주세요"));
            model.addAttribute("levelOneErr", "내용을 입력해주세요");
        }

        List<RequestCommentLevelOne> commentLevelOneList = commentService.findCommentLevelOneList(boardId);
        model.addAttribute("commentLevelOneList", commentLevelOneList);

        //게시글 개행 처리
        //String nlString = System.getProperty("line.separator").toString();
        String nlString = System.getProperty("line.separator");
        model.addAttribute("nlString", nlString);

        // 이전 게시글 page URL
        model.addAttribute("prevPage", request.getHeader("Referer"));

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

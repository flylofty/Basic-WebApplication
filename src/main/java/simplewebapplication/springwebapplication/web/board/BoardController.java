package simplewebapplication.springwebapplication.web.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import simplewebapplication.springwebapplication.domain.user.User;
import simplewebapplication.springwebapplication.dto.board.ResponseBoard;
import simplewebapplication.springwebapplication.web.form.CommentLevelOneForm;
import simplewebapplication.springwebapplication.service.comment.CommentService;
import simplewebapplication.springwebapplication.service.like.LikeService;
import simplewebapplication.springwebapplication.web.SessionConst;
import simplewebapplication.springwebapplication.web.form.CommentForm;
import simplewebapplication.springwebapplication.web.form.LikeForm;
import simplewebapplication.springwebapplication.web.form.WriteForm;
import simplewebapplication.springwebapplication.service.board.BoardService;
import simplewebapplication.springwebapplication.web.pagination.BoardPagination;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;
    private final LikeService likeService;

    @GetMapping
    public String findBoards(@RequestParam(required = false) Long page,
                             @RequestParam(required = false) Integer number,
                             @RequestParam(required = false, name = "searchWord") String searchWord,
                             Model model)
    {

        if (searchWord == null) {
            searchWord = "";
        }

        if (page == null) {
            page = 1L;
        }

        if (number == null) {
            number = 10;
        }

        List<ResponseBoard> boards = boardService.findBoardPageList(page, searchWord);

        if (boards.isEmpty()) {
            number = 0;
        }

        BoardPagination pagination = boardService.createBoardPagination(page, number, searchWord);

        model.addAttribute("boards", boards);
        model.addAttribute("pagination", pagination);
        return "boards/boards";
    }

    @GetMapping("/write")
    public String openWriteForm(@ModelAttribute("form") WriteForm form, Model model) {

        Long boardId = (Long) model.asMap().get("rewrite");

        if (boardId != null) {
            ResponseBoard board = boardService.findBoard(boardId, true);
            form.responseBoardToWriteForm(board);
        }

        return "boards/writeForm";
    }

    @PostMapping("/{boardId}/write")
    public String reWriteForm(@PathVariable Long boardId,
                              RedirectAttributes redirectAttributes)
    {
        redirectAttributes.addFlashAttribute("rewrite", boardId);
        return "redirect:/boards/write";
    }

    @PostMapping("/write")
    public String saveWriteForm(@ModelAttribute(name = "form") WriteForm form,
                                BindingResult bindingResult, HttpServletRequest request)
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

        Long boardId = 0L;
        if (form.getBoardId() == null) {
            // 2. 작성자를 UserService 에서 찾아와야함
            // 세션에 담을 정보를 간소화 해야할 필요가 있음.
            HttpSession session = request.getSession(false);
            User sessionUser = (User) session.getAttribute(SessionConst.LOGIN_USER);

            // 3. Board 를 만들고 BoardService 를 통해 새로운 게시글을 만듦
            boardId = boardService.createBoard(form.makeBoard(sessionUser));
        }
        else {
            boardService.updateBoard(form.getBoardId(), form.getContent());
            boardId = form.getBoardId();
        }

        return "redirect:/boards/" + boardId.toString();
    }

    @GetMapping("/{boardId}")
    public String findBoard(@ModelAttribute(name = "commentLevelOne") CommentForm commentLevelOne, BindingResult bindingResult,
                            @PathVariable Long boardId, Model model,
                            HttpServletResponse response, HttpServletRequest request)
    {
        // 게시글 객체
        ResponseBoard board = boardService.findBoard(boardId, viewDuplicateCheck(boardId, request, response));
        model.addAttribute("board", board);

        //게시글 개행 처리
        //String nlString = System.getProperty("line.separator").toString();
        String nlString = System.getProperty("line.separator");
        model.addAttribute("nlString", nlString);

        // 좋아요
        HttpSession session = request.getSession(false);
        User sessionUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        LikeForm likeForm = likeService.findLikeId(sessionUser, boardId);
        model.addAttribute("likeForm", likeForm);

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

        List<CommentLevelOneForm> commentLevelOneList = commentService.findCommentLevelOneList(boardId);
        model.addAttribute("commentLevelOneList", commentLevelOneList);

        // 이전 게시글 page URL, 개선해야할 점 많음...ㅠㅠ
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

    @PostMapping("{boardId}/like")
    public String likeBoard(@ModelAttribute(name = "likeForm") LikeForm form,
                            @PathVariable Long boardId,
                            HttpServletRequest request)
    {
        // 좋아요를 수행한 경우
        if (form.isPressedLikeButton()) {
            HttpSession session = request.getSession(false);
            User sessionUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
            likeService.createLike(sessionUser, boardId);
        }
        else { // 좋아요를 취소한 경우
            likeService.deleteById(form.getLikeId());
        }

        return "redirect:/boards/" + boardId;
    }

    private boolean viewDuplicateCheck(Long boardId, HttpServletRequest request, HttpServletResponse response) {

        // 조회 중복 방지 로직
        Cookie[] cookies = request.getCookies();
        Map map = new HashMap();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                map.put(cookie.getName(), cookie.getValue());
            }
        }

        String readCount = (String) map.get("read_count");
        String newReadCount = "|" + boardId;

        boolean isVisited = true;

        if (readCount == null || !readCount.contains(newReadCount)) {

            if (readCount == null) {
                readCount = "";
            }

            Cookie newCookie = new Cookie("read_count", readCount + newReadCount);
            response.addCookie(newCookie);
            isVisited = false;
        }
        return isVisited;
    }
}
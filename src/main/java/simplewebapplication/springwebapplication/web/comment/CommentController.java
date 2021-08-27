package simplewebapplication.springwebapplication.web.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import simplewebapplication.springwebapplication.domain.user.User;
import simplewebapplication.springwebapplication.service.board.BoardService;
import simplewebapplication.springwebapplication.service.comment.CommentService;
import simplewebapplication.springwebapplication.web.SessionConst;
import simplewebapplication.springwebapplication.web.form.CommentForm;
import simplewebapplication.springwebapplication.web.form.CommentModifyForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards/{boardId}/comments")
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;

    @PostMapping
    public String modifyLevelOneComment(CommentModifyForm form,
                                        @PathVariable Long boardId)
    {
        // 내용이 있을 경우만 처리
        if (StringUtils.hasText(form.getCommentContent())) {
            commentService.updateComment(form.getCommentId(), form.getCommentContent());
        }

        return "redirect:/boards/" + boardId;
    }

    @DeleteMapping
    public String deleteLevelOneComment(CommentModifyForm form,
                                        @PathVariable Long boardId)
    {
        log.info("게시글 댓글 {}을 삭제하겠습니다", form.getCommentId());

        commentService.deleteComment(form.getCommentId());

        log.info("게시글 {}로 돌아가겠습니다", boardId);
        return "redirect:/boards/" + boardId;
    }

    @PostMapping("/level1")
    public String createLevelOneComment(@ModelAttribute(name = "commentLevelOne") CommentForm form,
                                        RedirectAttributes redirectAttributes,
                                        @PathVariable Long boardId,
                                        HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "redirect:/boards/" + boardId;
        }

        // 댓글 검증
        if (!StringUtils.hasText(form.getContent())) {
            redirectAttributes.addFlashAttribute("levelOneErr", true);
            return "redirect:/boards/" + boardId;
        }

        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        form.initCommentForm(1, boardId, loginUser.getId());

        commentService.createComment(form);

        return "redirect:/boards/{boardId}";
    }
}

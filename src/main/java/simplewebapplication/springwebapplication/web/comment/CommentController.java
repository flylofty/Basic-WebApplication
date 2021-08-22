package simplewebapplication.springwebapplication.web.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import simplewebapplication.springwebapplication.domain.user.User;
import simplewebapplication.springwebapplication.dto.board.ResponseBoard;
import simplewebapplication.springwebapplication.service.board.BoardService;
import simplewebapplication.springwebapplication.service.comment.CommentService;
import simplewebapplication.springwebapplication.web.SessionConst;
import simplewebapplication.springwebapplication.web.form.CommentForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards/{boardId}/comments")
public class CommentController {

//    private final CommentService commentService;
    private final BoardService boardService;

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

        {
            User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
            form.initCommentForm(1, boardId, loginUser.getId());
        }

        //commentService.createComment(form);

        return "redirect:/boards";
        //return "redirect:/boards/{boardId}";
    }
}

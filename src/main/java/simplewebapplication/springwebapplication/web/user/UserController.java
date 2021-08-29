package simplewebapplication.springwebapplication.web.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import simplewebapplication.springwebapplication.domain.user.User;
import simplewebapplication.springwebapplication.service.board.BoardService;
import simplewebapplication.springwebapplication.service.comment.CommentService;
import simplewebapplication.springwebapplication.web.SessionConst;
import simplewebapplication.springwebapplication.web.form.ChangePasswordForm;
import simplewebapplication.springwebapplication.web.form.DeleteForm;
import simplewebapplication.springwebapplication.web.form.UserJoinForm;
import simplewebapplication.springwebapplication.web.form.UserLoginForm;
import simplewebapplication.springwebapplication.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final BoardService boardService;
    private final CommentService commentService;

    // 로그인 페이지로 이동
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("form") UserLoginForm form) {
        return "users/sign_in";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }

    // 로그인
    @PostMapping("/login")
    public String login(@ModelAttribute(name = "form") UserLoginForm form, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes, Model model,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request)
    {
        // 검증 로직

        // 아이디 기입 확인
        if (form.getId().isEmpty()|| form.getPassword().isEmpty()) {
            bindingResult.addError(new ObjectError("form", "아이디와 비밀번호를 입력해주세요."));
        }

        if (bindingResult.hasErrors()) {
            log.info("아이디와 비밀번호를 입력해주세요.");
            return "users/sign_in";
        }

        User loginUser = userService.login(form.getId(), form.getPassword());

        if (loginUser == null) {
            log.info("존재하지 않는 회원!!");
            bindingResult.addError(new ObjectError("form", "아이디와 비밀번호를 확인해주세요."));
            return "users/sign_in";
        }

        SessionUser sessionUser = new SessionUser(loginUser.getId(), loginUser.getRole().toString());

        // 로그인 성공 처리
        // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보를 보관
        session.setAttribute(SessionConst.LOGIN_USER, sessionUser);

        return "redirect:" + redirectURL;
    }

    // 회원가입 페이지로 이동
    @GetMapping("/join")
    public String userJoin(Model model) {
        model.addAttribute("joinInfo", new UserJoinForm());
        return "users/sign_up";
    }

    // 회원가입
    @PostMapping("/join")
    public String checkUserJoin(@ModelAttribute(name = "joinInfo") UserJoinForm form, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, Model model)
    {
        // 검증 로직

        // 아이디 확인
        if (!StringUtils.hasText(form.getUserId())) {
            log.info("유저 아이디 = {}", form.getUserId());
            bindingResult.addError(new FieldError("joinInfo", "userId", "회원 아이디는 필수 입니다"));
        }
        else if (7 > form.getUserId().length() || form.getUserId().length() > 15) {
            log.info("유저 아이디 글자수 = {}", form.getUserId().length());
            bindingResult.addError(new FieldError("joinInfo", "userId", "아이디는 7자 이상 15자 이하 입니다"));
        }

        // 비밀번호 확인
        if (!StringUtils.hasText(form.getPassword()) || !StringUtils.hasText(form.getRePassword())) {
            log.info("유저 비번 = {}", form.getPassword());
            log.info("유저 재비번 = {}", form.getRePassword());

            if (!StringUtils.hasText(form.getPassword())) {
                bindingResult.addError(new FieldError("joinInfo", "password", "비밀번호는 필수 입니다"));
            }

            if (!StringUtils.hasText(form.getRePassword())) {
                bindingResult.addError(new FieldError("joinInfo", "rePassword", "비밀번호는 필수 입니다"));
            }

        } else if (!form.getPassword().equals(form.getRePassword())) {
            bindingResult.addError(new FieldError("joinInfo", "rePassword", "입력하신 비밀번호가 일치하지 않습니다"));
        }

        // 이메일 확인
        if (!StringUtils.hasText(form.getEmail())) {
            log.info("유저 이메일 = {}", form.getEmail());
            bindingResult.addError(new FieldError("joinInfo", "email", "회원 이메일은 필수 입니다"));
        }
        else if (!isValidEmail(form.getEmail())) {
            bindingResult.addError(new FieldError("joinInfo", "email", "유효한 이메일 형식이 아닙니다"));
        }

        // 검증 실패 시 입력 폼으로 되돌림
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "users/sign_up";
        }

        // 성공 로직
        // 회원가입 회원정보 저장 로직 미구현

        String joinUser = userService.join(form.createUser());

        log.info("joinUser = {}", joinUser);

        if (joinUser == null) {
            bindingResult.addError(new FieldError("joinInfo", "userId", "사용할 수 없는 아이디 입니다."));
        }

        // 검증 실패
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "users/sign_up";
        }

        return "redirect:../";
    }

    // 마이페이지로 이동
    @GetMapping("/myInfo")
    public String myPage(HttpServletRequest request, Model model) {
        //User findUser = userService.findUser(userId);

        HttpSession session = request.getSession(false);
        SessionUser sessionUser = (SessionUser) session.getAttribute(SessionConst.LOGIN_USER);

        User user = userService.findUser(sessionUser.getId());
        Long boardCount = boardService.findBoardCount(sessionUser.getId());
        List<Long> commentCountList = commentService.findCommentCount(sessionUser.getId());

        model.addAttribute("id", user.getId());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("boardCount", boardCount);
        model.addAttribute("commentCountByPost", commentCountList.get(0));
        model.addAttribute("commentCount", commentCountList.get(1));
        return "users/myPage";
    }

    @GetMapping("/myInfo/changePw")
    public String changePasswordForm(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        SessionUser sessionUser = (SessionUser) session.getAttribute(SessionConst.LOGIN_USER);

        model.addAttribute("changeForm", new ChangePasswordForm(sessionUser.getId()));
        return "users/changeForm";
    }

    @PostMapping("/myInfo/changePw")
    public String changePassword(@ModelAttribute(name = "changeForm") ChangePasswordForm form,
                                 BindingResult bindingResult)
    {
        // 검증 로직
        // 현재 비밀번호 확인
        log.info("변경// 유저 아이디 = {}",form.getUserId());
        log.info("변경// 현재 비밀번호 = {}",form.getCurrentPassword());
        if (!StringUtils.hasText(form.getCurrentPassword())) {
            bindingResult.addError(new FieldError("changeForm",
                    "currentPassword", "현재 비밀번호를 입력해주세요"));
        }
        else if (userService.confirmCurrentPassword(form.getUserId(), form.getCurrentPassword())) {
            bindingResult.addError(new FieldError("changeForm",
                    "currentPassword", "비밀번호가 틀렸습니다"));
        }

        // 새로입력한 비밀번호 인증
        log.info("변경// 새로운 유저 아이디 = {}",form.getNewPassword());
        log.info("변경// 새로운 유저 아이디 한 번 더 = {}",form.getRePassword());
        if (!StringUtils.hasText(form.getNewPassword()) || !StringUtils.hasText(form.getRePassword())) {

            if (!StringUtils.hasText(form.getNewPassword())) {
                bindingResult.addError(new FieldError("changeForm",
                        "newPassword", "새로운 비밀번호를 입력해주세요"));
            }

            if (!StringUtils.hasText(form.getRePassword())) {
                bindingResult.addError(new FieldError("changeForm",
                        "rePassword", "새로운 비밀번호를 입력해주세요"));
            }
        }
        else if (!form.getNewPassword().equals(form.getRePassword())) {
            bindingResult.addError(new FieldError("changeForm",
                    "rePassword", "새로운 비밀번호가 일치하지 않습니다"));
        }

        if (bindingResult.hasErrors()) {
            log.info("PwForm errors = {}", bindingResult);
            return "users/changeForm";
        }

        userService.changePassword(form.getUserId(), form.getNewPassword());

        return "redirect:/users/myInfo";
    }

    @GetMapping("/myInfo/delete")
    public String deleteForm(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        SessionUser sessionUser = (SessionUser) session.getAttribute(SessionConst.LOGIN_USER);

        model.addAttribute("deleteForm", new DeleteForm(sessionUser.getId()));
        return "users/deleteForm";
    }

    @PostMapping("/myInfo/delete")
    public String deleteAccount(@ModelAttribute(name = "deleteForm") DeleteForm form,
                                BindingResult bindingResult)
    {
        if (!StringUtils.hasText(form.getConfirmPassword())) {
            bindingResult.addError(new FieldError("deleteForm",
                    "confirmPassword", "비밀번호를 입력해주세요"));
        }

        // 입력한 비밀번호가 같지 않을 경우
        if (userService.confirmCurrentPassword(form.getUserId(), form.getConfirmPassword())) {
            bindingResult.addError(new FieldError("deleteForm",
                    "confirmPassword", "비밀번호가 정확하지 않습니다"));
        }

        if (bindingResult.hasErrors()) {
            log.info("deleteAccount errors = {}", bindingResult);
            return "users/deleteForm";
        }

        userService.deleteAccount(form.getUserId());
        return "redirect:/users/logout";
    }

    public static boolean isValidEmail(String email) {
        // 출처 : http://develop.sunshiny.co.kr/592
        boolean err = false; String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            err = true;
        }
        return err;
    }
}
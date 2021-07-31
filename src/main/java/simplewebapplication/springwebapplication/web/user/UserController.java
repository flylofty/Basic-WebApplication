package simplewebapplication.springwebapplication.web.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import simplewebapplication.springwebapplication.domain.user.User;
import simplewebapplication.springwebapplication.web.form.UserJoinForm;
import simplewebapplication.springwebapplication.web.form.UserLoginForm;
import simplewebapplication.springwebapplication.service.user.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 로그인 페이지로 이동
    @GetMapping("/login")
    public String userLogin(Model model) {
        model.addAttribute("form", new UserLoginForm());
        return "users/sign_in";
    }

    // 로그인
    @PostMapping("/login")
    public String checkUserLogin(@ModelAttribute(name = "form") UserLoginForm form, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes, Model model)
    {

        //Map<String, String> errors = new HashMap<>();

        // 검증 로직

        // 아이디 기입 확인
        if (form.getId().isEmpty()|| form.getPassword().isEmpty()) {
            bindingResult.addError(new ObjectError("form", "아이디와 비밀번호를 입력해주세요."));
        }

        if (bindingResult.hasErrors()) {
            log.info("아이디와 비밀번호를 입력해주세요.");
            return "users/sign_in";
        }

        User user = userService.findUser(form.getId());

        if (user == null || !user.getPassword().equals(form.getPassword())) {
            log.info("존재하지 않는 회원!!");
            //errors.put("loginErr", "아이디와 비밀번호를 확인해주세요.");
            bindingResult.addError(new ObjectError("form", "아이디와 비밀번호를 확인해주세요."));
            return "users/sign_in";
        }

//        if (!user.getPassword().equals(form.getPassword())) {
//            log.info("비밀번호가 틀렸습니다.");
//            //errors.put("loginErr", "아이디와 비밀번호를 확인해주세요.");
//            //model.addAttribute("errors", errors);
//            return "users/sign_in";
//        }

        return "redirect:../";
    }

    // 회원가입 페이지로 이동
    @GetMapping("/join")
    public String userJoin(Model model) {
        model.addAttribute("joinInfo", new UserJoinForm());
        return "users/sign_up";
    }

    // 회원가입
    @PostMapping("/join")
    public String checkUserJoin(@ModelAttribute(name = "joinInfo") UserJoinForm joinInfo, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, Model model)
    {

        //Map<String, String> errors = new HashMap<>();

        // 검증 로직

        // 아이디 확인
        if (!StringUtils.hasText(joinInfo.getUserId())) {
            log.info("유저 아이디 = {}", joinInfo.getUserId());
            //errors.put("userId", "회원 아이디는 필수 입니다.");
            bindingResult.addError(new FieldError("joinInfo", "userId", "회원 아이디는 필수 입니다."));
        }
        if (7 > joinInfo.getUserId().length() || joinInfo.getUserId().length() > 15) {
            log.info("유저 아이디 글자수 = {}", joinInfo.getUserId().length());
            //errors.put("userId", "아이디의 글자 수는 7자 이상 15자 이하 입니다.");
            bindingResult.addError(new FieldError("joinInfo", "userId", "아이디의 글자 수는 7자 이상 15자 이하 입니다."));
        }

        // 비밀번호 확인
        if (!StringUtils.hasText(joinInfo.getPassword()) || !StringUtils.hasText(joinInfo.getRePassword())) {
            log.info("유저 비번 = {}", joinInfo.getPassword());
            log.info("유저 재비번 = {}", joinInfo.getRePassword());
            //errors.put("rePassword", "회원 비밀 번호는 필수 입니다.");
            bindingResult.addError(new FieldError("joinInfo", "rePassword", "회원 비밀번호는 필수 입니다."));
        } else if (!joinInfo.getPassword().equals(joinInfo.getRePassword())) {
            //errors.put("rePassword", "입력하신 비밀번호가 일치하지 않습니다.");
            bindingResult.addError(new FieldError("joinInfo", "rePassword", "입력하신 비밀번호가 일치하지 않습니다."));
        }

        // 이메일 확인
        if (!StringUtils.hasText(joinInfo.getEmail())) {
            log.info("유저 이메일 = {}", joinInfo.getEmail());
            //errors.put("userEmail", "회원 이메일은 필수 입니다.");
            bindingResult.addError(new FieldError("joinInfo", "email", "회원 이메일은 필수 입니다."));
        }
        else if (!isValidEmail(joinInfo.getEmail())) {
            //errors.put("userEmail", "유효한 이메일 형식이 아닙니다.");
            bindingResult.addError(new FieldError("joinInfo", "email", "유효한 이메일 형식이 아닙니다."));
        }

        // 검증 실패 시 입력 폼으로 되돌림
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            //model.addAttribute("errors", errors);
            return "users/sign_up";
        }

        // 성공 로직
        // 회원가입 회원정보 저장 로직 미구현
        return "redirect:../";
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

package simplewebapplication.springwebapplication.web.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import simplewebapplication.springwebapplication.dto.user.UserJoinFormDTO;
import simplewebapplication.springwebapplication.dto.user.UserLoginFormDTO;
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
    public String userLogin() {
        return "users/sign_in";
    }

    // 로그인
    @PostMapping("/login")
    public String checkUserLogin(UserLoginFormDTO user) {

        // form 에서 사용자가 입력한 아이디, 비밀번호 정보가 전달됨

        // 아이디가 존재하는지?

        // 아이디가 존재한다면 비밀번호가 맞는지?

        // 모두 맞을 경우에만 성공
        // 하나라도 틀릴 시 경고

        return "redirect:../";
    }

    // 회원가입 페이지로 이동
    @GetMapping("/join")
    public String userJoin(Model model) {
        model.addAttribute("joinInfo", new UserJoinFormDTO());
        return "users/sign_up";
    }

    // 회원가입
    @PostMapping("/join")
    public String checkUserJoinV1(@ModelAttribute UserJoinFormDTO joinInfo, Model model) {

        Map<String, String> errors = new HashMap<>();

        // 검증 로직

        // 아이디 확인
        if (!StringUtils.hasText(joinInfo.getUserId())) {
            log.info("유저 아이디 = {}", joinInfo.getUserId());
            errors.put("userId", "회원 아이디는 필수 입니다.");
        }
        if (7 > joinInfo.getUserId().length() || joinInfo.getUserId().length() > 15) {
            log.info("유저 아이디 글자수 = {}", joinInfo.getUserId().length());
            errors.put("userId", "아이디의 글자 수는 7자 이상 15자 이하 입니다.");
        }

        // 비밀번호 확인
        if (!StringUtils.hasText(joinInfo.getPassword()) || !StringUtils.hasText(joinInfo.getRePassword())) {
            log.info("유저 비번 = {}", joinInfo.getPassword());
            log.info("유저 재비번 = {}", joinInfo.getRePassword());
            errors.put("rePassword", "회원 비밀 번호는 필수 입니다.");
        } else if (!joinInfo.getPassword().equals(joinInfo.getRePassword())) {
            errors.put("rePassword", "입력하신 비밀번호가 일치하지 않습니다.");
        }

        // 이메일 확인
        if (!StringUtils.hasText(joinInfo.getEmail())) {
            log.info("유저 이메일 = {}", joinInfo.getEmail());
            errors.put("userEmail", "회원 이메일은 필수 입니다.");
        }
        else if (!isValidEmail(joinInfo.getEmail())) {
            errors.put("userEmail", "유효한 이메일 형식이 아닙니다.");
        }

        // 검증 실패 시 입력 폼으로 되돌림
        if (!errors.isEmpty()) {
            log.info("errors = {}", errors);
            model.addAttribute("errors", errors);
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

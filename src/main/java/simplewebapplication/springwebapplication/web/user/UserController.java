package simplewebapplication.springwebapplication.web.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import simplewebapplication.springwebapplication.dto.user.UserJoinFormDTO;
import simplewebapplication.springwebapplication.dto.user.UserLoginFormDTO;
import simplewebapplication.springwebapplication.service.user.UserService;

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
    public String userJoin() {
        return "users/sign_up";
    }

    // 회원가입
    @PostMapping("/join")
    public String checkUserJoin(UserJoinFormDTO joinInfo) {

        log.info(joinInfo.toString());

        // 아이디 중복 체크
        // 비밀번호 동일 체크
        // 이메일 중복 체크
        return "redirect:../";
    }
}

package simplewebapplication.springwebapplication.etc;

import org.junit.jupiter.api.Test;
import simplewebapplication.springwebapplication.SpringWebApplication;
import simplewebapplication.springwebapplication.service.board.BoardService;
import simplewebapplication.springwebapplication.service.user.UserService;
import org.assertj.core.api.Assertions;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoAppConfigTest {

    @Test
    void basicScan() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SpringWebApplication.class);

        // UserService 가 스프링 컨테이너에 등록 되었는지 확인
        UserService userService = ac.getBean(UserService.class);
        Assertions.assertThat(userService).isInstanceOf(UserService.class);

        // BoardService 가 스프링 컨테이너에 등록 되었는지 확인
        BoardService boardService = ac.getBean(BoardService.class);
        Assertions.assertThat(boardService).isInstanceOf(BoardService.class);
    }
}

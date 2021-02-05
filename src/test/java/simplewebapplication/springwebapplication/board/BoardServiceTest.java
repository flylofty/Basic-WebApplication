package simplewebapplication.springwebapplication.board;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import simplewebapplication.springwebapplication.domain.board.Board;
import simplewebapplication.springwebapplication.domain.user.User;
import simplewebapplication.springwebapplication.repository.board.BoardRepository;
import simplewebapplication.springwebapplication.service.board.BoardService;
import simplewebapplication.springwebapplication.service.user.UserService;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BoardServiceTest {

    @Autowired BoardService boardService;
    @Autowired BoardRepository boardRepository;
    @Autowired UserService userService;

    @Test
    public void 게시글_생성() throws Exception {

        // given
        User user01 = new User("User01", "123123", "1111@abc.comm");
        String title = "신기하고도 재미난 JPA";
        String content = "오늘부터 1일! 신기하고도 재미난 JPA 를 신이나게 배워봅시다!!!!";
        LocalDateTime dateTime = LocalDateTime.now();
        Board board01 = new Board(title, content, user01, dateTime);

        // when
        String savedUser = userService.join(user01);
        Long savedBoard = boardService.createBoard(board01);

        // then
        List<Board> findBoard = boardRepository.findAll(0, 0, 0);

        for (Board board : findBoard) {
            System.out.println("board.getId() = " + board.getId());
            System.out.println("board.getTitle() = " + board.getTitle());
            System.out.println("board.getContent() = " + board.getContent());
            System.out.println("board.getDateTime() = " + board.getDateTime());
            System.out.println("board.getUser().getId() = " + board.getUser().getId());
        }
    }

    @Test
    public void 게시글_삭제() throws Exception {

        // given
        User user01 = new User("User01", "123123", "1111@abc.comm");
        String title = "신기하고도 재미난 JPA";
        String content = "오늘부터 1일! 신기하고도 재미난 JPA 를 신이나게 배워봅시다!!!!";
        LocalDateTime dateTime = LocalDateTime.now();
        Board board01 = new Board(title, content, user01, dateTime);

        // when
        String savedUser = userService.join(user01);
        Long savedBoard = boardService.createBoard(board01);
        boardService.deleteBoard(savedBoard);

        // then
        List<Board> findBoard = boardRepository.findAll(0, 0, 0);

        if (findBoard.isEmpty()) {
            System.out.println("리스트가 비었습니다.");
        }
    }
}

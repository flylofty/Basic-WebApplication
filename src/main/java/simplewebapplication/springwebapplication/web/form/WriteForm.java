package simplewebapplication.springwebapplication.web.form;

import lombok.Getter;
import lombok.Setter;
import simplewebapplication.springwebapplication.domain.board.Board;
import simplewebapplication.springwebapplication.domain.user.User;

import java.time.LocalDateTime;

@Getter @Setter
public class WriteForm {

    private String title;
    private String content;

    public WriteForm() {
    }

    public WriteForm(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Board makeBoard(User user) {

        return new Board(this.title, this.content, user, LocalDateTime.now());
    }
}

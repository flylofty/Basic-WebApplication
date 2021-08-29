package simplewebapplication.springwebapplication.web.form;

import lombok.Getter;
import lombok.Setter;
import simplewebapplication.springwebapplication.domain.board.Board;
import simplewebapplication.springwebapplication.domain.user.User;
import simplewebapplication.springwebapplication.dto.board.ResponseBoard;

import java.time.LocalDateTime;

@Getter @Setter
public class WriteForm {

    private Long boardId;
    private String title;
    private String content;

    public WriteForm() {
    }

    public WriteForm(Long boardId, String title, String content) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
    }

    public void responseBoardToWriteForm(ResponseBoard board) {
        this.boardId = Long.parseLong(board.getId());
        this.title = board.getTitle();
        this.content = board.getContent();
    }
}

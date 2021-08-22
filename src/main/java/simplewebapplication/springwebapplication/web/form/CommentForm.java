package simplewebapplication.springwebapplication.web.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentForm {

    private String content;
    private Integer level;
    private Long boardId;
    private String userId;

    public CommentForm() {
    }

    public CommentForm(Long boardId) {
        this.boardId = boardId;
    }

    public void initCommentForm(Integer level, Long boardId, String userId) {
        this.level = level;
        this.boardId = boardId;
        this.userId = userId;
    }
}

package simplewebapplication.springwebapplication.web.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentModifyForm {

    private Long commentId;
    private String commentContent;

    public CommentModifyForm() {
    }

    public CommentModifyForm(Long commentId, String commentContent) {
        this.commentId = commentId;
        this.commentContent = commentContent;
    }
}

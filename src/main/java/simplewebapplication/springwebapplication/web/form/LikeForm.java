package simplewebapplication.springwebapplication.web.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LikeForm {

    private Long likeId;
    private Long boardId;

    public LikeForm() {
    }

    public LikeForm(Long likeId, Long boardId) {
        this.likeId = likeId;
        this.boardId = boardId;
    }

    public boolean isPressedLikeButton() {
        return this.likeId == 0;
    }
}

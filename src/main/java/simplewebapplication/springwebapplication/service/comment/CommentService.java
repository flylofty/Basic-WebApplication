package simplewebapplication.springwebapplication.service.comment;

import simplewebapplication.springwebapplication.web.form.CommentLevelOneForm;
import simplewebapplication.springwebapplication.web.form.CommentForm;

import java.util.List;

public interface CommentService {

    // create
    void createComment(CommentForm form);

    // read
    List<CommentLevelOneForm> findCommentLevelOneList(Long boardId);

    // 사용자 댓글 수 조회
    List<Long> findCommentCount(String userId);

    // update
    void updateComment(Long commentId, String content);

    // delete
    void deleteComment(Long commentId);
}

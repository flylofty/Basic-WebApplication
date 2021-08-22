package simplewebapplication.springwebapplication.service.comment;

import simplewebapplication.springwebapplication.domain.comment.Comment;
import simplewebapplication.springwebapplication.dto.comment.RequestCommentLevelOne;
import simplewebapplication.springwebapplication.web.form.CommentForm;

import java.util.List;

public interface CommentService {

    // create
    void createComment(CommentForm form);

    // read
    List<RequestCommentLevelOne> findCommentLevelOneList(Long boardId);

    // update

    // delete
}

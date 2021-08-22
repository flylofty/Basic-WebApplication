package simplewebapplication.springwebapplication.repository.comment;

import simplewebapplication.springwebapplication.domain.comment.Comment;

public interface CommentRepository {

    // 댓글 저장
    void save(Comment comment);

    // 댓글 삭제

    // 게시글 페이지 네이션
}

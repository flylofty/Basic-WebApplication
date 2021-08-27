package simplewebapplication.springwebapplication.repository.comment;

import simplewebapplication.springwebapplication.domain.comment.Comment;

import java.util.List;

public interface CommentRepository {

    // 댓글 저장
    void save(Comment comment);

    // 게시글 페이지 네이션, 게시글 가져오기
    List<Comment> findCommentListByBoardId(Long boardId);

    // 댓글 수정
    void updateCommentById(Long commentId, String content);

    // 댓글 삭제
    void deleteCommentById(Long commentId);
}

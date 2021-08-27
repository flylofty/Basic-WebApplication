package simplewebapplication.springwebapplication.repository.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import simplewebapplication.springwebapplication.domain.board.Board;
import simplewebapplication.springwebapplication.domain.comment.Comment;
import simplewebapplication.springwebapplication.domain.user.User;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class H2CommentRepository implements CommentRepository {

    private final EntityManager em;

    @Override
    public void save(Comment comment) {
        em.persist(comment);
    }

    @Override
    public List<Comment> findCommentListByBoardId(Long boardId) {
        log.info("리포지토리에서 댓글 찾기 전");
        Board findBoard = em.find(Board.class, boardId);
        return em.createQuery("select c from Comment c " +
                        " where c.board = :findBoard " +
                        " and c.group_number = null " +
                        " order by c.dateTime asc", Comment.class)
                .setParameter("findBoard", findBoard)
                .getResultList();
    }

    @Override
    public List<Long> findCommentCount(String userId) {

        List<Long> list = new ArrayList<>();

        User findUser = em.find(User.class, userId);

        List<Comment> findCommentListByPost = em.createQuery("select c from Comment c " +
                        " where c.user = :findUser " +
                        " order by c.board", Comment.class)
                .setParameter("findUser", findUser)
                .getResultList();

        list.add(Long.parseLong(String.valueOf(findCommentListByPost.size())));

        List<Comment> commentList = em.createQuery("select c from Comment c " +
                        " where c.user = :findUser", Comment.class)
                .setParameter("findUser", findUser)
                .getResultList();

        list.add(Long.parseLong(String.valueOf(commentList.size())));

        return list;
    }

    @Override
    public void updateCommentById(Long commentId, String content) {
        Comment findComment = em.find(Comment.class, commentId);
        findComment.updateContent(content);
    }

    @Override
    public void deleteCommentById(Long commentId) {
        Comment findComment = em.find(Comment.class, commentId);
        em.remove(findComment);
    }
}

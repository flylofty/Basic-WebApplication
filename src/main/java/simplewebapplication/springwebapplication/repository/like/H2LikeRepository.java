package simplewebapplication.springwebapplication.repository.like;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import simplewebapplication.springwebapplication.domain.board.Board;
import simplewebapplication.springwebapplication.domain.like.Likes;
import simplewebapplication.springwebapplication.domain.user.User;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class H2LikeRepository implements LikeRepository {

    private final EntityManager em;

    @Override
    public Long findOneByUserAndBoard(String userId, Long boardId) {

        User findUser = em.find(User.class, userId);
        Board board = em.find(Board.class, boardId);
        List<Likes> likes = em.createQuery("select l from Likes l " +
                        " where l.user = :user " +
                        " and l.board = :board", Likes.class)
                .setParameter("user", findUser)
                .setParameter("board", board)
                .getResultList();

        if (likes.isEmpty()) {
            return 0L;
        }

        return likes.get(0).getId();
    }

    @Override
    public void save(String userId, Long boardId) {
        User findUser = em.find(User.class, userId);
        Board findBoard = em.find(Board.class, boardId);
        findBoard.updateFavorite(1L);
        em.persist(new Likes(findUser, findBoard));
    }

    @Override
    public void deleteById(Long likeId) {
        Likes findLike = em.find(Likes.class, likeId);
        Board findBoard = em.find(Board.class, findLike.getBoard().getId());
        em.remove(findLike);
        findBoard.updateFavorite(-1L);
    }
}
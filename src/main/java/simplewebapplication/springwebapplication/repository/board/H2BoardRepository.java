package simplewebapplication.springwebapplication.repository.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import simplewebapplication.springwebapplication.domain.board.Board;
import simplewebapplication.springwebapplication.domain.board.BoardStatusType;
import simplewebapplication.springwebapplication.domain.user.User;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class H2BoardRepository implements BoardRepository {

    private final EntityManager em;

    @Override
    public Board findOne(Long boardId) {
        Board board = em.find(Board.class, boardId);
        board.changeViews(1);
        return board;
    }

    @Override
    public List<Board> findAll(int page, int count) {
        return em.createQuery("select b from Board b", Board.class)
                .getResultList();
    }

    @Override
    public List<Board> findPage(Long page) {
        // 페이지네이션 시간상 최신글 우선 순서
        int start = Math.toIntExact((int) 10 * page - 10);
        return em.createQuery("select b from Board b " +
                        " where b.status = :status" +
                        " order by b.dateTime desc", Board.class)
                .setParameter("status", BoardStatusType.CREATED)
                .setFirstResult(start)
                .setMaxResults(10)
                .getResultList();
    }

    @Override // 개선 필요...ㅠㅠ
    public Long findBoardCount(String userId) {
        User findUser = em.find(User.class, userId);
        List<Board> boardList = em.createQuery("select b from Board b " +
                        "where b.user = :findUser", Board.class)
                .setParameter("findUser", findUser)
                .getResultList();
        return Long.parseLong(String.valueOf(boardList.size()));
    }

    @Override
    public Long findBoardTotalNumber() {
        return (long) em.createQuery("select count(*) from Board b " +
                        " where b.status = :status")
                .setParameter("status", BoardStatusType.CREATED)
                .getSingleResult();
    }

    @Override
    public void save(Board board) {
        em.persist(board);
    }

    @Override
    public void delete(Long boardId) {
        Board board = em.find(Board.class, boardId);

        // 해당 게시글을 삭제하지 않고 상태만을 변경
        //em.remove(findBoard);
        board.deleteBoard();
    }

    @Override
    public void updateOne(Long boardId, String content) {
        Board findBoard = em.find(Board.class, boardId);
        findBoard.updateBoardContent(content);
    }
}

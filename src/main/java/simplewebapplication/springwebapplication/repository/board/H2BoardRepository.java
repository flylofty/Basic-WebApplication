package simplewebapplication.springwebapplication.repository.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import simplewebapplication.springwebapplication.domain.board.Board;
import simplewebapplication.springwebapplication.domain.board.BoardStatusType;

import javax.persistence.EntityManager;
import java.util.List;

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
    public List<Board> findPage(int page) {

        // 페이지네이션 시간상 최신글 우선 순서
        int start = 10 * page - 10;
        return em.createQuery("select b from Board b " +
                        " where b.status = :status" +
                        " order by b.dateTime desc", Board.class)
                .setParameter("status", BoardStatusType.CREATED)
                .setFirstResult(start)
                .setMaxResults(start + 10)
                .getResultList();
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

    // 테스트용 임시 코드...
    public void flushAndClear() {
        System.out.println("flushAndClear()");
        em.flush();
        em.clear();
    }
}

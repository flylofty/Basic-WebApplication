package simplewebapplication.springwebapplication.repository.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import simplewebapplication.springwebapplication.domain.board.Board;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class H2BoardRepository implements BoardRepository {

    private final EntityManager em;

    @Override
    public Board findOne(Long boardId) {
        return em.find(Board.class, boardId);
    }

    @Override
    public List<Board> findAll(int page, int count) {
        return em.createQuery("select b from Board b", Board.class)
                .getResultList();
    }

    @Override
    public List<Board> findPage(int page) {

        int start = 10 * page - 10;
        return em.createQuery("select b from Board b order by b.id asc", Board.class)
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
        Board findBoard = em.find(Board.class, boardId);
        em.remove(findBoard);
    }

    // 테스트용 임시 코드...
    public void flushAndClear() {
        System.out.println("flushAndClear()");
        em.flush();
        em.clear();
    }
}

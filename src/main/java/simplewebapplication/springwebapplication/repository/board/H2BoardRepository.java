package simplewebapplication.springwebapplication.repository.board;

import org.springframework.stereotype.Repository;
import simplewebapplication.springwebapplication.domain.board.Board;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class H2BoardRepository implements BoardRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Board findOne(Long boardId) {
        return em.find(Board.class, boardId);
    }

    @Override
    public List<Board> findAll(int start, int end, int number) {
        return em.createQuery("select b from Board b", Board.class)
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

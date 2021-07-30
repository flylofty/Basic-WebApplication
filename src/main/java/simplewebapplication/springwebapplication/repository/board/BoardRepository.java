package simplewebapplication.springwebapplication.repository.board;

import simplewebapplication.springwebapplication.domain.board.Board;
import simplewebapplication.springwebapplication.domain.user.User;

import java.util.List;

public interface BoardRepository {

    // 하나만 조회
    Board findOne(Long boardId);

    // 전체 조회, 페이지네이션
    List<Board> findAll(int page, int count);

    // 생성
    public void save(Board board);

    // 삭제
    public void delete(Long boardId);
}

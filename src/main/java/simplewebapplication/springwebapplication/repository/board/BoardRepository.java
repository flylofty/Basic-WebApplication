package simplewebapplication.springwebapplication.repository.board;

import simplewebapplication.springwebapplication.domain.board.Board;
import simplewebapplication.springwebapplication.domain.user.User;

import java.util.List;

public interface BoardRepository {

    // 조회
    List<Board> findAll(int start, int end, int number);

    // 생성
    public void save(Board board);

    // 삭제
    public void delete(Long boardId);
}

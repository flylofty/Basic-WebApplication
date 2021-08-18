package simplewebapplication.springwebapplication.repository.board;

import simplewebapplication.springwebapplication.domain.board.Board;
import simplewebapplication.springwebapplication.domain.user.User;

import java.util.List;

public interface BoardRepository {

    // 하나만 조회
    Board findOne(Long boardId);

    // 전체 조회
    List<Board> findAll(int page, int count);

    // 게시글 페이지 네이션
    List<Board> findPage(Long page);

    // 사용자 게시글 수
    Long findBoardCount(String userId);

    // 전체 게시글 수
    Long findBoardTotalNumber();

    // 생성
    public void save(Board board);

    // 삭제
    public void delete(Long boardId);
}

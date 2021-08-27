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
    List<Board> findPage(Long page, String searchWord);

    // 사용자 게시글 수
    Long findBoardCount(String userId);

    // 전체 게시글 수
    Long findBoardTotalNumber(String searchWord);

    // 생성
    void save(Board board);

    // 삭제
    void delete(Long boardId);

    // 게시글 하나 내용 수정
    void updateOne(Long boardId, String content);
}

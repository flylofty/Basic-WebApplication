package simplewebapplication.springwebapplication.service.board;

import simplewebapplication.springwebapplication.domain.board.Board;
import simplewebapplication.springwebapplication.dto.board.ResponseBoard;

import java.util.List;

public interface BoardService {

    // 게시글페이지네이션, 게시글 조회
    List<ResponseBoard> findBoards(int start, int end, int number);

    ResponseBoard findBoard(Long boardId);

    // 게시글 생성
    Long createBoard(Board board);

    // 게시글 삭제
    Long deleteBoard(Long boardId);

    // 게시글 수정
    void updateBoard(Long boardId, String Content);
}

package simplewebapplication.springwebapplication.service.board;

import simplewebapplication.springwebapplication.domain.board.Board;

import java.util.List;

public interface BoardService {

    // 게시글페이지네이션, 게시글 조회
    List<Board> findBoards(int start, int end, int number);

    // 게시글 생성
    Long createBoard(Board board);

    // 게시글 삭제
    Long deleteBoard(Long boardId);
}

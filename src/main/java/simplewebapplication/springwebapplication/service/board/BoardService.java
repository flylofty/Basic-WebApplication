package simplewebapplication.springwebapplication.service.board;

import simplewebapplication.springwebapplication.dto.board.ResponseBoard;
import simplewebapplication.springwebapplication.web.pagination.BoardPagination;

import java.util.List;

public interface BoardService {

    // 전체 게시글 목록 조회
    List<ResponseBoard> findBoards(int page, int count);

    // 원하는 페이지 게시글 리스트 조회
    List<ResponseBoard> findBoardPageList(Long page, String searchWord);

    // 게시글 하나 조회
    ResponseBoard findBoard(Long boardId, Boolean isVisited);

    // 사용자 게시글 수 조회
    Long findBoardCount(String userId);

    // 페이지네이션 객체 생성
    BoardPagination createBoardPagination(Long currentPage, int currentBoardNumber, String searchWord);

    // 게시글 생성
    Long createBoard(String userId, String title, String content);

    // 게시글 삭제
    Long deleteBoard(Long boardId);

    // 게시글 수정
    void updateBoard(Long boardId, String content);
}

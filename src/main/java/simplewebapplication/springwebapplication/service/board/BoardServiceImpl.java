package simplewebapplication.springwebapplication.service.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simplewebapplication.springwebapplication.domain.board.Board;
import simplewebapplication.springwebapplication.dto.board.ResponseBoard;
import simplewebapplication.springwebapplication.repository.board.BoardRepository;
import simplewebapplication.springwebapplication.web.pagination.BoardPagination;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public ResponseBoard findBoard(Long boardId, Boolean isVisited) {

        Board board = boardRepository.findOne(boardId);

        if (isVisited == false) {
            board.changeViews(1);
        }

        return new ResponseBoard(board.getId().toString(), board.getTitle(),
                board.getUser().getId(), board.getDateTime().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm")),
                board.getViews().toString(), board.getFavorite().toString(), board.getContent());
    }

    @Override
    public Long findBoardCount(String userId) {
        return boardRepository.findBoardCount(userId);
    }

    @Override
    public BoardPagination createBoardPagination(Long currentPage, int currentBoardNumber) {
        Long boardTotalNumber = boardRepository.findBoardTotalNumber();
        return new BoardPagination(boardTotalNumber, currentPage, currentBoardNumber);
    }

    @Override
    public List<ResponseBoard> findBoards(int page, int count) {

        List<Board> findAll = boardRepository.findAll(page, count);

        // id, title, user, dateTime, views, favorite
        List<ResponseBoard> boards = new ArrayList<>();
        for (Board temp : findAll) {
            ResponseBoard rb = new ResponseBoard(temp.getId().toString(), temp.getTitle(), temp.getUser().getId(),
                    temp.getDateTime().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm")), temp.getViews().toString(), temp.getFavorite().toString(), temp.getContent());
            boards.add(rb);
        }

        return boards;
    }

    @Override
    public List<ResponseBoard> findBoardPageList(Long page) {
        List<Board> boardList = boardRepository.findPage(page);

        // id, title, user, dateTime, views, favorite
        List<ResponseBoard> boards = new ArrayList<>();
        for (Board board : boardList) {
            ResponseBoard rb = new ResponseBoard(board.getId().toString(), board.getTitle(), board.getUser().getId(),
                    board.getDateTime().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm")), board.getViews().toString(), board.getFavorite().toString(), board.getContent());
            boards.add(rb);
        }

        return boards;
    }

    @Override
    @Transactional
    public Long createBoard(Board board) {
        boardRepository.save(board);
        return board.getId();
    }

    @Override
    @Transactional
    public Long deleteBoard(Long boardId) {
        boardRepository.delete(boardId);
        return boardId;
    }

    @Override
    @Transactional
    public void updateBoard(Long boardId, String content) {
        boardRepository.updateOne(boardId, content);
    }
}

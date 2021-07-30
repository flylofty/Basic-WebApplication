package simplewebapplication.springwebapplication.service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simplewebapplication.springwebapplication.domain.board.Board;
import simplewebapplication.springwebapplication.dto.board.ResponseBoard;
import simplewebapplication.springwebapplication.repository.board.BoardRepository;
import simplewebapplication.springwebapplication.repository.board.H2BoardRepository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public ResponseBoard findBoard(Long boardId) {

        Board fb = boardRepository.findOne(boardId);

        return new ResponseBoard(fb.getId().toString(), fb.getTitle(),
                fb.getUser().getId(), fb.getDateTime().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm")),
                fb.getViews().toString(), fb.getFavorite().toString(), fb.getContent());
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
    public void updateBoard(Long boardId, String content) {
        Board findBoard = boardRepository.findOne(boardId);

        //DC
        findBoard.changeContent(content);
    }

    // 테스트용 코드
    public void fAndC() {
        ((H2BoardRepository)boardRepository).flushAndClear();
    }
}

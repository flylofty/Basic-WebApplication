package simplewebapplication.springwebapplication.service.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simplewebapplication.springwebapplication.domain.board.Board;
import simplewebapplication.springwebapplication.repository.board.BoardRepository;
import simplewebapplication.springwebapplication.repository.board.H2BoardRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public List<Board> findBoards(int start, int end, int number) {
        return boardRepository.findAll(start, end, number);
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

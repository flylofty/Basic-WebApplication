package simplewebapplication.springwebapplication.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simplewebapplication.springwebapplication.domain.board.Board;
import simplewebapplication.springwebapplication.domain.comment.Comment;
import simplewebapplication.springwebapplication.domain.user.User;
import simplewebapplication.springwebapplication.repository.board.BoardRepository;
import simplewebapplication.springwebapplication.repository.comment.CommentRepository;
import simplewebapplication.springwebapplication.repository.user.UserRepository;
import simplewebapplication.springwebapplication.web.form.CommentForm;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public void createComment(CommentForm form) {
        User findUser = userRepository.findById(form.getUserId()).get();
        Board findBoard = boardRepository.findOne(form.getBoardId());
        Comment comment = new Comment(findBoard, findUser, form.getContent(), null, form.getLevel(), LocalDateTime.now());
        commentRepository.save(comment);
    }
}

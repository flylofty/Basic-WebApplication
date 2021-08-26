package simplewebapplication.springwebapplication.service.like;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simplewebapplication.springwebapplication.domain.user.User;
import simplewebapplication.springwebapplication.repository.like.LikeRepository;
import simplewebapplication.springwebapplication.web.form.LikeForm;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    @Override
    public LikeForm findLikeId(User user, Long boardId) {
        Long likeId = likeRepository.findOneByUserAndBoard(user, boardId);

        LikeForm likeForm = new LikeForm(likeId, boardId);;

        return likeForm;
    }

    @Override
    @Transactional
    public void createLike(User user, Long boardId) {
        likeRepository.save(user, boardId);
    }

    @Override
    @Transactional
    public void deleteById(Long likeId) {
        likeRepository.deleteById(likeId);
    }
}

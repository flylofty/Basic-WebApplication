package simplewebapplication.springwebapplication.repository.like;

import simplewebapplication.springwebapplication.domain.user.User;

public interface LikeRepository {

    // 좋아요 조회
    Long findOneByUserAndBoard(User user, Long boardId);

    // 좋아요 저장
    void save(User user, Long boardId);

    // 좋아요 삭제
    void deleteById(Long likeId);
}

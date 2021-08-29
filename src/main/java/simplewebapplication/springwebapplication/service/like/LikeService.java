package simplewebapplication.springwebapplication.service.like;

import simplewebapplication.springwebapplication.domain.user.User;
import simplewebapplication.springwebapplication.web.form.LikeForm;

public interface LikeService {

    // 좋아요 단건 조회
    LikeForm findLikeId(String userId, Long boardId);

    // 좋아요 저장
    void createLike(String userId, Long boardId);

    // 좋아요 삭제
    void deleteById(Long likeId);
}

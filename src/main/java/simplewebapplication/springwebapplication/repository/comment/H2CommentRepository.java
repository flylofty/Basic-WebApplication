package simplewebapplication.springwebapplication.repository.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import simplewebapplication.springwebapplication.domain.comment.Comment;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class H2CommentRepository implements CommentRepository{

    private final EntityManager em;

    @Override
    public void save(Comment comment) {
        em.persist(comment);
    }
}

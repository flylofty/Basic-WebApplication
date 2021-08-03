package simplewebapplication.springwebapplication.repository.user;

import org.springframework.stereotype.Repository;
import simplewebapplication.springwebapplication.domain.user.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class H2UserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(User user) {
        em.persist(user);
    }

    @Override
    public Optional<User> findById(String id) {

        User findUser = em.find(User.class, id);

        if (findUser == null)
            return Optional.empty();

        return Optional.of(findUser);
    }
}

package simplewebapplication.springwebapplication.repository.user;

import org.springframework.stereotype.Repository;
import simplewebapplication.springwebapplication.domain.user.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class H2UserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(User user) {
        em.persist(user);
    }

    @Override
    public User findById(String id) {
        return em.find(User.class, id);
    }
}

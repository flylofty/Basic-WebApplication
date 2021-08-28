package simplewebapplication.springwebapplication.repository.user;

import org.springframework.stereotype.Repository;
import simplewebapplication.springwebapplication.domain.user.User;
import simplewebapplication.springwebapplication.domain.user.UserRoleType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
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

        //User findUser = em.find(User.class, id);
        List<User> resultList = em.createQuery("select u from User u " +
                        " where u.id = : id " +
                        " and u.role != :role", User.class)
                .setParameter("id", id)
                .setParameter("role", UserRoleType.DELETED)
                .getResultList();

        if (resultList.isEmpty())
            return Optional.empty();

        return Optional.of(resultList.get(0));
    }

    @Override
    public void delete(String userId) {
        Optional<User> optionalUser = findById(userId);
        optionalUser.ifPresent(User::deleteAccount);
    }
}

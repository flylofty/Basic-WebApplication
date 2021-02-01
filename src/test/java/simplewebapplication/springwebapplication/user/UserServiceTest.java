package simplewebapplication.springwebapplication.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import simplewebapplication.springwebapplication.domain.user.User;
import simplewebapplication.springwebapplication.repository.user.UserRepository;
import simplewebapplication.springwebapplication.service.user.UserService;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    public void 회원가입_테스트() throws Exception {

        // given
        User user = new User("user01", "123123", "lyn00227@naver.com");

        // when
        String savedId = userService.join(user);

        // then
        User findUser = userRepository.findById(savedId);
        assertEquals(user, findUser);
        System.out.println("savedId = " + savedId);
        System.out.println("findUser = " + findUser.getId());
    }
}

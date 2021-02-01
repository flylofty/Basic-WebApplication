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

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {

        // given
        User user1 = new User("user01", "123123", "lyn00227@naver.com");
        //User user2 = new User("user01", "123123", "lyn00227@naver.com");
        User user2 = new User("user02", "123123", "lyn0227@nate.com");

        // when
        userService.join(user1);
        userService.join(user2); // 같은 이름이므로 예외가 발생해야 한다!!!!!

        // then
        fail("예외가 발생해야 한다."); // 코드가 돌다 여기에 오면 잘못됐음을 알림.
    }
}

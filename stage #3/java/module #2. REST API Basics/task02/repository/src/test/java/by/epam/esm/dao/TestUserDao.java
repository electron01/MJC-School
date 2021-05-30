package by.epam.esm.dao;

import by.epam.esm.RepositoryConfig;
import by.epam.esm.enity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest(classes = RepositoryConfig.class)
@ActiveProfiles("test")
@Transactional
public class TestUserDao {
    @Autowired
    private UserDao userDao;

    @Test
    public void addUserTest(){
        //given
        User user = new User();
        user.setLogin("user");
        user.setPassword("user1");
        user.setEmail("user@test.com");
        //when
        User newUser = userDao.add(user);
        //then
        Assertions.assertNotNull(newUser.getId());
    }

    @Test
    public void deleteUserByCorrectIdTest() {
        //given
        User user = createUser();
        //when
        userDao.deleteById(user.getId());
        //then
        Assertions.assertEquals(Optional.empty(), userDao.findById(user.getId()));
    }

    @Test
    public void deleteUserByUnCorrectId() {
        //given
        Long id =-1l;
        //when
        boolean wasDeleted = userDao.deleteById(id);
        //then
        Assertions.assertFalse(wasDeleted);
    }

    @Test
    public void userLoginExistTest() {
        //given
        User user = createUser();
        //when
        Optional<User> userLoginExist = userDao.isUserLoginExist(user.getLogin());
        //then
        Assertions.assertEquals(Optional.of(user),userLoginExist);
    }

    @Test
    public void userLoginNotExistTest() {
        //given
        String newLogin = "NewLogin";
        //when
        Optional<User> userLoginExist = userDao.isUserLoginExist(newLogin);
        //then
        Assertions.assertEquals(Optional.empty(),userLoginExist);
    }

    @Test
    public void findByCorrectIdTest() {
        //given
        User user = createUser();
        //when
        Optional<User> foundUser = userDao.findById(user.getId());
        //then
        Assertions.assertEquals(Optional.of(user),foundUser);
    }

    @Test
    public void findByUnCorrectIdTest() {
        //given
        Long id = -1l;
        //when
        Optional<User> foundUser = userDao.findById(id);
        //then
        Assertions.assertEquals(Optional.empty(),foundUser);
    }

    private User createUser(){
        User user = new User();
        user.setLogin("user");
        user.setPassword("user1");
        user.setEmail("user@test.com");
        return userDao.add(user);
    }
}

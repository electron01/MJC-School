package by.epam.esm.service;

import by.epam.esm.dao.UserDao;
import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.dto.entity.UserDto;
import by.epam.esm.dto.mapper.PaginationMapper;
import by.epam.esm.dto.mapper.UserMapper;
import by.epam.esm.dto.mapper.UserMapperImpl;
import by.epam.esm.enity.Pagination;
import by.epam.esm.enity.Tag;
import by.epam.esm.enity.User;
import by.epam.esm.exception.ServiceException;
import by.epam.esm.service.impl.UserServiceImpl;
import by.epam.esm.validator.BaseValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserDao userDao;
    @Spy
    private UserMapperImpl userMapper;
    @Mock
    private BaseValidator baseValidator;
    @Spy
    private PaginationMapper paginationMapper;
    @InjectMocks
    private UserServiceImpl userService;

    private static Long CORRECT_ID =1L;
    private static Long UN_CORRECT_ID =-1L;
    private static String CORRECT_LOGIN ="user";
    private static String CORRECT_PASSWORD ="user1";
    private static String CORRECT_EMAIL ="use@test.comr";
    private static User correctUser = new User();
    private static List<User> correctUserList = new ArrayList<>();

    @BeforeAll
    public static void init() {
        correctUser.setId(CORRECT_ID);
        correctUser.setLogin(CORRECT_LOGIN);
        correctUser.setPassword(CORRECT_PASSWORD);
        correctUser.setEmail(CORRECT_EMAIL);
        correctUserList = List.of(new User(), new User(), new User());
    }

    @Test
    public void findByCorrectIdTest(){
        //given
        User user = new User();
        user.setId(CORRECT_ID);
        Mockito.when(userDao.findById(CORRECT_ID)).thenReturn(Optional.of(correctUser));
        //when
        UserDto userDto = userService.findById(CORRECT_ID);
        //then
        Assertions.assertEquals(userDto.getLogin(),correctUser.getLogin());
    }

    @Test
    public void findByUnCorrectIdTest(){
        //given
        Mockito.when(userDao.findById(UN_CORRECT_ID)).thenReturn(Optional.empty());
        //when
        //then
        Assertions.assertThrows(ServiceException.class,()-> userService.findById(UN_CORRECT_ID));
    }

    @Test
    public void isUserLoginExistTest(){
        //given
        Mockito.when(userDao.isUserLoginExist(CORRECT_LOGIN)).thenReturn(Optional.of(correctUser));
        //then
        boolean userLoginExist = userService.isUserLoginExist(CORRECT_LOGIN);
        //then
        Assertions.assertTrue(userLoginExist);
    }
    @Test
    public void isUserLoginNotExistTest(){
        //given
        Mockito.when(userDao.isUserLoginExist(CORRECT_LOGIN)).thenReturn(Optional.empty());
        //then
        boolean userLoginExist = userService.isUserLoginExist(CORRECT_LOGIN);
        //then
        Assertions.assertFalse(userLoginExist);
    }

    @Test
    public void addNewUser(){
        //given
        Mockito.when(userDao.add(Mockito.any())).thenReturn(correctUser);
        Mockito.when(userDao.isUserLoginExist(CORRECT_LOGIN)).thenReturn(Optional.empty());
        //when
        UserDto user = new UserDto();
        user.setLogin(CORRECT_LOGIN);
        user.setEmail(CORRECT_EMAIL);
        user.setPassword(CORRECT_PASSWORD);
        UserDto newUser = userService.add(user);
        //then
        Assertions.assertEquals(correctUser.getLogin(), newUser.getLogin());
        Assertions.assertEquals(correctUser.getId(), newUser.getId());
    }
    @Test
    public void addDuplicateNewUser(){
        //given
        Mockito.when(userDao.isUserLoginExist(CORRECT_LOGIN)).thenReturn(Optional.of(correctUser));
        //when
        UserDto user = new UserDto();
        user.setLogin(CORRECT_LOGIN);
        //then
        Assertions.assertThrows(ServiceException.class,()->userService.add(user));
    }

    @Test
    public void addUnCorrectNewUser(){
        //given
        UserDto user = new UserDto();
        //when
        Mockito.doThrow(ServiceException.class).when(baseValidator).dtoValidator(user);
        //then
        Assertions.assertThrows(ServiceException.class,()->userService.add(user));
    }

    @Test
    public void deleteByCorrectId(){
       //given
        Mockito.when(userDao.deleteById(CORRECT_ID)).thenReturn(true);
        //when
        boolean wasDelete = userService.delete(CORRECT_ID);
        //then
        Assertions.assertTrue(wasDelete);
    }

    @Test
    public void deleteByUnCorrectId(){
        //given
        Mockito.when(userDao.deleteById(UN_CORRECT_ID)).thenReturn(false);
        //when
        boolean wasDelete = userService.delete(UN_CORRECT_ID);
        //then
        Assertions.assertFalse(wasDelete);
    }

    @Test
    public void findAllWithCorrectPaginationTest(){
        //given
        PaginationDto pagination = new PaginationDto();
       Mockito.when(userDao.findAll(Mockito.any(),Mockito.any())).thenReturn(correctUserList);
       //when
        List<UserDto> users = userService.findAll(new HashMap<>(), pagination);
        //then
        Assertions.assertTrue(users.size()==correctUserList.size());
    }

    @Test
    public void findAllWithUnCorrectPaginationTest(){
        //given
        PaginationDto unCorrectPaginationDto = new PaginationDto();
        //when
        Mockito.doThrow(ServiceException.class).when(baseValidator).dtoValidator(unCorrectPaginationDto);
        //then
        Assertions.assertThrows(ServiceException.class,()->userService.findAll(new HashMap<>(),unCorrectPaginationDto));
    }
}

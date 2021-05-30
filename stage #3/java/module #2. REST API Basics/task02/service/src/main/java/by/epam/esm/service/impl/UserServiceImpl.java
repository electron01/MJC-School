package by.epam.esm.service.impl;

import by.epam.esm.dao.UserDao;
import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.dto.entity.UserDto;
import by.epam.esm.dto.mapper.PaginationMapper;
import by.epam.esm.dto.mapper.UserMapper;
import by.epam.esm.enity.Pagination;
import by.epam.esm.enity.User;
import by.epam.esm.exception.ErrorCode;
import by.epam.esm.exception.ErrorMessage;
import by.epam.esm.exception.ServiceException;
import by.epam.esm.service.UserService;
import by.epam.esm.validator.BaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private UserMapper userMapper;
    private BaseValidator baseValidator;
    private PaginationMapper paginationMapper;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserMapper userMapper, BaseValidator baseValidator,PaginationMapper paginationMapper) {
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.baseValidator=baseValidator;
        this.paginationMapper = paginationMapper;
    }

    @Override
    public List<UserDto> findAll(Map<String, String[]> params, PaginationDto paginationDto) {
        baseValidator.dtoValidator(paginationDto);
        Pagination pagination = paginationMapper.toEntity(paginationDto);
        return userDao.findAll(params, pagination)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Long id) {
        return userDao.findById(id)
                .stream()
                .findAny()
                .map(userMapper::toDto)
                .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FIND_USER_BY_ID,
                        ErrorCode.NOT_FIND_USER_BY_ID.getMessage(),
                        Set.of(new ErrorMessage("id", String.valueOf(id), ErrorCode.NOT_FIND_USER_BY_ID.getMessage())
                        )));
    }


    @Override
    @Transactional
    public UserDto add(UserDto userDto) {
        baseValidator.dtoValidator(userDto);
        if (isUserLoginExist(userDto.getLogin())) {
            throw new ServiceException(ErrorCode.USER_LOGIN_IS_ALREADY_EXISTS,
                    ErrorCode.USER_LOGIN_IS_ALREADY_EXISTS.getMessage(),
                    Set.of(new ErrorMessage("name", userDto.getLogin(), ErrorCode.USER_LOGIN_IS_ALREADY_EXISTS.getMessage())
                    ));
        }
        userDto.setEmail(userDto.getEmail().trim());
        userDto.setLogin(userDto.getLogin().trim());
        userDto.setPassword(userDto.getPassword().trim());
        User user = userMapper.toEntity(userDto);
        return userMapper.toDto(userDao.add(user));
    }

    @Override
    public UserDto update(UserDto dto) {
        return null;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        return userDao.deleteById(id);
    }

    @Override
    public Integer getCountCountOfElements(Map<String, String[]> params) {
        return userDao.getCountOfElements(params);
    }

    @Override
    public boolean isUserLoginExist(String login) {
        return userDao.isUserLoginExist(login).isPresent();
    }
}

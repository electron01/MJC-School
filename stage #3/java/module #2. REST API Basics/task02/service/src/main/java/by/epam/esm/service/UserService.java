package by.epam.esm.service;

import by.epam.esm.dto.entity.UserDto;

public interface UserService extends BaseService<UserDto,Long> {
    boolean isUserLoginExist(String login);
}

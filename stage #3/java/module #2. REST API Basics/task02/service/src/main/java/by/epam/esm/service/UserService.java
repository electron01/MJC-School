package by.epam.esm.service;

import by.epam.esm.dto.entity.UserDto;

/**
 * class UserService
 * interface extends BaseService and contains methods for User
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public interface UserService extends BaseService<UserDto,Long> {
    /**
     * isUserLoginExist
     * method isUserLoginExist
     * @param login  - login for check
     * @return true - if exists
     */
    boolean isUserLoginExist(String login);
}

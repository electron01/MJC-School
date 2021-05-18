package by.epam.esm.dao;

import by.epam.esm.enity.User;

import java.util.Optional;

/**
 * interface UserDao
 * interface extends BaseDao and contains base methods for work with user entity
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public interface UserDao extends BaseDao<User, Long> {
    /**
     * isUserLoginExist
     * method isUserLoginExist
     * @param login - login for check
     * @return true - if exists
     */
    Optional<User> isUserLoginExist(String login);
}

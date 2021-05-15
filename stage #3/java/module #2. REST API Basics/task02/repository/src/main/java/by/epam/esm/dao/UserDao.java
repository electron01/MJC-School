package by.epam.esm.dao;

import by.epam.esm.enity.User;

import java.util.Optional;

public interface UserDao extends BaseDao<User,Long> {
    Optional<User> isUserLoginExist(String login);
}

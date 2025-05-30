package service;

import model.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserService {

    /* ---------- CRUD ---------- */
    void addUser(User user) throws SQLException;

    User getUserById(int id) throws SQLException;

    List<User> getAllUsers() throws SQLException;

    boolean removeUser(int id) throws SQLException;

    boolean modifyUser(User user) throws SQLException;

    /* ---------- Authentication ---------- */
    User login(String email, String password) throws SQLException;
}

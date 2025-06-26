package service;

import java.util.List;
import model.User;

public interface IUserService {

    /* ---------- CRUD ---------- */
    void addUser(User user);

    User getUserById(int id);

    List<User> getAllUsers();

    boolean removeUser(int id);

    boolean modifyUser(User user);

    /* ---------- Authentication ---------- */
    User login(String email, String password);
}

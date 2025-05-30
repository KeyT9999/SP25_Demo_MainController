package service;

import model.User;
import userDao.IUserDao;
import userDao.UserDao;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements IUserService {

    private final IUserDao userDao;

    /* === Constructors === */
    public UserServiceImpl() {
        this.userDao = new UserDao();
    }

    /* === CREATE === */
    @Override
    public void addUser(User user) throws SQLException {
        userDao.insertUser(user);
    }

    /* === READ === */
    @Override
    public User getUserById(int id) throws SQLException {
        return userDao.selectUser(id);
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        return userDao.selectAllUsers();
    }

    /* === DELETE === */
    @Override
    public boolean removeUser(int id) throws SQLException {
        return userDao.deleteUser(id);
    }

    /* === UPDATE === */
    @Override
    public boolean modifyUser(User user) throws SQLException {
        return userDao.updateUser(user);
    }

    /* === LOGIN === */
    @Override
    public User login(String email, String password) throws SQLException {
        return userDao.checkLogin(email, password);
    }
}

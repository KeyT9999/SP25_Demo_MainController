package userDao;

import model.User;
import java.sql.SQLException;
import java.util.List;

public interface IUserDao {
    /* CRUD */
    void insertUser(User user) throws SQLException;
    User selectUser(int id) throws SQLException;
    List<User> selectAllUsers() throws SQLException;
    boolean deleteUser(int id) throws SQLException;
    boolean updateUser(User user) throws SQLException;

    /* ---- THÊM HÀM LOGIN ---- */
    User checkLogin(String email, String password) throws SQLException;
}

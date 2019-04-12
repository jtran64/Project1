package repository;

import java.sql.SQLException;
import java.util.ArrayList;
import dto.User;

public interface UserDao {
	User getUser(String user);
    ArrayList<User> getAllUsers();
    User getUserByUserNameAndPassword();
    boolean insertUser();
    boolean updateUser(ArrayList<String> updateDetails, String username) throws SQLException;
    boolean deleteUser();
}

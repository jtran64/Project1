package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dto.User;
import util.DBUtil;

public class UserDaoImpl implements UserDao {
	private static Connection conn;
	private static PreparedStatement st;
	private static ResultSet rs;
	static {
		conn = null;
		st = null;
		rs = null;
	}

	@Override
	public User getUser(String user) {
		try {
			conn = DBUtil.getInstance();
			// TODO write query to join users table with user roles table
			String sql = "select u.ers_user_id, u.ers_username, u.ers_password, "
					+ "u.user_first_name, u.user_last_name, u.user_email, r.user_role "
					+ "from ers_users u inner join ers_user_roles r on u.user_role_id = r.ers_user_role_id "
					+ "where u.ers_username=?";
			st = conn.prepareStatement(sql);
			st.setString(1, user);
			rs = st.executeQuery();

			if (rs.next()) {
				User tempUser = new User(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7));
				return tempUser;
			} else
				return null;
		} catch (SQLException e) {
			System.out.println("Error. Could not verify login. Try again later.");
		} finally {
			closeConnections();
		}
		return null;
	}

	@Override
	public ArrayList<User> getAllUsers() {
		try {
			conn = DBUtil.getInstance();
			String sql = "select u.ers_user_id, u.ers_username, u.ers_password, "
					+ "u.user_first_name, u.user_last_name, u.user_email, r.user_role "
					+ "from ers_users u inner join ers_user_roles r on u.user_role_id = r.ers_user_role_id";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();

			ArrayList<User> users = new ArrayList<>();

			while (rs.next()) {
				User tempUser = new User(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7));
				users.add(tempUser);
			}
			return users;
		} catch (SQLException e) {
			System.out.println("Error. Could not get employees. Try again later.");
		} finally {
			closeConnections();
		}
		return null;
	}

	@Override
	public User getUserByUserNameAndPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertUser() {
		// TODO Auto-generated method stub
		return false;
	}

	// Only call if there is a field that needs to be updated
	@Override
	public boolean updateUser(ArrayList<String> updateDetails, String username) throws SQLException {
		try {
			conn = DBUtil.getInstance();
			String sql = null;
			boolean notNewPass = updateDetails.get(0).equals("");
			if (notNewPass)
				sql = "update ers_users set user_first_name=?, "
						+ "user_last_name=?, user_email=? where ers_username=?";
			else
				sql = "update ers_users set ers_password=?, user_first_name=?, "
						+ "user_last_name=?, user_email=? where ers_username=?";
			st = conn.prepareStatement(sql);

			if (notNewPass) {
				for (int i = 1; i < updateDetails.size(); i++) {
					st.setString(i, updateDetails.get(i));
				}
				st.setString(4, username);
			} else {
				for (int i = 0; i < updateDetails.size(); i++) {
					st.setString(i + 1, updateDetails.get(i));
				}
				st.setString(5, username);
			}

			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			conn.rollback();
			System.out.println("Error. Could not update details. Try again later." + e.getMessage());
		} finally {
			closeConnections();
		}
		return false;
	}

	@Override
	public boolean deleteUser() {
		// TODO Auto-generated method stub
		return false;
	}

	private static void closeConnections() {
		try {
			rs.close();
		} catch (Exception e) {
		}
		try {
			st.close();
		} catch (Exception e) {
		}
		try {
			conn.close();
		} catch (Exception e) {
		}
	}
}

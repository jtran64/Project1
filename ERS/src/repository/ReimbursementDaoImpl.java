package repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dto.Reimbursement;
import util.DBUtil;

public class ReimbursementDaoImpl implements ReimbursementDao {
	private static Connection conn;
	private static PreparedStatement st;
	private static ResultSet rs;
	static {
		conn = null;
		st = null;
		rs = null;
	}

	@Override
	public Reimbursement getReimbursement(long id) {
		try {
			conn = DBUtil.getInstance();
			
			String sql = "select r.reimb_id, r.reimb_amount, r.reimb_date_submitted, " + 
					"r.reimb_date_resolved,r.reimb_description, r.reimb_receipt, " + 
					"(auth.user_first_name || ' ' || auth.user_last_name), " +
					"(select (user_first_name || ' ' || user_last_name) from ers_users " +
					"where ers_user_id = r.reimb_resolver and r.reimb_resolver is not null), " +
					"s.reimb_status,t.reimb_type from ers_reimbursement r " + 
					"inner join ers_reimbursement_status s on r.reimb_status_id = s.reimb_status_id " + 
					"inner join ers_reimbursement_type t on r.reimb_type_id = t.reimb_type_id " +
					"inner join ers_users auth on r.reimb_author = auth.ers_user_id " +
					"where r.reimb_id = ?";
			st = conn.prepareStatement(sql);
			st.setLong(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Reimbursement reimb = new Reimbursement(rs.getLong(1), rs.getDouble(2), rs.getDate(3), rs.getDate(4),
						rs.getString(5), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
				return reimb;
			} else
				return null;
		} catch (SQLException e) {
			System.out.println("Error. Could not get reimbursement details. Try again later.");
		} finally {
			closeConnections();
		}
		return null;
	}

	@Override
	public ArrayList<Reimbursement> getAllReimbursements() {
		try {
			conn = DBUtil.getInstance();
			// TODO write query to join users table with user roles table
			String sql = "select r.reimb_id, r.reimb_amount, r.reimb_date_submitted, " + 
					"r.reimb_date_resolved,r.reimb_description, r.reimb_receipt, " + 
					"(auth.user_first_name || ' ' || auth.user_last_name) , " +
					"(select (user_first_name || ' ' || user_last_name) from ers_users " +
					"where ers_user_id = r.reimb_resolver and r.reimb_resolver is not null), " +
					"s.reimb_status,t.reimb_type from ers_reimbursement r " + 
					"inner join ers_reimbursement_status s on r.reimb_status_id = s.reimb_status_id " + 
					"inner join ers_reimbursement_type t on r.reimb_type_id = t.reimb_type_id " +
					"inner join ers_users auth on r.reimb_author = auth.ers_user_id " +
					"order by r.reimb_id";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			ArrayList<Reimbursement> reimbs = new ArrayList<>();
			while (rs.next()) {
				Reimbursement reimb = new Reimbursement(rs.getLong(1), rs.getDouble(2), rs.getDate(3), rs.getDate(4),
						rs.getString(5), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
				reimbs.add(reimb);
			}
			return reimbs;
		} catch (SQLException e) {
			System.out.println("Error. Could not get all reimbursement details. Try again later." + e.getMessage());
		} finally {
			closeConnections();
		}
		return null;
	}
	
	@Override
	public ArrayList<Reimbursement> getAllReimbursementsFromUser(String username) {
		try {
			conn = DBUtil.getInstance();
			// TODO write query to join users table with user roles table
			String sql = "select r.reimb_id, r.reimb_amount, r.reimb_date_submitted, " + 
					"r.reimb_date_resolved,r.reimb_description, r.reimb_receipt, " + 
					"(auth.user_first_name || ' ' || auth.user_last_name) , " +
					"(select (user_first_name || ' ' || user_last_name) from ers_users " +
					"where ers_user_id = r.reimb_resolver and r.reimb_resolver is not null), " +
					"s.reimb_status,t.reimb_type from ers_reimbursement r " + 
					"inner join ers_reimbursement_status s on r.reimb_status_id = s.reimb_status_id " + 
					"inner join ers_reimbursement_type t on r.reimb_type_id = t.reimb_type_id " +
					"inner join ers_users auth on r.reimb_author = auth.ers_user_id " +
					"where r.reimb_author = (select ers_user_id from ers_users where ers_username = ?) " +
					"order by r.reimb_id";
			st = conn.prepareStatement(sql);
			st.setString(1, username);
			rs = st.executeQuery();

			ArrayList<Reimbursement> reimbs = new ArrayList<>();
			while (rs.next()) {
				Reimbursement reimb = new Reimbursement(rs.getLong(1), rs.getDouble(2), rs.getDate(3), rs.getDate(4),
						rs.getString(5), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
				reimbs.add(reimb);
			}
			return reimbs;
		} catch (SQLException e) {
			System.out.println("Error. Could not get reimbursement details. Try again later.");
		} finally {
			closeConnections();
		}
		return null;
	}

	@Override
	public boolean insertReimbursement(Reimbursement reimb) throws SQLException {
		try {
			conn = DBUtil.getInstance();
			System.out.println(conn);
			String sql = "insert into ers_reimbursement (reimb_id, reimb_amount, reimb_description, reimb_receipt, reimb_author, reimb_type_id) "
					+ "values(5,?,?,null,(select ers_user_id from ers_users where ers_username = ?),?)";
			st = conn.prepareStatement(sql);
			st.setDouble(1, reimb.getReimbAmount());
			st.setString(2, reimb.getDescription());
			//st.setBlob(3, null);
			st.setString(3, reimb.getAuthor());
			
			String type = reimb.getTypeId().toUpperCase();
			if(type.equals("LODGING"))
				st.setInt(4, 1);
			else if(type.equals("TRAVEL"))
				st.setInt(4, 2);
			else if(type.equals("FOOD"))
				st.setInt(4, 3);
			else
				st.setInt(4, 4);
			
			st.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			conn.rollback();
			System.out.println("Error. Could not update reimbursement. Try again later." + e.getMessage());
		} finally {
			closeConnections();
		}
		return false;
	}

	@Override
	public boolean updateReimbursement(long id, int status, String resolv) throws SQLException {
		CallableStatement cstmt = null;
		try {
			conn = DBUtil.getInstance();
			String sql = "{call approve_or_deny_reimb (?, ?, ?)}";
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, status);
			cstmt.setLong(2, id);
			cstmt.setString(3, resolv);
		
			cstmt.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			conn.rollback();
			System.out.println("Error. Could not approve or deny reimbursement. Try again later.");
		} finally {
			cstmt.close();
			closeConnections();
		}
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
	
	public static void main(String[] args) throws SQLException {
		Reimbursement r = new Reimbursement(12,
				"test", "johnd", "Food");
		ReimbursementDaoImpl impl = new ReimbursementDaoImpl();
		impl.insertReimbursement(r);
	}
}

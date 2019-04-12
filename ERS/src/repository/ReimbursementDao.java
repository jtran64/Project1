package repository;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.Reimbursement;

public interface ReimbursementDao {
	Reimbursement getReimbursement(long id);
    ArrayList<Reimbursement> getAllReimbursements();
    ArrayList<Reimbursement> getAllReimbursementsFromUser(String username);
    boolean insertReimbursement(Reimbursement reimb) throws SQLException;
    boolean updateReimbursement(long id, int status, String resolv) throws SQLException;
}

package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import dto.Reimbursement;
import dto.User;
import repository.ReimbursementDaoImpl;

public class ReimbursementController {
	public static String getReimbursements(HttpServletRequest req, HttpServletResponse resp) {
		try {
			ArrayList<Reimbursement> reimbursements = null;
			User user = (User)req.getSession().getAttribute("User");
			ReimbursementDaoImpl reimbursementDaoImpl = new ReimbursementDaoImpl();
			Gson gson = new Gson();
			if(user != null && user.getRoleId().equals("manager"))
				reimbursements = reimbursementDaoImpl.getAllReimbursements();
			else if(user != null)
				reimbursements = reimbursementDaoImpl.getAllReimbursementsFromUser(user.getUserName());
			if (reimbursements != null) {
				String reimbs = gson.toJson(reimbursements);
				resp.getWriter().write(reimbs);
			}
		} catch (IOException e) {

		}
		return null;
	}
	
	public static String getReimbursement(HttpServletRequest req, HttpServletResponse resp) {
		try {
			Reimbursement reimbursement = null;
			ReimbursementDaoImpl reimbursementDaoImpl = new ReimbursementDaoImpl();
			Gson gson = new Gson();
			System.out.println(req.getParameter("reimbId"));
			reimbursement = reimbursementDaoImpl.getReimbursement(Long.parseLong(req.getParameter("reimbId")));
			if (reimbursement != null) {
				String reimb = gson.toJson(reimbursement);
				resp.getWriter().write(reimb);
			}
		} catch (IOException e) {

		}
		return null;
	}
	
	public static String insertReimbursement(HttpServletRequest req, HttpServletResponse resp) {
		try {
			ReimbursementDaoImpl reimbursementDaoImpl = new ReimbursementDaoImpl();
			User user = (User)req.getSession().getAttribute("User");
			String username = null;
			if(user != null)
				username = user.getUserName();

			Reimbursement reimbursement = new Reimbursement(Double.parseDouble(req.getParameter("amount")),
					req.getParameter("description"), username, req.getParameter("type"));
			reimbursementDaoImpl.insertReimbursement(reimbursement);
			return "/html/home.html";
		} catch (SQLException e) {
			System.out.println("Error creating reimbursement" + e.getMessage());
		}
		return null;
	}
	
	public static String approveDenyReimbursement(HttpServletRequest req, HttpServletResponse resp) {
		try {
			long id = Long.parseLong(req.getParameter("reimbId"));
			int status = Integer.parseInt(req.getParameter("reimbStatus"));
			User resolver = (User)req.getSession().getAttribute("User");
			ReimbursementDaoImpl reimbursementDaoImpl = new ReimbursementDaoImpl();
			
			reimbursementDaoImpl.updateReimbursement(id, status, resolver.getUserName());
			return "/html/home.html";
		} catch (SQLException e) {
			System.out.println("Error updating reimbursement" + e.getMessage());
		}
		return null;
	}
}

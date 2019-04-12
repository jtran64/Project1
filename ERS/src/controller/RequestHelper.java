package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestHelper {
	public static String process(HttpServletRequest req, HttpServletResponse resp) {
		
		switch(req.getRequestURI()) {
		
		case "/ERS/html/login.do":
			return LoginController.Login(req);
			
		case "/ERS/html/logout.do":
			return LogoutController.Logout(req, resp);
			
		case "/ERS/html/reimbsJSON.do":
			return ReimbursementController.getReimbursements(req, resp);
			
		case "/ERS/html/reimbJSON.do":
			return ReimbursementController.getReimbursement(req, resp);
			
		case "/ERS/html/createReimb.do":
			return ReimbursementController.insertReimbursement(req, resp);
			
		case "/ERS/html/getUser.do":
			return ProfileController.getProfile(req, resp);
			
		case "/ERS/html/approveDenyReq.do":
			return ReimbursementController.approveDenyReimbursement(req, resp);
			
		case "/ERS/html/updateProfile.do":
			return ProfileController.updateProfile(req);
			
		default: 
			return "/html/index.html";
		
		}
	}

}

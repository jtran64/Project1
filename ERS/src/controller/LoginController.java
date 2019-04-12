package controller;

import javax.servlet.http.HttpServletRequest;

import dto.User;
import repository.UserDaoImpl;

public class LoginController {
	public static String Login(HttpServletRequest req) {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		UserDaoImpl userDaoImpl = new UserDaoImpl();
		User user;
		user = userDaoImpl.getUser(username);
		
		if(user != null && username.equals(user.getUserName()) && password.equals(user.getPassword())) {
			req.getSession().setAttribute("User", user);
			return "/html/home.html";
		}
		return "/html/index.html";
	}
}

package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dto.User;
import repository.UserDaoImpl;

public class ProfileController {
	public static String getProfile(HttpServletRequest req, HttpServletResponse resp) {
		User user = (User) req.getSession().getAttribute("User");
		try {
			UserDaoImpl userDaoImpl = new UserDaoImpl();
			User currentUser = userDaoImpl.getUser(user.getUserName());
			if (currentUser != null) {
				Gson gson = new Gson();
				String json = gson.toJson(currentUser);
				System.out.println(json);
				resp.getWriter().write(json);
			}
		} catch (IOException e) {
			
		}
		return null;
	}

	public static String updateProfile(HttpServletRequest req) {
		ArrayList<String> updateDetails = new ArrayList<>();
		updateDetails.add(req.getParameter("password2"));
		updateDetails.add(req.getParameter("editFirst"));
		updateDetails.add(req.getParameter("editLast"));
		updateDetails.add(req.getParameter("editEmail"));
		
		System.out.println("Value of password2: " + updateDetails.get(0).equals(""));

		UserDaoImpl userDaoImpl = new UserDaoImpl();
		User user = (User) req.getSession().getAttribute("User");
		try {
			if (user != null && userDaoImpl.updateUser(updateDetails, user.getUserName())) {
				return "/html/profile.html";
			}
		} catch (SQLException e) {
			System.out.println("Could not update details" + e.getMessage());
		}
		return null;
	}
}

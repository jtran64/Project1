package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutController {
	public static String Logout(HttpServletRequest req, HttpServletResponse resp) {

		try {
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();
			req.getRequestDispatcher("/html/index.html").include(req, resp);
			
			HttpSession session = req.getSession();
			
			out.println("<script type=\"text/javascript\">");  
			out.println("alert('You have successfully logged out!');");  
			out.println("</script>");
			session.invalidate();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

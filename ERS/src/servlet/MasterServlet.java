package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.RequestHelper;

public class MasterServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestHelper.process(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String targetURL = RequestHelper.process(req, resp);
		if(req.getRequestURI().equals("/ERS/html/login.do") && targetURL == null) {
			resp.setContentType("text/html"); 
			PrintWriter out = resp.getWriter(); 
			req.getRequestDispatcher("index.html").forward(req, resp);
			 
			out.println("<script type=\"text/javascript\">");  
			out.println("alert('Invalid username and password combination!');");  
			out.println("</script>");
		}
		else
			req.getRequestDispatcher(targetURL).forward(req, resp);
	}
}

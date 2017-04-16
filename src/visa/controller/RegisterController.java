package visa.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.CookieGenerator;

import visa.dao.UserController;

@WebServlet("/register")
public class RegisterController extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "POST");
		String username = req.getParameter("username");
		String password= req.getParameter("password");
		String name = req.getParameter("name");
		String department = req.getParameter("department");
		String role = req.getParameter("role");
		String position = req.getParameter("position");
		UserController userController = new UserController();
		boolean isRegistered = userController.register(username, password, name, department, role, position);
		if (isRegistered){
			
            resp.sendError(200);
		}else{
			resp.sendError(404);
		}
		
	}
}

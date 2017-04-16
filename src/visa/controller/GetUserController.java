package visa.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import visa.dao.PostController;
import visa.dao.UserController;

/**
 * Servlet implementation class GetUserController
 */
@WebServlet("/user")
public class GetUserController extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		String userID = request.getParameter("id");
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(Cookie cookie: cookies){
				if(cookie.getName().equals("user"))
					{
						response.sendError(200);
						break;
					}
			}
		}else{
			response.sendError(401);
		}
		UserController uc = new UserController();
		String userJson = uc.getUserInfo(userID);
		
		PrintWriter pw = response.getWriter();
		response.setCharacterEncoding("utf-8");
		pw.write(userJson);
		pw.flush();
		
	}



}

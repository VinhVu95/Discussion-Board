package visa.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import visa.dao.PostController;

/**
 * Servlet implementation class DeletePostController
 */
@WebServlet("/delete")
public class DeletePostController extends HttpServlet {


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String postID = request.getParameter("id");
		PostController pc = new PostController();
		Cookie[] cookies = request.getCookies();
		String userID = "";
		if(cookies !=null){
			for(Cookie cookie: cookies){
				if(cookie.getName().equals("user"))
					{	
						userID = cookie.getValue();
					}
			}
		}else{
			response.sendError(404,"not login");
			return;
		}
		boolean isDeleted = pc.deletePost(postID,userID);	
		if (isDeleted){
			response.sendError(201);
		}else{
			response.sendError(404, "not deleted");
		}
		return;
	}

}

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
 * 
 * Like a post. If like successfully, return 201. Else return 404
 * input: id of post	
 *
 */
@WebServlet("/like")
public class LikePostController  extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String postID = req.getParameter("id");
	
		PostController postController = new PostController();
		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		resp.addHeader("Access-Control-Allow-Headers", "Content-Type");
		Cookie[] cookies = req.getCookies();
		String userID = "";
		if(cookies !=null){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals("user")) userID = cookie.getValue();
			}
		}else{
			resp.sendError(404,"not login");
			return;
		}
		boolean isSuccess = postController.addLike(postID, userID);
		
		if (isSuccess){
			resp.sendError(201);
		}else{
			resp.sendError(405, "liked before");
		}
		return;
		
	}
}

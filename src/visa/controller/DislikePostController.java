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
@WebServlet("/dislike")
public class DislikePostController  extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String postID = req.getParameter("id");
		PostController postController = new PostController();
		Cookie[] cookies = req.getCookies();
		String userID = "";
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "POST");
		if(cookies !=null){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals("user")) userID = cookie.getValue();
			}
		}else{
			resp.sendError(404, "not login");
			return;
		}
		boolean isSucess = postController.addDisLike(postID,userID);
		
		if (isSucess){
			resp.sendError(201);
		}else{
			resp.sendError(404, "like before");
		}
		return;
	}
}

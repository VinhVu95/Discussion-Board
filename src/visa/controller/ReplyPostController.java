package visa.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import visa.dao.PostController;
@WebServlet("/reply")
public class ReplyPostController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String postID = req.getParameter("id");
		
		String content = req.getParameter("content");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "POST");
		Cookie[] cookies = req.getCookies();
		String userID = "";
		if(cookies !=null){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals("user")) userID = cookie.getValue();
			}
		}else{
			resp.sendError(404);
		}
		PostController postController = new PostController();
		String replyId = postController.addReply(Integer.parseInt(postID),  content, userID);
		if (replyId == null){
			resp.sendError(404);
		}else{
//			String url = "topic?id=" + postID;
//			resp.sendRedirect(url);
			resp.addHeader("id", replyId);
			resp.sendError(200);
		}
	}
}

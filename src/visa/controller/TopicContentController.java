package visa.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import visa.dao.PostController;

/**
 * 
 * View a content of Topic
 * input : topic id
 *
 */
@WebServlet("/topic")
public class TopicContentController extends HttpServlet{
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("application/json");
		String topicID = req.getParameter("id");
		String offset = req.getParameter("offset");
		String size = req.getParameter("size");
		Cookie[] cookies = req.getCookies();
		String userID = "";
		if(cookies !=null){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals("user")) userID = cookie.getValue();
			}
		}else{
			resp.sendError(405);
		}
		PostController topicController = new PostController();
		String topicJson = "";
		if (topicID == null || topicID.equals("")){
			
			topicJson =  topicController.getPostContent(userID);
		}else{
			topicJson =  topicController.getPostContent(topicID, userID);
		}
		
		PrintWriter output = resp.getWriter();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "POST");
		resp.setCharacterEncoding("utf-8");
		output.write(topicJson);
		output.flush();
		
		
	}
	/**
	 * Create a post
	 * If cannot create, return 404 HTTP response code
	 * If can, redirect to the link in order to view the 
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Cookie[] cookies = req.getCookies();
		String userID = "";
		if(cookies !=null){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals("user")){
					userID = cookie.getValue();
					break;
				}
				
				
			}
		}else{
			resp.sendError(405);
			//return;
		}
		String title = req.getParameter("title");
		String content = req.getParameter("content");

		
		
		PostController postController = new PostController();
		String topicID = postController.createTopic(title, content, userID);
		//System.out.println (topicID);
		if (topicID == null){
			resp.sendError(403);
		}else{
			resp.addHeader("id", topicID);
			resp.setStatus(201);
			System.out.println (resp.getStatus());
			//resp.sendError(450, topicID);
			//resp.setStatus(Integer.parseInt(topicID));
//			String url = "topic?id=" + topicID;
	//		resp.sendRedirect(url);
		
		}
		return;
		
	}
	

}

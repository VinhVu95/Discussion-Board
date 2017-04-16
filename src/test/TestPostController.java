package test;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import visa.controller.TopicContentController;
import visa.dao.PostController;
import junit.framework.TestCase;

public class TestPostController extends TestCase{

	
	@Test
	public void testGetContent(){
		PostController postController = new PostController();
		System.out.println (postController.getPostContent("182","1"));
	}
//	public void testPostContent(){
//		PostController postController = new PostController();
//		for (int i = 0 ; i <50; i ++){
//			System.out.println (postController.createTopic("TITLE" + i, "CONTENT" + i, "2"));
//		}
//		//System.out.println (postController.createTopic("TITLE", "CONTENT", "2"));
//	}
	@Test
	public void testGet() throws IOException{
		  HttpServletRequest request = mock(HttpServletRequest.class);       
	        HttpServletResponse response = mock(HttpServletResponse.class);
	        when(response.getWriter()).thenReturn(new PrintWriter(System.out));

	        try {
				new TopicContentController().doGet(request, response);
				//assertEquals(200, response.getStatus());
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@Test
	public void testPost() throws ServletException, IOException{
		 HttpServletRequest request = mock(HttpServletRequest.class);
		 HttpServletResponse response = mock(HttpServletResponse.class);
		 when(request.getParameter("title")).thenReturn("testTitle");
		 when(request.getParameter("content")).thenReturn("testContent");
		 Cookie[] cookies = new Cookie[100];
		 Cookie cookie = new Cookie("user","2");
		 cookie.setMaxAge(60);
		 cookies[0] = cookie;
		 when(request.getCookies()).thenReturn(cookies);
		 
		 new TopicContentController().doPost(request, response);
		 //assertEquals(201,response.getStatus());
		 
	}
}

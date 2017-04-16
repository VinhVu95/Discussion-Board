package test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.*;

import javax.servlet.http.*;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.mockito.Mockito;

import visa.controller.LoginController;
import visa.controller.LogoutController;
import visa.dao.UserController;

public class TestLoginController extends TestCase{

   
//    public void testServlet() throws Exception {
//        HttpServletRequest request = mock(HttpServletRequest.class);       
//        HttpServletResponse response = mock(HttpServletResponse.class);    
//        HttpSession session = mock(HttpSession.class);
//        when(request.getParameter("username")).thenReturn("admin");
//        when(request.getParameter("password")).thenReturn("admin");
//        when(request.getSession()).thenReturn(session);
//        
//        PrintWriter writer = new PrintWriter("somefile.txt");
//        when(response.getWriter()).thenReturn(writer);
//
//        new LoginController().doPost(request, response);
//        System.out.println (session.getAttribute("user"));
//        
//        verify(request, atLeast(1)).getParameter("username"); // only if you want to verify username was called...
//        writer.flush(); // it may not have been flushed yet...
//        new LogoutController().doPost(request, response);
//        assertTrue(FileUtils.readFileToString(new File("somefile.txt"), "UTF-8")
//                   .contains("My Expected String"));
//        
//    }
//    public void testRegister(){
//    	UserController userController = new UserController();
//    	userController.register("test", "test", "test", "test", "test", "test");
//    }
    public void testLogin(){
    	UserController userController = new UserController();
    	System.out.println(userController.login("admin","admin"));
    	
    }
}
package visa.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.result.Output;
import org.springframework.web.util.CookieGenerator;

import visa.dao.UserController;

@WebServlet("/login")
public class LoginController extends HttpServlet{

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter output = new PrintWriter(new File("log.txt"));
		try{
			
			resp.addHeader("Access-Control-Allow-Origin", "*");
			resp.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
			resp.addHeader("Access-Control-Allow-Headers", "Content-Type");
			Cookie ck[]=req.getCookies();
			//If other user is logged in
			if 	(ck != null){
				resp.sendError(406, ck[0].getValue());
				return;
			}
			String username = req.getParameter("username").trim();
			String password= req.getParameter("password").trim();
			UserController userController = new UserController();
			int userID = userController.login(username, password);
			System.out.println (userID);
			if (userID != -1){
				Cookie loginCookie = new Cookie("user",String.valueOf(userID));
				loginCookie.setMaxAge(30*60);
				resp.addCookie(loginCookie);
				
	            //setting cookie to expiry in 30 mins
				
				/*
				 * return cookies header
				 */
				StringBuffer buffer = new StringBuffer();
			    int version = loginCookie.getVersion();
			    if (version != -1) {
			       buffer.append("$Version=\"");          
			       buffer.append(loginCookie.getVersion());
			       buffer.append("\"; ");
			    }
			    // cookie name/value
			    buffer.append(loginCookie.getName());
			    //buffer.append("=\"");
			    buffer.append("=");
			    buffer.append(loginCookie.getValue());
			    //buffer.append("\"; ");

			    // cookie path
			    String path = loginCookie.getPath();
			    if (path != null) {
			        
			        buffer.append("; path=");
			        buffer.append(path);
			                
			    }

			    String cookieHeader = buffer.toString();
			    resp.addHeader("access_token", cookieHeader);
				output.println(userID);
				output.close();
				resp.sendError(200);
				
				return;
			}else{
				output.println(userID);
				output.close();
				resp.sendError(451, String.valueOf(userID));
			}
		}catch (Exception e){
			output.println(e.getLocalizedMessage());
			output.close();
			resp.sendError(405,e.getMessage());
		}
		output.close();
		
	}
}

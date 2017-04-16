package visa.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;

import visa.entity.Post;
import visa.entity.User;
import visa.general.ConnectionManager;

public class PostController {
	
	
	/**
	 * Add 1 like when user click like
	 * @param post id which is liked
	 * @return return true if like success
	 */
	public boolean addLike(String id, String userID){

	    try{
	    	/*
	    	 * Get current number like
	    	 */
			Connection conn = ConnectionManager.getConnection();
			String sql = "SELECT count(*) FROM user_like ul  WHERE  ul.user_id = '" + userID + "' AND ul.post_id = '" + id + "'" ;
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        rs.next();
	        if ( rs.getInt(1) > 0){
	        	return false;
	        }
	        
	        /*
	         * Update number like
	         */
	        sql = "UPDATE post SET number_like = number_like + 1 WHERE id = " + id;
	        ps = conn.prepareStatement(sql);
	        ps.execute();
	        /*
	         * update in user_like table
	         */
	        String sql4 = "INSERT INTO `user_like`(`user_id`,`post_id`)VALUES(" + userID + ", " + id + ");";
	        ps = conn.prepareStatement(sql4);
	        ps.execute();
	        return true;

	    }catch (Exception e){
	    	e.printStackTrace();
	    }
		return false;
		
	}
	
	/**
	 * Create a reply for given topic or reply
	 * @param postID topic or reply which is replied
	 * @param content content of the reply
	 * @param title title of the reply
	 * @param userID user ID who reply
	 * @return id of the TOPIC which contain this reply
	 */
	public String addReply(int postID, String content, String userID){
		int lastID;
		Date createdDate = new Date(System.currentTimeMillis());
		try{    
			Connection conn = ConnectionManager.getConnection();
			/*
			 * INSERT CONTENT TO POST TABLE
			 */
			
			String sql = "INSERT INTO `post`(`content`,`user_id`,`number_like`,`number_dislike`,`created_date`, `modified_date`)VALUES('"+ content + "','" + userID +"','0','0','" + createdDate + "','" + createdDate + "');";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.execute();
	        /*
			 * INSERT TO USER_POST TABLE
			 */
	        String sql2 = "SELECT max(id) FROM `post`";
	        ps = conn.prepareStatement(sql2);
	        ResultSet rs = ps.executeQuery();
	        rs.next();
	        lastID = rs.getInt(1);
			String sql3 = "INSERT INTO `user_post`(`userid`,`postid`)VALUES(" + userID + ", " + lastID + ");";
		
			  ps = conn.prepareStatement(sql3);
			ps.execute();
			
			/*
			 * INSERT POST_POST TABLE 
			 */
			String sql4 = "INSERT INTO `post_post`(`parentid`,`childid`)VALUES(" + postID + ", " + lastID + ");";
			ps = conn.prepareStatement(sql4);
			ps.execute();
			
		  
	    }catch (Exception e){
	    	e.printStackTrace();
	    	return null;
	    }
		
		return String.valueOf(lastID);
		
	}
	
	/**
	 * Add dislike
	 * @param post id which is liked
	 * @return
	 */
	public boolean addDisLike(String postID, String userID){
		  try{
		    	/*
		    	 * Get current number like
		    	 */
				Connection conn = ConnectionManager.getConnection();
				String sql = "SELECT count(*) FROM user_dislike ul  WHERE  ul.user_id = '" + userID + "' AND ul.post_id = '" + postID + "'" ;
		        PreparedStatement ps = conn.prepareStatement(sql);
		        ResultSet rs = ps.executeQuery();
		        rs.next();
		        if ( rs.getInt(1) > 0){
		        	return false;
		        }
		      
		        /*
		         * Update number like
		         */
		        sql = "UPDATE post SET number_dislike = number_dislike + 1 WHERE id = " + postID;
		        ps = conn.prepareStatement(sql);
		        ps.execute();
		        
		        /*
		         * update in user_like table
		         */
		        String sql4 = "INSERT INTO `user_dislike`(`user_id`,`post_id`)VALUES(" + userID + ", " + postID + ");";
		        ps = conn.prepareStatement(sql4);
		        ps.execute();
		        return true;

		    }catch (Exception e){
		    	e.printStackTrace();
		    }
			return false;
			
	}
	/**
	 * Delete a post
	 * @param id
	 * @return boolean
	 */
	public boolean deletePost(String id,String loggedInID) {
		try{
			 Connection conn = ConnectionManager.getConnection();
			 //Check if the user delete post is the post owner
			 String sql2 = "SELECT * FROM user_post up WHERE up.postid="+id;
			 PreparedStatement ps = conn.prepareStatement(sql2);
			 ResultSet rs = ps.executeQuery();
			 rs.next();
			 if (!rs.getString("userid").toString().trim().equals(loggedInID.trim()))
			 {	
				 System.out.println("Not authorised user");
				 return false;
			 }
				 
			 //Update the deleted column
			 String sql = "UPDATE post SET deleted = 1 WHERE id= "+ id;
			 ps = conn.prepareStatement(sql);
			 ps.execute();
			 return true;
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * Get content of a post
	 * @param id
	 * @return
	 */
	public String getPostContent(String id, String loggedInID) {
		// TODO Auto-generated method stub
		
		String jsonString = "";
		//ArrayList<DocumentTypeDAO> docTypeList = new ArrayList<DocumentTypeDAO>();
	       // ProductDAO productDAO = new ProductDAO();
	    try{    
			Connection conn = ConnectionManager.getConnection();
			
			String sql = "SELECT p.id, p.content, p.title, p.number_like, p.number_dislike, p.created_date, p.modified_date, p.user_id, u.name, u.position, u.department, u.role"
					+ " FROM post p INNER JOIN user u WHERE  p.id = '" + id + "' AND p.user_id = u.id";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        rs.next();
	       // while (rs.next()) {
	    	Post post = new Post();
	    	post.setId(String.valueOf(rs.getInt("id")));
	    	post.setContent(rs.getString("content"));
	    	post.setTitle(rs.getString("title"));
	    	post.setNumberLike(rs.getInt("number_like"));
	    	post.setNumberDisLike(rs.getInt("number_dislike"));
	    	post.setCreatedDate(rs.getDate("created_date"));
	    	post.setModifiedDate(rs.getDate("modified_date"));
			/*
			 * Get created user
			 */
	    	User createdUser =  new User();
	    	int userID = rs.getInt("user_id");
	    	System.out.println(userID);
	    	createdUser.setName(rs.getString("name"));
	    	createdUser.setId(String.valueOf(userID));
	    	createdUser.setDepartment(rs.getString("department"));
	    	createdUser.setRole(rs.getString("role"));
	    	createdUser.setPosition(rs.getString("position"));
	    	
//			String sql2 = "SELECT DISTINCT up.postid FROM user_post up INNER JOIN post_post pp  WHERE up.postid <> pp.childid AND up.userid = " + userID ;
//			ps = conn.prepareStatement(sql2);
//			ArrayList<Integer> listTopic =  new ArrayList<Integer>();
//	        ResultSet rs2 = ps.executeQuery();
//	        while (rs2.next()){
//	        	listTopic.add(rs2.getInt(1));
//	        }
//	        
//	        createdUser.setListTopic(listTopic);
	        
//	        String sql3 = "SELECT DISTINCT up.postid FROM user_post up INNER JOIN post_post pp  WHERE up.postid = pp.childid AND up.userid = " + userID ;
//			ps = conn.prepareStatement(sql3);
//			ArrayList<Integer> listReply =  new ArrayList<Integer>();
//			
//	        ResultSet rs3 = ps.executeQuery();
//	        while (rs3.next()){
//	        	listReply.add(rs3.getInt(1));
//	        }
//	        
//	        createdUser.setListReply(listReply);
	        //System.out.println("Test from here");
	        System.out.println(createdUser);
	        //System.out.println("Test end here");
	    	post.setCreatedUser(createdUser);
	    	/*
	    	 * Get reply of post
	    	 */
	    	 String sql4 = "SELECT DISTINCT * FROM post p INNER JOIN post_post pp  WHERE p.id = pp.childid AND pp.parentid = " + post.getId() +" AND p.deleted='0'";
			 ps = conn.prepareStatement(sql4);
			 ArrayList<Post> listReply2 =  new ArrayList<Post>();
	        ResultSet rs4 = ps.executeQuery();
	        while (rs4.next()){
	        	Post reply = new Post();
	        	reply.setId(rs4.getString(1));
	        	reply.setContent(rs4.getString(2));
	        	reply.setCreatedDate(rs4.getDate(3));
	        	reply.setModifiedDate(rs4.getDate(4));
	        	reply.setNumberLike(rs4.getInt(6));
	        	reply.setNumberDisLike(rs4.getInt(7));
	        	/*
	        	 * get user of each reply
	        	 */
	        	String sql8 = "SELECT * from user INNER JOIN post where user.id=post.user_id AND post.id="+ reply.getId();
	        	PreparedStatement ps8 = conn.prepareStatement(sql8);
	        	ResultSet rs8 = ps8.executeQuery();
	        	rs8.next();
	        	User createdReplyUser = new User();
	        	createdReplyUser.setName(rs8.getString("name"));
	        	reply.setCreatedUser(createdReplyUser);
	        	/*
	        	 * Check if current user liked this comment or not
	        	 */
	        	String sql6 = "SELECT count(*) FROM user_like WHERE user_like.user_id = "+ loggedInID + " AND user_like.post_id=" + reply.getId();
	        	ps = conn.prepareStatement(sql6);
	        	rs = ps.executeQuery();
	        	rs.next();
	        	
	        	if(rs.getInt(1) > 0){
	        		reply.setLiked(true);
	        	}else{
	        		reply.setLiked(false);
	        	}
	        	/*
	        	 * Check if current user disliked this comment or not
	        	 */
	        	String sql7 = "SELECT count(*) FROM user_dislike WHERE user_dislike.user_id = "+ loggedInID +" AND user_dislike.post_id=" + reply.getId();
	        	ps = conn.prepareStatement(sql7);
	        	rs = ps.executeQuery();
	        	rs.next();
	        	
	        	if(rs.getInt(1) > 0){
	        		reply.setDisLiked(true);
	        	}else{
	        		reply.setDisLiked(false);
	        	}
	        	/*
	        	 * get replies of reply
	        	 */
	        	String sql5 = "SELECT DISTINCT * FROM post p INNER JOIN post_post pp  WHERE p.id = pp.childid AND pp.parentid = " + reply.getId() +" AND p.deleted='0'" ;
	        	PreparedStatement ps2 = conn.prepareStatement(sql5);
	        	ResultSet rs5 = ps2.executeQuery();
	        	ArrayList<Post> listReplyReply =  new ArrayList<Post>();
	        	while (rs5.next()){
		        	Post replyReply = new Post();
		        	replyReply.setId(rs5.getString(1));
		        	replyReply.setContent(rs5.getString(2));
		        	replyReply.setCreatedDate(rs5.getDate(3));
		        	replyReply.setModifiedDate(rs5.getDate(4));
		        	replyReply.setNumberLike(rs5.getInt(6));
		        	replyReply.setNumberDisLike(rs5.getInt(7));
		        	/*
		        	 * get replies of reply popularity
		        	 */
		        	replyReply.setPopularity(replyReply.getNumberLike()+replyReply.getNumberDisLike());
		        	/*
		        	 * check if current user like this reply to reply or not
		        	 */
//		        	String sql9 = "SELECT count(*) FROM user_like WHERE user_like.user_id ="+ loggedInID+ "AND user_like.post_id =" + replyReply.getId();
//		        	ps2 = conn.prepareStatement(sql9);
//		        	ResultSet rs6 = ps2.executeQuery();
//		        	rs6.next();
//		        	if(rs6.getInt(1) > 0){
//		        		replyReply.setLiked(true);
//		        	}else{
//		        		replyReply.setLiked(false);
//		        	}
		        	
		        	listReplyReply.add(replyReply);
	        	}
	        	reply.setListReply(listReplyReply);
	            listReply2.add(reply);
	            int replyPopularity = reply.getListReply().size()*2 + reply.getNumberLike() + reply.getNumberDisLike();
	            reply.setPopularity(replyPopularity);
	        }
	        post.setListReply(listReply2);
	        

	    	int popularity = post.getListReply().size()*2 + post.getNumberLike() + post.getNumberDisLike();
			
			post.setPopularity(popularity);
			ObjectMapper objectMapper = new ObjectMapper();
			
			
			jsonString += objectMapper.writeValueAsString(post);
			
			
				
			
	    }catch (Exception e){
	    	e.printStackTrace();
	    }
		return jsonString;
	}
	
	
	
	/**
	 * 
	 * @param title
	 * @param content
	 * @param createdUser
	 * @return ID of created post
	 */
	public String createTopic(String title, String content, String createdUser) {
		// TODO Auto-generated method stub
		int lastID;
		Date createdDate = new Date(System.currentTimeMillis());
		try{    
			Connection conn = ConnectionManager.getConnection();
			/*
			 * INSERT CONTENT TO POST TABLE
			 */
			
			String sql = "INSERT INTO `post`(`content`,`user_id`,`number_like`,`number_dislike`,`title`,`created_date`, `modified_date`)VALUES('"+ content + "','" + createdUser +"',0,0,'" + title + "','" + createdDate + "','" + createdDate + "');";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.execute();
	        /*
			 * INSERT TO USER_POST TABLE
			 */
	        String sql2 = "SELECT max(id) FROM `post`";
	        ps = conn.prepareStatement(sql2);
	        ResultSet rs = ps.executeQuery();
	        rs.next();
	        lastID = rs.getInt(1);
			String sql3 = "INSERT INTO `user_post`(`userid`,`postid`)VALUES(" + createdUser + ", " + lastID + ");";
			
			ps = conn.prepareStatement(sql3);
			ps.execute();
			
		  
	    }catch (Exception e){
	    	e.printStackTrace();
	    	return e.getMessage();
	    }
		
		return String.valueOf(lastID);
	}

	public String getPostContent(String loggedInID) {
		// TODO Auto-generated method stub
		String jsonString = "";
		ArrayList<Post> topics = new ArrayList<Post>();
		//ArrayList<DocumentTypeDAO> docTypeList = new ArrayList<DocumentTypeDAO>();
	       // ProductDAO productDAO = new ProductDAO();
	    try{    
			Connection conn = ConnectionManager.getConnection();
			
			String sql = "SELECT DISTINCT p.id, p.content, p.title, p.number_like, p.number_dislike, p.created_date, p.modified_date, p.user_id, u.name, u.position, u.department, u.role "
					+ "FROM post as p INNER JOIN user u WHERE NOT EXISTS (SELECT pp.childid FROM post_post pp WHERE p.id = pp.childid) AND u.id = p.user_id AND p.deleted='0'";
			
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        //rs.next();
	       while (rs.next()) {
		    	Post post = new Post();
		    	post.setId(String.valueOf(rs.getInt("id")));
		    	post.setNumberLike(rs.getInt("number_like"));
		    	post.setNumberDisLike(rs.getInt("number_dislike"));
		    	post.setContent(rs.getString("content"));
		    	post.setTitle(rs.getString("title"));
		    	post.setCreatedDate(rs.getDate("created_date"));
		    	post.setModifiedDate(rs.getDate("modified_date"));
	
		    	User createdUser =  new User();
		    	int userID = rs.getInt("user_id");
		    	
		    	createdUser.setName(rs.getString("name"));
		    	createdUser.setId(String.valueOf(userID));
		    	createdUser.setDepartment(rs.getString("department"));
		    	createdUser.setRole(rs.getString("role"));
		    	createdUser.setPosition(rs.getString("position"));
		       
		    	/*
		    	 * Get topics of user who create the post
		    	 */
				String sql2 = "SELECT DISTINCT up.postid FROM user_post up INNER JOIN post_post pp  WHERE up.postid <> pp.childid AND up.userid = " + userID ;
				ps = conn.prepareStatement(sql2);
				ArrayList<Integer> listTopic =  new ArrayList<Integer>();
		        ResultSet rs2 = ps.executeQuery();
		        while (rs2.next()){
		        	listTopic.add(rs2.getInt(1));
		        }
		        createdUser.setListTopic(listTopic);
		        
		        /*
		         * Get replies of user who create the post
		         */
		        String sql3 = "SELECT DISTINCT up.postid FROM user_post up INNER JOIN post_post pp  WHERE up.postid = pp.childid AND up.userid = " + userID ;
				ps = conn.prepareStatement(sql3);
				ArrayList<Integer> listReply =  new ArrayList<Integer>();
				
		        ResultSet rs3 = ps.executeQuery();
		        while (rs3.next()){
		        	listReply.add(rs3.getInt(1));
		        }
		        createdUser.setListReply(listReply);
		    	post.setCreatedUser(createdUser);
		    	
		    	
		    	 /*
		         * Check if current user is liked this post or not
		         * False if no one logged in
		         */
		    	String sql6 = "SELECT count(*) FROM user_like ul WHERE ul.user_id = '" + loggedInID + "' AND ul.post_id = " + post.getId() ;
//		    	if (loggedInID == null || loggedInID.equals("")){
//		    		sql6 = "SELECT count(*) FROM user_like ul WHERE  ul.post_id = " + post.getId() ;
//		    	}
		    	ps = conn.prepareStatement(sql6);
		    	rs2 = ps.executeQuery();
		    	rs2.next();
		    	if (rs2.getInt(1) > 0){
		    		post.setLiked(true);
		    	}else{
		    		post.setLiked(false);
		    	}
		    	
		    	/*
		         * Check if current user is liked this post or not
		         * False if no one logged in
		         */
		    	String sql7 = "SELECT count(*) FROM user_dislike ul WHERE ul.user_id = '" + loggedInID + "' AND ul.post_id = " + post.getId() ;
		    	
		    	ps = conn.prepareStatement(sql7);
		    	rs2 = ps.executeQuery();
		    	rs2.next();
		    	if (rs2.getInt(1) > 0){
		    		post.setDisLiked(true);
		    	}else{
		    		post.setDisLiked(false);
		    	}
		    	/*
		    	 * Count the number of reply of post
		    	 */
		    	/*
		         * Check if current user is liked this post or not
		         * False if no one logged in
		         */
		    	String sql8 = "SELECT count(*) FROM post_post pp WHERE pp.parentid = '" + post.getId() +"'";
		    	
		    	ps = conn.prepareStatement(sql8);
		    	rs2 = ps.executeQuery();
		    	rs2.next();
		    	int numberReply = rs2.getInt(1);
		    	int popularity = numberReply*2 + post.getNumberLike() + post.getNumberDisLike();
				
				post.setPopularity(popularity);
				topics.add(post);
	       }
	       
	       ObjectMapper objectMapper = new ObjectMapper();
			
			
			jsonString += objectMapper.writeValueAsString(topics);
			jsonString += "\n";
				
			
	    }catch (Exception e){
	    	e.printStackTrace();
	    }
		return jsonString;
	}
	
}

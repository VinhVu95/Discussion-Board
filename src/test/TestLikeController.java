package test;

import visa.dao.PostController;
import junit.framework.TestCase;

public class TestLikeController extends TestCase {
	public void testAddLike(){
		PostController postController  = new PostController();
		assertTrue(postController.addLike("159", "1"));
	}
	
	public void testAddDisLike(){
		PostController postController  = new PostController();
		assertTrue(postController.addDisLike("158", "1"));
	}


}

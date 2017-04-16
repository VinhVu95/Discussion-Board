package test;

import visa.dao.PostController;
import junit.framework.TestCase;

public class TestDeletePost extends TestCase {
	
	public void testDeletePost(){
		PostController pc = new PostController();
		assertTrue(pc.deletePost("182","1"));
	}
}

package test;

import junit.framework.TestCase;
import visa.dao.PostController;

public class TestReplyController extends TestCase{

	public void testAddReply(){
		PostController postController = new PostController();
		postController.addReply(1, "content of reply", "1");
	}
}

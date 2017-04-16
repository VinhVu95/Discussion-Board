package visa.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * 
 * Abstract class for post. Both Topic and Reply can considered as a post.
 * Class Topic and Reply extends this abstract class
 *
 */
public class Post{
	protected String title;
	protected String id;
 	protected String content;
 	protected int numberLike;
 	protected int numberDisLike;
 	protected Date createdDate;
 	protected Date modifiedDate;
 	protected User createdUser;
 	protected ArrayList<Post> listReply =  new ArrayList<Post>();
 	protected String parentID;				//If this post is topic, parentID = null\
 	protected boolean isLiked;
 	protected boolean isDisLiked;
 	public void setDisLiked(boolean isDisLiked) {
		this.isDisLiked = isDisLiked;
	}
 	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}
 	
 	public boolean isDisLiked() {
		return isDisLiked;
	}
 	public boolean isLiked() {
		return isLiked;
	}
 	
 	protected int popularity;

	public int getPopularity() {
		return popularity;
	}
	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}


 	
 	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
 	public Date getCreatedDate() {
		return createdDate;
	}
 	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
 	public Date getModifiedDate() {
		return modifiedDate;
	}
 	public void setTitle(String title) {
		this.title = title;
	}
 	
 	public String getTitle() {
		return title;
	}
 	public void setParentID(String parentID) {
		this.parentID = parentID;
	}
 	
 	public String getParentID() {
		return parentID;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the numberLike
	 */
	public int getNumberLike() {
		return numberLike;
	}
	/**
	 * @param numberLike the numberLike to set
	 */
	public void setNumberLike(int numberLike) {
		this.numberLike = numberLike;
	}
	/**
	 * @return the numberDisLike
	 */
	public int getNumberDisLike() {
		return numberDisLike;
	}
	/**
	 * @param numberDisLike the numberDisLike to set
	 */
	public void setNumberDisLike(int numberDisLike) {
		this.numberDisLike = numberDisLike;
	}
	
	/**
	 * @return the createdUser
	 */
	public User getCreatedUser() {
		return createdUser;
	}
	/**
	 * @param createdUser the createdUser to set
	 */
	public void setCreatedUser(User createdUser) {
		this.createdUser = createdUser;
	}
 	
	public void setListReply(ArrayList<Post> listReply) {
		this.listReply = listReply;
	}
	public ArrayList<Post> getListReply() {
		return listReply;
	}
}

package visa.entity;

import java.util.ArrayList;

public class User {
	private String id;
	private String name;
	private String position;
	private String role;
	private String userID;
	private ArrayList<Integer> listTopic = new ArrayList<Integer>();
	private ArrayList<Integer> listReply = new ArrayList<Integer>();
	private String department;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public ArrayList<Integer> getListTopic() {
		return listTopic;
	}
	public void setListTopic(ArrayList<Integer> listTopic) {
		this.listTopic = listTopic;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public ArrayList<Integer> getListReply() {
		return listReply;
	}
	public void setListReply(ArrayList<Integer> listReply) {
		this.listReply = listReply;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}

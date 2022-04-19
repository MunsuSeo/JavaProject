package userInfo;

import java.sql.Timestamp;

public class UserInfoBean {
	private String user_id;
	private String user_pwd;
	private String user_address;
	private int adminYN;
	private int user_mileage;
	private String user_tel;
	private Timestamp user_birth;
	private String user_name;
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_tel() {
		return user_tel;
	}
	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	public String getUser_address() {
		return user_address;
	}
	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}
	public int getAdminYN() {
		return adminYN;
	}
	public void setAdminYN(int adminYN) {
		this.adminYN = adminYN;
	}
	public int getUser_mileage() {
		return user_mileage;
	}
	public void setUser_mileage(int user_mileage) {
		this.user_mileage = user_mileage;
	}
	
	public Timestamp getUser_birth() {
		return user_birth;
	}
	public void setUser_birth(Timestamp user_birth) {
		this.user_birth = user_birth;
	}
	

	
}

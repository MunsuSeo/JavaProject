package userInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserInfoDBBean {
	private static UserInfoDBBean instance=new UserInfoDBBean();
	public static UserInfoDBBean getInstance() {
		return instance;
	}
	public Connection getConnection() throws Exception{
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}
	public int insertMember(UserInfoBean member) throws Exception {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="insert into userinfo values(?,?,?,?,?,?,?,?)";
		int re=-1;
		
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getUser_id());
			pstmt.setString(2, member.getUser_pwd());
			pstmt.setString(3, member.getUser_tel());
			pstmt.setString(4, member.getUser_address());
			pstmt.setTimestamp(5, member.getUser_birth());
			pstmt.setInt(6, 0);
			pstmt.setInt(7, member.getAdminYN());
			pstmt.setString(8, member.getUser_name());
			pstmt.executeUpdate();
			
			re=1;
 			pstmt.close();
 			conn.close();
 			
			System.out.println("추가 성공");
		} catch (Exception e) {
			System.out.println("추가 실패");
			e.printStackTrace();
		}
		
		return re;
	}
	
	public int confirmID(String id) throws Exception{ //가입된 아이디인지 확인
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select user_id from userinfo where user_id=?";
		int re=-1;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
 			rs = pstmt.executeQuery();
 			
 			if (rs.next()) { //이미 있는 아이디
 				re=1;
			}else { //없는 아이디
				re=-1;
			}
 			
 			rs.close();
 			pstmt.close();
 			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return re;
	}
	
	public int userCheck(String id, String pwd) throws Exception{ //아이디와 비밀번호가 맞는지 확인
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select user_pwd from userinfo where user_id=?";
		int re=-1;
		String db_user_pwd;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
 			rs = pstmt.executeQuery();
 			
 			if(rs.next()) {
 				db_user_pwd = rs.getString("user_pwd");
 				
 				if (db_user_pwd.equals(pwd)) {
					re=1;
				}else {
					re=0;
				}
 			}else {
 				re=-1;
			}
 			
 			rs.close();
 			pstmt.close();
 			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return re;
	}
	
	public UserInfoBean getMember(String id) throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select USER_ID, USER_PWD, USER_PHONENUMBER, USER_ADDRESS, USER_BIRTHDATE, USER_MILEAGE, ADMINYN, USER_NAME from userinfo where user_id=?";
		UserInfoBean member=null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
 			rs = pstmt.executeQuery();
 			
 			if (rs.next()) {
				member = new UserInfoBean();
				member.setUser_id(rs.getString("user_id"));
				member.setUser_pwd(rs.getString("user_pwd"));
				member.setUser_tel(rs.getString("USER_PHONENUMBER"));
				member.setUser_address(rs.getString("user_address"));
				member.setUser_birth(rs.getTimestamp("user_birthdate"));
				member.setUser_mileage(rs.getInt("user_mileage"));
				member.setAdminYN(rs.getInt("adminYN"));
				member.setUser_name(rs.getString("USER_NAME"));
			}
 			
 			rs.close();
 			pstmt.close();
 			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return member;
	}
	
	public int updateMember(UserInfoBean member) throws Exception {
		Connection conn=null;
		PreparedStatement pstmt=null;
		int re=-1;
		
		String sql="update userinfo set user_pwd=?, user_address=?, user_tel3=? where user_id=?";
		
		try {
			conn=getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getUser_pwd());
			pstmt.setString(2, member.getUser_address());
			pstmt.setString(3, member.getUser_tel());
			re = pstmt.executeUpdate();
			
			System.out.println("변경 성공");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("변경 실패");
		}
		
		return re;
	}
	
	public int deleteMember(String user_id, String user_pwd) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="";
		String pwd="";
		int re=-1;
		
		try {
			conn = getConnection();
			sql="select b_pwd from userinfo where user_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				pwd = rs.getString(1);
				if (pwd.equals(user_pwd)) {
					sql="delete from userinfo where user_id=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, user_id);
					pstmt.executeUpdate();
					re=1;
				}else {
					re=0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return re;
	}
}

package order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class OrderDBBean {

	private static OrderDBBean instance = new OrderDBBean();
	
	//OrderDBBean��ü ����
	public static OrderDBBean getInstance(){
		return instance;
	}
	
	//DBCP ��ü ����
	public Connection getConnection() throws Exception{
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/oracle");
		return ds.getConnection();
	}
	
	//�ֹ����̺� �ֹ����� �����ϴ� �޼ҵ�
	public int insertOrder(OrderBean order) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String insertQuery = "insert into order_table values(?,?,?,?,?)";
		int re = 0;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(insertQuery);
			pstmt.setInt(1, order.getOrder_number());
			pstmt.setString(2, order.getUser_id());
			pstmt.setInt(3, order.getBook_number());
			pstmt.setInt(4, order.getOrder_amount());
			pstmt.setTimestamp(5, order.getOrder_date());
			re = pstmt.executeUpdate();//���� ������ re=1�� ��
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return re;
	}
	
	//��� �ֹ������� �������� ���̺�
	public ArrayList<OrderBean> getAllOrder() {
		String selectQuery = "select * from order_table";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<OrderBean> orders = null;
		OrderBean order = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(selectQuery);
			rs = pstmt.executeQuery();
			orders = new ArrayList<OrderBean>();
			
			while (rs.next()) {
				order = new OrderBean();
				order.setOrder_number(rs.getInt(1));
				order.setUser_id(rs.getString(2));
				order.setBook_number(rs.getInt(3));
				order.setOrder_amount(rs.getInt(4));
				order.setOrder_date(rs.getTimestamp(5));
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
		return orders;
	}
	
	//�ֹ������� �Ű������� �ֹ������� �������� �޼ҵ�
	public OrderBean getOrder(int order_number) {
		String selectQuery = "select * from order_table where order_number=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderBean order = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(selectQuery);
			pstmt.setInt(1, order_number);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				order = new OrderBean();
				order.setOrder_number(rs.getInt(1));
				order.setUser_id(rs.getString(2));
				order.setBook_number(rs.getInt(3));
				order.setOrder_amount(rs.getInt(4));
				order.setOrder_date(rs.getTimestamp(5));
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
		return order;
	}
	
}

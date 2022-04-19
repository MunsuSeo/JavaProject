package v_order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class V_orderDBBean {
	private static V_orderDBBean instance = new V_orderDBBean();
	
	//객체를 리턴해주는 메소드
	public static V_orderDBBean getInstance(){
		return instance;
	}
	
	//dbcp객체를 리턴해주는 메소드
	public Connection getConnection() throws Exception{
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/oracle");
		return ds.getConnection();
	}
	
	public ArrayList<V_orderBean> getAllOrderView(){
		String selectQuery = "select * from v_order order by order_date";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<V_orderBean> orders = null;
		
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectQuery);
			orders = new ArrayList<V_orderBean>();
			
			while (rs.next()) {
				V_orderBean order_view = new V_orderBean();
				order_view.setOrder_number(rs.getInt(1));
				order_view.setOrder_date(rs.getTimestamp(2));
				order_view.setUser_id(rs.getString(3));
				order_view.setUser_name(rs.getString(4));
				order_view.setUser_phoneNumber(rs.getString(5));
				order_view.setUser_address(rs.getString(6));
				order_view.setBook_number(rs.getInt(7));
				order_view.setBook_name(rs.getString(8));
				
				orders.add(order_view);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return orders;
	}
}

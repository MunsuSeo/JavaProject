package bookInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class BookInfoDBBean {
	private static BookInfoDBBean instance = new BookInfoDBBean();
	
	//객체를 리턴해주는 메소드
	public static BookInfoDBBean getInstance(){
		return instance;
	}
	
	//dbcp객체를 리턴해주는 메소드
	public Connection getConnection() throws Exception{
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/oracle");
		return ds.getConnection();
	}
	
	//Database에 책 정보를 추가하는 메소드
	public int insertBookInfo(BookInfoBean book) throws Exception {//데이터 삽입
		String insertQuery = "insert into bookinfo values(?,?,?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		int re = -1;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(insertQuery);
			pstmt.setInt(1, book.getBook_number());
			pstmt.setString(2, book.getBook_name());
			pstmt.setInt(3, book.getBook_price());
			pstmt.setString(4, book.getBook_info());
			pstmt.setString(5, book.getBook_img());
			pstmt.setString(6, book.getBook_pub());
			pstmt.setString(7, book.getBook_author());
			pstmt.setInt(8, book.getBook_stock());

			re = pstmt.executeUpdate();
			
			re = 1;
			pstmt.close();
			conn.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return re;
	}
	
	//책 번호를 가져오는 메소드
	public int getCategoryNumber(String str_category) throws Exception{
		int category = Integer.parseInt(str_category+"00000");
		String selectQuery = "select count(*) from bookinfo where book_number >= ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int re = 0;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(selectQuery);
			
			pstmt.setInt(1, category);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				re = category + rs.getInt(1) + 1;
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return re;
	}
	
	//db에서 책의 정보를 가져오는 메소드
	public ArrayList<BookInfoBean> getBookList(int page, String category) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String minCategory = category + "00000";
		String maxCategory = category + "99999";
		ArrayList<BookInfoBean> books = null;
		String selectQuery = "select * from bookinfo where book_number between ? and ? and rownum<?";
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(selectQuery);
			pstmt.setString(1, minCategory);
			pstmt.setString(2, maxCategory);
			pstmt.setInt(3, page*10);
			rs = pstmt.executeQuery();
			books = new ArrayList<BookInfoBean>();
			while (rs.next()) {
				BookInfoBean book = new BookInfoBean();
				book.setBook_number(rs.getInt(1));
				book.setBook_name(rs.getString(2));
				book.setBook_price(rs.getInt(3));
				book.setBook_info(rs.getString(4));
				book.setBook_img(rs.getString(5));
				book.setBook_pub(rs.getString(6));
				book.setBook_author(rs.getString(7));
				books.add(book);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return books;
	}
	
	public ArrayList<BookInfoBean> getSearchBookList(String search){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BookInfoBean> books = null;
		search = "%"+search+"%";
		System.out.println(search);
		String selectQuery = "select * from bookinfo where book_name like ? or book_author like ?";
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(selectQuery);
			pstmt.setString(1, search);
			pstmt.setString(2, search);
			rs = pstmt.executeQuery();
			books = new ArrayList<BookInfoBean>();
			while (rs.next()) {
				BookInfoBean book = new BookInfoBean();
				book.setBook_number(rs.getInt(1));
				book.setBook_name(rs.getString(2));
				book.setBook_price(rs.getInt(3));
				book.setBook_info(rs.getString(4));
				book.setBook_img(rs.getString(5));
				book.setBook_pub(rs.getString(6));
				book.setBook_author(rs.getString(7));
				books.add(book);
			}
		} catch (Exception se) {
			se.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return books;
	}
	
	//하나의 책에대한 데이터를 가져오는 메소드
	public BookInfoBean getBookInfo(int book_number) throws Exception {
		String selectquery = "select * from bookinfo where book_number=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BookInfoBean book = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(selectquery);
			pstmt.setInt(1, book_number);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				book = new BookInfoBean();
				book.setBook_number(rs.getInt(1));
				System.out.println("name=>"+rs.getString(2));
				book.setBook_name(rs.getString(2));
				book.setBook_price(rs.getInt(3));
				book.setBook_info(rs.getString(4));
				book.setBook_img(rs.getString(5));
				book.setBook_pub(rs.getString(6));
				book.setBook_author(rs.getString(7));
			}
		} catch (SQLException se) {
			// TODO: handle exception
			se.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return book;
	}
}

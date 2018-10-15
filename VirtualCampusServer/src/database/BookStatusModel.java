package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import common.BookStatusInfo;

/**
 * �鼮������ϢModel
 * ͳһʵ��Model�ӿ�
 * 
 * @author Silence
 *
 */
public class BookStatusModel implements Model{

	/**
	 * ���ݿ�����
	 */
	private Connection con;
	/**
	 * SQL��ѯ���
	 */
	private String query;
	/**
	 * �鼮������Ϣ
	 */
	private BookStatusInfo info;
	
	public BookStatusModel() {
		this.con = DBConnection.getConnection();
		this.query = "";
		this.info = null;
	}

	/**
	 * ���ݿ���������ʵ��Model�ӿڣ�
	 * 
	 * @param obj ������Ľ��ļ�¼
	 * @return �Ƿ����ɹ�
	 */
	@Override
	public boolean insert(Object obj) {
		info = (BookStatusInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "insert into tbBookStatus values (" + info.getId() + ",'" + info.getName() + "','" + info.getBorrower() 
			+ "'," + info.getBorrowDate() + "," + info.getReturnDate() + ");";
			System.out.println(query);
			
			if (stmt.executeUpdate(query) != 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * ���ݿ��޸Ĳ�����ʵ��Model�ӿڣ�
	 * ��ID�ͽ���ʱ�乲ͬ��Ϊkey���޸ĸü�¼�Ļ���ʱ��
	 * 
	 * @param obj ���޸ĵĽ��ļ�¼
	 * @return �Ƿ��޸ĳɹ�
	 */
	@Override
	public boolean modify(Object obj) {
		info = (BookStatusInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "update tbBookStatus set returnDate=" + info.getReturnDate() + " where ID=" + info.getId() + " and borrowDate="
			+ info.getBorrowDate() + ";";
			System.out.println(query);
			
			if (stmt.executeUpdate(query) != 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * ���ݿ�ɾ��������ʵ��Model�ӿڣ�
	 * ��ID��Ϊkey
	 * 
	 * @param obj ��ɾ���Ľ��ļ�¼
	 * @return �Ƿ�ɾ���ɹ�
	 */
	@Override
	public boolean delete(Object obj) {
		info = (BookStatusInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "delete from tbBookStatus where ID=" + info.getId() + ";";
			System.out.println(query);
			
			if (stmt.executeUpdate(query) != 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * ���ݿ��ѯ������ʵ��Model�ӿڣ�
	 * �������벻ͬ���Խ����˻�������Ϊkey
	 * 
	 * @param obj ��ǰ�û���Ϣ
	 * @return ����ѯ�����н��ļ�¼
	 */
	@Override
	public Object search(Object obj) {
		info = (BookStatusInfo)obj;
		
		if (!info.getBorrower().equals(""))
			query = "select * from tbBookStatus where borrower='" + info.getBorrower() + "';";
		else if (!info.getName().equals(""))
			query = "select * from tbBookStatus where bookName='" + info.getName() + "';";
		
		try {
			Statement stmt = con.createStatement();
			System.out.println(query);
			
			ResultSet rs = stmt.executeQuery(query);
			
			if (rs != null)
				return rs;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}

package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import common.BankInfo;

/**
 * ����ģ��Model
 * ͳһʵ��Model�ӿ�
 * 
 * @author Silence
 *
 */
public class BankModel implements Model{
	/**
	 * ���ݿ�����
	 */
	private Connection con;
	/**
	 * SQL��ѯ���
	 */
	private String query;
	/**
	 * �����˻���Ϣ
	 */
	private BankInfo info;
	
	public BankModel() {
		this.con = DBConnection.getConnection();
		this.query = "";
		this.info = null;
	}	

	/**
	 * ���ݿ���������ʵ��Model�ӿڣ�
	 * 
	 * @param obj ������ļ�¼
	 * @return �Ƿ����ɹ�
	 */
	@Override
	public boolean insert(Object obj) {
		info = (BankInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "insert into tbBank values ('" + info.getId() + "'," + info.getBalance() + ",'" + info.getPwd() + "','" + info.getTransferTo() + "',"
			+ info.getTransferAmount() + "," + info.getTransferDate() + ");";
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
	 * ��userIDΪkey
	 * 
	 * @param obj ���޸ĵļ�¼
	 * @return �Ƿ��޸ĳɹ�
	 */
	@Override
	public boolean modify(Object obj) {
		info = (BankInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "update tbBank set balance=" + info.getBalance() + ",pwd='" + info.getPwd() + ",transferTo=" + info.getTransferTo()
			+ "',transferAmount=" + info.getTransferAmount() + ",transferDate=" + info.getTransferDate() + " where userID='" + info.getId() + "';";
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
	 * ��userIDΪkey
	 * 
	 * @param obj ��ɾ���ļ�¼
	 * @return �Ƿ�ɾ���ɹ�
	 */
	@Override
	public boolean delete(Object obj) {
		info = (BankInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "delete from tbBank where userID='" + info.getId() + "';";
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
	 * �������룬��userIDΪkey����userID�����빲ͬ��Ϊkey
	 * 
	 * @param obj ��ǰ�û���Ϣ
	 * @return ��ǰ�û������м�¼
	 */
	@Override
	public Object search(Object obj) {
		info = (BankInfo)obj;
		
		if (!info.getId().equals("")) {
			query = "select * from tbBank where userID='" + info.getId() + "' order by transferDate;";
			
			if (!info.getPwd().equals("")) {
				query = "select * from tbBank where userID='" + info.getId() + "' and pwd='" + info.getPwd() + "' order by transferDate;";
			}
		}
		
		try {
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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

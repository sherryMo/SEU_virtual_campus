package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import common.UserInfo;
import database.DBConnection;

/**
 * ��¼ģ��Model
 * ͳһʵ��Model�ӿ�
 * 
 * @author Silence
 *
 */
public class LoginModel implements Model{
	
	/**
	 * ���ݿ�����
	 */
	private Connection con;
	/**
	 * SQL��ѯ���
	 */
	private String query;
	/**
	 * �û��˻���Ϣ
	 */
	private UserInfo info;
	
	public LoginModel() {
		this.info = null;
		this.con = DBConnection.getConnection();
		this.query = "";
	}
	
	/**
	 * ���ݿ���������ʵ��Model�ӿڣ�
	 * 
	 * @param obj ��������û�
	 * @return �Ƿ����ɹ�
	 */
	@Override
	public boolean insert(Object obj) {
		info = (UserInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "insert into tbUser values ('" + info.getStuId() + "','" + info.getPwd() + "','" + info.getType() + "','" 
			+ info.getName() + "');";
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
	 * ��u_IDΪkey
	 * 
	 * @param obj ���޸ĵ��û�
	 * @return �Ƿ��޸ĳɹ�
	 */
	@Override
	public boolean modify(Object obj) {
		info = (UserInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "update tbUser set u_Pwd='" + info.getPwd() + "',u_Type='" + info.getType() + "',u_Name=" + info.getName() 
			+ "' where u_ID='" + info.getStuId() + "';";
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
	 * ��u_IDΪkey
	 * 
	 * @param obj ��ɾ�����û�
	 * @return �Ƿ�ɾ���ɹ�
	 */
	@Override
	public boolean delete(Object obj) {
		info = (UserInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "delete from tbUser where u_ID='" + info.getStuId() + "';";
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
	 * ��u_IDΪkey
	 * 
	 * @param obj ����ѯ���û�
	 * @return ����ѯ�û���ϸ��Ϣ
	 */
	@Override
	public Object search(Object obj) {
		info = (UserInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "select * from tbUser where u_ID='" + info.getStuId() + "';";
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

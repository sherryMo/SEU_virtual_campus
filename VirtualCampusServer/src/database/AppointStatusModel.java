package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import common.AppointStatusInfo;

/**
 * ԤԼ��Ŀ״̬Model
 * ͳһʵ��Model�ӿ�
 * 
 * @author Silence
 *
 */
public class AppointStatusModel implements Model{

	/**
	 * ���ݿ�����
	 */
	private Connection con;
	/**
	 * SQL��ѯ���
	 */
	private String query;
	/**
	 * ����ԤԼ��Ŀ��Ϣ
	 */
	private AppointStatusInfo info;
	
	public AppointStatusModel() {
		this.con = DBConnection.getConnection();
		this.query = "";
		this.info = null;
	}

	/**
	 * ���ݿ���������ʵ��Model�ӿڣ�
	 * 
	 * @param obj �������ԤԼ��¼
	 * @return �Ƿ����ɹ�
	 */
	@Override
	public boolean insert(Object obj) {
		info = (AppointStatusInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "insert into tbAppointStatus values ('" + info.getUserID() + "','" + info.getItem() + "'," + info.getAppointDate()
			+ "," + info.getAppointTime() + "," + info.getTimestamp() + ");";
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
	 * @param obj ���޸ĵ�ԤԼ��¼
	 * @return �Ƿ��޸ĳɹ�
	 */
	@Override
	public boolean modify(Object obj) {
		info = (AppointStatusInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "update tbAppointStatus set item='" + info.getItem() + "',appointDate=" + info.getAppointDate() + ",appointTime='" 
			+ info.getAppointTime() + ",timestamp='" + info.getTimestamp() + " where userID='" + info.getUserID() + "';";
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
	 * @param obj ��ɾ����ԤԼ��¼
	 * @return �Ƿ�ɾ���ɹ�
	 */
	@Override
	public boolean delete(Object obj) {
		info = (AppointStatusInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "delete from tbAppointStatus where userID='" + info.getUserID() + "';";
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
	 * ���������ж���userID��item��Ϊkey
	 * 
	 * @param obj ����ѯ��ԤԼ��¼
	 * @return ��ѯ�����м�¼
	 */
	@Override
	public Object search(Object obj) {
		info = (AppointStatusInfo)obj;
		
		if (info.getUserID() != null)
			query = "select * from tbAppointStatus where userID='" + info.getUserID() + "';";
		else if (info.getItem() != null)
			query = "select * from tbAppointStatus where item='" + info.getItem() + "';";
		
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

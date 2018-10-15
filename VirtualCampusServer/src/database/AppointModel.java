package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import common.AppointInfo;

/**
 * ����ԤԼ��ĿModel
 * ͳһʵ��Model�ӿ�
 * 
 * @author Silence
 *
 */
public class AppointModel implements Model{

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
	private AppointInfo info;
	
	public AppointModel() {
		this.con = DBConnection.getConnection();
		this.query = "";
		this.info = null;
	}

	/**
	 * ���ݿ���������ʵ��Model�ӿڣ�
	 * 
	 * @param obj ���������Ŀ
	 * @return �Ƿ����ɹ�
	 */
	@Override
	public boolean insert(Object obj) {
		info = (AppointInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "insert into tbAppoint values ('" + info.getItem() + "','" + info.getItemRemainStr() + "');";
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
	 * ��itemΪkey�޸�itemRemain
	 * 
	 * @param obj ���޸ĵ���Ŀ
	 * @return �Ƿ��޸ĳɹ�
	 */
	@Override
	public boolean modify(Object obj) {
		info = (AppointInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "update tbAppoint set itemRemain='" + info.getItemRemainStr() + "' where item='" + info.getItem() + "';";
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
	 * ��itemΪkey
	 * 
	 * @param obj ��ɾ������Ŀ
	 * @return �Ƿ�ɾ���ɹ�
	 */
	@Override
	public boolean delete(Object obj) {
		info = (AppointInfo)obj;
		
		try {
			Statement stmt = con.createStatement();

			
			query = "delete from tbAppoint where item='" + info.getItem() + "';";
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
	 * �������벻ͬ����itemΪkey�򷵻�������Ŀ��Ϣ
	 * 
	 * @param obj �����ҵ���Ŀ����
	 * @return ��Ŀ������Ϣ
	 */
	@Override
	public Object search(Object obj) {
		info = (AppointInfo)obj;
		if (info == null)
			query = "select * from tbAppoint;";
		else
			query = "select * from tbAppoint where item='" + info.getItem() + "';";
			
		
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

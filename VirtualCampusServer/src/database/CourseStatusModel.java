package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import common.CourseStatusInfo;

/**
 * �γ�ѡ���¼Model
 * ͳһʵ��Model�ӿ�
 * 
 * @author Silence
 *
 */
public class CourseStatusModel implements Model{
	
	/**
	 * ���ݿ�ӿ�
	 */
	private Connection con;
	/**
	 * SQL��ѯ���
	 */
	private String query;
	/**
	 * �γ�ѡ���¼��Ϣ
	 */
	private CourseStatusInfo info;
	
	public CourseStatusModel() {
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
		info = (CourseStatusInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "insert into tbCourseStatus values ('" + info.getId() + "','" + info.getSelector() + "');";
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
	 * ��IDΪkey
	 * 
	 * @param obj ���޸ĵļ�¼
	 * @return �Ƿ��޸ĳɹ�
	 */
	@Override
	public boolean modify(Object obj) {
		info = (CourseStatusInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "update tbCourseStatus set selector='" + info.getSelector() + "' where ID='" + info.getId() + "';";
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
	 * ��IDΪkey
	 * 
	 * @param obj ��ɾ���ļ�¼
	 * @return �Ƿ�ɾ���ɹ�
	 */
	@Override
	public boolean delete(Object obj) {
		info = (CourseStatusInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "delete from tbCourseStatus where ID='" + info.getId() + "';";
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
	 * �������벻ͬ����ID��ѡ��ѧ��ѧ��Ϊkey
	 * 
	 * @param obj ����ѯ����Ϣ
	 * @return ����ѯѡ�μ�¼��ϸ��Ϣ
	 */
	@Override
	public Object search(Object obj) {
		info = (CourseStatusInfo)obj;
		
		if (!info.getId().equals(""))
			query = "select * from tbCourseStatus where ID='" + info.getId() + "';";
		else if (!info.getSelector().equals(""))
			query = "select * from tbCourseStatus where selector='" + info.getSelector() + "';";
		
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

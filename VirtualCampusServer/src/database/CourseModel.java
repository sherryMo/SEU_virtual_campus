package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import common.CourseInfo;

/**
 * �γ���ϢModel
 * ͳһʵ��Model�ӿ�
 * 
 * @author Silence
 *
 */
public class CourseModel implements Model{

	/**
	 * ���ݿ�����
	 */
	private Connection con;
	/**
	 * SQL��ѯ���
	 */
	private String query;
	/**
	 * �γ���Ϣ
	 */
	private CourseInfo info;
	
	public CourseModel() {
		this.con = DBConnection.getConnection();
		this.query = "";
		this.info = null;
	}

	/**
	 * ���ݿ���������ʵ��Model�ӿڣ�
	 * 
	 * @param obj ������Ŀγ�
	 * @return �Ƿ����ɹ�
	 */
	@Override
	public boolean insert(Object obj) {
		info = (CourseInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "insert into tbCourse values ('" + info.getId() + "','" + info.getName() + "','" + info.getTeacher() 
			+ "','" + info.getPlace() + "','" + info.getTime() + "'," + info.getCredit() + ");";
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
	 * @param obj ���޸ĵĿγ�
	 * @return �Ƿ��޸ĳɹ�
	 */
	@Override
	public boolean modify(Object obj) {
		info = (CourseInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "update tbCourse set courseName='" + info.getName() + "',teacher='" + info.getTeacher() + "',place='" 
			+ info.getPlace() + "',time='" + info.getTime() + "',credit=" + info.getCredit() + " where ID='" + info.getId() + "';";
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
	 * @param obj ��ɾ���Ŀγ�
	 * @return �Ƿ�ɾ���ɹ�
	 */
	@Override
	public boolean delete(Object obj) {
		info = (CourseInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "delete from tbCourse where ID='" + info.getId() + "';";
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
	 * �������벻ͬ����ID����ʦ����Ϊkey�򷵻����пγ���Ϣ
	 * 
	 * @param obj ����ѯ�Ŀγ�
	 * @return ����ѯ�γ���ϸ��Ϣ
	 */
	@Override
	public Object search(Object obj) {
		info = (CourseInfo)obj;

		if (info == null)
			query = "select * from tbCourse;";
		else if (!info.getId().equals(""))
			query = "select * from tbCourse where ID='" + info.getId() + "';";
		else if (!info.getTeacher().equals(""))
			query = "select * from tbCourse where teacher='" + info.getTeacher() + "';";
		
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

package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import common.StudentRollInfo;

/**
 * ѧ��ѧ����Ϣģ��Model
 * ͳһʵ��Model�ӿ�
 * 
 * @author Silence
 *
 */
public class StudentRollModel implements Model{
	
	/**
	 * ���ݿ�����
	 */
	private Connection con;
	/**
	 * SQL��ѯ���
	 */
	private String query;
	/**
	 * ѧ��ѧ����Ϣ
	 */
	private StudentRollInfo info;
	
	public StudentRollModel() {
		this.con = DBConnection.getConnection();
		this.query = "";
		this.info = null;
	}	

	/**
	 * ���ݿ���������ʵ��Model�ӿڣ�
	 * 
	 * @param obj �������ѧ����Ϣ
	 * @return �Ƿ����ɹ�
	 */
	@Override
	public boolean insert(Object obj) {
		info = (StudentRollInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "insert into tbStudentRoll values ('" + info.getId() + "','" + info.getName() + "','" + info.getAge()
			+ "','" + info.getGender() + "','" + info.getBirthday() + "','" + info.getBirthPlace() + "','" + info.getEntranceTime()
			+ "','" + info.getPhoto() + "','" + info.getNation() + "','" + info.getDepartment() + "','" + info.getMajor()
			+ "','" + info.getDormitory() + "');";
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
	 * ��ѧ��IDΪkey
	 * 
	 * @param obj ���޸ĵ�ѧ����Ϣ
	 * @return �Ƿ��޸ĳɹ�
	 */
	@Override
	public boolean modify(Object obj) {
		info = (StudentRollInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "update tbStudentRoll set stuName='" + info.getName() + "',age='" + info.getAge()+ "',gender='" + info.getGender() 
			+ "',birthday='" + info.getBirthday() + "',birthPlace='" + info.getBirthPlace()+ "',entranceTime='" + info.getEntranceTime() 
			+ "',photo='" + info.getPhoto() + "',nation='" + info.getNation() + "',department='" + info.getDepartment() 
			+ "',major='" + info.getMajor() + "',dormitory='" + info.getDormitory() + "' where ID='" + info.getId() + "';";
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
	 * ��ѧ��IDΪkey
	 * 
	 * @param obj ���޸ĵ�ѧ����Ϣ
	 * @return �Ƿ��޸ĳɹ�
	 */
	@Override
	public boolean delete(Object obj) {
		info = (StudentRollInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "delete from tbStudentRoll where ID='" + info.getId() + "';";
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
	 * �������벻ͬ����ID��ID������Ϊkey���򷵻�����ѧ����Ϣ
	 * 
	 * @param obj ����ѯ����Ϣ
	 * @return ����ѯ������ѧ����Ϣ
	 */
	@Override
	public Object search(Object obj) {
		info = (StudentRollInfo)obj;
		
		if (info == null)
			query = "select * from tbStudentRoll;";
		else if (!info.getId().equals("")) {
				query = "select * from tbStudentRoll where ID='" + info.getId() + "';";
			
				if (!info.getName().equals("")) {
					query = "select * from tbStudentRoll where stuName='" + info.getName() + "' and ID='" + info.getId() + "';";
				}
		}
		
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

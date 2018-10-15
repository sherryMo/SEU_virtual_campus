package helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import common.StudentRollInfo;
import database.StudentRollModel;

/**
 * ѧ��ѧ����ϢController
 * 
 * @author Silence
 *
 */
public class StudentRoll {
	/**
	 * ѧ��ѧ����ϢModel
	 */
	private StudentRollModel model;
	
	public StudentRoll() {
		this.model = new StudentRollModel();
	}
	
	/**
	 * ����ѧ����Ϣ��Ӧ����
	 * �����ṩ�Ĳ�ͬkey��ѧ�ţ����������ؽ��
	 * 
	 * @param info ���ҵ�key
	 * @return ����ѯѧ����ϸ��Ϣ
	 */
	public StudentRollInfo query(StudentRollInfo info) {
		try {
			ResultSet rs = (ResultSet)model.search(info);
			
			if (rs.next()) {
				return new StudentRollInfo(rs.getString("ID"), rs.getString("stuName"), rs.getString("age"), rs.getString("gender"), 
					rs.getString("birthday"), rs.getString("birthPlace"), rs.getString("entranceTime"), rs.getString("photo"), rs.getString("nation"), 
					rs.getString("department"), rs.getString("major"), rs.getString("dormitory"));
			}
			
			return null;
			
		} catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();

			return null;
		} 
	}
	
	/**
	 * ��ѯ����ѧ����Ϣ��Ӧ����
	 * 
	 * @return ����ѧ����ϸ��Ϣ
	 */
	public StudentRollInfo[] queryAll() {
		try {
			ResultSet rs = (ResultSet)model.search(null);
			Vector<StudentRollInfo> v = new Vector<StudentRollInfo>();
			
			while (rs.next()) {
				StudentRollInfo temp = new StudentRollInfo(rs.getString("ID"), rs.getString("stuName"), rs.getString("age"), rs.getString("gender"), 
						rs.getString("birthday"), rs.getString("birthPlace"), rs.getString("entranceTime"), rs.getString("photo"), rs.getString("nation"), 
						rs.getString("department"), rs.getString("major"), rs.getString("dormitory"));
				v.add(temp);
			}
			
			return (StudentRollInfo[])v.toArray(new StudentRollInfo[rs.getRow()]);
			
		} catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();

			return null;
		} 
	}

	/**
	 * ���ѧ����Ϣ��Ӧ����
	 * 
	 * @param info Ҫ��ӵ�ѧ��
	 * @return �Ƿ���ӳɹ�
	 */
	public boolean addInfo(StudentRollInfo info) {
		return model.insert(info);
	}
	
	/**
	 * �޸�ѧ����Ϣ��Ӧ����
	 * 
	 * @param info Ҫ�޸ĵ�ѧ��
	 * @return �Ƿ��޸ĳɹ�
	 */
	public boolean modifyInfo(StudentRollInfo info) {
		return model.modify(info);
	}
	
	/**
	 * ɾ��ѧ����Ϣ��Ӧ����
	 * 
	 * @param info Ҫɾ����ѧ��
	 * @return �Ƿ�ɾ���ɹ�
	 */
	public boolean deleteInfo(StudentRollInfo info) {
		return model.delete(info);
	}
	
}

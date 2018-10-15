package helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import common.CourseInfo;
import common.CourseStatusInfo;
import database.CourseModel;
import database.CourseStatusModel;

/**
 * �γ�ģ��Controller
 * 
 * @author Silence
 *
 */
public class Course {
	/**
	 * �γ���ϢModel
	 */
	private CourseModel cModel;
	/**
	 * �γ�ѡ��״̬Model
	 */
	private CourseStatusModel csModel;
	
	public Course(){
		cModel = new CourseModel();
		csModel = new CourseStatusModel();
	}
	
	/**
	 * ��ѯȫ���γ���Ӧ����
	 * 
	 * @return ��ǰ���ݿ���ȫ���γ���Ϣ
	 */
	public CourseInfo[] queryCourse() {
		try {
			ResultSet rs = (ResultSet)cModel.search(null);
			Vector<CourseInfo> v = new Vector<CourseInfo>();
			
			while (rs.next()) {	
				CourseInfo temp = new CourseInfo(rs.getString("ID"), rs.getString("courseName"), rs.getString("teacher"), rs.getString("place"), 
						rs.getString("time"), rs.getDouble("credit"));
				v.add(temp);
				
			}
			
			return (CourseInfo[])v.toArray(new CourseInfo[rs.getRow()]);
			
		} catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();

			return null;
		}
	}
	
	/**
	 * ���ӿγ���Ӧ����
	 * 	
	 * @param info Ҫ���ӵĿγ�
	 * @return �Ƿ����ӳɹ�
	 */
	public boolean addCourse(CourseInfo info) {
		return cModel.insert(info);
	}
	
	/**
	 * ɾ���γ���Ӧ����
	 * 	
	 * @param info Ҫɾ���Ŀγ�
	 * @return �Ƿ�ɾ���ɹ�
	 */
	public boolean deleteCourse(CourseInfo info) {
		return cModel.delete(info);
	}
	
	/**
	 * �޸Ŀγ���Ϣ��Ӧ����
	 * 	
	 * @param info Ҫ�޸ĵĿγ�
	 * @return �Ƿ��޸ĳɹ�
	 */
	public boolean modifyCourse(CourseInfo info) {
		return cModel.modify(info);
	}
	
	/**
	 * ѡ��γ���Ӧ����
	 * 	
	 * @param info Ҫѡ��Ŀγ�
	 * @return �Ƿ�ѡ�γɹ�
	 */
	public boolean selectCourse(CourseStatusInfo info) {
		return csModel.insert(info);
	}
	
	/**
	 * ��ѡ�γ���Ӧ����
	 * 	
	 * @param info Ҫ��ѡ�Ŀγ�
	 * @return �Ƿ���ѡ�ɹ�
	 */
	public boolean deselectCourse(CourseStatusInfo info) {
		return csModel.delete(info);
	}
	
	/**
	 * ѧ����ѯ�α���Ӧ����
	 * ��tbCourseStatus���л���û���Ϣ����ѡ�γ�ID�����ÿγ�ID��tbCourse���в�ѯ�γ���ϸ��Ϣ
	 * 
	 * @param info ѧ���û���Ϣ
	 * @return ��ǰ��ѡ��Ŀγ��б�
	 */
	public CourseInfo[] queryCurriculum(CourseStatusInfo info) {
		try {
			ResultSet rs = (ResultSet)csModel.search(info);
			Vector<CourseInfo> v = new Vector<CourseInfo>();
			Vector<CourseInfo> v1 = new Vector<CourseInfo>();
			
			while (rs.next()) {
				CourseInfo temp = new CourseInfo(rs.getString("ID"), "", "", "", "", 0);
				
				v1.add(temp);
			}
			
			for (int i = 0; i < v1.size(); i++) {
				rs = (ResultSet)cModel.search(v1.get(i));
				
				if (rs.next()) {
					CourseInfo temp = new CourseInfo(rs.getString("ID"), rs.getString("courseName"), rs.getString("teacher"), rs.getString("place"), 
						rs.getString("time"), rs.getDouble("credit"));
					v.add(temp);
				}
			}

			CourseInfo arr[] = (CourseInfo[])v.toArray(new CourseInfo[rs.getRow()]);
			
			return arr;
			
		} catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();

			return null;
		}
	}
		
	/**
	 * ��ʦ��ѯѡ��ѧ����Ӧ����
	 * 
	 * @param info Ҫ��ѯ�Ŀγ�
	 * @return ѡ��γ̵�ѧ���б�
	 */
	public CourseStatusInfo[] queryStatus(CourseInfo info) {
		try {
			ResultSet rs = (ResultSet)cModel.search(info);
			Vector<CourseStatusInfo> v = new Vector<CourseStatusInfo>();
			
			if (rs.next()) {
				CourseStatusInfo temp = new CourseStatusInfo(rs.getString("ID"), null);
			
				rs = (ResultSet)csModel.search(temp);
		
				while (rs.next()) {	
					CourseStatusInfo temp1 = new CourseStatusInfo(rs.getString("ID"), rs.getString("selector"));
					v.add(temp1);
				}
			}

			CourseStatusInfo arr[] = (CourseStatusInfo[])v.toArray(new CourseStatusInfo[rs.getRow()]);
			
			return arr;
			
		} catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();

			return null;
		}
	}
}

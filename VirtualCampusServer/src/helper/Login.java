package helper;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.StudentRollInfo;
import common.UserInfo;
import database.LoginModel;
import database.StudentRollModel;

/**
 * ��¼ģ��Controller
 * 
 * @author Silence
 *
 */
public class Login{
	/**
	 * ��¼ģ��Model
	 */
	private LoginModel model;
	/**
	 * ѧ��ѧ��ģ��Model
	 */
	private StudentRollModel srm;
	
	public Login() {
		this.model = new LoginModel();
		this.srm = new StudentRollModel();
	}
	
	/**
	 * ��¼������Ӧ����
	 * ���û���ID�����룬���;������ݿ��м�¼ƥ��ʱ����¼�ɹ�
	 * 
	 * @param info ��ǰ��¼�û���Ϣ
	 * @return �Ƿ��¼�ɹ�
	 */
	public boolean login(UserInfo info) {
		
		try{

			ResultSet rs = (ResultSet)model.search(info);

			if (rs.next())
				return info.getPwd().equals(rs.getString("u_Pwd")) && info.getType().equals(rs.getString("u_Type"));
			
			return false;

		}
		catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();

			return false;
		} 
		
	}
	
	/**
	 * ע�������Ӧ����
	 * ���û������ID��������ѧ����Ϣ�м�¼ƥ��ʱ������ע��
	 * 
	 * @param info ��ǰע���û���Ϣ
	 * @return �Ƿ�ע��ɹ�
	 */
	public boolean register(UserInfo info) {
		StudentRollInfo temp = new StudentRollInfo(info.getStuId(), info.getName(), null, null, null, null, null, null, null, null, null, null);
		
		try {
			ResultSet rs = (ResultSet)srm.search(temp);
			if (rs.next())
				return model.insert(info);
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
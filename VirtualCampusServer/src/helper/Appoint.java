package helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import common.AppointInfo;
import common.AppointStatusInfo;
import database.AppointModel;
import database.AppointStatusModel;

/**
 * ����ԤԼģ��Controller
 * 
 * @author Silence
 *
 */
public class Appoint {
	
	/**
	 * ԤԼ��ĿModel
	 */
	private AppointModel am;
	/**
	 * ԤԼ��¼Model
	 */
	private AppointStatusModel asm;
	
	public Appoint() {
		am = new AppointModel();
		asm = new AppointStatusModel();
	}
	
	/**
	 * ��ѯԤԼ��Ŀ��Ӧ����
	 * ��ͻ��˷���������Ŀ����Ϣ
	 * 
	 * @return ������Ŀ����Ϣ
	 */
	public AppointInfo[] queryAppointItem() {
		try {
			ResultSet rs = (ResultSet)am.search(null);
			Vector<AppointInfo> v = new Vector<AppointInfo>();
			
			while (rs.next()) {
				AppointInfo temp = new AppointInfo(rs.getString("item"), rs.getString("itemRemain"));
				v.add(temp);				
			}
			
			AppointInfo[] arr = (AppointInfo[])v.toArray(new AppointInfo[rs.getRow()]);
			
			return arr;
			
		} catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();

			return null;
		}
	}
	
	/**
	 * �����Ŀ��Ӧ����
	 * 
	 * @param info Ҫ��ӵ���Ŀ
	 * @return �Ƿ���ӳɹ�
	 */
	public boolean addItem(AppointInfo info) {
		return am.insert(info);
	}
	
	/**
	 * ɾ����Ŀ��Ӧ����
	 * 
	 * @param info Ҫɾ������Ŀ
	 * @return �Ƿ�ɾ���ɹ�
	 */
	public boolean deleteItem(AppointInfo info) {
		return am.delete(info);
	}
	
	/**
	 * �޸���Ŀ��Ϣ��Ӧ����
	 * 
	 * @param info Ҫ�޸ĵ���Ŀ
	 * @return �Ƿ��޸ĳɹ�
	 */
	public boolean modifyItem(AppointInfo info) {
		return am.modify(info);
	}
	
	/**
	 * ���ԤԼ��Ӧ����
	 * �����ӳɹ���������Ŀ��Ϣ�н���ʱ���ʣ�������1
	 * 
	 * @param info ҪԤԼ����Ŀ
	 * @return �Ƿ�ԤԼ�ɹ�
	 */
	public boolean addStatus(AppointStatusInfo info) {
		
		AppointInfo temp = new AppointInfo(info.getItem(), "");
		ResultSet rs = (ResultSet)am.search(temp);
		
		try {
			rs.next();
			temp.setItemRemain(rs.getString("itemRemain"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String[][] cur = temp.getItemRemain();
		cur[info.getAppointDate()][info.getAppointTime()] = String.valueOf(Integer.parseInt(cur[info.getAppointDate()][info.getAppointTime()]) - 1);
		
		return asm.insert(info) && am.modify(temp);
	}
	
	/**
	 * ȡ��ԤԼ��Ӧ����
	 * 
	 * @param info Ҫȡ����ԤԼ
	 * @return �Ƿ�ȡ���ɹ�
	 */
	public boolean deleteStatus(AppointStatusInfo info) {
		return asm.delete(info);
	}
	
	/**
	 * �޸�ԤԼ��Ӧ����
	 * 
	 * @param info Ҫ�޸ĵ�ԤԼ
	 * @return �Ƿ��޸ĳɹ�
	 */
	public boolean modifyStatus(AppointStatusInfo info) {
		return asm.modify(info);
	}
	
	/**
	 * ��ѯԤԼ��¼��Ӧ����
	 * 
	 * @param info ��ǰ�û���Ϣ
	 * @return ��ǰ�û�������ԤԼ��¼
	 */
	public AppointStatusInfo[] queryStatus(AppointStatusInfo info) {
		try {
			ResultSet rs = (ResultSet)asm.search(info);
			Vector<AppointStatusInfo> v = new Vector<AppointStatusInfo>();
			
			while (rs.next()) {
				AppointStatusInfo temp = new AppointStatusInfo(rs.getString("userID"), rs.getString("item"), rs.getInt("appointDate")
						, rs.getInt("appointTime"), rs.getInt("timestamp"));
				v.add(temp);				
			}
			
			AppointStatusInfo[] arr = (AppointStatusInfo[])v.toArray(new AppointStatusInfo[rs.getRow()]);
			
			return arr;
			
		} catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();

			return null;
		}
	}

}

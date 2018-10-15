package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import common.OrderInfo;

/**
 * ������ϢModel
 * ͳһʵ��Model�ӿ�
 * 
 * @author Silence
 *
 */
public class OrderModel implements Model{
	
	/**
	 * ���ݿ�����
	 */
	private Connection con;
	/**
	 * SQL��ѯ���
	 */
	private String query;
	/**
	 * ������Ϣ
	 */
	private OrderInfo info;
	
	public OrderModel() {
		this.con = DBConnection.getConnection();
		this.query = "";
		this.info = null;
	}

	/**
	 * ���ݿ���������ʵ��Model�ӿڣ�
	 * 
	 * @param obj ������Ķ���
	 * @return �Ƿ����ɹ�
	 */
	@Override
	public boolean insert(Object obj) {
		info = (OrderInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "insert into tbOrder (productName,buyer,buyNum,buyTime) values ('" + info.getName() + "','" + info.getBuyer() 
			+ "'," + info.getBuyNum() + "," + info.getBuyTime() + ");";
			System.out.println(query);
			
			if (stmt.executeUpdate(query) != 0) {
				query = "update tbGoods set remainNum=remainNum-" + info.getBuyNum() + " where productName='" + info.getName() + "';";
				System.out.println(query);
				if (stmt.executeUpdate(query) != 0)
					return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * ���ݿ��޸Ĳ�����ʵ��Model�ӿڣ�
	 * �����ѧ�ţ�����ʱ��Ϊkey
	 * 
	 * @param obj ���޸ĵĶ���
	 * @return �Ƿ��޸ĳɹ�
	 */
	@Override
	public boolean modify(Object obj) {
		info = (OrderInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "update tbOrder set productName='" + info.getName() + "',buyNum=" + info.getBuyNum() 
			+ " where buyer='" + info.getBuyer() + "' and buyTime=" + info.getBuyTime() + ";";
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
	 * �����ѧ�ţ�����ʱ��Ϊkey
	 * 
	 * @param obj ���޸ĵĶ���
	 * @return �Ƿ��޸ĳɹ�
	 */
	@Override
	public boolean delete(Object obj) {
		info = (OrderInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "delete from tbOrder where buyer='" + info.getBuyer() + "' and buyTime=" + info.getBuyTime() + ";";
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
	 * �������벻ͬ�������ѧ��Ϊkey�򷵻����ж�����Ϣ
	 * 
	 * @param obj ����ѯ����Ϣ
	 * @return ����ѯ�����ж�����Ϣ
	 */
	@Override
	public Object search(Object obj) {
		info = (OrderInfo)obj;
		
		if (info == null)
			query = "select * from tbOrder;";
		else
			query = "select * from tbOrder where buyer='" + info.getBuyer() + "';";
		
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

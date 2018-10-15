package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import common.GoodInfo;

/**
 * ��Ʒ��ϢModel
 * ͳһʵ��Model�ӿ�
 * 
 * @author Silence
 *
 */
public class GoodModel implements Model{
	
	/**
	 * ���ݿ�����
	 */
	private Connection con;
	/**
	 * SQL��ѯ���
	 */
	private String query;
	/**
	 * ��Ʒ��Ϣ
	 */
	private GoodInfo info;
	
	public GoodModel() {
		this.con = DBConnection.getConnection();
		this.query = "";
		this.info = null;
	}

	/**
	 * ���ݿ���������ʵ��Model�ӿڣ�
	 * 
	 * @param obj ���������Ʒ
	 * @return �Ƿ����ɹ�
	 */
	@Override
	public boolean insert(Object obj) {
		info = (GoodInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "insert into tbGoods values (" + info.getId() + ",'" + info.getName() + "'," + info.getRemainNum() 
			+ "," + info.getPrice() + ",'" + info.getSupplier()+ "','" + info.getTag() + "');";
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
		info = (GoodInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "update tbGoods set productName='" + info.getName() + "',remainNum=" + info.getRemainNum() + ",price=" 
			+ info.getPrice()+ ",supplier='" + info.getSupplier() + "',tag='" + info.getTag() + "' where ID=" + info.getId() + ";";
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
		info = (GoodInfo)obj;
		
		try {
			Statement stmt = con.createStatement();
			query = "delete from tbGoods where ID=" + info.getId() + ";";
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
	 * 
	 * @param obj �˴�ֱ�Ӵ���null
	 * @return ����������Ʒ��Ϣ
	 */
	@Override
	public Object search(Object obj) {

		query = "select * from tbGoods;";
		
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

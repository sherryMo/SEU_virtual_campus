package helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import common.GoodInfo;
import common.OrderInfo;
import database.GoodModel;
import database.OrderModel;

/**
 * �̵�ģ��Controller
 * 
 * @author Silence
 *
 */
public class Shop {
	/**
	 * ��Ʒ��ϢModel
	 */
	private GoodModel goodModel;
	/**
	 * ������ϢModel
	 */
	private OrderModel orderModel;
	
	public Shop() {
		this.goodModel = new GoodModel();
		this.orderModel = new OrderModel();
	}
	
	/**
	 * ��ѯ��Ʒ��Ϣ��Ӧ����
	 * 
	 * @return ������Ʒ��Ϣ
	 */
	public GoodInfo[] queryGoods() {
		try {
			ResultSet rs = (ResultSet)goodModel.search(null);
			Vector<GoodInfo> v = new Vector<GoodInfo>();
			
			while (rs.next()) {
				GoodInfo temp =  new GoodInfo(rs.getInt("ID"), rs.getString("productName"), rs.getInt("remainNum"), rs.getDouble("price"), 
						rs.getString("supplier"), rs.getString("tag"));
				v.add(temp);
			}
			
			GoodInfo arr[] = (GoodInfo[])v.toArray(new GoodInfo[rs.getRow()]);
			
			return arr;
			
		} catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();

			return null;
		} 
	}
	
	/**
	 * ��ѯ������Ϣ��ѧ����ʦ����Ӧ����
	 * 
	 * @param info ��ǰ�û���Ϣ
	 * @return ���û����ж�����Ϣ
	 */
	public OrderInfo[] queryOrderStuTea(OrderInfo info) {
		try {
			ResultSet rs = (ResultSet)orderModel.search(info);
			Vector<OrderInfo> v = new Vector<OrderInfo>();
			
			while (rs.next()) {
				OrderInfo temp = new OrderInfo(rs.getInt("ID"), rs.getString("productName"), rs.getString("buyer"), rs.getInt("buyNum"), 
						rs.getLong("buyTime"));
				
				v.add(temp);
			}
			
			OrderInfo arr[] = (OrderInfo[])v.toArray(new OrderInfo[rs.getRow()]);
			
			return arr;
			
		} catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();

			return null;
		} 
	}
	
	/**
	 * ��ѯ������Ϣ������Ա����Ӧ����
	 * 
	 * @return ���ж�����Ϣ
	 */
	public OrderInfo[] queryOrderAdmin() {
		try {
			ResultSet rs = (ResultSet)orderModel.search(null);
			Vector<OrderInfo> v = new Vector<OrderInfo>();
			
			while (rs.next()) {
				OrderInfo temp = new OrderInfo(rs.getInt("ID"), rs.getString("productName"), rs.getString("buyer"), rs.getInt("buyNum"), 
						rs.getLong("buyTime"));
				
				v.add(temp);
			}
			
			OrderInfo arr[] = (OrderInfo[])v.toArray(new OrderInfo[rs.getRow()]);
			
			return arr;
			
		} catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();

			return null;
		} 
	}
	
	/**
	 * �����Ʒ��Ӧ����
	 * 
	 * @param info Ҫ��ӵ���Ʒ
	 * @return �Ƿ���ӳɹ�
	 */
	public boolean addGood(GoodInfo info) {
		return goodModel.insert(info);
	}
	
	/**
	 * ɾ����Ʒ��Ӧ����
	 * 
	 * @param info Ҫɾ������Ʒ
	 * @return �Ƿ�ɾ���ɹ�
	 */
	public boolean deleteGood(GoodInfo info) {
		return goodModel.delete(info);
	}
	
	/**
	 * �޸���Ʒ��Ϣ��Ӧ����
	 * 
	 * @param info Ҫ�޸ĵ���Ʒ
	 * @return �Ƿ��޸ĳɹ�
	 */
	public boolean modifyGood(GoodInfo info) {
		return goodModel.modify(info);
	}
	
	/**
	 * ������Ӧ����
	 * 
	 * @param info ������Ϣ�б�
	 * @return �Ƿ���ɹ�
	 */
	public boolean buy(OrderInfo[] info) {
		boolean flag = true;
		
		for (OrderInfo o: info) {
			flag &= orderModel.insert(o);
		}
		
		return flag;
	}
}

package helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import common.BankInfo;
import database.BankModel;

/**
 * ����ģ��Controller
 * 
 * @author Silence
 *
 */
public class Bank {
	/**
	 * ����ģ��Model
	 */
	private BankModel model;
	
	public Bank() {
		this.model = new BankModel();
	}
	
	/**
	 * ��ѯ�����Ӧ����
	 * ���ص�ǰ�û���������¼�У���ת��ʱ���������һ�����
	 * 
	 * @param info ��ǰ�û���Ϣ
	 * @return �û����
	 */
	public double queryBalance(BankInfo info) {
		
		try{

			ResultSet rs = (ResultSet)model.search(info);
			
			rs.last();//ȡ���һ����¼
			
			return Double.parseDouble(rs.getString("balance"));

		}
		catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();

			return 0;
		} 
		
	}
	
	/**
	 * ת����Ϣ��Ӧ����
	 * ÿ��ת�������д������ת����Ϣ��ת���˺ͱ�ת���˸�һ����
	 * ����ӵļ�¼��ͬʱ�޸����˵������Ϣ
	 * 
	 * @param info ת����Ϣ
	 * @return �Ƿ�ת�˳ɹ�
	 */
	public boolean transfer(BankInfo info) {
		
		info.setBalance(info.getBalance() - info.getTransferAmount());
		
		if (model.insert(info)) {
			BankInfo temp = new BankInfo(info.getTransferTo(), 0, info.getPwd(), info.getId(), -info.getTransferAmount(), info.getTransferDate());
			
			ResultSet rs = (ResultSet)model.search(temp);
			try {
				if (rs.last())
					temp.setBalance(rs.getDouble("balance") - temp.getTransferAmount());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if (model.insert(temp)) {
				return true;
			}
		}
		
		return false;

	}
	
	/**
	 * ��ѯת�˼�¼��Ӧ����
	 * 
	 * @param info ��ǰ�û���Ϣ
	 * @return ��ǰ�û�����ת�˼�¼
	 */
	public BankInfo[] queryTranferRecord(BankInfo info) {
		
		try{

			ResultSet rs = (ResultSet)model.search(info);
			Vector<BankInfo> v = new Vector<BankInfo>();

			while (rs.next()) {
				BankInfo temp = new BankInfo(rs.getString("userID"), rs.getDouble("balance"), rs.getString("pwd"), rs.getString("transferTo"),
						rs.getDouble("transferAmount"), rs.getLong("transferDate"));

				v.add(temp);
			}
			
			BankInfo arr[] = (BankInfo[])v.toArray(new BankInfo[rs.getRow()]);
			
			return arr;

		}
		catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();

			return null;
		} 
		
	}
}

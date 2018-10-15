package common;

import java.io.Serializable;

/**
 * ��Ʒ��Ϣ
 * ����tbGoods��Ľṹ��
 * 
 * @author Silence
 *
 */
public class GoodInfo implements Serializable {

	private static final long serialVersionUID = 8;
	/**
	 * ��ƷID
	 */
	private int id;
	/**
	 * ��Ʒ��
	 */
	private String name;
	/**
	 * ��Ʒʣ������
	 */
	private int remainNum;
	/**
	 * ��Ʒ����
	 */
	private double price;
	/**
	 * ��Ʒ��Ӧ��
	 */
	private String supplier;
	/**
	 * ��Ʒ��ǩ���¡�ʳ��ס���У�
	 */
	private String tag;
	
	public GoodInfo(int id, String name, int remainNum, double price, String supplier,
			String tag) {
		this.id = id;
		this.name = name;
		this.remainNum = remainNum;
		this.price = price;
		this.supplier = supplier;
		this.tag = tag;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRemainNum() {
		return remainNum;
	}
	public void setRemainNum(int remainNum) {
		this.remainNum = remainNum;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
}

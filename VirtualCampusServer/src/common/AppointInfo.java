package common;

import java.io.Serializable;

/**
 * ����ԤԼ��Ŀ��Ϣ
 * ����tbAppoint��Ľṹ��
 * 
 * @author Silence
 *
 */
public class AppointInfo implements Serializable{

	private static final long serialVersionUID = 6;
	/**
	 * ��Ŀ����
	 */
	private String item;
	/**
	 * �ö�ά���鱣�����ʱ���ʣ�ೡ��
	 */
	private String itemRemain[][];
	
	public AppointInfo(String item, String itemRemain) {
		this.item = item;
		setItemRemain(itemRemain);
	}
	
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}

	/**
	 * ���ض�ά������ʽ��ʣ�ೡ�Σ����ͻ��ˣ�
	 * 
	 * @return ��ά������ʽ��ʣ�ೡ��
	 */
	public String[][] getItemRemain(){
		return itemRemain;
	}

	/**
	 * �����ַ�����ʽ��ʣ�ೡ�Σ������ݿ⣩
	 * �ַ�����ʽ����ͬ����֮����";"�ָ���һ�첻ͬʱ��֮����"&"�ָ�
	 * 
	 * @return �ַ�����ʽ��ʣ�ೡ��
	 */
	public String getItemRemainStr() {
		String temp = "";
		
		for (int i = 0; i < itemRemain.length; i++) {  
            for (int j = 0; j < itemRemain[i].length; j++) {                
                temp += itemRemain[i][j] + "&";  
            }  
           	
            temp += ";";
        } 
		
		return temp;
	}
	

	/**
	 * ���ַ�����ʽ���ö�ά������ʽ
	 * 
	 * @param itemRemain �ַ�����ʽʣ�ೡ��
	 */
	public void setItemRemain(String itemRemain) {
		String temp[] = itemRemain.split(";");
		String res[][] = new String [temp.length][];
		
		for (int i = 0; i < temp.length; i++) {
			res[i] = temp[i].split("&");
		}

		this.itemRemain = res;
	}
}

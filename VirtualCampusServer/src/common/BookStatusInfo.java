package common;

import java.io.Serializable;

/**
 * ͼ����鼮������Ϣ
 * ����tbBookStatus��Ľṹ��
 * 
 * @author Silence
 *
 */
public class BookStatusInfo implements Serializable {
	
	private static final long serialVersionUID = 5;
	/**
	 * �鼮ID
	 */
	private int id;
	/**
	 * ����
	 */
	private String name;
	/**
	 * ������ѧ��
	 */
	private String borrower;
	/**
	 * ����ʱ�䣨ʱ�����
	 */
	private long borrowDate;
	/**
	 * ����ʱ�䣨ʱ�����
	 */
	private long returnDate;

	public BookStatusInfo(int id, String name, String borrower, long borrowDate, long returnDate) {
		this.id = id;
		this.name = name;
		this.borrower = borrower;
		this.borrowDate = borrowDate;
		this.returnDate = returnDate;
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
	public String getBorrower() {
		return borrower;
	}
	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}
	public long getBorrowDate() {
		return borrowDate;
	}
	public void setBorrowDate(long borrowDate) {
		this.borrowDate = borrowDate;
	}
	public long getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(long returnDate) {
		this.returnDate = returnDate;
	}

}

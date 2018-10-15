package helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import common.BookInfo;
import common.BookStatusInfo;
import database.BookModel;
import database.BookStatusModel;

/**
 * ͼ���ģ��Controller
 * 
 * @author Silence
 *
 */
public class Library {
	/**
	 * �鼮��ϢModel
	 */
	private BookModel bookModel;
	/**
	 * �鼮����״̬Model
	 */
	private BookStatusModel bsModel;
	
	public Library() {
		this.bookModel = new BookModel();
		this.bsModel = new BookStatusModel();
	}
	
	/**
	 * ��ѯ�鼮��Ӧ����
	 * ��������������ͬ����ȡ��ͬ��ʽ����
	 * 
	 * @param info ����ѯ��key�����������ߣ�etc��
	 * @return ����ѯ�鼮������
	 */
	public BookInfo[] queryBook(BookInfo info) {
		try {
			ResultSet rs = (ResultSet)bookModel.search(info);
			Vector<BookInfo> v = new Vector<BookInfo>();
			
			while (rs.next()) {	
				BookInfo temp = new BookInfo(rs.getInt("ID"), rs.getString("bookName"), rs.getString("ISBN"), rs.getString("author"), rs.getString("pub"), 
						rs.getBoolean("isBorrowed"));
				v.add(temp);
			}
			
			BookInfo arr[] = (BookInfo[])v.toArray(new BookInfo[rs.getRow()]);
			
			return arr;
			
		} catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();

			return null;
		} 
	}
	
	/**
	 * ����鼮��Ӧ����
	 * 
	 * @param info Ҫ��ӵ��鼮
	 * @return �Ƿ���ӳɹ�
	 */
	public boolean addBook(BookInfo info) {
		return bookModel.insert(info);
	}
	
	/**
	 * �޸��鼮��Ϣ��Ӧ����
	 * 
	 * @param info Ҫ�޸ĵ��鼮
	 * @return �Ƿ��޸ĳɹ�
	 */
	public boolean modifyBook(BookInfo info) {
		return bookModel.modify(info);
	}
	
	/**
	 * ɾ���鼮��Ӧ����
	 * 
	 * @param info Ҫɾ�����鼮
	 * @return �Ƿ�ɾ���ɹ�
	 */
	public boolean deleteBook(BookInfo info) {
		return bookModel.delete(info);
	}
	
	/**
	 * ��ѯ���ļ�¼��Ӧ����
	 * 
	 * @param info ��ǰ�û���Ϣ
	 * @return ��ǰ�û����н��ļ�¼
	 */
	public BookStatusInfo[] queryStatus(BookStatusInfo info) {
		try {
			ResultSet rs = (ResultSet)bsModel.search(info);
			Vector<BookStatusInfo> v = new Vector<BookStatusInfo>();
			
			while (rs.next()) {	
				BookStatusInfo temp = new BookStatusInfo(rs.getInt("ID"), rs.getString("bookName"), rs.getString("borrower"), rs.getLong("borrowDate"), 
						rs.getLong("returnDate"));
				v.add(temp);
			}
			
			BookStatusInfo arr[] = (BookStatusInfo[])v.toArray(new BookStatusInfo[rs.getRow()]);
			
			return arr;
			
		} catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();

			return null;
		} 
	}
	
	/**
	 * ������Ӧ����
	 * ͨ��ID��tbBook�в�ѯ���鼮������Ϣ���޸ĸ����isBorrowed����Ϊtrue
	 * ��tbBookStatus���в����µĽ��ļ�¼
	 * 
	 * @param info �û���Ϣ���鼮ID
	 * @return �Ƿ���ĳɹ�
	 */
	public boolean borrowBook(BookStatusInfo info) {
		BookInfo temp = new BookInfo(info.getId(), null, null, null, null, false);
		boolean flag = false;
		ResultSet rs = (ResultSet)bookModel.search(temp);
		
		try {
			if (rs.next()) {
				temp.setName(rs.getString("bookName"));
				temp.setIsbn(rs.getString("ISBN"));
				temp.setAuthor(rs.getString("author"));
				temp.setPub(rs.getString("pub"));
				flag = rs.getBoolean("isBorrowed");
				temp.setBorrowed(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (!flag)
			return bookModel.modify(temp) && bsModel.insert(info);
		else
			return false;
	}
	
	/**
	 * ������Ӧ����
	 * ͨ��ID��tbBook�в�ѯ���鼮������Ϣ���޸ĸ����isBorrowed����Ϊfalse
	 * ��tbBookStatus���޸�ԭ���ļ�¼�����ӻ���ʱ���ֶ�
	 * 
	 * @param info �û���Ϣ���鼮ID
	 * @return �Ƿ���ɹ�
	 */
	public boolean returnBook(BookStatusInfo info) {
		BookInfo temp = new BookInfo(info.getId(), null, null, null, null, false);
		boolean flag = true;
		ResultSet rs = (ResultSet)bookModel.search(temp);
		
		try {
			if (rs.next()) {
				temp.setName(rs.getString("bookName"));
				temp.setIsbn(rs.getString("ISBN"));
				temp.setAuthor(rs.getString("author"));
				temp.setPub(rs.getString("pub"));
				flag = rs.getBoolean("isBorrowed");
				temp.setBorrowed(false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (flag)
			return bookModel.modify(temp) && bsModel.modify(info);
		else
			return false;
	}
}

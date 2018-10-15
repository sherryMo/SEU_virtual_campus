package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * �������ݿ����ӣ�����JDBC����
 * 
 * @author Silence
 *
 */
public class DBConnection {
	/**
	 * ���ݿ�����
	 */
	private static Connection con = null;
	/**
	 * ���ݿ��ļ����·��
	 */
	private static String DBurl = "jdbc:Access:///../db/vCampus.accdb";
	
	/**
	 * ���������ݿ������
	 * ��̬����������model��ֱ�ӵ���
	 * 
	 * @return ���ݿ�����
	 */
	public static Connection getConnection() {
		if (con == null) {
			try{
				Class.forName("com.hxtt.sql.access.AccessDriver");
				System.out.println("Driver loaded");
				
				con = DriverManager.getConnection(DBurl);
				System.out.println("Database connected");
			}
			catch (ClassNotFoundException e){
				System.out.println("Fail to load driver");
			}
			catch (SQLException e){
				System.out.println("Fail to connect database");
			}
		}
		
		return con;
	}
}

package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import common.AppointInfo;
import common.AppointStatusInfo;
import common.BankInfo;
import common.BookInfo;
import common.BookStatusInfo;
import common.CourseInfo;
import common.CourseStatusInfo;
import common.GoodInfo;
import common.MsgType;
import common.OrderInfo;
import common.StudentRollInfo;
import common.UserInfo;
import helper.Bank;
import helper.Login;
import helper.Shop;
import helper.StudentRoll;
import helper.Library;
import helper.Course;
import helper.Appoint;
import view.ServerFrameView_MY;

/**
 * �ͻ����߳�
 * 
 * @author Silence
 *
 */
public class ClientThread extends Thread 
	implements MsgType{
	
	/**
	 * �ͻ��˵�ǰ���ӵķ������߳�
	 */
	private ServerThread currentServer;
	/**
	 * �ͻ���Socket
	 */
	private Socket client;
	/**
	 * Socket����������
	 */
	private ObjectInputStream ois;
	/**
	 * Socket���������
	 */
	private ObjectOutputStream oos;
	/**
	 * ��ǰ��¼�û�
	 */
	public String curUser; 
	
	public ClientThread(Socket s, ServerThread st) {
		client = s;
		currentServer = st;
		curUser = "";
		try {
			//���������������������ͻ����෴��
			ois = new ObjectInputStream(client.getInputStream());
			oos = new ObjectOutputStream(client.getOutputStream());	
			
			ServerFrameView_MY.setTextArea("�ͻ���������\n�ͻ���IP��" + client.getInetAddress().getHostAddress() + "\n");
			System.out.println("Client connected");
			
		} catch (IOException e) {
			System.out.println("Cannot get IO stream");
			e.printStackTrace();
		}
	}
	
	public void run() {
		int cmd = 0;//�ӿͻ��˶�������Ϣ
		
		while (true) {
			//��ȡ��Ϣ
			try {
				cmd = ois.readInt();
				System.out.println(cmd);
			} catch (IOException e) {
				//������ָ�˵���ѵǳ�
				return;
			}
			
			//�ж���Ϣ������һ���ͣ����ö�Ӧģ�麯�������Ӧ����
			switch (cmd / 100) {
			//����cmdû�б���ȷ��ʼ��
			case 0:
				System.out.println("Abnormal command");
				return;
				
			//��¼ģ��
			case 1:
				Login(cmd);
				break;
			
			//����ģ��
			case 2:
				Bank(cmd);				
				break;
				
			//ѧ������ģ��
			case 3:
				StudentRoll(cmd);
				break;
				
			//ͼ���ģ��
			case 4:
				Library(cmd);
				break;
				
			//�̵�ģ��
			case 5:
				Shop(cmd);				
				break;
				
			//�γ�ѡ��ģ��
			case 6:
				Course(cmd);
				break;
				
			//����ԤԼģ��
			case 7:
				Appoint(cmd);
				break;
					
			}		
		}
	}
	
	public void close() {
		if (client != null) {
			try {
				oos.close();
				ois.close();
				ServerFrameView_MY.setTextArea("�ͻ����ѶϿ�\n�ͻ���IP��" + client.getInetAddress().getHostAddress() + "\n");
				
				client.close();

				currentServer.closeClientConnection(this);//�ڷ������߳��йرոÿͻ���
				ServerFrameView_MY.setTextNumber(currentServer.getSize());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * ��ģ�鹦�ܺ���
	 * 
	 * ����������ͻ��˵����ݽ����������ģʽ��
	 * 1. �������ӿͻ��˶�ȡ��Ϣ
	 * 2. �������ӿͻ��˶�ȡ�����������ѡ��
	 * 3. ��������ͻ���д������״̬
	 * 4. ��������ͻ���д��������������ѡ�� 
	 */
	
	

	/**
	 * ��¼ģ��
	 * 
	 * @param cmd ���ܵ���Ϣ
	 */
	private void Login(int cmd) {
		UserInfo info = null;
		Login lg = new Login();
		
		try {
			//�ǳ���������Ҫ�ӿͻ��˶�ȡ��Ϣ
			if (cmd != LOGOUT)
				info = (UserInfo)ois.readObject();
		} catch (IOException e) {
			System.out.println("Cannot get message from client");
			e.printStackTrace();
			
			return;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//���ݲ�ͬ��Ϣ�����в�ͬ����
		switch (cmd) {
		//��¼
		case LOGIN:
			try {
				if (!currentServer.searchClientConnection(info.getStuId()) && lg.login(info)) {
					oos.writeInt(LOGIN_SUCCESS);
					
					currentServer.addClientConnection(this);
					curUser = info.getStuId();
					
					ServerFrameView_MY.setTextNumber(currentServer.getSize());
					System.out.println("Number of connected client: " + currentServer.getSize());
					ServerFrameView_MY.setTextArea("�û�" + curUser + "�ѵ�¼\n");
				}
				else {
					oos.writeInt(LOGIN_FAIL);
				}
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
				
			break;
			
		//ע��
		case REGISTER:
			try {
				int wb = (lg.register(info)) ? REGISTER_SUCCESS : REGISTER_FAIL;
				oos.writeInt(wb);
				
				oos.flush();
				
				//ע���ֱ�ӵ�¼
				if (wb == REGISTER_SUCCESS) {
					currentServer.addClientConnection(this);
					curUser = info.getStuId();
					
					ServerFrameView_MY.setTextNumber(currentServer.getSize());
					System.out.println("Number of connected client: " + currentServer.getSize());
					ServerFrameView_MY.setTextArea("�û�" + curUser + "�ѵ�¼\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			break;
			
		//�ǳ�	
		case LOGOUT:
			try {
				if (currentServer.searchClientConnection(curUser)) {
					ServerFrameView_MY.setTextArea("�û�" + curUser + "�ѵǳ�\n");
					oos.writeInt(LOGOUT_SUCCESS);
					this.close();
				}
				else {
					oos.writeInt(LOGOUT_FAIL);
				}
				
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
	}
	
	/**
	 * ����ģ��
	 * 
	 * @param cmd ���ܵ���Ϣ
	 */
	private void Bank(int cmd) {

		BankInfo bankInfo = null;
		Bank bk = new Bank();
		
		try {
			bankInfo = (BankInfo)ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//���ݲ�ͬ��Ϣ�����в�ͬ����
		switch (cmd) {
		//����ѯ
		case BANK_BALANCE_QUERY:
			try {
				double result = bk.queryBalance(bankInfo);
				if (result != 0) {
					oos.writeInt(BANK_BALANCE_QUERY_SUCCESS);
					oos.writeDouble(result);	
				}
				else {
					oos.writeInt(BANK_BALANCE_QUERY_FAIL);
				}
				
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			break;
			
		//ת��
		case BANK_TRANSFER:
			try {
				int wb = (bk.transfer(bankInfo)) ? BANK_TRANSFER_SUCCESS : BANK_TRANSFER_FAIL;
				oos.writeInt(wb);
				
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			break;
		
		//ת�˼�¼��ѯ
		case BANK_TRANSFER_RECORD_QUERY:
			try {
				BankInfo results[] = bk.queryTranferRecord(bankInfo);
				if (results != null) {
					oos.writeInt(BANK_TRANSFER_RECORD_QUERY_SUCCESS);
					oos.writeObject(results);
				}
				else {
					oos.writeInt(BANK_TRANSFER_RECORD_QUERY_FAIL);
				}
				
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			break;
				
		}
	}
	
	/**
	 * ѧ����Ϣģ��
	 * 
	 * @param cmd ���ܵ���Ϣ
	 */
	private void StudentRoll(int cmd) {

		StudentRollInfo stuInfo = null;
		StudentRoll sr = new StudentRoll();
		
		try {
			if (cmd != STUDENTROLL_INFO_QUERY_ADMIN)
				stuInfo = (StudentRollInfo)ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//���ݲ�ͬ��Ϣ�����в�ͬ����
		switch (cmd) {
		//��ѯѧ��ѧ����Ϣ
		case STUDENTROLL_INFO_QUERY:
			try {
				StudentRollInfo result = sr.query(stuInfo);
				if (result != null) {
					oos.writeInt(STUDENTROLL_INFO_QUERY_SUCCESS);
					oos.writeObject(result);	
				}
				else {
					oos.writeInt(STUDENTROLL_INFO_QUERY_FAIL);
				}
				
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			break;
			
		//���ѧ����Ϣ
		case STUDENTROLL_ADD:
			try {
				int wb = (sr.addInfo(stuInfo)) ? STUDENTROLL_ADD_SUCCESS : STUDENTROLL_ADD_FAIL;
				oos.writeInt(wb);
				
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			break;
			
		//ɾ��ѧ����Ϣ
		case STUDENTROLL_DELETE:
			try {
				int wb = (sr.deleteInfo(stuInfo)) ? STUDENTROLL_DELETE_SUCCESS : STUDENTROLL_DELETE_FAIL;
				oos.writeInt(wb);
				
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			break;
			
		//�޸�ѧ����Ϣ
		case STUDENTROLL_MODIFY:
			try {
				int wb = (sr.modifyInfo(stuInfo)) ? STUDENTROLL_MODIFY_SUCCESS : STUDENTROLL_MODIFY_FAIL;
				oos.writeInt(wb);
				
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			break;
			
		//ѧ����Ϣ��ѯ������Ա��ѯ��
		case STUDENTROLL_INFO_QUERY_ADMIN:
			try {
				StudentRollInfo[] result = sr.queryAll();
				if (result != null) {
					oos.writeInt(STUDENTROLL_INFO_QUERY_ADMIN_SUCCESS);
					oos.writeObject(result);	
				}
				else {
					oos.writeInt(STUDENTROLL_INFO_QUERY_ADMIN_FAIL);
				}
				
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ͼ���ģ��
	 * 
	 * @param cmd ���ܵ���Ϣ
	 */
	private void Library(int cmd) {
		
		BookInfo bookInfo = null;
		BookStatusInfo bsInfo = null;
		Library lb = new Library();
		
		//�鼮��Ϣ��ѯ��40������tbBook����в���
		if (cmd / 10 == 40) {
			try {
				bookInfo = (BookInfo)ois.readObject();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			//���ݲ�ͬ��Ϣ�����в�ͬ����
			switch(cmd) {
			//�鼮��Ϣ��ѯ
			case LIBRARY_BOOK_QUERY:
				try {
					BookInfo result[] = lb.queryBook(bookInfo);
					if (result != null) {
						oos.writeInt(LIBRARY_BOOK_QUERY_SUCCESS);
						oos.writeObject(result);	
					}
					else {
						oos.writeInt(LIBRARY_BOOK_QUERY_FAIL);
					}
					
					oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
				
			//����鼮
			case LIBRARY_BOOK_ADD:
				try {
					int writeBack = (lb.addBook(bookInfo)) ? LIBRARY_BOOK_ADD_SUCCESS : LIBRARY_BOOK_ADD_FAIL;
					oos.writeInt(writeBack);
					oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
				
			//ɾ���鼮
			case LIBRARY_BOOK_DELETE:
				try {
					int writeBack = (lb.deleteBook(bookInfo)) ? LIBRARY_BOOK_DELETE_SUCCESS : LIBRARY_BOOK_DELETE_FAIL;
					oos.writeInt(writeBack);
					oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
				
			//�޸��鼮��Ϣ
			case LIBRARY_BOOK_MODIFY:
				try {
					int writeBack = (lb.modifyBook(bookInfo)) ? LIBRARY_BOOK_MODIFY_SUCCESS : LIBRARY_BOOK_MODIFY_FAIL;
					oos.writeInt(writeBack);
					oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
			
			}
		}
		//�鼮����״̬��ѯ��41������tbBookStatus����в���
		else {
			try {
				bsInfo = (BookStatusInfo)ois.readObject();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//���ݲ�ͬ��Ϣ�����в�ͬ����
			switch(cmd) {
			//����
			case LIBRARY_STATUS_BORROW:
				try {
					int writeBack = (lb.borrowBook(bsInfo)) ? LIBRARY_STATUS_BORROW_SUCCESS : LIBRARY_STATUS_BORROW_FAIL;
					oos.writeInt(writeBack);
					oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
				
			//����
			case LIBRARY_STATUS_RETURN:
				try {
					int writeBack = (lb.returnBook(bsInfo)) ? LIBRARY_STATUS_RETURN_SUCCESS : LIBRARY_STATUS_RETURN_FAIL;
					oos.writeInt(writeBack);
					oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
				
			//���ļ�¼��ѯ
			case LIBRARY_STATUS_QUERY:
				try {
					BookStatusInfo result[] = lb.queryStatus(bsInfo);
					if (result != null) {
						oos.writeInt(LIBRARY_STATUS_QUERY_SUCCESS);
						oos.writeObject(result);	
					}
					else {
						oos.writeInt(LIBRARY_STATUS_QUERY_FAIL);
					}
					
					oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
				
			}
		}
		
	}
	
	/**
	 * �̵�ģ��
	 * 
	 * @param cmd ���ܵ���Ϣ
	 */
	private void Shop(int cmd) {

		GoodInfo goodInfo = null;
		Shop sp = new Shop();
		
		//��Ʒ��Ϣ��ѯ��50������tbGood����в���
		if (cmd / 10 == 50) {
			try {
				if (cmd != SHOP_GOODS_QUERY)
					goodInfo = (GoodInfo)ois.readObject();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//���ݲ�ͬ��Ϣ�����в�ͬ����
			switch(cmd) {
			//��Ʒ��Ϣ��ѯ
			case SHOP_GOODS_QUERY:
				try {
					GoodInfo[] result = sp.queryGoods();
					if (result != null) {
						oos.writeInt(SHOP_GOODS_QUERY_SUCCESS);
						oos.writeObject(result);	
					}
					else {
						oos.writeInt(SHOP_GOODS_QUERY_FAIL);
					}
					
					oos.flush();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
				break;
				
			//�����Ʒ
			case SHOP_GOODS_ADD:
				try {
					int wb = (sp.addGood(goodInfo)) ? SHOP_GOODS_ADD_SUCCESS : SHOP_GOODS_ADD_FAIL;
					oos.writeInt(wb);
					
					oos.flush();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
			
			//ɾ����Ʒ
			case SHOP_GOODS_DELETE:
				try {
					int wb = (sp.deleteGood(goodInfo)) ? SHOP_GOODS_DELETE_SUCCESS : SHOP_GOODS_DELETE_FAIL;
					oos.writeInt(wb);
					
					oos.flush();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
				
			//�޸���Ʒ��Ϣ
			case SHOP_GOODS_MODIFY:
				try {
					int wb = (sp.modifyGood(goodInfo)) ? SHOP_GOODS_MODIFY_SUCCESS : SHOP_GOODS_MODIFY_FAIL;
					oos.writeInt(wb);
					
					oos.flush();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
			}
		}
		//��Ʒ������ѯ��51������tbOrder����в���
		else {
			switch (cmd) {
			//������ѯ
			case SHOP_ORDER_QUERY_ADMIN:
				try {						
					OrderInfo result[] = sp.queryOrderAdmin();
					
					if (result != null) {
						oos.writeInt(SHOP_ORDER_QUERY_ADMIN_SUCCESS);
						oos.writeObject(result);
					}
					else {
						oos.writeInt(SHOP_ORDER_QUERY_ADMIN_FAIL);
					}
					
					oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
				
			//����
			case SHOP_ORDER_BUY:
				try {
					OrderInfo order[] = (OrderInfo[])ois.readObject();
					int wb = (sp.buy(order)) ? SHOP_ORDER_BUY_SUCCESS : SHOP_ORDER_BUY_FAIL;
					oos.writeInt(wb);
					
					oos.flush();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
						
				break;
				
			//������¼��ѯ
			case SHOP_ORDER_QUERY_STUTEA:
				try {
					OrderInfo orderInfo = (OrderInfo)ois.readObject();
					
					OrderInfo result[] = sp.queryOrderStuTea(orderInfo);
					if (result != null) {
						oos.writeInt(SHOP_ORDER_QUERY_STUTEA_SUCCESS);
						oos.writeObject(result);	
					}
					else {
						oos.writeInt(SHOP_ORDER_QUERY_STUTEA_FAIL);
					}
					
					oos.flush();
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
			}
		}

	}
	
	/**
	 * �γ�ѡ��ģ��
	 * 
	 * @param cmd ���ܵ���Ϣ
	 */
	private void Course(int cmd) {
		CourseInfo courseInfo = null;
		CourseStatusInfo csInfo = null;
		Course cs = new Course();
		
		//�γ���Ϣ��ѯ��60������tbCourse����в���
		if (cmd / 10 == 60) {
			try {
				if (cmd != COURSE_QUERY)
					courseInfo = (CourseInfo)ois.readObject();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//���ݲ�ͬ��Ϣ�����в�ͬ����
			switch(cmd) {
			//�γ̲�ѯ
			case COURSE_QUERY:
				try {
					CourseInfo result[] = cs.queryCourse();
					
					if (result != null) {
						oos.writeInt(COURSE_QUERY_SUCCESS);
						oos.writeObject(result);	
					}
					else {
						oos.writeInt(COURSE_QUERY_FAIL);
					}
					
					oos.flush();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
				break;
				
			//��ӿγ�
			case COURSE_ADD:
				try {
					int wb = (cs.addCourse(courseInfo)) ? COURSE_ADD_SUCCESS : COURSE_ADD_FAIL;
					oos.writeInt(wb);
					
					oos.flush();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
				
			//ɾ���γ�
			case COURSE_DELETE:
				try {
					int wb = (cs.deleteCourse(courseInfo)) ? COURSE_DELETE_SUCCESS : COURSE_DELETE_FAIL;
					oos.writeInt(wb);
					
					oos.flush();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
				
			//�޸Ŀγ���Ϣ
			case COURSE_MODIFY:
				try {
					int wb = (cs.modifyCourse(courseInfo)) ? COURSE_MODIFY_SUCCESS : COURSE_MODIFY_FAIL;
					oos.writeInt(wb);
					
					oos.flush();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
			}
		}
		//�γ�ѡ��61������tbCourseStatus����в���
		else {
			try {
				if (cmd != COURSE_STATUS_QUERY)
					csInfo = (CourseStatusInfo)ois.readObject();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//���ݲ�ͬ��Ϣ�����в�ͬ����
			switch (cmd) {
			//ѡ��
			case COURSE_STATUS_SELECT:
				try {
					int wb = (cs.selectCourse(csInfo)) ? COURSE_STATUS_SELECT_SUCCESS : COURSE_STATUS_SELECT_FAIL;
					oos.writeInt(wb);
					
					oos.flush();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
				
			//�˿�
			case COURSE_STATUS_DESELECT:
				try {
					int wb = (cs.deselectCourse(csInfo)) ? COURSE_STATUS_DESELECT_SUCCESS : COURSE_STATUS_DESELECT_FAIL;
					oos.writeInt(wb);
					
					oos.flush();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
				
			//ѧ����ѯ�α�
			case COURSE_STATUS_CURRICULUM:
				try {
					CourseInfo result[] = cs.queryCurriculum(csInfo);
					if (result != null) {
						oos.writeInt(COURSE_STATUS_CURRICULUM_SUCCESS);
						oos.writeObject(result);	
					}
					else {
						oos.writeInt(COURSE_STATUS_CURRICULUM_FAIL);
					}
					
					oos.flush();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
				
			//��ʦ��ѯѡ��ѧ��
			case COURSE_STATUS_QUERY:
				try {

					CourseInfo cInfo = (CourseInfo)ois.readObject();
	
					CourseStatusInfo result[] = cs.queryStatus(cInfo);
					if (result != null) {
						oos.writeInt(COURSE_STATUS_QUERY_SUCCESS);
						oos.writeObject(result);
					}
					else {
						oos.writeInt(COURSE_STATUS_QUERY_FAIL);
					}
					
					oos.flush();
					
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				break;
				
			}
		}
	}
	
	/**
	 * ����ԤԼģ��
	 * 
	 * @param cmd ���ܵ���Ϣ
	 */
	private void Appoint(int cmd) {
		
		AppointInfo apInfo = null;
		AppointStatusInfo apsInfo = null;
		Appoint ap = new Appoint();
		
		//ԤԼ��Ŀ��70������tbAppoint����в���
		if (cmd / 10 == 70) {
			try {
				if (cmd != APPOINT_ITEM_QUERY)
					apInfo = (AppointInfo)ois.readObject();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//���ݲ�ͬ��Ϣ�����в�ͬ����
			switch(cmd) {
			//��ѯԤԼ��Ŀ
			case APPOINT_ITEM_QUERY:
				try {
					AppointInfo[] result = ap.queryAppointItem();
					if (result != null) {
						oos.writeInt(APPOINT_ITEM_QUERY_SUCCESS);
						oos.writeObject(result);	
					}
					else {
						oos.writeInt(APPOINT_ITEM_QUERY_FAIL);
					}
					
					oos.flush();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
				break;
				
			//�����Ŀ
			case APPOINT_ITEM_ADD:
				try {
					int wb = (ap.addItem(apInfo)) ? APPOINT_ITEM_ADD_SUCCESS : APPOINT_ITEM_ADD_FAIL;
					oos.writeInt(wb);
					
					oos.flush();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
				
			//ɾ����Ŀ
			case APPOINT_ITEM_DELETE:
				try {
					int wb = (ap.deleteItem(apInfo)) ? APPOINT_ITEM_DELETE_SUCCESS : APPOINT_ITEM_DELETE_FAIL;
					oos.writeInt(wb);
					
					oos.flush();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
				
			//�޸���Ŀ��Ϣ
			case APPOINT_ITEM_MODIFY:
				try {
					int wb = (ap.modifyItem(apInfo)) ? APPOINT_ITEM_MODIFY_SUCCESS : APPOINT_ITEM_MODIFY_FAIL;
					oos.writeInt(wb);
					
					oos.flush();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
			}
		}
		//ԤԼ������71������tbAppointStatus����в���
		else {
			try {
				apsInfo = (AppointStatusInfo)ois.readObject();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//���ݲ�ͬ��Ϣ�����в�ͬ����
			switch (cmd) {
			//���ԤԼ
			case APPOINT_STATUS_ADD:
				try {
					int wb = (ap.addStatus(apsInfo)) ? APPOINT_STATUS_ADD_SUCCESS : APPOINT_STATUS_ADD_FAIL;
					oos.writeInt(wb);
					
					oos.flush();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
				
			//ȡ��ԤԼ
			case APPOINT_STATUS_DELETE:
				try {
					int wb = (ap.deleteStatus(apsInfo)) ? APPOINT_STATUS_DELETE_SUCCESS : APPOINT_STATUS_DELETE_FAIL;
					oos.writeInt(wb);
					
					oos.flush();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
				
			//�޸�ԤԼ��Ϣ
			case APPOINT_STATUS_MODIFY:
				try {
					int wb = (ap.modifyStatus(apsInfo)) ? APPOINT_STATUS_MODIFY_SUCCESS : APPOINT_STATUS_MODIFY_FAIL;
					oos.writeInt(wb);
					
					oos.flush();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
				
			//��ѯԤԼ��¼
			case APPOINT_STATUS_RECORD_QUERY:
				try {
					AppointStatusInfo result[] = ap.queryStatus(apsInfo);
					if (result != null) {
						oos.writeInt(APPOINT_STATUS_RECORD_QUERY_SUCCESS);
						oos.writeObject(result);	
					}
					else {
						oos.writeInt(APPOINT_STATUS_RECORD_QUERY_FAIL);
					}
					
					oos.flush();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
				
			}
		}
		
	}
}

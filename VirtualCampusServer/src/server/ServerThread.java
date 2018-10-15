package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import view.ServerFrameView_MY;

/**
 * ���������߳�
 * 
 * @author Silence
 *
 */
public class ServerThread extends Thread {
	/**
	 * ������Socket
	 */
	private ServerSocket server;
	/**
	 * �����ӵĿͻ����߳�����
	 */
	private Vector<ClientThread> clients;
	
	public ServerThread() {
		
		try {
			server = new ServerSocket(8081);
			ServerFrameView_MY.setTextArea("��������߳�����\n����8081�˿�");
			System.out.println("Server main thread start.\nListen on port 8081");
			clients = new Vector<ClientThread>();
			
			this.start();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		//��������������
		while(!server.isClosed()) {
			try {
				Socket client = server.accept();//�����µĿͻ���
				
				ClientThread current = new ClientThread(client, this);
				current.start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void close() {
		//���������Socket�ѱ���
		if (server != null) {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ���ص�ǰ�����ӿͻ�������
	 */
	public int getSize() {
		return clients.size();
	}
	
	/**
	 * ������������µĿͻ���
	 */
	public int addClientConnection(ClientThread ct) {
		clients.add(ct);
		
		return clients.size();
	}

	/**
	 * ���������Ƴ��رյĿͻ���
	 * 
	 * @param ct Ҫ�رյĿͻ����߳�
	 * @return �ر�״̬
	 */
	public boolean closeClientConnection(ClientThread ct) {	
		if (clients.contains(ct)) {
			clients.remove(ct);
			
			return true;
		}

		return false;	
	}
	
	/**
	 * �������а���¼�û�IDѰ�ҿͻ���
	 */
	public boolean searchClientConnection(String curUser) {	
		for (ClientThread ct: clients) {
			if (ct.curUser != null && ct.curUser.equals(curUser)) {
				return true;
			}
		}
		
		return false;
	}
}

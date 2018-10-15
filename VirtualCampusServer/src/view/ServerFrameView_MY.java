package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import com.sun.awt.AWTUtilities;

import database.DBConnection;
import server.ServerThread;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class ServerFrameView_MY extends JFrame {
	// ������
	private static JFrame Frame_Server;
	public static int count = 0;

	// ͷ������Ϳؼ�
	private JPanel  Panel_Header;
	private JButton Button_Minimize;
	private JButton Button_Maximize;
	private JButton Button_Close;
	private JButton Button_Campus;
	private JLabel lblVirtualCampusServer;// virtual campus in English
	private JLabel Label_Upright;  // ���ߣ���������
	private static JButton Button_Login;
	
	// �������Ϳؼ�
	private static JPanel Panel_Main;	
	private JLabel Label_MessageTime;
	private JLabel Label_ShowTime;
	private JLabel Label_MessageState;
	private JLabel Label_ShowState;
	private JLabel Label_MessageNumber;
	private static JLabel Label_ShowNumber;
	private JButton Button_Start;
	private JButton Button_Stop;
	
	JScrollPane ScrollPane_ShowOnlineMessage;
	private JLabel Label_MessageOnlineMessage ;
	public static JTextArea TextArea_ShowOnlineMessage;
	
	private ServerThread mainthread;
	
	// ��¼�������
	LoginPopUp_MY LoginPopUpViewWindow;
	
	// �ƶ���ܵ��������
	int xOld = 0;
	int yOld = 0;	
	int xx = 360;
	int yy = 150;
	
	private static String StudentId = null;
	private static boolean isLogin = false;
	
	public static void setStudentId(String id){
		StudentId = id;
		isLogin = true;
		Button_Login.setText(StudentId);//��½����µ�¼��ť
		Button_Login.setToolTipText("");
	}
	
	public static void setTextNumber(int count){
		Label_ShowNumber.setText(count + " ��");
		Panel_Main.repaint();		
	}
	
	public static void setTextArea(String content){
		TextArea_ShowOnlineMessage.append(content + "\n");
		Panel_Main.repaint();		
	}
	
	public static void setMinimizeRestore(){//������С���Ļ�ԭ
		Frame_Server.setVisible(true);
		Frame_Server.setExtendedState(JFrame.NORMAL);
		Frame_Server.toFront();		
	}
	
	public static void setMinimize(){//������С��
		Frame_Server.dispose();	
	}
	
	public static void main(String[] args) {
		//new ServerThread();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerFrameView_MY window = new ServerFrameView_MY();
					window.Frame_Server.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ServerFrameView_MY() {
		setHeaderPanel();
		setHeaderPanelListeners();
		setMainPanel();
		setMainPanelListeners();
	}

	private void setHeaderPanel(){//����ͷ�����
		Frame_Server = new JFrame();
		Frame_Server.setBounds(360, 150, 640, 357);
		Frame_Server.setUndecorated(true);
		Frame_Server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame_Server.getContentPane().setLayout(null);
		AWTUtilities.setWindowShape(Frame_Server, new RoundRectangle2D.Double(0.0D, 0.0D, Frame_Server.getWidth(), Frame_Server.getHeight(), 26.0D, 26.0D));
		
		this.Panel_Header = new JPanel();
		this.Panel_Header.setBackground(new Color(0, 120, 215));
		this.Panel_Header.setBounds(0, 0, 640, 41);
		Frame_Server.getContentPane().add(this.Panel_Header);
		this.Panel_Header.setLayout(null);		
		
		ImageIcon icon_minimize = new ImageIcon(getClass().getResource("/images/sysbtn/minimize.png"));
		ImageIcon icon_maxmize  = new ImageIcon(getClass().getResource("/images/sysbtn/maximize.png"));
		ImageIcon icon_close    = new ImageIcon(getClass().getResource("/images/sysbtn/close.png"));
		ImageIcon icon_campus   = new ImageIcon(getClass().getResource("/images/sysbtn/campus.png"));
		ImageIcon icon_login    = new ImageIcon(getClass().getResource("/images/sysbtn/login.png"));
				
		this.Button_Minimize = new JButton(icon_minimize);
		this.Button_Minimize.setToolTipText("\u6700\u5C0F\u5316");
		this.Button_Minimize.setBounds(554, 8, 23, 23);
		this.Button_Minimize.setBorderPainted(false);
		this.Button_Minimize.setContentAreaFilled(false);
		this.Panel_Header.add(this.Button_Minimize);
		
		this.Button_Maximize = new JButton(icon_maxmize);
		this.Button_Maximize.setToolTipText("\u6700\u5927\u5316\uFF08\u529F\u80FD\u6682\u65E0\uFF09");
		this.Button_Maximize.setBounds(582, 8, 23, 23);
		this.Button_Maximize.setBorderPainted(false);
		this.Button_Maximize.setContentAreaFilled(false);
		this.Panel_Header.add(this.Button_Maximize);
		
		this.Button_Close = new JButton(icon_close);
		this.Button_Close.setToolTipText("\u5173\u95ED\u9875\u9762");
		this.Button_Close.setBounds(610, 8, 23, 23);
		this.Button_Close.setBorderPainted(false);
		this.Button_Close.setContentAreaFilled(false);
		this.Panel_Header.add(this.Button_Close);
		
		this.Button_Campus= new JButton(icon_campus);
		this.Button_Campus.setBounds(20, 8, 30, 30);
		this.Button_Campus.setBorderPainted(false);
		this.Button_Campus.setContentAreaFilled(false);
		this.Panel_Header.add(Button_Campus);
		
		this.lblVirtualCampusServer = new JLabel("Virtual Campus Server");
		this.lblVirtualCampusServer.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		this.lblVirtualCampusServer.setForeground(SystemColor.text);
		this.lblVirtualCampusServer.setBounds(60, 9, 220, 31);
		this.Panel_Header.add(this.lblVirtualCampusServer);
		
		Button_Login = new JButton(" \u672A\u767B\u5F55");
		Button_Login.setIcon(icon_login);
		Button_Login.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		Button_Login.setForeground(SystemColor.controlHighlight);
		Button_Login.setToolTipText("���¼");
		Button_Login.setBounds(427, 6, 118, 30);
		Button_Login.setBorderPainted(false);
		Button_Login.setContentAreaFilled(false);
		Panel_Header.add(Button_Login);
		
		this.Label_Upright = new JLabel("|");
		this.Label_Upright.setFont(new Font("΢���ź�", Font.BOLD, 18));
		this.Label_Upright.setForeground(UIManager.getColor("ComboBox.selectionBackground"));
		this.Label_Upright.setBounds(537, 6, 50, 30);
		this.Panel_Header.add(this.Label_Upright);		
	}
	
	private void setHeaderPanelListeners(){
		Frame_Server.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
			    int xOnScreen = e.getXOnScreen();
			    int yOnScreen = e.getYOnScreen();
			    xx = xOnScreen - xOld;
			    yy = yOnScreen - yOld;
			    Frame_Server.setLocation(xx, yy);//������ק�󣬴��ڵ�λ��
			    if( LoginPopUpViewWindow != null){//���õ�¼�������������һ���϶�
			    	LoginPopUpViewWindow.getFrame().setLocation(xx + 385, yy + 42);
			    	LoginPopUpViewWindow.getFrame().setAlwaysOnTop(true);//���ڶ���
			    }
			}
		});
		Frame_Server.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			    xOld = e.getX();//��¼��갴��ʱ������
			    yOld = e.getY();
			}
			public void mouseClicked(MouseEvent e) {
				if(LoginPopUp_MY.Frame_Login != null)//������뿪LoginPopUp_MY���򲢵����ʱ������LoginPopUp_MY��ʧ
					LoginPopUp_MY.Frame_Login.dispose();
			}
		});
		this.Button_Close.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if (mainthread != null) {
					if (ServerFrameView_MY.this.mainthread.isAlive() == true)
						JOptionPane.showMessageDialog(null, "���ȵ�� [�رշ�����] ��ť���˳���");
					else
						System.exit(0);
				}
				else
					System.exit(0);
			}

			public void mousePressed(MouseEvent e) {
				ServerFrameView_MY.this.Button_Close.setBorder(BorderFactory.createLoweredBevelBorder());
			}

			public void mouseReleased(MouseEvent e) {
				ServerFrameView_MY.this.Button_Close.setBorder(null);
			}

			public void mouseEntered(MouseEvent e) {
				ImageIcon icon = new ImageIcon(getClass().getResource("/images/sysbtn/close_bright.png"));
				ServerFrameView_MY.this.Button_Close.setIcon(icon);
			}

			public void mouseExited(MouseEvent e) {
				ImageIcon icon = new ImageIcon(getClass().getResource("/images/sysbtn/close.png"));
				ServerFrameView_MY.this.Button_Close.setIcon(icon);
			}
		});
		
		this.Button_Minimize.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				ServerFrameView_MY.Frame_Server.setExtendedState(JFrame.ICONIFIED);
			}

			public void mousePressed(MouseEvent e) {
				ServerFrameView_MY.this.Button_Minimize.setBorder(BorderFactory.createLoweredBevelBorder());
			}

			public void mouseReleased(MouseEvent e) {
				ServerFrameView_MY.this.Button_Minimize.setBorder(null);
			}

			public void mouseEntered(MouseEvent e) {
				ImageIcon icon = new ImageIcon(getClass().getResource("/images/sysbtn/minimize_bright.png"));
				ServerFrameView_MY.this.Button_Minimize.setIcon(icon);
			}

			public void mouseExited(MouseEvent e) {
				ImageIcon icon = new ImageIcon(getClass().getResource("/images/sysbtn/minimize.png"));
				ServerFrameView_MY.this.Button_Minimize.setIcon(icon);
			}
		});
		
		this.Button_Maximize.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
			//	LoginView_MY.this.Frame_VC.setExtendedState(JFrame.MAXIMIZED_BOTH);
			}

			public void mousePressed(MouseEvent e) {
				ServerFrameView_MY.this.Button_Maximize.setBorder(BorderFactory.createLoweredBevelBorder());
			}

			public void mouseReleased(MouseEvent e) {
				ServerFrameView_MY.this.Button_Maximize.setBorder(null);
			}

			public void mouseEntered(MouseEvent e) {
				ImageIcon icon = new ImageIcon(getClass().getResource("/images/sysbtn/maximize_bright.png"));
				ServerFrameView_MY.this.Button_Maximize.setIcon(icon);
			}

			public void mouseExited(MouseEvent e) {
				ImageIcon icon = new ImageIcon(getClass().getResource("/images/sysbtn/maximize.png"));
				ServerFrameView_MY.this.Button_Maximize.setIcon(icon);
			}
		});
		
		Button_Login.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if(isLogin == false)
					buildLoginPopUpView();//�����������·�
			}

			public void mousePressed(MouseEvent e) {
				ServerFrameView_MY.Button_Login.setBorder(BorderFactory.createLoweredBevelBorder());
			}

			public void mouseReleased(MouseEvent e) {
				ServerFrameView_MY.Button_Login.setBorder(null);
			}

			public void mouseEntered(MouseEvent e) {
				ImageIcon icon = new ImageIcon(getClass().getResource("/images/sysbtn/login_bright.png"));
				ServerFrameView_MY.Button_Login.setIcon(icon);
				ServerFrameView_MY.Button_Login.setForeground(SystemColor.text);
			}

			public void mouseExited(MouseEvent e) {
				ImageIcon icon = new ImageIcon(getClass().getResource("/images/sysbtn/login.png"));
				ServerFrameView_MY.Button_Login.setIcon(icon);
				ServerFrameView_MY.Button_Login.setForeground(SystemColor.controlHighlight);
			}
		});
	}
		
	@SuppressWarnings("deprecation")
	private void setMainPanel() {//�����������
		Panel_Main = new JPanel();
		Panel_Main.setToolTipText("");
		Panel_Main.setFont(new Font("΢���ź�", Font.PLAIN, 36));
		Panel_Main.setForeground(UIManager.getColor("Button.darkShadow"));
		Panel_Main.setBackground(UIManager.getColor("Button.background"));
		Panel_Main.setBounds(0, 40, 640, 317);
		Panel_Main.setLayout(null);
		Frame_Server.getContentPane().add(Panel_Main);
		
		this.Label_MessageTime = new JLabel("\u5F53\u524D\u65F6\u95F4\uFF1A");
		this.Label_MessageTime.setForeground(Color.DARK_GRAY);
		this.Label_MessageTime.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		this.Label_MessageTime.setBounds(35, 67, 80, 32);
		Panel_Main.add(this.Label_MessageTime);
		
		this.Label_ShowTime = new JLabel("2017\u5E749\u670819\u65E5 8:40");
        Timer timer = new Timer();
        timer.schedule(new RemindTask(), 0, 1000);
		this.Label_ShowTime.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		this.Label_ShowTime.setBounds(133, 67, 188, 32);
		Panel_Main.add(this.Label_ShowTime);
				
		this.Label_MessageState = new JLabel("������״̬��");
		this.Label_MessageState.setForeground(Color.DARK_GRAY);
		this.Label_MessageState.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		this.Label_MessageState.setBounds(35, 117, 100, 32);
		Panel_Main.add(this.Label_MessageState);
		
		this.Label_ShowState = new JLabel("\u5173\u95ED");
		this.Label_ShowState.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		this.Label_ShowState.setBounds(133, 117, 188, 32);
		Panel_Main.add(this.Label_ShowState);
		
		this.Label_MessageNumber = new JLabel("\u5728\u7EBF\u4EBA\u6570\uFF1A");
		this.Label_MessageNumber.setForeground(Color.DARK_GRAY);
		this.Label_MessageNumber.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		this.Label_MessageNumber.setBounds(35, 167, 80, 32);
		Panel_Main.add(this.Label_MessageNumber);
		
		Label_ShowNumber = new JLabel("");
		Label_ShowNumber.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		Label_ShowNumber.setBounds(133, 167, 188, 32);
		Label_ShowNumber.setText("0 ��");
		Panel_Main.add(Label_ShowNumber);
		
		this.Button_Start = new JButton("����������");
		this.Button_Start.setFont(new Font("΢���ź�", 1, 14));
		this.Button_Start.setBounds(35, 255, 188, 32);
		Panel_Main.add(this.Button_Start);
		
		this.Button_Stop = new JButton("�رշ�����");
		this.Button_Stop.setEnabled(false);
		this.Button_Stop.setFont(new Font("΢���ź�", 1, 14));
		this.Button_Stop.setBounds(329, 255, 188, 32);
		Panel_Main.add(this.Button_Stop);
		
		this.Label_MessageOnlineMessage = new JLabel("����̨��Ϣ");
		this.Label_MessageOnlineMessage.setForeground(Color.DARK_GRAY);
		this.Label_MessageOnlineMessage.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		this.Label_MessageOnlineMessage.setBounds(337, 47, 124, 32);
		Panel_Main.add(this.Label_MessageOnlineMessage);
		
		this.ScrollPane_ShowOnlineMessage = new JScrollPane();
		this.ScrollPane_ShowOnlineMessage.setBounds(337, 77, 253, 140);
		Panel_Main.add(this.ScrollPane_ShowOnlineMessage);
		
		TextArea_ShowOnlineMessage = new JTextArea(50,17);
		TextArea_ShowOnlineMessage.setForeground(SystemColor.controlDkShadow);
		TextArea_ShowOnlineMessage.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		TextArea_ShowOnlineMessage.setBounds(343, 83, 253, 99);
		TextArea_ShowOnlineMessage.enable(false);
		TextArea_ShowOnlineMessage.setDisabledTextColor(SystemColor.controlDkShadow);//�������ڳ��ֽ����ı��ĵ�ǰ��ɫ
		ScrollPane_ShowOnlineMessage.setViewportView(TextArea_ShowOnlineMessage);

	}
	
    public String getTime() {
    	String time = null;
        Calendar calendar = Calendar.getInstance();
        Date date = (Date) calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time = format.format(date);

         return time;
    }
	
    private class RemindTask extends TimerTask {
        public void run() {
        	Label_ShowTime.setText(getTime());
        }
   }

	private void setMainPanelListeners(){
		Panel_Main.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(LoginPopUp_MY.Frame_Login != null)//������뿪LoginPopUp_MY���򲢵����ʱ������LoginPopUp_MY��ʧ
					LoginPopUp_MY.Frame_Login.dispose();
			}
		});
		this.Button_Start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isLogin == false)
					JOptionPane.showMessageDialog(null, "����δ��¼���޷�������������\n���ȵ�¼��");
				else{
					ServerFrameView_MY.this.mainthread = new ServerThread();
					ServerFrameView_MY.this.Button_Start.setEnabled(false);
					ServerFrameView_MY.this.Button_Stop.setEnabled(true);
					ServerFrameView_MY.this.Label_ShowState.setText("����");
					ServerFrameView_MY.TextArea_ShowOnlineMessage.append("�������Ѿ�������\n\n");
					ServerFrameView_MY.Panel_Main.repaint();	
				}
			}
		});
		this.Button_Stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isLogin == false)
					JOptionPane.showMessageDialog(null, "����δ��¼���޷�������������\n���ȵ�¼��");
				else{
					ServerFrameView_MY.this.mainthread.close();
					ServerFrameView_MY.this.Button_Start.setEnabled(true);
					ServerFrameView_MY.this.Button_Stop.setEnabled(false);
					ServerFrameView_MY.this.Label_ShowState.setText("�ر�");
					ServerFrameView_MY.TextArea_ShowOnlineMessage.append("�������Ѿ��رգ�\n\n");
					ServerFrameView_MY.count = 0;
					ServerFrameView_MY.Label_ShowNumber.setText(Integer.toString(ServerFrameView_MY.count));
				}
			}
		});	
	}
		
	private void buildLoginPopUpView(){//չʾδ��¼�������
		this.LoginPopUpViewWindow = new LoginPopUp_MY();
		this.LoginPopUpViewWindow.getFrame().setBounds(xx + 385, yy + 42, 250, 270);
	}
}

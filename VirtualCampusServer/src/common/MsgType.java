package common;

/**
 * ��Ϣ���ͽӿ�
 * ��һλ����ģ������
 * �ڶ�λ��0,1��������Ϊ�����ڲ�ͬ���в�ѯ����
 * ����λΪ����handler
 * ����λ��������ڣ��������״̬
 * 
 * @author Silence
 *
 */
public abstract interface MsgType {
	//��¼ģ�飨1��
	public static final int LOGIN  = 101;
	public static final int LOGIN_SUCCESS = 1011;
	public static final int LOGIN_FAIL = 1012;
	public static final int REGISTER = 102;
	public static final int REGISTER_SUCCESS = 1021;
	public static final int REGISTER_FAIL = 1022;
	public static final int LOGOUT = 103;
	public static final int LOGOUT_SUCCESS = 1031;
	public static final int LOGOUT_FAIL = 1032;
	
	//����ģ�飨2��
	public static final int BANK_BALANCE_QUERY = 201;
	public static final int BANK_BALANCE_QUERY_SUCCESS = 2011;
	public static final int BANK_BALANCE_QUERY_FAIL = 2012;
	public static final int BANK_TRANSFER = 202;
	public static final int BANK_TRANSFER_SUCCESS = 2021;
	public static final int BANK_TRANSFER_FAIL = 2022;
	public static final int BANK_TRANSFER_RECORD_QUERY = 203;
	public static final int BANK_TRANSFER_RECORD_QUERY_SUCCESS = 2031;
	public static final int BANK_TRANSFER_RECORD_QUERY_FAIL = 2032;
	
	//ѧ����Ϣģ�飨3��
	public static final int STUDENTROLL_INFO_QUERY = 301;
	public static final int STUDENTROLL_INFO_QUERY_SUCCESS = 3011;
	public static final int STUDENTROLL_INFO_QUERY_FAIL = 3012;
	public static final int STUDENTROLL_ADD = 302;
	public static final int STUDENTROLL_ADD_SUCCESS = 3021;
	public static final int STUDENTROLL_ADD_FAIL = 3022;
	public static final int STUDENTROLL_DELETE = 303;
	public static final int STUDENTROLL_DELETE_SUCCESS = 3031;
	public static final int STUDENTROLL_DELETE_FAIL = 3032;
	public static final int STUDENTROLL_MODIFY = 304;
	public static final int STUDENTROLL_MODIFY_SUCCESS = 3041;
	public static final int STUDENTROLL_MODIFY_FAIL = 3042;
	public static final int STUDENTROLL_INFO_QUERY_ADMIN = 305;
	public static final int STUDENTROLL_INFO_QUERY_ADMIN_SUCCESS = 3051;
	public static final int STUDENTROLL_INFO_QUERY_ADMIN_FAIL = 3052;
	
	//ͼ���ģ��(4)
	//ͼ����鼮ģ�飨40��
	public static final int LIBRARY_BOOK_QUERY = 401;
	public static final int LIBRARY_BOOK_QUERY_SUCCESS = 4011;
	public static final int LIBRARY_BOOK_QUERY_FAIL = 4012;
	public static final int LIBRARY_BOOK_ADD = 402;
	public static final int LIBRARY_BOOK_ADD_SUCCESS = 4021;
	public static final int LIBRARY_BOOK_ADD_FAIL = 4022;
	public static final int LIBRARY_BOOK_DELETE = 403;
	public static final int LIBRARY_BOOK_DELETE_SUCCESS = 4031;
	public static final int LIBRARY_BOOK_DELETE_FAIL = 4032;
	public static final int LIBRARY_BOOK_MODIFY = 404;
	public static final int LIBRARY_BOOK_MODIFY_SUCCESS = 4041;
	public static final int LIBRARY_BOOK_MODIFY_FAIL = 4042;
	//ͼ��ݽ���ģ�飨41��
	public static final int LIBRARY_STATUS_BORROW = 411;
	public static final int LIBRARY_STATUS_BORROW_SUCCESS = 4111;
	public static final int LIBRARY_STATUS_BORROW_FAIL = 4112;
	public static final int LIBRARY_STATUS_RETURN = 412;
	public static final int LIBRARY_STATUS_RETURN_SUCCESS = 4121;
	public static final int LIBRARY_STATUS_RETURN_FAIL = 4122;
	public static final int LIBRARY_STATUS_QUERY = 413;
	public static final int LIBRARY_STATUS_QUERY_SUCCESS = 4131;
	public static final int LIBRARY_STATUS_QUERY_FAIL = 4132;
	
	//�̵�ģ�飨5��
	//�̵���Ʒģ�飨50��
	public static final int SHOP_GOODS_QUERY = 501;
	public static final int SHOP_GOODS_QUERY_SUCCESS = 5011;
	public static final int SHOP_GOODS_QUERY_FAIL = 5012;
	public static final int SHOP_GOODS_ADD = 502;
	public static final int SHOP_GOODS_ADD_SUCCESS = 5021;
	public static final int SHOP_GOODS_ADD_FAIL = 5022;
	public static final int SHOP_GOODS_DELETE = 503;
	public static final int SHOP_GOODS_DELETE_SUCCESS = 5031;
	public static final int SHOP_GOODS_DELETE_FAIL = 5032;
	public static final int SHOP_GOODS_MODIFY = 504;
	public static final int SHOP_GOODS_MODIFY_SUCCESS = 5041;
	public static final int SHOP_GOODS_MODIFY_FAIL = 5042;
	//�̵궩��ģ�飨51��
	public static final int SHOP_ORDER_QUERY_ADMIN=511;
	public static final int SHOP_ORDER_QUERY_ADMIN_SUCCESS=5111;
	public static final int SHOP_ORDER_QUERY_ADMIN_FAIL=5112;
	public static final int SHOP_ORDER_BUY = 512;
	public static final int SHOP_ORDER_BUY_SUCCESS = 5121;
	public static final int SHOP_ORDER_BUY_FAIL = 5122;
	public static final int SHOP_ORDER_QUERY_STUTEA = 513;
	public static final int SHOP_ORDER_QUERY_STUTEA_SUCCESS = 5131;
	public static final int SHOP_ORDER_QUERY_STUTEA_FAIL = 5132;	
	
	//�γ�ģ�飨6��
	//�γ���Ϣģ�飨60��
	public static final int COURSE_QUERY = 601;
	public static final int COURSE_QUERY_SUCCESS = 6011;
	public static final int COURSE_QUERY_FAIL = 6012;
	public static final int COURSE_ADD = 602;
	public static final int COURSE_ADD_SUCCESS = 6021;
	public static final int COURSE_ADD_FAIL = 6022;
	public static final int COURSE_DELETE = 603;
	public static final int COURSE_DELETE_SUCCESS = 6031;
	public static final int COURSE_DELETE_FAIL = 6032;
	public static final int COURSE_MODIFY = 604;
	public static final int COURSE_MODIFY_SUCCESS = 6041;
	public static final int COURSE_MODIFY_FAIL = 6042;
	//�γ�ѡ��״̬ģ�飨61��
	public static final int COURSE_STATUS_SELECT = 611;
	public static final int COURSE_STATUS_SELECT_SUCCESS = 6111;
	public static final int COURSE_STATUS_SELECT_FAIL = 6112;
	public static final int COURSE_STATUS_DESELECT = 612;
	public static final int COURSE_STATUS_DESELECT_SUCCESS = 6121;
	public static final int COURSE_STATUS_DESELECT_FAIL = 6122;
	public static final int COURSE_STATUS_CURRICULUM = 613;
	public static final int COURSE_STATUS_CURRICULUM_SUCCESS = 6131;
	public static final int COURSE_STATUS_CURRICULUM_FAIL = 6132;
	public static final int COURSE_STATUS_QUERY = 614;
	public static final int COURSE_STATUS_QUERY_SUCCESS = 6141;
	public static final int COURSE_STATUS_QUERY_FAIL = 6142;
	
	//����ԤԼģ�飨7��
	//����ԤԼ��Ŀģ�飨70��
	public static final int APPOINT_ITEM_QUERY = 701;
	public static final int APPOINT_ITEM_QUERY_SUCCESS = 7011;
	public static final int APPOINT_ITEM_QUERY_FAIL = 7012;
	public static final int APPOINT_ITEM_ADD = 702;
	public static final int APPOINT_ITEM_ADD_SUCCESS = 7021;
	public static final int APPOINT_ITEM_ADD_FAIL = 7022;
	public static final int APPOINT_ITEM_DELETE = 703;
	public static final int APPOINT_ITEM_DELETE_SUCCESS = 7031;
	public static final int APPOINT_ITEM_DELETE_FAIL = 7032;
	public static final int APPOINT_ITEM_MODIFY = 704;
	public static final int APPOINT_ITEM_MODIFY_SUCCESS = 7041;
	public static final int APPOINT_ITEM_MODIFY_FAIL = 7042;
	//����ԤԼ��¼ģ�飨71��
	public static final int APPOINT_STATUS_ADD = 711;
	public static final int APPOINT_STATUS_ADD_SUCCESS = 7111;
	public static final int APPOINT_STATUS_ADD_FAIL = 7112;
	public static final int APPOINT_STATUS_DELETE = 712;
	public static final int APPOINT_STATUS_DELETE_SUCCESS = 7121;
	public static final int APPOINT_STATUS_DELETE_FAIL = 7122;
	public static final int APPOINT_STATUS_MODIFY = 713;
	public static final int APPOINT_STATUS_MODIFY_SUCCESS = 7131;
	public static final int APPOINT_STATUS_MODIFY_FAIL = 7132;
	public static final int APPOINT_STATUS_RECORD_QUERY = 714;
	public static final int APPOINT_STATUS_RECORD_QUERY_SUCCESS = 7141;
	public static final int APPOINT_STATUS_RECORD_QUERY_FAIL = 7142;
	
}

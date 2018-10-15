package common;

import java.io.Serializable;

/**
 * �γ�ѡ����Ϣ
 * ����tbCourseStatus��Ľṹ��
 * 
 * @author Silence
 *
 */
public class CourseStatusInfo implements Serializable {

	private static final long serialVersionUID = 2;
	/**
	 * �γ�ID
	 */
	private String id;
	/**
	 * ѡ��ÿγ̵�ѧ��ID
	 */
	private String selector;
	
	public CourseStatusInfo(String i, String selector) {
		this.id = i;
		this.selector = selector;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSelector() {
		return selector;
	}
	public void setSelector(String selector) {
		this.selector = selector;
	}
}

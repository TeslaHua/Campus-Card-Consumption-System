package dbbean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** * @author  ���� E-mail: 
 * @date ����ʱ�䣺2017��1��8�� ����10:18:08 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class  DBBean{
	 //���ݿ�����·��,1433ΪTCP �˿ں�
	private static final String URL  ="jdbc:sqlserver://localhost:1433; DatabaseName=CardManagementSystem";  	 
	private static final String DRIVER  ="com.microsoft.sqlserver.jdbc.SQLServerDriver";   		         //��������
	private static final String USERNAME  ="Sa";  	//�û���
	private static final String PASSWORD  ="Wang89918157";   //�û�����
	    
	private static Connection conn;   //���ض����ݿ�����ӵĽӿڣ��Ự��
	private static Statement st;      //����ִ�о�̬ SQL ��䲢�����������ɽ���Ķ���Ľӿ�
	
	/**
	 * 
	 * @Title: ConnectionDataBase()
	 * @Description: �������ݿ�
	 * @throws SQLException
	 * @return: void
	 */
	
	public static void ConnectionDataBase() {
		try{
			Class.forName(DRIVER);          //����������ֻ����һ��
		}catch(ClassNotFoundException event){
			System.out.print("�޷�����������ʽʵ��!");
		}
		
		try{	
			conn=DriverManager.getConnection(URL,USERNAME,PASSWORD );    //�������ݿ����
			conn.setAutoCommit(true);                                 //�����Զ��ύģʽ						
			System.out.println("�Ѿ����ӵ����ݿ�...");				
		}catch(SQLException e1) {
		   System.out.println("�쳣"+e1);
	    }
	}
	/**
	 * 
	 * @Title: InsertDateToDatebase
	 * @Description: �����в���,�޸�,ɾ������
	 * @throws SQLException
	 * @return: boolean,��������ɹ�����true,���򷵻�false
	 */

	public static boolean InsertDeleteUpdate(String str)throws SQLException 
	{
		st=conn.createStatement();   //ִ�о�̬ SQL ��䲢�����������ɽ���Ķ���	
		int number = st.executeUpdate(str);//����ִ�в�����
		st.close();       //�����ͷŴ� Statement ��������ݿ�� JDBC ��Դ
		return (number!=0?true:false);  //�����ݲ�������true,���򷵻�false
	}
	/**
	 * 
	 * @Title: QueryDateFromDatebase
	 * @Description: ��ѯ���е����ݲ�����һ�������
	 * @throws SQLException
	 * @return: ����һ�������
	 */

	public static ResultSet QueryDateFromDatebase(String str)throws SQLException 
	{
		// ִ�и����� SQL��䣬����䷵�ص��� ResultSet ����, ��ʾ���ݿ����������ݱ����������ڲ�����ʾ���Թ�����ȡ�����
		st=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);   //ִ�о�̬ SQL ��䲢�����������ɽ���Ķ���	 
		return st.executeQuery(str);
	}
}

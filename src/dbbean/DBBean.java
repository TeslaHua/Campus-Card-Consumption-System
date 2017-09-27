package dbbean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** * @author  作者 E-mail: 
 * @date 创建时间：2017年1月8日 上午10:18:08 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class  DBBean{
	 //数据库连接路径,1433为TCP 端口号
	private static final String URL  ="jdbc:sqlserver://localhost:1433; DatabaseName=CardManagementSystem";  	 
	private static final String DRIVER  ="com.microsoft.sqlserver.jdbc.SQLServerDriver";   		         //连接驱动
	private static final String USERNAME  ="Sa";  	//用户名
	private static final String PASSWORD  ="Wang89918157";   //用户密码
	    
	private static Connection conn;   //与特定数据库的连接的接口（会话）
	private static Statement st;      //用于执行静态 SQL 语句并返回它所生成结果的对象的接口
	
	/**
	 * 
	 * @Title: ConnectionDataBase()
	 * @Description: 连接数据库
	 * @throws SQLException
	 * @return: void
	 */
	
	public static void ConnectionDataBase() {
		try{
			Class.forName(DRIVER);          //加载驱动，只加载一次
		}catch(ClassNotFoundException event){
			System.out.print("无法创建驱动程式实体!");
		}
		
		try{	
			conn=DriverManager.getConnection(URL,USERNAME,PASSWORD );    //连接数据库对象
			conn.setAutoCommit(true);                                 //启用自动提交模式						
			System.out.println("已经连接到数据库...");				
		}catch(SQLException e1) {
		   System.out.println("异常"+e1);
	    }
	}
	/**
	 * 
	 * @Title: InsertDateToDatebase
	 * @Description: 往表中插入,修改,删除数据
	 * @throws SQLException
	 * @return: boolean,如果操作成功返回true,否则返回false
	 */

	public static boolean InsertDeleteUpdate(String str)throws SQLException 
	{
		st=conn.createStatement();   //执行静态 SQL 语句并返回它所生成结果的对象	
		int number = st.executeUpdate(str);//返回执行操作行
		st.close();       //立即释放此 Statement 对象的数据库和 JDBC 资源
		return (number!=0?true:false);  //有数据操作返回true,否则返回false
	}
	/**
	 * 
	 * @Title: QueryDateFromDatebase
	 * @Description: 查询表中的数据并返回一个结果集
	 * @throws SQLException
	 * @return: 返回一个结果集
	 */

	public static ResultSet QueryDateFromDatebase(String str)throws SQLException 
	{
		// 执行给定的 SQL语句，该语句返回单个 ResultSet 对象, 表示数据库结果集的数据表。其中括号内参数表示可以滚动获取结果集
		st=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);   //执行静态 SQL 语句并返回它所生成结果的对象	 
		return st.executeQuery(str);
	}
}

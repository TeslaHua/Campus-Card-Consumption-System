/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2016年12月30日 下午8:21:34 
 * @version 1.0
 * @parameter  
 * @since  
 * @return  
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.PortableInterceptor.HOLDING;
import dbbean.*;

/**
 * 
 * @ClassName: Login
 * @Description: TODO
 * @author: Administrator
 * @date: 2016年12月30日 下午8:01:09
 */
public class CardManagementSystem extends HomeScreen {

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;
	private static boolean flag = false;
	private String passwordString = new String(); // 负责接收查询的密码
	private ResultSet resultSet = null; // 查询的结果集
	private JButton LoginButton = new JButton("登录");
	private JButton CancelbButton1 = new JButton("取消");
	private JButton ModifyPasswordButton = new JButton("修改密码");
	private JButton ConfirmButton = new JButton("确认");
	private JButton CancelbButton2 = new JButton("取消");

	private JLabel PasswordLabel = new JLabel("六位密码：");
	private JLabel NewPasswordLable = new JLabel("输入新密码：");
	private JLabel ConfirmPasswordLable = new JLabel("确认新密码：");
	private JLabel RemindMessageLabel = new JLabel("(  密码为六位数字  !!)");
	private JPasswordField PasswordField = new JPasswordField(6);
	private JPasswordField modifyPasswordField = new JPasswordField(6);
	private JPasswordField ConfirmPasswordField = new JPasswordField(6);

	private JPanel loginPanel = new JPanel(); // 登录面板
	private JPanel modifyPanel = new JPanel(); // 修改面板

	public CardManagementSystem() {

		loginPanel.setLayout(null);
		PasswordLabel.setBounds(60, 15, 65, 40);
		PasswordField.setBounds(150, 25, 80, 25);
		PasswordField.setEchoChar('*'); // 设置回显字符

		RemindMessageLabel.setBounds(75, 50, 200, 30);
		RemindMessageLabel.setFont(new Font("Serif", Font.BOLD, 16));
		RemindMessageLabel.setForeground(Color.RED);

		LoginButton.setBounds(23, 80, 60, 25);
		ModifyPasswordButton.setBounds(93, 80, 100, 25);
		CancelbButton1.setBounds(203, 80, 60, 25);

		loginPanel.add(PasswordLabel);
		loginPanel.add(PasswordField);
		loginPanel.add(RemindMessageLabel);
		loginPanel.add(LoginButton);
		loginPanel.add(ModifyPasswordButton);
		loginPanel.add(CancelbButton1);

		loginPanel.setBorder(new TitledBorder("登录"));

		modifyPanel.setLayout(null);
		NewPasswordLable.setBounds(60, 5, 100, 40);
		modifyPasswordField.setBounds(150, 15, 80, 25);

		modifyPasswordField.setEchoChar('*'); // 设置回显字符
		ConfirmPasswordLable.setBounds(60, 50, 100, 40);
		ConfirmPasswordField.setBounds(150, 55, 80, 25);

		ConfirmPasswordField.setEchoChar('*'); // 设置回显字符
		ConfirmButton.setBounds(80, 100, 60, 25);
		CancelbButton2.setBounds(150, 100, 60, 25);

		modifyPanel.add(NewPasswordLable);
		modifyPanel.add(modifyPasswordField);
		modifyPanel.add(ConfirmPasswordLable);
		modifyPanel.add(ConfirmPasswordField);
		modifyPanel.add(ConfirmButton);
		modifyPanel.add(CancelbButton2);

		modifyPanel.setBorder(new TitledBorder("修改密码"));

		setLayout(new GridLayout(2, 1));
		add(loginPanel); // 添加登录面板
		add(modifyPanel); // 添加修改面板
	}

	// 事件处理函数
	public void HandleLoginEvent() {
		// 登录按钮事件操作,检查密码输入是否有效
		LoginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					resultSet = DBBean
							.QueryDateFromDatebase("SELECT * FROM Passwordtable");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					while (resultSet.next()) {
						passwordString = resultSet.getString("password"); // 在结果集中把密码列提取出来
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (String.valueOf(PasswordField.getPassword()).equals(
						passwordString)) { // 当前输入密码与数据库中密码相同
					HomeScreen homeScreen = new HomeScreen();
					homeScreen.setSize(700, 680);
					homeScreen.setTitle("主界面");
					homeScreen.setLocationRelativeTo(null);
					homeScreen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 指定框架关闭时的操作
					homeScreen.setVisible(true);
					homeScreen.homescreen();
					homeScreen.HandleMainEvent();
				}else{
					JOptionPane.showMessageDialog(null, "你输入的密码有误，请重新输入！");
					PasswordField.setText(""); // 清空输入框以便重新输入
				}
			}
		});

		// 取消输入密码
		CancelbButton1.addActionListener(new ActionListener() { // 取消密码输入

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						PasswordField.setText(""); // 清空输入框以便重新输入
					}
				});

		// 触发修改密码按钮
		ModifyPasswordButton.addActionListener(new ActionListener() { // 修改密码
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						try {
							resultSet = DBBean
									.QueryDateFromDatebase("SELECT * FROM Passwordtable");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							while (resultSet.next()) {
								passwordString = resultSet
										.getString("password"); // 在结果集中把密码列提取出来
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if (String.valueOf(PasswordField.getPassword()).equals(
								passwordString)) { // 当前输入密码与数据库中密码相同
							JOptionPane.showMessageDialog(null,
									"请在下面界面输入的你新密码 ！");
							flag = true; // 置位之后确认按钮才有效
						} else {
							JOptionPane.showMessageDialog(null,
									"你输入的原始密码有误，请重新输入！");
							PasswordField.setText(""); // 清空输入框以便重新输入
						}
					}
				});
		// 确认修改密码,且必须在输入的原始密码正确的情况下有效
		ConfirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (String.valueOf(modifyPasswordField.getPassword()).equals(
						String.valueOf(ConfirmPasswordField.getPassword()))
						&& String.valueOf(modifyPasswordField.getPassword())
								.matches("[0-9]{6}")
						&& String.valueOf(ConfirmPasswordField.getPassword())
								.matches("[0-9]{6}") && flag) { // flag置位之后保证用户是在输对原始密码
					try {
						// 将新密码存入数据库冲掉原来密码
						if (DBBean
								.InsertDeleteUpdate("UPDATE Passwordtable SET  password = "
										+ String.valueOf(modifyPasswordField
												.getPassword())
										+ " WHERE passwordID =1")) {
							JOptionPane.showMessageDialog(null, "密码修改成功，请重新登录"); // 可以使用新密码登录界面了
							PasswordField.setText(""); // 清空输入框以便重新输入
							modifyPasswordField.setText("");
							ConfirmPasswordField.setText("");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "你没有确认原始密码或者新密码格式不匹配！");
					modifyPasswordField.setText("");
					ConfirmPasswordField.setText("");
				}
				flag = false; // 将标志位取非，以便下一次触发
			}
		});
		// 取消修改密码
		CancelbButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				modifyPasswordField.setText("");
				ConfirmPasswordField.setText("");
			}
		});
	}

	// 主函数入口
	public static void main(String[] args) {
		CardManagementSystem login = new CardManagementSystem();
		login.setSize(300, 300);
		login.setTitle("登录界面");
		login.setLocationRelativeTo(null);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 指定框架关闭时的操作
		login.setVisible(true);
		DBBean.ConnectionDataBase(); // 开始连接数据库
		login.HandleLoginEvent();
	}
}

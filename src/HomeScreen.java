import java.awt.BorderLayout;
import java.sql.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.omg.CORBA.BAD_TYPECODE;

import SerialPort.Sms;
import dbbean.*;
import dynamicreports.*;

import java.net.*;
import java.util.*;

/**
 * * @author 作者 E-mail:
 * 
 * @date 创建时间：2016年12月30日 下午8:38:53
 * @version 1.0
 * @parameter
 * @since
 * @return
 */

public class HomeScreen extends Sms {

	private JLabel nameLabel = new JLabel("姓名：");
	private JLabel idLabel = new JLabel("卡号：");
	private JLabel schoolLabel = new JLabel("单位：");
	private JLabel PhoneLabel = new JLabel("手机号：");
	private JLabel moneyLabel = new JLabel("金额：");
	private JLabel cardstateLabel = new JLabel("IC卡状态：");
	private JLabel rechargemoneyLabel = new JLabel("充值金额：");

	private JLabel titleLabel = new JLabel("刷卡消费管理系统"); // 标题
	private JLabel nameremindlLabel = new JLabel("(不超过5个中文字符)");
	private JLabel idremindLabel = new JLabel("(不超过11个数字字符)");
	private JLabel schoolremindLabel = new JLabel("(不超过15个中文字符)");
	private JLabel PhoneremindLabel = new JLabel("(不超过15个数字字符)");
	private JLabel moneyremindlLabel = new JLabel("(必须是数字)");
	private JLabel cardstateremindlabel = new JLabel("(挂失或者正常)");

	private JTextField nameField = new JTextField(10);
	public static JTextField idField = new JTextField(6);
	private JTextField schoolField = new JTextField(20);
	private JTextField PhoneField = new JTextField(6);
	private JTextField moneyField = new JTextField(6);
	private JTextField cardstateField = new JTextField(5); // IC卡状态
	private JTextField rechargemoneyField = new JTextField(6);

	private JTextField searchField = new JTextField(10);

	private JButton ReportlossButton = new JButton("挂失");
	private JButton retrieveButton = new JButton("解挂");
	private JButton CancellationButton = new JButton("注销");
	private JButton rechargebButton = new JButton("充值");

	private JButton searchAccountButton = new JButton("查看全部账户");
	private JButton clearAccountButton = new JButton("清空表格");

	private JButton viewreportButton = new JButton("查看报表");
	private JButton openmachineButton = new JButton("连接下位机");

	private JRadioButton nameJRadioButton = new JRadioButton("按姓名搜索");
	private JRadioButton idJRadioButton = new JRadioButton("按卡号搜索");
	private JPanel jpRadioButtons = new JPanel(new GridLayout(2, 1)); // 用来放置单选按钮组
	private ButtonGroup group = new ButtonGroup(); // 创建一个单选按钮组

	private JPanel jPanel1 = new JPanel(); // 用来放置卡号信息的面板
	private JPanel jPanel2 = new JPanel(new FlowLayout()); // 用来放置搜索结果的面板
	private JPanel jPanel3 = new JPanel(); // 用来防止底部控件

	private BackGroundpanel backGroundpanel = new BackGroundpanel(); // 系统背景面板

	private String[] colnames = { "姓名", "卡号", "手机号", "金额", "单位", "状态" }; // 表中一共有六列
	private DefaultTableModel model = new DefaultTableModel(colnames, 100); // 这个表格有colnames的元素的个数个列数，行数有100行
	private JTable table = new JTable(model);           // 构造一个 JTable，使用数据模型
												        // model、默认的列模型和默认的选择模型对其进行初始化。
	private JProgressBar bar = new JProgressBar(0, 100); // 指定最小和最大值的进度条
	private JScrollPane scrollpane = new JScrollPane(table); // 创建一个显示指定组件内容的
																// JScrollPane，只要组件的内容超过table大小就会显示水平和垂直滚动条。

	public void homescreen() {

		setLayout(new BorderLayout());
		titleLabel.setBounds(225, -30, 250, 90);
		titleLabel.setFont(new Font("Dialog", Font.ITALIC, 30));
		titleLabel.setForeground(Color.RED);

		jPanel1.setLayout(null);
		jPanel1.setBorder(new TitledBorder("显示信息"));
		jPanel1.setBounds(18, 30, 650, 210);

		nameLabel.setBounds(30, 20, 50, 20);
		nameField.setBounds(90, 20, 80, 20);
		nameremindlLabel.setBounds(180, 20, 120, 20);
		nameField.setEditable(false);// 设置单行文本不可编辑
		idLabel.setBounds(30, 50, 50, 20);
		idField.setBounds(90, 50, 100, 20);
		idremindLabel.setBounds(200, 50, 130, 20);
		idField.setEditable(false);
		schoolLabel.setBounds(30, 80, 50, 20);
		schoolField.setBounds(90, 80, 130, 20);
		schoolremindLabel.setBounds(230, 80, 130, 20);
		ReportlossButton.setBounds(550, 35, 80, 25);
		schoolField.setEditable(false);
		PhoneLabel.setBounds(30, 110, 55, 20);
		PhoneField.setBounds(90, 110, 100, 20);
		PhoneremindLabel.setBounds(200, 110, 130, 20);
		retrieveButton.setBounds(550, 75, 80, 25);
		PhoneField.setEditable(false);
		moneyLabel.setBounds(30, 140, 50, 20);
		moneyField.setBounds(90, 140, 100, 20);
		moneyremindlLabel.setBounds(200, 140, 120, 20);
		CancellationButton.setBounds(550, 115, 80, 25);
		moneyField.setEditable(false);
		cardstateLabel.setBounds(30, 170, 80, 20);
		cardstateField.setBounds(110, 170, 90, 20);
		cardstateremindlabel.setBounds(220, 170, 100, 20);
		cardstateField.setEditable(false);
		rechargemoneyLabel.setBounds(340, 170, 80, 20);
		rechargemoneyField.setBounds(420, 170, 80, 20);
		rechargebButton.setBounds(550, 155, 80, 25);

		jPanel1.add(nameLabel);
		jPanel1.add(nameField);
		jPanel1.add(nameremindlLabel);
		jPanel1.add(idLabel);
		jPanel1.add(idField);
		jPanel1.add(idremindLabel);
		jPanel1.add(schoolLabel);
		jPanel1.add(schoolField);
		jPanel1.add(schoolremindLabel);
		jPanel1.add(PhoneLabel);
		jPanel1.add(PhoneField);
		jPanel1.add(ReportlossButton);
		jPanel1.add(retrieveButton);
		jPanel1.add(moneyLabel);
		jPanel1.add(moneyField);
		jPanel1.add(CancellationButton);
		jPanel1.add(PhoneremindLabel);
		jPanel1.add(moneyremindlLabel);
		jPanel1.add(cardstateLabel);
		jPanel1.add(cardstateField);
		jPanel1.add(cardstateremindlabel);
		jPanel1.add(rechargemoneyLabel);
		jPanel1.add(rechargemoneyField);
		jPanel1.add(rechargebButton);

		jPanel2.setBorder(new TitledBorder("查询"));
		jPanel2.setBounds(18, 480, 650, 60);
		jPanel2.add(searchAccountButton);
		jPanel2.add(clearAccountButton);
		jPanel2.add(bar); // 添加进度条

		scrollpane.setBorder(new TitledBorder("全部账户信息"));
		scrollpane.setBounds(18, 240, 650, 220);

		jPanel3.setBounds(18, 540, 650, 55);
		jPanel3.setLayout(new GridLayout(1, 4, 60, 15));
		jPanel3.setBorder(new TitledBorder("其他"));

		jpRadioButtons.add(nameJRadioButton);
		jpRadioButtons.add(idJRadioButton);
		group.add(nameJRadioButton);
		group.add(idJRadioButton);

		jPanel3.add(jpRadioButtons);
		jPanel3.add(searchField);
		jPanel3.add(openmachineButton);
		jPanel3.add(viewreportButton);

		backGroundpanel.setOpaque(false);
		backGroundpanel.setLayout(null);
		add(backGroundpanel, BorderLayout.CENTER);
		backGroundpanel.add(titleLabel);
		backGroundpanel.add(jPanel1);
		backGroundpanel.add(scrollpane); // 添加滑条
		backGroundpanel.add(jPanel2);
		backGroundpanel.add(jPanel3);

	}

	// 绘制主界面背景
	public class BackGroundpanel extends JPanel {

		private URL Imageurl = getClass().getResource("fanka.jpg"); // 创建饭卡系统的主界面背景图片
		// 此类是所有 Abstract Window Toolkit
		// 实际实现的抽象超类,获取默认工具包,返回一幅图像，该图像从指定文件中获取像素数据，图像格式可以是 GIF、JPEG 或 PNG
		private Image img = Toolkit.getDefaultToolkit().getImage(Imageurl);

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			int WinWidth = getWidth();
			int WinHeight = getHeight();
			int IWidth = img.getWidth(this);
			int IHeight = img.getHeight(this);
			int xindex = (WinWidth - IWidth) / 2;
			int yindex = (WinHeight - IHeight) / 2;
			g.drawImage(img, xindex, yindex, null); // 把xindex,yindex设为0就可以取消背景图片
		}
	}

	// 事件处理函数
	public void HandleMainEvent() {

		// 打开下位机以接收下位机发来的IC卡数据
		openmachineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ICcardData IC = new ICcardData(); // 创建一个任务，显示表格
				Thread thread = new Thread(IC); // 为这个任务分配一个线程
				thread.start(); // 开启线程
			}
		});
		/*
		 * //保存读卡器识别的账户信息存到数据库 saveButton.addActionListener(new ActionListener()
		 * {
		 * 
		 * @Override public void actionPerformed(ActionEvent e){ //TODO
		 * Auto-generated method stub //卡号信息满足的要求，全部用正则表达式约束
		 * if(nameField.getText
		 * ().matches("[^x00-xff]{1,5}")&&idField.getText().matches
		 * ("[0-9]{1,10}") &&schoolField.getText().matches("[^x00-xff]{1,15}")&&
		 * cardField.getText().matches("[0-9]{1,20}")&&
		 * moneyField.getText().matches("[0-9]+([.]{1}[0-9]+){0,1}")) { try {
		 * //现在检查该账户信息是否已经存在，如果不存在，返回一个next()为false的ResultSet 对象 ResultSet
		 * resultSet = DBBean.QueryDateFromDatebase(
		 * "SELECT CustomerName FROM AccountMessage WHERE CustomerID = '"
		 * +idField.getText()+"'"); if (!resultSet.next()) {//不存在就把信息插入数据库
		 * resultSet.first(); try {
		 * 
		 * DBBean.InsertDeleteUpdate(
		 * "INSERT INTO AccountMessage  (CustomerName,CustomerID,Customercard,CustomerMoney,Customeruint,Customerstate)"
		 * +
		 * "VALUES ('"+nameField.getText()+"','"+idField.getText()+"','"+cardField
		 * .getText()+"','"+moneyField.getText()+"','"+
		 * schoolField.getText()+"','未挂失')");
		 * JOptionPane.showMessageDialog(null,"账户信息保存成功！"); } catch
		 * (SQLException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } } else { //如果存在就提示账户已存在
		 * JOptionPane.showMessageDialog(null,"你输入的账户已存在！"); } } catch
		 * (SQLException e2) { // TODO Auto-generated catch block
		 * e2.printStackTrace(); } }else {
		 * JOptionPane.showMessageDialog(null,"你的账号信息不符合要求 ！"); } } });
		 */
		// 按姓名查找账户
		nameJRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (searchField.getText().matches("[^x00-xff]{1,5}")) {
					try {
						// 获取数据库中对应姓名的结果集
						ResultSet resultSet = DBBean
								.QueryDateFromDatebase("SELECT * FROM AccountMessage WHERE CustomerName = '"
										+ searchField.getText() + "'");
						if (resultSet.next()) {
							resultSet.first();
							nameField.setText(resultSet
									.getString("CustomerName"));
							idField.setText(resultSet.getString("CustomerID"));
							schoolField.setText(resultSet
									.getString("Customeruint"));
							PhoneField.setText(resultSet.getString(
									"CustomerPhone").trim()); // 数据库中定义的是char(10)当输入不足10个字节时自动用空格补全，
							moneyField.setText(resultSet.getString(
									"CustomerMoney").trim());// 为了防止出现正则不匹配的问题，就用trim()函数将其多余空格去除。
							cardstateField.setText(resultSet
									.getString("Customerstate"));
						} else {
							JOptionPane.showMessageDialog(null, "你搜索的账户不存在！");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "请输入不超过5个中文字符");
				}
			}
		});
		// 按卡号查找账户
		idJRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (searchField.getText().matches("[0-9]{1,11}")) {
					try {
						// 获取数据库中对应姓名的结果集
						ResultSet resultSet = DBBean
								.QueryDateFromDatebase("SELECT * FROM AccountMessage WHERE CustomerID = '"
										+ searchField.getText() + "'");
						if (resultSet.next()) {
							resultSet.first();
							nameField.setText(resultSet
									.getString("CustomerName"));
							idField.setText(resultSet.getString("CustomerID"));
							schoolField.setText(resultSet
									.getString("Customeruint"));
							PhoneField.setText(resultSet.getString(
									"CustomerPhone").trim()); // 数据库中定义的是char(10)当输入不足10个字节时自动用空格补全，
							moneyField.setText(resultSet.getString(
									"CustomerMoney").trim());// 为了防止出现正则不匹配的问题，就用trim()函数将其多余空格去除。
							cardstateField.setText(resultSet
									.getString("Customerstate"));
						} else {
							JOptionPane.showMessageDialog(null, "你搜索的账户不存在！");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "请输入不超过10个数字字符");
				}
			}
		});
		// 触发搜索全部账户信息按钮
		searchAccountButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ShowModel show = new ShowModel(); // 创建一个任务，显示表格
				Thread thread = new Thread(show); // 为这个任务分配一个线程
				thread.start(); // 开启线程
			}
		});
		// 触发清除搜索信息按钮
		clearAccountButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stubs
				model = new DefaultTableModel(colnames, 100);
				table.setModel(model);
			}
		});
		// 触发账户挂失按钮
		ReportlossButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (DBBean
							.InsertDeleteUpdate("UPDATE AccountMessage SET Customerstate = '挂失' WHERE CustomerID = '"
									+ idField.getText() + "'")) {
					    cardstateField.setText("挂失");  //显示挂失后的状态
						JOptionPane.showMessageDialog(null, "你的账户现在是挂失状态");
					}
					else {
						JOptionPane.showMessageDialog(null, "请先查询所要执行的账户！");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		// 解除挂失状态
		retrieveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if (DBBean
							.InsertDeleteUpdate("UPDATE AccountMessage SET Customerstate = '正常' WHERE CustomerID = '"
									+ idField.getText() + "'")) {
					    cardstateField.setText("正常");   //显示解挂后的状态
						JOptionPane.showMessageDialog(null, "你的账户现在是正常状态");
					}
					else {
						JOptionPane.showMessageDialog(null, "请先查询所要执行的账户！");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		// 注销账户的触发事件
		CancellationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if (DBBean
							.InsertDeleteUpdate("DELETE FROM AccountMessage  WHERE CustomerID = '"
									+ idField.getText() + "'")) {
						JOptionPane.showMessageDialog(null, "你的账户注销完成！");						
						//清空显示处
						nameField.setText("");
						idField.setText("");
						schoolField.setText("");
						PhoneField.setText(""); 
						moneyField.setText(" ");
						cardstateField.setText("");						
					}
					else {
						JOptionPane.showMessageDialog(null, "请先查询所要执行的账户！");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		// 充值按钮触发事件
		rechargebButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (rechargemoneyField.getText().matches(
						"[0-9]+([.]{1}[0-9]+){0,1}")) {
					try {
						ResultSet resultSet = DBBean
								.QueryDateFromDatebase("SELECT * FROM AccountMessage WHERE CustomerID = '"
										+ idField.getText() + "'");
						if (resultSet.next()) { // 说明存在这个账户信息
							System.out.println(cardstateField.getText());
							if(cardstateField.getText().endsWith("正常"))  //正常情况下充值
							{	resultSet.first();
								// 得到long类型当前时间
								long L = System.currentTimeMillis();
								// new日期对象
								Date date = new Date(L);
								// 转换提日期输出格式
								SimpleDateFormat dateFormat = new SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss");
								// 计算出充值之后的卡的余额
								Double currrentmoneyDouble = Double
										.valueOf(resultSet.getString(
												"CustomerMoney").trim())
										+ Double.valueOf(rechargemoneyField
												.getText());
								// 将对应的消费信息插入到数据库
								DBBean.InsertDeleteUpdate("INSERT INTO ReportMessage (OperateTime,CustomerName,Deductmoney,Savemoney,CustomerID,CustomerMoney)"
										+ "VALUES ('"
										+ dateFormat.format(date)
										+ "','"
										+ nameField.getText()
										+ "','0','"
										+ rechargemoneyField.getText()
										+ "','"
										+ idField.getText()
										+ "','"
										+ currrentmoneyDouble.toString() + "')");
								// 同时也要更新当前金额到账户相应信息
								DBBean.InsertDeleteUpdate("UPDATE AccountMessage SET CustomerMoney = "
										+ currrentmoneyDouble.toString()
										+ " WHERE CustomerID = '"
										+ idField.getText() + "'");
								JOptionPane.showMessageDialog(null, "充值成功！");
								moneyField.setText(currrentmoneyDouble.toString().trim());//显示充值后的余额。
							}
							else {
								JOptionPane.showMessageDialog(null, "IC卡已挂失，不可充值！"); //防止挂失后充值
							}
						} else {
							JOptionPane.showMessageDialog(null, "你要充值的账户不存在！");  //防止注销后充值
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "请输入正的数字！");
				}
				rechargemoneyField.setText(" ");
			}
		});
		// 查看用户消费报表
		viewreportButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Connection conn = null;
				try {
					conn = Reports.getConn();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Reports.buildReport(conn, idField.getText());
			}
		});
	}

	/**
	 * 
	 * @Title: ShowModel
	 * @Description: 一个任务类，实现显示数据表格的功能
	 * @throws
	 * @return:
	 */
	private class ShowModel implements Runnable {
		int rowcount = 0;

		public void run() {
			try {
				table.setVisible(false); // 进度条没有完成时，不显示表格数据
				ResultSet resultSet = DBBean
						.QueryDateFromDatebase("select * from AccountMessage"); // 开始查询账户信息并把结果集返回
				resultSet.last(); // 直接执行跳到结果集的最后一行
				bar.setMaximum(resultSet.getRow()); // 可以得到结果集的总行数，并以此设置进度条的最大值
				model.setRowCount(resultSet.getRow());// 以此设置table的总行数
				resultSet.beforeFirst();// 重新执行到第一行的前一行，以便查询结果集的集体内容
				while (resultSet.next()) {
					table.setValueAt(resultSet.getString("CustomerName"),
							rowcount, 0); // 改变单元格的值，三个参数分别是值，对应行，对应列
					table.setValueAt(resultSet.getString("CustomerID"),
							rowcount, 1);
					table.setValueAt(resultSet.getString("CustomerPhone"),
							rowcount, 2); // 改变单元格的值，三个参数分别是值，对应行，对应列
					table.setValueAt(resultSet.getString("Customermoney"),
							rowcount, 3);
					table.setValueAt(resultSet.getString("Customeruint"),
							rowcount, 4); // 改变单元格的值，三个参数分别是值，对应行，对应列
					table.setValueAt(resultSet.getString("Customerstate"),
							rowcount, 5);
					bar.setValue(rowcount + 1); // 更新进度条
					Thread.sleep(30); // 线程睡眠30，等待进度条
					rowcount++;
				}
				table.setVisible(true); // 进度条完成时，显示表格数据
				JOptionPane.showMessageDialog(null, "OK");
				bar.setValue(0); // 精度条值为0，回到初始状态
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @Title: ICcardData
	 * @Description: 一个任务类，接收wifi发来的IC卡数据以及往下位机发送数据
	 * @throws
	 * @return:
	 */
	private class ICcardData implements Runnable {
		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				ServerSocket Serversocket = new ServerSocket(1); // 创建一个服务器套接字，端口号1.范围从0到65536
				Socket socket = Serversocket.accept();// 服务器监听链接，侦听并接受到此套接字的连接
				// 数据输入流允许应用程序以与机器无关方式从底层输入流中读取Java的基本数据类型
				if(socket.isConnected())   //判断是否连接到下位机
					JOptionPane.showMessageDialog(null, "已经连接到下位机！");
				DataInputStream inputfromclient = new DataInputStream(
						socket.getInputStream()); // socket.getInputStream()返回此套接字的输入流,如果没有任何字节在套接字上缓冲或者read已经消耗了所有缓冲的字节，则对read的所有后续调用都将抛出IOException
				// 数据输出流允许应用程序以适当方式将Java基本数据类型写入输出流中
				DataOutputStream outputtoclient = new DataOutputStream(
						socket.getOutputStream());// 返回此套接字的输出流,如果创建输出流时发生 I/O
													// 错误或者没有连接套接字，将抛出IOException
				/*以下部分为上位机接收下位机发送的卡号*/
				byte[] CardId = new byte[6]; // 接收6个字节
				StringBuffer cardIdString = new StringBuffer(); // 将6个字节转化为字符串
				byte temp ;
				while (true) {
					temp = inputfromclient.readByte();  //读取第一个字节
					//System.out.println(temp);
					if(temp!=100)  /*说明接收到的是卡号*/
					{
//						System.out.println('A');
						CardId[0] = temp;  //第一个字节已经读出
						cardIdString.append(CardId[0]);  //添加此字符到卡号字符串
						for (int i = 1; i < 6; i++) {
							CardId[i] = inputfromclient.readByte();// 从包含的输入流中读取此操作需要的字节
							if (CardId[i] <= 9)
								cardIdString.append("0" + CardId[i]);
							else
								cardIdString.append(CardId[i]);
						}
					 /*以上部分为上位机接收下位机发送的卡号*/
						System.out.println(cardIdString);
						if (cardIdString.toString().matches("[0-9]{1,11}")) { // 匹配数据库CardID
							try {
								// 获取数据库中对应姓名的结果集
								ResultSet resultSet = DBBean
										.QueryDateFromDatebase("SELECT * FROM AccountMessage WHERE CustomerID = '"
												+ cardIdString.toString() + "'");
								if (resultSet.next()) {
									resultSet.first();
									nameField.setText(resultSet
											.getString("CustomerName"));
									idField.setText(resultSet
											.getString("CustomerID"));
									schoolField.setText(resultSet
											.getString("Customeruint"));
									PhoneField.setText(resultSet.getString(
											"CustomerPhone").trim()); // 数据库中定义的是char(10)当输入不足10个字节时自动用空格补全，
									moneyField.setText(resultSet.getString(
											"CustomerMoney").trim());// 为了防止出现正则不匹配的问题，就用trim()函数将其多余空格去除。
									cardstateField.setText(resultSet
											.getString("Customerstate"));
									cardIdString.delete(0, cardIdString.length());  //清空卡号存放字符串
																		
									/*以下部分为上位机发送下位机余额*/		
	                                char[] Balance =  resultSet.getString(
											"CustomerMoney").trim().toCharArray();
									for (int i = 0; i < Balance.length; i++) {
										if(Balance[i]!='.')
										  outputtoclient.writeByte(Balance[i]-'0');
										else 
										  outputtoclient.writeByte(Balance[i]-'7');  //以便显示小数点
										//outputtoclient.flush();
										System.out.println(Balance[i]);
									}
							   /*以上部分为上位机发送下位机余额*/									
								} else {
									JOptionPane.showMessageDialog(null,
											"你的账户在数据库中不存在！");
								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(null, "你的卡号不符合规则，请先激活！");
						}
					}
					/*接收到的是扣费金额*/
					else {  	
						System.out.println('B');
						if(cardstateField.getText().endsWith("正常"))  //用户想要扣费，刷卡发现卡号状态正常
						{	
							//System.out.println('C');
							int number = inputfromclient.read(CardId);  //这里没有数据就会阻塞。。。卧槽
							System.out.println("个数：" +number);
							if(number!=1)  //说明有扣费金额，而不只是只有100这一个防阻塞的输入
							{	
								for (int i = 1; i < number; i++) {  
									if(CardId[i]==46)
										cardIdString.append('.');
									else
										cardIdString.append(CardId[i]);
								}
								System.out.println(cardIdString);  //显示扣费金额的字符串形式
								if (cardIdString.toString().matches(
										"[0-9]+([.]{1}[0-9]+){0,1}")) {
									System.out.println('C');
									try {
										ResultSet resultSet = DBBean
												.QueryDateFromDatebase("SELECT * FROM AccountMessage WHERE CustomerID = '"
														+ idField.getText() + "'");
										// 得到long类型当前时间
										resultSet.first();
										long L = System.currentTimeMillis();
										// new日期对象
										Date date = new Date(L);
										// 转换提日期输出格式
										SimpleDateFormat dateFormat = new SimpleDateFormat(
												"yyyy-MM-dd HH:mm:ss");
										// 计算出充值之后的卡的余额
										Double currrentmoneyDouble = Double
												.valueOf(resultSet.getString(
														"CustomerMoney").trim())
												- Double.valueOf(cardIdString.toString());
										// 将对应的消费信息插入到数据库
										DBBean.InsertDeleteUpdate("INSERT INTO ReportMessage (OperateTime,CustomerName,Deductmoney,Savemoney,CustomerID,CustomerMoney)"
												+ "VALUES ('"
												+ dateFormat.format(date)
												+ "','"
												+ nameField.getText()
												+ "','"
												+cardIdString.toString()
												+ "','0','"
												+ idField.getText()
												+ "','"
												+ currrentmoneyDouble.toString() + "')");
										// 同时也要更新当前金额到账户相应信息
										DBBean.InsertDeleteUpdate("UPDATE AccountMessage SET CustomerMoney = "
												+ currrentmoneyDouble.toString()
												+ " WHERE CustomerID = '"
												+ idField.getText() + "'");
										JOptionPane.showMessageDialog(null, "扣费成功！");
										moneyField.setText(currrentmoneyDouble.toString().trim());//显示扣费后的余额。
										System.out.println(currrentmoneyDouble.toString().trim());//显示扣费之后的余额
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								} else {
									JOptionPane.showMessageDialog(null, "请输入正确的扣费金额！");
								}
								cardIdString.delete(0, cardIdString.length());  //清除扣费金额存放数据串
							}
						}
						else if(cardstateField.getText().endsWith("挂失")){ //用户有扣费动作但是卡号已经是挂失状态，报警
							outputtoclient.writeChar('A'); //发送'A'字节给下位机，以便报警
							inputfromclient.read(CardId);   //把企图盗刷的扣费金额数据滤除掉(有问题)
							sendmsn(PhoneField.getText().trim(),"您的校园卡出现盗刷，请及时处理！");  //发送短信
						}
						else {
							JOptionPane.showMessageDialog(null, "账户不存在！");
						}
					}
				}
			} catch (IOException ex) {
				System.err.println(ex);
			}
		}
	}
}

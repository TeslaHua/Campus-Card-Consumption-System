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
 * * @author ���� E-mail:
 * 
 * @date ����ʱ�䣺2016��12��30�� ����8:38:53
 * @version 1.0
 * @parameter
 * @since
 * @return
 */

public class HomeScreen extends Sms {

	private JLabel nameLabel = new JLabel("������");
	private JLabel idLabel = new JLabel("���ţ�");
	private JLabel schoolLabel = new JLabel("��λ��");
	private JLabel PhoneLabel = new JLabel("�ֻ��ţ�");
	private JLabel moneyLabel = new JLabel("��");
	private JLabel cardstateLabel = new JLabel("IC��״̬��");
	private JLabel rechargemoneyLabel = new JLabel("��ֵ��");

	private JLabel titleLabel = new JLabel("ˢ�����ѹ���ϵͳ"); // ����
	private JLabel nameremindlLabel = new JLabel("(������5�������ַ�)");
	private JLabel idremindLabel = new JLabel("(������11�������ַ�)");
	private JLabel schoolremindLabel = new JLabel("(������15�������ַ�)");
	private JLabel PhoneremindLabel = new JLabel("(������15�������ַ�)");
	private JLabel moneyremindlLabel = new JLabel("(����������)");
	private JLabel cardstateremindlabel = new JLabel("(��ʧ��������)");

	private JTextField nameField = new JTextField(10);
	public static JTextField idField = new JTextField(6);
	private JTextField schoolField = new JTextField(20);
	private JTextField PhoneField = new JTextField(6);
	private JTextField moneyField = new JTextField(6);
	private JTextField cardstateField = new JTextField(5); // IC��״̬
	private JTextField rechargemoneyField = new JTextField(6);

	private JTextField searchField = new JTextField(10);

	private JButton ReportlossButton = new JButton("��ʧ");
	private JButton retrieveButton = new JButton("���");
	private JButton CancellationButton = new JButton("ע��");
	private JButton rechargebButton = new JButton("��ֵ");

	private JButton searchAccountButton = new JButton("�鿴ȫ���˻�");
	private JButton clearAccountButton = new JButton("��ձ��");

	private JButton viewreportButton = new JButton("�鿴����");
	private JButton openmachineButton = new JButton("������λ��");

	private JRadioButton nameJRadioButton = new JRadioButton("����������");
	private JRadioButton idJRadioButton = new JRadioButton("����������");
	private JPanel jpRadioButtons = new JPanel(new GridLayout(2, 1)); // �������õ�ѡ��ť��
	private ButtonGroup group = new ButtonGroup(); // ����һ����ѡ��ť��

	private JPanel jPanel1 = new JPanel(); // �������ÿ�����Ϣ�����
	private JPanel jPanel2 = new JPanel(new FlowLayout()); // ��������������������
	private JPanel jPanel3 = new JPanel(); // ������ֹ�ײ��ؼ�

	private BackGroundpanel backGroundpanel = new BackGroundpanel(); // ϵͳ�������

	private String[] colnames = { "����", "����", "�ֻ���", "���", "��λ", "״̬" }; // ����һ��������
	private DefaultTableModel model = new DefaultTableModel(colnames, 100); // ��������colnames��Ԫ�صĸ�����������������100��
	private JTable table = new JTable(model);           // ����һ�� JTable��ʹ������ģ��
												        // model��Ĭ�ϵ���ģ�ͺ�Ĭ�ϵ�ѡ��ģ�Ͷ�����г�ʼ����
	private JProgressBar bar = new JProgressBar(0, 100); // ָ����С�����ֵ�Ľ�����
	private JScrollPane scrollpane = new JScrollPane(table); // ����һ����ʾָ��������ݵ�
																// JScrollPane��ֻҪ��������ݳ���table��С�ͻ���ʾˮƽ�ʹ�ֱ��������

	public void homescreen() {

		setLayout(new BorderLayout());
		titleLabel.setBounds(225, -30, 250, 90);
		titleLabel.setFont(new Font("Dialog", Font.ITALIC, 30));
		titleLabel.setForeground(Color.RED);

		jPanel1.setLayout(null);
		jPanel1.setBorder(new TitledBorder("��ʾ��Ϣ"));
		jPanel1.setBounds(18, 30, 650, 210);

		nameLabel.setBounds(30, 20, 50, 20);
		nameField.setBounds(90, 20, 80, 20);
		nameremindlLabel.setBounds(180, 20, 120, 20);
		nameField.setEditable(false);// ���õ����ı����ɱ༭
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

		jPanel2.setBorder(new TitledBorder("��ѯ"));
		jPanel2.setBounds(18, 480, 650, 60);
		jPanel2.add(searchAccountButton);
		jPanel2.add(clearAccountButton);
		jPanel2.add(bar); // ��ӽ�����

		scrollpane.setBorder(new TitledBorder("ȫ���˻���Ϣ"));
		scrollpane.setBounds(18, 240, 650, 220);

		jPanel3.setBounds(18, 540, 650, 55);
		jPanel3.setLayout(new GridLayout(1, 4, 60, 15));
		jPanel3.setBorder(new TitledBorder("����"));

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
		backGroundpanel.add(scrollpane); // ��ӻ���
		backGroundpanel.add(jPanel2);
		backGroundpanel.add(jPanel3);

	}

	// ���������汳��
	public class BackGroundpanel extends JPanel {

		private URL Imageurl = getClass().getResource("fanka.jpg"); // ��������ϵͳ�������汳��ͼƬ
		// ���������� Abstract Window Toolkit
		// ʵ��ʵ�ֵĳ�����,��ȡĬ�Ϲ��߰�,����һ��ͼ�񣬸�ͼ���ָ���ļ��л�ȡ�������ݣ�ͼ���ʽ������ GIF��JPEG �� PNG
		private Image img = Toolkit.getDefaultToolkit().getImage(Imageurl);

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			int WinWidth = getWidth();
			int WinHeight = getHeight();
			int IWidth = img.getWidth(this);
			int IHeight = img.getHeight(this);
			int xindex = (WinWidth - IWidth) / 2;
			int yindex = (WinHeight - IHeight) / 2;
			g.drawImage(img, xindex, yindex, null); // ��xindex,yindex��Ϊ0�Ϳ���ȡ������ͼƬ
		}
	}

	// �¼�������
	public void HandleMainEvent() {

		// ����λ���Խ�����λ��������IC������
		openmachineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ICcardData IC = new ICcardData(); // ����һ��������ʾ���
				Thread thread = new Thread(IC); // Ϊ����������һ���߳�
				thread.start(); // �����߳�
			}
		});
		/*
		 * //���������ʶ����˻���Ϣ�浽���ݿ� saveButton.addActionListener(new ActionListener()
		 * {
		 * 
		 * @Override public void actionPerformed(ActionEvent e){ //TODO
		 * Auto-generated method stub //������Ϣ�����Ҫ��ȫ����������ʽԼ��
		 * if(nameField.getText
		 * ().matches("[^x00-xff]{1,5}")&&idField.getText().matches
		 * ("[0-9]{1,10}") &&schoolField.getText().matches("[^x00-xff]{1,15}")&&
		 * cardField.getText().matches("[0-9]{1,20}")&&
		 * moneyField.getText().matches("[0-9]+([.]{1}[0-9]+){0,1}")) { try {
		 * //���ڼ����˻���Ϣ�Ƿ��Ѿ����ڣ���������ڣ�����һ��next()Ϊfalse��ResultSet ���� ResultSet
		 * resultSet = DBBean.QueryDateFromDatebase(
		 * "SELECT CustomerName FROM AccountMessage WHERE CustomerID = '"
		 * +idField.getText()+"'"); if (!resultSet.next()) {//�����ھͰ���Ϣ�������ݿ�
		 * resultSet.first(); try {
		 * 
		 * DBBean.InsertDeleteUpdate(
		 * "INSERT INTO AccountMessage  (CustomerName,CustomerID,Customercard,CustomerMoney,Customeruint,Customerstate)"
		 * +
		 * "VALUES ('"+nameField.getText()+"','"+idField.getText()+"','"+cardField
		 * .getText()+"','"+moneyField.getText()+"','"+
		 * schoolField.getText()+"','δ��ʧ')");
		 * JOptionPane.showMessageDialog(null,"�˻���Ϣ����ɹ���"); } catch
		 * (SQLException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } } else { //������ھ���ʾ�˻��Ѵ���
		 * JOptionPane.showMessageDialog(null,"��������˻��Ѵ��ڣ�"); } } catch
		 * (SQLException e2) { // TODO Auto-generated catch block
		 * e2.printStackTrace(); } }else {
		 * JOptionPane.showMessageDialog(null,"����˺���Ϣ������Ҫ�� ��"); } } });
		 */
		// �����������˻�
		nameJRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (searchField.getText().matches("[^x00-xff]{1,5}")) {
					try {
						// ��ȡ���ݿ��ж�Ӧ�����Ľ����
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
									"CustomerPhone").trim()); // ���ݿ��ж������char(10)�����벻��10���ֽ�ʱ�Զ��ÿո�ȫ��
							moneyField.setText(resultSet.getString(
									"CustomerMoney").trim());// Ϊ�˷�ֹ��������ƥ������⣬����trim()�����������ո�ȥ����
							cardstateField.setText(resultSet
									.getString("Customerstate"));
						} else {
							JOptionPane.showMessageDialog(null, "���������˻������ڣ�");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "�����벻����5�������ַ�");
				}
			}
		});
		// �����Ų����˻�
		idJRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (searchField.getText().matches("[0-9]{1,11}")) {
					try {
						// ��ȡ���ݿ��ж�Ӧ�����Ľ����
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
									"CustomerPhone").trim()); // ���ݿ��ж������char(10)�����벻��10���ֽ�ʱ�Զ��ÿո�ȫ��
							moneyField.setText(resultSet.getString(
									"CustomerMoney").trim());// Ϊ�˷�ֹ��������ƥ������⣬����trim()�����������ո�ȥ����
							cardstateField.setText(resultSet
									.getString("Customerstate"));
						} else {
							JOptionPane.showMessageDialog(null, "���������˻������ڣ�");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "�����벻����10�������ַ�");
				}
			}
		});
		// ��������ȫ���˻���Ϣ��ť
		searchAccountButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ShowModel show = new ShowModel(); // ����һ��������ʾ���
				Thread thread = new Thread(show); // Ϊ����������һ���߳�
				thread.start(); // �����߳�
			}
		});
		// �������������Ϣ��ť
		clearAccountButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stubs
				model = new DefaultTableModel(colnames, 100);
				table.setModel(model);
			}
		});
		// �����˻���ʧ��ť
		ReportlossButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (DBBean
							.InsertDeleteUpdate("UPDATE AccountMessage SET Customerstate = '��ʧ' WHERE CustomerID = '"
									+ idField.getText() + "'")) {
					    cardstateField.setText("��ʧ");  //��ʾ��ʧ���״̬
						JOptionPane.showMessageDialog(null, "����˻������ǹ�ʧ״̬");
					}
					else {
						JOptionPane.showMessageDialog(null, "���Ȳ�ѯ��Ҫִ�е��˻���");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		// �����ʧ״̬
		retrieveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if (DBBean
							.InsertDeleteUpdate("UPDATE AccountMessage SET Customerstate = '����' WHERE CustomerID = '"
									+ idField.getText() + "'")) {
					    cardstateField.setText("����");   //��ʾ��Һ��״̬
						JOptionPane.showMessageDialog(null, "����˻�����������״̬");
					}
					else {
						JOptionPane.showMessageDialog(null, "���Ȳ�ѯ��Ҫִ�е��˻���");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		// ע���˻��Ĵ����¼�
		CancellationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if (DBBean
							.InsertDeleteUpdate("DELETE FROM AccountMessage  WHERE CustomerID = '"
									+ idField.getText() + "'")) {
						JOptionPane.showMessageDialog(null, "����˻�ע����ɣ�");						
						//�����ʾ��
						nameField.setText("");
						idField.setText("");
						schoolField.setText("");
						PhoneField.setText(""); 
						moneyField.setText(" ");
						cardstateField.setText("");						
					}
					else {
						JOptionPane.showMessageDialog(null, "���Ȳ�ѯ��Ҫִ�е��˻���");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		// ��ֵ��ť�����¼�
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
						if (resultSet.next()) { // ˵����������˻���Ϣ
							System.out.println(cardstateField.getText());
							if(cardstateField.getText().endsWith("����"))  //��������³�ֵ
							{	resultSet.first();
								// �õ�long���͵�ǰʱ��
								long L = System.currentTimeMillis();
								// new���ڶ���
								Date date = new Date(L);
								// ת�������������ʽ
								SimpleDateFormat dateFormat = new SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss");
								// �������ֵ֮��Ŀ������
								Double currrentmoneyDouble = Double
										.valueOf(resultSet.getString(
												"CustomerMoney").trim())
										+ Double.valueOf(rechargemoneyField
												.getText());
								// ����Ӧ��������Ϣ���뵽���ݿ�
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
								// ͬʱҲҪ���µ�ǰ���˻���Ӧ��Ϣ
								DBBean.InsertDeleteUpdate("UPDATE AccountMessage SET CustomerMoney = "
										+ currrentmoneyDouble.toString()
										+ " WHERE CustomerID = '"
										+ idField.getText() + "'");
								JOptionPane.showMessageDialog(null, "��ֵ�ɹ���");
								moneyField.setText(currrentmoneyDouble.toString().trim());//��ʾ��ֵ�����
							}
							else {
								JOptionPane.showMessageDialog(null, "IC���ѹ�ʧ�����ɳ�ֵ��"); //��ֹ��ʧ���ֵ
							}
						} else {
							JOptionPane.showMessageDialog(null, "��Ҫ��ֵ���˻������ڣ�");  //��ֹע�����ֵ
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "�������������֣�");
				}
				rechargemoneyField.setText(" ");
			}
		});
		// �鿴�û����ѱ���
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
	 * @Description: һ�������࣬ʵ����ʾ���ݱ��Ĺ���
	 * @throws
	 * @return:
	 */
	private class ShowModel implements Runnable {
		int rowcount = 0;

		public void run() {
			try {
				table.setVisible(false); // ������û�����ʱ������ʾ�������
				ResultSet resultSet = DBBean
						.QueryDateFromDatebase("select * from AccountMessage"); // ��ʼ��ѯ�˻���Ϣ���ѽ��������
				resultSet.last(); // ֱ��ִ����������������һ��
				bar.setMaximum(resultSet.getRow()); // ���Եõ�������������������Դ����ý����������ֵ
				model.setRowCount(resultSet.getRow());// �Դ�����table��������
				resultSet.beforeFirst();// ����ִ�е���һ�е�ǰһ�У��Ա��ѯ������ļ�������
				while (resultSet.next()) {
					table.setValueAt(resultSet.getString("CustomerName"),
							rowcount, 0); // �ı䵥Ԫ���ֵ�����������ֱ���ֵ����Ӧ�У���Ӧ��
					table.setValueAt(resultSet.getString("CustomerID"),
							rowcount, 1);
					table.setValueAt(resultSet.getString("CustomerPhone"),
							rowcount, 2); // �ı䵥Ԫ���ֵ�����������ֱ���ֵ����Ӧ�У���Ӧ��
					table.setValueAt(resultSet.getString("Customermoney"),
							rowcount, 3);
					table.setValueAt(resultSet.getString("Customeruint"),
							rowcount, 4); // �ı䵥Ԫ���ֵ�����������ֱ���ֵ����Ӧ�У���Ӧ��
					table.setValueAt(resultSet.getString("Customerstate"),
							rowcount, 5);
					bar.setValue(rowcount + 1); // ���½�����
					Thread.sleep(30); // �߳�˯��30���ȴ�������
					rowcount++;
				}
				table.setVisible(true); // ���������ʱ����ʾ�������
				JOptionPane.showMessageDialog(null, "OK");
				bar.setValue(0); // ������ֵΪ0���ص���ʼ״̬
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @Title: ICcardData
	 * @Description: һ�������࣬����wifi������IC�������Լ�����λ����������
	 * @throws
	 * @return:
	 */
	private class ICcardData implements Runnable {
		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				ServerSocket Serversocket = new ServerSocket(1); // ����һ���������׽��֣��˿ں�1.��Χ��0��65536
				Socket socket = Serversocket.accept();// �������������ӣ����������ܵ����׽��ֵ�����
				// ��������������Ӧ�ó�����������޹ط�ʽ�ӵײ��������ж�ȡJava�Ļ�����������
				if(socket.isConnected())   //�ж��Ƿ����ӵ���λ��
					JOptionPane.showMessageDialog(null, "�Ѿ����ӵ���λ����");
				DataInputStream inputfromclient = new DataInputStream(
						socket.getInputStream()); // socket.getInputStream()���ش��׽��ֵ�������,���û���κ��ֽ����׽����ϻ������read�Ѿ����������л�����ֽڣ����read�����к������ö����׳�IOException
				// �������������Ӧ�ó������ʵ���ʽ��Java������������д���������
				DataOutputStream outputtoclient = new DataOutputStream(
						socket.getOutputStream());// ���ش��׽��ֵ������,������������ʱ���� I/O
													// �������û�������׽��֣����׳�IOException
				/*���²���Ϊ��λ��������λ�����͵Ŀ���*/
				byte[] CardId = new byte[6]; // ����6���ֽ�
				StringBuffer cardIdString = new StringBuffer(); // ��6���ֽ�ת��Ϊ�ַ���
				byte temp ;
				while (true) {
					temp = inputfromclient.readByte();  //��ȡ��һ���ֽ�
					//System.out.println(temp);
					if(temp!=100)  /*˵�����յ����ǿ���*/
					{
//						System.out.println('A');
						CardId[0] = temp;  //��һ���ֽ��Ѿ�����
						cardIdString.append(CardId[0]);  //��Ӵ��ַ��������ַ���
						for (int i = 1; i < 6; i++) {
							CardId[i] = inputfromclient.readByte();// �Ӱ������������ж�ȡ�˲�����Ҫ���ֽ�
							if (CardId[i] <= 9)
								cardIdString.append("0" + CardId[i]);
							else
								cardIdString.append(CardId[i]);
						}
					 /*���ϲ���Ϊ��λ��������λ�����͵Ŀ���*/
						System.out.println(cardIdString);
						if (cardIdString.toString().matches("[0-9]{1,11}")) { // ƥ�����ݿ�CardID
							try {
								// ��ȡ���ݿ��ж�Ӧ�����Ľ����
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
											"CustomerPhone").trim()); // ���ݿ��ж������char(10)�����벻��10���ֽ�ʱ�Զ��ÿո�ȫ��
									moneyField.setText(resultSet.getString(
											"CustomerMoney").trim());// Ϊ�˷�ֹ��������ƥ������⣬����trim()�����������ո�ȥ����
									cardstateField.setText(resultSet
											.getString("Customerstate"));
									cardIdString.delete(0, cardIdString.length());  //��տ��Ŵ���ַ���
																		
									/*���²���Ϊ��λ��������λ�����*/		
	                                char[] Balance =  resultSet.getString(
											"CustomerMoney").trim().toCharArray();
									for (int i = 0; i < Balance.length; i++) {
										if(Balance[i]!='.')
										  outputtoclient.writeByte(Balance[i]-'0');
										else 
										  outputtoclient.writeByte(Balance[i]-'7');  //�Ա���ʾС����
										//outputtoclient.flush();
										System.out.println(Balance[i]);
									}
							   /*���ϲ���Ϊ��λ��������λ�����*/									
								} else {
									JOptionPane.showMessageDialog(null,
											"����˻������ݿ��в����ڣ�");
								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(null, "��Ŀ��Ų����Ϲ������ȼ��");
						}
					}
					/*���յ����ǿ۷ѽ��*/
					else {  	
						System.out.println('B');
						if(cardstateField.getText().endsWith("����"))  //�û���Ҫ�۷ѣ�ˢ�����ֿ���״̬����
						{	
							//System.out.println('C');
							int number = inputfromclient.read(CardId);  //����û�����ݾͻ������������Բ�
							System.out.println("������" +number);
							if(number!=1)  //˵���п۷ѽ�����ֻ��ֻ��100��һ��������������
							{	
								for (int i = 1; i < number; i++) {  
									if(CardId[i]==46)
										cardIdString.append('.');
									else
										cardIdString.append(CardId[i]);
								}
								System.out.println(cardIdString);  //��ʾ�۷ѽ����ַ�����ʽ
								if (cardIdString.toString().matches(
										"[0-9]+([.]{1}[0-9]+){0,1}")) {
									System.out.println('C');
									try {
										ResultSet resultSet = DBBean
												.QueryDateFromDatebase("SELECT * FROM AccountMessage WHERE CustomerID = '"
														+ idField.getText() + "'");
										// �õ�long���͵�ǰʱ��
										resultSet.first();
										long L = System.currentTimeMillis();
										// new���ڶ���
										Date date = new Date(L);
										// ת�������������ʽ
										SimpleDateFormat dateFormat = new SimpleDateFormat(
												"yyyy-MM-dd HH:mm:ss");
										// �������ֵ֮��Ŀ������
										Double currrentmoneyDouble = Double
												.valueOf(resultSet.getString(
														"CustomerMoney").trim())
												- Double.valueOf(cardIdString.toString());
										// ����Ӧ��������Ϣ���뵽���ݿ�
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
										// ͬʱҲҪ���µ�ǰ���˻���Ӧ��Ϣ
										DBBean.InsertDeleteUpdate("UPDATE AccountMessage SET CustomerMoney = "
												+ currrentmoneyDouble.toString()
												+ " WHERE CustomerID = '"
												+ idField.getText() + "'");
										JOptionPane.showMessageDialog(null, "�۷ѳɹ���");
										moneyField.setText(currrentmoneyDouble.toString().trim());//��ʾ�۷Ѻ����
										System.out.println(currrentmoneyDouble.toString().trim());//��ʾ�۷�֮������
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								} else {
									JOptionPane.showMessageDialog(null, "��������ȷ�Ŀ۷ѽ�");
								}
								cardIdString.delete(0, cardIdString.length());  //����۷ѽ�������ݴ�
							}
						}
						else if(cardstateField.getText().endsWith("��ʧ")){ //�û��п۷Ѷ������ǿ����Ѿ��ǹ�ʧ״̬������
							outputtoclient.writeChar('A'); //����'A'�ֽڸ���λ�����Ա㱨��
							inputfromclient.read(CardId);   //����ͼ��ˢ�Ŀ۷ѽ�������˳���(������)
							sendmsn(PhoneField.getText().trim(),"����У԰�����ֵ�ˢ���뼰ʱ����");  //���Ͷ���
						}
						else {
							JOptionPane.showMessageDialog(null, "�˻������ڣ�");
						}
					}
				}
			} catch (IOException ex) {
				System.err.println(ex);
			}
		}
	}
}

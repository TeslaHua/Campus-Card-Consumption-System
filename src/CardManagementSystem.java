/** 
 * @author  ���� E-mail: 
 * @date ����ʱ�䣺2016��12��30�� ����8:21:34 
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
 * @date: 2016��12��30�� ����8:01:09
 */
public class CardManagementSystem extends HomeScreen {

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;
	private static boolean flag = false;
	private String passwordString = new String(); // ������ղ�ѯ������
	private ResultSet resultSet = null; // ��ѯ�Ľ����
	private JButton LoginButton = new JButton("��¼");
	private JButton CancelbButton1 = new JButton("ȡ��");
	private JButton ModifyPasswordButton = new JButton("�޸�����");
	private JButton ConfirmButton = new JButton("ȷ��");
	private JButton CancelbButton2 = new JButton("ȡ��");

	private JLabel PasswordLabel = new JLabel("��λ���룺");
	private JLabel NewPasswordLable = new JLabel("���������룺");
	private JLabel ConfirmPasswordLable = new JLabel("ȷ�������룺");
	private JLabel RemindMessageLabel = new JLabel("(  ����Ϊ��λ����  !!)");
	private JPasswordField PasswordField = new JPasswordField(6);
	private JPasswordField modifyPasswordField = new JPasswordField(6);
	private JPasswordField ConfirmPasswordField = new JPasswordField(6);

	private JPanel loginPanel = new JPanel(); // ��¼���
	private JPanel modifyPanel = new JPanel(); // �޸����

	public CardManagementSystem() {

		loginPanel.setLayout(null);
		PasswordLabel.setBounds(60, 15, 65, 40);
		PasswordField.setBounds(150, 25, 80, 25);
		PasswordField.setEchoChar('*'); // ���û����ַ�

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

		loginPanel.setBorder(new TitledBorder("��¼"));

		modifyPanel.setLayout(null);
		NewPasswordLable.setBounds(60, 5, 100, 40);
		modifyPasswordField.setBounds(150, 15, 80, 25);

		modifyPasswordField.setEchoChar('*'); // ���û����ַ�
		ConfirmPasswordLable.setBounds(60, 50, 100, 40);
		ConfirmPasswordField.setBounds(150, 55, 80, 25);

		ConfirmPasswordField.setEchoChar('*'); // ���û����ַ�
		ConfirmButton.setBounds(80, 100, 60, 25);
		CancelbButton2.setBounds(150, 100, 60, 25);

		modifyPanel.add(NewPasswordLable);
		modifyPanel.add(modifyPasswordField);
		modifyPanel.add(ConfirmPasswordLable);
		modifyPanel.add(ConfirmPasswordField);
		modifyPanel.add(ConfirmButton);
		modifyPanel.add(CancelbButton2);

		modifyPanel.setBorder(new TitledBorder("�޸�����"));

		setLayout(new GridLayout(2, 1));
		add(loginPanel); // ��ӵ�¼���
		add(modifyPanel); // ����޸����
	}

	// �¼�������
	public void HandleLoginEvent() {
		// ��¼��ť�¼�����,������������Ƿ���Ч
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
						passwordString = resultSet.getString("password"); // �ڽ�����а���������ȡ����
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (String.valueOf(PasswordField.getPassword()).equals(
						passwordString)) { // ��ǰ�������������ݿ���������ͬ
					HomeScreen homeScreen = new HomeScreen();
					homeScreen.setSize(700, 680);
					homeScreen.setTitle("������");
					homeScreen.setLocationRelativeTo(null);
					homeScreen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ָ����ܹر�ʱ�Ĳ���
					homeScreen.setVisible(true);
					homeScreen.homescreen();
					homeScreen.HandleMainEvent();
				}else{
					JOptionPane.showMessageDialog(null, "������������������������룡");
					PasswordField.setText(""); // ���������Ա���������
				}
			}
		});

		// ȡ����������
		CancelbButton1.addActionListener(new ActionListener() { // ȡ����������

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						PasswordField.setText(""); // ���������Ա���������
					}
				});

		// �����޸����밴ť
		ModifyPasswordButton.addActionListener(new ActionListener() { // �޸�����
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
										.getString("password"); // �ڽ�����а���������ȡ����
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if (String.valueOf(PasswordField.getPassword()).equals(
								passwordString)) { // ��ǰ�������������ݿ���������ͬ
							JOptionPane.showMessageDialog(null,
									"������������������������ ��");
							flag = true; // ��λ֮��ȷ�ϰ�ť����Ч
						} else {
							JOptionPane.showMessageDialog(null,
									"�������ԭʼ�����������������룡");
							PasswordField.setText(""); // ���������Ա���������
						}
					}
				});
		// ȷ���޸�����,�ұ����������ԭʼ������ȷ���������Ч
		ConfirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (String.valueOf(modifyPasswordField.getPassword()).equals(
						String.valueOf(ConfirmPasswordField.getPassword()))
						&& String.valueOf(modifyPasswordField.getPassword())
								.matches("[0-9]{6}")
						&& String.valueOf(ConfirmPasswordField.getPassword())
								.matches("[0-9]{6}") && flag) { // flag��λ֮��֤�û��������ԭʼ����
					try {
						// ��������������ݿ���ԭ������
						if (DBBean
								.InsertDeleteUpdate("UPDATE Passwordtable SET  password = "
										+ String.valueOf(modifyPasswordField
												.getPassword())
										+ " WHERE passwordID =1")) {
							JOptionPane.showMessageDialog(null, "�����޸ĳɹ��������µ�¼"); // ����ʹ���������¼������
							PasswordField.setText(""); // ���������Ա���������
							modifyPasswordField.setText("");
							ConfirmPasswordField.setText("");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "��û��ȷ��ԭʼ��������������ʽ��ƥ�䣡");
					modifyPasswordField.setText("");
					ConfirmPasswordField.setText("");
				}
				flag = false; // ����־λȡ�ǣ��Ա���һ�δ���
			}
		});
		// ȡ���޸�����
		CancelbButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				modifyPasswordField.setText("");
				ConfirmPasswordField.setText("");
			}
		});
	}

	// ���������
	public static void main(String[] args) {
		CardManagementSystem login = new CardManagementSystem();
		login.setSize(300, 300);
		login.setTitle("��¼����");
		login.setLocationRelativeTo(null);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ָ����ܹر�ʱ�Ĳ���
		login.setVisible(true);
		DBBean.ConnectionDataBase(); // ��ʼ�������ݿ�
		login.HandleLoginEvent();
	}
}

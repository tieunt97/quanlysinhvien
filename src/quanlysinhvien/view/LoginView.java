package quanlysinhvien.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class LoginView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfTaiKhoan;
	private JPasswordField pwMatKhau;
	private JButton btnDangNhap, btnHuy, btnThoat;

	public LoginView() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 547, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Tài khoản:");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(117, 97, 88, 30);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Mật khẩu:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(117, 148, 88, 30);
		contentPane.add(lblPassword);
		
		tfTaiKhoan = new JTextField();
		tfTaiKhoan.setBounds(211, 97, 212, 30);
		contentPane.add(tfTaiKhoan);
		tfTaiKhoan.setColumns(10);
		
		pwMatKhau = new JPasswordField();
		pwMatKhau.setBounds(211, 148, 212, 30);
		contentPane.add(pwMatKhau);
		
		btnDangNhap = new JButton("Đăng nhập");
		btnDangNhap.setForeground(Color.WHITE);
		btnDangNhap.setBackground(new Color(44, 62, 80));
		btnDangNhap.setBounds(310, 212, 113, 34);
		contentPane.add(btnDangNhap);
		
		btnHuy = new JButton("Hủy");
		btnHuy.setForeground(Color.WHITE);
		btnHuy.setBackground(new Color(44, 62, 80));
		btnHuy.setBounds(187, 212, 113, 34);
		contentPane.add(btnHuy);
		
		btnThoat = new JButton("");
		btnThoat.setToolTipText("Thoát");
		btnThoat.setIcon(new ImageIcon(this.getClass().getResource("/logout.png")));
		btnThoat.setBounds(507, 11, 24, 23);
		contentPane.add(btnThoat);
		
		JLabel lblHThngQun = new JLabel("Hệ thống quản lý sinh viên - nhóm 9");
		lblHThngQun.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblHThngQun.setBounds(10, 0, 290, 27);
		contentPane.add(lblHThngQun);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(this.getClass().getResource("/bgLogin.jpg")));
		label.setBounds(0, 0, 541, 341);
		contentPane.add(label);
	}

	public JTextField getTfTaiKhoan() {
		return tfTaiKhoan;
	}

	public JPasswordField getPwMatKhau() {
		return pwMatKhau;
	}

	public JButton getBtnDangNhap() {
		return btnDangNhap;
	}

	public JButton getBtnHuy() {
		return btnHuy;
	}

	public JButton getBtnThoat() {
		return btnThoat;
	}

}

package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import quanlysinhvien.view.PanelDoiMatKhauView;

public class DoiMatKhauController {
	private PanelDoiMatKhauView doiMatKhau;
	private JTextField tfTaiKhoan;
	private JPasswordField pwMatKhauCu, pwMatKhauMoi, pwXacNhan;
	private JButton btnThayDoi;
	private String tenTaiKhoan = "tieunt";
	private String matKhau = "tnt1827";
	
	public DoiMatKhauController(PanelDoiMatKhauView doiMatKhau) {
		this.doiMatKhau = doiMatKhau;
		this.tfTaiKhoan = doiMatKhau.getTfTaiKhoan();
		tfTaiKhoan.setText(tenTaiKhoan);
		tfTaiKhoan.setEnabled(false);
		this.pwMatKhauCu = doiMatKhau.getPwMatKhauCu();
		this.pwMatKhauMoi = doiMatKhau.getPwMatKhauMoi();
		this.pwXacNhan = doiMatKhau.getPwXacNhan();
		this.btnThayDoi = doiMatKhau.getBtnThayDoi();
		
		setAction();
	}
	
	private void setAction() {
		btnThayDoi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String matKhauCu = pwMatKhauCu.getText().trim();
				String matKhauMoi = pwMatKhauMoi.getText().trim();
				String xacNhan  = pwXacNhan.getText().trim();
				if(matKhauCu.equals(matKhau)) {
					if(matKhauMoi.equals(xacNhan)) {
						matKhau = matKhauMoi;
						JOptionPane.showMessageDialog(null, "Thay đổi mật khẩu thành công.");
						cancel();
					}else {
						JOptionPane.showMessageDialog(null, "Xác nhận mật khẩu sai", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Mật khẩu sai", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	private void cancel() {
		pwMatKhauCu.setText("");
		pwMatKhauMoi.setText("");
		pwXacNhan.setText("");
	}
	
}

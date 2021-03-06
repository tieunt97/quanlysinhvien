package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.SinhVien;
import quanlysinhvien.view.PanelDoiMatKhauView;

public class DoiMatKhauController {
	private JTextField tfTaiKhoan;
	private JPasswordField pwMatKhauCu, pwMatKhauMoi, pwXacNhan;
	private JButton btnThayDoi;
	private SinhVien sv;
	private Workbook workbook;
	
	public DoiMatKhauController(PanelDoiMatKhauView doiMatKhau, SinhVien sv) {
		this.tfTaiKhoan = doiMatKhau.getTfTaiKhoan();
		tfTaiKhoan.setText(sv.getIdSinhVien());
		tfTaiKhoan.setEnabled(false);
		this.pwMatKhauCu = doiMatKhau.getPwMatKhauCu();
		this.pwMatKhauMoi = doiMatKhau.getPwMatKhauMoi();
		this.pwXacNhan = doiMatKhau.getPwXacNhan();
		this.btnThayDoi = doiMatKhau.getBtnThayDoi();
		this.sv = sv;
		
		setAction();
	}
	
	private void setAction() {
		btnThayDoi.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				String matKhauCu = pwMatKhauCu.getText().trim().toUpperCase();
				String matKhauMoi = pwMatKhauMoi.getText().trim().toUpperCase();
				String xacNhan  = pwXacNhan.getText().trim().toUpperCase();
				if(matKhauCu.equals(sv.getTaiKhoan().getMatKhau())) {
					if(matKhauMoi.length() < 8) {
						JOptionPane.showMessageDialog(null, "Mật khẩu mới tối thiểu 8 ký tự", "Warning", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if(matKhauMoi.equals(xacNhan)) {
						sv.getTaiKhoan().setMatKhau(matKhauMoi);
						boolean ck;
						try {
							ck = changePW(sv.getTaiKhoan().getTaiKhoan(), matKhauMoi);
							if(ck) {
								JOptionPane.showMessageDialog(null, "Thay đổi mật khẩu thành công.");
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
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
	
	private boolean changePW(String taiKhoan, String matKhau) throws IOException {
		String fileName = "quanlysinhvien\\dsTaiKhoan.xlsx";
		boolean ck = false;
		FileInputStream fin = new FileInputStream(new File(fileName));
		workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		
		Row nextRow;
		if (iterator.hasNext())
			nextRow = iterator.next(); // loại bỏ dòng tiêu đề
		while(iterator.hasNext()) {
			nextRow = iterator.next();
			Cell cellTK = nextRow.getCell(1);
			String tk = cellTK.getStringCellValue();
			if(tk.equalsIgnoreCase(taiKhoan)) {
				Cell cellMK = nextRow.getCell(2);
				cellMK.setCellValue(matKhau);
				ck = true;
				break;
			}
		}
		
		fin.close();
		
		FileOutputStream fout = new FileOutputStream(new File(fileName));
		workbook.write(fout);
		fout.close();
		return ck;
	}
	
}

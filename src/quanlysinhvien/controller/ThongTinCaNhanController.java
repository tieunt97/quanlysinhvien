package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.QuanLy;
import quanlysinhvien.model.SinhVien;
import quanlysinhvien.model.SinhVienTinChi;
import quanlysinhvien.model.TaiKhoan;
import quanlysinhvien.view.PanelThongTinCaNhanView;

public class ThongTinCaNhanController {
	private PanelThongTinCaNhanView thongTinCaNhan;
	private JLabel gtIdSinhVien, gtHoTen, gtNgaySinh, gtLop;
	private JTextField tfEmail, tfSoDT, tfDiaChi;
	private JButton btnCapNhat;
	private String fileName;
	private SinhVien sv;

	public ThongTinCaNhanController(PanelThongTinCaNhanView thongTinCaNhan, SinhVien sv) {
		this.thongTinCaNhan = thongTinCaNhan;
		fileName = (sv instanceof SinhVienTinChi)?"quanlysinhvien\\sinhvientinchi\\dsSinhVienTC.xlsx":
			"quanlysinhvien\\sinhviennienche\\dsSinhVienNC.xlsx";
		this.gtIdSinhVien = thongTinCaNhan.getGtIdSinhVien();
		this.gtHoTen = thongTinCaNhan.getGtHoTen();
		this.gtNgaySinh = thongTinCaNhan.getGtNgaySinh();
		this.gtLop = thongTinCaNhan.getGtLop();
		this.tfEmail = thongTinCaNhan.getTfEmail();
		this.tfSoDT = thongTinCaNhan.getTfSoDT();
		this.tfDiaChi = thongTinCaNhan.getTfDiaChi();
		this.btnCapNhat = thongTinCaNhan.getBtnCapNhat();

		setThongTin();
		setAction();
	}

	//cập nhật thông tin sinh viên lên view
	private void setThongTin() {
		gtHoTen.setText(sv.getHoTen());
		gtIdSinhVien.setText(sv.getIdSinhVien());
		gtLop.setText(sv.getTenLop());
		gtNgaySinh.setText(sv.getNgaySinh());
	}

	//lấy dữ liệu cập nhật của sinh viên
	private boolean getCapNhat() {
		String email = tfEmail.getText();
		String soDT = tfSoDT.getText();
		String diaChi = tfDiaChi.getText();
		if (email.equals("") || soDT.equals("") || diaChi.equals("")) {
			JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		sv.setEmail(email);
		sv.setDiaChi(diaChi);
		sv.setSoDT(soDT);
		return true;
	}

	//bắt sự kiện
	private void setAction() {
		btnCapNhat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(getCapNhat()) {
					boolean ck = false;
					try {
						ck = updateSV(sv);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (ck)
						JOptionPane.showMessageDialog(null, "Cập nhật thành công");
				}
				cancel();
			}
		});
	}


	//cập nhật dữ liêu sinh viên trong file
	private boolean updateSV(SinhVien sv) throws IOException {
		boolean ck = false;
		String id = gtIdSinhVien.getText();
		FileInputStream fin = new FileInputStream(new File(fileName));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		Row nextRow;
		if (iterator.hasNext())
			nextRow = iterator.next();
		while (iterator.hasNext()) {
			nextRow = iterator.next();
			Cell cell = nextRow.getCell(1);
			String idSV = cell.getStringCellValue();
			if (idSV.equals(id)) {
				cell = nextRow.createCell(7);
				cell.setCellValue(sv.getEmail());
				cell = nextRow.createCell(8);
				cell.setCellValue(sv.getSoDT());
				cell = nextRow.createCell(9);
				cell.setCellValue(sv.getDiaChi());
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

	//reset input
	private void cancel() {
		tfDiaChi.setText("");
		tfEmail.setText("");
		tfSoDT.setText("");
	}
}

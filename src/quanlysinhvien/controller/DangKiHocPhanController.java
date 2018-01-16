package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.DangKyHocPhan;
import quanlysinhvien.model.HocPhan;
import quanlysinhvien.model.QuanLy;
import quanlysinhvien.model.SinhVien;
import quanlysinhvien.model.SinhVienNienChe;
import quanlysinhvien.model.SinhVienTinChi;
import quanlysinhvien.view.PanelDangKiHocPhanView;

public class DangKiHocPhanController {
	private PanelDangKiHocPhanView panelDkiHP;
	private JComboBox<String> hocKy;
	private JTextField tfDangky;
	private JButton btnDangKy, btnXoaHP, btnGuiDangKy;
	private JTable table;
	private JCheckBox checkBox;
	private JLabel lblSum;
	private int tongSoTinChiDaDangKy = 0;
	private QuanLy quanLy;
	private SinhVien sv;
	private String fileName;
	private ArrayList<DangKyHocPhan> dsHPDangKy;

	public DangKiHocPhanController(PanelDangKiHocPhanView panelDkiHP, QuanLy quanLy, SinhVien sv) {
		this.panelDkiHP = panelDkiHP;
		this.quanLy = quanLy;
		this.sv = sv;
		this.fileName = (sv instanceof SinhVienTinChi)
				? "quanlysinhvien/sinhvientinchi/" + sv.getIdSinhVien() + "/dsHPDangKy.xlsx"
				: "quanlysinhvien/sinhviennienche/" + sv.getIdSinhVien() + "/dsHPDangKy.xlsx";
		this.hocKy = panelDkiHP.getHocKy();
		this.tfDangky = panelDkiHP.getTfDangky();
		this.btnDangKy = panelDkiHP.getBtnDangKy();
		this.btnXoaHP = panelDkiHP.getBtnXoaHP();
		this.btnGuiDangKy = panelDkiHP.getBtnGuiDangKy();
		this.table = panelDkiHP.getTable();
		this.checkBox = panelDkiHP.getCheckBox();
		this.lblSum = panelDkiHP.getLbSum();

		/* load thông tin cũ vào bảng */
		loadBangDangKyHocPhan((String) hocKy.getItemAt(0));

		addEvents();
	}

	private void loadBangDangKyHocPhan(String hocKy) {
		// TODO Auto-generated method stub
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		/* Xóa dữ liệu trên bảng */
		while (model.getRowCount() != 0) {
			model.removeRow(0);
		}

		Object[] obj = new Object[6];
		dsHPDangKy = sv.getDSHPDangKy(hocKy);
		int count = dsHPDangKy.size();
		for (int i = 0; i < count; i++) {
			obj[0] = dsHPDangKy.get(i).getHocPhan().getIdHocPhan();
			obj[1] = dsHPDangKy.get(i).getHocPhan().getTenHP();
			obj[2] = dsHPDangKy.get(i).getNgayDangKy();
			obj[3] = "Thành công";
			obj[4] = dsHPDangKy.get(i).getHocPhan().getSoTinChi();
			obj[5] = false;

			model.addRow(obj);
		}

		tongSoTinChiDaDangKy = dsHPDangKy.size();
		lblSum.setText(tongSoTinChiDaDangKy + "");
	}

	private void addEvents() {
		// TODO Auto-generated method stub
		hocKy.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				String hKy = (String) hocKy.getSelectedItem();

				/* load thông tin cũ vào bảng */
				loadBangDangKyHocPhan(hKy);;
			}
		});

		checkBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int count = model.getRowCount();
				for (int row = 0; row < count; row++) {
					if (checkBox.isSelected()) {
						model.setValueAt(true, row, 5);
					} else {
						model.setValueAt(false, row, 5);
					}
				}
			}
		});

		btnDangKy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DangKiHocPhan();
				return;
			}

		});

		tfDangky.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					DangKiHocPhan();
					return;
				}
			}
		});

		btnXoaHP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				xoaHocPhan();
				return;
			}
		});
		// su kien gui dang ki
		btnGuiDangKy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				guiDangKy();
				return;
			}
		});
	}

	private void xoaHocPhan() {
		// TODO Auto-generated method stub
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int row = 0;
		while (row < model.getRowCount()) {
			Boolean check = Boolean.valueOf(model.getValueAt(row, 5).toString());
			if (check == true) {
				String idHocPhan = model.getValueAt(row, 0).toString();
				/*
				 * Xóa học phần trong ds học phần đăng kí. nếu xóa thành công thì xóa
				 * trên bảng đăng kí
				 */
				if (sv.xoaHPDangKy((String) hocKy.getSelectedItem(), idHocPhan)) {
					model.removeRow(row);
				} else {
					JOptionPane.showMessageDialog(null, "Không tìm thấy mã học phần: " + idHocPhan);
					return;
				}
				continue;
			}
			row++;
		}
		tongSoTinChiDaDangKy = dsHPDangKy.size();
		lblSum.setText("" + tongSoTinChiDaDangKy);
	}

	private void updataTableDanhSachDangKy() {
		// TODO Auto-generated method stub
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int count = model.getRowCount();
		for (int i = 0; i < count; i++) {
			model.setValueAt("Thành công", i, 3);
		}
	}

	private void guiDangKy() {
		// TODO Auto-generated method stub
		FileOutputStream fos = null;
		XSSFWorkbook wb = null;

		try {
			fos = new FileOutputStream(fileName);
			wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet();
			XSSFRow row;

			/* tạo header cho file exel */
			createHeader(sheet);
			/* lấy dữ liệu từ ds hp đăng kí ghi vào file */
			ArrayList<DangKyHocPhan> dsHocPhan = sv.getDSHPDangKy((String) hocKy.getSelectedItem());
			int count = dsHocPhan.size();
			int nextRow = 1;
			for (int i = 0; i < count; i++) {
				row = sheet.createRow(nextRow);
				writeHocPhan(dsHocPhan.get(i), row);

				nextRow++;
			}
			wb.write(fos);

			/* Update trạng thái đăng kí trên bảng đăng kí */
			updataTableDanhSachDangKy();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				wb.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void writeHocPhan(DangKyHocPhan dangKyHP, XSSFRow row) {
		// TODO Auto-generated method stub
		Cell cellMaHocPhan = row.createCell(1);
		cellMaHocPhan.setCellValue(dangKyHP.getHocPhan().getIdHocPhan());

		Cell cellNgayDangKy = row.createCell(2);
		cellNgayDangKy.setCellValue(dangKyHP.getNgayDangKy());
	}

	private void createHeader(XSSFSheet sheet) {
		// TODO Auto-generated method stub
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 12);
		cellStyle.setFont(font);

		// tạo dòng mới
		XSSFRow row = sheet.createRow(0);

		Cell cellMaHocPhan = row.createCell(1);
		cellMaHocPhan.setCellStyle(cellStyle);
		cellMaHocPhan.setCellValue("Mã Học Phần");

		Cell cellNgayDangKy = row.createCell(2);
		cellNgayDangKy.setCellStyle(cellStyle);
		cellNgayDangKy.setCellValue("Ngày Đăng Ký");
	}

	public void DangKiHocPhan() {
		String idHocPhan = tfDangky.getText();
		if (idHocPhan.equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn cần nhập vào mã học phần");
			return;
		}
		/* Kiểm tra học phần có tồn tại */
		HocPhan hocPhan = quanLy.getHocPhan(idHocPhan);
		if (hocPhan == null) {
			JOptionPane.showMessageDialog(null, "Không tìm thấy mã học phần.");
			return;
		}

		// chỉ cho sinh viên niên chế đăng kí những học phần nợ
		if (sv instanceof SinhVienNienChe) {
			if (!checkHPNo(((SinhVienNienChe) sv).getDsHocPhanNo(), idHocPhan)) {
				JOptionPane.showMessageDialog(null, "Bạn chỉ được đăng ký học lại những học phần còn nợ");
				return;
			}
		}

		/* kiểm tra học phần có trùng trong bảng đăng kí học phần */
		if (checkHP(idHocPhan)) {
			JOptionPane.showMessageDialog(null, "Học phần: " + idHocPhan + " đã tồn tại trong bảng đăng kí");
			return;
		}

		tongSoTinChiDaDangKy += hocPhan.getSoTinChi();
		/* kiểm tra có quá giới hạn tín chỉ HP được phép đăng ki */
		if (tongSoTinChiDaDangKy > 24) {
			JOptionPane.showMessageDialog(null, "Quá 24 tín chỉ.");
			return;
		}

		/* thêm học phần vào danh sách học phân đăng kí */
		DangKyHocPhan dkHP = new DangKyHocPhan((String) hocKy.getSelectedItem(), hocPhan, getDate());
		dsHPDangKy.add(dkHP);

		/* Thêm vào bảng */
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		Object[] obj = new Object[6];
		obj[0] = hocPhan.getIdHocPhan();
		obj[1] = hocPhan.getTenHP();
		obj[2] = dkHP.getNgayDangKy();
		obj[3] = "Chưa gửi đăng kí";
		obj[4] = Math.round(hocPhan.getSoTinChi());
		obj[5] = false;
		model.addRow(obj);

		/* set text fiel và label tín chỉ */
		lblSum.setText(tongSoTinChiDaDangKy + "");
		tfDangky.setText("");
	}

	// kiểm tra học phần có trong danh sách học phần nợ của sinh viên niên chế ko
	private boolean checkHPNo(ArrayList<HocPhan> dsHPNo, String idHP) {
		for (HocPhan hp : dsHPNo)
			if (hp.getIdHocPhan().equalsIgnoreCase(idHP)) {
				return true;
			}

		return false;
	}
	
	private boolean checkHP(String idHocPhan) {
		for(int i = 0; i < dsHPDangKy.size(); i++) {
			if(dsHPDangKy.get(i).getHocPhan().getIdHocPhan().equalsIgnoreCase(idHocPhan))
				return false;
		}
		
		return true;
	}

	public static String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); //định dạng ngày
		Date today = new Date();
	     
	    return dateFormat.format(today); //lấy ngày hiện tại
	}

}

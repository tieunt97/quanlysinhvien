package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
	private JComboBox<String> hocKy;
	private JTextField tfDangky;
	private JButton btnDangKy, btnXoaHP, btnGuiDangKy;
	private JTable table;
	private JCheckBox checkBox;
	private JLabel lblSum;
	private int tongSoTinChiDaDangKy = 0;
	private QuanLy quanLy;
	private SinhVien sv;
	private String fileName, hKy;
	private ArrayList<DangKyHocPhan> dsHPXoaDangKy;

	public DangKiHocPhanController(PanelDangKiHocPhanView panelDkiHP, QuanLy quanLy, SinhVien sv) {
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
		if (sv instanceof SinhVienTinChi) {
			hocKy.removeAllItems();
			hocKy.addItem("20172");
		} else {
			hocKy.removeAllItems();
			hocKy.addItem("20173");
		}
		this.hKy = (String) hocKy.getItemAt(0);
		this.dsHPXoaDangKy = new ArrayList<>();
		/* load thông tin cũ vào bảng */
		loadBangDangKyHocPhan(hKy);

		addEvents();
	}

	private void loadBangDangKyHocPhan(String hocKy) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		/* Xóa dữ liệu trên bảng */
		while (model.getRowCount() != 0) {
			model.removeRow(0);
		}

		Object[] obj = new Object[6];
		ArrayList<DangKyHocPhan> dsHPDangKy = sv.getDSHPDangKy(hocKy);
		int count = sv.getDSHPDangKy((String) hocKy).size();
		for (int i = 0; i < count; i++) {
			obj[0] = dsHPDangKy.get(i).getHocPhan().getIdHocPhan();
			obj[1] = dsHPDangKy.get(i).getHocPhan().getTenHP();
			obj[2] = dsHPDangKy.get(i).getNgayDangKy();
			obj[3] = "Thành công";
			obj[4] = dsHPDangKy.get(i).getHocPhan().getSoTinChi();
			obj[5] = false;

			model.addRow(obj);
			tongSoTinChiDaDangKy += dsHPDangKy.get(i).getHocPhan().getSoTinChi();
		}

		lblSum.setText(tongSoTinChiDaDangKy + "");
	}

	private void addEvents() {
		hocKy.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				String hKy = (String) hocKy.getSelectedItem();

				/* load thông tin cũ vào bảng */
				loadBangDangKyHocPhan(hKy);
				;
			}
		});

		checkBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
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
				DangKiHocPhan();
				return;
			}

		});

		tfDangky.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					DangKiHocPhan();
					return;
				}
			}
		});

		btnXoaHP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				xoaHocPhan();
				return;
			}
		});
		// su kien gui dang ki
		btnGuiDangKy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiDangKy();
				return;
			}
		});
	}

	private void xoaHocPhan() {
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
				DangKyHocPhan dkHP = sv.getHPDangKy(hKy, idHocPhan);
				if (dkHP != null) {
					themHPXoaDangKy(dkHP);
					tongSoTinChiDaDangKy -= dkHP.getHocPhan().getSoTinChi();
					model.removeRow(row);
				} else {
					JOptionPane.showMessageDialog(null, "Không tìm thấy mã học phần: " + idHocPhan);
					return;
				}
				continue;
			}
			row++;
		}
		lblSum.setText("" + tongSoTinChiDaDangKy);
	}

	private void themHPXoaDangKy(DangKyHocPhan dkHP) {
		for (int i = 0; i < dsHPXoaDangKy.size(); i++) {
			if (dsHPXoaDangKy.get(i).getHocKy().equalsIgnoreCase(dkHP.getHocKy()) && dsHPXoaDangKy.get(i).getHocPhan()
					.getIdHocPhan().equalsIgnoreCase(dkHP.getHocPhan().getIdHocPhan()))
				return;
		}

		dsHPXoaDangKy.add(dkHP);
	}

	private void updataTableDanhSachDangKy() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int count = model.getRowCount();
		for (int i = 0; i < count; i++) {
			model.setValueAt("Thành công", i, 3);
		}
	}

	private void guiDangKy() {
		for (int i = 0; i < dsHPXoaDangKy.size(); i++) {
			sv.xoaHPDangKy(dsHPXoaDangKy.get(i).getHocKy(), dsHPXoaDangKy.get(i).getHocPhan().getIdHocPhan());
		}
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
			int count = sv.getDsHPDangKy().size();
			int nextRow = 1;
			for (int i = 0; i < count; i++) {
				row = sheet.createRow(nextRow);
				writeHocPhan(sv.getDsHPDangKy().get(i), row);

				nextRow++;
			}
			wb.write(fos);

			/* Update trạng thái đăng kí trên bảng đăng kí */
			updataTableDanhSachDangKy();
			JOptionPane.showMessageDialog(null, "Đã gửi đăng ký");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void writeHocPhan(DangKyHocPhan dangKyHP, XSSFRow row) {
		Cell cellHocKy = row.createCell(1);
		cellHocKy.setCellValue(dangKyHP.getHocKy());

		Cell cellMaHocPhan = row.createCell(2);
		cellMaHocPhan.setCellValue(dangKyHP.getHocPhan().getIdHocPhan());

		Cell cellNgayDangKy = row.createCell(3);
		cellNgayDangKy.setCellValue(dangKyHP.getNgayDangKy());
	}

	private void createHeader(XSSFSheet sheet) {
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 11);
		cellStyle.setFont(font);

		// tạo dòng mới
		XSSFRow row = sheet.createRow(0);

		Cell cellHocKy = row.createCell(1);
		cellHocKy.setCellStyle(cellStyle);
		cellHocKy.setCellValue("Học kỳ");

		Cell cellMaHocPhan = row.createCell(2);
		cellMaHocPhan.setCellStyle(cellStyle);
		cellMaHocPhan.setCellValue("Mã Học Phần");

		Cell cellNgayDangKy = row.createCell(3);
		cellNgayDangKy.setCellStyle(cellStyle);
		cellNgayDangKy.setCellValue("Ngày Đăng Ký");
	}

	public void DangKiHocPhan() {
		String idHocPhan = tfDangky.getText().trim().toUpperCase();
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
		if (sv.getHPDangKy(hKy, idHocPhan) != null) {
			JOptionPane.showMessageDialog(null, "Học phần: " + idHocPhan + " đã tồn tại trong bảng đăng kí");
			return;
		}

		if (!checkHPDK(hocPhan)) {
			JOptionPane.showMessageDialog(null,
					"Cần học học phần có mã: " + hocPhan.getHocPhanDK().getIdHocPhan()
							+ " \ntrước khi học học phần có mã: " + hocPhan.getIdHocPhan(),
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		tongSoTinChiDaDangKy += hocPhan.getSoTinChi();

		/* thêm học phần vào danh sách học phân đăng kí */
		DangKyHocPhan dkHP = new DangKyHocPhan(hKy, hocPhan, getDate());
		sv.getDsHPDangKy().add(dkHP);

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

	// kiểm tra đã học học phần điều kiện chưa
	private boolean checkHPDK(HocPhan hp) {
		HocPhan hpDK = hp.getHocPhanDK();
		if (hpDK == null)
			return true;
		else {
			for (int i = 0; i < sv.getDsDiemHP().size(); i++) {
				if (sv.getDsDiemHP().get(i).getHocPhan().getIdHocPhan().equalsIgnoreCase(hp.getIdHocPhan()))
					return true;
			}

			return false;
		}
	}

	// kiểm tra học phần có trong danh sách học phần nợ của sinh viên niên chế ko
	private boolean checkHPNo(ArrayList<HocPhan> dsHPNo, String idHP) {
		for (HocPhan hp : dsHPNo)
			if (hp.getIdHocPhan().equalsIgnoreCase(idHP)) {
				return true;
			}

		return false;
	}

	public static String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); // định dạng ngày
		Date today = new Date();

		return dateFormat.format(today); // lấy ngày hiện tại
	}

}

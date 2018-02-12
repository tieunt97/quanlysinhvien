package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
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
import quanlysinhvien.model.DangKyLopHoc;
import quanlysinhvien.model.LopHocPhan;
import quanlysinhvien.model.QuanLy;
import quanlysinhvien.model.SinhVien;
import quanlysinhvien.model.SinhVienNienChe;
import quanlysinhvien.model.SinhVienTinChi;
import quanlysinhvien.view.PanelDangKiLopHocView;

public class DangKiLopHocController {
	private JTextField tfDangKy;
	private JButton btnDangKy, btnXoaDangKy, btnGuiDangKy;
	private JTable tableDangKy, tableTKB;
	private JCheckBox cbCheck;
	private JLabel lblSum;
	private SinhVien sv;
	private QuanLy quanLy;
	private String fileLopHoc, fileLopDaDK;
	private JComboBox<String> hocKy;
	private DangKyLopHoc dsLopHPDKTheoKy;
	private Workbook workbook;
	private String hKy;
	private ArrayList<String> dsLopChuaDK;
	// private ArrayList<DangKyHocPhan> dsHPDangKy;

	public DangKiLopHocController(PanelDangKiLopHocView dkiLopHoc, QuanLy quanLy, SinhVien sv) {
		this.quanLy = quanLy;
		this.sv = sv;
		this.tfDangKy = dkiLopHoc.getTfDangKy();
		this.btnDangKy = dkiLopHoc.getBtnDangKy();
		this.btnXoaDangKy = dkiLopHoc.getBtnXoaDangKy();
		this.btnGuiDangKy = dkiLopHoc.getBtnGuiDangKy();
		this.tableDangKy = dkiLopHoc.getTableDangKy();
		this.tableTKB = dkiLopHoc.getTableTKB();
		this.cbCheck = dkiLopHoc.getCbCheck();
		this.lblSum = dkiLopHoc.getLblSum();
		this.hocKy = dkiLopHoc.getHocKy();
		this.hocKy.removeAllItems();
		this.fileLopHoc = "quanlysinhvien\\danhsachhocphan\\lophocphan\\dsLopHP.xlsx";

		if (sv instanceof SinhVienTinChi) {
			this.hocKy.addItem("20172");
			this.fileLopDaDK = "quanlysinhvien\\sinhvientinchi\\" + sv.getIdSinhVien() + "\\dsLopHPDangKy.xlsx";
			// this.fileHPDaDK = "quanlysinhvien\\sinhvientinchi\\"+ sv.getIdSinhVien()
			// +"\\dsHPDangKy.xlsx";
		} else if (sv instanceof SinhVienNienChe) {
			this.hocKy.addItem("20173");
			this.fileLopDaDK = "quanlysinhvien\\sinhviennienche\\" + sv.getIdSinhVien() + "\\dsLopHPDangKy.xlsx";
			// this.fileHPDaDK = "quanlysinhvien\\sinhviennienche\\"+ sv.getIdSinhVien()
			// +"\\dsHPDangKy.xlsx";
		}
		this.hKy = (String) hocKy.getItemAt(0);
		this.dsLopHPDKTheoKy = new DangKyLopHoc(sv.getDSLopHPDangKy(hKy));
		/* load thông tin lớp học đăng kí phiên làm việc trước */
		loadThongTinDangKyTruoc();

		addEvents();
	}

	private void loadThongTinDangKyTruoc() {
		/* load lên bảng đăng kí */
		if (dsLopHPDKTheoKy.getDsLopHP().size() > 0) {
			ArrayList<LopHocPhan> dsLopHoc = dsLopHPDKTheoKy.getDsLopHP();
			int count = dsLopHoc.size();
			for (int i = 0; i < count; i++) {
				/* load trên bảng đăng kí */
				addDataTableDangKy(dsLopHoc.get(i), "old");
			}

			/* load bảng thời khóa biểu */
			loadTableThoiKhoaBieu(dsLopHoc);
			/* cập nhật lblSum */
			lblSum.setText(dsLopHPDKTheoKy.getTongSoTC() + "");
		}
	}

	private void addEvents() {
		btnDangKy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DangKyLopHoc();
				return;
			}

		});

		btnXoaDangKy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				XoaLopHoc();
				return;
			}

		});

		tfDangKy.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					DangKyLopHoc();
					return;
				}
			}
		});

		cbCheck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) tableDangKy.getModel();
				int count = model.getRowCount();
				for (int i = 0; i < count; i++) {
					if (cbCheck.isSelected()) {
						model.setValueAt(true, i, 5);
					} else {
						model.setValueAt(false, i, 5);
					}
				}
			}
		});

		btnGuiDangKy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GuiDangKy();
				return;
			}
		});
	}

	private void GuiDangKy() {
		/* Chỉnh trạng thái đăng kí sang thành công */
		DefaultTableModel model = (DefaultTableModel) tableDangKy.getModel();
		int count = model.getRowCount();
		for (int i = 0; i < count; i++) {
			if(model.getValueAt(i, 4).toString().equalsIgnoreCase("Chưa gửi đăng ký")) {
				dsLopChuaDK.add(model.getValueAt(i, 1).toString());
				model.setValueAt("Thành công", i, 4);
			}
		}

		/* Update lại file dsLopHoc tăng hoặc giảm số lượng sinh viên hiện tại */
		try {
			updateSoLuongSV(dsLopHPDKTheoKy);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* Cập nhật file danh sách lớp */
		UpdateDanhSachLop(dsLopHPDKTheoKy.getDsLopHP());
	}

	private void UpdateDanhSachLop(ArrayList<LopHocPhan> dsLop) {
		int count = dsLop.size();
		for (int i = 0; i < count; i++) {
			try {
				WriteFileDanhSachSV(dsLop.get(i));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void WriteFileDanhSachSV(LopHocPhan lopHocPhan) throws IOException {
		String fileName = "quanlysinhvien/danhsachhocphan/lophocphan/" + lopHocPhan.getIdLop() + "_dsSV.xlsx";
		XSSFSheet sheet = null;
		int lastRow = -1;
		try {
			FileInputStream inputStream = new FileInputStream(new File(fileName));
			workbook = new XSSFWorkbook(inputStream);
			sheet = (XSSFSheet) workbook.getSheetAt(0);
			lastRow = sheet.getLastRowNum();
		} catch (Exception e) {
			workbook = new XSSFWorkbook();
			sheet = (XSSFSheet) workbook.createSheet();
			System.out.println(e);
		}

		int rowNext = 1;
		Row row = null;
		if (lastRow < 0) { // file khong ton tai
			/* Tao tieu de file */
			createHeaderRow(sheet);

		} // file ton tai

		rowNext = 1;
		row = sheet.createRow(rowNext);
		if (row != null) {
			int count = lopHocPhan.getDsSinhVien().size();
			for (int i = 0; i < count; i++) {
				row = sheet.createRow(rowNext);
				writeSV(lopHocPhan.getDsSinhVien().get(i), row);
				rowNext++;
			}
		}

		FileOutputStream fout = new FileOutputStream(new File(fileName));
		workbook.write(fout);
		fout.close();
	}

	public void writeSV(SinhVien sv, Row row) {
		Cell cellMaSV = row.createCell(1);
		cellMaSV.setCellValue(sv.getIdSinhVien());
	}

	private void updateSoLuongSV(DangKyLopHoc dsLopHPDK) throws IOException {
		FileInputStream fin = new FileInputStream(new File(fileLopHoc));
		workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		Row row;
		if(iterator.hasNext()) {
			row = iterator.next();
		}
		
		while(iterator.hasNext()) {
			row = iterator.next();
			Cell idLop = row.getCell(2);
			if(capNhatSV(idLop.getStringCellValue())) {
				int soSV = (int) row.getCell(11).getNumericCellValue() + 1;
				Cell soSVHT = row.createCell(11);
				soSVHT.setCellValue(soSV);
				break;
			}
		}
		
		fin.close();

        FileOutputStream fout = new FileOutputStream(new File(fileLopHoc));
        workbook.write(fout);
        fout.close();
	}
	
	private boolean capNhatSV(String idLop) {
		for(int i = 0; i < dsLopChuaDK.size(); i++) {
			if(dsLopChuaDK.get(i).equalsIgnoreCase(idLop)) {
				dsLopChuaDK.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
//	private void WriteFileDangKyLopHoc(String string, ArrayList<LopHocPhan> dsLopHoc) {
//		try {
//			FileOutputStream fos = new FileOutputStream(new File(string));
//			workbook = new XSSFWorkbook();
//			XSSFSheet sheet = (XSSFSheet) workbook.createSheet("DSLopHocDaDangKy");
//
//			/* tạo header cho file */
//			String[] header = { "Học kỳ", "Mã lớp", "Loại lớp", "Mã học phần", "Tên lớp", "Thời gian",
//					"Tuần học", "Phòng học", "Tên giảng viên", "Số SV Max", "Số SV hiện tại" };
//			createHeaderRow(sheet, header);
//
//			/* viết nội dung cho file */
//			Row row;
//			int count = dsLopHoc.size();
//			int rowNext = 1;
//			for (int i = 0; i < count; i++) {
//				row = sheet.createRow(rowNext);
//				writeLopHocPhan(dsLopHoc.get(i), row);
//
//				rowNext++;
//			}
//
//			workbook.write(fos);
//
//			workbook.close();
//			fos.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

//	private void writeLopHocPhan(LopHocPhan lopHocPhan, Row row) {
//		Cell cellMaLop = row.createCell(1);
//		cellMaLop.setCellValue(lopHocPhan.getIdLop());
//	}

	private void createHeaderRow(XSSFSheet sheet) {
		CellStyle style = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 12);
		style.setFont(font);

		/* tạo mới dòng */
		XSSFRow row = sheet.createRow(0);
		Cell cellHocKy = row.createCell(1);
		cellHocKy.setCellStyle(style);
		cellHocKy.setCellValue("Mã sinh viên");
		
	}

	private void XoaLopHoc() {
		/* Xóa trên bảng đăng kí lớp học */
		DefaultTableModel model = (DefaultTableModel) tableDangKy.getModel();
		for (int i = 0; i < model.getRowCount();) {
			Boolean check = (boolean) model.getValueAt(i, 5);

			if (check == true) {
				String idLopHP = model.getValueAt(i, 0).toString();
				LopHocPhan lopHP = dsLopHPDKTheoKy.getLopHP(idLopHP);
				DangKyHocPhan dkHP = sv.getHPDangKy((String) hocKy.getItemAt(0), lopHP.getHocPhan().getIdHocPhan());
				/* Xóa trong danh sách lớp học đăng kí */
				dsLopHPDKTheoKy.xoaLopHP(idLopHP);

				/* giảm số sinh viên hiên tại của lớp trong ds Lớp */
				CapNhatSoLuongSinhVien(idLopHP, "sub");

				/* Cập nhật lại lblSum tổng số tín */
				dsLopHPDKTheoKy.setTongSoTC(dsLopHPDKTheoKy.getTongSoTC() - dkHP.getHocPhan().getSoTinChi());
				lblSum.setText(dsLopHPDKTheoKy.getTongSoTC() + "");

				/* xóa trên bảng đăng kí */
				model.removeRow(i);
			} else {
				i++;
			}
		}

		loadTableThoiKhoaBieu(dsLopHPDKTheoKy.getDsLopHP());
	}

	// private boolean checkDangKy() {
	// String idLopHP = tfDangKy.getText();
	// /* Kiểm tra rỗng */
	// if (idLopHP.equals("")) {
	// JOptionPane.showMessageDialog(null, "Nhập vào mã lớp học");
	// return false;
	// }
	//
	// /* Kiểm tra tồn tại mã lớp học (getLopHoc)*/
	// LopHocPhan lopHP = quanLy.getLopHocPhan(idLopHP);
	// if(lopHP == null){
	// JOptionPane.showMessageDialog(null, "Mã lớp học không tồn tại");
	// return false;
	// }
	//
	// /* Kiểm tra đã đăng kí học phần môn học này chưa (getHocPhan)*/
	// if(!checkDangKyHP(lopHP.getHocPhan().getIdHocPhan())){
	// JOptionPane.showMessageDialog(null, "Bạn chưa đăng kí học phần: " +
	// lopHP.getHocPhan().getIdHocPhan());
	// return false;
	// }
	//
	// //kiểm tra có đăng ký vượt số TC max ko
	// if(sv instanceof SinhVienTinChi) {
	// if(dsLopHPDKTheoKy.checkTCMax(((SinhVienTinChi) sv).getSoTCMax(),
	// lopHP.getHocPhan().getSoTinChi()))
	// JOptionPane.showMessageDialog(null, "Đăng ký vượt quá số TC max");
	// return false;
	// }
	//
	// //kiểm tra xem đã đăng kí lớp cho học phần tương ứng chưa
	// if(dsLopHPDKTheoKy.checkHocPhanDK(lopHP)) {
	// JOptionPane.showMessageDialog(null, "Lớp chứa mã học phần" +
	// lopHP.getHocPhan().getIdHocPhan() + " đã tồn tại trong bảng đăng ký");
	// return false;
	// }
	//
	// /* kiểm tra xem có trùng mã lớp học không, mã học phần */
	// if(((sv instanceof SinhVienNienChe) && !dsLopHPDKTheoKy.themLopHP(lopHP)) ||
	// (sv instanceof SinhVienTinChi && !dsLopHPDKTheoKy.themLopHP(((SinhVienTinChi)
	// sv).getSoTCMax(), lopHP))){
	// JOptionPane.showMessageDialog(null,
	// "Mã lớp: " + idLopHP + " đã tồn tại trong bảng đăng kí");
	// return;
	// }
	//
	//
	// return true;
	// }

	private void DangKyLopHoc() {
		String idLopHP = tfDangKy.getText();
		/* Kiểm tra rỗng */
		if (idLopHP.equals("")) {
			JOptionPane.showMessageDialog(null, "Nhập vào mã lớp học");
			return;
		}

		/* Kiểm tra tồn tại mã lớp học (getLopHoc) */
		LopHocPhan lopHP = quanLy.getLopHocPhan(idLopHP);
		if (lopHP == null) {
			JOptionPane.showMessageDialog(null, "Mã lớp học không tồn tại");
			return;
		}

		/* Kiểm tra đã đăng kí học phần môn học này chưa (getHocPhan) */
		if (!checkDangKyHP(lopHP.getHocPhan().getIdHocPhan())) {
			JOptionPane.showMessageDialog(null, "Bạn chưa đăng kí học phần: " + lopHP.getHocPhan().getIdHocPhan());
			return;
		}

		// kiểm tra có đăng ký vượt số TC max ko
		if (sv instanceof SinhVienTinChi) {
			if (dsLopHPDKTheoKy.checkTCMax(((SinhVienTinChi) sv).getSoTCMax(), lopHP.getHocPhan().getSoTinChi())) {
				JOptionPane.showMessageDialog(null, "Đăng ký vượt quá số TC max");
				return;
				
			}
		}

		// kiểm tra xem đã đăng kí lớp cho học phần tương ứng chưa
		if (dsLopHPDKTheoKy.checkHocPhanDK(lopHP)) {
			JOptionPane.showMessageDialog(null,
					"Lớp chứa mã học phần" + lopHP.getHocPhan().getIdHocPhan() + " đã tồn tại trong bảng đăng ký");
			return;
		}
		
		//kiểm tra xem đã vượt quá số TC max của svtc chưa
		if (sv instanceof SinhVienTinChi
				&& dsLopHPDKTheoKy.checkTCMax(((SinhVienTinChi) sv).getSoTCMax(), lopHP.getHocPhan().getSoTinChi())) {
			JOptionPane.showMessageDialog(null, "Vượt quá số TC tối đa", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}

		dsLopHPDKTheoKy.setTongSoTC(dsLopHPDKTheoKy.getTongSoTC());

		ArrayList<LopHocPhan> dsLopHPDK = dsLopHPDKTheoKy.getDsLopHP();
		boolean check = KiemTraDieuKienTrungLich(lopHP, dsLopHPDK);
		if (check == true) {
			/* Cập nhật lại số tín chỉ */
			dsLopHPDKTheoKy.setTongSoTC(dsLopHPDKTheoKy.getTongSoTC() - lopHP.getHocPhan().getSoTinChi());
			JOptionPane.showMessageDialog(null, "Trùng lịch: " + lopHP.getThoiGian());
			return;
		}
		dsLopHPDKTheoKy.themLopHP(lopHP);

		/* Tăng số sinh viên hiện tại lên 1 trong danh sách học phần */
		if (!CapNhatSoLuongSinhVien(lopHP.getIdLop(), "add")) {
			JOptionPane.showMessageDialog(null, "Thêm sinh viên không thành công.");
			return;
		}

		/* load dữ liệu lớp học vào bảng đăng kí lớp học */
		addDataTableDangKy(lopHP, "new");

		/* Load dữ liệu vào bảng thời khóa biểu */
		loadTableThoiKhoaBieu(dsLopHPDK);

		/* Set lại label tổng số tín chỉ */
		lblSum.setText(dsLopHPDKTheoKy.getTongSoTC() + "");
		tfDangKy.setText("");

	}

	private boolean checkDangKyHP(String idHP) {
		ArrayList<DangKyHocPhan> dsHPDangKy = sv.getDSHPDangKy(hKy);
		for (DangKyHocPhan DKHP : dsHPDangKy) {
			if (DKHP.getHocPhan().getIdHocPhan().equalsIgnoreCase(idHP))
				return true;
		}

		return false;
	}

	private boolean CapNhatSoLuongSinhVien(String idLop, String mode) {
		ArrayList<LopHocPhan> dsLopHP = quanLy.getDsLopHocPhan();
		int count = dsLopHP.size();
		for (int i = 0; i < count; i++) {
			if (idLop.equalsIgnoreCase(dsLopHP.get(i).getIdLop())) {
				if (mode.equalsIgnoreCase("add")) {
					/* Kiểm tra điều kiện trùng mã sinh viên */
					if (dsLopHP.get(i).kiemTraTrungSV(sv)) {
						JOptionPane.showMessageDialog(null,
								"Trùng mã sinh viên: " + sv.getIdSinhVien() + "- Họ Tên: " + sv.getHoTen());
						return false;
					}
					System.out.println("add");
					return dsLopHP.get(i).themSinhVien(sv, "max");
				}

				if (mode.equalsIgnoreCase("sub")) {
					System.out.println("sub");
					return dsLopHP.get(i).xoaSinhVien(sv.getIdSinhVien());
				}
			}
		}

		return false;
	}

	private boolean KiemTraDieuKienTrungLich(LopHocPhan lopHoc, ArrayList<LopHocPhan> dsLopHocDangKy) {
		String[] thoiGian = lopHoc.getThoiGian().trim().split(",");
		String day1 = thoiGian[0].trim();
		int start1 = Integer.parseInt(thoiGian[1].trim().split("-")[0].trim());
		int end1 = Integer.parseInt(thoiGian[1].trim().split("-")[1].trim());

		String[] thoiGian2;
		String day2;
		int start2, end2;

		int count = dsLopHocDangKy.size();
		for (int row = 0; row < count; row++) {
			thoiGian2 = dsLopHocDangKy.get(row).getThoiGian().trim().split(",");
			day2 = thoiGian2[0].trim();
			start2 = Integer.parseInt(thoiGian2[1].trim().split("-")[0].trim());
			end2 = Integer.parseInt(thoiGian2[1].trim().split("-")[1].trim());

			if (day1.equalsIgnoreCase(day2)) {
				if (start1 >= start2 && start1 <= end2) {
					return true;
				}

				if (start2 >= start1 && start2 <= end1) {
					return true;
				}

				System.out.println(start2 >= start1 && start2 <= end1);
			}
		}

		return false;
	}

	private void loadTableThoiKhoaBieu(ArrayList<LopHocPhan> dsLopHocDangKy2) {
		/* Sắp xếp lại array list theo thời khóa biểu */
		dsLopHocDangKy2.sort(new Comparator<LopHocPhan>() {

			@Override
			public int compare(LopHocPhan obj1, LopHocPhan obj2) {
				String[] time1 = obj1.getThoiGian().split(",");
				int day1 = Integer.parseInt(time1[0].trim().split("\\s")[1].trim());
				int start1 = Integer.parseInt(time1[1].trim().split("-")[0]);
				// int end1 = Integer.parseInt(time1[1].trim().split("-")[1].trim());

				String[] time2 = obj2.getThoiGian().split(",");
				int day2 = Integer.parseInt(time2[0].trim().split("\\s")[1].trim());
				int start2 = Integer.parseInt(time2[1].trim().split("-")[0]);
				// int end2 = Integer.parseInt(time2[1].trim().split("-")[1].trim());

				if (day1 > day2) {
					return 1;
				} else if (day1 < day2) {
					return -1;
				} else if (day1 == day2) {
					if (start1 > start2) {
						return 1;
					} else if (start1 < start2) {
						return -1;
					} else {
						return 0;
					}
				}
				return 0;

			}
		});

		/* load dữ liệu vào bảng */
		DefaultTableModel model = (DefaultTableModel) tableTKB.getModel();
		/* Xóa bảng dữ liệu trong bảng cũ */
		while (model.getRowCount() != 0) {
			model.removeRow(0);
		}

		/* load lại bảng dữ liệu đã được sắp xếp lịch */
		Object[] obj = new Object[5];
		for (int i = 0; i < dsLopHocDangKy2.size(); i++) {
			obj[0] = dsLopHocDangKy2.get(i).getThoiGian().split(",")[0].trim(); // thứ
			obj[1] = dsLopHocDangKy2.get(i).getThoiGian().split(",")[1].trim(); // thời gian
			obj[2] = dsLopHocDangKy2.get(i).getTuanHoc();
			obj[3] = dsLopHocDangKy2.get(i).getPhongHoc();
			obj[4] = dsLopHocDangKy2.get(i).getIdLop();

			model.addRow(obj);
		}

	}

	public void addDataTableDangKy(LopHocPhan lopHoc, String mode) {
		DefaultTableModel model = (DefaultTableModel) tableDangKy.getModel();
		Object[] obj = new Object[6];
		obj[0] = lopHoc.getIdLop();
		obj[1] = lopHoc.getHocPhan().getTenHP();
		obj[2] = lopHoc.getHocPhan().getIdHocPhan();
		obj[3] = lopHoc.getLoaiLop();
		obj[5] = false;

		if (mode.equalsIgnoreCase("new")) {
			obj[4] = "Chưa gửi đăng kí";
		}

		if (mode.equalsIgnoreCase("old")) {
			obj[4] = "thành công";
		}

		model.addRow(obj);
	}
}

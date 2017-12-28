package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.QuanLy;
import quanlysinhvien.model.SinhVien;
import quanlysinhvien.model.SinhVienNienChe;
import quanlysinhvien.model.SinhVienTinChi;
import quanlysinhvien.model.TaiKhoan;
import quanlysinhvien.view.CapNhatDiemSVView;
import quanlysinhvien.view.PanelSinhVienNienCheView;

public class SinhVienNienCheController {
	private JTable table;
	private JButton btnThem, btnSua, btnXoa, btnHuy, btnTimKiem, btnCapNhatDiem;
	private ButtonGroup bg;
	private JRadioButton radNam, radNu;
	private JComboBox<String> timKiemCB;
	private JTextField tfIdSV, tfHoTen, tfKhoa, tfNgaySinh, tfEmail, tfSoDT, tfDiaChi, tfDiemTB, tfTongSoKy, tfTimKiem;
	private PanelSinhVienNienCheView sinhVienNC;
	private String fileName;
	private QuanLy quanLy;
	private final String PATTERNNGAYSINH = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|"
			+ "-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|"
			+ "^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468]"
			+ "[048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|"
			+ "^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(" + "?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
	private final String PATTERNEMAIL = "^[\\w-]{1,30}@[\\w&&[^0-9_]]+\\.[\\w&&[^0-9]]+$";
	private final String PATTERNSDT = "0\\d{9,10}";
	private final String PATTERNDIEM = "\\d.\\d{1,2}|\\d";

	public SinhVienNienCheController(PanelSinhVienNienCheView sinhVienNC, QuanLy quanLy) {
		this.sinhVienNC = sinhVienNC;
		this.quanLy = quanLy;
		fileName = "quanlysinhvien\\sinhviennienche\\dsSinhVienNC.xlsx";
		this.table = sinhVienNC.getTable();
		this.bg = sinhVienNC.getBg();
		this.radNam = sinhVienNC.getRadNam();
		this.radNu = sinhVienNC.getRadNu();
		this.btnThem = sinhVienNC.getBtnThem();
		this.btnSua = sinhVienNC.getBtnSua();
		this.btnXoa = sinhVienNC.getBtnXoa();
		this.btnHuy = sinhVienNC.getBtnHuy();
		this.btnCapNhatDiem = sinhVienNC.getBtnCapNhatDiem();
		this.btnTimKiem = sinhVienNC.getBtnTimKiem();
		this.timKiemCB = sinhVienNC.getTimKiemCB();
		this.tfIdSV = sinhVienNC.getTfIdSV();
		this.tfHoTen = sinhVienNC.getTfHoTen();
		this.tfKhoa = sinhVienNC.getTfKhoa();
		this.tfNgaySinh = sinhVienNC.getTfNgaySinh();
		this.tfEmail = sinhVienNC.getTfEmail();
		this.tfSoDT = sinhVienNC.getTfSoDT();
		this.tfDiaChi = sinhVienNC.getTfDiaChi();
		this.tfDiemTB = sinhVienNC.getTfDiemTB();
		this.tfTongSoKy = sinhVienNC.getTfTongSoKy();
		this.tfTimKiem = sinhVienNC.getTfTimKiem();
		this.sinhVienNC.loadData(table, quanLy.getDsSinhVien(), "", "");

		setAction();
	}

	// bắt sự kiện
	private void setAction() {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				if (row >= 0) {
					tfIdSV.setText((String) table.getValueAt(row, 0));
					tfIdSV.setEnabled(false);
					tfHoTen.setText((String) table.getValueAt(row, 1));
					tfKhoa.setText((String) table.getValueAt(row, 2));
					tfNgaySinh.setText((String) table.getValueAt(row, 3));
					if (table.getValueAt(row, 4).equals("Nam")) {
						radNam.setSelected(true);
					} else {
						radNu.setSelected(true);
					}
					tfEmail.setText((String) table.getValueAt(row, 5));
					tfSoDT.setText((String) table.getValueAt(row, 6));
					tfDiaChi.setText((String) table.getValueAt(row, 7));
					tfDiemTB.setText((String) table.getValueAt(row, 8));
					tfTongSoKy.setText((String) table.getValueAt(row, 9));
				}
			}
		});

		btnThem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SinhVienNienChe svnc = getSinhVienNC();
				if (svnc != null) {
					String idSV = svnc.getIdSinhVien();
					if (quanLy.themSinhVien(svnc)) {
						// thêm sinh viên vào bảng
						((DefaultTableModel) table.getModel())
								.addRow(new Object[] { svnc.getIdSinhVien(), svnc.getHoTen(), svnc.getKhoa(),svnc.getNgaySinh(),
										svnc.getGioiTinh(), svnc.getEmail(), svnc.getSoDT(),svnc.getDiaChi(), svnc.getDiemTB() + "",
										svnc.getTongSoKy() + "", svnc.getDsHocPhanNo().size() + "" });
						try {
							addSV(svnc, fileName);
							System.out.println(Directory.createDir("quanlysinhvien\\sinhviennienche\\" + idSV));
							QuanLyTaiKhoan.addTaiKhoan(new TaiKhoan(idSV, idSV, "svnc"));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "Thêm thành công");
						cancel();
					} else {
						JOptionPane.showMessageDialog(null, "Trùng mã sinh viên", "Error insert",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		btnSua.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				if (row < 0) {
					JOptionPane.showMessageDialog(null, "Cần chọn một hàng để sửa", "Error update",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				SinhVienNienChe svnc = getSinhVienNC();
				if (svnc != null) {
					for (int i = 0; i < quanLy.getDsSinhVien().size(); i++) {
						SinhVien sv = quanLy.getDsSinhVien().get(i);
						if (!(sv instanceof SinhVienNienChe))
							continue;
						SinhVienNienChe svnc1 = (SinhVienNienChe) sv;
						if (svnc1.getIdSinhVien().equals(svnc.getIdSinhVien())) {
							svnc1.setHoTen(svnc.getHoTen());
							svnc1.setKhoa(svnc.getKhoa());
							svnc1.setNgaySinh(svnc.getNgaySinh());
							svnc1.setEmail(svnc.getEmail());
							svnc1.setGioiTinh(svnc.getGioiTinh());
							svnc1.setSoDT(svnc.getSoDT());
							svnc1.setDiaChi(svnc.getDiaChi());
							svnc1.setDiemTB(svnc.getDiemTB());
							svnc1.setTongSoKy(svnc.getTongSoKy());
							break;
						}
					}
					updateRowTable(svnc, row); // sửa dữ liêu sinh viên trên bảng
					boolean ck = false;
					try {
						ck = updateSV(svnc, fileName);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (ck)
						JOptionPane.showMessageDialog(null, "Cập nhật thành công");
					else {
						JOptionPane.showMessageDialog(null, "Lỗi cập nhật", "Error update", JOptionPane.ERROR_MESSAGE);
					}
					cancel();
				}

			}
		});

		btnXoa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				if (row < 0) {
					JOptionPane.showMessageDialog(null, "Cần chọn một hàng để xóa", "Error delete",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else {
					int select = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa không?", "Notify",
							JOptionPane.YES_NO_OPTION);
					if (select == 0) {
						String id = (String) table.getValueAt(row, 0);
						for (int i = 0; i < quanLy.getDsSinhVien().size(); i++) {
							SinhVien sv = quanLy.getDsSinhVien().get(i);
							if (sv.getIdSinhVien().equals(id) && sv instanceof SinhVienNienChe) {
								boolean ck = false;
								try {
									ck = deleteSV((SinhVienNienChe) sv, fileName);
									System.out.println(
											Directory.deleteDir(new File("quanlysinhvien\\sinhviennienche\\" + id)));
									QuanLyTaiKhoan.deleteTaiKhoan(id);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								if (ck) {
									quanLy.xoaSinhVien(id);
									// xóa sinh viên trên bảng
									((DefaultTableModel) table.getModel()).removeRow(row);
									JOptionPane.showMessageDialog(null, "Xóa thành công");
								}
								else {
									JOptionPane.showMessageDialog(null, "Xóa lỗi", "Error delete",
											JOptionPane.ERROR_MESSAGE);
								}
								cancel();
								return;
							}
						}
					}
				}
			}
		});

		btnHuy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				cancel();
				sinhVienNC.loadData(table, quanLy.getDsSinhVien(), "", "");
			}
		});

		btnTimKiem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String timKiem = timKiemCB.getSelectedItem().toString();
				String giaTri = tfTimKiem.getText().trim().toLowerCase();
				sinhVienNC.loadData(table, quanLy.getDsSinhVien(), timKiem, giaTri);
			}
		});

		btnCapNhatDiem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row < 0) {
					JOptionPane.showMessageDialog(null, "Cần chọn sinh viên để cập nhật điểm", "Error update",
							JOptionPane.ERROR_MESSAGE);
				} else {
					String idSV = (String) table.getValueAt(row, 0);
					CapNhatDiemSVView capNhatDiem = new CapNhatDiemSVView(idSV);
					new CapNhatDiemSVController(capNhatDiem, quanLy.getSinhVien(idSV), quanLy);
					capNhatDiem.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							// TODO Auto-generated method stub
							try {
								SinhVienNienChe sv = (SinhVienNienChe) quanLy.getSinhVien(idSV);
								updateDiemSV(sv);
								updateRowTable(sv, row);
								cancel();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								System.out.println("Error CapNhatDiem SVTC: " + e1);
							}

						}
					});
				}
			}
		});
	}

	// cập nhật dữ liệu sinh viên tại hàng row
	private void updateRowTable(SinhVienNienChe svnc, int row) {
		table.setValueAt(svnc.getHoTen(), row, 1);
		table.setValueAt(svnc.getKhoa(), row, 2);
		table.setValueAt(svnc.getNgaySinh(), row, 3);
		table.setValueAt(svnc.getGioiTinh(), row, 4);
		table.setValueAt(svnc.getEmail(), row, 5);
		table.setValueAt(svnc.getSoDT(), row, 6);
		table.setValueAt(svnc.getDiaChi(), row, 7);
		table.setValueAt(svnc.getDiemTB() + "", row, 8);
		table.setValueAt(svnc.getTongSoKy() + "", row, 9);
	}

	// lấy dữ liệu sinh viên niên chế từ input
	private SinhVienNienChe getSinhVienNC() {
		String idSinhVien = tfIdSV.getText().toUpperCase().trim();
		String hoTen = tfHoTen.getText().trim();
		String khoa = tfKhoa.getText().trim();
		String ngaySinh = tfNgaySinh.getText().trim();
		String gioiTinh = "";
		if (radNam.isSelected())
			gioiTinh = "Nam";
		else if (radNu.isSelected())
			gioiTinh = "Nữ";
		String email = tfEmail.getText().trim();
		String soDT = tfSoDT.getText().trim();
		String diaChi = tfDiaChi.getText().trim();
		if (idSinhVien.equals("") || hoTen.equals("") || khoa.equals("") || ngaySinh.equals("") || gioiTinh.equals("")
				|| email.equals("") || soDT.equals("") || diaChi.equals("")) {
			JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if (!email.matches(PATTERNEMAIL)) {
			JOptionPane.showMessageDialog(null, "Email không đúng!!!", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if (!ngaySinh.matches(PATTERNNGAYSINH)) {
			JOptionPane.showMessageDialog(null, "Ngày sinh không đúng!!!", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if (!soDT.matches(PATTERNSDT)) {
			JOptionPane.showMessageDialog(null, "Số điện thoại không đúng!!!", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		double diemTB;
		int tongSoKy;
		try {
			String diem = tfDiemTB.getText().trim();
			if (!diem.matches(PATTERNDIEM)) {
				JOptionPane.showMessageDialog(null, "Điểm trung bình không đúng!!!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return null;
			}
			diemTB = Double.parseDouble(diem);
			tongSoKy = Integer.parseInt(tfTongSoKy.getText().trim());
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(null, "Hãy kiểm tra các giá trị: Điểm TB, Tổng số kỳ", "Error",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}

		SinhVienNienChe svnc = new SinhVienNienChe(idSinhVien, hoTen, khoa, "null", ngaySinh, gioiTinh, email, soDT,
				diaChi, diemTB, tongSoKy);
		return svnc;
	}

	// reset input
	private void cancel() {
		table.getSelectionModel().clearSelection();
		timKiemCB.setSelectedIndex(0);
		tfIdSV.setText("");
		tfIdSV.setEnabled(true);
		tfHoTen.setText("");
		tfKhoa.setText("");
		tfNgaySinh.setText("");
		bg.clearSelection();
		tfEmail.setText("");
		tfSoDT.setText("");
		tfDiaChi.setText("");
		tfTongSoKy.setText("0");
		tfDiemTB.setText("0.0");
		tfTimKiem.setText("");
	}

	// cập nhật điểm sinh viên trong file diem
	private void updateDiemSV(SinhVienNienChe svnc) throws IOException {
		FileInputStream fin = new FileInputStream(
				new File("quanlysinhvien\\sinhviennienche\\" + svnc.getIdSinhVien() + "\\diem.xlsx"));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		Row nextRow;
		if (iterator.hasNext())
			nextRow = iterator.next(); // loại bỏ dòng tiêu đề
		ArrayList<String> kyHocAll = new ArrayList<>();
		int soMonNo = 0;
		double diemTB = 0.0;
		double sum = 0.0;
		while (iterator.hasNext()) {
			nextRow = iterator.next();
			Cell hocKy = nextRow.getCell(1);
			kyHocAll.add(hocKy.getStringCellValue());
			Cell cellTC = nextRow.getCell(4);
			int tinChi = (int) Double.parseDouble(Double.toString(cellTC.getNumericCellValue()));
			Cell cellDiem10 = nextRow.getCell(8);
			double diem4 = Double.parseDouble(Double.toString(cellDiem10.getNumericCellValue()));
			sum += tinChi * diem4;
			if (!checkNo(diem4)) {
				soMonNo += 1;
			}
		}
		ArrayList<String> hocKy = (ArrayList<String>) kyHocAll.stream().distinct().collect(Collectors.toList());
		diemTB = (double) Math.round(((double) sum / soMonNo) * 10) / 10;
		svnc.setDiemTB(diemTB);
		int tongSoKy = 0;
		for (String ky : hocKy) {
			if (Integer.parseInt(ky) % 10 != 3)
				tongSoKy += 1;
		}
		svnc.setTongSoKy(tongSoKy);
		fin.close();

		String fileName = "quanlysinhvien\\sinhviennienche\\dsSinhVienNC.xlsx";
		FileInputStream fin1 = new FileInputStream(new File(fileName));
		workbook = new XSSFWorkbook(fin1);
		sheet = workbook.getSheetAt(0);
		iterator = sheet.iterator();
		if (iterator.hasNext())
			nextRow = iterator.next();
		while (iterator.hasNext()) {
			nextRow = iterator.next();
			Cell cell = nextRow.getCell(1);
			String idSV = cell.getStringCellValue();
			if (idSV.equals(svnc.getIdSinhVien())) {
				cell = nextRow.getCell(10);
				cell.setCellValue(diemTB);
				cell = nextRow.getCell(11);
				cell.setCellValue(tongSoKy);
				cell = nextRow.getCell(12);
				cell.setCellValue(soMonNo);
				break;
			}

		}
		fin1.close();
		FileOutputStream fout = new FileOutputStream(new File(fileName));
		workbook.write(fout);
		fout.close();
	}

	// kiểm tra học phần nợ
	private boolean checkNo(double diem10) {
		if (diem10 < 5.0)
			return true;
		else
			return false;
	}

	// tạo tiêu đề file dsSinhVienNC
	private void createHeader(Sheet sheet) {
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		cellStyle.setFont(font);

		Row row = sheet.createRow(0);

		Cell cellIdSV = row.createCell(1);
		cellIdSV.setCellStyle(cellStyle);
		cellIdSV.setCellValue("Mã sinh viên");

		Cell cellHoTen = row.createCell(2);
		cellHoTen.setCellStyle(cellStyle);
		cellHoTen.setCellValue("Họ tên");

		Cell cellKhoa = row.createCell(3);
		cellKhoa.setCellStyle(cellStyle);
		cellKhoa.setCellValue("Khóa");

		Cell cellTenLop = row.createCell(4);
		cellTenLop.setCellStyle(cellStyle);
		cellTenLop.setCellValue("Tên lớp");

		Cell cellNgaySinh = row.createCell(5);
		cellNgaySinh.setCellStyle(cellStyle);
		cellNgaySinh.setCellValue("Ngày sinh");

		Cell cellGioiTinh = row.createCell(6);
		cellGioiTinh.setCellStyle(cellStyle);
		cellGioiTinh.setCellValue("Giới tính");

		Cell cellEmail = row.createCell(7);
		cellEmail.setCellStyle(cellStyle);
		cellEmail.setCellValue("Email");

		Cell cellSoDT = row.createCell(8);
		cellSoDT.setCellStyle(cellStyle);
		cellSoDT.setCellValue("Số ĐT");

		Cell cellDiaChi = row.createCell(9);
		cellDiaChi.setCellStyle(cellStyle);
		cellDiaChi.setCellValue("Địa chỉ");

		Cell cellDiemTB = row.createCell(10);
		cellDiemTB.setCellStyle(cellStyle);
		cellDiemTB.setCellValue("Điểm TB");

		Cell cellSoTCQua = row.createCell(11);
		cellSoTCQua.setCellStyle(cellStyle);
		cellSoTCQua.setCellValue("Tổng số kỳ");

		Cell cellSoMonNo = row.createCell(12);
		cellSoMonNo.setCellStyle(cellStyle);
		cellSoMonNo.setCellValue("Số môn nợ");

	}

	// ghi dòng dữ liệu svnc tại dòng row
	private void writeSV(SinhVienNienChe sv, Row row) {
		Cell cell = row.createCell(1);
		cell.setCellValue(sv.getIdSinhVien());
		cell = row.createCell(2);
		cell.setCellValue(sv.getHoTen());
		cell = row.createCell(3);
		cell.setCellValue(sv.getKhoa());
		cell = row.createCell(4);
		cell.setCellValue(sv.getTenLop());
		cell = row.createCell(5);
		cell.setCellValue(sv.getNgaySinh());
		cell = row.createCell(6);
		cell.setCellValue(sv.getGioiTinh());
		cell = row.createCell(7);
		cell.setCellValue(sv.getEmail());
		cell = row.createCell(8);
		cell.setCellValue(sv.getSoDT());
		cell = row.createCell(9);
		cell.setCellValue(sv.getDiaChi());
		cell = row.createCell(10);
		cell.setCellValue(sv.getDiemTB());
		cell = row.createCell(11);
		cell.setCellValue(sv.getTongSoKy());
		cell = row.createCell(12);
		cell.setCellValue(sv.getDsHocPhanNo().size());
	}

	// thêm sinh viên vào file
	private void addSV(SinhVienNienChe svnc, String fileName) throws IOException {
		Workbook workbook = null;
		Sheet sheet = null;
		int lastRow = -1;
		try {
			FileInputStream inputStream = new FileInputStream(new File(fileName));
			workbook = new XSSFWorkbook(inputStream);
			sheet = workbook.getSheetAt(0);
			lastRow = sheet.getLastRowNum();
		} catch (Exception e) {
			// TODO: handle exception
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet();
			System.out.println(e);
		}

		Row row = null;
		if (lastRow < 0) {
			createHeader(sheet);
			row = sheet.createRow(1);
		} else {
			row = sheet.createRow(lastRow + 1);
		}
		if (row != null) {
			writeSV(svnc, row);
		}

		FileOutputStream fout = new FileOutputStream(new File(fileName));
		workbook.write(fout);
		fout.close();
	}

	// cập nhật dữ liệu sinh viên trong file
	private boolean updateSV(SinhVienNienChe svnc, String fileName) throws IOException {
		boolean ck = false;
		FileInputStream fin = new FileInputStream(new File(fileName));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();

		Row nextRow;
		if (iterator.hasNext())
			nextRow = iterator.next(); // loại bỏ dòng tiêu đề
		while (iterator.hasNext()) {
			nextRow = iterator.next();
			Cell cell = nextRow.getCell(1);
			String idSV = cell.getStringCellValue();
			if (idSV.equalsIgnoreCase(svnc.getIdSinhVien())) {
				cell = nextRow.createCell(2);
				cell.setCellValue(svnc.getHoTen());
				cell = nextRow.createCell(3);
				cell.setCellValue(svnc.getKhoa());
				cell = nextRow.createCell(4);
				cell.setCellValue(svnc.getTenLop());
				cell = nextRow.createCell(5);
				cell.setCellValue(svnc.getNgaySinh());
				cell = nextRow.createCell(6);
				cell.setCellValue(svnc.getGioiTinh());
				cell = nextRow.createCell(7);
				cell.setCellValue(svnc.getEmail());
				cell = nextRow.createCell(8);
				cell.setCellValue(svnc.getSoDT());
				cell = nextRow.createCell(9);
				cell.setCellValue(svnc.getDiaChi());
				cell = nextRow.createCell(10);
				cell.setCellValue(svnc.getDiemTB());
				cell = nextRow.createCell(11);
				cell.setCellValue(svnc.getTongSoKy());
				cell = nextRow.createCell(12);
				cell.setCellValue(svnc.getDsHocPhanNo().size());
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

	// xóa sinh viên svnc trong file
	private boolean deleteSV(SinhVienNienChe svnc, String fileName) throws IOException {
		boolean ck = false;
		FileInputStream fin = new FileInputStream(new File(fileName));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);

		Iterator<Row> iterator = sheet.iterator();

		Row nextRow;
		if (iterator.hasNext())
			nextRow = iterator.next(); // loại bỏ dòng tiêu đề
		int i = 0;
		while (iterator.hasNext()) {
			nextRow = iterator.next();
			i++;
			Cell cell = nextRow.getCell(1);
			String idSV = cell.getStringCellValue();
			if (idSV.equalsIgnoreCase(svnc.getIdSinhVien())) {
				int lastRow = sheet.getLastRowNum();
				if (i < lastRow) {
					sheet.shiftRows(i + 1, lastRow, -1);
					ck = true;
				}
				if (i == lastRow) {
					Row removeRow = sheet.getRow(i);
					if (removeRow != null) {
						sheet.removeRow(removeRow);
						ck = true;
					}
				}
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

package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.SinhVienTinChi;
import quanlysinhvien.view.PanelSinhVienTinChiView;

public class SinhVienTinChiController {
	private PanelSinhVienTinChiView sinhVienTC;
	private JTable table;
	private JButton btnThem, btnSua, btnXoa, btnHuy, btnTimKiem, btnLuu;
	private JComboBox<String> timKiemCB;
	private JRadioButton radNam, radNu;
	private JTextField tfIdSV, tfHoTen, tfKhoa, tfNgaySinh, tfEmail, tfSoDT, tfDiaChi, tfDiemTB, tfSoTCQua, tfSoTCNo,
			tfTimKiem;
	private ArrayList<SinhVienTinChi> dsSinhVien;
	private String fileName;

	public SinhVienTinChiController(PanelSinhVienTinChiView sinhVienTC) {
		this.sinhVienTC = sinhVienTC;

		// Tao thu muc con
//		File file = new File(
//				"C:\\Users\\tieu_nt\\Desktop\\Lập trình hướng đối tượng\\quanlysinhvien\\sinhvientinchi\\demo");
//		file.mkdir();
//		System.out.println("isDiratory: " + file.isDirectory());
		// System.out.println(deleteDir(file)); // xoa thu muc va tat ca trong no
		fileName = "C:\\Users\\tieu_nt\\Desktop\\Lập trình hướng đối tượng\\quanlysinhvien\\sinhvientinchi\\dsSinhVienTC.xlsx";
		try {
			dsSinhVien = readFile(fileName);
			// System.out.println("Success readFile.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			dsSinhVien = new ArrayList<>();
			// e.printStackTrace();
			System.out.println("Error: " + e);
		}
		this.table = sinhVienTC.getTable();
		this.btnThem = sinhVienTC.getBtnThem();
		this.btnSua = sinhVienTC.getBtnSua();
		this.btnXoa = sinhVienTC.getBtnXoa();
		this.btnHuy = sinhVienTC.getBtnHuy();
		this.btnTimKiem = sinhVienTC.getBtnTimKiem();
		this.btnLuu = sinhVienTC.getBtnLuu();
		this.timKiemCB = sinhVienTC.getTimKiemCB();
		this.tfIdSV = sinhVienTC.getTfIdSV();
		this.tfHoTen = sinhVienTC.getTfHoTen();
		this.tfKhoa = sinhVienTC.getTfKhoa();
		this.tfNgaySinh = sinhVienTC.getTfNgaySinh();
		this.tfEmail = sinhVienTC.getTfEmail();
		this.tfSoDT = sinhVienTC.getTfSoDT();
		this.tfDiaChi = sinhVienTC.getTfDiaChi();
		this.tfDiemTB = sinhVienTC.getTfDiemTB();
		this.tfSoTCNo = sinhVienTC.getTfSoTCNo();
		this.tfSoTCQua = sinhVienTC.getTfSoTCQua();
		this.tfTimKiem = sinhVienTC.getTfTimKiem();
		this.radNam = sinhVienTC.getRadNam();
		this.radNu = sinhVienTC.getRadNu();
		this.sinhVienTC.loadData(table, dsSinhVien, "", "");

		setAction();
	}

	private void setAction() {
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				if (row >= 0) {
					tfIdSV.setText((String) table.getValueAt(row, 0));
					tfIdSV.setEnabled(false);
					tfHoTen.setText((String) table.getValueAt(row, 1));
					tfKhoa.setText((String) table.getValueAt(row, 2));
					tfNgaySinh.setText((String) table.getValueAt(row, 3));
					if (table.getValueAt(row, 4).equals("Nam")) {
						radNu.setSelected(false);
						radNam.setSelected(true);
					} else {
						radNam.setSelected(false);
						radNu.setSelected(true);
					}
					tfEmail.setText((String) table.getValueAt(row, 5));
					tfSoDT.setText((String) table.getValueAt(row, 6));
					tfDiaChi.setText((String) table.getValueAt(row, 7));
					tfDiemTB.setText((String) table.getValueAt(row, 8));
					tfSoTCQua.setText((String) table.getValueAt(row, 9));
					tfSoTCNo.setText((String) table.getValueAt(row, 10));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		radNam.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (radNu.isSelected())
					radNu.setSelected(false);
			}
		});
		radNu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (radNam.isSelected())
					radNam.setSelected(false);
			}
		});

		btnThem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SinhVienTinChi svtc = getSinhVienTC();
				if (svtc != null) {
					if (checkSV(svtc.getIdSinhVien())) {
						dsSinhVien.add(svtc);
						sinhVienTC.loadData(table, dsSinhVien, "", "");
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
				SinhVienTinChi svtc = getSinhVienTC();
				if (svtc != null) {
					for (int i = 0; i < dsSinhVien.size(); i++) {
						if (dsSinhVien.get(i).getIdSinhVien().equals(svtc.getIdSinhVien())) {
							dsSinhVien.get(i).setHoTen(svtc.getHoTen());
							dsSinhVien.get(i).setKhoa(svtc.getKhoa());
							dsSinhVien.get(i).setNgaySinh(svtc.getNgaySinh());
							dsSinhVien.get(i).setEmail(svtc.getEmail());
							dsSinhVien.get(i).setGioiTinh(svtc.getGioiTinh());
							dsSinhVien.get(i).setSoDT(svtc.getSoDT());
							dsSinhVien.get(i).setDiaChi(svtc.getDiaChi());
							dsSinhVien.get(i).setDiemTB(svtc.getDiemTB());
							dsSinhVien.get(i).setSoTCQua(svtc.getSoTCQua());
							dsSinhVien.get(i).setSoTCNo(svtc.getSoTCNo());
							break;
						}
					}
					sinhVienTC.loadData(table, dsSinhVien, "", "");
					JOptionPane.showMessageDialog(null, "Cập nhật thành công");
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
						for (int i = 0; i < dsSinhVien.size(); i++) {
							if (dsSinhVien.get(i).getIdSinhVien().equals(id)) {
								dsSinhVien.remove(i);
								sinhVienTC.loadData(table, dsSinhVien, "", "");
								JOptionPane.showMessageDialog(null, "Xóa thành công");
								return;
							}
						}
					}
				}
			}
		});

		btnHuy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cancel();
				sinhVienTC.loadData(table, dsSinhVien, "", "");
			}
		});
		btnTimKiem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String timKiem = timKiemCB.getSelectedItem().toString();
				String giaTri = tfTimKiem.getText().trim().toLowerCase();
				sinhVienTC.loadData(table, dsSinhVien, timKiem, giaTri);
			}
		});

		btnLuu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					printFile(dsSinhVien, fileName);
					JOptionPane.showMessageDialog(null, "Đã lưu");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Có lỗi xảy ra", "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
	}

	private void cancel() {
		tfIdSV.setText("");
		tfHoTen.setText("");
		tfKhoa.setText("");
		tfNgaySinh.setText("");
		tfEmail.setText("");
		tfSoDT.setText("");
		tfDiaChi.setText("");
		tfDiemTB.setText("");
		tfSoTCQua.setText("");
		tfSoTCNo.setText("");
		tfTimKiem.setText("");
		radNam.setSelected(false);
		radNu.setSelected(false);
	}

	private boolean checkSV(String id) {
		for (int i = 0; i < dsSinhVien.size(); i++) {
			if (dsSinhVien.get(i).getIdSinhVien().equals(id))
				return false;
		}
		return true;
	}

	private SinhVienTinChi getSinhVienTC() {
		String idSinhVien = tfIdSV.getText().trim().toUpperCase();
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
		int soTCNo;
		int soTCQua;
		double diemTB;
		try {
			soTCNo = Integer.parseInt(tfSoTCNo.getText().trim());
			soTCQua = Integer.parseInt(tfSoTCQua.getText().trim());
			diemTB = Double.parseDouble(tfDiemTB.getText().trim());
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Hãy kiểm tra các giá trị: Điểm TB, Số TC nợ, Số TC qua", "Error",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}

		SinhVienTinChi svtc = new SinhVienTinChi(idSinhVien, hoTen, khoa, ngaySinh, gioiTinh, email, soDT, diaChi,
				diemTB, soTCQua, soTCNo);
		return svtc;
	}

	private ArrayList<SinhVienTinChi> readFile(String fileName) throws IOException {
		ArrayList<SinhVienTinChi> dsSV = new ArrayList<>();
		FileInputStream inputStream = new FileInputStream(new File(fileName));

		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();

		Row nextRow;
		if (iterator.hasNext())
			nextRow = iterator.next(); // loại bỏ dòng tiêu đề
		while (iterator.hasNext()) {
			nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			ArrayList<String> dataSV = new ArrayList<>();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				String data = "";
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					data = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					data = Double.toString(cell.getNumericCellValue());
					break;
				default:
					data = "";
					break;
				}
				dataSV.add(data);
			}
			SinhVienTinChi sv = new SinhVienTinChi(dataSV.get(0), dataSV.get(1),
					Integer.toString((int) Double.parseDouble(dataSV.get(2))), dataSV.get(3), dataSV.get(4),
					dataSV.get(5), dataSV.get(6), dataSV.get(7), Double.parseDouble(dataSV.get(8)),
					(int) (Double.parseDouble(dataSV.get(9))), (int) Double.parseDouble(dataSV.get(10)));
			dsSV.add(sv);
		}

		workbook.close();
		inputStream.close();
		return dsSV;
	}

	private void printFile(ArrayList<SinhVienTinChi> dsSinhVien, String fileName) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		createHeader(sheet);
		int count = 0;

		for (SinhVienTinChi sv : dsSinhVien) {
			Row row = sheet.createRow(++count);
			writeSV(sv, row);
		}

		FileOutputStream fileout = new FileOutputStream(fileName);
		workbook.write(fileout);
	}

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

		Cell cellNgaySinh = row.createCell(4);
		cellNgaySinh.setCellStyle(cellStyle);
		cellNgaySinh.setCellValue("Ngày sinh");

		Cell cellGioiTinh = row.createCell(5);
		cellGioiTinh.setCellStyle(cellStyle);
		cellGioiTinh.setCellValue("Giới tính");

		Cell cellEmail = row.createCell(6);
		cellEmail.setCellStyle(cellStyle);
		cellEmail.setCellValue("Email");

		Cell cellSoDT = row.createCell(7);
		cellSoDT.setCellStyle(cellStyle);
		cellSoDT.setCellValue("Số ĐT");

		Cell cellDiaChi = row.createCell(8);
		cellDiaChi.setCellStyle(cellStyle);
		cellDiaChi.setCellValue("Địa chỉ");

		Cell cellDiemTB = row.createCell(9);
		cellDiemTB.setCellStyle(cellStyle);
		cellDiemTB.setCellValue("Điểm TB");

		Cell cellSoTCQua = row.createCell(10);
		cellSoTCQua.setCellStyle(cellStyle);
		cellSoTCQua.setCellValue("Số TC qua");

		Cell cellSoTCNo = row.createCell(11);
		cellSoTCNo.setCellStyle(cellStyle);
		cellSoTCNo.setCellValue("Số TC nợ");

	}

	private void writeSV(SinhVienTinChi sv, Row row) {
		Cell cell = row.createCell(1);
		cell.setCellValue(sv.getIdSinhVien());
		cell = row.createCell(2);
		cell.setCellValue(sv.getHoTen());
		cell = row.createCell(3);
		cell.setCellValue(sv.getKhoa());
		cell = row.createCell(4);
		cell.setCellValue(sv.getNgaySinh());
		cell = row.createCell(5);
		cell.setCellValue(sv.getGioiTinh());
		cell = row.createCell(6);
		cell.setCellValue(sv.getEmail());
		cell = row.createCell(7);
		cell.setCellValue(sv.getSoDT());
		cell = row.createCell(8);
		cell.setCellValue(sv.getDiaChi());
		cell = row.createCell(9);
		cell.setCellValue(sv.getDiemTB());
		cell = row.createCell(10);
		cell.setCellValue(sv.getSoTCQua());
		cell = row.createCell(11);
		cell.setCellValue(sv.getSoTCNo());
	}

	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}
}

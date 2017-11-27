package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.SinhVienNienChe;
import quanlysinhvien.model.SinhVienTinChi;
import quanlysinhvien.view.PanelSinhVienNienCheView;

public class SinhVienNienCheController {
	private JTable table;
	private JButton btnThem, btnSua, btnXoa, btnLuu, btnHuy, btnTimKiem;
	private JRadioButton radNam, radNu;
	private JComboBox<String> timKiemCB;
	private JTextField tfIdSV, tfHoTen, tfKhoa, tfNgaySinh, tfGioiTinh, tfEmail, tfSoDT, tfDiaChi, tfDiemTB, tfTongSoKy, tfTimKiem;
	private PanelSinhVienNienCheView sinhVienNC;
	private ArrayList<SinhVienNienChe> dsSinhVien;
	private String fileName;
	
	public SinhVienNienCheController(PanelSinhVienNienCheView sinhVienNC) {
		this.sinhVienNC = sinhVienNC;
//		dsSinhVien = new ArrayList<>();
		fileName = "C:\\Users\\tieu_nt\\Desktop\\Lập trình hướng đối tượng\\quanlysinhvien\\sinhviennienche\\dsSinhVienNC.xlsx";
		try {
			this.dsSinhVien = readFile(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.dsSinhVien = new ArrayList<>();
			e.printStackTrace();
		}
		this.table = sinhVienNC.getTable();
		this.radNam = sinhVienNC.getRadNam();
		this.radNu = sinhVienNC.getRadNu();
		this.btnThem = sinhVienNC.getBtnThem();
		this.btnSua = sinhVienNC.getBtnSua();
		this.btnXoa = sinhVienNC.getBtnXoa();
		this.btnLuu = sinhVienNC.getBtnLuu();
		this.btnHuy = sinhVienNC.getBtnHuy();
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
		this.sinhVienNC.loadData(table, dsSinhVien, "", "");
		
		setAction();
	}
	
	private void setAction() {
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				if(row >= 0) {
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
					tfTongSoKy.setText((String) table.getValueAt(row, 9));
				}
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
				SinhVienNienChe svnc = getSinhVienNC();
				if(svnc != null) {
					if (checkSV(svnc.getIdSinhVien())) {
						dsSinhVien.add(svnc);
						sinhVienNC.loadData(table, dsSinhVien, "", "");
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
					for (int i = 0; i < dsSinhVien.size(); i++) {
						if (dsSinhVien.get(i).getIdSinhVien().equals(svnc.getIdSinhVien())) {
							dsSinhVien.get(i).setHoTen(svnc.getHoTen());
							dsSinhVien.get(i).setKhoa(svnc.getKhoa());
							dsSinhVien.get(i).setNgaySinh(svnc.getNgaySinh());
							dsSinhVien.get(i).setEmail(svnc.getEmail());
							dsSinhVien.get(i).setGioiTinh(svnc.getGioiTinh());
							dsSinhVien.get(i).setSoDT(svnc.getSoDT());
							dsSinhVien.get(i).setDiaChi(svnc.getDiaChi());
							dsSinhVien.get(i).setDiemTB(svnc.getDiemTB());
							dsSinhVien.get(i).setTongSoKy(svnc.getTongSoKy());;
							break;
						}
					}
					sinhVienNC.loadData(table, dsSinhVien, "", "");
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
								sinhVienNC.loadData(table, dsSinhVien, "", "");
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
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				cancel();
				sinhVienNC.loadData(table, dsSinhVien, "", "");
			}
		});
		
		btnTimKiem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String timKiem = timKiemCB.getSelectedItem().toString();
				String giaTri = tfTimKiem.getText().trim().toLowerCase();
				sinhVienNC.loadData(table, dsSinhVien, timKiem, giaTri);
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
	
	private boolean checkSV(String id) {
		for (int i = 0; i < dsSinhVien.size(); i++) {
			if (dsSinhVien.get(i).getIdSinhVien().equals(id))
				return false;
		}
		return true;
	}
	
	private SinhVienNienChe getSinhVienNC() {
		String idSinhVien = tfIdSV.getText();
		String hoTen = tfHoTen.getText();
		String khoa = tfKhoa.getText();
		String ngaySinh = tfNgaySinh.getText();
		String gioiTinh = "";
		if (radNam.isSelected())
			gioiTinh = "Nam";
		else if (radNu.isSelected())
			gioiTinh = "Nữ";
		String email = tfEmail.getText();
		String soDT = tfSoDT.getText();
		String diaChi = tfDiaChi.getText();
		if (idSinhVien.equals("") || hoTen.equals("") || khoa.equals("") || ngaySinh.equals("") || gioiTinh.equals("")
				|| email.equals("") || soDT.equals("") || diaChi.equals("")) {
			JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		double diemTB;
		int tongSoKy;
		try {
			diemTB = Double.parseDouble(tfDiemTB.getText().trim());
			tongSoKy = Integer.parseInt(tfTongSoKy.getText().trim());
		}catch(Exception exc) {
			JOptionPane.showMessageDialog(null, "Hãy kiểm tra các giá trị: Điểm TB, Tổng số kỳ", "Error",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		SinhVienNienChe svnc = new SinhVienNienChe(idSinhVien, hoTen, khoa, ngaySinh, gioiTinh, email, soDT, diaChi, diemTB, tongSoKy);
		return svnc;
	}
	
	private void cancel() {
		table.getSelectionModel().clearSelection();
		timKiemCB.setSelectedIndex(0);
		tfIdSV.setText("");
		tfIdSV.setEnabled(true);
		tfHoTen.setText("");
		tfKhoa.setText("");
		tfNgaySinh.setText("");
		radNam.setSelected(false);
		radNu.setSelected(false);
		tfEmail.setText("");
		tfSoDT.setText("");
		tfDiaChi.setText("");
		tfTongSoKy.setText("");
		tfDiemTB.setText("");
		tfTimKiem.setText("");
	}
	
	private ArrayList<SinhVienNienChe> readFile(String fileName) throws IOException {
		ArrayList<SinhVienNienChe> dsSV = new ArrayList<>();
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
			SinhVienNienChe sv = new SinhVienNienChe(dataSV.get(0), dataSV.get(1),
					Integer.toString((int) Double.parseDouble(dataSV.get(2))), dataSV.get(3), dataSV.get(4),
					dataSV.get(5), dataSV.get(6), dataSV.get(7), Double.parseDouble(dataSV.get(8)),
					(int) (Double.parseDouble(dataSV.get(9))));
			dsSV.add(sv);
		}

		workbook.close();
		inputStream.close();
		return dsSV;
	}

	private void printFile(ArrayList<SinhVienNienChe> dsSinhVien, String fileName) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		createHeader(sheet);
		int count = 0;

		for (SinhVienNienChe sv : dsSinhVien) {
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
		cellSoTCQua.setCellValue("Tổng số kỳ");

	}

	private void writeSV(SinhVienNienChe sv, Row row) {
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
		cell.setCellValue(sv.getTongSoKy());
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

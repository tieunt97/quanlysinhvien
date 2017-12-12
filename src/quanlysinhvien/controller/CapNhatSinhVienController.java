package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.LopChuyenNganh;
import quanlysinhvien.model.LopHocPhan;
import quanlysinhvien.model.SinhVien;
import quanlysinhvien.model.SinhVienNienChe;
import quanlysinhvien.model.SinhVienTinChi;
import quanlysinhvien.view.CapNhatSinhVienLCNView;

public class CapNhatSinhVienController {
	private CapNhatSinhVienLCNView capNhatSV;
	private JTable table;
	private JButton btnThem, btnXoa;
	private JTextField tfIdSinhVien;
	private JComboBox<String> loaiSVCB;
	private LopChuyenNganh lopCN;
	private LopHocPhan lopHP;
	private String fileName;

	public CapNhatSinhVienController(CapNhatSinhVienLCNView capNhatSV, LopChuyenNganh lopCN, LopHocPhan lopHP, String fileName) {
		this.capNhatSV = capNhatSV;
		this.fileName = fileName;
		this.table = capNhatSV.getTable();
		this.btnThem = capNhatSV.getBtnThem();
		this.btnXoa = capNhatSV.getBtnXoa();
		this.tfIdSinhVien = capNhatSV.getTfIdSinhVien();
		this.loaiSVCB = capNhatSV.getLoaiSVCB();
		if(lopCN != null) {
			this.lopCN = lopCN;
			this.capNhatSV.loadData(table, lopCN.getDsSinhVien());
		}
		if(lopHP != null) {
			this.lopHP = lopHP;
			this.capNhatSV.loadData(table, lopHP.getDsSinhVien());
		}
		if (lopHP != null)
			this.lopHP = lopHP;

		setAction();
	}

	//bắt sự kiện
	private void setAction() {
		btnThem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String idSV = tfIdSinhVien.getText().toUpperCase().trim();
				String loaiSinhVien = (String) loaiSVCB.getSelectedItem();
				if (idSV.equals("")) {
					JOptionPane.showMessageDialog(null, "Mã sinh viên trống", "Error insert",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				SinhVien sv = null;
				try {
					sv = getSinhVien(loaiSinhVien, idSV);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (sv == null) {
					JOptionPane.showMessageDialog(null, "Sinh viên không tồn tại", "Error insert",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else {
					boolean ck = false;
					if (lopCN != null) {
						//thêm sinh viên lớp chuyên ngành
						 if(sv.getTenLop().equals("null")) {
							 ck = lopCN.themSinhVien(sv);
							 sv.setTenLop(lopCN.getTenLop());
							 try {
								 //thêm sinh viên vào file
								addSV(sv, fileName);
								//cập nhật lớp sinh viên
								updateLopSV(idSV, lopCN.getTenLop(), loaiSinhVien);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						 }else {
							 JOptionPane.showMessageDialog(null,
										"Sinh viên đang thuộc lớp: " + sv.getTenLop()
												+ "\nCần xóa sinh viên khỏi lớp trước khi thêm vào lớp mới",
										"Warning", JOptionPane.WARNING_MESSAGE);
								return;
						 }
						 if(ck) {
							//thêm sinh viên vào bảng danh sách sinh viên của lớp
								((DefaultTableModel) table.getModel()).addRow(new Object[] { sv.getIdSinhVien(), sv.getHoTen(),
										sv.getKhoa(), sv.getTenLop(), sv.getNgaySinh(), sv.getGioiTinh(), sv.getEmail(),
										sv.getSoDT(), sv.getDiaChi(), sv.getDiemTB() + "" });
							 JOptionPane.showMessageDialog(null, "Thêm thành công");
						 }else
							 JOptionPane.showMessageDialog(null, "Trùng mã sinh viên");
						 return;
					} 
					if(lopHP != null) {
						ck = lopHP.themSinhVien(sv, ""); 	//thêm sinh viên ko quan tâm max SV của lớp học phần
						if(ck) {
							try {
								//thêm sinh viên vào file dsSinhVien của lớp
								addSV(sv, fileName);
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							//thêm sinh viên vào bảng danh sách sinh viên của lớp
							((DefaultTableModel) table.getModel()).addRow(new Object[] { sv.getIdSinhVien(), sv.getHoTen(),
									sv.getKhoa(), sv.getTenLop(), sv.getNgaySinh(), sv.getGioiTinh(), sv.getEmail(),
									sv.getSoDT(), sv.getDiaChi(), sv.getDiemTB() + "" });
							if(loaiSinhVien.equalsIgnoreCase("Sinh viên niên chế"))
								try {
									addLopTKB(idSV, lopHP);		//thêm lớp vào tkb svnc
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							JOptionPane.showMessageDialog(null, "Thêm thành công");
						}
						return;
					}
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
						boolean ck = false;
						if(lopCN != null) {
							ck = lopCN.xoaSinhVien(id);
							if(ck) {
								try {
									//xóa sinh viên trong file dsSinhVien của lớp
									deleteSV(id, fileName);
									//cập nhật lại lớp SV
									if(!updateLopSV(id, "null", "Sinh viên tín chỉ"))
										updateLopSV(id, "null", "Sinh viên niên chế");
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								//xóa sinh viên khỏi bảng
								((DefaultTableModel) table.getModel()).removeRow(row);
								JOptionPane.showMessageDialog(null, "Xóa thành công");
							}else {
								JOptionPane.showMessageDialog(null, "Xóa lỗi", "Error delete", JOptionPane.ERROR_MESSAGE);
							}
							
							return;
						}
						if(lopHP != null) {
							ck = lopHP.xoaSinhVien(id);
							if(ck) {
								try {
									deleteSV(id, fileName);
									deleteLopTKB(id, lopHP.getIdLop());
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								//xóa sinh viên khỏi bảng
								((DefaultTableModel) table.getModel()).removeRow(row);
								JOptionPane.showMessageDialog(null, "Xóa thành công");
							}else {
								JOptionPane.showMessageDialog(null, "Xóa lỗi", "Error delete", JOptionPane.ERROR_MESSAGE);
							}
							return;
						}
						cancel();
					}
				}
			}
		});
	}

	//reset input
	private void cancel() {
		tfIdSinhVien.setText("");
	}

	//lấy dữ liệu sinh viên
	private SinhVien getSinhVien(String loaiSinhVien, String idSV) throws IOException {
		SinhVien sv = null;
		String fileName = "";
		if (loaiSinhVien.equals("Sinh viên tín chỉ")) {
			fileName = "quanlysinhvien\\sinhvientinchi\\dsSinhVienTC.xlsx";
		}
		if (loaiSinhVien.equals("Sinh viên niên chế")) {
			fileName = "quanlysinhvien\\sinhviennienche\\dsSinhVienNC.xlsx";
		}
		FileInputStream fin = new FileInputStream(new File(fileName));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		Row nextRow;
		if (iterator.hasNext()) {			//bỏ qua tiêu đề
			nextRow = iterator.next();
		}
		while (iterator.hasNext()) {
			nextRow = iterator.next();
			ArrayList<String> dataSV = new ArrayList<>();
			Iterator<Cell> itrCell = nextRow.iterator();
			while(itrCell.hasNext()){
				Cell cell = itrCell.next();
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
				if(dataSV.size() < 1) return null;
			}
			
			if(dataSV.get(0).equalsIgnoreCase(idSV)){
				if(loaiSinhVien.equalsIgnoreCase("Sinh viên tín chỉ")){
					sv = new SinhVienTinChi(dataSV.get(0), dataSV.get(1), dataSV.get(2), dataSV.get(3), dataSV.get(4), dataSV.get(5), dataSV.get(6), dataSV.get(7), dataSV.get(8), Double.parseDouble(dataSV.get(9)), (int) Double.parseDouble(dataSV.get(10)), (int) Double.parseDouble(dataSV.get(11)));
				}else if(loaiSinhVien.equalsIgnoreCase("Sinh viên niên chế")){
					sv = new SinhVienNienChe(dataSV.get(0), dataSV.get(1), dataSV.get(2), dataSV.get(3), dataSV.get(4), dataSV.get(5), dataSV.get(6), dataSV.get(7), dataSV.get(8), Double.parseDouble(dataSV.get(9)), (int) Double.parseDouble(dataSV.get(10)), (int) Double.parseDouble(dataSV.get(11)));
				}
				return sv;
			}
		}
		workbook.close();
		fin.close();

		return sv;
	}

	//tạo tiêu đề cho file dsSinhVien
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
	}

	//ghi dòng dữ liệu sinh viên vào file dsSinhVien
	private void writeSV(SinhVien sv, Row row) {
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
	}

	//thêm sinh viên vào file dsSinhVien của lớp
	private void addSV(SinhVien sv, String fileName) throws IOException {
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
			writeSV(sv, row);
		}

		FileOutputStream fout = new FileOutputStream(new File(fileName));
		workbook.write(fout);
		fout.close();
	}

	//xóa sinh viên khỏi file dsSinhVien của lớp
	private boolean deleteSV(String idSinhVien, String fileName) throws IOException {
		boolean ck = false;
		FileInputStream fin = new FileInputStream(new File(fileName));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);

		Iterator<Row> iterator = sheet.iterator();

		Row nextRow = null;
		if (iterator.hasNext())
			nextRow = iterator.next(); // loại bỏ dòng tiêu đề
		int i = 0;
		while (iterator.hasNext()) {
			nextRow = iterator.next();
			i++;
			Cell cell = nextRow.getCell(1);
			String idSV = cell.getStringCellValue();
			if (idSV.equalsIgnoreCase(idSinhVien)) {
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

	//cập nhật tên lớp chuyên ngành cho sinh viên
	private boolean updateLopSV(String idSinhVien, String tenLop, String loaiSV) throws IOException {
		boolean ck = false;
		String fileName = "";
		if (loaiSV.equals("Sinh viên tín chỉ"))
			fileName = "quanlysinhvien\\sinhvientinchi\\dsSinhVienTC.xlsx";
		else if (loaiSV.equals("Sinh viên niên chế"))
			fileName = "quanlysinhvien\\sinhviennienche\\dsSinhVienNC.xlsx";
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
			if (idSV.equalsIgnoreCase(idSinhVien)) {
				cell = nextRow.createCell(4);
				cell.setCellValue(tenLop);
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

	//tạo tiêu đề tkb cho sinh viên niên chế
	private void createHeaderTKB(Sheet sheet) {
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		cellStyle.setFont(font);

		Row row = sheet.createRow(0);

		Cell cellHocKy = row.createCell(1);
		cellHocKy.setCellStyle(cellStyle);
		cellHocKy.setCellValue("Học kỳ");

		Cell cellIdLop = row.createCell(2);
		cellIdLop.setCellStyle(cellStyle);
		cellIdLop.setCellValue("Mã lớp");

		Cell cellLoaiLop = row.createCell(3);
		cellLoaiLop.setCellStyle(cellStyle);
		cellLoaiLop.setCellValue("Loại lớp");

		Cell cellIdHocPhan = row.createCell(4);
		cellIdHocPhan.setCellStyle(cellStyle);
		cellIdHocPhan.setCellValue("Mã học phần");

		Cell cellTenLop = row.createCell(5);
		cellTenLop.setCellStyle(cellStyle);
		cellTenLop.setCellValue("Tên lớp");

		Cell cellThoiGian = row.createCell(6);
		cellThoiGian.setCellStyle(cellStyle);
		cellThoiGian.setCellValue("Thời gian");

		Cell cellTuanHoc = row.createCell(7);
		cellTuanHoc.setCellStyle(cellStyle);
		cellTuanHoc.setCellValue("Tuần học");

		Cell cellPhongHoc = row.createCell(8);
		cellPhongHoc.setCellStyle(cellStyle);
		cellPhongHoc.setCellValue("Phòng học");

		Cell cellTenGV = row.createCell(9);
		cellTenGV.setCellStyle(cellStyle);
		cellTenGV.setCellValue("Tên giảng viên");

		Cell cellSoSVMax = row.createCell(10);
		cellSoSVMax.setCellStyle(cellStyle);
		cellSoSVMax.setCellValue("Số SV max");

		Cell cellSoSVHT = row.createCell(11);
		cellSoSVHT.setCellStyle(cellStyle);
		cellSoSVHT.setCellValue("Số SV hiện tại");
	}

	//ghi dòng dữ liệu tkb ứng với dòng row
	private void writeLopTKB(LopHocPhan lopHP, Row row) {
		Cell cell = row.createCell(1);
		cell.setCellValue(lopHP.getHocKy());
		cell = row.createCell(2);
		cell.setCellValue(lopHP.getIdLop());
		cell = row.createCell(3);
		cell.setCellValue(lopHP.getLoaiLop());
		cell = row.createCell(4);
		cell.setCellValue(lopHP.getIdHocPhan());
		cell = row.createCell(5);
		cell.setCellValue(lopHP.getTenLop());
		cell = row.createCell(6);
		cell.setCellValue(lopHP.getThoiGian());
		cell = row.createCell(7);
		cell.setCellValue(lopHP.getTuanHoc());
		cell = row.createCell(8);
		cell.setCellValue(lopHP.getPhongHoc());
		cell = row.createCell(9);
		cell.setCellValue(lopHP.getTenGiangVien());
		cell = row.createCell(10);
		cell.setCellValue(lopHP.getSoSVMax());
		cell = row.createCell(11);
		cell.setCellValue(lopHP.getSoSVHienTai());
	}

	//thêm lớp vào tkb của sinh viên niên chế
	private void addLopTKB(String idSV, LopHocPhan lopHP) throws IOException {
		String fileName = "quanlysinhvien\\sinhviennienche\\" + idSV + "\\tkb.xlsx";
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
			createHeaderTKB(sheet);
			row = sheet.createRow(1);
		} else {
			row = sheet.createRow(lastRow + 1);
		}
		if (row != null) {
			writeLopTKB(lopHP, row);
		}

		FileOutputStream fout = new FileOutputStream(new File(fileName));
		workbook.write(fout);
		fout.close();
	}

	//xóa lớp tkb sinh viên niên chế
	private boolean deleteLopTKB(String idSV, String idLop) throws IOException {
		boolean ck = false;
		String fileName = "quanlysinhvien\\sinhviennienche\\" + idSV + "\\tkb.xlsx";
		FileInputStream fin = new FileInputStream(new File(fileName));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);

		Iterator<Row> iterator = sheet.iterator();

		Row nextRow = null;
		if (iterator.hasNext())
			nextRow = iterator.next(); // loại bỏ dòng tiêu đề
		int i = 0;
		while (iterator.hasNext()) {
			nextRow = iterator.next();
			i++;
			Cell cell = nextRow.getCell(2);
			String idLopHP = cell.getStringCellValue();
			if (idLopHP.equalsIgnoreCase(idLop)) {
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

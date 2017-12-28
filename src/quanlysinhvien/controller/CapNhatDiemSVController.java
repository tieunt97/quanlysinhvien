package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import quanlysinhvien.model.DiemHocPhan;
import quanlysinhvien.model.HocPhan;
import quanlysinhvien.model.LopHocPhan;
import quanlysinhvien.model.QuanLy;
import quanlysinhvien.model.SinhVien;
import quanlysinhvien.model.SinhVienNienChe;
import quanlysinhvien.model.SinhVienTinChi;
import quanlysinhvien.view.CapNhatDiemSVView;

public class CapNhatDiemSVController {
	private JTable table;
	private JTextField tfIdHocPhan, tfIdLopHoc, tfDiemQT, tfDiemThi;
	private JButton btnThem, btnSua, btnXoa, btnHuy;
	private JComboBox<String> hocKyCB;
	private CapNhatDiemSVView capNhatDiemSV;
	private SinhVien sv;
	private QuanLy quanLy;
	
	public CapNhatDiemSVController(CapNhatDiemSVView capNhatDiemSV, SinhVien sv, QuanLy quanLy) {
		this.capNhatDiemSV = capNhatDiemSV;
		this.sv = sv;
		this.quanLy = quanLy;
		this.table = capNhatDiemSV.getTable();
		this.tfIdHocPhan = capNhatDiemSV.getTfIdHocPhan();
		this.tfIdLopHoc = capNhatDiemSV.getTfIdLopHoc();
		this.tfDiemQT = capNhatDiemSV.getTfDiemQT();
		this.tfDiemThi = capNhatDiemSV.getTfDiemThi();
		this.btnThem = capNhatDiemSV.getBtnThem();
		this.btnSua = capNhatDiemSV.getBtnSua();
		this.btnXoa = capNhatDiemSV.getBtnXoa();
		this.btnHuy = capNhatDiemSV.getBtnHuy();
		this.hocKyCB = capNhatDiemSV.getHocKyCB();
		this.capNhatDiemSV.loadData(table, sv.getDsDiemHP());

		setAction();
	}

	
	//bắt sự kiện
	private void setAction() {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				if (row >= 0) {
					hocKyCB.setSelectedItem(table.getValueAt(row, 0));
					tfIdHocPhan.setText((String) table.getValueAt(row, 1));
					tfIdHocPhan.setEnabled(false);
					tfIdLopHoc.setText((String) table.getValueAt(row, 4));
					tfDiemQT.setText((String) table.getValueAt(row, 5));
					tfDiemThi.setText((String) table.getValueAt(row, 6));
				}
			}
		});

		btnThem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DiemHocPhan diemHP = getDiemHP();
				if (diemHP != null) {
					HocPhan hp = diemHP.getHocPhan();
					String idHocPhan = hp.getIdHocPhan();
					if (quanLy.getHocPhan(idHocPhan.toUpperCase()) != null) {
						System.out.println("ABC");
						sv.getDsDiemHP().add(diemHP);
						//thêm điểm học phần của sinh viên lên bảng điểm
						((DefaultTableModel) table.getModel()).addRow(new Object[] { diemHP.getHocKy(), hp.getIdHocPhan(), hp.getTenHP(),
						hp.getSoTinChi() + "", diemHP.getIdLopHoc(), diemHP.getDiemQT() + "",
						diemHP.getDiemThi() + "", diemHP.getDiemChu(), diemHP.getDiemThang4() + "" });
						try {
							addDiemSV(diemHP);
							if (sv instanceof SinhVienNienChe && diemHP.getDiemThang10() < 5.0) {
								//thêm học phần vào danh sách học phần nợ của sinh viên
								((SinhVienNienChe) sv).themHocPhanNo(hp);
								addHocPhanNo("quanlysinhvien\\sinhviennienche\\" + sv.getIdSinhVien() + "\\hocPhanNo.xlsx",
										idHocPhan);
							}
//							updateDiemCTDT(diemHP);
							JOptionPane.showMessageDialog(null, "Thêm thành công");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						cancel();
					} else {
						JOptionPane.showMessageDialog(null, "Trùng mã học phần", "Error insert",
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
				DiemHocPhan diemHP = getDiemHP();
				HocPhan hocPhan = diemHP.getHocPhan();
				double diemThang4 = 0;
				if (diemHP != null) {
					for (int i = 0; i < sv.getDsDiemHP().size(); i++) {
						HocPhan hp = sv.getDsDiemHP().get(i).getHocPhan();
						if (hp.getIdHocPhan().equals(hocPhan.getIdHocPhan())) {
							diemThang4 = sv.getDsDiemHP().get(i).getDiemThang4();
							sv.getDsDiemHP().get(i).setHocKy(diemHP.getHocKy());
							hp.setTenHP(hocPhan.getTenHP());
							hp.setSoTinChi(hocPhan.getSoTinChi());
							sv.getDsDiemHP().get(i).setIdLopHoc(diemHP.getIdLopHoc());
							sv.getDsDiemHP().get(i).setDiemQT(diemHP.getDiemQT());
							sv.getDsDiemHP().get(i).setDiemThi(diemHP.getDiemThi());
							if(sv instanceof SinhVienTinChi) {
								sv.getDsDiemHP().get(i).setDiemChu(diemHP.getDiemChu());
								sv.getDsDiemHP().get(i).setDiemThang4(diemHP.getDiemThang4());
							}
							if(sv instanceof SinhVienNienChe) {
								sv.getDsDiemHP().get(i).setDiemThang10(diemHP.getDiemThang10());
							}
							break;
						}
					}
					updateRowTable(diemHP, row); //cập nhật lại giá trị của hàng trong bảng - ứng với điểm sinh viên đc sửa
					boolean ck = false;
					try {
						ck = updateDiemHP(diemHP);
						if(sv instanceof SinhVienNienChe && diemThang4 < 5.0 && diemHP.getDiemThang4() >= 5.0) {
							//xóa học phần nợ khi đã qua
							((SinhVienNienChe) sv).xoaHocPhanNo(diemHP.getHocPhan().getIdHocPhan());
							deleteHocPhanNo("quanlysinhvien\\sinhviennienche\\" + sv.getIdSinhVien() + "\\hocPhanNo.xlsx",
									diemHP.getHocPhan().getIdHocPhan());
						}
//						updateDiemCTDT(diemHP);
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
						String id = (String) table.getValueAt(row, 1);
						double diemTB = Double.parseDouble((String) table.getValueAt(row, 8));
						for (int i = 0; i < sv.getDsDiemHP().size(); i++) {
							if (sv.getDsDiemHP().get(i).getHocPhan().getIdHocPhan().equals(id)) {
								boolean ck = false;
								try {
									ck = deleteDiemHP(id);
									if (diemTB == 0.0)
										if (deleteHocPhanNo(
												"quanlysinhvien\\sinhviennienche\\" + sv.getIdSinhVien() + "\\hocPhanNo.xlsx", id))
											System.out.println("delete hocPhanNo");
//									updateDiemCTDT(new DiemHocPhan("", id, "", -1, "", 0, 0, "", -1));
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								sv.getDsDiemHP().remove(i);
								((DefaultTableModel) table.getModel()).removeRow(row); //xía hàng tương ứng trên bảng
								if (ck)
									JOptionPane.showMessageDialog(null, "Xóa thành công");
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
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cancel();
			}
		});

	}

	//cập nhật giá trị của hàng trong bảng
	private void updateRowTable(DiemHocPhan diem, int row) {
		table.setValueAt(diem.getHocKy(), row, 0);
		table.setValueAt(diem.getHocPhan().getTenHP(), row, 2);
		table.setValueAt(diem.getHocPhan().getSoTinChi() + "", row, 3);
		table.setValueAt(diem.getIdLopHoc(), row, 4);
		table.setValueAt(diem.getDiemQT() + "", row, 5);
		table.setValueAt(diem.getDiemThi() + "", row, 6);
		table.setValueAt(diem.getDiemChu(), row, 7);
		table.setValueAt(diem.getDiemThang4() + "", row, 8);
	}

	//reset input
	private void cancel() {
		table.getSelectionModel().clearSelection();
		tfIdHocPhan.setText("");
		tfIdHocPhan.setEnabled(true);
		tfIdLopHoc.setText("");
		tfDiemQT.setText("");
		tfDiemThi.setText("");
	}

	//lấy điểm học phần từ input
	private DiemHocPhan getDiemHP() {
		String hocKy = (String) hocKyCB.getSelectedItem();
		String idLopHoc = tfIdLopHoc.getText().trim().toUpperCase();
		String idHocPhan = tfIdHocPhan.getText().trim().toUpperCase();
		if (hocKy.equals("") || idLopHoc.equals("")) {
			JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		double diemQT, diemThi;
		try {
			diemQT = Double.parseDouble(tfDiemQT.getText().trim());
			diemThi = Double.parseDouble(tfDiemThi.getText().trim());
		} catch (NumberFormatException exc) {
			JOptionPane.showMessageDialog(null, "Kiểm tra lại giá trị Điểm QT, Điểm thi", "Error",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		HocPhan hocPhan = quanLy.getHocPhan(idHocPhan);
		if(quanLy.getLopHocPhan(idLopHoc) == null) {
			JOptionPane.showMessageDialog(null, "Lop hoc ko ton tai!!!", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		DiemHocPhan diem = new DiemHocPhan(hocKy, hocPhan, idLopHoc, diemQT, diemThi);
		return diem;
	}
	

	//thêm điểm học phần vào file điểm của sinh viên
	private void addDiemSV(DiemHocPhan diemHP) throws IOException {
		Workbook workbook = null;
		Sheet sheet = null;
		int lastRow = -1;
		String fileName = "";
		try {
			if (sv instanceof SinhVienTinChi)
				fileName = "quanlysinhvien\\sinhvientinchi\\" + sv.getIdSinhVien() + "\\diem.xlsx";
			else if (sv instanceof SinhVienNienChe)
				fileName = "quanlysinhvien\\sinhviennienche\\" + sv.getIdSinhVien() + "\\diem.xlsx";
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
		if (lastRow < 0) { //tạo tiêu đề nếu file chưa có dữ liệu
			createHeader(sheet);
			row = sheet.createRow(1);
		} else {
			row = sheet.createRow(lastRow + 1);
		}
		if (row != null) {
			writeDiemHP(diemHP, row);
		}

		FileOutputStream fout = new FileOutputStream(new File(fileName));
		workbook.write(fout);
		fout.close();
	}

	//tạo tiêu đề file điểm học phần của sinh viên
	private void createHeader(Sheet sheet) {
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		cellStyle.setFont(font);

		Row row = sheet.createRow(0);

		Cell cellHocKy = row.createCell(1);
		cellHocKy.setCellStyle(cellStyle);
		cellHocKy.setCellValue("Học kỳ");

		Cell cellIdHP = row.createCell(2);
		cellIdHP.setCellStyle(cellStyle);
		cellIdHP.setCellValue("Mã học phần");

		Cell cellTenHP = row.createCell(3);
		cellTenHP.setCellStyle(cellStyle);
		cellTenHP.setCellValue("Tên học phần");

		Cell cellSoTC = row.createCell(4);
		cellSoTC.setCellStyle(cellStyle);
		cellSoTC.setCellValue("Số TC");

		Cell cellIdLop = row.createCell(5);
		cellIdLop.setCellStyle(cellStyle);
		cellIdLop.setCellValue("Mã lớp");

		Cell cellDiemQT = row.createCell(6);
		cellDiemQT.setCellStyle(cellStyle);
		cellDiemQT.setCellValue("Điểm QT");

		Cell cellDiemThi = row.createCell(7);
		cellDiemThi.setCellStyle(cellStyle);
		cellDiemThi.setCellValue("Điểm thi");

		if(sv instanceof SinhVienTinChi) {
			Cell cellDiemChu = row.createCell(8);
			cellDiemChu.setCellStyle(cellStyle);
			cellDiemChu.setCellValue("Điểm chữ");
			
			Cell cellDiem4 = row.createCell(9);
			cellDiem4.setCellStyle(cellStyle);
			cellDiem4.setCellValue("Điểm thang 4");
		}
		if(sv instanceof SinhVienNienChe) {
			Cell cellDiem10 = row.createCell(8);
			cellDiem10.setCellStyle(cellStyle);
			cellDiem10.setCellValue("Điểm TB");
		}
	}

	//cập nhật điểm học phần của sinh viên trong file điểm
	private boolean updateDiemHP(DiemHocPhan diemHP) throws FileNotFoundException, IOException {
		String fileName = "";
		HocPhan hp = diemHP.getHocPhan();
		boolean ck = false;
		if (sv instanceof SinhVienTinChi)
			fileName = "quanlysinhvien\\sinhvientinchi\\" + sv.getIdSinhVien() + "\\diem.xlsx";
		else if (sv instanceof SinhVienNienChe)
			fileName = "quanlysinhvien\\sinhviennienche\\" + sv.getIdSinhVien() + "\\diem.xlsx";
		FileInputStream fin = new FileInputStream(new File(fileName));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();

		Row nextRow;
		if (iterator.hasNext())
			nextRow = iterator.next(); // loại bỏ dòng tiêu đề
		while (iterator.hasNext()) {
			nextRow = iterator.next();
			Cell cell = nextRow.getCell(2);
			String idHocPhan = cell.getStringCellValue();
			if (idHocPhan.equalsIgnoreCase(hp.getIdHocPhan())) {
				cell = nextRow.createCell(1);
				cell.setCellValue(diemHP.getHocKy());
				cell = nextRow.createCell(3);
				cell.setCellValue(hp.getTenHP());
				cell = nextRow.createCell(4);
				cell.setCellValue(hp.getSoTinChi());
				cell = nextRow.createCell(5);
				cell.setCellValue(diemHP.getIdLopHoc());
				cell = nextRow.createCell(6);
				cell.setCellValue(diemHP.getDiemQT());
				cell = nextRow.createCell(7);
				cell.setCellValue(diemHP.getDiemThi());
				if(sv instanceof SinhVienTinChi) {
					cell = nextRow.createCell(8);
					cell.setCellValue(diemHP.getDiemChu());
					cell = nextRow.createCell(9);
					cell.setCellValue(diemHP.getDiemThang4());
				}
				if(sv instanceof SinhVienNienChe) {
					cell = nextRow.createCell(8);
					cell.setCellValue(diemHP.getDiemThang10());
				}
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

	//xóa điểm học phần sinh viên
	private boolean deleteDiemHP(String idHocPhan) throws IOException {
		boolean ck = false;
		String fileName = "";
		if (sv instanceof SinhVienTinChi)
			fileName = "quanlysinhvien\\sinhvientinchi\\" + sv.getIdSinhVien() + "\\diem.xlsx";
		else if (sv instanceof SinhVienNienChe)
			fileName = "quanlysinhvien\\sinhviennienche\\" + sv.getIdSinhVien() + "\\diem.xlsx";

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
			String idHP = cell.getStringCellValue();
			if (idHP.equalsIgnoreCase(idHocPhan)) {
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

	//ghi dòng dữ liệu diểm học phần tương ứng với row trong file điểm 
	private void writeDiemHP(DiemHocPhan diemHP, Row row) {
		Cell cell = row.createCell(1);
		cell.setCellValue(diemHP.getHocKy());
		cell = row.createCell(2);
		cell.setCellValue(diemHP.getHocPhan().getIdHocPhan());
		cell = row.createCell(3);
		cell.setCellValue(diemHP.getHocPhan().getTenHP());
		cell = row.createCell(4);
		cell.setCellValue(diemHP.getHocPhan().getSoTinChi());
		cell = row.createCell(5);
		cell.setCellValue(diemHP.getIdLopHoc());
		cell = row.createCell(6);
		cell.setCellValue(diemHP.getDiemQT());
		cell = row.createCell(7);
		cell.setCellValue(diemHP.getDiemThi());
		if(sv instanceof SinhVienTinChi) {
			cell = row.createCell(8);
			cell.setCellValue(diemHP.getDiemChu());
			cell = row.createCell(9);
			cell.setCellValue(diemHP.getDiemThang4());
		}
		if(sv instanceof SinhVienNienChe) {
			cell = row.createCell(8);
			cell.setCellValue(diemHP.getDiemThang10());
		}
	}

	//thêm vào danh sách học phần nợ của sinh viên niên chế
	private void addHocPhanNo(String fileName, String idHP) throws IOException {
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
			createHeaderHPNo(sheet);
			row = sheet.createRow(1);
		} else {
			row = sheet.createRow(lastRow + 1);
		}
		if (row != null) {
			writeHPNo(idHP, row);
		}

		FileOutputStream fout = new FileOutputStream(new File(fileName));
		workbook.write(fout);
		fout.close();

	}

	//xóa học phần nợ tương ứng idHocPhan trong file học phần nợ svnc
	private boolean deleteHocPhanNo(String fileName, String idHocPhan) throws IOException {
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
			String idHP = cell.getStringCellValue();
			if (idHP.equalsIgnoreCase(idHocPhan)) {
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

	//tạo tiêu đề file hocPhanNo svnc
	private void createHeaderHPNo(Sheet sheet) {
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		cellStyle.setFont(font);

		Row row = sheet.createRow(0);

		Cell cellHocKy = row.createCell(1);
		cellHocKy.setCellStyle(cellStyle);
		cellHocKy.setCellValue("Mã học phần");
	}

	private void writeHPNo(String idHP, Row row) {
		Cell cell = row.createCell(1);
		cell.setCellValue(idHP);
	}
//	
//	private boolean updateDiemCTDT(DiemHocPhan diem) throws IOException {
//		boolean ck = false;
//		String fileName = "";
//		if(loaiSV.equalsIgnoreCase("svtc"))
//			fileName = "quanlysinhvien\\sinhvientinchi\\" + idSV + "\\ctdt.xlsx";
//		else fileName = "quanlysinhvien\\sinhviennienche\\" + idSV + "\\ctdt.xlsx";
//		FileInputStream fin = new FileInputStream(new File(fileName));
//		Workbook workbook = new XSSFWorkbook(fin);
//		Sheet sheet = workbook.getSheetAt(0);
//		Row nextRow;
//		Iterator<Row> iterator = sheet.iterator();
//		if(iterator.hasNext())
//			nextRow = iterator.next();
//		while(iterator.hasNext()) {
//			nextRow = iterator.next();
//			Cell cell = nextRow.getCell(1);
//			if(diem.getHocPhan().getIdHocPhan().equalsIgnoreCase(cell.getStringCellValue())) {
//				cell = nextRow.getCell(4);
//				if(diem.getHocPhan().getSoTinChi() == -1)
//					cell.setCellValue("");
//				else cell.setCellValue(diem.getHocPhan().getSoTinChi());
//				cell = nextRow.getCell(5);
//				if(diem.getDiemChu() == "") cell.setCellValue("");
//				else
//					cell.setCellValue(diem.getDiemChu());
//				cell = nextRow.getCell(6);
//				if(diem.getDiemThang4() == -1)
//					cell.setCellValue("");
//				else
//					cell.setCellValue(diem.getDiemThang4());
//				ck = true;
//			}
//		}
//		fin.close();
//		FileOutputStream fout = new FileOutputStream(new File(fileName));
//		workbook.write(fout);
//		fout.close();
//		return ck;
//	}
}

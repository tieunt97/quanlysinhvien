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
import quanlysinhvien.model.SinhVien;
import quanlysinhvien.view.CapNhatDiemSVView;

public class CapNhatDiemSVController {
	private JTable table;
	private JTextField tfIdHocPhan, tfIdLopHoc, tfDiemQT, tfDiemThi;
	private JButton btnThem, btnSua, btnXoa, btnHuy;
	private JComboBox<String> hocKyCB;
	private CapNhatDiemSVView capNhatDiemSV;
	private ArrayList<DiemHocPhan> dsDiemHP;
	private String idSV, loaiSV;

	public CapNhatDiemSVController(CapNhatDiemSVView capNhatDiemSV, String idSV, String loaiSV) {
		this.capNhatDiemSV = capNhatDiemSV;
		this.idSV = idSV;
		this.loaiSV = loaiSV;
		try {
			dsDiemHP = readFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			dsDiemHP = new ArrayList<>();
			System.out.println("Error diemHP: " + e);
		}
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
		this.capNhatDiemSV.loadData(table, dsDiemHP);

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
					if (checkDiemHP(idHocPhan)) {
						dsDiemHP.add(diemHP);
						//thêm điểm học phần của sinh viên lên bảng điểm
						((DefaultTableModel) table.getModel()).addRow(new Object[] { diemHP.getHocKy(), hp.getIdHocPhan(), hp.getTenHP(),
						hp.getSoTinChi() + "", diemHP.getIdLopHoc(), diemHP.getDiemQT() + "",
						diemHP.getDiemThi() + "", diemHP.getDiemChu(), diemHP.getDiemThang4() + "" });
						try {
							addDiemSV(diemHP);
							updateDiemCTDT(diemHP);
							if (loaiSV.equals("svnc") && diemHP.getDiemThang4() == 0.0) {
								//thêm học phần vào danh sách học phần nợ của sinh viên
								addHocPhanNo("quanlysinhvien\\sinhviennienche\\" + idSV + "\\hocPhanNo.xlsx",
										idHocPhan);
							}
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
					for (int i = 0; i < dsDiemHP.size(); i++) {
						HocPhan hp = dsDiemHP.get(i).getHocPhan();
						if (hp.getIdHocPhan().equals(hocPhan.getIdHocPhan())) {
							diemThang4 = dsDiemHP.get(i).getDiemThang4();
							dsDiemHP.get(i).setHocKy(diemHP.getHocKy());
							hp.setTenHP(hocPhan.getTenHP());
							hp.setSoTinChi(hocPhan.getSoTinChi());
							dsDiemHP.get(i).setIdLopHoc(diemHP.getIdLopHoc());
							dsDiemHP.get(i).setDiemQT(diemHP.getDiemQT());
							dsDiemHP.get(i).setDiemThi(diemHP.getDiemThi());
							dsDiemHP.get(i).setDiemChu(diemHP.getDiemChu());
							dsDiemHP.get(i).setDiemThang4(diemHP.getDiemThang4());
							break;
						}
					}
					updateRowTable(diemHP, row); //cập nhật lại giá trị của hàng trong bảng - ứng với điểm sinh viên đc sửa
					boolean ck = false;
					try {
						ck = updateDiemHP(diemHP);
						updateDiemCTDT(diemHP);
						if(diemThang4 == 0 && diemHP.getDiemThang4() != 0) {
							//xóa học phần nợ khi đã qua
							deleteHocPhanNo("quanlysinhvien\\sinhviennienche\\" + idSV + "\\hocPhanNo.xlsx",
										diemHP.getHocPhan().getIdHocPhan());
						}
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
						for (int i = 0; i < dsDiemHP.size(); i++) {
							if (dsDiemHP.get(i).getHocPhan().getIdHocPhan().equals(id)) {
								boolean ck = false;
								try {
									ck = deleteDiemHP(id);
									updateDiemCTDT(new DiemHocPhan("", id, "", 0, "", 0, 0, "", -1));
									if (diemTB == 0.0)
										if (deleteHocPhanNo(
												"quanlysinhvien\\sinhviennienche\\" + idSV + "\\hocPhanNo.xlsx", id))
											System.out.println("delete hocPhanNo");
									;
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								dsDiemHP.remove(i);
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

	//kiểm tra xem đã có điểm học phần cần kiểm tra trong bảng chưa?
	private boolean checkDiemHP(String idHocPhan) {
		for (DiemHocPhan diemHP : dsDiemHP) {
			if (diemHP.getHocPhan().getIdHocPhan().equals(idHocPhan))
				return false;
		}
		return true;
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
		HocPhan hocPhan = null;
		LopHocPhan lopHocPhan = null;
		try {
			hocPhan = getHocPhan(tfIdHocPhan.getText().trim().toUpperCase());
			lopHocPhan = getLopHP(idLopHoc);
			if (hocPhan == null || lopHocPhan == null || !hocPhan.getIdHocPhan().equals(lopHocPhan.getIdHocPhan())) {
				JOptionPane.showMessageDialog(null, "Lớp học không tồn tại", "Error", JOptionPane.ERROR_MESSAGE);
				return null;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DiemHocPhan diem = new DiemHocPhan(hocKy, hocPhan, idLopHoc, diemQT, diemThi);
		return diem;
	}

	
	//lấy học phần ứng với mã học phần truyền vào
	private HocPhan getHocPhan(String idHocPhan) throws IOException {
		HocPhan hp = null;
		FileInputStream fin = new FileInputStream(new File("quanlysinhvien\\danhsachhocphan\\dsHocPhan.xlsx"));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		Row nextRow;
		if (iterator.hasNext()) {
			nextRow = iterator.next();
		}
		while (iterator.hasNext()) {
			nextRow = iterator.next();
			Cell cell = nextRow.getCell(1);
			String idHP = cell.getStringCellValue();
			if (idHP.equals(idHocPhan)) {
				cell = nextRow.getCell(2);
				String tenHP = cell.getStringCellValue();
				cell = nextRow.getCell(3);
				int soTinChi = (int) Double.parseDouble(Double.toString(cell.getNumericCellValue()));
				cell = nextRow.getCell(4);
				double soTCHocPhi = Double.parseDouble(Double.toString(cell.getNumericCellValue()));
				cell = nextRow.getCell(5);
				String idNganh = cell.getStringCellValue();
				cell = nextRow.getCell(6);
				double trongSo = Double.parseDouble(Double.toString(cell.getNumericCellValue()));
				hp = new HocPhan(idHocPhan, tenHP, soTinChi, soTCHocPhi, idNganh, trongSo);
				break;
			}
		}
		workbook.close();
		fin.close();
		return hp;
	}

	//lấy lớp học phần trong danh sách lớp học phần ứng với mã lớp truyền vào
	private LopHocPhan getLopHP(String idLop) throws IOException {
		LopHocPhan lopHP = null;
		FileInputStream fin = new FileInputStream(
				new File("quanlysinhvien\\danhsachhocphan\\lophocphan\\dsLopHP.xlsx"));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		Row nextRow;
		if (iterator.hasNext()) {
			nextRow = iterator.next();
		}
		while (iterator.hasNext()) {
			nextRow = iterator.next();
			Cell cell = nextRow.getCell(2);
			String idL = cell.getStringCellValue();
			if (idL.equals(idLop)) {
				cell = nextRow.getCell(1);
				String hocKy = cell.getStringCellValue();
				cell = nextRow.getCell(3);
				String loaiLop = cell.getStringCellValue();
				cell = nextRow.getCell(4);
				String idHocPhan = cell.getStringCellValue();
				cell = nextRow.getCell(5);
				String tenLop = cell.getStringCellValue();
				cell = nextRow.getCell(6);
				String thoiGian = cell.getStringCellValue();
				cell = nextRow.getCell(7);
				String tuanHoc = cell.getStringCellValue();
				cell = nextRow.getCell(8);
				String phongHoc = cell.getStringCellValue();
				cell = nextRow.getCell(9);
				String tenGiangVien = cell.getStringCellValue();
				cell = nextRow.getCell(10);
				int soSVMax = (int) Double.parseDouble(Double.toString(cell.getNumericCellValue()));
				cell = nextRow.getCell(11);
				int soSVHienTai = (int) Double.parseDouble(Double.toString(cell.getNumericCellValue()));

				lopHP = new LopHocPhan(hocKy, idLop, loaiLop, idHocPhan, tenLop, thoiGian, tuanHoc, phongHoc,
						tenGiangVien, soSVMax, soSVHienTai);
			}
		}

		return lopHP;
	}

	
	//lấy danh sách điểm học phần của sinh viên
	private ArrayList<DiemHocPhan> readFile() throws IOException {
		ArrayList<DiemHocPhan> dsDiemHP = new ArrayList<>();
		String fileName = "";
		if (loaiSV.equals("svtc"))
			fileName = "quanlysinhvien\\sinhvientinchi\\" + idSV + "\\diem.xlsx";
		else if (loaiSV.equals("svnc"))
			fileName = "quanlysinhvien\\sinhviennienche\\" + idSV + "\\diem.xlsx";
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
			ArrayList<String> dataDiemHP = new ArrayList<>();
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
				dataDiemHP.add(data);
				if (dataDiemHP.size() < 1)
					return null;
			}
			if (dataDiemHP.size() > 0) {
				DiemHocPhan diemHP = new DiemHocPhan(dataDiemHP.get(0), dataDiemHP.get(1), dataDiemHP.get(2),
						(int) Double.parseDouble(dataDiemHP.get(3)), dataDiemHP.get(4),
						Double.parseDouble(dataDiemHP.get(5)), Double.parseDouble(dataDiemHP.get(6)), dataDiemHP.get(7),
						Double.parseDouble(dataDiemHP.get(8)));
				dsDiemHP.add(diemHP);
			}
		}

		workbook.close();
		inputStream.close();
		return dsDiemHP;
	}

	//thêm điểm học phần vào file điểm của sinh viên
	private void addDiemSV(DiemHocPhan diemHP) throws IOException {
		Workbook workbook = null;
		Sheet sheet = null;
		int lastRow = -1;
		String fileName = "";
		try {
			if (loaiSV.equals("svtc"))
				fileName = "quanlysinhvien\\sinhvientinchi\\" + idSV + "\\diem.xlsx";
			else if (loaiSV.equals("svnc"))
				fileName = "quanlysinhvien\\sinhviennienche\\" + idSV + "\\diem.xlsx";
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

		Cell cellDiemChu = row.createCell(8);
		cellDiemChu.setCellStyle(cellStyle);
		cellDiemChu.setCellValue("Điểm chữ");

		Cell cellDiem4 = row.createCell(9);
		cellDiem4.setCellStyle(cellStyle);
		cellDiem4.setCellValue("Điểm thang 4");
	}

	//cập nhật điểm học phần của sinh viên trong file điểm
	private boolean updateDiemHP(DiemHocPhan diemHP) throws FileNotFoundException, IOException {
		String fileName = "";
		HocPhan hp = diemHP.getHocPhan();
		boolean ck = false;
		if (loaiSV.equals("svtc"))
			fileName = "quanlysinhvien\\sinhvientinchi\\" + idSV + "\\diem.xlsx";
		else if (loaiSV.equals("svnc"))
			fileName = "quanlysinhvien\\sinhviennienche\\" + idSV + "\\diem.xlsx";
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
				cell = nextRow.createCell(8);
				cell.setCellValue(diemHP.getDiemChu());
				cell = nextRow.createCell(9);
				cell.setCellValue(diemHP.getDiemThang4());
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
		if (loaiSV.equals("svtc"))
			fileName = "quanlysinhvien\\sinhvientinchi\\" + idSV + "\\diem.xlsx";
		else if (loaiSV.equals("svnc"))
			fileName = "quanlysinhvien\\sinhviennienche\\" + idSV + "\\diem.xlsx";

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
		cell = row.createCell(8);
		cell.setCellValue(diemHP.getDiemChu());
		cell = row.createCell(9);
		cell.setCellValue(diemHP.getDiemThang4());
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
	
	private boolean updateDiemCTDT(DiemHocPhan diem) throws IOException {
		boolean ck = false;
		String fileName = "";
		if(loaiSV.equalsIgnoreCase("svtc"))
			fileName = "quanlysinhvien\\sinhvientinchi\\" + idSV + "\\ctdt.xlsx";
		else fileName = "quanlysinhvien\\sinhviennienche\\" + idSV + "\\ctdt.xlsx";
		FileInputStream fin = new FileInputStream(new File(fileName));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		Row nextRow;
		Iterator<Row> iterator = sheet.iterator();
		if(iterator.hasNext())
			nextRow = iterator.next();
		while(iterator.hasNext()) {
			nextRow = iterator.next();
			Cell cell = nextRow.getCell(1);
			if(diem.getHocPhan().getIdHocPhan().equalsIgnoreCase(cell.getStringCellValue())) {
				cell = nextRow.getCell(4);
				if(diem.getHocPhan().getSoTinChi() == -1)
					cell.setCellValue("");
				else cell.setCellValue(diem.getHocPhan().getSoTinChi());
				cell = nextRow.getCell(5);
				if(diem.getDiemChu() == "") cell.setCellValue("");
				else
					cell.setCellValue(diem.getDiemChu());
				cell = nextRow.getCell(6);
				if(diem.getDiemThang4() == -1)
					cell.setCellValue("");
				else
					cell.setCellValue(diem.getDiemThang4());
				ck = true;
			}
		}
		fin.close();
		FileOutputStream fout = new FileOutputStream(new File(fileName));
		workbook.write(fout);
		fout.close();
		return ck;
	}
}

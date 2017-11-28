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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.DiemHocPhan;
import quanlysinhvien.model.HocPhan;
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
//			e.printStackTrace();
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
	
	private void setAction() {
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				if(row >= 0) {
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
					String idHocPhan = diemHP.getIdHocPhan();
					if (checkDiemHP(idHocPhan)) {
						dsDiemHP.add(diemHP);
						capNhatDiemSV.loadData(table, dsDiemHP);
						try {
							addDiemSV(diemHP);
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
				if (diemHP != null) {
					for (int i = 0; i < dsDiemHP.size(); i++) {
						if (dsDiemHP.get(i).getIdHocPhan().equals(diemHP.getIdHocPhan())) {
//		hocKy; IdHocPhan; TenHP() ;SoTinChi; idLopHoc; diemQT; diemThi; DiemChu(hocPhan);
							dsDiemHP.get(i).setHocKy(diemHP.getHocKy());
							dsDiemHP.get(i).setTenHP(diemHP.getTenHP());
							dsDiemHP.get(i).setTinChi(diemHP.getTinChi());
							dsDiemHP.get(i).setLopHoc(diemHP.getLopHoc());
							dsDiemHP.get(i).setDiemQT(diemHP.getDiemQT());
							dsDiemHP.get(i).setDiemThi(diemHP.getDiemThi());
							dsDiemHP.get(i).setDiemChu(diemHP.getDiemChu());
							break;
						}
					}
					capNhatDiemSV.loadData(table, dsDiemHP);
					boolean ck = false;
					try {
						ck = updateDiemHP(diemHP);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(ck) JOptionPane.showMessageDialog(null, "Cập nhật thành công");
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
						for (int i = 0; i < dsDiemHP.size(); i++) {
							if (dsDiemHP.get(i).getIdHocPhan().equals(id)) {
								boolean ck = false;
								try {
									ck = deleteDiemHP(id);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								dsDiemHP.remove(i);
								capNhatDiemSV.loadData(table, dsDiemHP);
								if(ck) JOptionPane.showMessageDialog(null, "Xóa thành công");
								else {
									JOptionPane.showMessageDialog(null, "Xóa lỗi", "Error delete", JOptionPane.ERROR_MESSAGE);
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
	
	private boolean checkDiemHP(String idHocPhan) {
		for (int i = 0; i < dsDiemHP.size(); i++) {
			if (dsDiemHP.get(i).getIdHocPhan().equals(idHocPhan))
				return false;
		}
		return true;
	}
	
	private void cancel() {
		table.getSelectionModel().clearSelection();
		tfIdHocPhan.setText("");
		tfIdHocPhan.setEnabled(true);
		tfIdLopHoc.setText("");
		tfDiemQT.setText("");
		tfDiemThi.setText("");
	}
	
	private DiemHocPhan getDiemHP() {
		String hocKy = (String) hocKyCB.getSelectedItem();
		String idLopHoc = tfIdLopHoc.getText().trim();
		if(hocKy.equals("") || idLopHoc.equals("")) {
			JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		double diemQT, diemThi;
		try{
			diemQT = Double.parseDouble(tfDiemQT.getText().trim());
			diemThi = Double.parseDouble(tfDiemThi.getText().trim());
		}catch(NumberFormatException exc) {
			JOptionPane.showMessageDialog(null, "Kiểm tra lại giá trị Điểm QT, Điểm thi", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		HocPhan hocPhan = null;
		try {
			hocPhan = getHocPhan(tfIdHocPhan.getText().trim());
			if(hocPhan == null) {
				JOptionPane.showMessageDialog(null, "Học phần không tồn tại", "Error", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DiemHocPhan diem = new DiemHocPhan(hocKy, hocPhan, idLopHoc, diemQT, diemThi);
		return diem;
	}
	
	private HocPhan getHocPhan(String idHocPhan) throws IOException {
		HocPhan hp = null;
		FileInputStream fin = new FileInputStream(new File("quanlysinhvien\\danhsachhocphan\\dsHocPhan.xlsx"));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		Row nextRow;
		if(iterator.hasNext()) {
			nextRow = iterator.next();
		}
		while(iterator.hasNext()) {
			nextRow = iterator.next();
			Cell cell = nextRow.getCell(1);
			String idHP = cell.getStringCellValue();
			if(idHP.equals(idHocPhan)) {
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
	
	private ArrayList<DiemHocPhan> readFile() throws IOException {
		ArrayList<DiemHocPhan> dsDiemHP = new ArrayList<>();
		String fileName = "";
		if(loaiSV.equals("svtc"))
			fileName = "quanlysinhvien\\sinhvientinchi\\" + idSV + "\\diem.xlsx";
		else if(loaiSV.equals("svnc"))
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
				if(dataDiemHP.size() < 1) return null;
			}
			if(dataDiemHP.size() > 0) {
				DiemHocPhan diemHP = new DiemHocPhan(dataDiemHP.get(0), dataDiemHP.get(1), dataDiemHP.get(2), (int) Double.parseDouble(dataDiemHP.get(3)), dataDiemHP.get(4), Double.parseDouble(dataDiemHP.get(5)), Double.parseDouble(dataDiemHP.get(6)), dataDiemHP.get(7));
				dsDiemHP.add(diemHP);
			}
		}

		workbook.close();
		inputStream.close();
		return dsDiemHP;
	}
	
	private void addDiemSV(DiemHocPhan diemHP) throws IOException {
		Workbook workbook = null;
		Sheet sheet = null;
		int lastRow = -1;
		String fileName = "";
		try {
			if(loaiSV.equals("svtc"))
				fileName = "quanlysinhvien\\sinhvientinchi\\" + idSV + "\\diem.xlsx";
			else if(loaiSV.equals("svnc"))
				fileName = "quanlysinhvien\\sinhviennienche\\" + idSV + "\\diem.xlsx";
			FileInputStream inputStream = new FileInputStream(new File(fileName));
			workbook = new XSSFWorkbook(inputStream);
			sheet = workbook.getSheetAt(0);
			lastRow = sheet.getLastRowNum();
		}catch (Exception e) {
			// TODO: handle exception
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet();
			System.out.println(e);
		}

		Row row = null;
		if(lastRow < 0) {
			createHeader(sheet);
			row = sheet.createRow(1);
		}else {
			row = sheet.createRow(lastRow + 1);
		}
		if(row != null) {
			writeDiemHP(diemHP, row);
		}
		
		FileOutputStream fout = new FileOutputStream(new File(fileName));
		workbook.write(fout);
		fout.close();
	}
	
	private void createHeader(Sheet sheet) {
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		cellStyle.setFont(font);

		Row row = sheet.createRow(0);

		Cell cellIdSV = row.createCell(1);
		cellIdSV.setCellStyle(cellStyle);
		cellIdSV.setCellValue("Học kỳ");

		Cell cellHoTen = row.createCell(2);
		cellHoTen.setCellStyle(cellStyle);
		cellHoTen.setCellValue("Mã học phần");

		Cell cellKhoa = row.createCell(3);
		cellKhoa.setCellStyle(cellStyle);
		cellKhoa.setCellValue("Tên học phần");

		Cell cellNgaySinh = row.createCell(4);
		cellNgaySinh.setCellStyle(cellStyle);
		cellNgaySinh.setCellValue("Số TC");

		Cell cellGioiTinh = row.createCell(5);
		cellGioiTinh.setCellStyle(cellStyle);
		cellGioiTinh.setCellValue("Mã lớp");

		Cell cellEmail = row.createCell(6);
		cellEmail.setCellStyle(cellStyle);
		cellEmail.setCellValue("Điểm QT");

		Cell cellSoDT = row.createCell(7);
		cellSoDT.setCellStyle(cellStyle);
		cellSoDT.setCellValue("Điểm thi");

		Cell cellDiaChi = row.createCell(8);
		cellDiaChi.setCellStyle(cellStyle);
		cellDiaChi.setCellValue("Điểm chữ");
	}
	
	private boolean updateDiemHP(DiemHocPhan diemHP) throws FileNotFoundException, IOException {
		String fileName = "";
		boolean ck = false;
		if(loaiSV.equals("svtc"))
			fileName = "quanlysinhvien\\sinhvientinchi\\" + idSV + "\\diem.xlsx";
		else if(loaiSV.equals("svnc"))
			fileName = "quanlysinhvien\\sinhviennienche\\" + idSV + "\\diem.xlsx";
		FileInputStream fin = new FileInputStream(new File(fileName));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		
		Row nextRow;
		if (iterator.hasNext())
			nextRow = iterator.next(); // loại bỏ dòng tiêu đề
		while(iterator.hasNext()) {
			nextRow = iterator.next();
			Cell cell = nextRow.getCell(2);
			String idHocPhan = cell.getStringCellValue();
			if(idHocPhan.equalsIgnoreCase(diemHP.getIdHocPhan())) {
				cell = nextRow.createCell(1);
				cell.setCellValue(diemHP.getHocKy());
				cell = nextRow.createCell(3);
				cell.setCellValue(diemHP.getTenHP());
				cell = nextRow.createCell(4);
				cell.setCellValue(diemHP.getTinChi());
				cell = nextRow.createCell(5);
				cell.setCellValue(diemHP.getLopHoc());
				cell = nextRow.createCell(6);
				cell.setCellValue(diemHP.getDiemQT());
				cell = nextRow.createCell(7);
				cell.setCellValue(diemHP.getDiemThi());
				cell = nextRow.createCell(8);
				cell.setCellValue(diemHP.getDiemChu());
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
	
	private boolean deleteDiemHP(String idHocPhan) throws IOException {
		boolean ck = false;
		String fileName = "";
		if(loaiSV.equals("svtc"))
			fileName = "quanlysinhvien\\sinhvientinchi\\" + idSV + "\\diem.xlsx";
		else if(loaiSV.equals("svnc"))
			fileName = "quanlysinhvien\\sinhviennienche\\" + idSV + "\\diem.xlsx";
		
		FileInputStream fin = new FileInputStream(new File(fileName));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		
		Iterator<Row> iterator = sheet.iterator();
		
		Row nextRow = null;
		if (iterator.hasNext())
			nextRow = iterator.next(); // loại bỏ dòng tiêu đề
		int i = 0;
		while(iterator.hasNext()) {
			nextRow = iterator.next();
			i++;
			Cell cell = nextRow.getCell(2);
			String idHP = cell.getStringCellValue();
			if(idHP.equalsIgnoreCase(idHocPhan)) {
				int lastRow = sheet.getLastRowNum();
				if(i < lastRow) {
					sheet.shiftRows(i + 1, lastRow, -1);
					ck = true;
				}
				if(i == lastRow) {
					Row removeRow = sheet.getRow(i);
					if(removeRow != null) {
						sheet.removeRow(removeRow);
						ck = true;
					}
				}
				//hate you 
				break;
			}
		}
		
		fin.close();
		
		FileOutputStream fout = new FileOutputStream(new File(fileName));
		workbook.write(fout);
		fout.close();
		return ck;
	}
	
	private void writeDiemHP(DiemHocPhan diemHP, Row row) {
		Cell cell = row.createCell(1);
		cell.setCellValue(diemHP.getHocKy());
		cell = row.createCell(2);
		cell.setCellValue(diemHP.getIdHocPhan());
		cell = row.createCell(3);
		cell.setCellValue(diemHP.getTenHP());
		cell = row.createCell(4);
		cell.setCellValue(diemHP.getTinChi());
		cell = row.createCell(5);
		cell.setCellValue(diemHP.getLopHoc());
		cell = row.createCell(6);
		cell.setCellValue(diemHP.getDiemQT());
		cell = row.createCell(7);
		cell.setCellValue(diemHP.getDiemThi());
		cell = row.createCell(8);
		cell.setCellValue(diemHP.getDiemChu());
	}
}
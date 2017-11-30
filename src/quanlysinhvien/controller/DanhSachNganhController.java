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
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.HocPhan;
import quanlysinhvien.model.LopChuyenNganh;
import quanlysinhvien.model.LopHocPhan;
import quanlysinhvien.model.Nganh;
import quanlysinhvien.model.SinhVien;
import quanlysinhvien.view.PanelDanhSachNganhView;
import quanlysinhvien.view.XemDanhSachLopCNView;

public class DanhSachNganhController {
	private PanelDanhSachNganhView danhSachNganh;
	private JTable table;
	private JTextField tfIdNganh, tfTenNganh, tfTimKiem;
	private JButton btnThem, btnSua, btnXoa, btnTimKiem, btnHuy, btnXemDSNganh;
	private JComboBox<String> timKiemCB;
	private ArrayList<Nganh> dsNganh;
	private String fileName;
	
	public DanhSachNganhController(PanelDanhSachNganhView danhSachNganh) {
		this.danhSachNganh = danhSachNganh;
		this.fileName = "quanlysinhvien\\danhsachchuyennganh\\dsNganh.xlsx";
		try {
			this.dsNganh = readFile(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.dsNganh = new ArrayList<>();
//			e.printStackTrace();
			System.out.println("Error DanhSachNganh: " + e);
		}
		this.table = danhSachNganh.getTable();
		this.btnThem = danhSachNganh.getBtnThem();
		this.btnSua = danhSachNganh.getBtnSua();
		this.btnXoa = danhSachNganh.getBtnXoa();
		this.btnTimKiem = danhSachNganh.getBtnTimKiem();
		this.btnHuy = danhSachNganh.getBtnHuy();
		this.btnXemDSNganh = danhSachNganh.getBtnXemDSNganh();
		this.timKiemCB = danhSachNganh.getTimKiemCB();
		this.tfIdNganh = danhSachNganh.getTfIdNganh();
		this.tfTenNganh = danhSachNganh.getTfTenNganh();
		this.tfTimKiem = danhSachNganh.getTfTimKiem();
		danhSachNganh.loadData(table, dsNganh, "", "");
		
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
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				if(row >= 0) {
					tfIdNganh.setText((String) table.getValueAt(row, 0));
					tfIdNganh.setEnabled(false);
					tfTenNganh.setText((String) table.getValueAt(row, 1));
				}
			}
		});
		btnThem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Nganh nganh = getNganh();
				if (nganh != null) {
					if (checkNganh(nganh.getIdNganh())) {
						dsNganh.add(nganh);
						danhSachNganh.loadData(table, dsNganh, "", "");
						try {
							addNganh(nganh, fileName);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "Thêm thành công");
						cancel();
					} else {
						JOptionPane.showMessageDialog(null, "Trùng mã ngành", "Error insert",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnSua.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (table.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(null, "Cần chọn một hàng để sửa", "Error update",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				Nganh nganh = getNganh();
				if (nganh != null) {
					for (int i = 0; i < dsNganh.size(); i++) {
						String idNganh = dsNganh.get(i).getIdNganh();
						if (idNganh.equals(nganh.getIdNganh())) {
							dsNganh.get(i).setTenNganh(nganh.getTenNganh());;
							break;
						}
					}
					boolean ck = false;
					try {
						ck = updateNganh(nganh, fileName);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(ck) JOptionPane.showMessageDialog(null, "Cập nhật thành công");
					else {
						JOptionPane.showMessageDialog(null, "Lỗi cập nhật", "Error update", JOptionPane.ERROR_MESSAGE);
					}
					danhSachNganh.loadData(table, dsNganh, "", "");
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
				} else {
					int select = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa không?", "Notify",
							JOptionPane.YES_NO_OPTION);
					if (select == 0) {
						String id = (String) table.getValueAt(row, 0);
						for (int i = 0; i < dsNganh.size(); i++) {
							if (dsNganh.get(i).getIdNganh().equals(id)) {
								boolean ck = false;
								try {
									ck = deleteNganh(dsNganh.get(i).getIdNganh(), fileName);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								dsNganh.remove(i);
								danhSachNganh.loadData(table, dsNganh, "", "");
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
				danhSachNganh.loadData(table, dsNganh, "", "");
			}
		});
		btnTimKiem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String timKiem = timKiemCB.getSelectedItem().toString();
				String giaTri = tfTimKiem.getText().trim().toLowerCase();
				danhSachNganh.loadData(table, dsNganh, timKiem, giaTri);
			}
		});
		btnXemDSNganh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				if(row < 0) {
					JOptionPane.showMessageDialog(null, "Cần chọn ngành để xem danh sách lớp chuyên ngành", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String idNganh = (String) table.getValueAt(row, 0);
				XemDanhSachLopCNView xemDSLopCN = new XemDanhSachLopCNView(idNganh, (String) table.getValueAt(row, 1));
				new XemDSLopCNController(xemDSLopCN, idNganh);
			}
		});
	}
	
	private ArrayList<Nganh> readFile(String fileName) throws IOException{
		ArrayList<Nganh> dsNganh = new ArrayList<>();
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
			ArrayList<String> dataNganh = new ArrayList<>();
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
				dataNganh.add(data);
				if(dataNganh.size() < 1) return null;
			}
			if(dataNganh.size() > 0) {
				ArrayList<LopChuyenNganh> dsLopChuyenNganh;
				try {
					dsLopChuyenNganh = getDSLopChuyenNganh(dataNganh.get(0));
				}catch(Exception exc) {
					dsLopChuyenNganh = new ArrayList<>();
					System.out.println("Error DanhSachNganhController:" + exc);
				}
				Nganh nganh = new Nganh(null, dataNganh.get(0), dataNganh.get(1));
				dsNganh.add(nganh);
			}
		}

		workbook.close();
		inputStream.close();
		return dsNganh;
	}
	
	private void createHeader(Sheet sheet) {
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		cellStyle.setFont(font);

		Row row = sheet.createRow(0);

		Cell cellHocKy = row.createCell(1);
		cellHocKy.setCellStyle(cellStyle);
		cellHocKy.setCellValue("Mã ngành");

		Cell cellIdLop = row.createCell(2);
		cellIdLop.setCellStyle(cellStyle);
		cellIdLop.setCellValue("Tên ngành");
	}
	
	private void writeNganh(Nganh nganh, Row row) {
		Cell cell = row.createCell(1);
		cell.setCellValue(nganh.getIdNganh());
		cell = row.createCell(2);
		cell.setCellValue(nganh.getTenNganh());
	}
	
	private void addNganh(Nganh nganh, String fileName) throws IOException {
		Workbook workbook = null;
		Sheet sheet = null;
		int lastRow = -1;
		try {
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
			writeNganh(nganh, row);
		}
		
		FileOutputStream fout = new FileOutputStream(new File(fileName));
		workbook.write(fout);
		fout.close();
	}
	
	private boolean updateNganh(Nganh Nganh, String fileName) throws IOException {
		boolean ck = false;
		FileInputStream fin = new FileInputStream(new File(fileName));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		
		Row nextRow;
		if (iterator.hasNext())
			nextRow = iterator.next(); // loại bỏ dòng tiêu đề
		while(iterator.hasNext()) {
			nextRow = iterator.next();
			Cell cell = nextRow.getCell(1);
			String idNganh = cell.getStringCellValue();
			if(idNganh.equalsIgnoreCase(Nganh.getIdNganh())) {
				cell = nextRow.createCell(2);
				cell.setCellValue(Nganh.getTenNganh());
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
	
	private boolean deleteNganh(String idNganh, String fileName) throws IOException {
		boolean ck = false;
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
			Cell cell = nextRow.getCell(1);
			String idN = cell.getStringCellValue();
			if(idN.equalsIgnoreCase(idNganh)) {
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
				break;
			}
		}
		
		fin.close();
		
		FileOutputStream fout = new FileOutputStream(new File(fileName));
		workbook.write(fout);
		fout.close();
		return ck;
	}
	
	private ArrayList<LopChuyenNganh> getDSLopChuyenNganh(String idNganh) throws IOException {
		ArrayList<LopChuyenNganh> dsLopChuyenNganh = new ArrayList<>();
		String fileName = "quanlysinhvien\\danhsachchuyennganh\\lopchuyennganh\\dsLopCN.xlsx";
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
			ArrayList<String> dataLopChuyenNganh = new ArrayList<>();
			while (cellIterator.hasNext()) {
				Cell cellCK = nextRow.getCell(1);
				if(!cellCK.equals(idNganh)) break; //bỏ qua lớp ngành khác
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
				dataLopChuyenNganh.add(data);
				if(dataLopChuyenNganh.size() < 1) return null;
			}
			if(dataLopChuyenNganh.size() > 0) {
				LopChuyenNganh lopCN = new LopChuyenNganh(null, dataLopChuyenNganh.get(0), dataLopChuyenNganh.get(1), dataLopChuyenNganh.get(2), dataLopChuyenNganh.get(3), dataLopChuyenNganh.get(4));
				dsLopChuyenNganh.add(lopCN);
			}
		}

		workbook.close();
		inputStream.close();
		return dsLopChuyenNganh;
	}

	
	private Nganh getNganh() {
		String idNganh = tfIdNganh.getText();
		String tenNganh = tfTenNganh.getText();
		if(idNganh.equals("") || tenNganh.equals("")) {
			JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		Nganh nganh = new Nganh(null, idNganh, tenNganh);
		return nganh;
	}
	
	private boolean checkNganh(String idNganh) {
		for (int i = 0; i < dsNganh.size(); i++) {
			if (dsNganh.get(i).getIdNganh().equals(idNganh))
				return false;
		}
		return true;
	}
	
	private void cancel() {
		table.getSelectionModel().clearSelection();
		tfIdNganh.setText("");
		tfIdNganh.setEnabled(true);
		tfTenNganh.setText("");
		tfTimKiem.setText("");
	}
}

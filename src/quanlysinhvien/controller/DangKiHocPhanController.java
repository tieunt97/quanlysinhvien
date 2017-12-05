package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
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

import quanlysinhvien.model.DiemHocPhan;
import quanlysinhvien.model.HocPhan;
import quanlysinhvien.view.PanelDangKiHocPhanView;

public class DangKiHocPhanController {
	private JComboBox<String> hocKy;
	private JTextField tfDangky;
	private JButton btnDangKy, btnXoaHP, btnGuiDangKy;
	private JTable table;
	private PanelDangKiHocPhanView panelDkiHP;
	private JCheckBox checkBox;
	private JLabel lblSum;
	private int tongSoTinChiDaDangKy = 0;
	private ArrayList<HocPhan> dsHocPhan;
	private ArrayList<HocPhan> dsHocPhanDaDangKy;
	private String idSV;

	String fileDSHocPhan = "quanlysinhvien\\danhsachhocphan\\dsHocPhan.xlsx";

	public DangKiHocPhanController(PanelDangKiHocPhanView panelDkiHP, String idSV) {
		this.panelDkiHP = panelDkiHP;
		this.idSV = idSV;
		this.hocKy = panelDkiHP.getHocKy();
		this.tfDangky = panelDkiHP.getTfDangky();
		this.btnDangKy = panelDkiHP.getBtnDangKy();
		this.btnXoaHP = panelDkiHP.getBtnXoaHP();
		this.btnGuiDangKy = panelDkiHP.getBtnGuiDangKy();
		this.table = panelDkiHP.getTable();
		this.checkBox = panelDkiHP.getCheckBox();
		this.lblSum = panelDkiHP.getLbSum();

		/* Danh sách học phần trong bảng học phần */
		try {
			this.dsHocPhan = loadDanhSachHocPhan();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.dsHocPhan = new ArrayList<>();
//			e.printStackTrace();
		}

		try {
			this.dsHocPhanDaDangKy = loadDSHocPhanDaDangKy();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.dsHocPhanDaDangKy = new ArrayList<>();
//			e.printStackTrace();
		}

		/*load thông tin cũ vào bảng*/
		loadBangDangKyHocPhan();

		addEvents();
	}

	private ArrayList<HocPhan> loadDanhSachHocPhan() throws IOException {
		// TODO Auto-generated method stub
		ArrayList<HocPhan> dsHocPhan = new ArrayList<>();
		/* Khởi tạo file input */

		FileInputStream fis = new FileInputStream(fileDSHocPhan);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		Row rowT;

		String idHocPhan;
		String tenHP;
		String idNganh = "";
		int soTinChi;
		double soTCHocPhi;
		double trongSo;
		String ngayDangKyHP;

		int lastRow = sheet.getLastRowNum();
		for (int row = 1; row <= lastRow; row++) {
			rowT = sheet.getRow(row);

			idHocPhan = rowT.getCell(1).getStringCellValue();
			tenHP = rowT.getCell(2).getStringCellValue();
			soTinChi = (int) Math.round(rowT.getCell(3).getNumericCellValue());
			soTCHocPhi = rowT.getCell(4).getNumericCellValue();
			idNganh = rowT.getCell(5).getStringCellValue();
			trongSo = rowT.getCell(6).getNumericCellValue();
			ngayDangKyHP = new String(new Date(System.currentTimeMillis()).toString());

			dsHocPhan.add(new HocPhan(idHocPhan, tenHP, soTinChi, soTCHocPhi, idNganh, trongSo, ngayDangKyHP));
		}
		
		wb.close();
		fis.close();

		return dsHocPhan;

	}

	private void loadBangDangKyHocPhan() {
		// TODO Auto-generated method stub
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		Object[] obj = new Object[6];

		int count = dsHocPhanDaDangKy.size();
		for (int i = 0; i < count; i++) {
			obj[0] = dsHocPhanDaDangKy.get(i).getIdHocPhan();
			obj[1] = dsHocPhanDaDangKy.get(i).getTenHP();
			obj[2] = dsHocPhanDaDangKy.get(i).getNgayDangKy();
			obj[3] = "Thành công";
			obj[4] = dsHocPhanDaDangKy.get(i).getSoTinChi();
			obj[5] = false;

			tongSoTinChiDaDangKy += Integer.parseInt(obj[4].toString());

			model.addRow(obj);
		}
		lblSum.setText(tongSoTinChiDaDangKy + "");
	}

	private ArrayList<HocPhan> loadDSHocPhanDaDangKy() throws IOException {
		// TODO Auto-generated method stub
		ArrayList<HocPhan> dsHP = new ArrayList<>();
		File file = new File("quanlysinhvien\\sinhvientinchi\\" + idSV + "\\dsHocPhanDaDangKy.xlsx");
		if(!file.exists()){
			return new ArrayList<>();
		}
		
		FileInputStream fis = new FileInputStream(file);
		Workbook workbook = new XSSFWorkbook(fis);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();

		Row nextRow;
		if (iterator.hasNext())
			nextRow = iterator.next(); // loại bỏ dòng tiêu đề
		while (iterator.hasNext()) {
			nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			ArrayList<String> dataHP = new ArrayList<>();
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
				dataHP.add(data);
				if(dataHP.size() < 1) return null;
			}
			if(dataHP.size() > 0) {
				HocPhan hocPhan = new HocPhan(dataHP.get(0), dataHP.get(1), Integer.parseInt(dataHP.get(4)), dataHP.get(2));
				dsHP.add(hocPhan);
			}
		}

		workbook.close();
		fis.close();
		return dsHP;
	}

	private void addEvents() {
		// TODO Auto-generated method stub
		checkBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub
				DangKiHocPhan();
				return;
			}

		});

		tfDangky.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					DangKiHocPhan();
					return;
				}

			}
		});

		btnXoaHP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int row = 0;
				int soTinChiHP = 0;
				while (row < model.getRowCount()) {
					Boolean check = Boolean.valueOf(model.getValueAt(row, 5).toString());
					if (check == true) {
						soTinChiHP = Integer.parseInt(model.getValueAt(row, 4).toString());
						tongSoTinChiDaDangKy -= soTinChiHP;
						model.removeRow(row);
						lblSum.setText("" + tongSoTinChiDaDangKy);
						continue;
					}
					row++;
				}

			}
		});

		// su kien gui dang ki
		btnGuiDangKy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				UpdataTableDanhSachDangKy();
				GuiDangKy();
				return;
			}

		});
	}

	private void UpdataTableDanhSachDangKy() {
		// TODO Auto-generated method stub
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int count = model.getRowCount();
		for (int i = 0; i < count; i++) {
			model.setValueAt("Thành công", i, 3);
		}
	}

	private void GuiDangKy() {
		// TODO Auto-generated method stub
		try {
			FileOutputStream fos = new FileOutputStream("quanlysinhvien\\sinhvientinchi\\" + idSV + "\\dsHocPhanDaDangKy.xlsx");
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet("DSHPdadangky");
			XSSFRow Row;

			// ghi ra file exel
			DefaultTableModel model = (DefaultTableModel) table.getModel();

			// tạo header cho file exel
			createHeaderRow(sheet);

			// lấy dữ liệu từ bảng học phần lưu vào file
			int count = model.getRowCount();
			String[] data = new String[5];
			for (int row = 0; row < count; row++) {
				Row = sheet.createRow(row + 1);
				data[0] = model.getValueAt(row, 0).toString();
				data[1] = model.getValueAt(row, 1).toString();
				data[2] = model.getValueAt(row, 2).toString();
				data[3] = model.getValueAt(row, 3).toString();
				data[4] = model.getValueAt(row, 4).toString();

				addDataInputFile(data, Row);
			}

			wb.write(fos);

			wb.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		try {
//			dsHocPhanDaDangKy = loadDSHocPhanDaDangKy();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			dsHocPhanDaDangKy = new ArrayList<>();
//			e.printStackTrace();
//		}
	}

	private void addDataInputFile(String[] data, XSSFRow row) {
		// TODO Auto-generated method stub
		int count = data.length;
		for (int i = 0; i < count; i++) {
			Cell cell = row.createCell(i+1);
			cell.setCellValue(data[i]);
		}
	}

	private void createHeaderRow(XSSFSheet sheet) {
		// TODO Auto-generated method stub
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 12);
		cellStyle.setFont(font);

		// tạo dòng mới
		XSSFRow row = sheet.createRow(0);
		Cell cellMaHocPhan = row.createCell(1);

		cellMaHocPhan.setCellStyle(cellStyle);
		cellMaHocPhan.setCellValue("Mã Học Phần");

		Cell cellTenHocPhan = row.createCell(2);
		cellTenHocPhan.setCellStyle(cellStyle);
		cellTenHocPhan.setCellValue("Tên Lớp");

		Cell cellNgayDK = row.createCell(3);
		cellNgayDK.setCellStyle(cellStyle);
		cellNgayDK.setCellValue("Ngày đăng kí");

		Cell cellTTDangKy = row.createCell(4);
		cellTTDangKy.setCellStyle(cellStyle);
		cellTTDangKy.setCellValue("TT Đăng Ký");

		Cell celltongSoTinChiDaDangKy = row.createCell(5);
		celltongSoTinChiDaDangKy.setCellStyle(cellStyle);
		celltongSoTinChiDaDangKy.setCellValue("Số Tín Chỉ");
	}

	public void DangKiHocPhan() {
		String maHocPhan = tfDangky.getText();
		if (maHocPhan.equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn cần nhập vào mã học phần");
			return;
		}
		/* Kiểm tra học phần có tồn tại */
		HocPhan hocphan = getHocPhan(maHocPhan);
		if (hocphan == null) {
			JOptionPane.showMessageDialog(null, "Không tìm thấy mã học phần.");
			return;
		}

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		/* kiểm tra học phần có trùng trong bảng đăng kí học phần */
		int count = model.getRowCount();
		String maHP = "";
		for (int i = 0; i < count; i++) {
			maHP = model.getValueAt(i, 0).toString();
			if (maHP.equalsIgnoreCase(maHocPhan)) {
				JOptionPane.showMessageDialog(null, "Mã học phần đã có trong bảng đăng kí học phần.");
				return;
			}
		}

		tongSoTinChiDaDangKy += hocphan.getSoTinChi();
		/* kiểm tra có quá giới hạn tín chỉ HP được phép đăng ki */
		if (tongSoTinChiDaDangKy > 24) {
			JOptionPane.showMessageDialog(null, "Quá 24 tín chỉ.");
			return;
		}

		/* Thêm vào bảng */
		Object[] obj = new Object[6];
		obj[0] = hocphan.getIdHocPhan();
		obj[1] = hocphan.getTenHP();
		obj[2] = new Date(System.currentTimeMillis());
		obj[3] = "Chưa gửi đăng kí";
		obj[4] = Math.round(hocphan.getSoTinChi());
		obj[5] = false;

		model.addRow(obj);
		lblSum.setText(tongSoTinChiDaDangKy + "");

		tfDangky.setText("");
	}

	public HocPhan getHocPhan(String maHP) {
		int count = dsHocPhan.size();
		for (int i = 0; i < count; i++) {
			if (maHP.equalsIgnoreCase(dsHocPhan.get(i).getIdHocPhan())) {
				return dsHocPhan.get(i);
			}
		}

		return null;
	}
}

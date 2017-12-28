package quanlysinhvien.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.DiemHocPhan;
import quanlysinhvien.model.HocPhan;
import quanlysinhvien.model.SinhVien;
import quanlysinhvien.view.PanelBangDiemHocPhanView;

public class BangDiemHocPhanController {
	private JTextField tfHocKy, tfIdHP, tfTenHP, tfTinChi, tfDiemHP, tfIdSinhVien;
	private JLabel labSumHP, labSumTC;
	private JTable table;
	private PanelBangDiemHocPhanView bangDiemHocPhan;

	private String[][] data;

	public BangDiemHocPhanController(PanelBangDiemHocPhanView bangDiemHocPhan, SinhVien sv) {
		this.bangDiemHocPhan = bangDiemHocPhan;
		this.tfHocKy = bangDiemHocPhan.getTfHocKy();
		this.tfIdHP = bangDiemHocPhan.getTfIdHP();
		this.tfTenHP = bangDiemHocPhan.getTfTenHP();
		this.tfTinChi = bangDiemHocPhan.getTfTinChi();
		this.tfDiemHP = bangDiemHocPhan.getTfDiemHP();
		this.tfIdSinhVien = bangDiemHocPhan.getTfIdSinhVien();
		tfIdSinhVien.setText(sv.getIdSinhVien());
		this.labSumHP = bangDiemHocPhan.getLabSumHP();
		this.labSumTC = bangDiemHocPhan.getLabSumTC();
		this.labSumTC.setText("0");
		this.table = bangDiemHocPhan.getTable();

		Collections.sort(sv.getDsDiemHP(), new sapXepDiem());
		labSumHP.setText(Integer.toString(sv.getDsDiemHP().size()));

		this.bangDiemHocPhan.loadData(table, sv.getDsDiemHP());

		loadDataIntoPiece();

		setAction();
	}

	private void setAction() {
		tfHocKy.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bangDiemHocPhan.loadData(table, ketQuaTimKiem(tfHocKy.getText(), 0));
				}
			}
		});

		tfIdHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bangDiemHocPhan.loadData(table, ketQuaTimKiem(tfIdHP.getText(), 1));
				}
			}
		});

		tfTenHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bangDiemHocPhan.loadData(table, ketQuaTimKiem(tfTenHP.getText(), 2));
				}
			}
		});

		tfTinChi.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bangDiemHocPhan.loadData(table, ketQuaTimKiem(tfTinChi.getText(), 3));
				}
			}
		});

		tfDiemHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					bangDiemHocPhan.loadData(table, ketQuaTimKiem(tfDiemHP.getText(), 4));
				}
			}
		});
	}

//	private ArrayList<DiemHocPhan> readFile(String idSV, String loaiSV) throws IOException {
//		dsDiem = new ArrayList<DiemHocPhan>();
//		int sumTC = 0;
//
//		File file;
//		if (loaiSV.equals("svtc"))
//			file = new File("quanlysinhvien/sinhvientinchi/" + idSV + "/diem.xlsx");
//		else
//			file = new File("quanlysinhvien/sinhviennienche/" + idSV + "/diem.xlsx");
//		FileInputStream inputStream = new FileInputStream(file);
//
//		Workbook workbook = new XSSFWorkbook(inputStream);
//		Sheet sheet = workbook.getSheetAt(0);
//
//		int rowCount = sheet.getLastRowNum();
//
//		for (int i = 1; i <= rowCount; i++) {
//			Row row = sheet.getRow(i);
//			Cell cell;
//
//			cell = row.getCell(1);
//			String hocKy = cell.getStringCellValue();
//
//			cell = row.getCell(2);
//			String idHocPhan = cell.getStringCellValue();
//
//			cell = row.getCell(3);
//			String tenHP = cell.getStringCellValue();
//
//			cell = row.getCell(4);
//			int tc = (int) cell.getNumericCellValue();
//			sumTC += tc;
//
//			cell = row.getCell(5);
//			String idLopHP = cell.getStringCellValue();
//
//			cell = row.getCell(6);
//			double diemQT = (double) cell.getNumericCellValue();
//
//			cell = row.getCell(7);
//			double diemThi = (double) cell.getNumericCellValue();
//
//			cell = row.getCell(8);
//			String diemChu = cell.getStringCellValue();
//
//			cell = row.getCell(9);
//			double diemThang4 = cell.getNumericCellValue();
//
//			DiemHocPhan diem = new DiemHocPhan(hocKy, idHocPhan, tenHP, tc, idLopHP, diemQT, diemThi, diemChu,
//					diemThang4);
//			dsDiem.add(diem);
//		}
//		labSumTC.setText(Integer.toString(sumTC));
//
//		workbook.close();
//		inputStream.close();
//
//		return dsDiem;
//	}

	private ArrayList<DiemHocPhan> ketQuaTimKiem(String strTimKiem, int j) {
		ArrayList<DiemHocPhan> result = new ArrayList<DiemHocPhan>();
		strTimKiem = strTimKiem.toLowerCase();
		for (int i = 0; i < data.length; i++) {
			String str = data[i][j].toLowerCase();
			if (str.indexOf(strTimKiem) > -1)
				result.add(dsDiem.get(i));
		}

		return result;
	}

	private void loadDataIntoPiece() {
		data = new String[dsDiem.size()][5];
		for (int i = 0; i < dsDiem.size(); i++) {
			HocPhan hp = dsDiem.get(i).getHocPhan();
			data[i][0] = dsDiem.get(i).getHocKy();
			data[i][1] = hp.getIdHocPhan();
			data[i][2] = hp.getTenHP();
			data[i][3] = Integer.toString(hp.getSoTinChi());
			data[i][4] = dsDiem.get(i).getDiemChu();
		}
	}
}

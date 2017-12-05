package quanlysinhvien.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.DiemHocPhan;
import quanlysinhvien.model.HocPhan;
import quanlysinhvien.model.TaiKhoan;
import quanlysinhvien.view.PanelChuongTrinhDaoTaoSVView;

public class ChuongTrinhDaoTaoSVController {
	private JTextField tfIdSinhVien, tfIdHP, tfTenHP, tfKyHoc, tfTinChi, tfDiemChu, tfDiemSo, tfVien_Khoa;
	private JTable table;
	
	private PanelChuongTrinhDaoTaoSVView chuongTrinhDaoTaoSV;
	
	private ArrayList<DiemHocPhan> dsDiem;
	
	private String[][] Data;
	private String[] vienKhoa;
	
	public ChuongTrinhDaoTaoSVController(PanelChuongTrinhDaoTaoSVView chuongTrinhDaoTaoSV, TaiKhoan tk) {
		this.chuongTrinhDaoTaoSV = chuongTrinhDaoTaoSV;
		this.tfIdSinhVien = chuongTrinhDaoTaoSV.getTfIdSinhVien();
                this.tfIdSinhVien.setText(tk.getTaiKhoan());
		this.tfIdHP = chuongTrinhDaoTaoSV.getTfTenHP();
		this.tfKyHoc = chuongTrinhDaoTaoSV.getTfKyHoc();
		this.tfTinChi = chuongTrinhDaoTaoSV.getTfTinChi();
		this.tfDiemChu = chuongTrinhDaoTaoSV.getTfDiemChu();
		this.tfDiemSo = chuongTrinhDaoTaoSV.getTfDiemSo();
		this.table = chuongTrinhDaoTaoSV.getTable();
		
		try {
			dsDiem = readFile(tk.getTaiKhoan(), tk.getLoaiTK());
		} catch (IOException e) {
                    System.out.println("a");
			// TODO Auto-generated catch block
			dsDiem = new ArrayList<>();
//			e.printStackTrace();
			System.out.println("Error chuongTrinhDaoTao: " + e);
		}
		
		this.chuongTrinhDaoTaoSV.loadData(table, dsDiem, vienKhoa);
		
		setAction();
	}
	
	private void setAction() {
		
	}
	
	private ArrayList<DiemHocPhan> readFile(String idSV, String loaiSV) throws IOException {
		dsDiem = new ArrayList<DiemHocPhan>();
                File file;
		if(loaiSV.equals("svtc")){
		    file = new File("quanlysinhvien/sinhvientinchi/"+idSV+"/ctdt.xlsx");
                }else{
                    file = new File("quanlysinhvien/sinhviennienche/"+idSV+"/ctdt.xlsx");
                }
		FileInputStream inputStream = new FileInputStream(file);
		
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		
		int rowCount = sheet.getLastRowNum();
		vienKhoa = new String[rowCount];
		for (int i = 1; i <= rowCount; i++) {
			
			DiemHocPhan diem = new DiemHocPhan();
			
			Row row = sheet.getRow(i);
			Cell cell;
			
			cell = row.getCell(3);
			diem.setHocKy(cell.getNumericCellValue()+"");
			
			cell = row.getCell(1);
			diem.setIdHocPhan(cell.getStringCellValue());
			
			cell = row.getCell(2);
			diem.setTenHP(cell.getStringCellValue());
			
			cell = row.getCell(4);
			diem.setTinChi((int) cell.getNumericCellValue());
			
			cell = row.getCell(5);
			if (cell!=null)
			diem.setDiemChu(cell.getStringCellValue());
			
			cell = row.getCell(6);
			if (cell!=null)
			diem.setDiemThang4(cell.getNumericCellValue());
			
			cell = row.getCell(7);
			vienKhoa[i - 1] = cell.getStringCellValue();
			
			dsDiem.add(diem);
			
		}
		workbook.close();
		inputStream.close();
		
		return dsDiem;
	}
}

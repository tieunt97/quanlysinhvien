package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.LopHocPhan;
import quanlysinhvien.model.QuanLy;
import quanlysinhvien.model.SinhVien;
import quanlysinhvien.view.PanelTKBView;

//sắp xếp tkb
class SapXepTKB implements Comparator<LopHocPhan> {

	@Override
	public int compare(LopHocPhan o1, LopHocPhan o2) {
		// TODO Auto-generated method stub
		String thoiGian1 = o1.getThoiGian();
		String thoiGian2 = o2.getThoiGian();
		if (thoiGian1.compareTo(thoiGian2) > 0) {
			return 1;
		} else if (thoiGian1.compareTo(thoiGian2) == 0) {
			return 0;
		} else {
			return -1;
		}
	}
}

public class ThoiKhoaBieuController {
	private PanelTKBView tkb;
	private JTable table;
	private JLabel labStatus;
	private JTextField tfHocKy;
	private JButton btnTraCuu;
	private ArrayList<LopHocPhan> dsLopHPDangKy;
	private SinhVien sv;
	private QuanLy quanLy;

	public ThoiKhoaBieuController(PanelTKBView tkb, SinhVien sv, QuanLy quanLy) {
		this.tkb = tkb;
		this.sv = sv;
		this.quanLy = quanLy;
		this.table = tkb.getTable();
		this.labStatus = tkb.getLabStatus();
		this.tfHocKy = tkb.getTfHocKy();
		this.btnTraCuu = tkb.getBtnTraCuu();
		for(int i = 0; i < sv.getDsLopHPDangKy().size(); i++) {
			dsLopHPDangKy.add(quanLy.getLopHocPhan(sv.getDsLopHPDangKy().get(i)));
		}
		if (sv.getDsLopHPDangKy().size() != 0)
			Collections.sort(dsLopHPDangKy, new SapXepTKB());

		setActions();
	}
	
	private void setActions() {
		btnTraCuu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String hocKy = tfHocKy.getText();
				if(hocKy.equals("")) {
					labStatus.setText("Sinh viên không có thời khóa biểu kì này");
					return;
				}
				ArrayList<LopHocPhan> dsLopHP = new ArrayList<>();
				for(int i = 0; i < sv.getDsLopHPDangKy().size(); i++) {
					LopHocPhan lopHP = quanLy.getLopHocPhan(sv.getDsLopHPDangKy().get(i));
					if(lopHP.getHocKy().equals(hocKy))
						dsLopHP.add(lopHP);
				}
				if(dsLopHP.size() > 0) {
					tkb.loadData(table, dsLopHP);
				}else
					labStatus.setText("Sinh viên không có thời khóa biểu kì này");
			}
		});
	}

}

package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import quanlysinhvien.model.LopHocPhan;
import quanlysinhvien.model.SinhVien;
import quanlysinhvien.view.PanelTKBView;

//sắp xếp tkb
class SapXepTKB implements Comparator<LopHocPhan> {

	@Override
	public int compare(LopHocPhan o1, LopHocPhan o2) {
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

	public ThoiKhoaBieuController(PanelTKBView tkb, SinhVien sv) {
		this.tkb = tkb;
		this.sv = sv;
		this.table = tkb.getTable();
		this.labStatus = tkb.getLabStatus();
		this.tfHocKy = tkb.getTfHocKy();
		this.btnTraCuu = tkb.getBtnTraCuu();
		this.dsLopHPDangKy = sv.getDsLopHPDangKy();
		if (sv.getDsLopHPDangKy().size() != 0)
			Collections.sort(dsLopHPDangKy, new SapXepTKB());

		setActions();
	}
	
	private void setActions() {
		btnTraCuu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String hocKy = tfHocKy.getText();
				if(hocKy.equals("")) {
					labStatus.setText("Sinh viên không có thời khóa biểu kì này");
					return;
				}
				dsLopHPDangKy = sv.getDSLopHPDangKy(hocKy);
				if(dsLopHPDangKy.size() > 0) {
					tkb.loadData(table, dsLopHPDangKy);
				}else
					labStatus.setText("Sinh viên không có thời khóa biểu kì này");
			}
		});
	}

}

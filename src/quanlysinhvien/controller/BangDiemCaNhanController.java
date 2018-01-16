package quanlysinhvien.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.DiemHocPhan;
import quanlysinhvien.model.HocPhan;
import quanlysinhvien.model.SinhVien;
import quanlysinhvien.model.TaiKhoan;
import quanlysinhvien.view.PanelBangDiemCaNhanView;

//sắp xếp danh sách điểm 
class sapXepDiem implements Comparator<DiemHocPhan> {

	@Override
	public int compare(DiemHocPhan o1, DiemHocPhan o2) {
		// TODO Auto-generated method stub
		if (o1.getHocKy().compareTo(o2.getHocKy()) > 0)
			return 1;
		else if (o1.getHocKy().compareTo(o2.getHocKy()) < 0)
			return -1;
		else
			return 0;
	}

}

public class BangDiemCaNhanController {
	private JTable tableDiem, tableKetQua;
	private JTextField tfHocKy, tfIdHP, tfTenHP, tfTinChi, tfLopHoc, tfDiemQT, tfDiemThi, tfDiemChu;
	private JLabel gtIdSinhVien, gtHoTen, gtNgaySinh, gtLop;
	private SinhVien sv;
	private PanelBangDiemCaNhanView bangDiemCaNhan;
	private ArrayList<DiemHocPhan> dsDiem;

	public BangDiemCaNhanController(PanelBangDiemCaNhanView bangDiemCaNhan, SinhVien sv) {
		this.bangDiemCaNhan = bangDiemCaNhan;
		this.sv = sv;
		this.tableDiem = bangDiemCaNhan.getTableDiem();
		this.tableKetQua = bangDiemCaNhan.getTableKetQua();
		this.tfHocKy = bangDiemCaNhan.getTfHocKy();
		this.tfIdHP = bangDiemCaNhan.getTfIdHP();
		this.tfTenHP = bangDiemCaNhan.getTfTenHP();
		this.tfTinChi = bangDiemCaNhan.getTfTinChi();
		this.tfLopHoc = bangDiemCaNhan.getTfLopHoc();
		this.tfDiemQT = bangDiemCaNhan.getTfDiemQT();
		this.tfDiemThi = bangDiemCaNhan.getTfDiemThi();
		this.tfDiemChu = bangDiemCaNhan.getTfDiemChu();
		this.gtIdSinhVien = bangDiemCaNhan.getGtIdSinhVien();
		this.gtHoTen = bangDiemCaNhan.getGtHoTen();
		this.gtNgaySinh = bangDiemCaNhan.getGtNgaySinh();
		this.gtLop = bangDiemCaNhan.getGtLop();

		Collections.sort(dsDiem, new sapXepDiem());

		this.bangDiemCaNhan.loadData1(tableDiem, dsDiem, "", "");
		this.bangDiemCaNhan.loadData2(tableKetQua, dsDiem);
		// tableKetQua.getColumnModel().removeColumn(tableKetQua.getColumnModel().getColumn(3));
		// tableKetQua.getColumnModel().removeColumn(tableKetQua.getColumnModel().getColumn(4));
		// tableKetQua.getColumnModel().removeColumn(tableKetQua.getColumnModel().getColumn(4));

		setThongTin();
		setAction();
	}

	private void setAction() {
		tfHocKy.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String giaTri = tfHocKy.getText().toLowerCase();
					bangDiemCaNhan.loadData1(tableDiem, sv.getDsDiemHP(), "hocKy", giaTri);
				}
			}
		});

		tfIdHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String giaTri = tfIdHP.getText().toLowerCase();
					bangDiemCaNhan.loadData1(tableDiem, sv.getDsDiemHP(), "idHP", giaTri);
				}
			}
		});

		tfTenHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String giaTri = tfTenHP.getText().toLowerCase();
					bangDiemCaNhan.loadData1(tableDiem, sv.getDsDiemHP(), "tenHP", giaTri);
				}
			}
		});

		tfTinChi.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String giaTri = tfTinChi.getText().toLowerCase();
					bangDiemCaNhan.loadData1(tableDiem, sv.getDsDiemHP(), "tinChi", giaTri);
				}
			}
		});

		tfLopHoc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String giaTri = tfLopHoc.getText().toLowerCase();
					bangDiemCaNhan.loadData1(tableDiem, sv.getDsDiemHP(), "idLop", giaTri);
				}
			}
		});

		tfDiemQT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String giaTri = tfDiemQT.getText().toLowerCase();
					bangDiemCaNhan.loadData1(tableDiem, sv.getDsDiemHP(), "diemQT", giaTri);
				}
			}
		});

		tfDiemThi.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String giaTri = tfDiemThi.getText().toLowerCase();
					bangDiemCaNhan.loadData1(tableDiem, sv.getDsDiemHP(), "diemThi", giaTri);
				}
			}
		});

		tfDiemChu.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String giaTri = tfDiemChu.getText().toLowerCase();
					bangDiemCaNhan.loadData1(tableDiem, sv.getDsDiemHP(), "diemChu", giaTri);
				}
			}
		});
	}

	private void setThongTin() {
		gtHoTen.setText(sv.getHoTen());
		gtIdSinhVien.setText(sv.getIdSinhVien());
		gtLop.setText(sv.getTenLop());
		gtNgaySinh.setText(sv.getNgaySinh());
	}

}

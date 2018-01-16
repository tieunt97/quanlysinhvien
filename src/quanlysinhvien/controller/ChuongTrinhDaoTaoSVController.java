package quanlysinhvien.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
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
import quanlysinhvien.model.SinhVien;
import quanlysinhvien.model.SinhVienTinChi;
import quanlysinhvien.model.TaiKhoan;
import quanlysinhvien.view.PanelChuongTrinhDaoTaoSVView;

public class ChuongTrinhDaoTaoSVController {
	private JTextField tfIdSinhVien, tfIdHP, tfTenHP, tfKyHoc, tfTinChi, tfDiemChu, tfDiemSo, tfVien_Khoa;
	private JTable table;
	private PanelChuongTrinhDaoTaoSVView chuongTrinhDaoTaoSV;
	private SinhVien sv;

	public ChuongTrinhDaoTaoSVController(PanelChuongTrinhDaoTaoSVView chuongTrinhDaoTaoSV, SinhVien sv) {
		this.chuongTrinhDaoTaoSV = chuongTrinhDaoTaoSV;
		this.sv = sv;
		this.tfIdSinhVien = chuongTrinhDaoTaoSV.getTfIdSinhVien();
		this.tfIdSinhVien.setText(sv.getIdSinhVien());
		this.tfIdHP = chuongTrinhDaoTaoSV.getTfIdHP();
		this.tfTenHP = chuongTrinhDaoTaoSV.getTfTenHP();
		this.tfKyHoc = chuongTrinhDaoTaoSV.getTfKyHoc();
		this.tfTinChi = chuongTrinhDaoTaoSV.getTfTinChi();
		if (sv instanceof SinhVienTinChi)
			this.tfDiemChu = chuongTrinhDaoTaoSV.getTfDiemChu();
		this.tfVien_Khoa = chuongTrinhDaoTaoSV.getTfVien_Khoa();
		this.tfDiemSo = chuongTrinhDaoTaoSV.getTfDiemSo();
		this.table = chuongTrinhDaoTaoSV.getTable();
		this.chuongTrinhDaoTaoSV.loadData(table, "", "");

		setAction();
	}

	// bắt sự kiện
	private void setAction() {
		tfIdHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String giaTri = tfIdHP.getText().toLowerCase();
				if (giaTri.equals(""))
					chuongTrinhDaoTaoSV.loadData(table, "", "");
				else
					chuongTrinhDaoTaoSV.loadData(table, "idHP", giaTri);
			}
		});
		tfTenHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String giaTri = tfTenHP.getText().toLowerCase();
				if (giaTri.equals(""))
					chuongTrinhDaoTaoSV.loadData(table, "", "");
				else
					chuongTrinhDaoTaoSV.loadData(table, "tenHP", giaTri);
			}
		});
		tfKyHoc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String giaTri = tfKyHoc.getText().toLowerCase();
				if (giaTri.equals(""))
					chuongTrinhDaoTaoSV.loadData(table, "", "");
				else
					chuongTrinhDaoTaoSV.loadData(table, "hocKy", giaTri);
			}
		});
		tfTinChi.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String giaTri = tfTinChi.getText().toLowerCase();
				if (giaTri.equals(""))
					chuongTrinhDaoTaoSV.loadData(table, "", "");
				else
					chuongTrinhDaoTaoSV.loadData(table, "tinChi", giaTri);
			}
		});
		if (sv instanceof SinhVienTinChi)
			tfDiemChu.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					String giaTri = tfDiemChu.getText().toLowerCase();
					if (giaTri.equals(""))
						chuongTrinhDaoTaoSV.loadData(table, "", "");
					else
						chuongTrinhDaoTaoSV.loadData(table, "diemChu", giaTri);
				}
			});
		tfDiemSo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String giaTri = tfDiemSo.getText().toLowerCase();
				if (giaTri.equals(""))
					chuongTrinhDaoTaoSV.loadData(table, "", "");
				else
					chuongTrinhDaoTaoSV.loadData(table, "diem4", giaTri);
			}
		});
		tfVien_Khoa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String giaTri = tfVien_Khoa.getText().toLowerCase();
				if (giaTri.equals(""))
					chuongTrinhDaoTaoSV.loadData(table, "", "");
				else
					chuongTrinhDaoTaoSV.loadData(table, "vienKhoa", giaTri);
			}
		});
	}

}

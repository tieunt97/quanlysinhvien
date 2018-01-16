package quanlysinhvien.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import quanlysinhvien.model.SinhVien;
import quanlysinhvien.view.PanelBangDiemHocPhanView;

public class BangDiemHocPhanController {
	private JTextField tfHocKy, tfIdHP, tfTenHP, tfTinChi, tfDiemHP, tfIdSinhVien;
	private JLabel labSumHP, labSumTC;
	private JTable table;
	private PanelBangDiemHocPhanView bangDiemHocPhan;
	private SinhVien sv;


	public BangDiemHocPhanController(PanelBangDiemHocPhanView bangDiemHocPhan, SinhVien sv) {
		this.bangDiemHocPhan = bangDiemHocPhan;
		this.sv = sv;
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

		this.bangDiemHocPhan.loadData(table, sv, "", "");

		setAction();
	}

	private void setAction() {
		tfHocKy.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String giaTri = tfHocKy.getText().toLowerCase();
					bangDiemHocPhan.loadData(table, sv, "hocKy", giaTri);
				}
			}
		});

		tfIdHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String giaTri = tfIdHP.getText().toLowerCase();
					bangDiemHocPhan.loadData(table, sv, "idHP", giaTri);
				}
			}
		});

		tfTenHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String giaTri = tfTenHP.getText().toLowerCase();
					bangDiemHocPhan.loadData(table, sv, "tenHP", giaTri);
				}
			}
		});

		tfTinChi.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String giaTri = tfTinChi.getText().toLowerCase();
					bangDiemHocPhan.loadData(table, sv, "tinChi", giaTri);
				}
			}
		});

		tfDiemHP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String giaTri = tfIdHP.getText().toLowerCase();
					bangDiemHocPhan.loadData(table, sv, "diemHP", giaTri);
				}
			}
		});
	}
}

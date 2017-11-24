package quanlysinhvien.main;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import quanlysinhvien.controller.DanhSachHPController;
import quanlysinhvien.controller.SinhVienTinChiController;
import quanlysinhvien.view.PanelCapNhatDiemSVView;
import quanlysinhvien.view.PanelDanhSachHPView;
import quanlysinhvien.view.PanelDanhSachNganhView;
import quanlysinhvien.view.PanelHeThongView;
import quanlysinhvien.view.PanelLopChuyenNganhView;
import quanlysinhvien.view.PanelLopHocPhanView;
import quanlysinhvien.view.PanelSinhVienNienCheView;
import quanlysinhvien.view.PanelSinhVienTinChiView;

public class MainQuanLy extends JFrame implements ActionListener{
	private JMenuBar menuBar;
	private JMenu menuQuanLySV, menuQuanLyMonHoc, menuQuanLyNganh;
	private JMenuItem miSVTinChi, miSVNienChe, miCapNhatDiem, miQuanLyHocPhan, miQuanlyLopHP, miQuanLyNganh, miQuanLyLopChuyenNganh;
	private Container conter;
	private CardLayout carLayout;
	
	private PanelHeThongView trangChu;
	private PanelSinhVienTinChiView SVTC;
	private PanelSinhVienNienCheView SVNC;
	private PanelCapNhatDiemSVView capNhatDiem;
	
	private PanelDanhSachHPView dsHP;
	private PanelLopHocPhanView lopHP;
	
	private PanelDanhSachNganhView dsNganh;
	private PanelLopChuyenNganhView lopCN;
	
	public MainQuanLy() {
		conter = this.getContentPane();
		setLayout(carLayout = new CardLayout());
		setJMenuBar(menuBar = createJMenuBar());
		
		conter.add(trangChu = new PanelHeThongView());
		
		conter.add(SVTC = new PanelSinhVienTinChiView(), "SVTC");
		new SinhVienTinChiController(SVTC);
		conter.add(SVNC = new PanelSinhVienNienCheView(), "SVNC");
		conter.add(capNhatDiem = new PanelCapNhatDiemSVView(), "capNhatDiem");
		
		conter.add(dsHP = new PanelDanhSachHPView(), "dsHP");
		new DanhSachHPController(dsHP);
		conter.add(lopHP = new PanelLopHocPhanView(), "lopHP");
		
		conter.add(dsNganh = new PanelDanhSachNganhView(), "dsNganh");
		conter.add(lopCN = new PanelLopChuyenNganhView(), "lopCN");
		
		setTitle("Hệ thống quản lý sinh viên");
		setSize(1200, 720);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private JMenuBar createJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menuQuanLySV = createMenu("Quản lý sinh viên"));
		menuQuanLySV.add(miSVTinChi = createMenuItem("Sinh viên tín chỉ"));
		menuQuanLySV.add(miSVNienChe = createMenuItem("Sinh viên niên chế"));
		menuQuanLySV.add(miCapNhatDiem = createMenuItem("Cập nhật điểm sinh viên"));
		
		menuBar.add(menuQuanLyMonHoc = createMenu("Quản lý học phần"));
		menuQuanLyMonHoc.add(miQuanLyHocPhan = createMenuItem("Danh sách học phần"));
		menuQuanLyMonHoc.add(miQuanlyLopHP = createMenuItem("Quản lý lớp học phần"));
		
		menuBar.add(menuQuanLyNganh = createMenu("Quản lý ngành đào tạo"));
		menuQuanLyNganh.add(miQuanLyNganh = createMenuItem("Danh sách ngành đào tạo"));
		menuQuanLyNganh.add(miQuanLyLopChuyenNganh = createMenuItem("Quản lý lớp chuyên ngành"));
		
		return menuBar;
	}
	
	private JMenu createMenu(String name) {
		JMenu menu = new JMenu(name);
		return menu;
	}
	
	private JMenuItem createMenuItem(String name) {
		JMenuItem menuItem = new JMenuItem(name);
		menuItem.addActionListener(this);
		return menuItem;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == miSVTinChi) {
			carLayout.show(conter, "SVTC");
			return;
		}
		if(e.getSource() == miSVNienChe) {
			carLayout.show(conter, "SVNC");
			return;
		}
		if(e.getSource() == miCapNhatDiem) {
			carLayout.show(conter, "capNhatDiem");
			return;
		}
		if(e.getSource() == miQuanLyHocPhan) {
			carLayout.show(conter, "dsHP");
			return;
		}
		if(e.getSource() == miQuanlyLopHP) {
			carLayout.show(conter, "lopHP");
			return;
		}
		if(e.getSource() == miQuanLyNganh) {
			carLayout.show(conter, "dsNganh");
			return;
		}
		if(e.getSource() == miQuanLyLopChuyenNganh) {
			carLayout.show(conter, "lopCN");
			return;
		}
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new MainQuanLy();
			}
		});
	}
}


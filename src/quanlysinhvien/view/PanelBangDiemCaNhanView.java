package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class PanelBangDiemCaNhanView extends JPanel{
	private JLabel gtIdSinhVien, gtHoTen, gtNgaySinh, gtLop, gtChuongTrinh, gtHeHoc, gtTrangThai;
	private JTable tableDiem, tableKetQua;
	private JTextField tfHocKy, tfIdHP, tfTenHP, tfTinChi, tfLopHoc, tfDiemQT, tfDiemThi, tfDiemChu;
	private String[] titleCols1 = {"Học kỳ", "Mã HP", "Tên HP", "TC", "Lớp học", "Điểm QT", "Điểm thi", "Điểm chữ"};
	private String[] titleCols2 = {"Học kỳ", "GPA", "CPA", "TC qua", "TC tích lũy", "TC nợ ĐK", "TC ĐK", "Trình độ",
			"mức CC", "CTĐT", "Dự kiến XLHT", "Xử lý chính thức"};
	 
	 
	public PanelBangDiemCaNhanView() {
		setLayout(new BorderLayout(5, 10));
		add(createTitlePanel(), BorderLayout.NORTH);
		add(createMainPanel(), BorderLayout.CENTER);
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 10, 5, 10));
		JLabel label = new JLabel("Bảng điểm cá nhân");
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);
		label.setIcon(new ImageIcon(this.getClass().getResource("/score.png")));
//		label.setIcon(new ImageIcon("/score.png"));
		
		panel.add(label);
		panel.setBackground(new Color(0x009999));
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.add(createHeaderMain(), BorderLayout.NORTH);
		panel.add(createMainTable(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createHeaderMain() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBorder(new EmptyBorder(0, 50, 0, 750));
		panel.add(createTitleHeader(), BorderLayout.NORTH);
		panel.add(createMainHeader(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createMainTable() {
		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.setBorder(new EmptyBorder(0, 35, 5, 35));
		panel.add(createTablePanel1());
		panel.add(createTablePanel2());
		
		return panel;
	}
	
	private JPanel createTablePanel1() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createTitle("Bảng điểm học phần sinh viên"), BorderLayout.NORTH);
		panel.add(createTable(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createTablePanel2() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(20, 0, 5, 0));
		panel.add(createTitle("Kết quả học tập sinh viên"), BorderLayout.NORTH);
		panel.add(createTable2(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createTable2() {
		JPanel panel = new JPanel(new BorderLayout());
		tableKetQua = new JTable();
		loadData(tableKetQua, titleCols2);
		JScrollPane scroll = new JScrollPane(tableKetQua);
		panel.add(scroll, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createTitle(String name) {
		JPanel panel = new JPanel();
		panel.add(createLabel(name, Font.BOLD, 16));
		panel.setBackground(Color.LIGHT_GRAY);
		
		return panel;
	}
	
	private JPanel createTable() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		tableDiem = new JTable();
		loadData(tableDiem, titleCols1);
		JScrollPane scroll = new JScrollPane(tableDiem);
		panel.add(scroll, BorderLayout.CENTER);
		JPanel panelB = new JPanel(new GridLayout(1, 8, 5, 5));
		panelB.add(createtfTimKiem(tfHocKy));
		panelB.add(createtfTimKiem(tfIdHP));
		panelB.add(createtfTimKiem(tfTenHP));
		panelB.add(createtfTimKiem(tfTinChi));
		panelB.add(createtfTimKiem(tfLopHoc));
		panelB.add(createtfTimKiem(tfDiemQT));
		panelB.add(createtfTimKiem(tfDiemThi));
		panelB.add(createtfTimKiem(tfDiemChu));
		panel.add(panelB, BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel createtfTimKiem(JTextField tf) {
		JPanel panel = new JPanel(new BorderLayout(0, 0));
		panel.add(tf = new JTextField(), BorderLayout.CENTER);
		
		panel.add(new JLabel(new ImageIcon(this.getClass().getResource("/key.png"))), BorderLayout.EAST);
		return panel;
	}
	
	private void loadData(JTable table, String[] titleCols) {
		SwingUtilities.invokeLater(new Runnable(){public void run(){
			String data[][] = null;
		    //Update the model here
			DefaultTableModel tableModel = new DefaultTableModel(data, titleCols) {
				@Override
				public boolean isCellEditable(int row, int column) {
					// TODO Auto-generated method stub
					return false;
				}
			};
			table.setModel(tableModel);
		}});
	}
	
	private JPanel createTitleHeader() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.add(createLabel("Thông tin sinh viên", Font.PLAIN, 14));
		
		return panel;
	}
	
	private JPanel createMainHeader() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		JPanel panelL = new JPanel(new GridLayout(7, 1, 5, 5));
		panelL.add(createLabel("+ Mã sinh viên:", Font.PLAIN, 14));
		panelL.add(createLabel("+ Họ tên SV:", Font.PLAIN, 14));
		panelL.add(createLabel("+ Ngày sinh:", Font.PLAIN, 14));
		panelL.add(createLabel("+ Lớp:", Font.PLAIN, 14));
		panelL.add(createLabel("+ Chương trình:", Font.PLAIN, 14));
		panelL.add(createLabel("+ Hệ học", Font.PLAIN, 14));
		panelL.add(createLabel("+ Trạng thái:", Font.PLAIN, 14));
		panel.add(panelL, BorderLayout.WEST);
		
		JPanel panelR = new JPanel(new GridLayout(7, 1, 5, 5));
		panelR.add(gtIdSinhVien = createLabel("20153752", Font.BOLD, 14));
		panelR.add(gtHoTen = createLabel("Nguyễn Tài Tiêu", Font.BOLD, 14));
		panelR.add(gtNgaySinh = createLabel("27.10.1997", Font.BOLD, 14));
		panelR.add(gtLop = createLabel("CNTT2-1 K60", Font.BOLD, 14));
		panelR.add(gtChuongTrinh = createLabel("CT Nhóm ngành CNTT-TT 2-2015", Font.BOLD, 14));
		panelR.add(gtHeHoc = createLabel("Đại học", Font.BOLD, 14));
		panelR.add(gtTrangThai = createLabel("Học", Font.BOLD, 14));
		panel.add(panelR, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JLabel createLabel(String name, int indam, int kichThuoc) {
		JLabel label = new JLabel(name);
		label.setFont(new Font("Caribli", indam, kichThuoc));
		
		return label;
	}
	
	
}

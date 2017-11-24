package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import quanlysinhvien.model.HocPhan;

public class PanelLopChuyenNganhView extends JPanel{
	private JTable table;
	private JTextField tfIdNganh, tfIdLopChuyenNganh, tfTenNganh, tfTenChuNhiem, tfTimKiem;
	private JButton btnThem, btnSua, btnXoa, btnCapNhat, btnHuy, btnTimKiem;
	private JComboBox<String> timKiemCB;
	private String[] titleCols = {"Mã lớp", "Chủ nhiệm", "Mã ngành", "Tên ngành"};
	private String[] timKiemVals = {"Mã lớp", "Chủ nhiệm", "Mã ngành"};
//	private String[] hocKyVals = {"20172", "20171", "20163", "20162", "20161", "20153", "20152", "20151"};
	
	
	
	public PanelLopChuyenNganhView() {
		setLayout(new BorderLayout(10, 10));
		add(createTitlePanel(), BorderLayout.PAGE_START);
		add(createMainPanel(), BorderLayout.CENTER);
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Danh sách lớp chuyên ngành");
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);
		panel.setBackground(new Color(0x009999));
		panel.add(label, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 15, 15));
		panel.setBorder(new EmptyBorder(10, 50, 20, 50));
		panel.add(createTablePanel());
		panel.add(createBottomPanel());
		return panel;
	}
	
	private JPanel createTablePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		JScrollPane scroll = new JScrollPane(table = new JTable());
		loadData(table, new ArrayList<HocPhan>());
		panel.add(scroll);
		
		return panel;
	}
	
	private JPanel createBottomPanel() {
		JPanel panel = new JPanel(new BorderLayout(15, 15));
		panel.add(createInputPanel(), BorderLayout.CENTER);
		panel.add(createButtonPanel(), BorderLayout.EAST);
		return panel;
	}
	private JPanel createInputPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
		panel.setBorder(new EmptyBorder(0, 0, 192, 0));
		panel.add(createInputLeftPanel());
		panel.add(createInputRightPanel());
		
		return panel;
	}
	
	private JPanel createInputLeftPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(0, 0, 0, 15));
		JPanel panelL = new JPanel(new GridLayout(3, 1, 5, 5));
		panelL.add(new JLabel("Mã ngành:"));
		panelL.add(new JLabel("Mã Lớp CN:"));
		panelL.add(new JLabel(""));
		
		JPanel panelR = new JPanel(new GridLayout(3, 1, 5, 5));
		panelR.add(tfIdNganh = new JTextField());
		panelR.add(tfIdLopChuyenNganh = new JTextField());
		panelR.add(new JLabel(""));

		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createInputRightPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		JPanel panelL = new JPanel(new GridLayout(3, 1 , 5, 5));
		panelL.add(new JLabel("Tên ngành:"));
		panelL.add(new JLabel("Chủ nhiệm lớp:"));
		panelL.add(new JLabel(""));
		
		JPanel panelR = new JPanel(new GridLayout(3, 1, 5, 5));
		panelR.add(tfTenNganh = new JTextField());
		panelR.add(tfTenChuNhiem = new JTextField());
		panelR.add(btnCapNhat = new JButton("Cập nhật"));
		
		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
		panel.setBorder(new EmptyBorder(0, 45, 100, 0));
		panel.add(createTimKiemPanel());
		panel.add(createBtnOtherPanel());
		
		return panel;
	}
	
	private JPanel createTimKiemPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
		panel.setBorder(new EmptyBorder(0, 0, 58, 0));
		JPanel panelButCB = new JPanel(new GridLayout(1, 2, 5, 5));
		panelButCB.add(btnTimKiem = new JButton("Tìm kiếm"));
		panelButCB.add(timKiemCB = new JComboBox<>(timKiemVals));
		
		JPanel panelTF = new JPanel(new BorderLayout());
		panelTF.add(tfTimKiem = new JTextField());
		panel.add(panelButCB);
		panel.add(panelTF);
		
		return panel;
	}
	
	private JPanel createBtnOtherPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
		panel.setBorder(new EmptyBorder(0, 55, 0, 55));
		panel.add(btnThem = new JButton("Thêm"));
		panel.add(btnSua = new JButton("Sửa"));
		panel.add(btnXoa = new JButton("Xóa"));
		panel.add(btnHuy = new JButton("Hủy"));
		
		return panel;
	}
	
	public void loadData(JTable table, ArrayList<HocPhan> dsHP) {
		String[][] data = convertData(dsHP);
		DefaultTableModel model = new DefaultTableModel(data, titleCols);
		table.setModel(model);
	}
	
	private String[][] convertData(ArrayList<HocPhan> list) {
		int size = list.size();
		String data[][] = new String[size][titleCols.length];
		for (int i = 0; i < size; i++) {
			HocPhan hp = list.get(i);
			data[i][0] = hp.getIdHocPhan();
			data[i][1] = hp.getTenHP();
			data[i][2] = hp.getSoTinChi() + "";
			data[i][3] = hp.getIdNganh();
			data[i][4] = hp.getTrongSo() + "";
		}
		return data;
	}
}

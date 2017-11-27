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

public class PanelLopHocPhanView extends  JPanel{
	private JTable table;
	private JTextField tfIdLop, tfThoiGian, tfTuanHoc, tfPhongHoc, tfTenGV, tfSoSVMax, tfSoSVHienTai, tfTimKiem;
	private JButton btnThem, btnSua, btnXoa, btnLuu, btnHuy, btnTimKiem, btnCapNhatSV;
	private JComboBox<String> timKiemCB, hocKyCB;
	private String[] titleCols = {"Học kỳ", "Mã lớp", "Thời gian", "Tuần học", "Phòng học", "Tên giảng viên", "Số SV max", "Số SV hiện tại"};
	private String[] timKiemVals = {"Mã lớp", "Phòng học", "Tên giảng viên", "Số SV max"};
	private String[] hocKyVals = {"20172", "20171", "20163", "20162", "20161", "20153", "20152", "20151"};
	
	
	
	public PanelLopHocPhanView() {
		setLayout(new BorderLayout(10, 10));
		add(createTitlePanel(), BorderLayout.PAGE_START);
		add(createMainPanel(), BorderLayout.CENTER);
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Danh sách lớp học phần");
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
		panel.setBorder(new EmptyBorder(0, 0, 125, 0));
		panel.add(createInputLeftPanel());
		panel.add(createInputRightPanel());
		
		return panel;
	}
	
	private JPanel createInputLeftPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(0, 0, 0, 10));
		JPanel panelL = new JPanel(new GridLayout(5, 1 , 5, 5));
		panelL.add(new JLabel("Học kỳ:"));
		panelL.add(new JLabel("Mã lớp:"));
		panelL.add(new JLabel("Thời gian:"));
		panelL.add(new JLabel("Tuần học:"));
		
		JPanel panelR = new JPanel(new GridLayout(5, 1, 5, 5));
		panelR.add(createPanelCB(hocKyCB, hocKyVals));
		panelR.add(tfIdLop = new JTextField());
		panelR.add(tfThoiGian = new JTextField());
		panelR.add(tfTuanHoc = new JTextField());

		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createInputRightPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		JPanel panelL = new JPanel(new GridLayout(5, 1 , 5, 5));
		panelL.add(new JLabel("Phòng học:"));
		panelL.add(new JLabel("Tên giảng viên:"));
		panelL.add(new JLabel("Số SV max:"));
		panelL.add(new JLabel("Số SV hiện tại:"));
		
		JPanel panelR = new JPanel(new GridLayout(5, 1, 5, 5));
		panelR.add(tfPhongHoc = new JTextField());
		panelR.add(tfTenGV = new JTextField());
		panelR.add(tfSoSVMax = new JTextField());
		panelR.add(tfSoSVHienTai = new JTextField());
		
		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(0, 25, 25, 0));
		panel.add(createTimKiemPanel(), BorderLayout.NORTH);
		panel.add(createBtnOtherPanel(), BorderLayout.CENTER);

		return panel;
	}

	private JPanel createTimKiemPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
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
		JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
		panel.setBorder(new EmptyBorder(10, 80, 65, 80));
		JPanel panel1 = new JPanel(new GridLayout(1, 2, 10, 10));
		panel1.add(btnThem = new JButton("Thêm"));
		panel1.add(btnSua = new JButton("Sửa"));
		JPanel panel2 = new JPanel(new GridLayout(1, 2, 10, 10));
		panel2.add(btnXoa = new JButton("Xóa"));
		panel2.add(btnHuy = new JButton("Hủy"));
		panel.add(panel1);
		panel.add(panel2);
		panel.add(btnLuu = new JButton("Lưu"));
		panel.add(btnCapNhatSV = new JButton("Cập nhật DS sinh viên"));

		return panel;
	}
	
	private JPanel createPanelCB(JComboBox<String> cb, String[] vals) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(0, 0, 0, 145));
		panel.add(cb = new JComboBox<>(vals));
		
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

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

import quanlysinhvien.model.SinhVienNienChe;

public class PanelSinhVienNienCheView extends JPanel{
	private JTable table = new JTable();
	private JButton btnThem, btnSua, btnXoa, btnCapNhap, btnHuy, btnTimKiem;
	private JComboBox<String> timKiemCB;
	private JTextField tfIdSV, tfHoTen, tfKhoa, tfNgaySinh, tfGioiTinh, tfEmail, tfSoDT, tfDiaChi, tfDiemTB, tfaTongSoKy, tfTimKiem;
	private String[] titleCols = {"Mã sinh viên", "Họ tên", "Khóa", "Ngày sinh", "Giới tính", 
			"Email", "Số ĐT", "Địa chỉ", "Điểm TB", "Tổng số kỳ"};
	private String[] timKiemVals = {"Mã sinh viên", "Họ tên", "Khóa", "Địa chỉ", "Điểm TB", "Tổng số kỳ"};
	
	public PanelSinhVienNienCheView() {
		setLayout(new BorderLayout(10, 10));
		add(createTitlePanel(), BorderLayout.NORTH);
		add(createMainPanel(), BorderLayout.CENTER);
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel();
		panel.add(createLabel("Danh sách sinh viên niên chế", Font.BOLD, 18, 0xFFFF00));
		panel.setBackground(new Color(0x009999));
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
		panel.setBorder(new EmptyBorder(10, 50, 40, 50));
		panel.add(createTablePanel());
		panel.add(createBottomPanel());
		
		return panel;
	}
	
	private JPanel createTablePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		JScrollPane scroll = new JScrollPane(table = new JTable());
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(titleCols);
		table.setModel(model);
		panel.add(scroll, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createBottomPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.add(createInputPanel(), BorderLayout.CENTER);
		panel.add(createButtonPanel(), BorderLayout.EAST);
		
		return panel;
	}
	
	private JPanel createInputPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 15, 15));
		panel.setBorder(new EmptyBorder(0, 0, 80, 0));
		panel.add(createInputLeftPanel());
		panel.add(createInputRightPanel());
		
		return panel;
	}
	
	private JPanel createInputLeftPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		JPanel panelL = new JPanel(new GridLayout(6, 1, 5, 5));
		panelL.add(new JLabel("Mã sinh viên:"));
		panelL.add(new JLabel("Họ tên:"));
		panelL.add(new JLabel("Khóa:"));
		panelL.add(new JLabel("Ngày sinh:"));
		panelL.add(new JLabel("Điểm TB:"));
		panelL.add(new JLabel(""));
		
		
		JPanel panelR = new JPanel(new GridLayout(6, 1, 5, 5));
		panelR.add(tfIdSV = new JTextField());
		panelR.add(tfHoTen = new JTextField());
		panelR.add(tfKhoa = new JTextField());
		panelR.add(tfNgaySinh = new JTextField());
		panelR.add(tfDiemTB = new JTextField());
		panelR.add(new JLabel(""));
		
		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel createInputRightPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		JPanel panelL = new JPanel(new GridLayout(6, 1, 5, 5));
		panelL.add(new JLabel("Giới tính:"));
		panelL.add(new JLabel("Email:"));
		panelL.add(new JLabel("Số ĐT:"));
		panelL.add(new JLabel("Địa chỉ:"));
		panelL.add(new JLabel("Tổng số kỳ:"));
		panelL.add(new JLabel(""));
		
		JPanel panelR = new JPanel(new GridLayout(6, 1, 5, 5));
		panelR.add(tfIdSV = new JTextField());
		panelR.add(tfHoTen = new JTextField());
		panelR.add(tfKhoa = new JTextField());
		panelR.add(tfNgaySinh = new JTextField());
		panelR.add(tfaTongSoKy = new JTextField());
		panelR.add(btnCapNhap = new JButton("Cập nhật"));
		
		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
		panel.setBorder(new EmptyBorder(0, 25, 100, 0));
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
	
	private JLabel createLabel(String name, int inDam, int kichThuoc, int maMau) {
		JLabel label = new JLabel(name);
		label.setFont(new Font("Caribli", inDam, kichThuoc));
		if(maMau != 0)
			label.setForeground(new Color(maMau));
		
		return label;
	}
	
	public void loadData(JTable table, ArrayList<SinhVienNienChe> dsSVNC) {
		String[][] data = convertData(dsSVNC);
		DefaultTableModel model = new DefaultTableModel(data, titleCols);
		table.setModel(model);
	}
	
	private String[][] convertData(ArrayList<SinhVienNienChe> list) {
		int size = list.size();
		String data[][] = new String[size][titleCols.length];
		for (int i = 0; i < size; i++) {
//			tfIdSV, tfHoTen, tfKhoa, tfNgaySinh, tfGioiTinh, tfEmail, tfSoDT, tfDiaChi, tfDiemTB, tfaTongSoKy
			SinhVienNienChe svnc = list.get(i);
			data[i][0] = svnc.getIdSinhVien();
			data[i][1] = svnc.getHoTen();
			data[i][2] = svnc.getKhoa();
			data[i][3] = svnc.getNgaySinh();
			data[i][4] = svnc.getGioiTinh();
			data[i][5] = svnc.getEmail();
			data[i][6] = svnc.getSoDT();
			data[i][7] = svnc.getDiaChi();
			data[i][8] = svnc.getDiemTB() + "";
			data[i][9] = svnc.getTongSoKy();
		}
		return data;
	}
}

package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import quanlysinhvien.model.DiemHocPhan;
import quanlysinhvien.model.SinhVien;
import quanlysinhvien.model.SinhVienTinChi;

public class CapNhatDiemSVView extends JDialog{
	private JTable table;
	private JTextField tfIdHocPhan, tfIdLopHoc, tfDiemQT, tfDiemThi;
	private JButton btnThem, btnSua, btnXoa, btnHuy;
	private JComboBox<String> hocKyCB;
	private String[] titleCols = {"Học kỳ", "Mã học phần", "Tên học phần", "Tín chỉ", "Lớp học", "Điểm QT", "Điểm thi", "Điểm chữ"};
	private String[] hocKyVals = {"20172", "20171", "20163", "20162", "20161", "20153", "20152", "20151"};
	
	public CapNhatDiemSVView() {
		setSize(950, 650);
		setLocationRelativeTo(null);
		add(createTitlePanel(), BorderLayout.NORTH);
		add(createMainPanel(), BorderLayout.CENTER);
		setVisible(true);
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0x0009999));
		panel.add(createLabel());
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
		panel.setBorder(new EmptyBorder(5, 25, 20, 25));
		panel.add(createTablePanel());
		panel.add(createBottomPanel());
		
		return panel;
	}
	
	private JPanel createTablePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		JScrollPane scroll = new JScrollPane(table = new JTable());
		loadData(table, new ArrayList<DiemHocPhan>());
		
		panel.add(scroll);
		return panel;
	}
	
	private JPanel createBottomPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBorder(new EmptyBorder(10, 160, 100, 160));
		panel.add(createInputPanel(), BorderLayout.CENTER);
		panel.add(createButtonPanel(), BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel createInputPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 25, 15));
		panel.add(createInputLeft());
		panel.add(createInputRight());
		
		return panel;
	}
	
	private JPanel createInputLeft() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		JPanel panelL = new JPanel(new GridLayout(3, 1, 5, 5));
		panelL.add(new JLabel("Học kỳ:"));
		panelL.add(new JLabel("Mã hoc phần:"));
		panelL.add(new JLabel("Lớp học:"));
		JPanel panelR = new JPanel(new GridLayout(3, 1, 5, 5));
		JPanel panelHocKy = new JPanel(new GridLayout());
		panelHocKy.add(hocKyCB = new JComboBox<>(hocKyVals));
		panelHocKy.setBorder(new EmptyBorder(0, 0, 0, 120));
		panelR.add(panelHocKy);
		panelR.add(tfIdHocPhan = new JTextField());
		panelR.add(tfIdLopHoc = new JTextField());
		
		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel createInputRight() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		JPanel panelL = new JPanel(new GridLayout(3, 1, 5, 5));
		panelL.add(new JLabel("Điểm QT:"));
		panelL.add(new JLabel("Điểm thi:"));
		JPanel panelR = new JPanel(new GridLayout(3, 1, 5, 5));
		panelR.add(tfDiemQT = new JTextField());
		panelR.add(tfDiemThi = new JTextField());
		
		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 2, 5, 10));
		panel.setBorder(new EmptyBorder(10, 95, 0, 95));
		panel.add(btnThem = new JButton("Thêm"));
		panel.add(btnSua = new JButton("Sửa"));
		panel.add(btnXoa = new JButton("Xóa"));
		panel.add(btnHuy = new JButton("Hủy"));
		
		return panel;
	}
	
	private JLabel createLabel() {
		JLabel label = new JLabel("Danh sách sinh viên");
		label.setFont(new Font("Calibri", Font.BOLD, 16));
		label.setForeground(Color.yellow);
		return label;
	}
	
	public void loadData(JTable table, ArrayList<DiemHocPhan> dsDiem) {
		String[][] data = convertData(dsDiem);
		DefaultTableModel model = new DefaultTableModel(data, titleCols);
		table.setModel(model);
	}
	
	private String[][] convertData(ArrayList<DiemHocPhan> dsDiem) {
		int size = dsDiem.size();
		String data[][] = new String[size][titleCols.length];
		for(int i = 0; i < size; i++) {
//			"Học kỳ", "Mã học phần", "Tên học phần", "Tín chỉ", "Lớp học", "Điểm QT", "Điểm thi", "Điểm chữ"
			data[i][0] = dsDiem.get(i).getHocKy();
			data[i][1] = dsDiem.get(i).getIdHocPhan();
			data[i][2] = dsDiem.get(i).getTenHP();
			data[i][3] = dsDiem.get(i).getTinChi() + "";
			data[i][4] = dsDiem.get(i).getLopHoc();
			data[i][5] = dsDiem.get(i).getDiemQT() + "";
			data[i][6] = dsDiem.get(i).getDiemThi() + "";
			data[i][7] = dsDiem.get(i).getDiemChu();
		}
		
		return data;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new CapNhatDiemSVView();
			}
		});
	}
}

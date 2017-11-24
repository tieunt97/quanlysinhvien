package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
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

public class PanelChuongTrinhDaoTaoSVView extends JPanel{
	private JTextField tfIdSinhVien, tfIdHP, tfTenHP, tfKyHoc, tfTinChi, tfDiemChu, tfDiemSo, tfVien_Khoa;
	private JTable table;
	private String[] titleCols = {"Mã HP", "Tên HP", "Kỳ học", "Tín chỉ", "Điểm chữ", "Điểm số", "Viện/Khoa"};
	
	
	public PanelChuongTrinhDaoTaoSVView() {
		setLayout(new BorderLayout(15, 15));
		add(createTitlePanel(), BorderLayout.NORTH);
		add(createMainPanel(), BorderLayout.CENTER);
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 10, 5, 10));
		JLabel label = new JLabel("Các môn trong chương trình đào tạo của sinh viên");
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);
		label.setIcon(new ImageIcon(this.getClass().getResource("/list.png")));
		panel.add(label);
		panel.setBackground(new Color(0x009999));
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout(15, 15));
		panel.setBorder(new EmptyBorder(0, 35, 20, 35));
		panel.add(createHeaderMain(), BorderLayout.NORTH);
		panel.add(createTablePanel(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createHeaderMain() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBorder(new EmptyBorder(0, 0, 0, 900));
		panel.add(createLabel("Mã sinh viên:", 16), BorderLayout.WEST);
		panel.add(tfIdSinhVien = new JTextField(20), BorderLayout.CENTER);
		tfIdSinhVien.setText("20153752");
		tfIdSinhVien.setEditable(false);
		
		return panel;
	}
	
	private JPanel createTablePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createTitle(), BorderLayout.NORTH);
		panel.add(createTable(), BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel createTitle() {
		JPanel panel = new JPanel();
		panel.add(createLabel("Chương trình đào tạo sinh viên", 18));
		panel.setBackground(Color.LIGHT_GRAY);
		
		return panel;
	}
	
	private JPanel createTable() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		table = new JTable();
		loadData(table);
		JScrollPane scroll = new JScrollPane(table);
		panel.add(scroll, BorderLayout.CENTER);
		JPanel panelB = new JPanel(new GridLayout(1, 7, 5, 5));
		panelB.add(createtfTimKiem(tfIdHP));
		panelB.add(createtfTimKiem(tfTenHP));
		panelB.add(createtfTimKiem(tfKyHoc));
		panelB.add(createtfTimKiem(tfTinChi));
		panelB.add(createtfTimKiem(tfDiemChu));
		panelB.add(createtfTimKiem(tfDiemSo));
		panelB.add(createtfTimKiem(tfVien_Khoa));
		panel.add(panelB, BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel createtfTimKiem(JTextField tf) {
		JPanel panel = new JPanel(new BorderLayout(0, 0));
		panel.add(tf = new JTextField(), BorderLayout.CENTER);
		
		panel.add(new JLabel(new ImageIcon(this.getClass().getResource("/key.png"))), BorderLayout.EAST);
		return panel;
	}
	
	private JLabel createLabel(String name, int kickThuoc) {
		JLabel lb = new JLabel(name);
		lb.setFont(new Font("Caribli", Font.PLAIN, kickThuoc));
		
		return lb;
	}
	
	private void loadData(JTable table) {
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
}

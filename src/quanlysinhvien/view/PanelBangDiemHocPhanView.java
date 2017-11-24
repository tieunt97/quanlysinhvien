package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class PanelBangDiemHocPhanView extends JPanel{
	private JTextField tfIdSinhVien, tfHocKy, tfIdHP, tfTenHP, tfTinChi, tfDiemHP;
	private JLabel lbSumHP, lbSumTC;
	private String titleCols[] = {"Học kỳ", "Mã HP", "Tên HP", "TC", "Điểm học phẩn"};
	private JTable table;
	 
	
	public PanelBangDiemHocPhanView() {
		setLayout(new BorderLayout(15, 15));
		add(createTitlePanel(), BorderLayout.NORTH);
		add(createMainPanel(), BorderLayout.CENTER);
	} 

	private JPanel createTitlePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 10, 5, 10));
		JLabel label = new JLabel("Bảng điểm học phần");
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);
		label.setIcon(new ImageIcon(this.getClass().getResource("/score.png")));
		panel.add(label);
		panel.setBackground(new Color(0x009999));
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout(15, 15));
		panel.setBorder(new EmptyBorder(5, 40, 40, 40));
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
		panel.add(createBottom(), BorderLayout.SOUTH);
		return panel;
	}
	
	private JPanel createTitle() {
		JPanel panel = new JPanel();
		panel.add(createLabel("Bảng điểm học phần sinh viên", 18));
		panel.setBackground(Color.LIGHT_GRAY);
		
		return panel;
	}
	
	private JPanel createTable() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		table = new JTable();
		loadData(table);
		JScrollPane scroll = new JScrollPane(table);
		panel.add(scroll, BorderLayout.CENTER);
		JPanel panelB = new JPanel(new GridLayout(1, 5, 5, 5));
		panelB.add(createtfTimKiem(tfHocKy));
		panelB.add(createtfTimKiem(tfIdHP));
		panelB.add(createtfTimKiem(tfTenHP));
		panelB.add(createtfTimKiem(tfTinChi));
		panelB.add(createtfTimKiem(tfDiemHP));
		panel.add(panelB, BorderLayout.SOUTH);
		
		return panel;
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
	
	private JPanel createBottom() {
		JPanel panel = new JPanel(new GridLayout(1, 2));
		JPanel panelL = new JPanel();
		panelL.add(createLabel("C =", 12));
		panelL.add(lbSumTC = new JLabel("32"));
		panel.add(panelL);
		
		JPanel panelR  = new JPanel();
		panelR.add(createLabel("TC =", 12));
		panelR.add(lbSumHP = new JLabel("69"));
		panel.add(panelR);
		
		return panel;
	}
	
	private JLabel createLabel(String name, int kickThuoc) {
		JLabel lb = new JLabel(name);
		lb.setFont(new Font("Caribli", Font.PLAIN, kickThuoc));
		
		return lb;
	}
	
	private JPanel createtfTimKiem(JTextField tf) {
		JPanel panel = new JPanel(new BorderLayout(0, 0));
		panel.add(tf = new JTextField(), BorderLayout.CENTER);
		
		panel.add(new JLabel(new ImageIcon(this.getClass().getResource("/key.png"))), BorderLayout.EAST);
		return panel;
	}
}

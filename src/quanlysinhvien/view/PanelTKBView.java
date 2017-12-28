package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.xmlbeans.impl.jam.JField;

import quanlysinhvien.model.LopHocPhan;

public class PanelTKBView extends JPanel{
	private String titleCols[] = {"Thời gian", "Tuần học", "Phòng học", "Mã lớp", "Loại lớp", "Mã HP", "Tên lớp"};
	private JTable table;
	private JLabel labStatus;
	private JTextField tfHocKy;
	private JButton btnTraCuu;
	
	public PanelTKBView() {
		setLayout(new BorderLayout(10, 10));
		setBorder(new EmptyBorder(20, 60, 180, 60));
		add(createTitlePanel(), BorderLayout.PAGE_START);
		add(createMainPanel(), BorderLayout.CENTER);
		add(createStatusPanel(), BorderLayout.SOUTH);
	}
	
	private JPanel createTitlePanel() {
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT, 5, 5);
		JPanel panelTraCuuTKB = new JPanel(layout);
		panelTraCuuTKB.add(new JLabel("Nhập học kỳ:"));
		panelTraCuuTKB.add(tfHocKy = new JTextField(10));
		panelTraCuuTKB.add(btnTraCuu = new JButton("Tra cứu"));
		
		return panelTraCuuTKB;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		JPanel panelTitle = new JPanel();
		JLabel label = new JLabel("Thời khóa biểu");
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);
		label.setIcon(new ImageIcon(this.getClass().getResource("/tkb.png")));
		panelTitle.setBackground(new Color(0x009999));
		panelTitle.add(label, BorderLayout.CENTER);
		table = new JTable();
		JScrollPane scroll = new JScrollPane(table);
		panel.add(panelTitle, BorderLayout.NORTH);
		panel.add(scroll, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createStatusPanel() {
		JPanel panel = new JPanel();
		labStatus = new JLabel("");
		labStatus.setFont(new Font("Caribli", Font.BOLD, 16));
		labStatus.setForeground(Color.BLUE);
		panel.add(labStatus);
		
		return panel;
	}
	
	public void loadData(JTable table, ArrayList<LopHocPhan> dsLopHP) {
		String[][] data = convertData(dsLopHP);
		DefaultTableModel model = new DefaultTableModel(data, titleCols);
		table.setModel(model);
		table.getColumnModel().getColumn(6).setPreferredWidth(175);
	}
	
	private String[][] convertData(ArrayList<LopHocPhan> dsLopHP){
		int size = dsLopHP.size();
		String data[][] = new String[size][titleCols.length];
		for(int i = 0; i < size; i++) {
			LopHocPhan lopHP = dsLopHP.get(i);
			data[i][0] = lopHP.getThoiGian();
			data[i][1] = lopHP.getTuanHoc();
			data[i][2] = lopHP.getPhongHoc();
			data[i][3] = lopHP.getIdLop();
			data[i][4] = lopHP.getLoaiLop();
			data[i][5] = lopHP.getHocPhan().getIdHocPhan();
			data[i][6] = lopHP.getHocPhan().getTenHP();
		}
		
		return data;
	}
	
	public JTable getTable() {
		return table;
	}

	public JLabel getLabStatus() {
		return labStatus;
	}

	public JTextField getTfHocKy() {
		return tfHocKy;
	}

	public JButton getBtnTraCuu() {
		return btnTraCuu;
	}
	
//	public static void main(String[] args) {
//		JFrame frame = new JFrame();
//		frame.add(new PanelTKBView());
//		frame.setSize(1200, 700);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setLocationRelativeTo(null);
//		frame.setVisible(true);
//	}
}

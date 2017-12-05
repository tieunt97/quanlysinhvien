package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.DiemHocPhan;

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
		label.setIcon(new ImageIcon("images/list.png"));
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
		panel.add(createLabel("MÃ£ sinh viÃªn:", 16), BorderLayout.WEST);
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
//		loadData(table);
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
		
		panel.add(new JLabel(new ImageIcon("/key.png")), BorderLayout.EAST);
		return panel;
	}
	
	private JLabel createLabel(String name, int kickThuoc) {
		JLabel lb = new JLabel(name);
		lb.setFont(new Font("Caribli", Font.PLAIN, kickThuoc));
		
		return lb;
	}
	
	public void loadData(JTable table, ArrayList<DiemHocPhan> dsDiem, String[] vienKhoa) {
		SwingUtilities.invokeLater(new Runnable(){public void run(){
			String data[][] = convertData(dsDiem, vienKhoa);
		    //Update the model here
			DefaultTableModel tableModel = new DefaultTableModel(data, titleCols) {
				@Override
				public boolean isCellEditable(int row, int column) {
					// TODO Auto-generated method stub
					return false;
				}
			};
			table.setModel(tableModel);
			table.getColumnModel().getColumn(1).setPreferredWidth(200);
		}});
	}
	
	private String[][] convertData(ArrayList<DiemHocPhan> dsDiem, String[] vienKhoa) {
		String[][] data = new String[dsDiem.size()][7];
		for (int i = 0; i < dsDiem.size(); i++) {
			data[i][0] = dsDiem.get(i).getIdHocPhan();
			data[i][1] = dsDiem.get(i).getTenHP();
			data[i][2] = dsDiem.get(i).getHocKy();
			data[i][3] = dsDiem.get(i).getTinChi()+"";
			data[i][4] = dsDiem.get(i).getDiemChu();
			data[i][5] = dsDiem.get(i).getDiemThang4()+"";
			data[i][6] = vienKhoa[i];
		}
		
		return data;
	}
	
	public JTextField getTfIdSinhVien() {
		return tfIdSinhVien;
	}

	public void setTfIdSinhVien(JTextField tfIdSinhVien) {
		this.tfIdSinhVien = tfIdSinhVien;
	}

	public JTextField getTfIdHP() {
		return tfIdHP;
	}

	public void setTfIdHP(JTextField tfIdHP) {
		this.tfIdHP = tfIdHP;
	}

	public JTextField getTfTenHP() {
		return tfTenHP;
	}

	public void setTfTenHP(JTextField tfTenHP) {
		this.tfTenHP = tfTenHP;
	}

	public JTextField getTfKyHoc() {
		return tfKyHoc;
	}

	public void setTfKyHoc(JTextField tfKyHoc) {
		this.tfKyHoc = tfKyHoc;
	}

	public JTextField getTfTinChi() {
		return tfTinChi;
	}

	public void setTfTinChi(JTextField tfTinChi) {
		this.tfTinChi = tfTinChi;
	}

	public JTextField getTfDiemChu() {
		return tfDiemChu;
	}

	public void setTfDiemChu(JTextField tfDiemChu) {
		this.tfDiemChu = tfDiemChu;
	}

	public JTextField getTfDiemSo() {
		return tfDiemSo;
	}

	public void setTfDiemSo(JTextField tfDiemSo) {
		this.tfDiemSo = tfDiemSo;
	}

	public JTextField getTfVien_Khoa() {
		return tfVien_Khoa;
	}

	public void setTfVien_Khoa(JTextField tfVien_Khoa) {
		this.tfVien_Khoa = tfVien_Khoa;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}
}

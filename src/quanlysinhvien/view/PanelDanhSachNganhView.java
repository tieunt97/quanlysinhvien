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
import quanlysinhvien.model.Nganh;

public class PanelDanhSachNganhView extends JPanel{
	private JTable table;
	private JTextField tfIdNganh, tfTenNganh, tfTimKiem;
	private JButton btnThem, btnSua, btnXoa, btnTimKiem, btnHuy, btnXemDSNganh;
	private JComboBox<String> timKiemCB;
	private String[] titleCols = {"Mã ngành", "Tên ngành"};
	private String[] timKiemVals = {"Mã ngành", "Tên ngành"};
	
	
	public PanelDanhSachNganhView() {
		setLayout(new BorderLayout(10, 10));
		add(createTitlePanel(), BorderLayout.NORTH);
		add(createMainPanel(), BorderLayout.CENTER);
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel();
		panel.add(createLabel("Danh sách ngành", Font.BOLD, 18, 0xFFFF00));
		panel.setBackground(new Color(0x009999));
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 15, 15));
		panel.setBorder(new EmptyBorder(10, 50, 40, 50));
		panel.add(createTablePanel());
		panel.add(createRightPanel());
		
		return panel;
	}
	
	private JPanel createTablePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		JScrollPane scroll = new JScrollPane(table = new JTable());
		panel.add(scroll);
		
		return panel;
	}
	
	private JPanel createRightPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(50, 0, 35, 0));
		panel.add(createInputPanel(), BorderLayout.NORTH);
		panel.add(createBottomPanel(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createInputPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
		panel.add(createInputLeftPanel());
		panel.add(createInputRightPanel());
		
		return panel;
	}
	
	private JPanel createBottomPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(0, 40, 0, 40));
		panel.add(createTimKiemPanel(), BorderLayout.NORTH);
		panel.add(createButtonPanel(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
		panel.setBorder(new EmptyBorder(25, 45, 180, 45));
		JPanel panel1 = new JPanel(new GridLayout(1, 2, 10, 10));
		panel1.add(btnThem = new JButton("Thêm"));
		panel1.add(btnSua = new JButton("Sửa"));
		JPanel panel2 = new JPanel(new GridLayout(1, 2, 10, 10));
		panel2.add(btnXoa = new JButton("Xóa"));
		panel2.add(btnHuy = new JButton("Hủy"));
		panel.add(panel1);
		panel.add(panel2);
		panel.add(btnXemDSNganh = new JButton("Xem danh sách lớp chuyên ngành"));

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
	
	private JPanel createInputLeftPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		JPanel panelL = new JPanel(new GridLayout(2, 1, 10, 10));
		panelL.add(new JLabel("Mã ngành:"));
		panelL.add(new JLabel(""));
		JPanel panelR = new JPanel(new GridLayout(2, 1, 10, 10));
		panelR.add(tfIdNganh = new JTextField());
		panelR.add(new JLabel(""));
		
		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createInputRightPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		JPanel panelL = new JPanel(new GridLayout(2, 1, 10, 10));
		panelL.add(new JLabel("Tên ngành:"));
		JPanel panelR = new JPanel(new GridLayout(2, 1, 10, 10));
		panelR.add(tfTenNganh = new JTextField());
		JPanel panelCapNhat = new JPanel(new GridLayout());
		panelR.add(panelCapNhat);
		
		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JLabel createLabel(String name, int inDam, int kichThuoc, int maMau) {
		JLabel label = new JLabel(name);
		label.setFont(new Font("Caribli", inDam, kichThuoc));
		if(maMau != 0)
			label.setForeground(new Color(maMau));
		
		return label;
	}
	
	public void loadData(JTable table, ArrayList<Nganh> dsNganh, String timKiem, String giaTri) {
		String[][] data = convertData(dsNganh, timKiem, giaTri);
		DefaultTableModel model = new DefaultTableModel(data, titleCols);
		table.setModel(model);
	}
	
	private String[][] convertData(ArrayList<Nganh> list, String timKiem, String giaTri) {
		int size = list.size();
		String data[][] = new String[size][titleCols.length];
		int index = 0;
		for (int i = 0; i < size; i++) {
			Nganh nganh = list.get(i);
			switch(timKiem) {
			case "Mã ngành": 
				if(nganh.getIdNganh().toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = nganh.getIdNganh();
					data[index][1] = nganh.getTenNganh();
					index++;
				}
				break;
			case "Tên ngành":
				if(nganh.getTenNganh().toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = nganh.getIdNganh();
					data[index][1] = nganh.getTenNganh();
					index++;
				}
				break;
			case "":{
				data[index][0] = nganh.getIdNganh();
				data[index][1] = nganh.getTenNganh();
				index++;
				}
				break;
			}
		}
		if(!giaTri.equals("")) {
			String[][] datatk = new String[index][titleCols.length];
			for(int i = 0; i < index; i++) {
				datatk[i] = data[i];
			}
			return datatk;
		}
		return data;
	}

	public JTable getTable() {
		return table;
	}

	public JTextField getTfIdNganh() {
		return tfIdNganh;
	}

	public JTextField getTfTenNganh() {
		return tfTenNganh;
	}

	public JTextField getTfTimKiem() {
		return tfTimKiem;
	}

	public JButton getBtnThem() {
		return btnThem;
	}

	public JButton getBtnSua() {
		return btnSua;
	}

	public JButton getBtnXoa() {
		return btnXoa;
	}

	public JButton getBtnTimKiem() {
		return btnTimKiem;
	}

	public JButton getBtnHuy() {
		return btnHuy;
	}

	public JButton getBtnXemDSNganh() {
		return btnXemDSNganh;
	}

	public JComboBox<String> getTimKiemCB() {
		return timKiemCB;
	}
	
}

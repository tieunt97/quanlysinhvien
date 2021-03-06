package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
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

import quanlysinhvien.model.DiemHocPhan;
import quanlysinhvien.model.HocPhan;
import quanlysinhvien.model.SinhVien;
import quanlysinhvien.model.SinhVienNienChe;
import quanlysinhvien.model.SinhVienTinChi;

public class PanelBangDiemHocPhanView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField tfIdSinhVien, tfHocKy, tfIdHP, tfTenHP, tfTinChi, tfDiemHP;
	private String titleCols[] = { "Học kỳ", "Mã HP", "Tên HP", "TC", "Điểm học phần" };
	private JLabel labSumHP, labSumTC;
	private JTable table;

	String[][] data;

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
		label.setIcon(new ImageIcon("images/score.png"));
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
		tfIdSinhVien.setText("");
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
		JScrollPane scroll = new JScrollPane(table);
		panel.add(scroll, BorderLayout.CENTER);
		JPanel panelB = new JPanel(new GridLayout(1, 5, 5, 5));

		tfHocKy = new JTextField();
		panelB.add(createtfTimKiem(tfHocKy));
		tfIdHP = new JTextField();
		panelB.add(createtfTimKiem(tfIdHP));
		tfTenHP = new JTextField();
		panelB.add(createtfTimKiem(tfTenHP));
		tfTinChi = new JTextField();
		panelB.add(createtfTimKiem(tfTinChi));
		tfDiemHP = new JTextField();
		panelB.add(createtfTimKiem(tfDiemHP));
		panel.add(panelB, BorderLayout.SOUTH);

		return panel;
	}

	public void loadData(JTable table, SinhVien sv, String timKiem, String giaTri) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				String data[][] = convertData(sv, timKiem, giaTri);
				// Update the model here
				@SuppressWarnings("serial")
				DefaultTableModel tableModel = new DefaultTableModel(data, titleCols) {
					@Override
					public boolean isCellEditable(int row, int column) {
						// TODO Auto-generated method stub
						return false;
					}
				};

				table.setModel(tableModel);
			}
		});
	}

	private String[][] convertData(SinhVien sv, String timKiem, String giaTri) {
		ArrayList<DiemHocPhan> dsDiem = sv.getDsDiemHP();
		String[][] data = new String[dsDiem.size()][5];
		int index = 0;
		for (int i = 0; i < dsDiem.size(); i++) {
			HocPhan hp = dsDiem.get(i).getHocPhan();
			switch (timKiem) {
			case "hocKy":
				if (dsDiem.get(i).getHocKy().toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = dsDiem.get(i).getHocKy();
					data[index][1] = hp.getIdHocPhan();
					data[index][2] = hp.getTenHP();
					data[index][3] = hp.getSoTinChi() + "";
					if (sv instanceof SinhVienTinChi)
						data[index][4] = dsDiem.get(i).getDiemChu();
					else
						data[index][4] = dsDiem.get(i).getDiemThang10() + "";
					index++;
				}
				break;
			case "idHP":
				if (hp.getIdHocPhan().toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = dsDiem.get(i).getHocKy();
					data[index][1] = hp.getIdHocPhan();
					data[index][2] = hp.getTenHP();
					data[index][3] = hp.getSoTinChi() + "";
					if (sv instanceof SinhVienTinChi)
						data[index][4] = dsDiem.get(i).getDiemChu();
					else
						data[index][4] = dsDiem.get(i).getDiemThang10() + "";
					index++;
				}
				break;
			case "tenHP":
				if (hp.getTenHP().toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = dsDiem.get(i).getHocKy();
					data[index][1] = hp.getIdHocPhan();
					data[index][2] = hp.getTenHP();
					data[index][3] = hp.getSoTinChi() + "";
					if (sv instanceof SinhVienTinChi)
						data[index][4] = dsDiem.get(i).getDiemChu();
					else
						data[index][4] = dsDiem.get(i).getDiemThang10() + "";
					index++;
				}
				break;
			case "soTC":
				if (hp.getSoTinChi() == Integer.parseInt(giaTri)) {
					data[index][0] = dsDiem.get(i).getHocKy();
					data[index][1] = hp.getIdHocPhan();
					data[index][2] = hp.getTenHP();
					data[index][3] = hp.getSoTinChi() + "";
					if (sv instanceof SinhVienTinChi)
						data[index][4] = dsDiem.get(i).getDiemChu();
					else
						data[index][4] = dsDiem.get(i).getDiemThang10() + "";
					index++;
				}
			case "diemHP":
				if ((sv instanceof SinhVienTinChi) && dsDiem.get(i).getDiemChu().toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = dsDiem.get(i).getHocKy();
					data[index][1] = hp.getIdHocPhan();
					data[index][2] = hp.getTenHP();
					data[index][3] = hp.getSoTinChi() + "";
					data[index][4] = dsDiem.get(i).getDiemChu();
					index++;
					break;
				} else if ((sv instanceof SinhVienNienChe)
						&& Double.toString(dsDiem.get(i).getDiemThang10()).toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = dsDiem.get(i).getHocKy();
					data[index][1] = hp.getIdHocPhan();
					data[index][2] = hp.getTenHP();
					data[index][3] = hp.getSoTinChi() + "";
					data[index][4] = dsDiem.get(i).getDiemThang10() + "";
					index++;
				}
			case "":
			{
				data[index][0] = dsDiem.get(i).getHocKy();
				data[index][1] = hp.getIdHocPhan();
				data[index][2] = hp.getTenHP();
				data[index][3] = hp.getSoTinChi() + "";
				if (sv instanceof SinhVienTinChi)
					data[index][4] = dsDiem.get(i).getDiemChu();
				else
					data[index][4] = dsDiem.get(i).getDiemThang10() + "";
				index++;
			}
				
			}
		}

		if(index == dsDiem.size())
			return data;
		else {
			String data1[][] = new String[index][titleCols.length];
			for(int i = 0; i < index; i++) {
				data1[i] = data[i];
			}
			return data1;
		}
	}

	private JPanel createBottom() {
		JPanel panel = new JPanel(new GridLayout(1, 2));
		JPanel panelL = new JPanel();
		panelL.add(createLabel("C =", 12));
		panelL.add(labSumHP = new JLabel());
		panel.add(panelL);

		JPanel panelR = new JPanel();
		panelR.add(createLabel("TC =", 12));
		panelR.add(labSumTC = new JLabel());
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
		panel.add(tf, BorderLayout.CENTER);

		panel.add(new JLabel(new ImageIcon("images/key.png")), BorderLayout.EAST);
		return panel;
	}

	public JTextField getTfHocKy() {
		return tfHocKy;
	}

	public void setTfHocKy(JTextField tfHocKy) {
		this.tfHocKy = tfHocKy;
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

	public JTextField getTfTinChi() {
		return tfTinChi;
	}

	public void setTfTinChi(JTextField tfTinChi) {
		this.tfTinChi = tfTinChi;
	}

	public JTextField getTfDiemHP() {
		return tfDiemHP;
	}

	public void setTfDiemHP(JTextField tfDiemHP) {
		this.tfDiemHP = tfDiemHP;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JTextField getTfIdSinhVien() {
		return tfIdSinhVien;
	}

	public JLabel getLabSumHP() {
		return labSumHP;
	}

	public JLabel getLabSumTC() {
		return labSumTC;
	}

}

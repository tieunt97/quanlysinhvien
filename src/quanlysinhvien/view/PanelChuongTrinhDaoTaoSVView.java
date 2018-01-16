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

import quanlysinhvien.model.ChuongTrinhDaoTao;
import quanlysinhvien.model.HocPhan;
import quanlysinhvien.model.SinhVien;
import quanlysinhvien.model.SinhVienTinChi;

public class PanelChuongTrinhDaoTaoSVView extends JPanel {
	private JTextField tfIdSinhVien, tfIdHP, tfTenHP, tfKyHoc, tfTinChi, tfDiemChu, tfDiemSo, tfVien_Khoa;
	private JTable table;
	private String[] titleCols = { "Mã HP", "Tên HP", "Kỳ học", "Tín chỉ", "Điểm chữ", "Điểm số", "Viện/Khoa" };
	private String[] titleCols1 = { "Mã HP", "Tên HP", "Kỳ học", "Tín chỉ", "Điểm TB", "Viện/Khoa" };
	private SinhVien sv;

	public PanelChuongTrinhDaoTaoSVView(SinhVien sv) {
		this.sv = sv;
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
		JScrollPane scroll = new JScrollPane(table = new JTable());
		panel.add(scroll, BorderLayout.CENTER);
		JPanel panelB = new JPanel(new GridLayout(1, 7, 5, 5));
		tfIdHP = new JTextField();
		panelB.add(createtfTimKiem(tfIdHP));
		tfTenHP = new JTextField();
		panelB.add(createtfTimKiem(tfTenHP));
		tfKyHoc = new JTextField();
		panelB.add(createtfTimKiem(tfKyHoc));
		tfTinChi = new JTextField();
		panelB.add(createtfTimKiem(tfTinChi));
		if (sv instanceof SinhVienTinChi) {
			tfDiemChu = new JTextField();
			panelB.add(createtfTimKiem(tfDiemChu));
		}
		tfDiemSo = new JTextField();
		panelB.add(createtfTimKiem(tfDiemSo));
		tfVien_Khoa = new JTextField();
		panelB.add(createtfTimKiem(tfVien_Khoa));
		panel.add(panelB, BorderLayout.SOUTH);

		return panel;
	}

	private JPanel createtfTimKiem(JTextField tf) {
		JPanel panel = new JPanel(new BorderLayout(0, 0));
		panel.add(tf, BorderLayout.CENTER);

		panel.add(new JLabel(new ImageIcon("/key.png")), BorderLayout.EAST);
		return panel;
	}

	private JLabel createLabel(String name, int kickThuoc) {
		JLabel lb = new JLabel(name);
		lb.setFont(new Font("Caribli", Font.PLAIN, kickThuoc));

		return lb;
	}

	public void loadData(JTable table, String timKiem, String giaTri) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				String data[][] = convertData(timKiem, giaTri);
				// Update the model here

				DefaultTableModel tableModel;
				if (sv instanceof SinhVienTinChi)
					tableModel = new DefaultTableModel(data, titleCols) {
						@Override
						public boolean isCellEditable(int row, int column) {
							// TODO Auto-generated method stub
							return false;
						}
					};
				else
					tableModel = new DefaultTableModel(data, titleCols1) {
						@Override
						public boolean isCellEditable(int row, int column) {
							// TODO Auto-generated method stub
							return false;
						}
					};
				table.setModel(tableModel);
				table.getColumnModel().getColumn(1).setPreferredWidth(200);
			}
		});
	}

	private String[][] convertData(String timKiem, String giaTri) {
		ArrayList<ChuongTrinhDaoTao> ctdt = sv.getCtdt();
		int size = ctdt.size();
		String[][] data = (sv instanceof SinhVienTinChi)?new String[size][7]:new String[size][6];
		int index = 0;
		for (int i = 0; i < size; i++) {
			HocPhan hp = ctdt.get(i).getHocPhan();
			switch (timKiem) {
			case "idHP":
				if (hp.getIdHocPhan().toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = hp.getIdHocPhan();
					data[index][1] = hp.getTenHP();
					data[index][2] = ctdt.get(i).getKyHoc() + "";
					data[index][3] = hp.getSoTinChi() + "";
					if (sv instanceof SinhVienTinChi) {
						data[index][4] = ctdt.get(i).getDiemChu();
						double diem4 = ctdt.get(i).getDiemSo();
						if (diem4 == 0.0)
							data[index][5] = "";
						else
							data[index][5] = diem4 + "";
						data[index][6] = hp.getIdNganh();
					} else {
						data[index][4] = ctdt.get(i).getDiemSo() + "";
						data[index][5] = hp.getIdNganh();
					}
					index++;
				}
				break;
			case "tenHP":
				if (hp.getTenHP().toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = hp.getIdHocPhan();
					data[index][1] = hp.getTenHP();
					data[index][2] = ctdt.get(i).getKyHoc() + "";
					data[index][3] = hp.getSoTinChi() + "";
					if (sv instanceof SinhVienTinChi) {
						data[index][4] = ctdt.get(i).getDiemChu();
						double diem4 = ctdt.get(i).getDiemSo();
						if (diem4 == 0.0)
							data[index][5] = "";
						else
							data[index][5] = diem4 + "";
						data[index][6] = hp.getIdNganh();
					} else {
						data[index][4] = ctdt.get(i).getDiemSo() + "";
						data[index][5] = hp.getIdNganh();
					}
					index++;
				}
				break;
			case "hocKy":
				if (ctdt.get(i).getKyHoc() == Integer.parseInt(giaTri)) {
					data[index][0] = hp.getIdHocPhan();
					data[index][1] = hp.getTenHP();
					data[index][2] = ctdt.get(i).getKyHoc() + "";
					data[index][3] = hp.getSoTinChi() + "";
					if (sv instanceof SinhVienTinChi) {
						data[index][4] = ctdt.get(i).getDiemChu();
						double diem4 = ctdt.get(i).getDiemSo();
						if (diem4 == 0.0)
							data[index][5] = "";
						else
							data[index][5] = diem4 + "";
						data[index][6] = hp.getIdNganh();
					} else {
						data[index][4] = ctdt.get(i).getDiemSo() + "";
						data[index][5] = hp.getIdNganh();
					}
					index++;
				}
				break;
			case "tinChi":
				if (hp.getSoTinChi() == Integer.parseInt(giaTri)) {
					data[index][0] = hp.getIdHocPhan();
					data[index][1] = hp.getTenHP();
					data[index][2] = ctdt.get(i).getKyHoc() + "";
					data[index][3] = hp.getSoTinChi() + "";
					if (sv instanceof SinhVienTinChi) {
						data[index][4] = ctdt.get(i).getDiemChu();
						double diem4 = ctdt.get(i).getDiemSo();
						if (diem4 == 0.0)
							data[index][5] = "";
						else
							data[index][5] = diem4 + "";
						data[index][6] = hp.getIdNganh();
					} else {
						data[index][4] = ctdt.get(i).getDiemSo() + "";
						data[index][5] = hp.getIdNganh();
					}
					index++;
				}
				break;
			case "diemChu":
				if (ctdt.get(i).getDiemChu().toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = hp.getIdHocPhan();
					data[index][1] = hp.getTenHP();
					data[index][2] = ctdt.get(i).getKyHoc() + "";
					data[index][3] = hp.getSoTinChi() + "";
					data[index][4] = ctdt.get(i).getDiemChu();
					double diem4 = ctdt.get(i).getDiemSo();
					if (diem4 == 0.0)
						data[index][5] = "";
					else
						data[index][5] = diem4 + "";
					data[index][6] = hp.getIdNganh();
					index++;
				}
				break;
			case "diem4":
				if (ctdt.get(i).getDiemSo() == Double.parseDouble(giaTri)) {
					data[index][0] = hp.getIdHocPhan();
					data[index][1] = hp.getTenHP();
					data[index][2] = ctdt.get(i).getKyHoc() + "";
					data[index][3] = hp.getSoTinChi() + "";
					if (sv instanceof SinhVienTinChi) {
						data[index][4] = ctdt.get(i).getDiemChu();
						double diem4 = ctdt.get(i).getDiemSo();
						if (diem4 == 0.0)
							data[index][5] = "";
						else
							data[index][5] = diem4 + "";
						data[index][6] = hp.getIdNganh();
					} else {
						data[index][4] = ctdt.get(i).getDiemSo() + "";
						data[index][5] = hp.getIdNganh();
					}
					index++;
				}
				break;
			case "vienKhoa":
				if (hp.getIdNganh().toLowerCase().indexOf(giaTri) >= 0) {
					data[index][0] = hp.getIdHocPhan();
					data[index][1] = hp.getTenHP();
					data[index][2] = ctdt.get(i).getKyHoc() + "";
					data[index][3] = hp.getSoTinChi() + "";
					if (sv instanceof SinhVienTinChi) {
						data[index][4] = ctdt.get(i).getDiemChu();
						double diem4 = ctdt.get(i).getDiemSo();
						if (diem4 == 0.0)
							data[index][5] = "";
						else
							data[index][5] = diem4 + "";
						data[index][6] = hp.getIdNganh();
					} else {
						data[index][4] = ctdt.get(i).getDiemSo() + "";
						data[index][5] = hp.getIdNganh();
					}
					index++;
				}
				break;
			case "": {
				data[index][0] = hp.getIdHocPhan();
				data[index][1] = hp.getTenHP();
				data[index][2] = ctdt.get(i).getKyHoc() + "";
				data[index][3] = hp.getSoTinChi() + "";
				if (sv instanceof SinhVienTinChi) {
					data[index][4] = ctdt.get(i).getDiemChu();
					double diem4 = ctdt.get(i).getDiemSo();
					if (diem4 == 0.0)
						data[index][5] = "";
					else
						data[index][5] = diem4 + "";
					data[index][6] = hp.getIdNganh();
				} else {
					data[index][4] = ctdt.get(i).getDiemSo() + "";
					data[index][5] = hp.getIdNganh();
				}
				index++;
			}
				break;
			}

		}

		if (index < size) {
			String[][] data1 = new String[index][7];
			for (int i = 0; i < index; i++) {
				data1[i][0] = data[i][0];
				data1[i][1] = data[i][1];
				data1[i][2] = data[i][2];
				data1[i][3] = data[i][3];
				data1[i][4] = data[i][4];
				data1[i][5] = data[i][5];
				data1[i][6] = data[i][6];
			}
			return data1;
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

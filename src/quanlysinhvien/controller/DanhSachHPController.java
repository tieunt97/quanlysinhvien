package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.HocPhan;
import quanlysinhvien.model.SinhVienTinChi;
import quanlysinhvien.view.PanelDanhSachHPView;

public class DanhSachHPController {
	private PanelDanhSachHPView danhSachHP;
	private JTable table;
	private JButton btnThem, btnSua, btnXoa, btnHuy, btnTimKiem, btnLuu;
	private JComboBox<String> timKiemCB;
	private JTextField tfIdHocPhan, tfTenHP, tfSoTC, tfIdNganh, tfTrongSo, tfTimKiem, tfSoTCHocPhi;
	private ArrayList<HocPhan> dsHocPhan;
	private String fileName;

	public DanhSachHPController(PanelDanhSachHPView danhSachHP) {
		this.danhSachHP = danhSachHP;
		fileName = "C:\\Users\\tieu_nt\\Desktop\\Lập trình hướng đối tượng\\quanlysinhvien\\danhsachhocphan\\dsHocPhan.xlsx";
		try {
			this.dsHocPhan = readFile(fileName);
			System.out.println("Success readFile.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			dsHocPhan = new ArrayList<>();
			e.printStackTrace();
		}
		this.table = danhSachHP.getTable();
		this.btnThem = danhSachHP.getBtnThem();
		this.btnSua = danhSachHP.getBtnSua();
		this.btnXoa = danhSachHP.getBtnXoa();
		this.btnHuy = danhSachHP.getBtnHuy();
		this.btnTimKiem = danhSachHP.getBtnTimKiem();
		this.tfIdHocPhan = danhSachHP.getTfIdHocPhan();
		this.tfTenHP = danhSachHP.getTfTenHP();
		this.tfSoTC = danhSachHP.getTfSoTC();
		this.tfSoTCHocPhi = danhSachHP.getTfSoTCHocPhi();
		this.tfIdNganh = danhSachHP.getTfIdNganh();
		this.tfTrongSo = danhSachHP.getTfTrongSo();
		this.tfTimKiem = danhSachHP.getTfTimKiem();
		this.timKiemCB = danhSachHP.getTimKiemCB();
		this.btnLuu = danhSachHP.getBtnLuu();
		this.danhSachHP.loadData(table, dsHocPhan, "", "");

		setAction();
	}

	private void setAction() {
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				if (row >= 0) {
					tfIdHocPhan.setText((String) table.getValueAt(row, 0));
					tfIdHocPhan.setEnabled(false);
					tfTenHP.setText((String) table.getValueAt(row, 1));
					tfSoTC.setText((String) table.getValueAt(row, 2));
					tfSoTCHocPhi.setText((String) table.getValueAt(row, 3));
					tfIdNganh.setText((String) table.getValueAt(row, 4));
					tfTrongSo.setText((String) table.getValueAt(row, 5));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnThem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				HocPhan hp = getHocPhan();
				if (hp != null) {
					if (checkID(hp.getIdHocPhan())) {
						dsHocPhan.add(hp);
						danhSachHP.loadData(table, dsHocPhan, "", "");
						JOptionPane.showMessageDialog(null, "Thêm thành công");
						cancel();
					} else {
						JOptionPane.showMessageDialog(null, "Trùng mã học phần", "Error insert",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnSua.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(null, "Cần chọn một hàng để sửa", "Error update",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				HocPhan hp = getHocPhan();
				if (hp != null) {
					for (int i = 0; i < dsHocPhan.size(); i++) {
						if (dsHocPhan.get(i).getIdHocPhan().equals(hp.getIdHocPhan())) {
							dsHocPhan.get(i).setTenHP(hp.getTenHP());
							dsHocPhan.get(i).setSoTinChi(hp.getSoTinChi());
							dsHocPhan.get(i).setSoTCHocPhi(hp.getSoTCHocPhi());
							dsHocPhan.get(i).setIdNganh(hp.getIdNganh());
							dsHocPhan.get(i).setTrongSo(hp.getTrongSo());
							break;
						}
					}
					JOptionPane.showMessageDialog(null, "Cập nhật thành công");
					danhSachHP.loadData(table, dsHocPhan, "", "");
					cancel();
				}
			}
		});

		btnXoa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				if (row < 0) {
					JOptionPane.showMessageDialog(null, "Cần chọn một hàng để xóa", "Error delete",
							JOptionPane.ERROR_MESSAGE);
				} else {
					int select = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa không?", "Notify",
							JOptionPane.YES_NO_OPTION);
					if (select == 0) {
						String id = (String) table.getValueAt(row, 0);
						for (int i = 0; i < dsHocPhan.size(); i++) {
							if (dsHocPhan.get(i).getIdHocPhan().equals(id)) {
								dsHocPhan.remove(i);
								JOptionPane.showMessageDialog(null, "Xóa thành công");
								danhSachHP.loadData(table, dsHocPhan, "", "");
								return;
							}
						}
					}
				}
			}
		});

		btnHuy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cancel();
				danhSachHP.loadData(table, dsHocPhan, "", "");
			}
		});

		btnTimKiem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String timKiem = timKiemCB.getSelectedItem().toString();
				String giaTri = tfTimKiem.getText().trim().toLowerCase();
				danhSachHP.loadData(table, dsHocPhan, timKiem, giaTri);
			}
		});
		
		btnLuu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					printFile(dsHocPhan, fileName);
					JOptionPane.showMessageDialog(null, "Đã lưu");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Có lỗi xảy ra", "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});

	}

	private void cancel() {
		tfIdHocPhan.setText("");
		tfIdHocPhan.setEnabled(true);
		tfTenHP.setText("");
		tfIdNganh.setText("");
		tfSoTC.setText("");
		tfSoTCHocPhi.setText("");
		tfTrongSo.setText("");
		tfTimKiem.setText("");
	}

	private boolean checkID(String id) {
		for (int i = 0; i < dsHocPhan.size(); i++) {
			if (dsHocPhan.get(i).getIdHocPhan().equals(id))
				return false;
		}
		return true;
	}

	private HocPhan getHocPhan() {
		String idHocPhan = tfIdHocPhan.getText().trim().toUpperCase();
		String tenHP = tfTenHP.getText().trim();
		String idNganh = tfIdNganh.getText().trim().toUpperCase();
		int soTCHocPhi;
		int soTinChi;
		double trongSo;
		if (idHocPhan.equals("") || tenHP.equals("") || idNganh.equals("")) {
			JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		try {
			soTinChi = Integer.parseInt(tfSoTC.getText().trim());
			soTCHocPhi = Integer.parseInt(tfSoTCHocPhi.getText().trim());
			trongSo = Double.parseDouble(tfTrongSo.getText().trim());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Hãy kiểm tra các giá trị: Số tín chỉ, Số TC học phí", "Error",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}

		HocPhan hp = new HocPhan(idHocPhan, tenHP, soTinChi, soTCHocPhi, idNganh, trongSo);
		return hp;
	}
	
	private ArrayList<HocPhan> readFile(String fileName) throws IOException {
		ArrayList<HocPhan> dsHocPhan = new ArrayList<>();
		FileInputStream inputStream = new FileInputStream(new File(fileName));
        
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
         
        Row nextRow;
        if(iterator.hasNext()) nextRow = iterator.next();  //loại bỏ dòng tiêu đề
        while (iterator.hasNext()) {
            nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            ArrayList<String> dataHP = new ArrayList<>();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String data = "";
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                    	data = cell.getStringCellValue();
                    	break;
                    case Cell.CELL_TYPE_NUMERIC:
                        data = Double.toString(cell.getNumericCellValue());
                        break;
                    default: data = "";
                        break;
                }
                dataHP.add(data);
            }
            HocPhan hp = new HocPhan(dataHP.get(0), dataHP.get(1), (int)(Double.parseDouble(dataHP.get(2))), (int)(Double.parseDouble(dataHP.get(3))), dataHP.get(4), Double.parseDouble(dataHP.get(5)));
            dsHocPhan.add(hp);
        }
        
        workbook.close();
        inputStream.close();
        return dsHocPhan;
	}
	
	private void printFile(ArrayList<HocPhan> dsHocPhan, String fileName) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		createHeader(sheet);
		int count = 0;
		
		for(HocPhan hp: dsHocPhan) {
			Row row = sheet.createRow(++count);
			writeHocPhan(hp, row);
		}
		
		FileOutputStream fileout = new FileOutputStream(fileName);
		workbook.write(fileout);
	}
	
	private void createHeader(Sheet sheet) {
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		cellStyle.setFont(font);
		
		Row row = sheet.createRow(0);
		Cell cellIdHP  = row.createCell(1);
		cellIdHP.setCellStyle(cellStyle);
		cellIdHP.setCellValue("Mã học phần");
		
		Cell cellTenHP = row.createCell(2);
		cellTenHP.setCellStyle(cellStyle);
		cellTenHP.setCellValue("Tên HP");
		
		Cell cellSoTC = row.createCell(3);
		cellSoTC.setCellStyle(cellStyle);
		cellSoTC.setCellValue("Số TC");
		
		Cell cellSoTCHocPhi = row.createCell(4);
		cellSoTCHocPhi.setCellStyle(cellStyle);
		cellSoTCHocPhi.setCellValue("Số TC học phí");
		
		Cell cellIdNganh = row.createCell(5);
		cellIdNganh.setCellStyle(cellStyle);
		cellIdNganh.setCellValue("Mã ngành");
		
		Cell cellTrongSo = row.createCell(6);
		cellTrongSo.setCellStyle(cellStyle);
		cellTrongSo.setCellValue("Trọng số");
		
	}

	private void writeHocPhan(HocPhan hp, Row row) {
		Cell cell = row.createCell(1);
		cell.setCellValue(hp.getIdHocPhan());
		cell = row.createCell(2);
		cell.setCellValue(hp.getTenHP());
		cell = row.createCell(3);
		cell.setCellValue(hp.getSoTinChi());
		cell = row.createCell(4);
		cell.setCellValue(hp.getSoTCHocPhi());
		cell = row.createCell(5);
		cell.setCellValue(hp.getIdNganh());
		cell = row.createCell(6);
		cell.setCellValue(hp.getTrongSo());
	}
}

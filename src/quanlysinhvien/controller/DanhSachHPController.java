package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.HocPhan;
import quanlysinhvien.model.QuanLy;
import quanlysinhvien.view.PanelDanhSachHPView;

public class DanhSachHPController {

    private PanelDanhSachHPView danhSachHP;
    private JTable table;
    private JButton btnThem, btnSua, btnXoa, btnHuy, btnTimKiem;
    private JComboBox<String> timKiemCB;
    private JTextField tfIdHocPhan, tfTenHP, tfSoTC, tfIdNganh, tfTrongSo, tfTimKiem, tfSoTCHocPhi, tfIdHPDK;
    private String fileName;
    private QuanLy quanLy;
    private XSSFWorkbook workbook;

    public DanhSachHPController(PanelDanhSachHPView danhSachHP, QuanLy quanLy) {
        this.danhSachHP = danhSachHP;
        this.quanLy = quanLy;
        fileName = "quanlysinhvien\\danhsachhocphan\\dsHocPhan.xlsx";
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
        this.tfIdHPDK = danhSachHP.getTfIdHPDK();
        this.tfTimKiem = danhSachHP.getTfTimKiem();
        this.timKiemCB = danhSachHP.getTimKiemCB();
        this.danhSachHP.loadData(table, quanLy.getDsHocPhan(), "", ""); //load danh sách học phần lên bảng

        setAction();
    }

    //bắt sự kiện
    private void setAction() {
        table.addMouseListener(new MouseAdapter() {
        	 @Override
             public void mousePressed(MouseEvent e) {
                 int row = table.getSelectedRow();
                 if (row >= 0) {
                     tfIdHocPhan.setText((String) table.getValueAt(row, 0));
                     tfIdHocPhan.setEnabled(false);
                     tfTenHP.setText((String) table.getValueAt(row, 1));
                     tfSoTC.setText((String) table.getValueAt(row, 2));
                     tfSoTCHocPhi.setText((String) table.getValueAt(row, 3));
                     tfIdNganh.setText((String) table.getValueAt(row, 4));
                     tfTrongSo.setText((String) table.getValueAt(row, 5));
                     tfIdHPDK.setText("");
                 }
             }
		});
        btnThem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                HocPhan hp = getHocPhan();  //lấy dữ liệu học phần từ input
                if (hp != null) {
                    if (quanLy.themHocPhan(hp)) {
                    	//thêm học phần hp vào bảng
                    	((DefaultTableModel) table.getModel()).addRow(new Object[] {hp.getIdHocPhan(), hp.getTenHP(), hp.getSoTinChi() + "", 
                    			hp.getSoTCHocPhi() + "", hp.getIdNganh(), hp.getTrongSo() + "", (hp.getHocPhanDK() != null)?hp.getHocPhanDK().getIdHocPhan():"null"});
                        try {
                            addHP(hp, fileName);
                            JOptionPane.showMessageDialog(null, "Thêm thành công");
                            cancel();
                        } catch (IOException e1) {
                            System.out.println("Error insert: " + e1);
                        }
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
            	int row = table.getSelectedRow() ;
                if (row < 0) {
                    JOptionPane.showMessageDialog(null, "Cần chọn một hàng để sửa", "Error update",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                HocPhan hp = getHocPhan();
                if (hp != null) {
                    HocPhan hocPhan = quanLy.getHocPhan(hp.getIdHocPhan());
                    quanLy.xoaHocPhan(hocPhan.getIdHocPhan());
                    quanLy.themHocPhan(hp);
                    updateRowTable(hp, row);  //cập nhật dữ liệu học phần trên bảng
                    boolean ck = false;
                    try {
                        ck = updateHP(hp, fileName); //cập nhật dữ liệu học phần trong file
                        if (ck) {
                            JOptionPane.showMessageDialog(null, "Cập nhật thành công");
                        } else {
                            JOptionPane.showMessageDialog(null, "Lỗi cập nhật", "Error update", JOptionPane.ERROR_MESSAGE);
                        }
                        cancel();
                    } catch (IOException e1) {
                        System.out.println("Error update: " + e1);
                    }
                }
            }
        });

        btnXoa.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(null, "Cần chọn một hàng để xóa", "Error delete",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    int select = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa không?", "Notify",
                            JOptionPane.YES_NO_OPTION);
                    if (select == 0) {
                        String id = (String) table.getValueAt(row, 0);
                        HocPhan hocPhan = quanLy.getHocPhan(id);
                        boolean ck = false;
                        if (quanLy.xoaHocPhan(id)) {
                            //xóa hàng tương ứng row
                            ((DefaultTableModel) table.getModel()).removeRow(row);
                            try {
                                /*Xoa tren file*/
                                ck = deleteHP(hocPhan, fileName);
                                if (ck) {
                                    JOptionPane.showMessageDialog(null, "Xóa thành công");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Xóa không thành công", "Error delete", JOptionPane.ERROR_MESSAGE);
                                }
                                cancel();
                                return;
                            } catch (IOException e1) {
                                System.out.println("Error delete: " + e1);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Xóa không thành công", "Error delete", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                }
            }
        });

        btnHuy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
                danhSachHP.loadData(table, quanLy.getDsHocPhan(), "", "");
            }
        });

        btnTimKiem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String timKiem = timKiemCB.getSelectedItem().toString();
                String giaTri = tfTimKiem.getText().trim().toLowerCase();
                danhSachHP.loadData(table, quanLy.getDsHocPhan(), timKiem, giaTri);
            }
        });

    }

    //cập nhật dữ liệu học phần trên bảng
    private void updateRowTable(HocPhan hp, int row) {
    	table.setValueAt(hp.getIdHocPhan(), row, 0);
    	table.setValueAt(hp.getTenHP(), row, 1);
    	table.setValueAt(hp.getSoTinChi() + "", row, 2);
    	table.setValueAt(hp.getSoTCHocPhi() + "", row, 3);
    	table.setValueAt(hp.getIdNganh(), row, 4);
    	table.setValueAt(hp.getTrongSo() + "", row, 5);
    	table.setValueAt(hp.getHocPhanDK() != null?hp.getHocPhanDK().getIdHocPhan():"null", row, 6);
    }
    
    //reset input
    private void cancel() {
    	table.getSelectionModel().clearSelection();
        tfIdHocPhan.setText("");
        tfIdHocPhan.setEnabled(true);
        tfTenHP.setText("");
        tfIdNganh.setText("");
        tfSoTC.setText("");
        tfSoTCHocPhi.setText("");
        tfTrongSo.setText("");
        tfTimKiem.setText("");
        tfIdHPDK.setText("");
    }

    //lấy dữ liệu học phần từ input
    private HocPhan getHocPhan() {
        String idHocPhan = tfIdHocPhan.getText().trim().toUpperCase();
        String tenHP = tfTenHP.getText().trim();
        String idNganh = tfIdNganh.getText().trim().toUpperCase();
        String idHPDK = tfIdHPDK.getText().trim().toUpperCase();
        double soTCHocPhi;
        int soTinChi;
        double trongSo;
        if (idHocPhan.equals("") || tenHP.equals("") || idNganh.equals("")) {
            JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (quanLy.getKhoa_Vien(idNganh) == null) {
            JOptionPane.showMessageDialog(null, "Mã ngành không tồn tại", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        try {
            soTinChi = Integer.parseInt(tfSoTC.getText().trim());
            soTCHocPhi = Double.parseDouble(tfSoTCHocPhi.getText().trim());
            trongSo = Double.parseDouble(tfTrongSo.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hãy kiểm tra các giá trị: Số tín chỉ, Số TC học phí, Trọng số", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        HocPhan hpDK = quanLy.getHocPhan(idHPDK);
        
        if(hpDK == null) {
        	return new HocPhan(idHocPhan, tenHP, soTinChi, soTCHocPhi, idNganh, trongSo);
        }else return new HocPhan(idHocPhan, tenHP, soTinChi, soTCHocPhi, idNganh, trongSo, hpDK);
    }

    //tạo tiêu đề file danh sách học phần
    private void createHeader(Sheet sheet) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        cellStyle.setFont(font);

        Row row = sheet.createRow(0);
        Cell cellIdHP = row.createCell(1);
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
        
        Cell cellIdHPDK = row.createCell(7);
        cellIdHPDK.setCellStyle(cellStyle);
        cellIdHPDK.setCellValue("Trọng số");

    }

    //ghi dòng dữ liệu học phần ứng với row
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
        cell = row.createCell(7);
        cell.setCellValue(hp.getHocPhanDK() != null?hp.getHocPhanDK().getIdHocPhan():"null");
    }

    //thêm học phần vào file dsHocPhan
    private void addHP(HocPhan hp, String fileName) throws IOException {
        Sheet sheet = null;
        int lastRow = -1;
		try {
            FileInputStream inputStream = new FileInputStream(new File(fileName));
            workbook = new XSSFWorkbook(inputStream);
            sheet = workbook.getSheetAt(0);
            lastRow = sheet.getLastRowNum();
        } catch (Exception e) {
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet();
            System.out.println(e);
        }

        Row row = null;
        //tạo dòng mới trong file excel
        if (lastRow < 0) {
            createHeader(sheet);
            row = sheet.createRow(1);
        } else {
            row = sheet.createRow(lastRow + 1);
        }
        if (row != null) {
            writeHocPhan(hp, row);
        }

        FileOutputStream fout = new FileOutputStream(new File(fileName));
        workbook.write(fout);
        fout.close();
    }

    //cập nhật dữ liệu học phần hp trong file dsHocPhan
    private boolean updateHP(HocPhan hp, String fileName) throws IOException {
        boolean ck = false;
        FileInputStream fin = new FileInputStream(new File(fileName));
        workbook = new XSSFWorkbook(fin);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();

        Row nextRow;
        if (iterator.hasNext()) {
            nextRow = iterator.next(); // loại bỏ dòng tiêu đề
        }
        while (iterator.hasNext()) {
            nextRow = iterator.next();
            Cell cell = nextRow.getCell(1);
            String idHP = cell.getStringCellValue();
            if (idHP.equalsIgnoreCase(hp.getIdHocPhan())) {
                cell = nextRow.createCell(2);
                cell.setCellValue(hp.getTenHP());
                cell = nextRow.createCell(3);
                cell.setCellValue(hp.getSoTinChi());
                cell = nextRow.createCell(4);
                cell.setCellValue(hp.getSoTCHocPhi());
                cell = nextRow.createCell(5);
                cell.setCellValue(hp.getIdNganh());
                cell = nextRow.createCell(6);
                cell.setCellValue(hp.getTrongSo());
                cell = nextRow.createCell(7);
                cell.setCellValue(hp.getHocPhanDK() != null?hp.getHocPhanDK().getIdHocPhan():"null");
                ck = true;
                break;
            }
        }

        fin.close();

        FileOutputStream fout = new FileOutputStream(new File(fileName));
        workbook.write(fout);
        fout.close();
        return ck;
    }

    //xóa học phần hp trong dsHocPhan
    private boolean deleteHP(HocPhan hp, String fileName) throws IOException {
        boolean ck = false;
        FileInputStream fin = new FileInputStream(new File(fileName));
        workbook = new XSSFWorkbook(fin);
        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> iterator = sheet.iterator();

        Row nextRow = null;
        if (iterator.hasNext()) {
            nextRow = iterator.next(); // loại bỏ dòng tiêu đề
        }
        int i = 0;
        while (iterator.hasNext()) {
            nextRow = iterator.next();
            i++;
            Cell cell = nextRow.getCell(1);
            String idHP = cell.getStringCellValue();
            if (idHP.equalsIgnoreCase(hp.getIdHocPhan())) {
                int lastRow = sheet.getLastRowNum();
                if (i < lastRow) {
                    sheet.shiftRows(i + 1, lastRow, -1);
                    ck = true;
                }
                if (i == lastRow) {
                    Row removeRow = sheet.getRow(i);
                    if (removeRow != null) {
                        sheet.removeRow(removeRow);
                        ck = true;
                    }
                }
                break;
            }
        }

        fin.close();

        FileOutputStream fout = new FileOutputStream(new File(fileName));
        workbook.write(fout);
        fout.close();
        return ck;
    }

}

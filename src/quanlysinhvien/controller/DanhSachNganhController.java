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
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.Khoa_Vien;
import quanlysinhvien.model.QuanLy;
import quanlysinhvien.view.PanelDanhSachNganhView;
import quanlysinhvien.view.XemDanhSachLopCNView;

public class DanhSachNganhController {

    private PanelDanhSachNganhView danhSachNganh;
    private JTable table;
    private JTextField tfIdKhoa_Vien, tfTenKhoa_Vien, tfTimKiem;
    private JButton btnThem, btnSua, btnXoa, btnTimKiem, btnHuy, btnXemDSNganh;
    private JComboBox<String> timKiemCB;
    private String fileName;
    private QuanLy quanLy;

    public DanhSachNganhController(PanelDanhSachNganhView danhSachNganh, QuanLy quanLy) {
        this.danhSachNganh = danhSachNganh;
        this.quanLy = quanLy;
        this.fileName = "quanlysinhvien\\danhsachchuyennganh\\dsNganh.xlsx";
        this.table = danhSachNganh.getTable();
        this.btnThem = danhSachNganh.getBtnThem();
        this.btnSua = danhSachNganh.getBtnSua();
        this.btnXoa = danhSachNganh.getBtnXoa();
        this.btnTimKiem = danhSachNganh.getBtnTimKiem();
        this.btnHuy = danhSachNganh.getBtnHuy();
        this.btnXemDSNganh = danhSachNganh.getBtnXemDSNganh();
        this.timKiemCB = danhSachNganh.getTimKiemCB();
        this.tfIdKhoa_Vien = danhSachNganh.getTfIdKhoa_Vien();
        this.tfTenKhoa_Vien = danhSachNganh.getTfTenKhoa_Vien();
        this.tfTimKiem = danhSachNganh.getTfTimKiem();
        this.danhSachNganh.loadData(table, quanLy.getDsKhoa_Vien(), "", "");

        setAction();
    }

    //bắt sự kiện
    private void setAction() {
        table.addMouseListener(new MouseAdapter() {
        	@Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                int row = table.getSelectedRow();
                if (row >= 0) {
                    tfIdKhoa_Vien.setText((String) table.getValueAt(row, 0));
                    tfIdKhoa_Vien.setEnabled(false);
                    tfTenKhoa_Vien.setText((String) table.getValueAt(row, 1));
                }
            }
		});
        btnThem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                Khoa_Vien khoa_vien = getKhoa_Vien();
                if (khoa_vien != null) {
                    if (quanLy.themKhoa_Vien(khoa_vien)) {
                    	//thêm dữ liệu khoa viện vào bảng
                        ((DefaultTableModel)table.getModel()).addRow(new Object[] {khoa_vien.getIdKhoa_Vien(), khoa_vien.getTenKhoa_Vien()});
                        try {
                            addKhoa_Vien(khoa_vien, fileName);
                            JOptionPane.showMessageDialog(null, "Thêm thành công");
                            cancel();
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            System.out.println("Error insert: " + e1);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Trùng mã khoa viện", "Error insert",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        btnSua.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
            	int row = table.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(null, "Cần chọn một hàng để sửa", "Error update",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Khoa_Vien khoa_vien = getKhoa_Vien();
                if (khoa_vien != null) {
                    quanLy.xoaKhoa_Vien(khoa_vien.getIdKhoa_Vien());
                    quanLy.themKhoa_Vien(khoa_vien);
                    updateRowTable(khoa_vien, row); 	//cập nhật lại dữ liệu khoa viện khoa_vien trên bảng
                    boolean ck = false;
                    try {
                        ck = updateKhoa_Vien(khoa_vien, fileName);   //cập nhật dữ liệu khoa viện trong file
                        if (ck) {
                            JOptionPane.showMessageDialog(null, "Cập nhật thành công");
                        } else {
                            JOptionPane.showMessageDialog(null, "Lỗi cập nhật", "Error update", JOptionPane.ERROR_MESSAGE);
                        }
                        cancel();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        System.out.println("Error update: "+e1);
                    }
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
                        String idKhoa_Vien = (String) table.getValueAt(row, 0);
                        if (quanLy.xoaKhoa_Vien(idKhoa_Vien)) {
                        	//xóa khoa viện ứng với row trên bảng
                        	((DefaultTableModel) table.getModel()).removeRow(row);
                            boolean ck = false;
                            try {
                                ck = deleteKhoa_Vien(idKhoa_Vien, fileName);
                                if (ck) {
                                    JOptionPane.showMessageDialog(null, "Xóa thành công");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Xóa không thành công", "Error delete", JOptionPane.ERROR_MESSAGE);
                                }
                                cancel();
                                return;
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
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
                // TODO Auto-generated method stub
                cancel();
                danhSachNganh.loadData(table, quanLy.getDsKhoa_Vien(), "", "");
            }
        });
        btnTimKiem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String timKiem = timKiemCB.getSelectedItem().toString();
                String giaTri = tfTimKiem.getText().trim().toLowerCase();
                danhSachNganh.loadData(table, quanLy.getDsKhoa_Vien(), timKiem, giaTri);
            }
        });
        btnXemDSNganh.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                int row = table.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(null, "Cần chọn khoa viện để xem danh sách lớp chuyên ngành", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String idKhoa_Vien = (String) table.getValueAt(row, 0);
                String tenKhoa_Vien = (String) table.getValueAt(row, 1);
                XemDanhSachLopCNView xemDSLopCN = new XemDanhSachLopCNView(tenKhoa_Vien);
                new XemDSLopCNController(xemDSLopCN, quanLy.getDSLopCN(idKhoa_Vien));
            }
        });
    }

    private void updateRowTable(Khoa_Vien kv, int row) {
    	table.setValueAt(kv.getIdKhoa_Vien(), row, 0);
    	table.setValueAt(kv.getTenKhoa_Vien(), row, 1);
    }
    

    //tạo tiêu đề file dsKhoaVien
    private void createHeader(Sheet sheet) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        cellStyle.setFont(font);

        Row row = sheet.createRow(0);

        Cell cellHocKy = row.createCell(1);
        cellHocKy.setCellStyle(cellStyle);
        cellHocKy.setCellValue("Mã Khoa/Viện");

        Cell cellIdLop = row.createCell(2);
        cellIdLop.setCellStyle(cellStyle);
        cellIdLop.setCellValue("Tên Khoa/Viện");
    }

    //ghi dòng dữ liệu khoa_vien vào row tương ứng
    private void writeKhoaVien(Khoa_Vien khoa_vien, Row row) {
        Cell cell = row.createCell(1);
        cell.setCellValue(khoa_vien.getIdKhoa_Vien());
        cell = row.createCell(2);
        cell.setCellValue(khoa_vien.getTenKhoa_Vien());
    }

    //thêm khoa_vien vào file dsKhoaVien
    private void addKhoa_Vien(Khoa_Vien khoa_vien, String fileName) throws IOException {
        Workbook workbook = null;
        Sheet sheet = null;
        int lastRow = -1;
        try {
            FileInputStream inputStream = new FileInputStream(new File(fileName));
            workbook = new XSSFWorkbook(inputStream);
            sheet = workbook.getSheetAt(0);
            lastRow = sheet.getLastRowNum();
        } catch (Exception e) {
            // TODO: handle exception
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet();
            System.out.println(e);
        }

        Row row = null;
        if (lastRow < 0) {
            createHeader(sheet);
            row = sheet.createRow(1);
        } else {
            row = sheet.createRow(lastRow + 1);
        }
        if (row != null) {
            writeKhoaVien(khoa_vien, row);
        }

        FileOutputStream fout = new FileOutputStream(new File(fileName));
        workbook.write(fout);
        fout.close();
    }

    //cập nhật dữ liệu khoa viện trong file
    private boolean updateKhoa_Vien(Khoa_Vien khoa_vien, String fileName) throws IOException {
        boolean ck = false;
        FileInputStream fin = new FileInputStream(new File(fileName));
        Workbook workbook = new XSSFWorkbook(fin);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();

        Row nextRow;
        if (iterator.hasNext()) {
            nextRow = iterator.next(); // loại bỏ dòng tiêu đề
        }
        while (iterator.hasNext()) {
            nextRow = iterator.next();
            Cell cell = nextRow.getCell(1);
            String idKhoaVien = cell.getStringCellValue();
            if (idKhoaVien.equalsIgnoreCase(khoa_vien.getIdKhoa_Vien())) {
                cell = nextRow.createCell(2);
                cell.setCellValue(khoa_vien.getTenKhoa_Vien());
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

    //xóa khoa viện có idKhoa_Vien tương ứng trong file
    private boolean deleteKhoa_Vien(String idKhoa_Vien, String fileName) throws IOException {
        boolean ck = false;
        FileInputStream fin = new FileInputStream(new File(fileName));
        Workbook workbook = new XSSFWorkbook(fin);
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
            String idN = cell.getStringCellValue();
            if (idN.equalsIgnoreCase(idKhoa_Vien)) {
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

    //lấy dữ liệu khoa viện từ input
    private Khoa_Vien getKhoa_Vien() {
        String idNganh = tfIdKhoa_Vien.getText().toUpperCase();
        String tenNganh = tfTenKhoa_Vien.getText();
        if (idNganh.equals("") || tenNganh.equals("")) {
            JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        Khoa_Vien khoa_vien = new Khoa_Vien(idNganh, tenNganh);
        return khoa_vien;
    }

    //reset input
    private void cancel() {
        table.getSelectionModel().clearSelection();
        tfIdKhoa_Vien.setText("");
        tfIdKhoa_Vien.setEnabled(true);
        tfTenKhoa_Vien.setText("");
        tfTimKiem.setText("");
    }
}

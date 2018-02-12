package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.LopChuyenNganh;
import quanlysinhvien.model.QuanLy;
import quanlysinhvien.model.SinhVien;
import quanlysinhvien.view.CapNhatSinhVienLCNView;
import quanlysinhvien.view.PanelLopChuyenNganhView;

public class LopChuyenNganhController {

    PanelLopChuyenNganhView lopChuyenNganh;
    private JTable table;
    private JTextField tfIdNganh, tfIdLopChuyenNganh, tfTenLop, tfTenChuNhiem, tfTimKiem;
    private JButton btnThem, btnSua, btnXoa, btnHuy, btnTimKiem, btnCapNhatSV;
    private JComboBox<String> timKiemCB;
    private String fileName;
    private QuanLy quanLy;
	private Workbook workbook;

    public LopChuyenNganhController(PanelLopChuyenNganhView lopChuyenNganh, QuanLy quanLy) {
        this.lopChuyenNganh = lopChuyenNganh;
        this.quanLy = quanLy;
        fileName = "quanlysinhvien\\danhsachchuyennganh\\lopchuyennganh\\dsLopCN.xlsx";
        this.table = lopChuyenNganh.getTable();
        this.btnThem = lopChuyenNganh.getBtnThem();
        this.btnSua = lopChuyenNganh.getBtnSua();
        this.btnXoa = lopChuyenNganh.getBtnXoa();
        this.btnHuy = lopChuyenNganh.getBtnHuy();
        this.btnTimKiem = lopChuyenNganh.getBtnTimKiem();
        this.btnCapNhatSV = lopChuyenNganh.getBtnCapNhatSV();
        this.timKiemCB = lopChuyenNganh.getTimKiemCB();
        this.tfIdNganh = lopChuyenNganh.getTfIdNganh();
        this.tfIdLopChuyenNganh = lopChuyenNganh.getTfIdLopChuyenNganh();
        this.tfTenLop = lopChuyenNganh.getTfTenLop();
        this.tfTenChuNhiem = lopChuyenNganh.getTfTenChuNhiem();
        this.tfTimKiem = lopChuyenNganh.getTfTimKiem();
        this.lopChuyenNganh.loadData(table, quanLy.getDsLopChuyenNganh(), "", "");

        setAction();
    }

    //bắt sự kiện
    private void setAction() {
        table.addMouseListener(new MouseAdapter() {
        	@Override
            public void mousePressed(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    tfIdLopChuyenNganh.setText((String) table.getValueAt(row, 0));
                    tfIdLopChuyenNganh.setEnabled(false);
                    tfTenLop.setText((String) table.getValueAt(row, 1));
                    tfTenChuNhiem.setText((String) table.getValueAt(row, 2));
                    tfIdNganh.setText((String) table.getValueAt(row, 3));
                }
            }
		});
        btnThem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                LopChuyenNganh lopCN = getLopChuyenNganh();
                if (lopCN != null) {
                    if (quanLy.themLopChuyenNganh(lopCN)) {
                    	//thêm dữ liệu lopCN vào bảng
                    	((DefaultTableModel)table.getModel()).addRow(new Object[] {lopCN.getIdLopChuyenNganh(), lopCN.getTenLop(), 
                    			lopCN.getTenChuNhiem(), lopCN.getIdKhoa_Vien()});
                        try {
                            addLopCN(lopCN, fileName);  //thêm lopCN vào file
                            JOptionPane.showMessageDialog(null, "Thêm thành công");
                            cancel();
                        } catch (IOException e1) {
                            System.out.println("Error insert: " + e1);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Trùng mã lớp", "Error insert", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        btnSua.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(null, "Cần chọn một hàng để sửa", "Error update", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LopChuyenNganh lopCN = getLopChuyenNganh();
                if (lopCN != null) {
                    quanLy.xoaLopChuyenNganh(lopCN.getIdLopChuyenNganh());
                    quanLy.themLopChuyenNganh(lopCN);
                    LopChuyenNganh lopChuyenNganh = quanLy.getLopChuyenNganh(lopCN.getIdLopChuyenNganh());
                    lopChuyenNganh.setTenLop(lopCN.getTenLop());
                    lopChuyenNganh.setIdKhoa_Vien(lopCN.getIdKhoa_Vien());
                    lopChuyenNganh.setTenChuNhiem(lopCN.getTenChuNhiem());
                    updateRowTable(lopCN, row);  //cập nhật dữ liệu lopCN trên bảng
                    boolean ck = false;
                    try {
                        ck = updateLopCN(lopCN, fileName);
                        if (ck) {
                            JOptionPane.showMessageDialog(null, "Cập nhật thành công");
                        } else {
                            JOptionPane.showMessageDialog(null, "Lỗi cập nhật", "Error update", JOptionPane.ERROR_MESSAGE);
                        }
                        cancel();
                    } catch (IOException e1) {
                        System.out.println("Error update: "+e1);
                    }
                }
            }
        });
        btnXoa.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(null, "Cần chọn một hàng để xóa", "Error update", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    int select = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa không?", "Notify",
                            JOptionPane.YES_NO_OPTION);
                    if (select == 0) {
                        String idLopCN = (String) table.getValueAt(row, 0);
                        if (quanLy.xoaLopChuyenNganh(idLopCN)) {
                            ((DefaultTableModel) table.getModel()).removeRow(row);
                            boolean ck = false;
                            try {
                                ck = deleteLopCN(idLopCN, fileName);
                                if (ck) {
                                    JOptionPane.showMessageDialog(null, "Xóa thành công");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Xóa lỗi", "Error delete", JOptionPane.ERROR_MESSAGE);
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
                lopChuyenNganh.loadData(table, quanLy.getDsLopChuyenNganh(), "", "");
            }
        });
        btnTimKiem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String timKiem = timKiemCB.getSelectedItem().toString();
                String giaTri = tfTimKiem.getText().trim().toLowerCase();
                lopChuyenNganh.loadData(table, quanLy.getDsLopChuyenNganh(), timKiem, giaTri);
            }
        });
        btnCapNhatSV.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(null, "Cần chọn lớp học phần để cập nhật dssv", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LopChuyenNganh lopCN = null;
                String idLop = (String) table.getValueAt(row, 0);
                lopCN = quanLy.getLopChuyenNganh(idLop);
                CapNhatSinhVienLCNView capNhatSV = new CapNhatSinhVienLCNView(idLop);
                new CapNhatSinhVienController(capNhatSV, lopCN, null, quanLy, "quanlysinhvien\\danhsachchuyennganh\\lopchuyennganh\\" + idLop + "_dsSV.xlsx");
            }
        });
    }

    private void updateRowTable(LopChuyenNganh lopCN, int row) {
    	table.setValueAt(lopCN.getIdLopChuyenNganh(), row, 0);
    	table.setValueAt(lopCN.getTenLop(), row, 1);
    	table.setValueAt(lopCN.getTenChuNhiem(), row, 2);
    	table.setValueAt(lopCN.getIdKhoa_Vien(), row, 3);
    }
    

    //tạo tiêu đề file dsLopCN
    private void createHeader(Sheet sheet) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        cellStyle.setFont(font);

        Row row = sheet.createRow(0);

        Cell cellHocKy = row.createCell(1);
        cellHocKy.setCellStyle(cellStyle);
        cellHocKy.setCellValue("Mã lớp");

        Cell cellIdLop = row.createCell(2);
        cellIdLop.setCellStyle(cellStyle);
        cellIdLop.setCellValue("Tên lớp");

        Cell cellChuNhiem = row.createCell(3);
        cellChuNhiem.setCellStyle(cellStyle);
        cellChuNhiem.setCellValue("Chủ nhiệm");

        Cell cellLoaiLop = row.createCell(4);
        cellLoaiLop.setCellStyle(cellStyle);
        cellLoaiLop.setCellValue("Mã ngành");
    }

    //ghi dòng dữ liệu lopCN vào row
    private void writeLopCN(LopChuyenNganh lopCN, Row row) {
        Cell cell = row.createCell(1);
        cell.setCellValue(lopCN.getIdLopChuyenNganh());
        cell = row.createCell(2);
        cell.setCellValue(lopCN.getTenLop());
        cell = row.createCell(3);
        cell.setCellValue(lopCN.getTenChuNhiem());
        cell = row.createCell(4);
        cell.setCellValue(lopCN.getIdKhoa_Vien());
    }

    //thêm lớp chuyên ngành vào file dsLopCN
    private void addLopCN(LopChuyenNganh lopCN, String fileName) throws IOException {
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
            writeLopCN(lopCN, row);
        }

        FileOutputStream fout = new FileOutputStream(new File(fileName));
        workbook.write(fout);
        fout.close();
    }

    //cập nhật dữ liệu lopCN trong file
    private boolean updateLopCN(LopChuyenNganh lopCN, String fileName) throws IOException {
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
            String idLopCN = cell.getStringCellValue();
            if (idLopCN.equalsIgnoreCase(lopCN.getIdLopChuyenNganh())) {
                cell = nextRow.createCell(2);
                cell.setCellValue(lopCN.getTenLop());
                cell = nextRow.createCell(3);
                cell.setCellValue(lopCN.getTenChuNhiem());
                cell = nextRow.createCell(4);
                cell.setCellValue(lopCN.getIdKhoa_Vien());
                cell = nextRow.createCell(5);
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

    //xóa lớp chuyên ngành trong file ứng với idLopCN
    private boolean deleteLopCN(String idLopCN, String fileName) throws IOException {
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
            String idLop = cell.getStringCellValue();
            if (idLop.equalsIgnoreCase(idLopCN)) {
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


    //lấy tên ngành ứng với idNganh
    private String getTenNganh(String idNganh) throws IOException {
        String tenN = "";
        FileInputStream fin = new FileInputStream(new File("quanlysinhvien\\danhsachchuyennganh\\dsNganh.xlsx"));
        Workbook workbook = new XSSFWorkbook(fin);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();
        Row nextRow;
        if (iterator.hasNext()) {
            nextRow = iterator.next();
        }
        while (iterator.hasNext()) {
            nextRow = iterator.next();
            Cell cell = nextRow.getCell(1);
            String idN = cell.getStringCellValue();
            if (idN.equals(idNganh)) {
                cell = nextRow.getCell(2);
                tenN = cell.getStringCellValue();
                break;
            }
        }
        workbook.close();
        fin.close();
        return tenN;
    }

    //lấy dữ liệu lớp chuyên ngành từ input
    private LopChuyenNganh getLopChuyenNganh() {
        LopChuyenNganh lopCN;
        String idLopChuyenNganh = tfIdLopChuyenNganh.getText().toUpperCase().trim();
        String tenLop = tfTenLop.getText().trim();
        String tenChuNhiem = tfTenChuNhiem.getText().trim();
        String idNganh = tfIdNganh.getText().toUpperCase().trim();
        String tenNganh = "";
        try {
            tenNganh = getTenNganh(idNganh);
            if (tenNganh.equals("")) {
                JOptionPane.showMessageDialog(null, "Mã ngành không tồn tại", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (idLopChuyenNganh.equals("") || tenLop.equals("") || tenChuNhiem.equals("")
                || idNganh.equals("")) {
            JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        lopCN = new LopChuyenNganh(new ArrayList<SinhVien>(), idLopChuyenNganh, tenLop, tenChuNhiem, idNganh);
        return lopCN;
    }

    //reset input
    private void cancel() {
        table.getSelectionModel().clearSelection();
        tfIdNganh.setText("");
        tfIdLopChuyenNganh.setText("");
        tfIdLopChuyenNganh.setEnabled(true);
        tfTenLop.setText("");
        tfTenChuNhiem.setText("");
        tfTimKiem.setText("");
    }
}

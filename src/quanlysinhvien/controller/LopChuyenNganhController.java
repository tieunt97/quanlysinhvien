package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
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
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import quanlysinhvien.model.Khoa_Vien;

import quanlysinhvien.model.LopChuyenNganh;
import quanlysinhvien.model.SinhVien;
import quanlysinhvien.view.CapNhatSinhVienLCNView;
import quanlysinhvien.view.PanelLopChuyenNganhView;

public class LopChuyenNganhController {

    PanelLopChuyenNganhView lopChuyenNganh;
    private JTable table;
    private JTextField tfIdNganh, tfIdLopChuyenNganh, tfTenLop, tfTenChuNhiem, tfTimKiem;
    private JButton btnThem, btnSua, btnXoa, btnHuy, btnTimKiem, btnCapNhatSV;
    private JComboBox<String> timKiemCB;
    private Khoa_Vien khoa_Vien;
    private String fileName;

    public LopChuyenNganhController(PanelLopChuyenNganhView lopChuyenNganh) {
        this.lopChuyenNganh = lopChuyenNganh;
        ArrayList<LopChuyenNganh> dsLopCN;
        fileName = "quanlysinhvien\\danhsachchuyennganh\\lopchuyennganh\\dsLopCN.xlsx";
        try {
            dsLopCN = readFile(fileName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            dsLopCN = new ArrayList<>();
            System.out.println("Error LopChuyenNganh: " + e);
        }
        khoa_Vien = new Khoa_Vien(dsLopCN);
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
        this.lopChuyenNganh.loadData(table, khoa_Vien.getDsLopChuyenNganh(), "", "");

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
                // TODO Auto-generated method stub
                LopChuyenNganh lopCN = getLopChuyenNganh();
                if (lopCN != null) {
                    if (khoa_Vien.themLopChuyenNganh(lopCN)) {
                    	//thêm dữ liệu lopCN vào bảng
                    	((DefaultTableModel)table.getModel()).addRow(new Object[] {lopCN.getIdLopChuyenNganh(), lopCN.getTenLop(), 
                    			lopCN.getTenChuNhiem(), lopCN.getIdKhoaVien(), lopCN.getTenKhoaVien()});
                        try {
                            addLopCN(lopCN, fileName);  //thêm lopCN vào file
                            JOptionPane.showMessageDialog(null, "Thêm thành công");
                            cancel();
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
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
                // TODO Auto-generated method stub
                int row = table.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(null, "Cần chọn một hàng để sửa", "Error update", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LopChuyenNganh lopCN = getLopChuyenNganh();
                if (lopCN != null) {
                    khoa_Vien.xoaLopChuyenNganh(lopCN.getIdLopChuyenNganh());
                    khoa_Vien.themLopChuyenNganh(lopCN);
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
                    JOptionPane.showMessageDialog(null, "Cần chọn một hàng để xóa", "Error update", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    int select = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa không?", "Notify",
                            JOptionPane.YES_NO_OPTION);
                    if (select == 0) {
                        String idLopCN = (String) table.getValueAt(row, 0);
                        if (khoa_Vien.xoaLopChuyenNganh(idLopCN)) {
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
                lopChuyenNganh.loadData(table, khoa_Vien.getDsLopChuyenNganh(), "", "");
            }
        });
        btnTimKiem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String timKiem = timKiemCB.getSelectedItem().toString();
                String giaTri = tfTimKiem.getText().trim().toLowerCase();
                lopChuyenNganh.loadData(table, khoa_Vien.getDsLopChuyenNganh(), timKiem, giaTri);
            }
        });
        btnCapNhatSV.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                int row = table.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(null, "Cần chọn lớp học phần để cập nhật dssv", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LopChuyenNganh lopCN = null;
                String idLop = (String) table.getValueAt(row, 0);
//                String tenLop = (String) table.getValueAt(row, 1);
                for (int i = 0; i < khoa_Vien.getDsLopChuyenNganh().size(); i++) {
                    if (khoa_Vien.getDsLopChuyenNganh().get(i).getIdLopChuyenNganh().equals(idLop)) {
                    	lopCN = khoa_Vien.getDsLopChuyenNganh().get(i);
//                        try {
//                            dsSinhVien = khoa_Vien.getDsLopChuyenNganh().get(i).getDsSinhVien();
//                            break;
//                        } catch (Exception e1) {
//                            // TODO: handle exception
//                            System.out.println("Error lopCN: " + e1);
//                            dsSinhVien = new ArrayList<>();
//                            break;
//                        }
                    }
                }
                CapNhatSinhVienLCNView capNhatSV = new CapNhatSinhVienLCNView(idLop);
                new CapNhatSinhVienController(capNhatSV, lopCN, null, "quanlysinhvien\\danhsachchuyennganh\\lopchuyennganh\\" + idLop + "_dsSV.xlsx");
            }
        });
    }

    private void updateRowTable(LopChuyenNganh lopCN, int row) {
    	table.setValueAt(lopCN.getIdLopChuyenNganh(), row, 0);
    	table.setValueAt(lopCN.getTenLop(), row, 1);
    	table.setValueAt(lopCN.getTenChuNhiem(), row, 2);
    	table.setValueAt(lopCN.getIdKhoaVien(), row, 3);
    	table.setValueAt(lopCN.getTenKhoaVien(), row, 4);
    }
    
    //lấy danh sách lớp chuyên ngành từ file
    private ArrayList<LopChuyenNganh> readFile(String fileName) throws IOException {
        ArrayList<LopChuyenNganh> dsLopCN = new ArrayList<>();
        FileInputStream inputStream = new FileInputStream(new File(fileName));

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();

        Row nextRow;
        if (iterator.hasNext()) {
            nextRow = iterator.next(); // loại bỏ dòng tiêu đề
        }
        while (iterator.hasNext()) {
            nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            ArrayList<String> dataLopCN = new ArrayList<>();
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
                    default:
                        data = "";
                        break;
                }
                dataLopCN.add(data);
                if (dataLopCN.size() < 1) {
                    return null;
                }
            }
            if (dataLopCN.size() > 0) {
                ArrayList<SinhVien> dsSinhVien;
                try {
                    dsSinhVien = getDSSinhVien(dataLopCN.get(0));
                } catch (Exception exc) {
                    dsSinhVien = new ArrayList<>();
                    System.out.println("Error loadDSSVLopCN: " + exc);
                }
                LopChuyenNganh lopChuyenNganh = new LopChuyenNganh(dsSinhVien, dataLopCN.get(0), dataLopCN.get(1), dataLopCN.get(2), dataLopCN.get(3), dataLopCN.get(4));
                dsLopCN.add(lopChuyenNganh);
            }
        }

        workbook.close();
        inputStream.close();
        return dsLopCN;
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

        Cell cellIdHocPhan = row.createCell(5);
        cellIdHocPhan.setCellStyle(cellStyle);
        cellIdHocPhan.setCellValue("Tên ngành");
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
        cell.setCellValue(lopCN.getIdKhoaVien());
        cell = row.createCell(5);
        cell.setCellValue(lopCN.getTenKhoaVien());
    }

    //thêm lớp chuyên ngành vào file dsLopCN
    private void addLopCN(LopChuyenNganh lopCN, String fileName) throws IOException {
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
            String idLopCN = cell.getStringCellValue();
            if (idLopCN.equalsIgnoreCase(lopCN.getIdLopChuyenNganh())) {
                cell = nextRow.createCell(2);
                cell.setCellValue(lopCN.getTenLop());
                cell = nextRow.createCell(3);
                cell.setCellValue(lopCN.getTenChuNhiem());
                cell = nextRow.createCell(4);
                cell.setCellValue(lopCN.getIdKhoaVien());
                cell = nextRow.createCell(5);
                String tenLop = "";
                try {
                    tenLop = getTenNganh(lopCN.getIdKhoaVien());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                cell.setCellValue(tenLop);
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

    //lấy danh sách sinh viên từ file ứng với idLop
    private ArrayList<SinhVien> getDSSinhVien(String idLop) throws IOException {
        ArrayList<SinhVien> dsSV = new ArrayList<>();
        String fileName = "quanlysinhvien\\danhsachchuyennganh\\lopchuyennganh\\" + idLop + "_dsSV.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(fileName));

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();

        Row nextRow;
        if (iterator.hasNext()) {
            nextRow = iterator.next(); // loại bỏ dòng tiêu đề
        }
        while (iterator.hasNext()) {
            nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            ArrayList<String> dataSV = new ArrayList<>();
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
                    default:
                        data = "";
                        break;
                }
                dataSV.add(data);
                if (dataSV.size() < 1) {
                    return null;
                }
            }
            if (dataSV.size() > 0) {
                SinhVien sv = new SinhVien(dataSV.get(0), dataSV.get(1), dataSV.get(2), dataSV.get(3), dataSV.get(4), dataSV.get(5), dataSV.get(6), dataSV.get(7), dataSV.get(8), Double.parseDouble(dataSV.get(9)));
                dsSV.add(sv);
            }
        }

        workbook.close();
        inputStream.close();
        return dsSV;
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
        lopCN = new LopChuyenNganh(new ArrayList<SinhVien>(), idLopChuyenNganh, tenLop, tenChuNhiem, idNganh, tenNganh);
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

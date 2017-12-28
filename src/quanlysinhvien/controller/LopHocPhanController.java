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

import quanlysinhvien.model.HocPhan;
import quanlysinhvien.model.LopHocPhan;
import quanlysinhvien.model.QuanLy;
import quanlysinhvien.model.SinhVien;
import quanlysinhvien.view.CapNhatSinhVienLCNView;
import quanlysinhvien.view.PanelLopHocPhanView;

public class LopHocPhanController {

    private JTable table;
    private JTextField tfIdLop, tfLoaiLop, tfIdHocPhan, tfThoiGian, tfTuanHoc, tfPhongHoc, tfTenGV, tfSoSVMax, tfSoSVHienTai, tfTimKiem;
    private JButton btnThem, btnSua, btnXoa, btnHuy, btnTimKiem, btnCapNhatSV;
    private JComboBox<String> timKiemCB, hocKyCB;
    private PanelLopHocPhanView lopHocPhan;
    private String fileName;
    private QuanLy quanLy;

    public LopHocPhanController(PanelLopHocPhanView lopHocPhan, QuanLy quanLy) {
        this.lopHocPhan = lopHocPhan;
        this.quanLy = quanLy;
        ArrayList<LopHocPhan> dsLopHP;
        fileName = "quanlysinhvien\\danhsachhocphan\\lophocphan\\dsLopHP.xlsx";
        this.table = lopHocPhan.getTable();
        this.btnThem = lopHocPhan.getBtnThem();
        this.btnSua = lopHocPhan.getBtnSua();
        this.btnXoa = lopHocPhan.getBtnXoa();
        this.btnHuy = lopHocPhan.getBtnHuy();
        this.btnTimKiem = lopHocPhan.getBtnTimKiem();
        this.btnCapNhatSV = lopHocPhan.getBtnCapNhatSV();
        this.tfIdLop = lopHocPhan.getTfIdLop();
        this.tfLoaiLop = lopHocPhan.getTfLoaiLop();
        this.tfIdHocPhan = lopHocPhan.getTfIdHocPhan();
        this.tfThoiGian = lopHocPhan.getTfThoiGian();
        this.tfTuanHoc = lopHocPhan.getTfTuanHoc();
        this.tfPhongHoc = lopHocPhan.getTfPhongHoc();
        this.tfTenGV = lopHocPhan.getTfTenGV();
        this.tfSoSVMax = lopHocPhan.getTfSoSVMax();
        this.tfSoSVHienTai = lopHocPhan.getTfSoSVHienTai();
        this.tfTimKiem = lopHocPhan.getTfTimKiem();
        this.timKiemCB = lopHocPhan.getTimKiemCB();
        this.hocKyCB = lopHocPhan.getHocKyCB();
        this.lopHocPhan.loadData(table, quanLy.getDsLopHocPhan(), "", "");

        setAction();
    }

    //bắt sự kiện
    private void setAction() {
        table.addMouseListener(new MouseAdapter() {
        	 @Override
             public void mousePressed(MouseEvent arg0) {
                 // TODO Auto-generated method stub
                 int row = table.getSelectedRow();
                 if (row >= 0) {
                     hocKyCB.setSelectedItem(table.getValueAt(row, 0));
                     tfIdLop.setText((String) table.getValueAt(row, 1));
                     tfIdLop.setEnabled(false);
                     tfLoaiLop.setText((String) table.getValueAt(row, 2));
                     tfIdHocPhan.setText((String) table.getValueAt(row, 3));
                     tfThoiGian.setText((String) table.getValueAt(row, 5));
                     tfTuanHoc.setText((String) table.getValueAt(row, 6));
                     tfPhongHoc.setText((String) table.getValueAt(row, 7));
                     tfTenGV.setText((String) table.getValueAt(row, 8));
                     tfSoSVMax.setText((String) table.getValueAt(row, 9));
                     tfSoSVHienTai.setText((String) table.getValueAt(row, 10));
                 }
             }
		});
        btnThem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                LopHocPhan lopHP = getLopHocPhan();
                if (lopHP != null) {
                    if (quanLy.themLopHocPhan(lopHP)) {
                    	//thêm lopHP vào bảng
                    	((DefaultTableModel)table.getModel()).addRow(new Object[] {lopHP.getHocKy(), lopHP.getIdLop(), lopHP.getLoaiLop(),
                    			lopHP.getHocPhan().getIdHocPhan(), lopHP.getHocPhan().getTenHP(), lopHP.getThoiGian(), lopHP.getTuanHoc(), lopHP.getPhongHoc(), 
                    			lopHP.getTenGiangVien(), lopHP.getSoSVMax() + "", lopHP.getSoSVHienTai() + ""});
                        try {
                            addLopHP(lopHP, fileName);
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
                LopHocPhan lopHP = getLopHocPhan();
                if (lopHP != null) {
                	LopHocPhan lopHocPhan = quanLy.getLopHocPhan(lopHP.getIdLop());
                	lopHocPhan.setHocKy(lopHP.getHocKy());
                	lopHocPhan.setHocPhan(lopHP.getHocPhan());
                	lopHocPhan.setLoaiLop(lopHP.getLoaiLop());
                	lopHocPhan.setPhongHoc(lopHP.getPhongHoc());
                	lopHocPhan.setThoiGian(lopHP.getThoiGian());
                	lopHocPhan.setTuanHoc(lopHP.getTuanHoc());
                	lopHocPhan.setTenGiangVien(lopHP.getTenGiangVien());
                	lopHocPhan.setSoSVMax(lopHP.getSoSVMax());
                	lopHocPhan.setSoSVHienTai(lopHP.getSoSVHienTai());
                    updateRowTable(lopHP, row);		//cập nhật lopHP trên bảng
                    boolean ck = false;
                    try {
                        ck = updateLopHP(lopHP, fileName);
                        if (ck) {
                            JOptionPane.showMessageDialog(null, "Cập nhật thành công");
                        } else {
                            JOptionPane.showMessageDialog(null, "Lỗi cập nhật", "Error update", JOptionPane.ERROR_MESSAGE);
                        }
                        cancel();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        System.out.println("Error update: " + e1);
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
                        String idLop = (String) table.getValueAt(row, 1);
                        boolean ck = false;
                        if (quanLy.xoaLopHocPhan(idLop)) {
                            ((DefaultTableModel)table.getModel()).removeRow(row);	//xóa lopHP trên bảng
                            try {
                                ck = deleteLopHP(idLop, fileName);
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
                lopHocPhan.loadData(table, quanLy.getDsLopHocPhan(), "", "");
            }
        });
        btnTimKiem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String timKiem = timKiemCB.getSelectedItem().toString();
                String giaTri = tfTimKiem.getText().trim().toLowerCase();
                lopHocPhan.loadData(table, quanLy.getDsLopHocPhan(), timKiem, giaTri);
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
                String idLop = (String) table.getValueAt(row, 1);
                CapNhatSinhVienLCNView capNhatSV = new CapNhatSinhVienLCNView(idLop);
                LopHocPhan lopHP = quanLy.getLopHocPhan(idLop);
                new CapNhatSinhVienController(capNhatSV, null, lopHP, quanLy, "quanlysinhvien\\danhsachhocphan\\lophocphan\\" + idLop + "_dsSV.xlsx");
            }
        });

    }

    //cập nhật dữ liệu lopHP hàng row
    private void updateRowTable(LopHocPhan lopHP, int row) {
    	table.setValueAt(lopHP.getHocKy(), row, 0);
    	table.setValueAt(lopHP.getIdLop(), row, 1);
    	table.setValueAt(lopHP.getLoaiLop(), row, 2);
    	table.setValueAt(lopHP.getHocPhan().getIdHocPhan(), row, 3);
    	table.setValueAt(lopHP.getHocPhan().getTenHP(), row, 4);
    	table.setValueAt(lopHP.getThoiGian(), row, 5);
    	table.setValueAt(lopHP.getTuanHoc(), row, 6);
    	table.setValueAt(lopHP.getPhongHoc(), row, 7);
    	table.setValueAt(lopHP.getTenGiangVien(), row, 8);
    	table.setValueAt(lopHP.getSoSVMax() + "", row, 9);
    	table.setValueAt(lopHP.getSoSVHienTai() + "", row, 10);
    }
    
    //tạo tiêu đề file dsLopHP
    private void createHeader(Sheet sheet) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        cellStyle.setFont(font);

        Row row = sheet.createRow(0);

        Cell cellHocKy = row.createCell(1);
        cellHocKy.setCellStyle(cellStyle);
        cellHocKy.setCellValue("Học kỳ");

        Cell cellIdLop = row.createCell(2);
        cellIdLop.setCellStyle(cellStyle);
        cellIdLop.setCellValue("Mã lớp");

        Cell cellLoaiLop = row.createCell(3);
        cellLoaiLop.setCellStyle(cellStyle);
        cellLoaiLop.setCellValue("Loại lớp");

        Cell cellIdHocPhan = row.createCell(4);
        cellIdHocPhan.setCellStyle(cellStyle);
        cellIdHocPhan.setCellValue("Mã học phần");

        Cell cellTenLop = row.createCell(5);
        cellTenLop.setCellStyle(cellStyle);
        cellTenLop.setCellValue("Tên lớp");

        Cell cellThoiGian = row.createCell(6);
        cellThoiGian.setCellStyle(cellStyle);
        cellThoiGian.setCellValue("Thời gian");

        Cell cellTuanHoc = row.createCell(7);
        cellTuanHoc.setCellStyle(cellStyle);
        cellTuanHoc.setCellValue("Tuần học");

        Cell cellPhongHoc = row.createCell(8);
        cellPhongHoc.setCellStyle(cellStyle);
        cellPhongHoc.setCellValue("Phòng học");

        Cell cellTenGV = row.createCell(9);
        cellTenGV.setCellStyle(cellStyle);
        cellTenGV.setCellValue("Tên giảng viên");

        Cell cellSoSVMax = row.createCell(10);
        cellSoSVMax.setCellStyle(cellStyle);
        cellSoSVMax.setCellValue("Số SV max");

        Cell cellSoSVHT = row.createCell(11);
        cellSoSVHT.setCellStyle(cellStyle);
        cellSoSVHT.setCellValue("Số SV hiện tại");
    }

    //ghi dòng dữ liệu lopHP vào row
    private void writeLopHP(LopHocPhan lopHP, Row row) {
        Cell cell = row.createCell(1);
        cell.setCellValue(lopHP.getHocKy());
        cell = row.createCell(2);
        cell.setCellValue(lopHP.getIdLop());
        cell = row.createCell(3);
        cell.setCellValue(lopHP.getLoaiLop());
        cell = row.createCell(4);
        cell.setCellValue(lopHP.getHocPhan().getIdHocPhan());
        cell = row.createCell(5);
        cell.setCellValue(lopHP.getHocPhan().getTenHP());
        cell = row.createCell(6);
        cell.setCellValue(lopHP.getThoiGian());
        cell = row.createCell(7);
        cell.setCellValue(lopHP.getTuanHoc());
        cell = row.createCell(8);
        cell.setCellValue(lopHP.getPhongHoc());
        cell = row.createCell(9);
        cell.setCellValue(lopHP.getTenGiangVien());
        cell = row.createCell(10);
        cell.setCellValue(lopHP.getSoSVMax());
        cell = row.createCell(11);
        cell.setCellValue(lopHP.getSoSVHienTai());
    }

    //thêm lopHP vào file
    private void addLopHP(LopHocPhan lopHP, String fileName) throws IOException {
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
            writeLopHP(lopHP, row);
        }

        FileOutputStream fout = new FileOutputStream(new File(fileName));
        workbook.write(fout);
        fout.close();
    }

    //cập nhật dữ liệu lopHP trong file
    private boolean updateLopHP(LopHocPhan lopHP, String fileName) throws IOException {
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
            Cell cell = nextRow.getCell(2);
            String idLopHP = cell.getStringCellValue();
            if (idLopHP.equalsIgnoreCase(lopHP.getIdLop())) {
                cell = nextRow.createCell(1);
                cell.setCellValue(lopHP.getHocKy());
                cell = nextRow.createCell(3);
                cell.setCellValue(lopHP.getLoaiLop());
                cell = nextRow.createCell(4);
                cell.setCellValue(lopHP.getHocPhan().getIdHocPhan());
                cell = nextRow.createCell(5);
                cell.setCellValue(lopHP.getHocPhan().getTenHP());
                cell = nextRow.createCell(6);
                cell.setCellValue(lopHP.getThoiGian());
                cell = nextRow.createCell(7);
                cell.setCellValue(lopHP.getTuanHoc());
                cell = nextRow.createCell(8);
                cell.setCellValue(lopHP.getPhongHoc());
                cell = nextRow.createCell(9);
                cell.setCellValue(lopHP.getTenGiangVien());
                cell = nextRow.createCell(10);
                cell.setCellValue(lopHP.getSoSVMax());
                cell = nextRow.createCell(11);
                cell.setCellValue(lopHP.getSoSVHienTai());
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

    //xóa lopHP trong file
    private boolean deleteLopHP(String idLopHP, String fileName) throws IOException {
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
            Cell cell = nextRow.getCell(2);
            String idLop = cell.getStringCellValue();
            if (idLop.equalsIgnoreCase(idLopHP)) {
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

    //lấy dữ liệu lớp học phần từu input
    private LopHocPhan getLopHocPhan() {
        LopHocPhan lopHP;
        String hocKy = (String) hocKyCB.getSelectedItem();
        String idLop = tfIdLop.getText().toUpperCase().trim();
        String loaiLop = tfLoaiLop.getText().trim();
        String idHocPhan = tfIdHocPhan.getText().trim().toUpperCase();
        HocPhan hocPhan = this.quanLy.getHocPhan(idHocPhan);
        String thoiGian = tfThoiGian.getText().trim();
        String tuanHoc = tfTuanHoc.getText().trim();
        String phongHoc = tfPhongHoc.getText().trim();
        String tenGiangVien = tfTenGV.getText().trim();
        if (hocKy.equals("") || idLop.equals("") || loaiLop.equals("") || idHocPhan.equals("")
                || tenGiangVien.equals("") || thoiGian.equals("") || tuanHoc.equals("") || phongHoc.equals("")) {
            JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        int soSVMax, soSVHienTai;
        try {
            soSVMax = Integer.parseInt(tfSoSVMax.getText().trim());
            soSVHienTai = Integer.parseInt(tfSoSVHienTai.getText().trim());
        } catch (NumberFormatException exc) {
            JOptionPane.showMessageDialog(null, "Hãy kiểm tra các giá trị: Số SV max, Số SV hiện tại", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        lopHP = new LopHocPhan(hocKy, idLop, loaiLop, hocPhan, thoiGian, tuanHoc, phongHoc, new ArrayList<SinhVien>(), tenGiangVien, soSVMax, soSVHienTai);
        return lopHP;
    }

    //reset input
    private void cancel() {
        table.getSelectionModel().clearSelection();
        tfIdLop.setText("");
        tfIdLop.setEnabled(true);
        tfLoaiLop.setText("");
        tfIdHocPhan.setText("");
        tfThoiGian.setText("");
        tfTuanHoc.setText("");
        tfPhongHoc.setText("");
        tfTenGV.setText("");
        tfSoSVMax.setText("");
        tfSoSVHienTai.setText("");
        tfTimKiem.setText("");
    }
}

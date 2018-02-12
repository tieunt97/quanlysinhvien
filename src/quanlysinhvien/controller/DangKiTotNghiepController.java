package quanlysinhvien.controller;

import java.awt.CardLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.SinhVien;
import quanlysinhvien.model.SinhVienNienChe;
import quanlysinhvien.model.SinhVienTinChi;
import quanlysinhvien.view.PanelDangKiTotNghiepView;

public class DangKiTotNghiepController {
	private JLabel labDiemTB, labTongSoTC, labSoTCNo, labTongSoKy, labDiemTB1, labHoTen, labHoTen1, labSoMonNo;
	private JButton btnDangKy;
	private JPanel panel;
	private CardLayout cardLayout;
	private SinhVien sv;
	private Workbook workbook;
	
	public DangKiTotNghiepController(PanelDangKiTotNghiepView dkTN, SinhVien sv) {
		this.panel = dkTN.getPanel();
		this.sv = sv;
		this.cardLayout = (CardLayout) panel.getLayout();
		this.labDiemTB = dkTN.getLabDiemTB();
		this.labTongSoTC = dkTN.getLabTongSoTC();
		this.labSoTCNo = dkTN.getLabSoTCNo();
		this.labTongSoKy = dkTN.getLabTongSoKy();
		this.labDiemTB1 = dkTN.getLabDiemTB1();
		this.labHoTen = dkTN.getLabHoTen();
		this.labHoTen1 = dkTN.getLabHoTen1();
		this.labSoMonNo = dkTN.getLabSoMonNo();
		this.btnDangKy = dkTN.getBtnDangKy();

		setThongTin();
		addEvents();
	}

	private void addEvents() {
		btnDangKy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				XetTotNghiep(sv);
			}
		});
	}

	private void setThongTin() {
		if (sv instanceof SinhVienTinChi) {
			SinhVienTinChi svtc = (SinhVienTinChi) sv;
			labHoTen.setText(svtc.getHoTen());
			labTongSoTC.setText(Integer.toString(svtc.getSoTCQua()));
			labSoTCNo.setText(Integer.toString(svtc.getSoTCNo()));
			labDiemTB.setText(Double.toString(svtc.getDiemTB()));
			cardLayout.show(panel, "svtc");
		} else {
			SinhVienNienChe svnc = (SinhVienNienChe) sv;
			labHoTen1.setText(svnc.getHoTen());
			labTongSoKy.setText(Integer.toString(svnc.getTongSoKy()));
			labDiemTB1.setText(Double.toString(svnc.getDiemTB()));
			labSoMonNo.setText(Integer.toString(svnc.getDsHocPhanNo().size()));
			cardLayout.show(panel, "svnc");
		}
	}
	
	private boolean XetTotNghiep(SinhVien sv) {
		/* Xét điều kiện tốt nghiệp */
		if(sv instanceof SinhVienTinChi) {
			SinhVienTinChi svtc = (SinhVienTinChi) sv;
			try {
				if(checkNoTheChat(svtc)) {
					JOptionPane.showMessageDialog(null, "Sinh viên chưa hoàn thành các môn học thể chất");
					return false;
				}
				if (svtc.getSoTCQua() >= 165 && svtc.getSoTCNo() == 0 && sv.getDiemTB() >= 2.0) {
					if (sv.getDiemTB() >= 3.8) {
						JOptionPane.showMessageDialog(null,
								"Bạn đã đăng kí tốt nghiệp thành công\n." + "Xếp loại XUẤT SẮC");
					} else if (svtc.getDiemTB() >= 3.5) {
						JOptionPane.showMessageDialog(null,
								"Bạn đã đăng kí tốt nghiệp thành công\n." + "Xếp loại GIỎI");
					} else if (svtc.getDiemTB() >= 2.5) {
						JOptionPane.showMessageDialog(null,
								"Bạn đã đăng kí tốt nghiệp thành công\n." + "Xếp loại KHÁ");
					} else if (svtc.getDiemTB() >= 2.0) {
						JOptionPane.showMessageDialog(null,
								"Bạn đã đăng kí tốt nghiệp thành công\n." + "Xếp loại TRUNG BÌNH");
					}
					
					return true;
				} else {
					JOptionPane.showMessageDialog(null,
							"Số tín chỉ tích lũy = " + svtc.getSoTCQua() + "\nTín chỉ nợ = " + svtc.getSoTCNo() + "\nCPA = " + svtc.getDiemTB()
							+ "\nBạn không đủ điều kiện tốt nghiệp\n"
							+ "Điều kiện tốt nghiệp: TC tích lũy >= 165, TC nợ = 0 , CPA >= 2.0");
					return false;
				}
			} catch (HeadlessException | IOException e) {
				e.printStackTrace();
				return false;
			}
		}else if(sv instanceof SinhVienNienChe){
			SinhVienNienChe svnc = (SinhVienNienChe) sv;
			if(svnc.getTongSoKy() == 8 && svnc.getDsHocPhanNo().size() == 0 && svnc.getDiemTB() >=2.0) {
				if (svnc.getDiemTB() >= 3.8) {
					JOptionPane.showMessageDialog(null,
							"Bạn đã đăng kí tốt nghiệp thành công\n." + "Xếp loại XUẤT SẮC");
				} else if (svnc.getDiemTB() >= 3.5) {
					JOptionPane.showMessageDialog(null,
							"Bạn đã đăng kí tốt nghiệp thành công\n." + "Xếp loại GIỎI");
				} else if (svnc.getDiemTB() >= 2.5) {
					JOptionPane.showMessageDialog(null,
							"Bạn đã đăng kí tốt nghiệp thành công\n." + "Xếp loại KHÁ");
				} else if (svnc.getDiemTB() >= 2.0) {
					JOptionPane.showMessageDialog(null,
							"Bạn đã đăng kí tốt nghiệp thành công\n." + "Xếp loại TRUNG BÌNH");
				}
				
				return true;
			}else {
				JOptionPane.showMessageDialog(null,
						"Tổng số kỳ = " + svnc.getTongSoKy() + "\nSố môn nợ = " + svnc.getDsHocPhanNo().size() + "\nCPA = " + svnc.getDiemTB()
						+ "\nBạn không đủ điều kiện tốt nghiệp\n"
						+ "Điều kiện tốt nghiệp: Số kỳ học = 8, Số môn nợ = 0 , CPA >= 2.0");
				return false;
			}
		}
		
		return false;
	}
	
	
	//kiểm tra sinh viên tín chỉ đã qua hết các môn thể chất chưa?
	private boolean checkNoTheChat(SinhVienTinChi svtc) throws IOException {
		boolean ck = false;
		FileInputStream fin = new FileInputStream(new File("quanlysinhvien\\sinhvientinchi\\" + svtc.getIdSinhVien() + "\\diem.xlsx"));
		workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		Row nextRow;
		Iterator<Row> iterator = sheet.iterator();
		if(iterator.hasNext()) nextRow = iterator.next();
		while(iterator.hasNext()) {
			nextRow = iterator.next();
			Cell cell = nextRow.getCell(2);
			if(cell.getStringCellValue().indexOf("PE") == 0) {
				cell = nextRow.getCell(9);
				if((double) cell.getNumericCellValue() == 0.0) {
					ck = true;
					break;
				}
			}
		}
		
		return ck;
	}
}

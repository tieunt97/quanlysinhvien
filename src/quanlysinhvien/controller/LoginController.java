package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.main.MainQuanLy;
import quanlysinhvien.main.MainSinhVienNC;
import quanlysinhvien.main.MainSinhVienTC;
import quanlysinhvien.model.ChuongTrinhDaoTao;
import quanlysinhvien.model.DangKyHocPhan;
import quanlysinhvien.model.DiemHocPhan;
import quanlysinhvien.model.HocPhan;
import quanlysinhvien.model.Khoa_Vien;
import quanlysinhvien.model.LopChuyenNganh;
import quanlysinhvien.model.LopHocPhan;
import quanlysinhvien.model.QuanLy;
import quanlysinhvien.model.SinhVien;
import quanlysinhvien.model.SinhVienNienChe;
import quanlysinhvien.model.SinhVienTinChi;
import quanlysinhvien.model.TaiKhoan;
import quanlysinhvien.view.LoginView;

public class LoginController {
	private LoginView loginView;
	private JTextField tfTaiKhoan;
	private JPasswordField pwMatKhau;
	private MainQuanLy mainQL;
	private MainSinhVienTC mainSVTC;
	private MainSinhVienNC mainSVNC;
	private JButton btnDangNhap, btnHuy, btnThoat, btnDangXuat;
	private QuanLy quanLy;
	private ArrayList<TaiKhoan> dsTaiKhoan;
	public LoginController(LoginView login) {
		loginView = login;
		btnDangNhap = login.getBtnDangNhap();
		btnHuy = login.getBtnHuy();
		btnThoat = login.getBtnThoat();
		tfTaiKhoan = login.getTfTaiKhoan();
		pwMatKhau = login.getPwMatKhau();
		try {
			dsTaiKhoan = getAllTaiKhoan();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		quanLy = new QuanLy();
		//lấy danh sách học phần
		try {
			getAllDSHocPhan(quanLy.getDsHocPhan());
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Danh sách học phần trống: " + e);
		}
		//lấy danh sách lớp học phần
//		
		//lấy danh sách sinh viên
		try {
			getDSSVTinChi(quanLy.getDsSinhVien());
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Danh sách sinh viên TC trống: " + e);
		}
		try {
			getDSSVNienChe(quanLy.getDsSinhVien());
		}catch (IOException e) {
			// TODO: handle exception
			System.out.println("Danh sách sinh viên NC trống: " + e);
		}
		//lấy danh sách lớp chuyên ngành
		try {
			getAllDSLopCN(quanLy);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			getAllKhoa_Vien(quanLy);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			getAllDSLopHP(quanLy);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		
		setActions();
	}
	
	private void setActions() {
		btnDangNhap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					Login();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnHuy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cancel();
			}
		});
		
		btnThoat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		pwMatKhau.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						Login();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	}
	
	private void Login() throws IOException {
		String taiKhoan = tfTaiKhoan.getText().trim();
		String matKhau = pwMatKhau.getText().trim();
		if(taiKhoan.equals("") || matKhau.equals("")) {
			JOptionPane.showMessageDialog(null, "Nhập tài khoản và mật khẩu để đăng nhập", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		TaiKhoan tk = getTaiKhoan(taiKhoan.toUpperCase());
		if(tk != null && tk.getMatKhau().equalsIgnoreCase(matKhau)) {
			cancel();
			if(tk.getLoaiTK().equals("admin")) {
				mainQL = new MainQuanLy(this.quanLy);
				btnDangXuat = mainQL.getBtnLogout();
			}else if(tk.getLoaiTK().equals("svtc")) {
				mainSVTC = new MainSinhVienTC((SinhVienTinChi) quanLy.getSinhVien(tk.getTaiKhoan()), quanLy);
				btnDangXuat = mainSVTC.getBtnLogout();
			}else if(tk.getLoaiTK().equals("svnc")) {
				mainSVNC = new MainSinhVienNC((SinhVienNienChe) quanLy.getSinhVien(tk.getTaiKhoan()), quanLy);
				btnDangXuat = mainSVNC.getBtnLogout();
			}
			btnDangXuat.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(mainQL != null) {
						mainQL.dispose();
					}
					if(mainSVTC != null) {
						mainSVTC.dispose();
					}
					if(mainSVNC != null) {
						mainSVNC.dispose();
					}
					loginView.setVisible(true);
					return;
				}
			});
			loginView.setVisible(false);
		}else {
			JOptionPane.showMessageDialog(null, "Tên đăng nhập hoặc mật khẩu sai", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void cancel() {
		tfTaiKhoan.setText("");
		pwMatKhau.setText("");
	}
	
	private void getDSSVTinChi(ArrayList<SinhVien> dsSinhVien) throws IOException{
		FileInputStream inputStream = new FileInputStream(new File("quanlysinhvien/sinhvientinchi/dsSinhVienTC.xlsx"));

		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();

		Row nextRow;
		if (iterator.hasNext())
			nextRow = iterator.next(); // loại bỏ dòng tiêu đề
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
			}
			if (dataSV.size() > 0) {
				TaiKhoan tk = getTaiKhoan(dataSV.get(0));
				SinhVienTinChi sv = new SinhVienTinChi(dataSV.get(0), dataSV.get(1),Integer.toString((int) Double.parseDouble(dataSV.get(2))), 
						dataSV.get(3), dataSV.get(4),dataSV.get(5), dataSV.get(6), dataSV.get(7), dataSV.get(8), Double.parseDouble(dataSV.get(9)), tk,
						(int) (Double.parseDouble(dataSV.get(10))), (int) (Double.parseDouble(dataSV.get(11))), (int) Double.parseDouble(dataSV.get(12)));
				//lấy danh sách điểm của sinh viên
				try {
					getDSDiemHP(sv);
				} catch (IOException e) {
					System.out.println("Danh sách điểm " + sv.getIdSinhVien() + " trống: " + e);
				}
				//lấy danh sách học phần đăng ký của sinh viên
				try {
					getDSHocPhanDangKy(sv);
				} catch (IOException e) {
					System.out.println("Danh sách học phần đăng ký " + sv.getIdSinhVien() + " trống: " + e);
				}
				//lấy danh sách lớp học đăng ký của sinh viên
				try {
					getDSLopHPDangKy(sv);
				} catch (IOException e) {
					System.out.println("Danh sách lớp học đăng ký " + sv.getIdSinhVien() + " trống: " + e);
				}
				//lấy chương trình đào tạo của sinh viên
				try {
					getChuongTrinhDT(sv);
				}catch(IOException e) {
					System.out.println("Chương trình đào tạo " + sv.getIdSinhVien() + " trống: " + e);
				}
				System.out.println();
				
				dsSinhVien.add(sv);
			}
		}

		workbook.close();
		inputStream.close();
	}
	
	private void getChuongTrinhDT(SinhVien sv) throws IOException {
		String fileName = (sv instanceof SinhVienTinChi)?"quanlysinhvien/sinhvientinchi/" + sv.getIdSinhVien() + "/ctdt.xlsx":
			"quanlysinhvien/sinhviennienche/" + sv.getIdSinhVien() + "/ctdt.xlsx";
		FileInputStream fin = new FileInputStream(new File(fileName));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		Row nextRow;
		if(iterator.hasNext()) nextRow = iterator.next();
		while(iterator.hasNext()) {
			nextRow = iterator.next();
			ChuongTrinhDaoTao ctdt;
			Cell cell = nextRow.getCell(1);
			String idHP = cell.getStringCellValue();
			cell = nextRow.getCell(2);
			int kyHoc = (int) cell.getNumericCellValue();
			if(sv instanceof SinhVienTinChi) {
				cell = nextRow.getCell(3);
				String diemChu = cell.getStringCellValue();
				cell = nextRow.getCell(4);
				double diemSo = cell.getNumericCellValue();
				ctdt = new ChuongTrinhDaoTao(quanLy.getHocPhan(idHP), kyHoc, diemSo, diemChu);
				
			}else {
				cell = nextRow.getCell(3);
				double diemSo = cell.getNumericCellValue();
				ctdt = new ChuongTrinhDaoTao(quanLy.getHocPhan(idHP), kyHoc, diemSo);
			}
			sv.getCtdt().add(ctdt);
		}
		
		workbook.close();
		fin.close();
	}
	
	private ArrayList<TaiKhoan> getAllTaiKhoan() throws IOException {
		ArrayList<TaiKhoan> dsTaiKhoan = new ArrayList<>();
		FileInputStream inputStream = new FileInputStream(new File("quanlysinhvien//dsTaiKhoan.xlsx"));
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();

		Row nextRow;
		if (iterator.hasNext())
			nextRow = iterator.next(); // loại bỏ dòng tiêu đề
		while (iterator.hasNext()) {
			nextRow = iterator.next();
			Cell cell = nextRow.getCell(1);
			String taiKhoan = cell.getStringCellValue();
			cell = nextRow.getCell(2);
			String matKhau = cell.getStringCellValue();
			cell = nextRow.getCell(3);
			String loaiTK = cell.getStringCellValue();
			
			TaiKhoan tk = new TaiKhoan(taiKhoan, matKhau, loaiTK);
			dsTaiKhoan.add(tk);
		}

		workbook.close();
		inputStream.close();
		return dsTaiKhoan;
	}
	
	private TaiKhoan getTaiKhoan(String idSV) {
		for(TaiKhoan tk: dsTaiKhoan) {
			if(tk.getTaiKhoan().equals(idSV))
				return tk;
		}
		
		return null;
	}
	
	private void getDSSVNienChe(ArrayList<SinhVien> dsSinhVien) throws IOException{
		FileInputStream inputStream = new FileInputStream(new File("quanlysinhvien/sinhviennienche/dsSinhVienNC.xlsx"));
		
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
		
		Row nextRow;
		if (iterator.hasNext())
			nextRow = iterator.next(); // loại bỏ dòng tiêu đề
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
			}
			if (dataSV.size() > 0) {
				TaiKhoan tk = getTaiKhoan(dataSV.get(0));
				SinhVienNienChe sv = new SinhVienNienChe(dataSV.get(0), dataSV.get(1),Integer.toString((int) Double.parseDouble(dataSV.get(2))), 
						dataSV.get(3), dataSV.get(4),dataSV.get(5), dataSV.get(6), dataSV.get(7), dataSV.get(8), Double.parseDouble(dataSV.get(9)), tk,
						(int) (Double.parseDouble(dataSV.get(10))));
				//lấy danh sách điểm của sinh viên
				try {
					getDSDiemHP(sv);
				} catch (IOException e) {
					System.out.println("Danh sách điểm " + sv.getIdSinhVien() + " trống: " + e);
				}
				//lấy danh sách học phần đăng ký của sinh viên
				try {
					getDSHocPhanDangKy(sv);
				} catch (IOException e) {
					System.out.println("Danh sách học phần đăng ký " + sv.getIdSinhVien() + " trống: " + e);
				}
				//lấy danh sách lớp học đăng ký của sinh viên
				try {
					getDSLopHPDangKy(sv);
				} catch (IOException e) {
					System.out.println("Danh sách lớp học đăng ký " + sv.getIdSinhVien() + " trống: " + e);
				}
				//lấy danh sách học phần nợ của sinh viên
				try {
					getDSHocPhanNo(sv);
				} catch (IOException e) {
					System.out.println("Danh sách học phần nợ trống " + sv.getIdSinhVien() + " trống: " + e);
				}
				//lấy chương trình đào tạo của sinh viên
				try {
					getChuongTrinhDT(sv);
				}catch(IOException e) {
					System.out.println("Chương trình đào tạo " + sv.getIdSinhVien() + " trống: " + e);
				}
				System.out.println();
				
				dsSinhVien.add(sv);
			}
		}
		
		workbook.close();
		inputStream.close();
	}
	
	private void getDSHocPhanNo(SinhVienNienChe sv) throws IOException {
		String fileName = "quanlysinhvien/sinhviennienche/" + sv.getIdSinhVien() + "/hocPhanNo.xlsx";
		FileInputStream fin = new FileInputStream(new File(fileName));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		Row nextRow;
		Iterator<Row> iterator = sheet.iterator();
		if(iterator.hasNext())
			nextRow = iterator.next();
		while(iterator.hasNext()) {
			nextRow = iterator.next();
			Cell cellIdHP = nextRow.getCell(1);
			sv.getDsHocPhanNo().add(this.quanLy.getHocPhan(cellIdHP.getStringCellValue()));
		}
		
		workbook.close();
		fin.close();
	}
	
	private void getDSLopHPDangKy(SinhVien sv) throws IOException {
		String fileName = (sv instanceof SinhVienTinChi)?"quanlysinhvien/sinhvientinchi/" + sv.getIdSinhVien() + "/dsLopHPDangKy.xlsx"
				:"quanlysinhvien/sinhviennienche/" + sv.getIdSinhVien() + "/dsLopHPDangKy.xlsx";
		FileInputStream fin = new FileInputStream(new File(fileName));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		Row nextRow;
		if(iterator.hasNext())
			nextRow = iterator.next();
		while(iterator.hasNext()) {
			nextRow = iterator.next();
			Cell cellIdLop = nextRow.getCell(1);
			sv.getDsLopHPDangKy().add(quanLy.getLopHocPhan(cellIdLop.getStringCellValue()));
		}
		
		workbook.close();
		fin.close();
	}
	
	private void getDSHocPhanDangKy(SinhVien sv) throws IOException {
		String fileName = (sv instanceof SinhVienTinChi)?"quanlysinhvien/sinhvientinchi/" + sv.getIdSinhVien() + "/dsHPDangKy.xlsx"
				:"quanlysinhvien/sinhviennienche/" + sv.getIdSinhVien() + "/dsHPDangKy.xlsx";
		FileInputStream fin = new FileInputStream(new File(fileName));
		Workbook workbook = new XSSFWorkbook(fin);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		Row nextRow;
		if(iterator.hasNext())
			nextRow = iterator.next();
		while(iterator.hasNext()) {
			nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			ArrayList<String> dataHPDangKy = new ArrayList<>();
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
				dataHPDangKy.add(data);
				if (dataHPDangKy.size() < 1)
					return ;
			}
			if(dataHPDangKy.size() > 0) {
				DangKyHocPhan dangKyHP = new DangKyHocPhan(dataHPDangKy.get(0), this.quanLy.getHocPhan(dataHPDangKy.get(1)), dataHPDangKy.get(7));
				sv.getDsHPDangKy().add(dangKyHP);
			}
		}
		
		workbook.close();
		fin.close();
	}
	
	private void getDSDiemHP(SinhVien sv) throws IOException{
		String fileName = "";
		String loaiSV = (sv instanceof SinhVienTinChi)?"svtc":"svnc"; 
		if (loaiSV.equals("svtc"))
			fileName = "quanlysinhvien\\sinhvientinchi\\" + sv.getIdSinhVien() + "\\diem.xlsx";
		else if (loaiSV.equals("svnc"))
			fileName = "quanlysinhvien\\sinhviennienche\\" + sv.getIdSinhVien() + "\\diem.xlsx";
		FileInputStream inputStream = new FileInputStream(new File(fileName));

		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();

		Row nextRow;
		if (iterator.hasNext())
			nextRow = iterator.next(); // loại bỏ dòng tiêu đề
		while (iterator.hasNext()) {
			nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			ArrayList<String> dataDiemHP = new ArrayList<>();
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
				dataDiemHP.add(data);
				if (dataDiemHP.size() < 1)
					return ;
			}
			if (dataDiemHP.size() > 0) {
				DiemHocPhan diemHP = null;
				if(loaiSV.equals("svnc")) diemHP = new DiemHocPhan(dataDiemHP.get(0), this.quanLy.getHocPhan(dataDiemHP.get(1)), dataDiemHP.get(4), Double.parseDouble(dataDiemHP.get(5)), Double.parseDouble(dataDiemHP.get(6)), Double.parseDouble(dataDiemHP.get(7)));
				else if(loaiSV.equals("svtc")) diemHP = new DiemHocPhan(dataDiemHP.get(0), this.quanLy.getHocPhan(dataDiemHP.get(1)), dataDiemHP.get(4), 
						Double.parseDouble(dataDiemHP.get(5)), Double.parseDouble(dataDiemHP.get(6)), dataDiemHP.get(7), Double.parseDouble(dataDiemHP.get(8)));
				sv.getDsDiemHP().add(diemHP);
			}
		}

		workbook.close();
		inputStream.close();
	}
	
	private void getAllDSLopHP(QuanLy quanLy) throws IOException {
		FileInputStream inputStream = new FileInputStream(new File("quanlysinhvien/danhsachhocphan/lophocphan/dsLopHP.xlsx"));

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
            ArrayList<String> dataLopHP = new ArrayList<>();
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
                dataLopHP.add(data);
                if (dataLopHP.size() < 1) {
                    return ;
                }
            }
            if (dataLopHP.size() > 0) {
               LopHocPhan lopHocPhan = new LopHocPhan(dataLopHP.get(0), dataLopHP.get(1), dataLopHP.get(2), quanLy.getHocPhan(dataLopHP.get(3)), 
            		   dataLopHP.get(5), dataLopHP.get(6), dataLopHP.get(7), dataLopHP.get(8), (int) Double.parseDouble(dataLopHP.get(9)),
            		   (int) Double.parseDouble(dataLopHP.get(10)));
            try {
				lopHocPhan.setDsSinhVien(getDSSinhVien(dataLopHP.get(1), true));
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.out.println("Lớp " + dataLopHP.get(1) + " không có sinh viên" + e);
			}
               quanLy.getDsLopHocPhan().add(lopHocPhan);
            }
        }

        workbook.close();
        inputStream.close();
	}
	
	private void getAllDSLopCN(QuanLy quanLy) throws IOException {
		FileInputStream inputStream = new FileInputStream(new File("quanlysinhvien/danhsachchuyennganh/lopchuyennganh/dsLopCN.xlsx"));

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
                    return ;
                }
            }
            if (dataLopCN.size() > 0) {
                LopChuyenNganh lopChuyenNganh = new LopChuyenNganh(dataLopCN.get(0), dataLopCN.get(1), dataLopCN.get(2), dataLopCN.get(3));
                try {
					lopChuyenNganh.setDsSinhVien(getDSSinhVien(dataLopCN.get(0), false));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                quanLy.getDsLopChuyenNganh().add(lopChuyenNganh);
            }
        }

        workbook.close();
        inputStream.close();
	}
	
	private void getAllKhoa_Vien(QuanLy quanLy) throws IOException {
		FileInputStream inputStream = new FileInputStream(new File("quanlysinhvien/danhsachchuyennganh/dsNganh.xlsx"));

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
            ArrayList<String> dataNganh = new ArrayList<>();
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
                dataNganh.add(data);
                if (dataNganh.size() < 1) {
                    return ;
                }
            }
            if (dataNganh.size() > 0) {
                Khoa_Vien khoa_vien = new Khoa_Vien(dataNganh.get(0), dataNganh.get(1));
                khoa_vien.setDsLopChuyenNganh(quanLy.getDSLopCN(dataNganh.get(0)));
                quanLy.themKhoa_Vien(khoa_vien);
            }
        }

        workbook.close();
        inputStream.close();
	}
	
	private ArrayList<SinhVien> getDSSinhVien(String idLop, boolean lopHP) throws IOException{
		ArrayList<SinhVien> dsSV = new ArrayList<>();
		String fileName = null;
        if(lopHP) fileName = "quanlysinhvien\\danhsachhocphan\\lophocphan\\" + idLop + "_dsSV.xlsx";
        else fileName = "quanlysinhvien\\danhsachchuyennganh\\lopchuyennganh\\" + idLop + "_dsSV.xlsx";
        File file = new File(fileName);
        if(!file.exists()) return dsSV;
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
            Cell cellIdSV = nextRow.getCell(1);
            SinhVien sv = quanLy.getSinhVien(cellIdSV.getStringCellValue());
            dsSV.add(sv);
        }

        workbook.close();
        inputStream.close();
        return dsSV;
	}
	
	private void getAllDSHocPhan(ArrayList<HocPhan> dsHocPhan) throws IOException {
		FileInputStream inputStream = new FileInputStream(new File("quanlysinhvien/danhsachhocphan/dsHocPhan.xlsx"));

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();

        Row nextRow;
        if (iterator.hasNext()) {
            nextRow = iterator.next();  //loại bỏ dòng tiêu đề
        }
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
                    default:
                        data = "";
                        break;
                }
                dataHP.add(data);
            }
            HocPhan hp = new HocPhan(dataHP.get(0), dataHP.get(1), (int) (Double.parseDouble(dataHP.get(2))), (int) (Double.parseDouble(dataHP.get(3))), dataHP.get(4), Double.parseDouble(dataHP.get(5)));
            dsHocPhan.add(hp);
        }

        workbook.close();
        inputStream.close();
	}
}

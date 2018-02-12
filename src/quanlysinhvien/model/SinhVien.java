package quanlysinhvien.model;

import java.util.ArrayList;

public class SinhVien {
	protected String idSinhVien;
	protected String hoTen;
	protected String khoa;
	protected String tenLop;
	protected String ngaySinh;
	protected String gioiTinh;
	protected String email;
	protected String soDT;
	protected String diaChi;
	protected double diemTB;
	protected TaiKhoan taiKhoan;
	protected ArrayList<ChuongTrinhDaoTao> ctdt;
	protected ArrayList<DiemHocPhan> dsDiemHP;
	protected ArrayList<LopHocPhan> dsLopHPDangKy;
	protected ArrayList<DangKyHocPhan> dsHPDangKy;
	
	
	public SinhVien() {
		
	}
	
	public SinhVien(String idSinhVien, String hoTen, String khoa, String tenLop, String ngaySinh, String gioiTinh, String email,
			String soDT, String diaChi, double diemTB) {
		this.idSinhVien = idSinhVien;
		this.hoTen = hoTen;
		this.khoa = khoa;
		this.tenLop = tenLop;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.email = email;
		this.soDT = soDT;
		this.diaChi = diaChi;
		this.diemTB = diemTB;
		this.ctdt = new ArrayList<>();
		this.dsDiemHP = new ArrayList<>();
		this.dsLopHPDangKy = new ArrayList<>();
		this.dsHPDangKy = new ArrayList<>();
	}

	public SinhVien(String idSinhVien, String hoTen, String khoa, String tenLop, String ngaySinh, String gioiTinh, String email,
			String soDT, String diaChi, double diemTB, ArrayList<DiemHocPhan> dsDiemHP, ArrayList<LopHocPhan> dsLopHPDangKy, ArrayList<DangKyHocPhan> dsHPDangKy, ArrayList<ChuongTrinhDaoTao> ctdt) {
		this.idSinhVien = idSinhVien;
		this.hoTen = hoTen;
		this.khoa = khoa;
		this.tenLop = tenLop;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.email = email;
		this.soDT = soDT;
		this.diaChi = diaChi;
		this.diemTB = diemTB;
		this.dsDiemHP = dsDiemHP;
		this.dsLopHPDangKy = dsLopHPDangKy;
		this.dsHPDangKy = dsHPDangKy;
		this.ctdt = ctdt;
	}
	
	public boolean checkLopHP(String idLopHP) {
		for(int i = 0; i < dsLopHPDangKy.size(); i++) {
			if(dsLopHPDangKy.get(i).getIdLop().equalsIgnoreCase(idLopHP))
				return true;
		}
		
		return false;
	}
	
	public ArrayList<DangKyHocPhan> getDSHPDangKy(String hocKy){
		ArrayList<DangKyHocPhan> dsHPDK = new ArrayList<>();
		for(int i = 0; i < dsHPDangKy.size(); i++) {
			if(dsHPDangKy.get(i).getHocKy().equalsIgnoreCase(hocKy)) {
				dsHPDK.add(dsHPDangKy.get(i));
			}
		}
		
		return dsHPDK;
	}
	
	public ArrayList<LopHocPhan> getDSLopHPDangKy(String hocKy){
		ArrayList<LopHocPhan> dsLopHP = new ArrayList<>();
		for(int i = 0; i < dsLopHPDangKy.size(); i++) {
			if(dsLopHPDangKy.get(i).getHocKy().equalsIgnoreCase(hocKy)) {
				dsLopHP.add(dsLopHPDangKy.get(i));
			}
		}
		return dsLopHP;
	}
	
	public DangKyHocPhan getHPDangKy(String hocKy, String idHP) {
		for(DangKyHocPhan dkHP: dsHPDangKy) {
			if(dkHP.getHocKy().equalsIgnoreCase(hocKy) && dkHP.getHocPhan().getIdHocPhan().equalsIgnoreCase(idHP))
				return dkHP;
		}
		
		return null;
	}
	
	public boolean xoaHPDangKy(String hocKy, String idHP) {
		for(int i = 0; i < dsHPDangKy.size(); i++) {
			if(hocKy.equalsIgnoreCase(dsHPDangKy.get(i).getHocKy()) && idHP.equalsIgnoreCase(dsHPDangKy.get(i).getHocPhan().getIdHocPhan())) {
				dsHPDangKy.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
	public String getIdSinhVien() {
		return idSinhVien;
	}

	public void setIdSinhVien(String idSinhVien) {
		this.idSinhVien = idSinhVien;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public String getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(String ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSoDT() {
		return soDT;
	}

	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getKhoa() {
		return khoa;
	}

	public void setKhoa(String khoa) {
		this.khoa = khoa;
	}

	public String getTenLop() {
		return tenLop;
	}

	public void setTenLop(String tenLop) {
		this.tenLop = tenLop;
	}

	public double getDiemTB() {
		return diemTB;
	}

	public void setDiemTB(double diemTB) {
		this.diemTB = diemTB;
	}

	public ArrayList<DiemHocPhan> getDsDiemHP() {
		return dsDiemHP;
	}

	public void setDsDiemHP(ArrayList<DiemHocPhan> dsDiemHP) {
		this.dsDiemHP = dsDiemHP;
	}

	public ArrayList<LopHocPhan> getDsLopHPDangKy() {
		return dsLopHPDangKy;
	}

	public void setDsLopHPDangKy(ArrayList<LopHocPhan> dsLopHPDangKy) {
		this.dsLopHPDangKy = dsLopHPDangKy;
	}

	public ArrayList<DangKyHocPhan> getDsHPDangKy() {
		return dsHPDangKy;
	}

	public void setDsHPDangKy(ArrayList<DangKyHocPhan> dsHPDangKy) {
		this.dsHPDangKy = dsHPDangKy;
	}

	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	public ArrayList<ChuongTrinhDaoTao> getCtdt() {
		return ctdt;
	}

	public void setCtdt(ArrayList<ChuongTrinhDaoTao> ctdt) {
		this.ctdt = ctdt;
	}
	
}

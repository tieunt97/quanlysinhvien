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
	protected ArrayList<DiemHocPhan> dsDiemHP;
	protected ArrayList<String> dsLopHPDangKy;
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
		this.dsDiemHP = new ArrayList<>();
		this.dsLopHPDangKy = new ArrayList<>();
		this.dsHPDangKy = new ArrayList<>();
	}

	public SinhVien(String idSinhVien, String hoTen, String khoa, String tenLop, String ngaySinh, String gioiTinh, String email,
			String soDT, String diaChi, double diemTB, ArrayList<DiemHocPhan> dsDiemHP, ArrayList<String> dsLopHPDangKy, ArrayList<DangKyHocPhan> dsHPDangKy) {
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

	public ArrayList<String> getDsLopHPDangKy() {
		return dsLopHPDangKy;
	}

	public void setDsLopHPDangKy(ArrayList<String> dsLopHPDangKy) {
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
	
}

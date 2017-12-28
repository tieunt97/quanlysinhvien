package quanlysinhvien.model;

import java.util.ArrayList;

public class SinhVienTinChi extends SinhVien{
	private int soTCMax;
	private int soTCQua;
	private int soTCNo;
	
	
	public SinhVienTinChi() {
		super();
	}
	
	public SinhVienTinChi(String idSinhVien, String hoTen, String khoa, String tenLop, String ngaySinh, String gioiTinh, String email,
			String soDT, String diaChi, double diemTB, int soTCMax, int soTCQua, int soTCNo) {
		super(idSinhVien, hoTen, khoa, tenLop, ngaySinh, gioiTinh, email, soDT, diaChi, diemTB);
		this.taiKhoan = new TaiKhoan(idSinhVien, idSinhVien, "svtc");
		this.soTCMax = soTCMax;
		this.soTCQua = soTCQua;
		this.soTCNo = soTCNo;
	}
	
	public SinhVienTinChi(String idSinhVien, String hoTen, String khoa, String tenLop, String ngaySinh, String gioiTinh, String email,
			String soDT, String diaChi, double diemTB, TaiKhoan taiKhoan, int soTCMax, int soTCQua, int soTCNo) {
		super(idSinhVien, hoTen, khoa, tenLop, ngaySinh, gioiTinh, email, soDT, diaChi, diemTB);
		this.taiKhoan = taiKhoan;
		this.soTCMax = soTCMax;
		this.soTCQua = soTCQua;
		this.soTCNo = soTCNo;
	}
	
	public SinhVienTinChi(String idSinhVien, String hoTen, String khoa, String tenLop, String ngaySinh, String gioiTinh, String email,
			String soDT, String diaChi, double diemTB, TaiKhoan taiKhoan, ArrayList<DiemHocPhan> dsDiemHP, ArrayList<String> dsLopHPDangKy, ArrayList<DangKyHocPhan> dsHPDangKy,
			int soTCMax, int soTCQua, int soTCNo) {
		super(idSinhVien, hoTen, khoa, tenLop, ngaySinh, gioiTinh, email, soDT, diaChi, diemTB, dsDiemHP, dsLopHPDangKy, dsHPDangKy);
		this.taiKhoan = taiKhoan;
		this.soTCMax = soTCMax;
		this.soTCQua = soTCQua;
		this.soTCNo = soTCNo;
	}

	public int getSoTCQua() {
		return soTCQua;
	}

	public void setSoTCQua(int soTCQua) {
		this.soTCQua = soTCQua;
	}

	public int getSoTCNo() {
		return soTCNo;
	}

	public void setSoTCNo(int soTCNo) {
		this.soTCNo = soTCNo;
	}

	public int getSoTCMax() {
		return soTCMax;
	}

	public void setSoTCMax(int soTCMax) {
		this.soTCMax = soTCMax;
	}

}

package quanlysinhvien.model;

import java.util.ArrayList;

public class SinhVienNienChe extends SinhVien{
	private int tongSoKy;
	private ArrayList<HocPhan> dsHocPhanNo;
	
	
	public SinhVienNienChe() {
		super();
	}
	
	public SinhVienNienChe(String idSinhVien, String hoTen, String khoa, String tenLop, String ngaySinh, String gioiTinh, String email,
			String soDT, String diaChi, double diemTB, int tongSoKy) {
		super(idSinhVien, hoTen, khoa, tenLop, ngaySinh, gioiTinh, email, soDT, diaChi, diemTB);
		this.taiKhoan = new TaiKhoan(idSinhVien, idSinhVien, "svnc");
		this.tongSoKy = tongSoKy;
		this.dsHocPhanNo = new ArrayList<>();
	}
	
	public SinhVienNienChe(String idSinhVien, String hoTen, String khoa, String tenLop, String ngaySinh, String gioiTinh, String email,
			String soDT, String diaChi, double diemTB, TaiKhoan taiKhoan, int tongSoKy) {
		super(idSinhVien, hoTen, khoa, tenLop, ngaySinh, gioiTinh, email, soDT, diaChi, diemTB);
		this.taiKhoan = taiKhoan;
		this.tongSoKy = tongSoKy;
		this.dsHocPhanNo = new ArrayList<>();
	}
	
	public SinhVienNienChe(String idSinhVien, String hoTen, String khoa, String tenLop, String ngaySinh, String gioiTinh, String email,
			String soDT, String diaChi, double diemTB, ArrayList<DiemHocPhan> dsDiemHP, ArrayList<String> dsLopHPDangKy, ArrayList<DangKyHocPhan> dsHPDangKy,
			TaiKhoan taiKhoan, int tongSoKy, ArrayList<HocPhan> dsHPNo) {
		super(idSinhVien, hoTen, khoa, tenLop, ngaySinh, gioiTinh, email, soDT, diaChi, diemTB, dsDiemHP, dsLopHPDangKy, dsHPDangKy);
		this.taiKhoan = taiKhoan;
		this.tongSoKy = tongSoKy;
		this.dsHocPhanNo = dsHPNo;
	}
	
	public boolean themHocPhanNo(HocPhan hp) {
		for(int i = 0; i < dsHocPhanNo.size(); i++) {
			if(dsHocPhanNo.get(i).getIdHocPhan().equals(hp.getIdHocPhan()))
				return false;
		}
		
		dsHocPhanNo.add(hp);
		return true;
	}
	
	public boolean xoaHocPhanNo(String idHocPhan) {
		for(int i = 0; i < dsHocPhanNo.size(); i++) {
			if(dsHocPhanNo.get(i).getIdHocPhan().equals(idHocPhan)) {
				dsHocPhanNo.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
	public int getTongSoKy() {
		return tongSoKy;
	}

	public void setTongSoKy(int tongSoKy) {
		this.tongSoKy = tongSoKy;
	}

	public ArrayList<HocPhan> getDsHocPhanNo() {
		return dsHocPhanNo;
	}

	public void setDsHocPhanNo(ArrayList<HocPhan> dsHocPhanNo) {
		this.dsHocPhanNo = dsHocPhanNo;
	}
	
}

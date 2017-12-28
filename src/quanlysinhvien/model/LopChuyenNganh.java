package quanlysinhvien.model;

import java.util.ArrayList;

public class LopChuyenNganh {
	private ArrayList<SinhVien> dsSinhVien;
	private String idLopChuyenNganh;
	private String tenLop;
	private String tenChuNhiem;
	private String idKhoa_Vien;
	

	
	public LopChuyenNganh() {
		dsSinhVien = new ArrayList<>();
	}
	
	public LopChuyenNganh(String idLopCN, String tenLop, String tenChunhiem, String idKhoa_Vien) {
		this.idLopChuyenNganh = idLopCN;
		this.tenLop = tenLop;
		this.tenChuNhiem = tenChunhiem;
		this.idKhoa_Vien = idKhoa_Vien;
		this.dsSinhVien = new ArrayList<>();
	}
	
	public LopChuyenNganh(ArrayList<SinhVien> dsSinhVien, String idLopChuyenNganh, String tenLop, String tenChuNhiem, String idKhoa_Vien) {
		this.dsSinhVien = dsSinhVien;
		this.idLopChuyenNganh = idLopChuyenNganh;
		this.tenLop = tenLop;
		this.idKhoa_Vien = idKhoa_Vien;
		this.tenChuNhiem = tenChuNhiem;
	}

	public boolean themSinhVien(SinhVien sv) {
		for(SinhVien sv1: dsSinhVien) {
			if(sv1.getIdSinhVien().equalsIgnoreCase(sv.getIdSinhVien())) {
				return false;
			}
		}
		this.dsSinhVien.add(sv);
		return true;
	}
	
	public boolean xoaSinhVien(String idSinhVien) {
		for(int i = 0; i < dsSinhVien.size(); i++) {
			if(dsSinhVien.get(i).getIdSinhVien().equals(idSinhVien)) {
				dsSinhVien.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<SinhVien> getDsSinhVien() {
		return dsSinhVien;
	}

	public void setDsSinhVien(ArrayList<SinhVien> dsSinhVien) {
		this.dsSinhVien = dsSinhVien;
	}

	public String getIdLopChuyenNganh() {
		return idLopChuyenNganh;
	}

	public void setIdLopChuyenNganh(String idLopChuyenNganh) {
		this.idLopChuyenNganh = idLopChuyenNganh;
	}

	public String getTenChuNhiem() {
		return tenChuNhiem;
	}

	public void setTenChuNhiem(String tenChuNhiem) {
		this.tenChuNhiem = tenChuNhiem;
	}

	public String getTenLop() {
		return tenLop;
	}

	public void setTenLop(String tenLop) {
		this.tenLop = tenLop;
	}

	public String getIdKhoa_Vien() {
		return idKhoa_Vien;
	}

	public void setIdKhoa_Vien(String idKhoa_Vien) {
		this.idKhoa_Vien = idKhoa_Vien;
	}
	
}

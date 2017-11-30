package quanlysinhvien.model;

import java.util.ArrayList;

public class LopChuyenNganh {
	private ArrayList<SinhVien> dsSinhVien;
	private String idLopChuyenNganh;
	private String tenLop;
	private String tenChuNhiem;
	private String idNganh;
	private String tenNganh;
	

	
	public LopChuyenNganh() {
		dsSinhVien = new ArrayList<>();
	}
	
	public LopChuyenNganh(ArrayList<SinhVien> dsSinhVien, String idLopChuyenNganh, String tenLop,
			String tenChuNhiem, String idNganh, String tenNganh) {
		this.dsSinhVien = dsSinhVien;
		this.idLopChuyenNganh = idLopChuyenNganh;
		this.tenLop = tenLop;
		this.tenChuNhiem = tenChuNhiem;
		this.idNganh = idNganh;
		this.tenNganh = tenNganh;
	}

	public void themSinhVien(SinhVien sv) {
		this.dsSinhVien.add(sv);
	}
	
	public boolean xoaSinhVien(String idSinhVien, SinhVien sv) {
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

	public String getIdNganh() {
		return idNganh;
	}

	public void setIdNganh(String idNganh) {
		this.idNganh = idNganh;
	}

	public String getTenNganh() {
		return tenNganh;
	}

	public void setTenNganh(String tenNganh) {
		this.tenNganh = tenNganh;
	}

	public String getTenLop() {
		return tenLop;
	}

	public void setTenLop(String tenLop) {
		this.tenLop = tenLop;
	}
	
}

package quanlysinhvien.model;

import java.util.ArrayList;

public class LopHocPhan {
	private String hocKy;
	private String idLop;
	private String loaiLop;
	private HocPhan hocPhan;
	private String thoiGian;
	private String tuanHoc;
	private String phongHoc;
	private ArrayList<SinhVien> dsSinhVien;
	private String tenGiangVien;
	private int soSVMax;
	private int soSVHienTai;
	
	
	
	public LopHocPhan() {
		dsSinhVien = new ArrayList<>();
		this.soSVMax = 80;
		this.soSVHienTai = dsSinhVien.size();
	}
	
	public LopHocPhan(String hocKy, String idLop, String loaiLop, HocPhan hocPhan, String thoiGian, String tuanHoc, String phongHoc, String tenGiangVien, int soSVMax, int soSVHienTai) {
		this.hocKy = hocKy;
		this.idLop = idLop;
		this.loaiLop = loaiLop;
		this.hocPhan = hocPhan;
		this.thoiGian = thoiGian;
		this.tuanHoc = tuanHoc;
		this.phongHoc = phongHoc;
		this.tenGiangVien = tenGiangVien;
		this.soSVMax = soSVMax;
		this.soSVHienTai = soSVHienTai;
		
		this.dsSinhVien = new ArrayList<>();
	}
	
	
	public LopHocPhan(String hocKy, String idLop, String loaiLop, HocPhan hocPhan, String thoiGian, String tuanHoc, String phongHoc, ArrayList<SinhVien> dsSinhVien, String tenGiangVien, int soSVMax, int soSVHienTai) {
		this.hocKy = hocKy;
		this.idLop = idLop;
		this.loaiLop = loaiLop;
		this.hocPhan = hocPhan;
		this.thoiGian = thoiGian;
		this.tuanHoc = tuanHoc;
		this.phongHoc = phongHoc;
		this.dsSinhVien = dsSinhVien;
		this.tenGiangVien = tenGiangVien;
		this.soSVMax = soSVMax;
		this.soSVHienTai = dsSinhVien.size();
	}
	
	public boolean themSinhVien(SinhVien sv, String max) {
		if(kiemTraTrungSV(sv) == true){
			return false;
		}
		if(max.equals("max"))
			if(soSVHienTai < soSVMax) {
				this.dsSinhVien.add(sv);
				soSVHienTai = dsSinhVien.size();
				return true;
			}else {
				return false;
			}
		else {
			this.dsSinhVien.add(sv);
			return true;
		}
	}
	
	public boolean kiemTraTrungSV(SinhVien sv) {
		// TODO Auto-generated method stub
		int count = dsSinhVien.size();
		for(int i = 0; i < count; i++){
			if(sv.idSinhVien.equalsIgnoreCase(dsSinhVien.get(i).getIdSinhVien())){
				return true;
			}
		}
		
		return false;
	}

	public boolean xoaSinhVien(String idSinhVien) {
		for(int i = 0; i < dsSinhVien.size(); i++) {
			if(dsSinhVien.get(i).getIdSinhVien().equals(idSinhVien)) {
				dsSinhVien.remove(i);
				soSVHienTai = dsSinhVien.size();
				return true;
			}
		}
		return false;
	}
	

	public String getIdLop() {
		return idLop;
	}

	public void setIdLop(String idLop) {
		this.idLop = idLop;
	}

	public ArrayList<SinhVien> getDsSinhVien() {
		return dsSinhVien;
	}

	public void setDsSinhVien(ArrayList<SinhVien> dsSinhVien) {
		this.dsSinhVien = dsSinhVien;
	}

	public String getTenGiangVien() {
		return tenGiangVien;
	}

	public void setTenGiangVien(String tenGiangVien) {
		this.tenGiangVien = tenGiangVien;
	}

	public int getSoSVMax() {
		return soSVMax;
	}

	public void setSoSVMax(int soSVMax) {
		this.soSVMax = soSVMax;
	}

	public int getSoSVHienTai() {
		return soSVHienTai;
	}

	public void setSoSVHienTai(int soSVHienTai) {
		this.soSVHienTai = soSVHienTai;
	}

	public String getThoiGian() {
		return thoiGian;
	}

	public void setThoiGian(String thoiGian) {
		this.thoiGian = thoiGian;
	}

	public String getTuanHoc() {
		return tuanHoc;
	}

	public void setTuanHoc(String tuanHoc) {
		this.tuanHoc = tuanHoc;
	}

	public String getPhongHoc() {
		return phongHoc;
	}

	public void setPhongHoc(String phongHoc) {
		this.phongHoc = phongHoc;
	}

	public HocPhan getHocPhan() {
		return hocPhan;
	}

	public void setHocPhan(HocPhan hocPhan) {
		this.hocPhan = hocPhan;
	}

	public String getHocKy() {
		return hocKy;
	}

	public void setHocKy(String hocKy) {
		this.hocKy = hocKy;
	}

	public String getLoaiLop() {
		return loaiLop;
	}

	public void setLoaiLop(String loaiLop) {
		this.loaiLop = loaiLop;
	}
	
}

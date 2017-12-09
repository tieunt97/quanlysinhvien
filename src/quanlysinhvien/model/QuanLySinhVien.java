package quanlysinhvien.model;

import java.util.ArrayList;

public class QuanLySinhVien {
	private ArrayList<SinhVien> dsSinhVien;

	public QuanLySinhVien() {
		dsSinhVien = new ArrayList<SinhVien>();
	}
	
	public boolean addSV(SinhVien sinhVien) {
		for(SinhVien sv: dsSinhVien)
			if(sv.getIdSinhVien().equalsIgnoreCase(sinhVien.getIdSinhVien()))
				return false;
		dsSinhVien.add(sinhVien);
		return true;
	}
	
	public boolean deleteSV(String idSV) {
		for(int i = 0; i < dsSinhVien.size(); i++) {
			if(dsSinhVien.get(i).getIdSinhVien().equalsIgnoreCase(idSV)) {
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
	
}

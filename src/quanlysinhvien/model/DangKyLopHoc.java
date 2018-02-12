package quanlysinhvien.model;

import java.util.ArrayList;

public class DangKyLopHoc {
	private ArrayList<LopHocPhan> dsLopHP;
	private int tongSoTC;
	
	public DangKyLopHoc() {
		dsLopHP = new ArrayList<>();
		tongSoTC = 0;
	}
	
	public DangKyLopHoc(ArrayList<LopHocPhan> dsLopHP) {
		this.dsLopHP = dsLopHP;
		for(int i = 0; i < dsLopHP.size(); i++) {
			tongSoTC += dsLopHP.get(i).getHocPhan().getSoTinChi();
		}
	}

	public LopHocPhan getLopHP(String idLopHP) {
		for(int i = 0; i < dsLopHP.size(); i++) {
			if(dsLopHP.get(i).getIdLop().equalsIgnoreCase(idLopHP))
				return dsLopHP.get(i);
		}
		
		return null;
	}
	
	public boolean checkTCMax(int soTCMax, int soTCHP) {
		if(tongSoTC + soTCHP > soTCMax)
			return true;
		else return false;
	}
	
	public boolean checkHocPhanDK(LopHocPhan lopHP) {
		for(int i = 0; i < dsLopHP.size(); i++) {
			if(dsLopHP.get(i).getHocKy().equalsIgnoreCase(lopHP.getHocKy()) &&
					dsLopHP.get(i).getHocPhan().getIdHocPhan().equalsIgnoreCase(lopHP.getHocPhan().getIdHocPhan()))
				return true;
		}
		
		return false;
	}
	
	public boolean themLopHP(LopHocPhan lopHP) {
		for(int i = 0; i < dsLopHP.size(); i++) {
			if(dsLopHP.get(i).getHocKy().equalsIgnoreCase(lopHP.getHocKy()) &&
					dsLopHP.get(i).getIdLop().equalsIgnoreCase(lopHP.getIdLop()))
				return false;
		}
		
		dsLopHP.add(lopHP);
		tongSoTC += lopHP.getHocPhan().getSoTinChi();
		return true;
	}
	
	public boolean xoaLopHP(String idLopHP) {
		for(int i = 0; i < dsLopHP.size(); i++) {
			if(dsLopHP.get(i).getHocPhan().getIdHocPhan().equalsIgnoreCase(idLopHP)) {
				tongSoTC -= dsLopHP .get(i).getHocPhan().getSoTinChi();
				dsLopHP.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
	public ArrayList<LopHocPhan> getDsLopHP() {
		return dsLopHP;
	}

	public void setDsLopHP(ArrayList<LopHocPhan> dsLopHP) {
		this.dsLopHP = dsLopHP;
	}

	public int getTongSoTC() {
		return tongSoTC;
	}

	public void setTongSoTC(int tongSoTC) {
		this.tongSoTC = tongSoTC;
	}
	
}

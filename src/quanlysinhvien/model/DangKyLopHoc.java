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
	
	public boolean checkHocPhanDK(String idHP) {
		for(int i = 0; i < dsLopHP.size(); i++) {
			if(dsLopHP.get(i).getHocPhan().getIdHocPhan().equalsIgnoreCase(idHP))
				return true;
		}
		
		return false;
	}
	
	public boolean themLopHP(LopHocPhan lopHP) {
		for(int i = 0; i < dsLopHP.size(); i++) {
			if(dsLopHP.get(i).getIdLop().equalsIgnoreCase(lopHP.getIdLop()))
				return false;
		}
		
		dsLopHP.add(lopHP);
		return true;
	}
	
	public boolean themLopHP(int soTCMax, LopHocPhan lopHocPhan) {
		for(int i = 0; i < dsLopHP.size(); i++) {
			if(dsLopHP.get(i).getIdLop().equalsIgnoreCase(lopHocPhan.getIdLop()))
				return false;
		}
		if(tongSoTC + lopHocPhan.getHocPhan().getSoTinChi() > soTCMax)
			return false;
		else {
			dsLopHP.add(lopHocPhan);
			return true;
		}
	}
	
	public boolean xoaLopHP(String idLopHP) {
		for(int i = 0; i < dsLopHP.size(); i++) {
			if(dsLopHP.get(i).getHocPhan().getIdHocPhan().equalsIgnoreCase(idLopHP)) {
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

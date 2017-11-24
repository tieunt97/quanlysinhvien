package quanlysinhvien.model;

import java.util.ArrayList;

public class Nganh {
	private ArrayList<LopChuyenNganh> dsLopChuyenNganh;
	private String idNganh;
	private String tenNganh;
	
	
	
	public Nganh() {
		this.dsLopChuyenNganh = new ArrayList<>();
	}
	
	public Nganh(ArrayList<LopChuyenNganh> dsLopChuyenNganh, String idNganh, String tenNganh) {
		this.dsLopChuyenNganh = dsLopChuyenNganh;
		this.idNganh = idNganh;
		this.tenNganh = tenNganh;
	}
	
	public void themLopChuyenNganh(LopChuyenNganh lopCN) {
		this.dsLopChuyenNganh.add(lopCN);
	}
	
	public boolean xoaLopChuyenNganh(String idLopChuyenNganh) {
		for(int i = 0; i < dsLopChuyenNganh.size(); i++) {
			if(dsLopChuyenNganh.get(i).getIdLopChuyenNganh().equals(idLopChuyenNganh)) {
				dsLopChuyenNganh.remove(i);
				return true;
			}
		}
		return false;
	}

	
	public ArrayList<LopChuyenNganh> getDsLopChuyenNganh() {
		return dsLopChuyenNganh;
	}

	public void setDsLopChuyenNganh(ArrayList<LopChuyenNganh> dsLopChuyenNganh) {
		this.dsLopChuyenNganh = dsLopChuyenNganh;
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
	
}

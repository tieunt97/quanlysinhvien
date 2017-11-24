package quanlysinhvien.model;

import java.util.ArrayList;

public class PhongDaoTao {
	private ArrayList<HocPhan> dsHocPhan;
	private ArrayList<LopHocPhan> dsLopHocPhan;
	private ArrayList<Nganh> dsNganh;
	private ArrayList<LopChuyenNganh> dsLopChuyenNganh;
	
	
	
	public PhongDaoTao() {
		this.dsHocPhan = new ArrayList<>();
		this.dsLopHocPhan = new ArrayList<>();
		this.dsNganh = new ArrayList<>();
	}
	
	public PhongDaoTao(ArrayList<HocPhan> dsHocPhan, ArrayList<LopHocPhan> dsLopHocPhan, 
			ArrayList<Nganh> dsNganh, ArrayList<LopChuyenNganh> dsLopChuyenNganh) {
		this.dsHocPhan = dsHocPhan;
		this.dsLopHocPhan = dsLopHocPhan;
		this.dsNganh = dsNganh;
		this.dsLopChuyenNganh = dsLopChuyenNganh;
	}

	
	public ArrayList<HocPhan> getDsHocPhan() {
		return dsHocPhan;
	}

	public void setDsHocPhan(ArrayList<HocPhan> dsHocPhan) {
		this.dsHocPhan = dsHocPhan;
	}

	public ArrayList<LopHocPhan> getDsLopHocPhan() {
		return dsLopHocPhan;
	}

	public void setDsLopHocPhan(ArrayList<LopHocPhan> dsLopHocPhan) {
		this.dsLopHocPhan = dsLopHocPhan;
	}

	public ArrayList<Nganh> getDsNganh() {
		return dsNganh;
	}

	public void setDsNganh(ArrayList<Nganh> dsNganh) {
		this.dsNganh = dsNganh;
	}

	public ArrayList<LopChuyenNganh> getDsLopChuyenNganh() {
		return dsLopChuyenNganh;
	}

	public void setDsLopChuyenNganh(ArrayList<LopChuyenNganh> dsLopChuyenNganh) {
		this.dsLopChuyenNganh = dsLopChuyenNganh;
	}
	
}

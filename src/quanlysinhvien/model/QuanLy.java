package quanlysinhvien.model;

import java.util.ArrayList;

public class QuanLy {
	private ArrayList<SinhVien> dsSinhVien;
	private ArrayList<HocPhan> dsHocPhan;
	private ArrayList<LopHocPhan> dsLopHocPhan;
	private ArrayList<LopChuyenNganh> dsLopChuyenNganh;
	private ArrayList<Khoa_Vien> dsKhoa_Vien;
	
	public QuanLy() {
		dsSinhVien = new ArrayList<>();
		dsHocPhan = new ArrayList<>();
		dsLopHocPhan = new ArrayList<>();
		dsLopChuyenNganh = new ArrayList<>();
		dsKhoa_Vien = new ArrayList<>();
	}
	
	public QuanLy(ArrayList<SinhVien> dsSinhVien, ArrayList<HocPhan> dsHocPhan, ArrayList<LopHocPhan> dsLopHocPhan, 
			ArrayList<Khoa_Vien> dsKhoa_Vien, ArrayList<LopChuyenNganh> dsLopChuyenNganh) {
		this.dsSinhVien = dsSinhVien;
		this.dsHocPhan = dsHocPhan;
		this.dsLopHocPhan = dsLopHocPhan;
		this.dsLopChuyenNganh = dsLopChuyenNganh;
		this.dsKhoa_Vien = dsKhoa_Vien;
	}
	
	public boolean themKhoa_Vien(Khoa_Vien khoaVien) {
		for(Khoa_Vien kv: dsKhoa_Vien) {
			if(kv.getIdKhoa_Vien().equals(khoaVien.getIdKhoa_Vien()))
				return false;
		}
		dsKhoa_Vien.add(khoaVien);
		return true;
	}
	
	public boolean xoaKhoa_Vien(String idKhoa_Vien) {
		for(int i = 0; i < dsKhoa_Vien.size(); i++) {
			if(dsKhoa_Vien.get(i).getIdKhoa_Vien().equals(idKhoa_Vien)) {
				dsKhoa_Vien.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
	public Khoa_Vien getKhoa_Vien(String idKhoa_Vien) {
		for(Khoa_Vien kv: dsKhoa_Vien) {
			if(kv.getIdKhoa_Vien().equals(idKhoa_Vien))
				return kv;
		}
		return null;
	}
	
	public ArrayList<LopChuyenNganh> getDSLopCN(String idKhoa_Vien){
		ArrayList<LopChuyenNganh> dsLopCN = new ArrayList<>();
		for(LopChuyenNganh lopCN: dsLopChuyenNganh) {
			if(lopCN.getIdKhoa_Vien().equals(idKhoa_Vien))
				dsLopCN.add(lopCN);
		}
		
		return dsLopCN;
	}
	
	public boolean themSinhVien(SinhVien sinhVien) {
		for(SinhVien sv: dsSinhVien)
			if(sv.getIdSinhVien().equalsIgnoreCase(sinhVien.getIdSinhVien()))
				return false;
		dsSinhVien.add(sinhVien);
		return true;
	}
	
	public boolean xoaSinhVien(String idSV) {
		for(int i = 0; i < dsSinhVien.size(); i++) {
			if(dsSinhVien.get(i).getIdSinhVien().equalsIgnoreCase(idSV)) {
				dsSinhVien.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public SinhVien getSinhVien(String idSV) {
		for(SinhVien sv: dsSinhVien) {
			if(sv.getIdSinhVien().equals(idSV)) {
				return sv;
			}
		}
		
		return null;
	}
	
	public boolean themHocPhan(HocPhan hocPhan) {
		for(HocPhan hp: dsHocPhan) {
			if(hp.getIdHocPhan().equals(hocPhan.getIdHocPhan()))
				return false;
		}
		dsHocPhan.add(hocPhan);
		return true;
	}
	
	public boolean xoaHocPhan(String idHocPhan) {
		for(int i = 0; i < dsHocPhan.size(); i++) {
			if(dsHocPhan.get(i).getIdHocPhan().equals(idHocPhan)) {
				dsHocPhan.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public HocPhan getHocPhan(String idHocPhan) {
		for(HocPhan hp: dsHocPhan) {
			if(hp.getIdHocPhan().equals(idHocPhan))
				return hp;
		}
		return null;
	}
	
	public boolean themLopHocPhan(LopHocPhan lopHocPhan) {
		for(LopHocPhan lopHP: dsLopHocPhan) {
			if(lopHP.getIdLop().equals(lopHocPhan.getIdLop()))
				return false;
		}
		dsLopHocPhan.add(lopHocPhan);
		return true;
	}
	
	public boolean xoaLopHocPhan(String idLopHP) {
		for(int i = 0; i < dsLopHocPhan.size(); i++) {
			if(dsLopHocPhan.get(i).getIdLop().equals(idLopHP)) {
				dsLopHocPhan.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public LopHocPhan getLopHocPhan(String idLopHP) {
		for(LopHocPhan lopHP: dsLopHocPhan) {
			if(lopHP.getIdLop().equals(idLopHP))
				return lopHP;
		}
		return null;
	}
	
	public boolean themLopChuyenNganh(LopChuyenNganh LopChuyenNganh) {
		for(LopChuyenNganh lopCN: dsLopChuyenNganh) {
			if(lopCN.getIdLopChuyenNganh().equals(LopChuyenNganh.getIdLopChuyenNganh()))
				return false;
		}
		dsLopChuyenNganh.add(LopChuyenNganh);
		return true;
	}
	
	public boolean xoaLopChuyenNganh(String idLopCN) {
		for(int i = 0; i < dsLopChuyenNganh.size(); i++) {
			if(dsLopChuyenNganh.get(i).getIdLopChuyenNganh().equals(idLopCN)) {
				dsLopChuyenNganh.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public LopChuyenNganh getLopChuyenNganh(String idLopCN) {
		for(LopChuyenNganh lopCN: dsLopChuyenNganh) {
			if(lopCN.getIdLopChuyenNganh().equals(idLopCN))
				return lopCN;
		}
		
		return null;
	}

	public ArrayList<SinhVien> getDsSinhVien() {
		return dsSinhVien;
	}

	public void setDsSinhVien(ArrayList<SinhVien> dsSinhVien) {
		this.dsSinhVien = dsSinhVien;
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

	public ArrayList<LopChuyenNganh> getDsLopChuyenNganh() {
		return dsLopChuyenNganh;
	}

	public void setDsLopChuyenNganh(ArrayList<LopChuyenNganh> dsLopChuyenNganh) {
		this.dsLopChuyenNganh = dsLopChuyenNganh;
	}

	public ArrayList<Khoa_Vien> getDsKhoa_Vien() {
		return dsKhoa_Vien;
	}

	public void setDsKhoa_Vien(ArrayList<Khoa_Vien> dsKhoa_Vien) {
		this.dsKhoa_Vien = dsKhoa_Vien;
	}

}

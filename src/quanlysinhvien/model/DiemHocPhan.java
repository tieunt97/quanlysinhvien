package quanlysinhvien.model;

public class DiemHocPhan {
	private String hocKy, idHocPhan, tenHP, idLopHoc, diemChu;
	private int tinChi;
	private double diemQT, diemThi;
	
	public DiemHocPhan() {
		
	}
	
	public DiemHocPhan(String hocKy,  HocPhan hocPhan, String idLopHoc, double diemQT, double diemThi) {
		this.hocKy = hocKy;
		this.idHocPhan = hocPhan.getIdHocPhan();
		this.tenHP = hocPhan.getTenHP();
		this.tinChi = hocPhan.getSoTinChi();
		this.idLopHoc = idLopHoc;
		this.diemQT = diemQT;
		this.diemThi = diemThi;
		this.diemChu = tinhDiemChu(hocPhan);
	}
	
	public DiemHocPhan(String hocKy,  String idHocPhan, String tenHP, int tinChi, String idLopHoc, double diemQT, double diemThi, String diemChu) {
		this.hocKy = hocKy;
		this.idHocPhan = idHocPhan;
		this.tenHP = tenHP;
		this.tinChi = tinChi;
		this.idLopHoc = idLopHoc;
		this.diemQT = diemQT;
		this.diemThi = diemThi;
		this.diemChu = diemChu;
	}
	
	private String tinhDiemChu(HocPhan hp) {
		double trongSo = hp.getTrongSo();
		double diem = (double)Math.round(((this.diemThi * trongSo + this.diemQT * (1 - trongSo))*100))/100;
		System.out.println(diem);
		if(diem < 4.0) return "F";
		else if(diem >= 4.0 && diem < 5.0) return "D";
		else if(diem >= 5.0 && diem < 5.5) return "D+";
		else if(diem >= 5.5 && diem < 6.5) return "C";
		else if(diem >= 6.5 && diem < 7.0) return "C+";
		else if(diem >= 7.0 && diem < 8.0) return "B";
		else if(diem >= 8.0 && diem < 8.5) return "B+";
		else if(diem >= 8.5 && diem < 9.5) return "A";
		else return "A+";
	}
	
	public String getHocKy() {
		return hocKy;
	}

	public void setHocKy(String hocKy) {
		this.hocKy = hocKy;
	}

	public String getIdHocPhan() {
		return idHocPhan;
	}

	public void setIdHocPhan(String idHocPhan) {
		this.idHocPhan = idHocPhan;
	}

	public String getTenHP() {
		return tenHP;
	}

	public void setTenHP(String tenHP) {
		this.tenHP = tenHP;
	}

	public String getLopHoc() {
		return idLopHoc;
	}

	public void setLopHoc(String lopHoc) {
		this.idLopHoc = lopHoc;
	}

	public String getDiemChu() {
		return diemChu;
	}

	public void setDiemChu(String diemChu) {
		this.diemChu = diemChu;
	}

	public int getTinChi() {
		return tinChi;
	}

	public void setTinChi(int tinChi) {
		this.tinChi = tinChi;
	}

	public double getDiemQT() {
		return diemQT;
	}

	public void setDiemQT(double diemQT) {
		this.diemQT = diemQT;
	}

	public double getDiemThi() {
		return diemThi;
	}

	public void setDiemThi(double diemThi) {
		this.diemThi = diemThi;
	}

	public static void main(String[] args) {
		DiemHocPhan diem = new DiemHocPhan("20171", new HocPhan("IT3090", "Làm việc nhóm", 3, 3, "IT", 0.7), "1232467", 8, 8.5);
		System.out.println(diem.getDiemChu());
	}
}

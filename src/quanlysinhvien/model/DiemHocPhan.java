package quanlysinhvien.model;

public class DiemHocPhan {
	private String hocKy, diemChu;
	private HocPhan hocPhan;
	private String idLopHoc;
	private double diemQT, diemThi, diemThang4, diemThang10;
	
	public DiemHocPhan() {
		
	}
	
	public DiemHocPhan(String hocKy,  HocPhan hocPhan, String idLopHoc, double diemQT, double diemThi, String svtc) {
		this.hocKy = hocKy;
		this.hocPhan = hocPhan;
		this.idLopHoc = idLopHoc;
		this.diemQT = diemQT;
		this.diemThi = diemThi;
		this.diemChu = tinhDiemChu();
		this.diemThang4 = tinhDiemThang4(this.diemChu);
	}
	
	public DiemHocPhan(String hocKy,  HocPhan hocPhan, String idLopHoc, double diemQT, double diemThi) {
		this.hocKy = hocKy;
		this.hocPhan = hocPhan;
		this.idLopHoc = idLopHoc;
		this.diemQT = diemQT;
		this.diemThi = diemThi;
		this.diemThang10 = tinhDiemThang10(diemQT, diemThi);
	}
	
	public DiemHocPhan(String hocKy,  HocPhan hocPhan, String idLopHoc, double diemQT, double diemThi, String diemChu, double diemThang4) {
		this.hocKy = hocKy;
		this.hocPhan = hocPhan;
		this.idLopHoc = idLopHoc;
		this.diemQT = diemQT;
		this.diemThi = diemThi;
		this.diemChu = diemChu;
		this.diemThang4 = diemThang4;
	}
	
	public DiemHocPhan(String hocKy, HocPhan hocPhan, String idLopHoc, double diemQT, double diemThi, double diemThang10) {
		this.hocKy = hocKy;
		this.hocPhan = hocPhan;
		this.idLopHoc = idLopHoc;
		this.diemQT = diemQT;
		this.diemThi = diemThi;
		this.diemThang10 = diemThang10;
	}
	
	private String tinhDiemChu() {
		double trongSo = hocPhan.getTrongSo();
		double diem = (double)Math.round(((this.diemThi * trongSo + this.diemQT * (1 - trongSo))*10))/10;
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
	
	private double tinhDiemThang10(double diemQT, double diemThi) {
		double trongSo = hocPhan.getTrongSo();
		double diem = (double)Math.round(((this.diemThi * trongSo + this.diemQT*(1 - trongSo))*10))/10;
		double du1 = diem%1;
		if(du1 <= 0.3) return diem/1;
		else if(du1 < 0.5) return diem/1 + 0.5;
		else if(du1 == 0.5) return diem;
		else if(du1 < 0.7) return diem/1 + 0.5;
		else return diem/1 + 1;
	}
	
	private double tinhDiemThang4(String diemChu) {
		if(diemChu.equals("F")) return 0;
		else if(diemChu.equals("D")) return 1.0;
		else if(diemChu.equals("D+")) return 1.5;
		else if(diemChu.equals("C")) return 2.0;
		else if(diemChu.equals("C+")) return 2.5;
		else if(diemChu.equals("B")) return 3.0;
		else if(diemChu.equals("B+")) return 3.5;
		else return 4.0;
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

	public String getIdLopHoc() {
		return idLopHoc;
	}

	public void setIdLopHoc(String idLopHoc) {
		this.idLopHoc = idLopHoc;
	}

	public String getDiemChu() {
		return diemChu;
	}

	public void setDiemChu(String diemChu) {
		this.diemChu = diemChu;
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

	public double getDiemThang4() {
		return diemThang4;
	}

	public void setDiemThang4(double diemThang4) {
		this.diemThang4 = diemThang4;
	}

	public double getDiemThang10() {
		return diemThang10;
	}

	public void setDiemThang10(double diemThang10) {
		this.diemThang10 = diemThang10;
	}
	
}

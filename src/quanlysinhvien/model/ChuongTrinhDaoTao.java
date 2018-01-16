package quanlysinhvien.model;

public class ChuongTrinhDaoTao {
	private int kyHoc;
	private double diemSo;
	private String diemChu;
	private HocPhan hocPhan;
	
	public ChuongTrinhDaoTao(HocPhan hocPhan, int kyHoc, double diemSo) {
		this.hocPhan = hocPhan;
		this.kyHoc = kyHoc;
		this.diemSo = diemSo;
	}
	
	public ChuongTrinhDaoTao(HocPhan hocPhan, int kyHoc, double diemSo, String diemChu) {
		this.hocPhan = hocPhan;
		this.kyHoc = kyHoc;
		this.diemSo = diemSo;
		this.diemChu = diemChu;
	}

	public int getKyHoc() {
		return kyHoc;
	}

	public void setKyHoc(int kyHoc) {
		this.kyHoc = kyHoc;
	}

	public double getDiemSo() {
		return diemSo;
	}

	public void setDiemSo(double diemTB) {
		this.diemSo = diemTB;
	}

	public String getDiemChu() {
		return diemChu;
	}

	public void setDiemChu(String diemChu) {
		this.diemChu = diemChu;
	}

	public HocPhan getHocPhan() {
		return hocPhan;
	}

	public void setHocPhan(HocPhan hocPhan) {
		this.hocPhan = hocPhan;
	}
	
}

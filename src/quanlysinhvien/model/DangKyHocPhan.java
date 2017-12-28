package quanlysinhvien.model;

public class DangKyHocPhan {
	private String hocKy;
	private HocPhan hocPhan;
	private String ngayDangKy;
	
	
	public DangKyHocPhan() {
		hocPhan = new HocPhan();
	}
	
	public DangKyHocPhan(String hocKy, HocPhan hocPhan, String ngayDangKy) {
		this.hocKy = hocKy;
		this.hocPhan = hocPhan;
		this.ngayDangKy = ngayDangKy;
	}

	public String getHocKy() {
		return hocKy;
	}

	public void setHocKy(String hocKy) {
		this.hocKy = hocKy;
	}

	public HocPhan getHocPhan() {
		return hocPhan;
	}

	public void setHocPhan(HocPhan hocPhan) {
		this.hocPhan = hocPhan;
	}

	public String getNgayDangKy() {
		return ngayDangKy;
	}

	public void setNgayDangKy(String ngayDangKy) {
		this.ngayDangKy = ngayDangKy;
	}
}

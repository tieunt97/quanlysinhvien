package quanlysinhvien.model;

public class SinhVienNienChe extends SinhVien{
	private String tongSoKy;
	
	
	
	public SinhVienNienChe() {
		super();
	}
	
	public SinhVienNienChe(String idSinhVien, String hoTen, String khoa, String ngaySinh, String gioiTinh, String email,
			String soDT, String diaChi, double diemTB, String tongSoKy) {
		super(idSinhVien, hoTen, khoa, ngaySinh, gioiTinh, email, soDT, diaChi, diemTB);
		this.tongSoKy = tongSoKy;
	}

	
	public String getTongSoKy() {
		return tongSoKy;
	}

	public void setTongSoKy(String tongSoKy) {
		this.tongSoKy = tongSoKy;
	}
	
}

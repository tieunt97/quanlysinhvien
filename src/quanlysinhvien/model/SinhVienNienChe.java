package quanlysinhvien.model;

public class SinhVienNienChe extends SinhVien{
	private int tongSoKy;
	private int soMonNo;
	
	
	public SinhVienNienChe() {
		super();
	}
	
	public SinhVienNienChe(String idSinhVien, String hoTen, String khoa, String tenLop, String ngaySinh, String gioiTinh, String email,
			String soDT, String diaChi, double diemTB, int tongSoKy, int soMonNo) {
		super(idSinhVien, hoTen, khoa, tenLop, ngaySinh, gioiTinh, email, soDT, diaChi, diemTB);
		this.tongSoKy = tongSoKy;
		this.soMonNo = soMonNo;
	}

	
	public int getTongSoKy() {
		return tongSoKy;
	}

	public void setTongSoKy(int tongSoKy) {
		this.tongSoKy = tongSoKy;
	}

	public int getSoMonNo() {
		return soMonNo;
	}

	public void setSoMonNo(int soMonNo) {
		this.soMonNo = soMonNo;
	}
	
}

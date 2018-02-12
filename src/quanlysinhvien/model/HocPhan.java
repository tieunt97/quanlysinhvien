package quanlysinhvien.model;

public class HocPhan {
	private String idHocPhan;
	private String tenHP;
	private HocPhan hocPhanDK;
	private int soTinChi;
	private double soTCHocPhi;
	private String idNganh;
	private double trongSo;
	
	
	public HocPhan() {
		
	}
	
	public HocPhan(String idHocPhan, String tenHP, int soTinChi, double soTCHocPhi, String idNganh, double trongSo) {
		this.idHocPhan = idHocPhan;
		this.tenHP = tenHP;
		this.soTinChi = soTinChi;
		this.soTCHocPhi = soTCHocPhi;
		this.idNganh = idNganh;
		this.trongSo = trongSo;
	}
	
	public HocPhan(String idHocPhan, String tenHP, int soTinChi, double soTCHocPhi, String idNganh, double trongSo, HocPhan hocPhanDK) {
		this(idHocPhan, tenHP, soTinChi, soTCHocPhi, idNganh, trongSo);
		this.hocPhanDK = hocPhanDK;
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

	public int getSoTinChi() {
		return soTinChi;
	}

	public void setSoTinChi(int soTinChi) {
		this.soTinChi = soTinChi;
	}

	public double getSoTCHocPhi() {
		return soTCHocPhi;
	}

	public void setSoTCHocPhi(double soTCHocPhi) {
		this.soTCHocPhi = soTCHocPhi;
	}

	public String getIdNganh() {
		return idNganh;
	}

	public void setIdNganh(String idNganh) {
		this.idNganh = idNganh;
	}

	public double getTrongSo() {
		return trongSo;
	}

	public void setTrongSo(double trongSo) {
		this.trongSo = trongSo;
	}

	public HocPhan getHocPhanDK() {
		return hocPhanDK;
	}

	public void setHocPhanDK(HocPhan hocPhanDK) {
		this.hocPhanDK = hocPhanDK;
	}

}

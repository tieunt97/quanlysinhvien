package quanlysinhvien.model;

import java.util.ArrayList;

public class Khoa_Vien {

    private ArrayList<LopChuyenNganh> dsLopChuyenNganh;
    private String idKhoa_Vien;
    private String tenKhoa_Vien;

    public Khoa_Vien() {
        this.dsLopChuyenNganh = new ArrayList<>();
    }

    public Khoa_Vien(ArrayList<LopChuyenNganh> dsLopChuyenNganh) {
        this.dsLopChuyenNganh = dsLopChuyenNganh;
    }

    public Khoa_Vien(ArrayList<LopChuyenNganh> dsLopChuyenNganh, String idKhoa_Vien, String tenKhoaVien) {
        this.dsLopChuyenNganh = dsLopChuyenNganh;
        this.idKhoa_Vien = idKhoa_Vien;
        this.tenKhoa_Vien = tenKhoaVien;
    }

    public boolean themLopChuyenNganh(LopChuyenNganh lopCN) {
        if(checkLopChuyenNganh(lopCN.getIdLopChuyenNganh())){
            return false;
        }
        dsLopChuyenNganh.add(lopCN);
        return true;
    }
    public boolean checkLopChuyenNganh(String idLopCN){
        int count = dsLopChuyenNganh.size();
        for(int i=0; i<count; i++){
            if(idLopCN.equalsIgnoreCase(dsLopChuyenNganh.get(i).getIdLopChuyenNganh())){
                return true;
            }
        }
        return false;
    }

    public boolean xoaLopChuyenNganh(String idLopCN) {
        int count = dsLopChuyenNganh.size();
        for (int i = 0; i < count; i++) {
            if (idLopCN.equalsIgnoreCase(dsLopChuyenNganh.get(i).getIdLopChuyenNganh())) {
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

    public String getIdKhoa_Vien() {
        return idKhoa_Vien;
    }

    public void setIdKhoa_Vien(String idKhoaVien) {
        this.idKhoa_Vien = idKhoaVien;
    }

    public String getTenKhoa_Vien() {
        return tenKhoa_Vien;
    }

    public void setTenKhoa_Vien(String tenKhoa_Vien) {
        this.tenKhoa_Vien = tenKhoa_Vien;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlysinhvien.model;

import java.util.ArrayList;

/**
 *
 * @author Trong
 */
public class QuanLyKhoa_Vien {

    private ArrayList<Khoa_Vien> dsKhoa_Vien;

    public QuanLyKhoa_Vien(ArrayList<Khoa_Vien> dsKhoa_Vien) {
        super();
        this.dsKhoa_Vien = dsKhoa_Vien;
    }

    public ArrayList<Khoa_Vien> getDsKhoa_Vien() {
        return dsKhoa_Vien;
    }

    public void setDsKhoa_Vien(ArrayList<Khoa_Vien> dsKhoa_Vien) {
        this.dsKhoa_Vien = dsKhoa_Vien;
    }

    public boolean themKhoa_Vien(Khoa_Vien khoa_Vien) {
        if (checkKhoa_Vien(khoa_Vien.getIdKhoa_Vien())) {
            System.out.println("trùng idKhoa_Vien");
            return false;
        }

        dsKhoa_Vien.add(khoa_Vien);
        return true;
    }

    public boolean xoaKhoa_Vien(String idKhoa_Vien) {
        int count = dsKhoa_Vien.size();
        for (int i = 0; i < count; i++) {
            if (idKhoa_Vien.equalsIgnoreCase(dsKhoa_Vien.get(i).getIdKhoa_Vien())) {
                dsKhoa_Vien.remove(i);
                return true;
            }
        }
        System.out.println("không tìm thấy mã khoa viện");
        return false;
    }

    public boolean checkKhoa_Vien(String idKhoa_Vien) {
        int count = dsKhoa_Vien.size();
        for (int i = 0; i < count; i++) {
            if (idKhoa_Vien.equalsIgnoreCase(dsKhoa_Vien.get(i).getIdKhoa_Vien())) {
                return true;
            }
        }
        return false;
    }
}

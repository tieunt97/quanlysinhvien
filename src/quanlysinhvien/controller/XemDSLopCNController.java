package quanlysinhvien.controller;

import java.util.ArrayList;

import javax.swing.JTable;

import quanlysinhvien.model.LopChuyenNganh;
import quanlysinhvien.view.XemDanhSachLopCNView;

public class XemDSLopCNController {
	private XemDanhSachLopCNView xemDSLopCN;
	private JTable table;
	
	public XemDSLopCNController(XemDanhSachLopCNView xemDSLopCN, ArrayList<LopChuyenNganh> dsLopCN) {
		this.xemDSLopCN = xemDSLopCN;
		this.table = xemDSLopCN.getTable();
		this.xemDSLopCN.loadData(table, dsLopCN);
	}
	
}

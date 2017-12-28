package quanlysinhvien.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JTable;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlysinhvien.model.LopChuyenNganh;
import quanlysinhvien.model.QuanLy;
import quanlysinhvien.view.XemDanhSachLopCNView;

public class XemDSLopCNController {
	private XemDanhSachLopCNView xemDSLopCN;
	private JTable table;
	
	public XemDSLopCNController(XemDanhSachLopCNView xemDSLopCN, QuanLy quanLy) {
		this.xemDSLopCN = xemDSLopCN;
		this.table = xemDSLopCN.getTable();
		xemDSLopCN.loadData(table, quanLy.getDsLopChuyenNganh());
	}
	
}

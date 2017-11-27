package quanlysinhvien.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

import quanlysinhvien.view.CapNhatDiemSVView;

public class CapNhatDiemSVController {
	private JTable table;
	private JTextField tfIdHocPhan, tfIdLopHoc, tfDiemQT, tfDiemThi;
	private JButton btnThem, btnSua, btnXoa, btnHuy;
	private JComboBox<String> hocKyCB;
	private CapNhatDiemSVView capNhatDiemSV;
	
	public CapNhatDiemSVController(CapNhatDiemSVView capNhatDiemSV) {
		this.capNhatDiemSV = capNhatDiemSV;
		this.table = capNhatDiemSV.getTable();
		this.tfIdHocPhan = capNhatDiemSV.getTfIdHocPhan();
		this.tfIdLopHoc = capNhatDiemSV.getTfIdLopHoc();
		this.tfDiemQT = capNhatDiemSV.getTfDiemQT();
		this.tfDiemThi = capNhatDiemSV.getTfDiemThi();
		this.btnThem = capNhatDiemSV.getBtnThem();
		this.btnSua = capNhatDiemSV.getBtnSua();
		this.btnXoa = capNhatDiemSV.getBtnXoa();
		this.btnHuy = capNhatDiemSV.getBtnHuy();
		this.hocKyCB = capNhatDiemSV.getHocKyCB();
		
		setAction();
	}
	
	private void setAction() {
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btnThem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		btnSua.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		btnXoa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		btnHuy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cancel();
			}
		});
		
	}
	
	private void cancel() {
		table.getSelectionModel().clearSelection();
		tfIdHocPhan.setText("");
		tfIdLopHoc.setText("");
		tfDiemQT.setText("");
		tfDiemThi.setText("");
	}
}

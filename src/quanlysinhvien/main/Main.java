package quanlysinhvien.main;

import quanlysinhvien.controller.LoginController;
import quanlysinhvien.view.LoginView;

public class Main {
	public static void main(String[] args) {
		LoginView loginView = new LoginView();
		loginView.setVisible(true);
		loginView.setLocationRelativeTo(null);
		new LoginController(loginView);
	}
}

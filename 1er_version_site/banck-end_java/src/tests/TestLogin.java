package tests;

import java.sql.SQLException;

import services.User;

public class TestLogin {
	public static void main(String in[]) throws SQLException {
		try {
		System.out.println(User.creerSession("anni","1234"));
		//System.out.println(User.creerSession("Namide","Godofwar"));
		}catch(Exception e){
			e.printStackTrace();
		}

		}
}

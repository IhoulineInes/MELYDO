package tests;

import java.sql.SQLException;

import services.User;

public class TestLogOut {
	public static void main(String in[]) throws SQLException {
		try {
		System.out.println(User.deconnexion("k2ZqV1T61jIS9IfpoZo9umLbJ9Vc4"));
		}catch(Exception e){
			e.printStackTrace();
		}

		}
	
}

package tests;

import java.sql.SQLException;


import services.User;

public class TestDeleteUser {
	public static void main(String in[]) throws SQLException {
		try {
		System.out.println(User.suprimerUtilisateur("IAQPNrvidadmWqTwYpTfUzrMd","anni", "1234"));
		}catch(Exception e){
			e.printStackTrace();
		}

		}
}

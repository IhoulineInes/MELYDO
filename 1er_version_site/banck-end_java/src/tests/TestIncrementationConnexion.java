package tests;

import java.sql.SQLException;


import services.User;

public class TestIncrementationConnexion {
	public static void main(String in[]) throws SQLException {
		
		tools.UserTool.incrementerConnexion("Namide");
		tools.UserTool.incrementerConnexion("Istar");
		
		}
}
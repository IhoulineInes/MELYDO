package tests;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bd.Database;

public class TestSQL {
	
	public static void main(String[] args) {
		Connection c=null;
		Statement st=null;
		ResultSet r=null;
		String str="";
		try {
			c=Database.getMySQLConnection();
			st=c.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		String query = "SELECT date from session where id_user ='"+12+"'";
		try {
			r = st.executeQuery(query);
			if(r.next()) {
				str=r.getString("date");
				System.out.println(str);
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
	

}

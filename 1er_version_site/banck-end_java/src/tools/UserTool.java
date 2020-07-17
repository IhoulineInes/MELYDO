package tools;

import org.json.JSONException;
import org.json.JSONObject;

import bd.Database;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class UserTool {

	public static boolean loginExistUser(String login) {

		Connection c=null;
		Statement st=null;
		ResultSet r=null;
//		String str="";
		try {
			c=Database.getMySQLConnection();
			st=c.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		String query = "SELECT * from user where login ='"+login+"'";
		try {
			r = st.executeQuery(query);
			//str=r.getString("login");
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			if(r.next()) {
				return true;
			}else {
				return false;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			r.close();
			st.close();
			c.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return true;

	}

	public static boolean mailExist(String mail) {
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

		String query = "SELECT * from user where mail ='"+mail+"'";
		try {
			r = st.executeQuery(query);
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			if(r.next()) {
				return true;
			}else {
				return false;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			r.close();
			st.close();
			c.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return true;

	}

	public static JSONObject insererUtilisateur(String login, String mdp, String mail, String pref) {
		GregorianCalendar calendar= new GregorianCalendar();
		java.util.Date date= calendar.getTime();
		Connection co =null;
		Statement st=null;
		
		try {
			co = Database.getMySQLConnection();
			st =co.createStatement();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		//Timestamp date=new Timestamp(System.currentTimeMillis());
		String q = "INSERT INTO user VALUES(null,'" + login + "','" + mdp + "','" + mail + "','" + pref + "','" + date + "','" + 0 +"')";

		
		try {
			st.executeUpdate(q);
		} catch (SQLException e) {
			e.printStackTrace();
			return tools.ErrorTool.servicerefused("sql eror", -1);
		}
		try {
			st.close();
			co.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return tools.ErrorTool.servicerefused("sql eror", -1);
		}
		
		
		
		return tools.ErrorTool.serviceaccepted("la creation s'est bien passé",1);
	}

	public static boolean correspandanceLoginMdp(String login, String mdp) {
		Connection co =null;
		Statement st=null;
		ResultSet s=null;
		String mdp_prit=null;
		try {
			co = Database.getMySQLConnection();
			st =co.createStatement();
			String query = "SELECT * from user where login ='"+login+"'";

			s=st.executeQuery(query);
			if(s.next()) {
				mdp_prit=s.getString("mdp");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		if(mdp.equals(mdp_prit)) {
			try {
				co.close();
				st.close();
				s.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return true;
		}else {
			try {
				co.close();
				st.close();
				s.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
	}

	public static String insererSession(String login, String mdp) {
		Connection co =null;
		Statement st=null;
		String cle=creerUneCle();
		int id_user=0;
		GregorianCalendar calendar= new GregorianCalendar();
		java.util.Date date= calendar.getTime();
		id_user=getidFromLogin(login);
		
		
		if((id_user==0)||(id_user==-1)) {
			return null;
		}
		
		//Timestamp date=new Timestamp(System.currentTimeMillis());
		
		try {
			co = Database.getMySQLConnection();
			st =co.createStatement();
			String query="INSERT INTO session VALUES('"+id_user+"','"+date+"','"+cle+"',null)";
			st.executeUpdate(query);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			co.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cle;
		
	}
	

	public static int getidFromLogin(String login) {
		Connection co =null;
		Statement st=null;
		ResultSet s=null;
		int id_user=0;
		String query="SELECT * from user where login='"+login+"' ";
		System.out.println("ok: getidFromLogin");

		try {
			co = Database.getMySQLConnection();
			st =co.createStatement();
			s=st.executeQuery(query);

			if(s.next()) {
				try {
					id_user=s.getInt("id_user");
					co.close();
					st.close();
					s.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				return id_user;
			}else {
				try {
					co.close();
					st.close();
					s.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			co.close();
			st.close();
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	private static String creerUneCle() {
		String chaine="0123456789azertyuiopqsdfghjklmwxcvbnAZERTYUIOPQSDFGHJKLMWXCVBN";
		String cle = "";
		int longeur=(int) (25+(Math.random()*(10)));
		for (int i=0; i<longeur; i++) {
			int r = new Random().nextInt(chaine.length());
			cle += chaine.charAt(r);
		}
		return cle;
	}

	public static JSONObject suprimeSession(String cle) {
		Connection co =null;
		Statement st=null;

		String query="DELETE from session where cle='"+cle+"' ";
		
		try {
			co = Database.getMySQLConnection();
			st =co.createStatement();
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			co.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tools.ErrorTool.serviceaccepted(" fonction suprime session passé", 1);
	}

	
	static int getidFromCle(String cle) {
		
		Connection co =null;
		Statement st=null;
		String query="SELECT * from session WHERE cle='"+cle+"'";
		ResultSet set=null;
		int id_user=0;
		try {
			co = Database.getMySQLConnection();
			st =co.createStatement();
			set=st.executeQuery(query);
			
			if(set.next()) {
				id_user=set.getInt("id_user");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		return id_user;
	}

	
	public static JSONObject suprimeUtilisateur(String log) {
		Connection co =null;
		Statement st=null;

		String query="DELETE from user where login='"+log+"' ";
		
		try {
			co = Database.getMySQLConnection();
			st =co.createStatement();
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			co.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tools.ErrorTool.serviceaccepted(" fonction suprime utilasateur passé", 1);
	}

	public static boolean cleValide(String cle) {
		Connection co =null;
		Statement st=null;
		ResultSet set=null;
		
		String query="SELECT COUNT(*) from session where cle='"+cle+"'";
		try {
			co = Database.getMySQLConnection();
			st =co.createStatement();
			set=st.executeQuery(query);
			if(set.next()) {
				//System.out.println(set.getInt(1));
				if(set.getInt(1)>0) {
					return true;
				}else {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public static boolean LoginPresentDansSession(String login) {
		int id_user=getidFromLogin(login);
		
		Connection co =null;
		Statement st=null;
		ResultSet set=null;
		
		String query="SELECT COUNT(*) from session where id_user='"+id_user+"' ";
		System.out.println("ok: LoginPresentDansSession");
		
		try {
			co = Database.getMySQLConnection();
			st =co.createStatement();
			set=st.executeQuery(query);
			if(set.next()) {
				if(set.getInt(1)>0) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public static String getloginFromCle(String cle) {
		int i=getidFromCle(cle);
		
		Connection co =null;
		Statement st=null;
		ResultSet set=null;
		
		String query="SELECT * from user where id_user='"+i+"' ";
		
		try {
			co = Database.getMySQLConnection();
			st =co.createStatement();
			set=st.executeQuery(query);
			if(set.next()) {
				return set.getString("login");
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static JSONObject recupererDateUser(int id_user){
		String date=null;

		JSONObject temps=new JSONObject();
		Connection co =null;
		Statement st=null;
		ResultSet set=null;
		
		String query="Select date from user where id_user='"+id_user+"'";
		
		try {
			co = Database.getMySQLConnection();
			st =co.createStatement();
			set=st.executeQuery(query);
			if(set.next()) {
				date=set.getString("date");
				temps.put("jour",date.substring(4,7));
				temps.put("jour_date",date.substring(8,10));
				temps.put("heure",date.substring(11,13));
				temps.put("minute",date.substring(14,16));
				temps.put("seconde",date.substring(17,19));
				temps.put("anne",date.substring(24,28));
				
				return temps;
			}
			return tools.ErrorTool.serviceaccepted("apres affichage", 1);
		}catch (SQLException e) {
			e.printStackTrace();
			return tools.ErrorTool.servicerefused("sql error", -1);
		} catch (JSONException e) {
			e.printStackTrace();
			return tools.ErrorTool.servicerefused("Json exception", -1);
		}
		
	}
	
	
	public static JSONObject recupererDateSession(int id_user){
		String date=null;

		JSONObject temps=new JSONObject();
		Connection co =null;
		Statement st=null;
		ResultSet set=null;
		
		String query="Select date from session where id_user_user='"+id_user+"'";
		
		try {
			co = Database.getMySQLConnection();
			st =co.createStatement();
			set=st.executeQuery(query);
			if(set.next()) {
				date=set.getString("date");
				temps.put("jour",date.substring(0,3));
				temps.put("jour_date",date.substring(8,10));
				temps.put("heure",date.substring(11,13));
				temps.put("minute",date.substring(14,16));
				temps.put("seconde",date.substring(17,19));
				temps.put("anne",date.substring(25,29));
				temps.put("mois",date.substring(4,7));
				
				return temps;
			}
			return tools.ErrorTool.serviceaccepted("apres affichage", 1);
		}catch (SQLException e) {
			e.printStackTrace();
			return tools.ErrorTool.servicerefused("sql error", -1);
		} catch (JSONException e) {
			e.printStackTrace();
			return tools.ErrorTool.servicerefused("Json exception", -1);
		}
		
	}

	public static int incrementerConnexion(String login) {
		Connection co =null;
		Statement st=null;
		ResultSet set=null;
		
		int tmp=0;
		
		String query="Select nbr_connexion from user where login='"+login+"'";
		
		try {
			
			co = Database.getMySQLConnection();
			st =co.createStatement();
			set=st.executeQuery(query);
			if(set.next()) {
				tmp=set.getInt("nbr_connexion");
			}
			co.close();
			st.close();
			set.close();
			
			tmp=tmp+1;
			
			query="Update user Set nbr_connexion='"+tmp+"' where user.login='"+login+"'";
			
			co = Database.getMySQLConnection();
			st =co.createStatement();
			st.executeUpdate(query);
			
			co.close();
			st.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			co.close();
			st.close();
			set.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tmp;
		
	}

	public static int nbrInscrits(){
		Connection co =null;
		Statement st=null;
		ResultSet set=null;
		
		int tmp=0;
		
		String query="Select count(*) from user";
			
			try {
				co = Database.getMySQLConnection();
			
			st =co.createStatement();
			set=st.executeQuery(query);
			if(set.next()) {
				tmp=set.getInt(1);
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return tmp;
	}
	
	public static int nbrConnecte() {
		Connection co =null;
		Statement st=null;
		ResultSet set=null;
		
		int tmp=0;
		
		String query="Select count(*) from session";
			
			try {
				co = Database.getMySQLConnection();
			
			st =co.createStatement();
			set=st.executeQuery(query);
			if(set.next()) {
				tmp=set.getInt(1);
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return tmp;
	}
}
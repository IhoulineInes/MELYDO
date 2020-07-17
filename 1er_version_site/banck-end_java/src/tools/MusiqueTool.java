package tools;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.GregorianCalendar;

import org.json.JSONException;
import org.json.JSONObject;

import bd.Database;

public class MusiqueTool {
	
	public static boolean idGenreExist(int id_genre) {
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

		String query = "SELECT * from genre where id_genre ='"+id_genre+"'";
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

	
	public static boolean genreExist(String nom_genre) {
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

		String query = "SELECT * from genre where nom_genre ='"+nom_genre+"'";
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

	
	public static boolean MusiqueExist(String nom_musique, String nom_genre) {
		Connection co =null;
		Statement st=null;
		ResultSet set=null;
		int id_genre = tools.MusiqueTool.getIdFromGenre(nom_genre);
		int id_musique = tools.MusiqueTool.getIDMusique(id_genre,nom_musique);
		
		String query="SELECT COUNT(*) from musique where genre='"+ id_genre +"' AND id_musique='" + id_musique+"' ";
		
		try {
			co = Database.getMySQLConnection();
			st =co.createStatement();
			set=st.executeQuery(query);
			if(set.next()) {
				System.out.println(set.getInt(1));
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
	

	
	public static JSONObject ajoutMusique(String nom_musique, String nom_genre) {
		GregorianCalendar calendar= new GregorianCalendar();
		java.util.Date date= calendar.getTime();
		
		Connection co =null;
		Statement st=null;
		int id_genre = tools.MusiqueTool.getIdFromGenre(nom_genre);
		
		String query = "INSERT INTO musique VALUES(null,'" + id_genre + "','" + nom_musique + "','" + date  +"')";

			
		try {
			co = Database.getMySQLConnection();
			st =co.createStatement();
			st.executeUpdate(query);	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tools.ErrorTool.serviceaccepted("musique ajouté", 1);
	}

	public static String getGenreFromId(int id_genre) {
		Connection co =null;
		Statement st=null;
		ResultSet s=null;
		String genre="";
		String query="SELECT * from genre where id_genre='"+id_genre+"' ";
		System.out.println("ok: getGenreFromId");
		
		try {
			co = Database.getMySQLConnection();
			st =co.createStatement();
			s=st.executeQuery(query);

			if(s.next()) {
				try {
					genre=s.getString("nom_genre");
					co.close();
					st.close();
					s.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				return genre;
			}else {
				try {
					co.close();
					st.close();
					s.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return ">>";
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
		return "<<";
	}



	public static int getIdFromGenre(String nom_genre) {
		Connection co =null;
		Statement st=null;
		ResultSet s=null;
		int id_genre=0;
		String query="SELECT * from genre where nom_genre='"+nom_genre+"' ";
		System.out.println("ok: getGenreFromId");
		
		try {
			co = Database.getMySQLConnection();
			st =co.createStatement();
			s=st.executeQuery(query);

			if(s.next()) {
				try {
					id_genre=s.getInt("id_genre");
					co.close();
					st.close();
					s.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				return id_genre;
			}else {
				try {
					co.close();
					st.close();
					s.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return -2;
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

	public static int getIDMusique(int id_genre,String nom_musique) {
		Connection co =null;
		Statement st=null;
		ResultSet s=null;
		int id_musique=0;
		String query="SELECT * from musique where nom_musique='"+ nom_musique +"' AND genre='"+ id_genre +"' ";
		System.out.println("ok: getIDMusique");
		
		try {
			co = Database.getMySQLConnection();
			st =co.createStatement();
			s=st.executeQuery(query);

			if(s.next()) {
				try {
					id_musique=s.getInt("id_musique");
					co.close();
					st.close();
					s.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				return id_musique;
			}else {
				try {
					co.close();
					st.close();
					s.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return -2;
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
	
	public static JSONObject suprimerMusique(String nom_genre, String nom_musique) {
		Connection co =null;
		Statement st=null;

		
		int id_genre = tools.MusiqueTool.getIdFromGenre(nom_genre);
		int id_musique = tools.MusiqueTool.getIDMusique(id_genre, nom_musique);
		
		
		String query= "DELETE from musique  where id_musique='"+ id_musique +"' AND genre='"+ id_genre +"' ";
		
		try {
			co = Database.getMySQLConnection();
			st =co.createStatement();
			st.executeUpdate(query);
			return tools.ErrorTool.serviceaccepted("musique suprimé", 1);
	
		} catch (SQLException e) {
			e.printStackTrace();
			return tools.ErrorTool.servicerefused("ereure sql", -1);
		}
		
	}



	public static JSONObject listerMusique(String nom_genre) throws JSONException {
		
		int id_genre = tools.MusiqueTool.getIdFromGenre(nom_genre);
		
		Connection co =null;
		Statement st=null;
		ResultSet set=null;
		
		String nom_musique=null;
		String date=null;
		

		JSONObject listMusique=new JSONObject();
		
		String query="SELECT nom_musique, date FROM musique WHERE genre='"+id_genre+"'";
		
			
			try {
				co = Database.getMySQLConnection();
				st =co.createStatement();
				set=st.executeQuery(query);
				while(set.next()) {
					
						nom_musique=set.getString("nom_musique");
						date=set.getString("date");
						listMusique.put(nom_musique, date);

					
				}
			return listMusique;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		return null;
	}
	
	
	public static boolean existeLike(String cle, String nom_genre, String nom_musique) {
		Connection co = null;
		Statement st = null;
		ResultSet set = null;
		
		
		int id_user = tools.UserTool.getidFromCle(cle);
		int id_genre = tools.MusiqueTool.getIdFromGenre(nom_genre);
		int id_musique = tools.MusiqueTool.getIDMusique(id_genre, nom_musique);
		
		
		String query="SELECT id_musique from `like` where id_musique='"+ id_musique +"' AND id_user='"+ id_user +"' ";
				
				
		try {
			co = Database.getMySQLConnection();
			st =co.createStatement();
			set=st.executeQuery(query);
			if(set.next()) {
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


	public static JSONObject ajoutLike(String cle,String nom_genre,String nom_musique) {
		GregorianCalendar calendar= new GregorianCalendar();
		java.util.Date date= calendar.getTime();
		
		Connection co =null;
		Statement st=null;
		
		int id_user = tools.UserTool.getidFromCle(cle);
		int id_genre = tools.MusiqueTool.getIdFromGenre(nom_genre);
		int id_musique = tools.MusiqueTool.getIDMusique(id_genre, nom_musique);
		
		
		
		String query="INSERT INTO `like` VALUES('"+id_user+"','"+id_musique+"','"+date+"' )";
		
		try {
			co = Database.getMySQLConnection();
			st =co.createStatement();
			st.executeUpdate(query);	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tools.ErrorTool.serviceaccepted("like ajouté", 1);
	}
	
	public static JSONObject suprimerLike(String cle,String nom_genre,String nom_musique) {
		Connection co =null;
		Statement st=null;


		int id_user = tools.UserTool.getidFromCle(cle);
		int id_genre = tools.MusiqueTool.getIdFromGenre(nom_genre);
		int id_musique = tools.MusiqueTool.getIDMusique(id_genre, nom_musique);
		
		
		String query= "DELETE from `like`  where id_musique='"+ id_musique +"' AND id_user='"+ id_user +"' ";
		
		try {
			co = Database.getMySQLConnection();
			st =co.createStatement();
			st.executeUpdate(query);
			return tools.ErrorTool.serviceaccepted("like suprimé", 1);
	
		} catch (SQLException e) {
			e.printStackTrace();
			return tools.ErrorTool.servicerefused("ereure sql", -1);
		}
		
	}
	
public static JSONObject listerLike(String cle) throws JSONException {
		
		int id_user = tools.UserTool.getidFromCle(cle);
		
		Connection co =null;
		Statement st=null;
		ResultSet set=null;
		
		String nom_musique=null;
		String date=null;
		

		JSONObject listMusique=new JSONObject();
		
		String query="SELECT m.nom_musique, g.nom_genre FROM musique m INNER JOIN `like` l ON m.id_musique = l.id_musique INNER JOIN genre g ON g.id_genre=m.genre WHERE l.id_user='"+id_user+"'";
		
		
		
			
			try {
				co = Database.getMySQLConnection();
				st =co.createStatement();
				set=st.executeQuery(query);
				while(set.next()) {
					
						nom_musique=set.getString("nom_musique");
						date=set.getString("date");
						listMusique.put(nom_musique, date);

					
				}
			return listMusique;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		return null;
	}
	
}

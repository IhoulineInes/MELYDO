package tests;


import java.sql.*;

import org.json.JSONObject;

import bd.Database;
import services.Musique;
import services.User;
import tools.MusiqueTool;

public class TestsCreateUser{

	public static void main(String in[]) throws SQLException {
		try {
		//System.out.println(User.creerutilisateur("arot","123456","arot@mail.com","rock"));
		//System.out.println(User.creerutilisateur("Aslan","123456","aslan@mail.com","jazz"));
		//System.out.println(User.creerutilisateur("Ines","Ihouline","Gateau","cend","ines.ihouline@mail.com"));
		//System.out.println(User.creerutilisateur("Anissa","bensilman","Istar","Algerie","ani.ben@mail.fr"));
		//System.out.println(User.creerutilisateur("Aslan","Ozdoev","Vaynax","bpat","aslan.ozdoev@mail.com"));
		//System.out.println(Musique.ajouterMusique("Classique","musique8"));
		//System.out.println(MusiqueTool.getIDMusique(1,"musique1"));
		//System.out.println(Musique.suprimerMusique("Classique", "musique3"));
		//System.out.println(MusiqueTool.suprimerMusique("", nom_genre))
		//System.out.println(Musique.ListerMusiqueParGenre("Classique"));
			
		//System.out.println(Musique.ajouterLike("GzoqRW03XETLnkMjsnav2UVoB22", "Classique", "musique3"));
		//System.out.println(MusiqueTool.existeLike("GzoqRW03XETLnkMjsnav2UVoB22", "Classique", "musique3"));
		}catch(Exception e){
			e.printStackTrace();
		}

		}

}
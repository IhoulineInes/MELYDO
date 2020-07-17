package services;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class Musique {

	public static JSONObject ajouterMusique(String nom_genre, String nom_musique) {
		
		if(!tools.MusiqueTool.genreExist(nom_genre)) {
			return tools.ErrorTool.servicerefused("genre inexistant", -1);
		}
		
		if(tools.MusiqueTool.MusiqueExist( nom_musique, nom_genre)) {
			return tools.ErrorTool.servicerefused("musique existante", -1);
		}
		
		return tools.MusiqueTool.ajoutMusique(nom_musique,nom_genre);
		//return null;
	}

	public static JSONObject suprimerMusique(String nom_genre, String nom_musique) {
		
		if(!tools.MusiqueTool.genreExist(nom_genre)) {
			return tools.ErrorTool.servicerefused("genre invalide", -1);
		}
		
		
		if(!tools.MusiqueTool.MusiqueExist(nom_musique, nom_genre)) {
			return tools.ErrorTool.servicerefused("musique ciblé inexistant", -1);
		}
		
		return tools.MusiqueTool.suprimerMusique(nom_genre,nom_musique);
	}

	public static JSONObject ListerMusiqueParGenre(String nom_genre) throws JSONException {
		if(!tools.MusiqueTool.genreExist(nom_genre)) {
			return tools.ErrorTool.servicerefused("session invalide", -1);
		}
		

		return tools.MusiqueTool.listerMusique(nom_genre);

	}
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////	
	

	public static JSONObject ajouterLike(String cle, String nom_genre, String nom_musique) {
		if(!tools.UserTool.cleValide(cle)) {
			return tools.ErrorTool.servicerefused("session invalide", -1);
		}
		
		if(!tools.MusiqueTool.genreExist(nom_genre)) {
			return tools.ErrorTool.servicerefused("genre ciblé inexistant", -1);
		}
		
		if(!tools.MusiqueTool.MusiqueExist(nom_musique, nom_genre)) {
			return tools.ErrorTool.servicerefused("musique ciblé inexistant", -1);
		}
		
		
		if(tools.MusiqueTool.existeLike(cle,nom_genre,nom_musique)) {
			return tools.ErrorTool.servicerefused("amitié existant",-1);
			
		}
		
		return tools.MusiqueTool.ajoutLike(cle,nom_genre,nom_musique);
		
	}

	public static JSONObject suprimerLike(String cle, String nom_genre, String  nom_musique) {
		if(!tools.UserTool.cleValide(cle)) {
			return tools.ErrorTool.servicerefused("session invalide", -1);
		}

		String demandeur=tools.UserTool.getloginFromCle(cle);
		
		if(!tools.MusiqueTool.existeLike(cle,nom_genre,nom_musique)) {
			return tools.ErrorTool.servicerefused("amitié inexistant",-1);
			
		}
		return tools.MusiqueTool.suprimerLike(cle, nom_genre, nom_musique);
	}

	public static JSONObject ListerLike(String cle) throws JSONException {
		if(!tools.UserTool.cleValide(cle)) {
			return tools.ErrorTool.servicerefused("session invalide", -1);
		}
		
		String log=tools.UserTool.getloginFromCle(cle);

		return tools.MusiqueTool.listerLike(cle);

	}
	


}
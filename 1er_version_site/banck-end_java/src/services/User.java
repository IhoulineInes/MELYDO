package services;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
public static JSONObject deconnexion(String cle) {
		
		if(!tools.UserTool.cleValide(cle)) {
			return tools.ErrorTool.servicerefused("session invalide", -1);
		}
		return tools.UserTool.suprimeSession(cle);
	}





public static JSONObject creerSession(String login,String mdp) {
	JSONObject o=new JSONObject();
	if(!tools.UserTool.loginExistUser(login)) {
		return tools.ErrorTool.servicerefused("login inexistant",-1);
	}
	
	if(!tools.UserTool.correspandanceLoginMdp(login,mdp)) {
		return tools.ErrorTool.servicerefused("mot de passe ne correspand pas",-1);
	}
	
	if(tools.UserTool.LoginPresentDansSession(login)) {
		return tools.ErrorTool.servicerefused("login '"+login+"' deja connecté", -1);
	}
	String cle=tools.UserTool.insererSession(login,mdp);
	System.out.println(">>>>>>>>>>>>>>>>>");
	System.out.println(cle);
	if((cle)!=null) {
		try {
			int nbr_connexion=tools.UserTool.incrementerConnexion(login);
			o.put("nbr_connexion", nbr_connexion);
			int nbr_inscrit=tools.UserTool.nbrInscrits();
			o.put("nbr_inscrits", nbr_inscrit);
			int nbr_connecte=tools.UserTool.nbrConnecte();
			o.put("nbr_connecte", nbr_connecte);
			//int nbr_message=tools.MessageTool.nbr_message(login);
			//o.put("nbr_message", nbr_message);
			//int nbr_message_commente=tools.MessageTool.nbr_message_commente(login);
			//o.put("nbr_message_commente",nbr_message_commente);
			o.put("login",login);
			o.put("session",tools.ErrorTool.serviceaccepted(cle, 1));
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		return o;
	}else {
		return tools.ErrorTool.servicerefused("creation de session echoué", -1);
	}
	}


public static JSONObject suprimerUtilisateur(String cle,String log, String mdp) {
	if(log == null || mdp == null){
		return tools.ErrorTool.servicerefused("mauvais parametres", -1);
	}
	
	if(!tools.UserTool.correspandanceLoginMdp(log, mdp)) {
		return tools.ErrorTool.servicerefused("login ne correspond pas au mdp", -1);
	}
	
	if(!tools.UserTool.loginExistUser(log)) {
		return tools.ErrorTool.servicerefused("login existe pas", -1);
	}
	tools.UserTool.suprimeSession(cle);
	return (tools.UserTool.suprimeUtilisateur(log));

}



public static JSONObject creerutilisateur(String login,String mdp,String mail, String pref) {
	
	if((login==null)||(mdp==null)||(mail==null)||(pref==null)) {
		return tools.ErrorTool.servicerefused("Mauvais argument(s)",-1);
	}
	
	
	boolean is_user=tools.UserTool.loginExistUser(login);
	if(is_user) {
		return tools.ErrorTool.servicerefused("login existant",-1);
	}
	
	
	boolean is_mail=tools.UserTool.mailExist(mail);
	if(is_mail) {
		return tools.ErrorTool.servicerefused("mail existant ",-1);
	}
	
	return tools.UserTool.insererUtilisateur(login,mdp,mail,pref);
}

}

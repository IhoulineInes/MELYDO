package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class AjouterLike extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {

		String cle=req.getParameter("cle");
		String nom_genre=req.getParameter("nom_genre");
		String nom_musique=req.getParameter("nom_musique");

		
		
		JSONObject rep=new JSONObject();
				
		rep=services.Musique.ajouterLike(cle, nom_genre, nom_musique);
				

		res.setContentType("text/json");
		PrintWriter out = res.getWriter();
		
	}

}
package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class SuprimerMusique extends HttpServlet {
	public void doDelete(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
			
			String nom_genre=req.getParameter("genre");
			String nom_musique=req.getParameter("musique");
			
			
			JSONObject rep=new JSONObject();
			
			rep=services.Musique.suprimerMusique(nom_genre, nom_musique);
			
			res.setContentType("text/json");
			PrintWriter out = res.getWriter();

			out.println(rep);
			
		}
}

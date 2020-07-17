package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class ListMusique extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
			
			String nom_genre=req.getParameter("nom_genre");
			
			JSONObject rep=new JSONObject();
			
			try {
				rep=services.Musique.ListerMusiqueParGenre(nom_genre);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			res.setContentType("text/json");
			PrintWriter out = res.getWriter();

			out.println(rep);
			
		}
}
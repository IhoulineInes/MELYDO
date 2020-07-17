package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateUser extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		
		String log=req.getParameter("log");
		String mdp=req.getParameter("mdp");
		String mail=req.getParameter("mail");
		String pref=req.getParameter("pref");
		
		JSONObject rep=new JSONObject();
		
		rep=services.User.creerutilisateur(log,mdp,mail,pref);
		res.setContentType("text/json");
		PrintWriter out = res.getWriter();

		out.println(rep);
		
	}

}
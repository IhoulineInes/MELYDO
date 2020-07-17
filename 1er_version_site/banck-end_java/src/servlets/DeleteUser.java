package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class DeleteUser extends HttpServlet {

	public void doDelete(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		
		String log=req.getParameter("log");
		String mdp=req.getParameter("mdp");
		String cle=req.getParameter("cle");
		
		JSONObject rep=new JSONObject();
				
		rep=services.User.suprimerUtilisateur(cle,log,mdp);
		
		res.setContentType("text/json");
		PrintWriter out = res.getWriter();

		out.println(rep);
		
	}

}
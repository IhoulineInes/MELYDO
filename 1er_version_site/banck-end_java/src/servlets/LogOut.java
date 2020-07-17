package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class LogOut extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		
		String cle=req.getParameter("cle");
		JSONObject rep=new JSONObject();
				
		rep=services.User.deconnexion(cle);
		
		res.setContentType("text/json");
		PrintWriter out = res.getWriter();

		out.println(rep);
		
	}

}
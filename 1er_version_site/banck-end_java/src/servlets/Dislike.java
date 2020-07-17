package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class Dislike extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		
		/*
		 
		String key=req.getParameter("key");
		String id_com=req.getParameter("id_com");

		
		
		JSONObject rep=new JSONObject();
				
		rep=services.Messages.Dislike(key,id_com);

		*/
		res.setContentType("text/json");
		PrintWriter out = res.getWriter();

		out.println("methode non prete");
		
	}

}
package tests;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.*;

public class Adition extends HttpServlet {

	public void doGet(HttpServletRequest requete, HttpServletResponse reponse)
		throws ServletException, IOException {
		
		
		String sa=requete.getParameter("a");
		String sb=requete.getParameter("b");
		

		int a=Integer.parseInt(sa);
		int b=Integer.parseInt(sb);
		
		
		int tot=a+b;
		
		
		
		reponse.setContentType("text/plain");
		PrintWriter out = reponse.getWriter();
		
		out.println(tot);
		
	}
	

}
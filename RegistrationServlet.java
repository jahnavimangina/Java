package com.assignment.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.assignment.dao.DBConnection;
import com.assignment.domain.Registration;

@WebServlet({ "/RegistrationServlet", "/rs" })
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Registration reg = new Registration();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		//out.println("welcome to servlets");
		String fname = request.getParameter("firstname");
		// setting retrieved values into RegistrationPojo class variables
		reg.setFirstName(fname);
		String lname = request.getParameter("lastname");
		reg.setLastName(lname);
		String email = request.getParameter("email");
		reg.setEmail(email);
		System.out.println(request.getParameter("dob"));
		// covert util date to sql date to store it n database
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			java.util.Date date = formatter.parse(request.getParameter("dob"));
			java.sql.Date birthdate = new java.sql.Date(date.getTime());
			reg.setBirthdate(birthdate);
			System.out.println("date converted to sql date " + birthdate);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		String gender = request.getParameter("gender");
		reg.setGender(gender);
		Long phone = Long.parseLong(request.getParameter("phone"));
		reg.setPhone(phone);
		String password = request.getParameter("password");
		reg.setPassword(password);
		String verifyPassword = request.getParameter("verify_password");
		reg.setVerifyPassword(verifyPassword);
		
		DBConnection connect = new DBConnection();
		
		if(connect.addUser(reg))
		{
			System.out.println("new user is inserted");
    	  	request.setAttribute("message", "user added successfully!");
	        RequestDispatcher rd=request.getRequestDispatcher("jsp/success.jsp");
			rd.forward(request,response);
		}
		
		out.close();
	}

}

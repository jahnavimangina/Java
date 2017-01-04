package com.assignment.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.assignment.domain.Registration;

public class DBConnection {

	public boolean addUser(Registration regUser) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// jdbc:oracle:thin:@//myHost:1521/service_name
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "webstore", "1992");
			System.out.println("connection established	");
			PreparedStatement _pst = con.prepareStatement("select email from user_info");
			ResultSet rs = _pst.executeQuery();

			if (rs.next()) {
				String e_mail = rs.getString("email");
				if ((e_mail.equals(regUser.getEmail()))) {
					System.out.println("user already registered");
				} else {
					PreparedStatement pst = con.prepareStatement(
							"insert into user_info(first_name,last_name,email,birth_date,gender,phone,password,verify_password) values(?,?,?,?,?,?,?,?)");
					pst.setString(1, regUser.getFirstName());
					pst.setString(2, regUser.getLastName());
					pst.setString(3, regUser.getEmail());
					pst.setDate(4, regUser.getBirthdate());
					pst.setString(5, regUser.getGender());
					pst.setLong(6, regUser.getPhone());
					pst.setString(7, regUser.getPassword());
					pst.setString(8, regUser.getVerifyPassword());

					if (pst.executeUpdate() == 1) {
						System.out.println("inserted new user");
						return true;
					} else {
						System.out.println("not inserted user");
						return false;
					}
				}
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

}

package org.jsp.userapp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class CreateUserTable {
	public static void main(String[] args) throws IOException {
		Connection con = null;
		Statement st = null;
		String qry = "create table user(id int not null,name varchar(45) not null,phone bigint(20)not null unique,email varchar(45) unique not null,password varchar(45) not null,primary key(id))";
		InputStream stream = new FileInputStream("userapp.properties");
		Properties p = new Properties();
		p.load(stream);
		try {
			Class.forName(p.getProperty("driverClass"));
			con = DriverManager.getConnection(p.getProperty("url"), p);
			st = con.createStatement();
			st.execute(qry);
			System.out.println("User table created");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

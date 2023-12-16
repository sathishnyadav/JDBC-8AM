package org.jsp.userapp;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class UserCRUD {
	static Connection con;
	static PreparedStatement pst;
	static ResultSet rs;
	static Scanner sc = new Scanner(System.in);
	static {
		try {
			FileInputStream stream = new FileInputStream("userapp.properties");
			Properties p = new Properties();
			p.load(stream);
			con = DriverManager.getConnection(p.getProperty("url"), p);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SQLException {
		boolean b = true;
		while (b) {
			System.out.println("1.Save User");
			System.out.println("2.Update User");
			System.out.println("3.Find User By Id");
			System.out.println("4.Verify User By Phone and password");
			System.out.println("5.Verify User By Email and password");
			System.out.println("6.Delete User");
			System.out.println("7.Exit");
			int choice = sc.nextInt();
			switch (choice) {
			case 1: {
				save();
				break;
			}
			case 2: {
				update();
				break;
			}
			case 3: {
				findById();
				break;
			}
			case 4: {
				verifyByPhoneAndPassword();
				break;
			}
			case 5: {
				verifyByEmailAndPassword();
				break;
			}
			case 6: {
				delete();
				break;
			}
			case 7: {
				exit();
				b = false;
				System.out.println("Thank You!!!");
			}
			default: {
				System.err.print("Invalid Choice");
				exit();
				System.out.println("Thank You!!!");
			}
			}

		}
	}

	public static void save() {
		System.out.println("Enter the Id,name,phone,email and password to save user");
		int id = sc.nextInt();
		String name = sc.next();
		long phone = sc.nextLong();
		String email = sc.next();
		String password = sc.next();
		try {
			pst = con.prepareStatement("insert into user values(?,?,?,?,?)");
			pst.setInt(1, id);
			pst.setString(2, name);
			pst.setLong(3, phone);
			pst.setString(4, email);
			pst.setString(5, password);
			pst.executeUpdate();
			System.out.println("user saved");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void update() {
		System.out.println("Enter the Id,name,phone,email and password to update user");
		int id = sc.nextInt();
		String name = sc.next();
		long phone = sc.nextLong();
		String email = sc.next();
		String password = sc.next();
		try {
			pst = con.prepareStatement("update user set name=?,phone=?,email=?,password=? where id=?");
			pst.setInt(5, id);
			pst.setString(1, name);
			pst.setLong(2, phone);
			pst.setString(3, email);
			pst.setString(4, password);
			pst.executeUpdate();
			System.out.println("user Updated");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void verifyByPhoneAndPassword() {
		System.out.println("Enter the Phone Number and password to verify user");
		long phone = sc.nextLong();
		String password = sc.next();
		try {
			pst = con.prepareStatement("select * from user where phone=? and password=?");
			pst.setLong(1, phone);
			pst.setString(2, password);
			rs = pst.executeQuery();
			if (rs.next()) {
				System.out.println("verification Succesfull");
				System.out.println("User Id:" + rs.getInt("id"));
				System.out.println("User name:" + rs.getString("name"));
				System.out.println("Phone Number:" + rs.getLong("phone"));
				System.out.println("Email Id:" + rs.getString("email"));
			} else {
				System.err.println("Invalid Phone Number or Password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void verifyByEmailAndPassword() {
		System.out.println("Enter the Email Id and password to verify user");
		String email = sc.next();
		String password = sc.next();
		try {
			pst = con.prepareStatement("select * from user where email=? and password=?");
			pst.setString(1, email);
			pst.setString(2, password);
			rs = pst.executeQuery();
			if (rs.next()) {
				System.out.println("verification Succesfull");
				System.out.println("User Id:" + rs.getInt("id"));
				System.out.println("User name:" + rs.getString("name"));
				System.out.println("Phone Number:" + rs.getLong("phone"));
				System.out.println("Email Id:" + rs.getString("email"));
			} else {
				System.err.println("Invalid Email id or Password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void findById() {
		System.out.println("Enter the user id to display details");
		int id = sc.nextInt();
		try {
			pst = con.prepareStatement("select * from user where id=?");
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				System.out.println("User Found");
				System.out.println("User Id:" + rs.getInt("id"));
				System.out.println("User name:" + rs.getString("name"));
				System.out.println("Phone Number:" + rs.getLong("phone"));
				System.out.println("Email Id:" + rs.getString("email"));
			} else {
				System.err.println("Invalid Id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void delete() {
		System.out.println("Enter the user id to display details");
		int id = sc.nextInt();
		try {
			pst = con.prepareStatement("delete from user where id=?");
			pst.setInt(1, id);
			int r = pst.executeUpdate();
			if (r == 1)
				System.out.println("user deleted");
			else
				System.err.println("Cannot delete as Id is invallid");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void exit() throws SQLException {
		if (con != null)
			con.close();
		if (pst != null)
			pst.close();
		if (rs != null)
			rs.close();
		System.out.println("Closed all costly resources");
	}
}

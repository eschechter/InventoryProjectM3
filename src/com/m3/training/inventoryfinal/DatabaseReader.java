package com.m3.training.inventoryfinal;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class DatabaseReader {

	private String db_url;
	private String username;
	private String pass;
	private String table;
	private String name_col;
	private String stock_col;
	
	private Connection conn;
	private Statement stmt;

	 public DatabaseReader() {
		loadProperties();
	}
	
	public void loadProperties() {
		try {

			InputStream input = getClass().getClassLoader().getResourceAsStream("connection.properties");

			// load a properties file
			Properties prop = new Properties();
			prop.load(input);

			
			db_url = prop.getProperty("db_url");
			username = prop.getProperty("username");
			pass = prop.getProperty("password");
			table = prop.getProperty("table");
			name_col = prop.getProperty("name_col");
			stock_col = prop.getProperty("stock_col");
			conn = DriverManager.getConnection(db_url, username, pass);
			stmt = conn.createStatement();

		} catch (IOException ex) {
			ex.printStackTrace();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public ArrayList<Item> readDatabase() {

		// STEP 2: Register JDBC driver
		try {
			ResultSet rs = null;
			String sql = "SELECT " + name_col + ", " + stock_col + " FROM " + table;
			if (stmt.execute(sql)) {
				rs = stmt.getResultSet();
			} else {
				System.out.println("bad resultset");
			}

			ArrayList<Item> itemList = new ArrayList<>();

			while (rs.next()) {
				String name = rs.getString(name_col);
				int count = rs.getInt(stock_col);
				Item toAdd = new Item(name, count);
				itemList.add(toAdd);
			}
			
			return itemList;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static void main(String[] args) {
		DatabaseReader dr = new DatabaseReader();
		ArrayList<Item> i = dr.readDatabase();
		System.out.println(i);
	}
}

package com.m3.training.inventoryfinal;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseWriter {

	private String db_url;
	private String username;
	private String pass;
	private String table_name;
	private String name_col;
	private String stock_col;

	private Connection conn;
	private Statement stmt;

	public DatabaseWriter() {
		loadProperties();
	}

	public void loadProperties() {
		try {

			InputStream input = getClass().getClassLoader().getResourceAsStream("connection.properties");

			// load a properties file
			Properties prop = new Properties();
			prop.load(input);

			table_name = prop.getProperty("table");
			name_col = prop.getProperty("name_col");
			stock_col = prop.getProperty("stock_col");
			db_url = prop.getProperty("db_url");
			username = prop.getProperty("username");
			pass = prop.getProperty("password");
			conn = DriverManager.getConnection(db_url, username, pass);
			stmt = conn.createStatement();

		} catch (IOException ex) {
			ex.printStackTrace();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void changeStock(String name, int newStock) {
		String parsedName = "'" + name + "'";
		String sql = "UPDATE " + table_name + " SET " + stock_col + " = " + newStock + " WHERE " + name_col + " = "
				+ parsedName;

		try {
			if (stmt.execute(sql)) {
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addItem(String name, int count) {
		String parsedName = "'" + name + "'";
		String sql = "INSERT INTO " + table_name + " VALUES " + "(" + parsedName + ", " + count + ')';
		try {
			if (stmt.execute(sql)) {
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void wipeTable() {
		String sql = "TRUNCATE TABLE " + table_name;
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		DatabaseWriter dw = new DatabaseWriter();
		dw.wipeTable();
	}
}

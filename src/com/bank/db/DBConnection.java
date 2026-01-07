package com.bank.db;
import java.sql.Connection;
import java.sql.DriverManager;
public class DBConnection 
{
	//Database URL
	private static final String URL="jdbc:mysql://localhost:3306/bankdb";
	
	//MySQL username
	private static final String USER="root";
	
	//MySQL password
	private static final String PASSWORD="Karthika@123sakthi";
	
	//database connected method
	public static Connection getConnection()
	{
		try
		{
			//MySQL driver load
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Connection create
			Connection con= DriverManager.getConnection(URL, USER, PASSWORD);
			return con;//success
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;//fail
		}
	}
	
}
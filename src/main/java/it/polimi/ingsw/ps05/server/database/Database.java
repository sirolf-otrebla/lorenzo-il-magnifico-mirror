package it.polimi.ingsw.ps05.server.database;

import java.sql.*;

public class Database {
	private static Database instance = null;
	
	Connection c;
	private Database() {
		try {
			c = DriverManager.getConnection("jdbc:sqlite:data.sqlite");
			c.setAutoCommit(true);
			System.out.println("Opened database successfully");
			//"CREATE TABLE 'data' ('ID' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, 
			//'Username' VARCHAR(16) NOT NULL UNIQUE, 
			//'Password' VARCHAR(20) NOT NULL, 
			//'DataAdded' TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
			//'GamePlayed' INTEGER NOT NULL DEFAULT 0, 
			//'GameWon' INTEGER NOT NULL DEFAULT 0, 
			//'GameTime' DOUBLE NOT NULL DEFAULT 0)";

			
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}
	
	public static Database getInstance(){
		if (instance == null){
			instance = new Database();
		}
		return instance;
	}

	public boolean registerNewUser(String username, String password){
		try {
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO 'data' (Username,Password) VALUES ('"+ username + "', '" + password + "')";
			stmt.executeUpdate(sql);
		} catch (Exception e){
			System.out.println("aggiunta fallita");
			return false;
		}

		return true;
	}

	public boolean checkUser(String username, String password){
		try {
			Statement stmt = c.createStatement();
			String sql = "SELECT  Username,Password FROM data WHERE (Username ='"+ username + "' AND Password='" + password + "')";
			ResultSet rs = stmt.executeQuery(sql);
			if (!rs.next()){
				System.out.println("non presente");
				return false;
			}
		} catch (Exception e){
			System.out.println("selezione fallita");
			e.printStackTrace();
			return false;
		}
		System.out.println("presente");
		return true;
	}
	
	public void updateGameStats(String username, boolean gameWon, int seconds){
		try {
			Statement stmt = c.createStatement();
			String sql = "UPDATE data SET GamePlayed = GamePlayed + 1, GameWon = GameWon + " + (gameWon==true ? 1:0) + " ";
			stmt.executeUpdate(sql);
		} catch (Exception e){
			System.out.println("aggiunta fallita");
		}
	}
	
	private void printDatabase(){
		Statement stmt;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM data;");

			while ( rs.next() ) {
				System.out.println( "ID = " + rs.getInt("ID") );
				System.out.println( "USR = " + rs.getString("Username") );
				System.out.println( "PSW = " + rs.getString("Password") );
				System.out.println( "GamePlayed = " + rs.getInt("GamePlayed") );
				System.out.println( "GameWon = " + rs.getInt("GameWon") );
				System.out.println( "GameLost = " + (rs.getInt("GamePlayed") - rs.getInt("GameWon") ));
				System.out.println( "GameTime = " + rs.getDouble("GameTime") );
				System.out.println();
			}
		} catch (SQLException e) {
			System.out.println("stampa fallita");
		}

	}
}

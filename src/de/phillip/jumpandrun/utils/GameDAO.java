package de.phillip.jumpandrun.utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GameDAO {
	
	private static GameDAO gameDAO;
	
	private String connectionURL = "jdbc:derby:C:/Users/phill/derby_db/jumpdb";
	private Connection connection;
	private Statement statement;
	
	private GameDAO() {
		if (Files.isDirectory(Paths.get("C:/Users/phill/derby_db/jumpdb"))) {
			createConnection();
		} else {
			createDataBase();
		}
	}

	public static GameDAO getInstance() {
		if (gameDAO == null) {
			gameDAO = new GameDAO();
		}
		return gameDAO;
	}
	
	private void createDataBase() {
		try {
			String url = connectionURL + ";create=true";
			connection = DriverManager.getConnection(url);
			statement = connection.createStatement();
			statement.execute("CREATE TABLE playerStatus (name varchar(16), score integer, primary key(name))");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void createConnection() {
		try {
			connection = DriverManager.getConnection(connectionURL);
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setScore(int score) {
		try {
			ResultSet rs = statement.executeQuery("SELECT * FROM playerStatus WHERE name = 'Player 1'");
			if(!rs.next()) {
				statement.executeUpdate("INSERT INTO playerStatus (name, score) VALUES ('Player 1', " + score + ")");
			} else {
				statement.executeUpdate("UPDATE playerStatus SET score = " + score + " WHERE name = 'Player 1'");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getScore() {
		try {
			ResultSet rs = statement.executeQuery("SELECT score FROM playerStatus WHERE name = 'Player 1'");
			if (!rs.next()) {
				return 0;
			} else {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}

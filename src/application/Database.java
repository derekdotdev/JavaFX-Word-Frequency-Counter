package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/** The Database class will be home to all methods which  
 *  pertain to the local MySQL database for this project.
 *  @author derekdileo */
public class Database {

	// Declare variable
	protected static Connection conn;
	
	/** Method establishes a connection with local MySQL database
	 *  @return returns a database Connection to the caller
	 *  @throws Exception */
	public static Connection getConnection() throws Exception {
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/word_occurrences"; 
//			String url = "jdbc:mysql://24.196.52.166:3306/database_name"; used for an online db
			String username = "root";
			String password = "rootpassword";
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
			return conn;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
 		} 
		// Return null if not successful
		return null;
	}
	
	/** Method creates the standard words table within the database (if it does not exist already).
	 *  @param tableName is the desired name for database table to be created and is only used for testing purposes- 
	 *  otherwise, this would be hard-coded.
	 *  @throws Exception */
	public static void createWordsTable(String tableName) throws Exception {
		try {
			// Establish a connection
			conn = getConnection();
			
			// Create PreparedStatement and Execute
			String create = "CREATE TABLE IF NOT EXISTS " + tableName + " (word varchar(255) NOT NULL UNIQUE, frequency int NOT NULL, PRIMARY KEY(word))";
			PreparedStatement pstmt = conn.prepareStatement(create);
 		 	pstmt.executeUpdate();
 		 	
 		 	// Close the connection
			conn.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			System.out.println("The Method: createTable() is complete!");
		}
		
	}
	
	/** Method drops a table within the database (if it exists).
	 *  @param tableName is the name of the table to be deleted.
	 *  @throws Exception */
	public static void deleteTable(String tableName) throws Exception {
		try {
			// Establish a connection
			conn = getConnection();
			
			// Create PreparedStatement and Execute
			String delete = "DROP TABLE IF EXISTS " + tableName + "";
			PreparedStatement pstmt = conn.prepareStatement(delete);
 		 	pstmt.executeUpdate();
 		 	
 		 	// Close the connection
			conn.close();
		} catch(Exception e) {
			System.out.println("Error in Database.deleteTable(): " + e.getMessage());
			e.printStackTrace();
		} finally {
			System.out.println("The Method: deleteTable() is complete!");
		}
		
	}
	
	/** Method posts (inserts) desired word and frequency values into the words table
	 *  @param word is the desired word to be posted to the words table
	 *  @param frequency is the frequency of occurrence of the word in our program 
	 *  @throws Exception */
	public static void post(String word, int frequency) throws Exception {
		try {
			conn = getConnection();
			String post = "INSERT INTO words (word, frequency) VALUES ('" + word +"', '" + frequency + "')";
			PreparedStatement pstmt = conn.prepareStatement(post);
			pstmt.executeUpdate();
			conn.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		
	}
	
	/** Method deletes desired word and frequency values from the words table
	 *  @param word is the desired word to be removed from the words table
	 *  @throws Exception */
	public static void delete(String word) throws Exception {
		try {
			conn = getConnection();
			String post = "DELETE FROM words WHERE word = '" + word + "'";
			PreparedStatement pstmt = conn.prepareStatement(post);
			pstmt.executeUpdate();
			conn.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		
	}
	
	/** Method updates the frequency (occurrences) of the selected word in the table
	 *  @param word is the target key whose frequency is to be updated
	 *  @param frequency is the new frequency of occurrence of the word in our program 
	 *  @throws Exception */
	public static void update(String word, int frequency) {
		try {
			conn = getConnection();
			String update = "UPDATE words SET frequency = " + frequency + " WHERE word  = '" + word + "'";
			PreparedStatement pstmt = conn.prepareStatement(update);
			pstmt.executeUpdate();
			conn.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		
	}
	
	/** Method to query for a word and return its frequency (if present)- 
	 *  otherwise, frequency is set to -1. 
	 *  @param word is the word to search for in the database.
	 *  @return frequency of the word (or -1 if word not present in db) */
	public static int queryFrequency(String word) {
		try {
			conn = getConnection();
			String query = "SELECT frequency FROM words WHERE word = '" + word + "'";
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			int frequency = 0;
			if(rs.next()) {
				frequency = rs.getInt(1);
				conn.close();
				return frequency;
			} else {
				frequency = -1;
				conn.close();
				return frequency;
			}
		} catch(Exception e) {
			System.out.println("Error in Database.queryFrequency(): " + e.getMessage());
			e.printStackTrace();
		}
		
		return -1; // if not successful
	}
	
	/** Method that queries database for * FROM words, orders by frequency DESC and returns a ResultSet. 
	 *  @return ResultSet of query SELECT * FROM words ORDER BY frequency DESC */
	public static ResultSet getResults() {
		try {
			conn = getConnection();
			String query = "SELECT * FROM words ORDER BY frequency DESC";
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet results = pstmt.executeQuery();
			return results;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			System.out.println("The Method: queryAll is complete!");
		}
		
		return null; // if not successful
	}
	
	/** Used for testing while coding and will be deleted later.
	 * @param args */
	public static void main(String[] args) {
		try {
			deleteTable("words");
			createWordsTable("words");
			// Should pass and return 2
			post("The", 2);
			int freq = queryFrequency("The");
			System.out.println(freq);
			
			// Should fail and return -1
			post("He", 2);
			freq = queryFrequency("Alas");
			System.out.println(freq);
			
			getResults();
			
			deleteTable("words");
			createWordsTable("words");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}

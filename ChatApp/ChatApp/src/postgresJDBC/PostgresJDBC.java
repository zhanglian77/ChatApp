package postgresJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresJDBC {
	Connection connection = null; 
	Statement statement = null;
	String username;
	String receiver;
	public PostgresJDBC() {}
	
	
	public boolean logIn(String un, String pass){
		boolean res = true;
		try
		{
			String databaseUser = "huangdant";
			String databaseUserPass = "pass123";           
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://db.ecs.vuw.ac.nz/"+databaseUser+"_jdbc";
			connection = DriverManager.getConnection(url,databaseUser, databaseUserPass);
			System.out.println(connection);
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from users where name='"+un+"' and pass='"+pass+"'");
			if(! rs.next())
				res = false;            
			rs.close();
			//connection.close();
		}
		catch(Exception e){
			System.out.println("LogIn Error: "+e.toString());
			res = false;
		}
		
		if(res) this.username = un;
		return res;
	}
	
	public boolean setReceiver(String receiver) throws SQLException {
		boolean result = true;
		String query = "select * from users where name= '"+receiver+"'";
		ResultSet rs = statement.executeQuery(query);
		if(!rs.next()) {
			result = false;
		}else {
			this.receiver = receiver;
		}
		return result;
	}
	
	public boolean signup(String un, String pass){
		boolean result = true;
		try{
			String databaseUser = "huangdant";
			String databaseUserPass = "pass123";           
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://db.ecs.vuw.ac.nz/"+databaseUser+"_jdbc";
			connection = DriverManager.getConnection(url,databaseUser, databaseUserPass);
			System.out.println(connection);
			statement = connection.createStatement();
			String query = "select * from users where name= '"+un+"'";
			ResultSet rs = statement.executeQuery(query);
			if(!rs.next()) {
			String insertQuery = "insert into users(name, pass) values  ('"+ un +"','"+pass+"')";
			statement.executeUpdate(insertQuery);
			}else {
				result = false;
			}
		}
		catch(Exception e)
		{
			System.out.println("Sign up fail: "+e.toString());
			result = false;
		}
		
		if(result) this.username = un;
		return result;
	}
	

	public void receiveMessage(String receiver, String message) {	
		String time = "testTime";
		String insertQuery = "insert into messages(name, message, receiver, time) values ('"+ this.username +"','"+message +"','"+receiver+"','"+time+"')";
		System.out.println("shoule be receiver "+ receiver);	
		System.out.println("shoule be message "+message);			
		try {
			statement.executeUpdate(insertQuery);			
		} catch (SQLException e) {
			e.printStackTrace();
		}
}

	public ResultSet updateMessage() {
		ResultSet rs = null;
		try {
			String query = "select * from messages where (name = '" + this.username + "' and receiver = '"+this.receiver+"') or (name = '"+this.receiver+"' and receiver= '"+this.username+"')";
			rs = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}

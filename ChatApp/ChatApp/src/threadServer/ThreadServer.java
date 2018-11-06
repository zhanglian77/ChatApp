package threadServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import postgresJDBC.PostgresJDBC;

public class ThreadServer extends Thread {
	Socket socket;
	PostgresJDBC postgresJDBC;
	boolean isLogin = false;
	String username;
	String receiver;
	
	public ThreadServer(Socket s) {
		this.socket = s;
		this.postgresJDBC = new PostgresJDBC();
	}
	
	public void update(PrintWriter out) throws IOException, SQLException {
		ResultSet rs = this.postgresJDBC.updateMessage();
		StringBuilder builder = new StringBuilder();
        builder.append("update:");
		while (rs.next()) {			
			String name = rs.getString("name");
			String message = rs.getString("message");
			String receiver = rs.getString("receiver");
			String time = rs.getString("time");
			
			String cell = time +"---"+name + " to "+ receiver + "---"+message;
			System.out.println("messageCell "+cell);
			builder.append(cell);
			builder.append(":");			
		}	
		System.out.println("update: "+builder.toString());
		out.println(builder.toString());		
	}

	public void run(){
		PrintWriter out;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			System.out.println(socket);
			out.println("this is the server");
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			while(!this.isLogin) {
			String info[] = br.readLine().split(":");
			
			if(info[0].equals("auth")) {
				String username = info[1];
				String password = info[2];
				System.out.println(username+" "+password);
				this.isLogin = this.postgresJDBC.logIn(username, password);
				System.out.println("isLogin" + isLogin);
				this.username = info[1];
				if(this.isLogin) {
				out.println("login_success");
				}else {
					out.println("login_fail");
				}
			}	
			if(info[0].equals("sign")) {
				String username = info[1];
				String password = info[2];
				System.out.println(username+" "+password);
				boolean isSignup = this.postgresJDBC.signup(username, password);
				System.out.println("isSign" + isLogin);
				this.username = info[1];
				if(isSignup) {
				out.println("sign_success");
				}else {
					out.println("sign_user has already exist");
				}
			}	
			}
		
			
			while(this.isLogin) {
				System.out.println(isLogin);
				
				String i[] = br.readLine().split(":");
				if(i[0].equals("send")){
					this.postgresJDBC.receiveMessage(this.receiver, i[2]);
				}
				if(i[0].equals("update")) {
					try {
						this.update(out);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(i[0].equals("receiver")) {
					boolean rs;
					try {
						rs = this.postgresJDBC.setReceiver(i[1]);
						System.out.println("receiver inof" + rs);
						if(rs) {
						System.out.println("receiver: "+ i[1]);
						out.println("receriver set");
						this.receiver = i[1];
						}else {
							out.println("error:user is not exit");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}
				} 
			
		} catch (Exception e) {
			isLogin = false;

			System.out.println("1 "+e.toString());
			System.out.println("isLogin " +isLogin);
			e.printStackTrace();
		}	
		finally {
			try {
				isLogin = false;
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}		
}


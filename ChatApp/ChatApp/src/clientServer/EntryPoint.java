package clientServer;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EntryPoint {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					String serverAddress = "127.0.0.1";
					int port = 9050;
					Socket s = new Socket(serverAddress, port);
					PrintWriter out = new PrintWriter(s.getOutputStream(), true);
					BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
					Login.initialize(out, input, s);
					String message = input.readLine();
					System.out.println("Message received from the client : " +message);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}

package clientServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Chat {

	public static void run(PrintWriter out, BufferedReader input, String userStr, String receiverStr, Socket s) {
		System.out.println("This is Chat");
		JFrame f4 = new JFrame();
		f4.setVisible(true);
		f4.setBounds(100, 100, 655, 644);
//		f4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f4.getContentPane().setLayout(null);

		JTextArea messages = new JTextArea();
		messages.setLineWrap(true);
		messages.setBounds(41, 112, 370, 250);
		f4.getContentPane().add(messages);
		messages.setColumns(10);

		JTextField sendMessage = new JTextField();
		sendMessage.setBounds(38, 438, 373, 74);
		f4.getContentPane().add(sendMessage);
		sendMessage.setColumns(10);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnUpdate.setBounds(459, 243, 115, 74);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				out.println("update:" + "user1");

				messages.setText("");;
				try {
					String[] info = input.readLine().split(":");
					for(int i =1; i<info.length; i++) {
						messages.append(info[i] + "\n");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		f4.getContentPane().add(btnUpdate);


		JButton btnSend = new JButton("Send");
		btnSend.setBounds(470, 450, 104, 51);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = sendMessage.getText();
				System.out.println("send:" + receiverStr + ":" + message);
				out.println("send:" + receiverStr + ":" + message);
				sendMessage.setText("");
			}
		});
		f4.getContentPane().add(btnSend);

		JLabel lblMessages = new JLabel("Messages between " + userStr + " and " + receiverStr);
		lblMessages.setBounds(41, 68, 250, 20);
		f4.getContentPane().add(lblMessages);

		JLabel lblName = new JLabel(userStr + " says:");
		lblName.setBounds(41, 401, 69, 20);
		f4.getContentPane().add(lblName);

		JButton btnBack = new JButton("Back");
		btnBack.setBounds(459, 16, 115, 29);
		f4.getContentPane().add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				f4.setVisible(false);
				ChooseReceiver.run(out, input, userStr, s);
			}
		});

		f4.addWindowListener(new WindowAdapter(){ 

			public void windowClosing(WindowEvent e) {

				super.windowClosing(e);
				try {
					s.close();
					f4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}



		}); 	
	}

}

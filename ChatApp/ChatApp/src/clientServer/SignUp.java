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
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignUp {

	public static void run(PrintWriter out, BufferedReader input, Socket s) {
		JFrame f2 = new JFrame();
		f2.setVisible(true);
		f2.setBounds(100, 100, 450, 300);
//		f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f2.getContentPane().setLayout(null);

		JLabel lblChatapp = new JLabel("CHATAPP - Sign Up");
		lblChatapp.setBounds(143, 28, 128, 20);
		f2.getContentPane().add(lblChatapp);

		JLabel lblUser = new JLabel("User:");
		lblUser.setBounds(52, 74, 69, 20);
		f2.getContentPane().add(lblUser);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(31, 110, 79, 20);
		f2.getContentPane().add(lblPassword);

		JTextField txtSetUser = new JTextField();
		txtSetUser.setText("user5");
		txtSetUser.setBounds(125, 71, 146, 26);
		f2.getContentPane().add(txtSetUser);
		txtSetUser.setColumns(10);

		JPasswordField pwdSetPass = new JPasswordField();
		pwdSetPass.setText("passuser5");
		pwdSetPass.setBounds(125, 107, 146, 26);
		f2.getContentPane().add(pwdSetPass);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(143, 149, 115, 29);
		f2.getContentPane().add(btnSubmit);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String setUser = txtSetUser.getText();
				@SuppressWarnings("deprecation")
				String setPass = pwdSetPass.getText();
				System.out.println("sign:" + setUser + ":" + setPass);
				out.println("sign:" + setUser + ":" + setPass);

				String signInfo;
				try {
					signInfo = input.readLine();	
					System.out.print(signInfo);
					if(signInfo.equals("sign_success")) {
						f2.setVisible(false);
						Login.initialize(out, input, s);
					}
					else {
						JOptionPane.showMessageDialog(null, "User has already exist", "alert", JOptionPane.ERROR_MESSAGE); 
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
		});

		JButton btnBack = new JButton("Back");
		btnBack.setBounds(298, 16, 115, 29);
		f2.getContentPane().add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				f2.setVisible(false);
				Login.initialize(out, input, s);;
			}
		});

		f2.addWindowListener(new WindowAdapter(){ 

			public void windowClosing(WindowEvent e) {

				super.windowClosing(e);
				try {
					s.close();
					f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}



		}); 	
	}
}

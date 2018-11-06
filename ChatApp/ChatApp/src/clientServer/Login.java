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

public class Login {

	public static void initialize(PrintWriter out, BufferedReader input,Socket s) {
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 450, 300);
	//	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblChatapp = new JLabel("CHATAPP");
		lblChatapp.setBounds(143, 28, 128, 20);
		frame.getContentPane().add(lblChatapp);

		JLabel lblUser = new JLabel("User:");
		lblUser.setBounds(52, 74, 69, 20);
		frame.getContentPane().add(lblUser);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(31, 110, 79, 20);
		frame.getContentPane().add(lblPassword);

		JTextField txtUser = new JTextField();
		txtUser.setText("user1");
		txtUser.setBounds(125, 71, 146, 26);
		frame.getContentPane().add(txtUser);
		txtUser.setColumns(10);

		JPasswordField pwdPass = new JPasswordField();
		pwdPass.setText("passuser1");
		pwdPass.setBounds(125, 107, 146, 26);
		frame.getContentPane().add(pwdPass);

		JButton btnLoginIn = new JButton("Login in");
		btnLoginIn.setBounds(143, 149, 115, 29);

		btnLoginIn.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				String userStr = txtUser.getText();
				String passStr = new String(pwdPass.getPassword());
				
				try {

					System.out.println("auth:" + userStr +":" +passStr);
					out.println("auth:" + userStr +":" +passStr);
					String loginInfo = input.readLine();
					System.out.print(loginInfo);
					if(loginInfo.equals("login_success")) {
						
						frame.setVisible(false);
						ChooseReceiver.run(out, input, userStr, s);
					}
					else {
						JOptionPane.showMessageDialog(null, "Login in failed, please sign in.", "alert", JOptionPane.ERROR_MESSAGE); 
					}

				} 
				catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});

		frame.getContentPane().add(btnLoginIn);

		JButton btnSignUp = new JButton("Sign up");
		btnSignUp.setBounds(143, 194, 115, 29);
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				SignUp.run(out, input, s);
			}
		});

		frame.getContentPane().add(btnSignUp);
		frame.addWindowListener(new WindowAdapter(){ 

			public void windowClosing(WindowEvent e) {

				super.windowClosing(e);
				try {
					s.close();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}



		}); 	
	}



}
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
import javax.swing.JTextField;

public class ChooseReceiver {

	
	public static void run(PrintWriter out, BufferedReader input, String userStr, Socket s) {

		JFrame f3 = new JFrame();
		f3.setVisible(true);
		f3.setBounds(100, 100, 450, 300);
		f3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f3.getContentPane().setLayout(null);

		JLabel lblChatapp = new JLabel(userStr + " Send Message To...");
		lblChatapp.setBounds(135, 29, 150, 20);
		f3.getContentPane().add(lblChatapp);

		JTextField txtSetReceiver = new JTextField();
		txtSetReceiver.setText("user4");
		txtSetReceiver.setBounds(125, 71, 146, 26);
		f3.getContentPane().add(txtSetReceiver);
		txtSetReceiver.setColumns(10);

		JButton btnNext = new JButton("Next");
		btnNext.setBounds(143, 149, 115, 29);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String receiverStr = txtSetReceiver.getText();
				out.println("receiver:" + receiverStr);
				String receiverInfo;
				try {
					receiverInfo = input.readLine();
					System.out.print(receiverInfo);
					if(receiverInfo.equals("error:user is not exit")) {
						JOptionPane.showMessageDialog(null, "User is not exit", "alert", JOptionPane.ERROR_MESSAGE); 
					}
					else {				

						f3.setVisible(false);
						Chat.run(out, input, userStr, receiverStr, s);

					}
				} catch (IOException e) {
					e.printStackTrace();
				}



			}
		});
		f3.getContentPane().add(btnNext);
		f3.addWindowListener(new WindowAdapter(){ 

			public void windowClosing(WindowEvent e) {

				super.windowClosing(e);
				try {
					s.close();
					f3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}



		}); 	

	}
}



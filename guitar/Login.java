import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;

public class Login extends JPanel {
	private JTextField textField;
	
	public Login(Game game) {
		setBackground(Color.BLACK);
		setLayout(null);
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Arial Black", Font.PLAIN, 20));
		textField.setBounds(104, 98, 245, 54);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("CHOOSE YOUR NAME!");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 24));
		lblNewLabel.setBounds(104, 25, 245, 54);
		add(lblNewLabel);
		
		JButton btnNewButton = new JButton("LET`S GO");
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.setIsInGame(true);
				game.getPlayer().setName(textField.getText());
			}
		});
		setPreferredSize(new Dimension(450,300));
		btnNewButton.setBounds(135, 186, 192, 37);
		add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("OBJECTIVE: Press the coresponding keys as the rectangles touch");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(26, 234, 402, 55);
		add(lblNewLabel_1);
		
	}
}

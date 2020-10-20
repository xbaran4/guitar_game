import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class EndScreen extends JPanel {

	public EndScreen(Game game) {
		setPreferredSize(new Dimension(450, 300));
		setBackground(Color.BLACK);
		setLayout(null);
		JLabel lblNewLabel = new JLabel("U HAVE LOST!");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 26));
		lblNewLabel.setBounds(126, 40, 180, 44);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Final Score : " + game.getPlayer().getScore());
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(126, 119, 180, 34);
		add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Whatever");
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 20));
		btnNewButton.setIcon(null);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(1);
			}
		});
		btnNewButton.setBounds(126, 184, 180, 44);
		add(btnNewButton);
	}
}

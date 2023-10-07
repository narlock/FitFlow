package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class AppOverlayPanel extends JPanel {

	private static final long serialVersionUID = -8017650452354752855L;
	
	private BufferedImage logoImage;
	private GridBagConstraints gbc;
	
	private State state;
	
	public AppOverlayPanel() {
		setBackground(new Color(255, 102, 0));
		try {
			logoImage = ImageIO.read(getClass().getResource("/Logo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		
		// Default state will be the home state
		state = new HomeState(this);
		add(state, gbc);
	}
	
	public void changeState(State state) {
		remove(this.state);
		this.state = state;
		add(this.state, gbc);
		
		repaint();
		revalidate();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(logoImage, 105, 10, null);
		
		g.setFont(new Font("Arial", Font.BOLD, 12));
		g.setColor(Color.black);
		g.drawString("Version 1.0.0", 265, 700);
		g.drawString("Made by narlock", 257, 715);
	}
}

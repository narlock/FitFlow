package main;

import javax.swing.JFrame;

import state.AppOverlayPanel;

public class Window extends JFrame {

	private static final long serialVersionUID = -4810618286807932601L;

	public Window() {
		setTitle("FitFlow");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 800);
		setResizable(false);
		
		AppOverlayPanel appOverlayPanel = new AppOverlayPanel();
		
		
		add(appOverlayPanel);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
}

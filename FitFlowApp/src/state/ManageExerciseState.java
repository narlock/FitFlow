package state;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import component.ManageExercisesPanel;

public class ManageExerciseState extends State {
	private static final long serialVersionUID = 67790702042007131L;
	private GridBagConstraints gbc;
	private AppOverlayPanel aop;
	
	private JLabel manageExercisesLabel = new JLabel("Manage Exercises");
	private JButton addExerciseButton = new JButton(new ImageIcon(getClass().getResource("/ADD.png")));
	
	private ManageExercisesPanel manageExercisesPanel;

	private JButton returnToHomeButton = new JButton("Return to Home");
	
	public ManageExerciseState(AppOverlayPanel aop) {
		super(aop);
		this.aop = aop;
		
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		
		manageExercisesPanel = new ManageExercisesPanel();
		
		JPanel titlePanel = new JPanel();
		titlePanel.add(manageExercisesLabel);
		titlePanel.add(addExerciseButton);
		add(titlePanel, gbc);
		
		JScrollPane scrollPane = new JScrollPane(manageExercisesPanel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(400, 400));
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		add(scrollPane, gbc);
		
		add(returnToHomeButton, gbc);
		addActions();
	}
	
	public void addActions() {
		returnToHomeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				aop.changeState(new HomeState(aop));
			}
		});
	}

}

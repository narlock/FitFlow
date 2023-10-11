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
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import component.ManageWorkoutsPanel;

public class ManageWorkoutState extends State {

	private static final long serialVersionUID = -7964344136669578628L;
	private GridBagConstraints gbc;
	private AppOverlayPanel aop;
	
	private JLabel manageWorkoutsLabel = new JLabel("Manage Workouts");
	private JButton addWorkoutButton = new JButton(new ImageIcon(getClass().getResource("/ADD.png")));

	private JScrollPane scrollPane;
	private ManageWorkoutsPanel manageWorkoutsPanel;
	
	private JButton returnToHomeButton = new JButton("Return to Home");
	
	public ManageWorkoutState(AppOverlayPanel aop) {
		super(aop);
		this.aop = aop;
		
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		
		manageWorkoutsPanel = new ManageWorkoutsPanel(this);
		
		JPanel titlePanel = new JPanel();
		titlePanel.add(manageWorkoutsLabel);
		titlePanel.add(addWorkoutButton);
		add(titlePanel, gbc);
		
		scrollPane = new JScrollPane(manageWorkoutsPanel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(400, 400));
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		add(scrollPane, gbc);
		
		add(returnToHomeButton, gbc);
		addActions();
	}
	
	public void addActions() {
		addWorkoutButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		returnToHomeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				aop.changeState(new HomeState(aop));
			}
		});
	}
	
	public void refreshWorkouts() {
		remove(scrollPane);
		remove(returnToHomeButton);
		
		manageWorkoutsPanel = new ManageWorkoutsPanel(this);
		scrollPane = new JScrollPane(manageWorkoutsPanel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(400, 400));
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		add(scrollPane, gbc);
		
		add(returnToHomeButton, gbc);
		repaint();
		revalidate();
	}

}

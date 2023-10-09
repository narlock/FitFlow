package state;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import component.WorkoutsPanel;

public class HomeState extends State {

	private AppOverlayPanel aop;
	private static final long serialVersionUID = -8017650452354752855L;
	private GridBagConstraints gbc;
	
	private JLabel chooseAWorkoutLabel = new JLabel("Pick your power session!");
	private JScrollPane scrollPane;
	private WorkoutsPanel workoutsPanel;
	private JButton manageExercisesButton = new JButton("Manage Exercises");
	private JButton manageWorkoutsButton = new JButton("Manage Workouts");
	
	
	public HomeState(AppOverlayPanel aop) {
		super(aop);
		this.aop = aop;
		
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		
		workoutsPanel = new WorkoutsPanel(aop);
		
		add(chooseAWorkoutLabel, gbc);
		
		scrollPane = new JScrollPane(workoutsPanel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(400, 400));
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		add(scrollPane, gbc);
		
		
		add(manageExercisesButton, gbc);
		add(manageWorkoutsButton, gbc);
		
		setButtonActions();
	}
	
	public void setButtonActions() {
		manageExercisesButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				aop.changeState(new ManageExerciseState(aop));
			}
			
		});
		
		manageWorkoutsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				aop.changeState(new ManageWorkoutState(aop));
			}
			
		});
	}
}

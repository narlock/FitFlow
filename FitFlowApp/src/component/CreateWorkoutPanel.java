package component;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Workout;

public class CreateWorkoutPanel extends JPanel {

	private static final long serialVersionUID = 1693099404220930960L;
	
	private JLabel workoutNameLabel;
	private JTextField workoutNameTextField;
	
	private WorkoutPanel workoutPanel;

	public CreateWorkoutPanel(Workout workout) {
		setLayout(new BorderLayout());
		
		workoutPanel = new WorkoutPanel(workout);
		
		JPanel northPanel = new JPanel();
		workoutNameLabel = new JLabel("Workout Name ");
		workoutNameTextField = new JTextField(15);
		northPanel.add(workoutNameLabel);
		northPanel.add(workoutNameTextField);
		
		add(northPanel, BorderLayout.NORTH);
		add(workoutPanel, BorderLayout.CENTER);
	}
	
	public JTextField getWorkoutNameTextField() {
		return workoutNameTextField;
	}

}

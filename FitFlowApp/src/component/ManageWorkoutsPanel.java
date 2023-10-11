package component;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import io.FitFlowIO;
import model.Workout;
import state.ManageWorkoutState;

public class ManageWorkoutsPanel extends JPanel {

	private static final long serialVersionUID = 5243790376667472375L;
	private GridBagConstraints gbc;
	
	private ManageWorkoutState manageWorkoutState;
	
	private List<Workout> workouts;
	
	public ManageWorkoutsPanel(ManageWorkoutState manageWorkoutState) {
		this.manageWorkoutState = manageWorkoutState;
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		
		workouts = FitFlowIO.readWorkouts();
		for(Workout workout : workouts) {
			add(createWorkoutManagePanel(workout), gbc);
		}
	}
	
	public JPanel createWorkoutManagePanel(Workout workout) {
		JPanel panel = new JPanel();
		
		JButton workoutButton = new JButton(workout.getName());
		workoutButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel workoutPanel = new WorkoutPanel(workout);
				int choice = JOptionPane.showConfirmDialog(getRootPane(),
						workoutPanel,
						workout.getName(),
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.PLAIN_MESSAGE,
						null
					);
				
				if(choice == JOptionPane.YES_OPTION) {
					System.out.println("Updating workout");
					FitFlowIO.upsertWorkouts(workouts);
				}
			}
		});
		panel.add(workoutButton);
		
		JButton deleteButton = new JButton(new ImageIcon(getClass().getResource("/DELETE.png")));
		deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(getRootPane(), 
						"Confirm removal of " + workout.getName() + "?", 
						"Remove Workout", 
						JOptionPane.DEFAULT_OPTION, 
						JOptionPane.PLAIN_MESSAGE, 
						null
					);
				
				if(choice == JOptionPane.YES_OPTION) {
					workouts.remove(workout);
					
					FitFlowIO.upsertWorkouts(workouts);
					manageWorkoutState.refreshWorkouts();
				}
			}
		});
		panel.add(deleteButton);
		
		return panel;
	}
}

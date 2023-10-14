package component;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import io.FitFlowIO;
import model.Exercise;
import model.ExerciseItem;
import model.Workout;
import state.ManageExerciseState;
import util.FitFlowUtils;

public class ManageExercisesPanel extends JPanel {

	private static final long serialVersionUID = -8295711527385467430L;
	private GridBagConstraints gbc;
	
	private ManageExerciseState manageExerciseState;
	
	private List<Exercise> exercises;
	
	public ManageExercisesPanel(ManageExerciseState manageExerciseState) {
		this.manageExerciseState = manageExerciseState;
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		
		exercises = FitFlowIO.readExercises(); // reads the exercises from local JSON file
		for(int i = 0; i < exercises.size(); i++) {
			Exercise exercise = exercises.get(i);
			add(createExerciseManagePanel(exercise, i), gbc);
		}
	}
	
	public JPanel createExerciseManagePanel(Exercise exercise, int index) {
		JPanel panel = new JPanel();
		
		// Get Image
		JLabel imageLabel = new JLabel(new ImageIcon(FitFlowUtils.getScaledImageForIcon(exercise.getImagePath())));
		
		// Get Title
		JLabel title = new JLabel(exercise.getName() != null ? exercise.getName() : "Unknown Exercise");
		
		// Edit Button
		JButton editButton = new JButton(new ImageIcon(getClass().getResource("/EDIT.png")));
		editButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Changes to edit mode
				panel.removeAll();
				editExercise(panel, exercise);
			}
		});
		
		// Delete Button
		JButton deleteButton = new JButton(new ImageIcon(getClass().getResource("/DELETE.png")));
		deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(getRootPane(), 
						"Confirm removal of " + exercise.getName() + "?", 
						"Remove Exercise", 
						JOptionPane.DEFAULT_OPTION, 
						JOptionPane.PLAIN_MESSAGE, 
						null
					);
				
				if(choice == JOptionPane.YES_OPTION) {
					
					List<Workout> workouts = FitFlowIO.readWorkouts();
					
					workouts.forEach(workout -> {
						// for each workout, remove the instance of the exercise to remove by index
					    List<ExerciseItem> exercisesToRemove = workout.getExercises().stream()
					            .filter(exerciseItem -> exerciseItem.getIndex() == index)
					            .collect(Collectors.toList());

					    workout.getExercises().removeAll(exercisesToRemove);

					    // then, reorder the indices to ensure no errors
					    workout.getExercises().forEach(exerciseItem -> {
					        if (exerciseItem.getIndex() > index) {
					            exerciseItem.setIndex(exerciseItem.getIndex() - 1);
					        }
					    });
					});
					
					// remove the exercise
					exercises.remove(exercise);
					
					// rewrite exercises and workouts
					FitFlowIO.upsertExercises(exercises);
					FitFlowIO.upsertWorkouts(workouts);
					
					// refresh gui
					manageExerciseState.refreshExercises();
				}
			}
		});

		panel.add(imageLabel);
		panel.add(title);
		panel.add(editButton);
		panel.add(deleteButton);
		
		return panel;
	}
	
	private void editExercise(JPanel panel, Exercise exercise) {
		// JButton image
		JButton imageButton = new JButton(new ImageIcon(FitFlowUtils.getScaledImageForIcon(exercise.getImagePath())));
		imageButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Open file menu dialog that will store path of exercise
				JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(getRootPane());

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    	// Input validation
                		String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                		if(filePath.endsWith(".png") || filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) {
                			System.out.println("[ManageExercisesPanel.editExercise.imageButton] Valid file chosen: " + filePath);
                			// Set the image path
                			exercise.setImagePath(filePath);
                			
                			// Update the icon of the button
                			imageButton.setIcon(new ImageIcon(FitFlowUtils.getScaledImageForIcon(filePath)));
                			imageButton.repaint();
                			imageButton.revalidate();
                			
                		} else {
                			// Print an error dialog
                			JOptionPane.showMessageDialog(getRootPane(), "Invalid file type provided. Supports: .png, .jpg, .jpeg");
                			throw new RuntimeException("Invalid file type");
                		}
                    	
                }
			}
		});
		
		// Title text field
		JTextField titleTextField = new JTextField(18);
		titleTextField.setText(exercise.getName() != null ? exercise.getName() : "Unknown Exercise");
		
		// Done button
		JButton doneButton = new JButton(new ImageIcon(getClass().getResource("/DONE.png")));
		doneButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				exercise.setName(titleTextField.getText());
				FitFlowIO.upsertExercises(exercises);
				
				panel.removeAll();
				viewExercise(panel, exercise);
			}
		});
		
		panel.add(imageButton);
		panel.add(titleTextField);
		panel.add(doneButton);
		
		panel.repaint();
		panel.revalidate();
	}
	
	private void viewExercise(JPanel panel, Exercise exercise) {
		// Get Image
		JLabel imageLabel = new JLabel(new ImageIcon(FitFlowUtils.getScaledImageForIcon(exercise.getImagePath())));
		
		// Get Title
		JLabel title = new JLabel(exercise.getName() != null ? exercise.getName() : "Unknown Exercise");
		
		// Edit Button
		JButton editButton = new JButton(new ImageIcon(getClass().getResource("/EDIT.png")));
		editButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Changes to edit mode
				panel.removeAll();
				editExercise(panel, exercise);
			}
		});
		
		// Delete Button
		JButton deleteButton = new JButton(new ImageIcon(getClass().getResource("/DELETE.png")));
		deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(getRootPane(), 
						"Confirm removal of " + exercise.getName() + "?", 
						"Remove Exercise", 
						JOptionPane.DEFAULT_OPTION, 
						JOptionPane.PLAIN_MESSAGE, 
						null
					);
				
				if(choice == JOptionPane.YES_OPTION) {
					exercises.remove(exercise);
					
					// TODO Add logic that removes all instances of this exercise
					// from any work outs.
					
					FitFlowIO.upsertExercises(exercises);
					manageExerciseState.refreshExercises();
				}
			}
		});
		
		panel.add(imageLabel);
		panel.add(title);
		panel.add(editButton);
		panel.add(deleteButton);
		
		panel.repaint();
		panel.revalidate();
	}
}

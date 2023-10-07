package component;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import io.FitFlowIO;
import model.Exercise;

public class ManageExercisesPanel extends JPanel {

	private static final long serialVersionUID = -8295711527385467430L;
	private GridBagConstraints gbc;
	
	private List<Exercise> exercises;
	
	public ManageExercisesPanel() {
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		
		exercises = FitFlowIO.readExercises(); // reads the exercises from local JSON file
		for(Exercise exercise : exercises) {
			add(createExerciseManagePanel(exercise), gbc);
		}
	}
	
	public JPanel createExerciseManagePanel(Exercise exercise) {
		JPanel panel = new JPanel();
		
		// Get Image
		JLabel imageLabel = new JLabel(new ImageIcon(getExerciseImage(exercise.getImagePath())));
		
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
		
		panel.add(imageLabel);
		panel.add(title);
		panel.add(editButton);
		panel.add(deleteButton);
		
		return panel;
	}
	
	private Image getExerciseImage(String path) {
		Image image = null;
		try {
			image = ImageIO.read(getClass().getResource(path));
			image = image.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
		} catch (Exception e) {
			// If the file throws an error for whatever reason, assign it a default image
			try {
				image = ImageIO.read(getClass().getResource("/DefaultExercise.png"));
				image = image.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			} catch (IOException e1) {
				// If this fails, we in big trouble
				e1.printStackTrace();
			}
		}
		return image;
	}
	
	private void editExercise(JPanel panel, Exercise exercise) {
		// JButton image
		JButton imageButton = new JButton(new ImageIcon(getExerciseImage(exercise.getImagePath())));
		imageButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Open file menu dialog that will store path of exercise
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
				// TODO Save the changes to local file
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
		JLabel imageLabel = new JLabel(new ImageIcon(getExerciseImage(exercise.getImagePath())));
		
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
		
		panel.add(imageLabel);
		panel.add(title);
		panel.add(editButton);
		panel.add(deleteButton);
		
		panel.repaint();
		panel.revalidate();
	}
}

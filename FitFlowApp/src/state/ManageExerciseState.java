package state;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import component.ManageExercisesPanel;
import io.FitFlowIO;
import model.Exercise;
import util.FitFlowUtils;

public class ManageExerciseState extends State {
	private static final long serialVersionUID = 67790702042007131L;
	private GridBagConstraints gbc;
	private AppOverlayPanel aop;
	
	private JLabel manageExercisesLabel = new JLabel("Manage Exercises");
	private JButton addExerciseButton = new JButton(new ImageIcon(getClass().getResource("/ADD.png")));
	
	private JScrollPane scrollPane;
	private ManageExercisesPanel manageExercisesPanel;

	private JButton returnToHomeButton = new JButton("Return to Home");
	
	public ManageExerciseState(AppOverlayPanel aop) {
		super(aop);
		this.aop = aop;
		
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		
		manageExercisesPanel = new ManageExercisesPanel(this);
		
		JPanel titlePanel = new JPanel();
		titlePanel.add(manageExercisesLabel);
		titlePanel.add(addExerciseButton);
		add(titlePanel, gbc);
		
		scrollPane = new JScrollPane(manageExercisesPanel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(400, 400));
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		add(scrollPane, gbc);
		
		add(returnToHomeButton, gbc);
		addActions();
	}
	
	public void addActions() {
		addExerciseButton.addActionListener(new ActionListener() {
			String imagePath = null;
			String name = "";
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Reset the values within imagePath and name
				imagePath = null;
				name = "";
				
				JPanel panel = new JPanel(new GridBagLayout());
				
				JPanel exerciseNamePanel = new JPanel();
				JLabel exerciseNameLabel = new JLabel("Exercise Name");
				JTextField exerciseNameTextField = new JTextField(20);
				exerciseNamePanel.add(exerciseNameLabel);
				exerciseNamePanel.add(exerciseNameTextField);
				
				JButton exerciseImageButton = new JButton("Select Image", new ImageIcon(FitFlowUtils.getScaledImageForIcon(null)));
				exerciseImageButton.addActionListener(new ActionListener() {
					
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
		                			imagePath = filePath;
		                			
		                			// Update the icon of the button
		                			exerciseImageButton.setIcon(new ImageIcon(FitFlowUtils.getScaledImageForIcon(filePath)));
		                			exerciseImageButton.repaint();
		                			exerciseImageButton.revalidate();
		                			
		                		} else {
		                			// Print an error dialog
		                			throw new RuntimeException("Invalid file type");
		                		}
		                    	
		                }
					}
				});
				
				panel.add(exerciseNamePanel, gbc);
				panel.add(exerciseImageButton, gbc);
				
				int choice = JOptionPane.showConfirmDialog(getRootPane(), 
						panel, 
						"Create new Exercise", 
						JOptionPane.DEFAULT_OPTION, 
						JOptionPane.PLAIN_MESSAGE, 
						null
					);
			
				if(choice == JOptionPane.OK_OPTION) {
					// Save and refresh the list of exercises
					System.out.println("OK!");
					name = exerciseNameTextField.getText();
					
					List<Exercise> exercises = FitFlowIO.readExercises();
					exercises.add(new Exercise(name, imagePath));
					
					FitFlowIO.upsertExercises(exercises);
					refreshExercises();
				} else {
					// Cancel
					System.out.println("Cancel");
				}
			}
		});
		
		returnToHomeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				aop.changeState(new HomeState(aop));
			}
		});
	}

	public void refreshExercises() {
		remove(scrollPane);
		remove(returnToHomeButton);

		manageExercisesPanel = new ManageExercisesPanel(this);
		scrollPane = new JScrollPane(manageExercisesPanel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(400, 400));
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		add(scrollPane, gbc);
		
		add(returnToHomeButton, gbc);
		repaint();
		revalidate();
	}
}

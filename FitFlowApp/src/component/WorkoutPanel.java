package component;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import io.FitFlowIO;
import model.Exercise;
import model.ExerciseItem;
import model.Workout;

public class WorkoutPanel extends JPanel {

	private static final long serialVersionUID = 5324880856162547729L;
	private GridBagConstraints gbc;
	private Workout workout;

	private ExerciseItem currentExerciseItem;
	private int currentExerciseItemIndex;
	private JPanel currentExercisesPanel = new JPanel();
	private JLabel currentExercisesLabel = new JLabel("Current Exercises");
	private DefaultListModel<String> currentExerciseListModel;
	private JList<String> currentExercisesList;
	private JPanel currentWorkTimePanel;
	private JLabel currentExerciseWorkTime = new JLabel("Work (sec)");
	private JTextField currentExerciseWorkTimeTextField = new JTextField(5);
	private JPanel currentBreakTimePanel;
	private JLabel currentExerciseBreakTime = new JLabel("Break (sec)");
	private JTextField currentExerciseBreakTimeTextField = new JTextField(5);
	
	private JPanel currentButtonPanel = new JPanel();
	private JButton currentExerciseUpdateButton = new JButton(new ImageIcon(getClass().getResource("/DONE.png")));
	private JButton removeExistingExerciseButton = new JButton(new ImageIcon(getClass().getResource("/DELETE.png")));
	
	private ExerciseItem newExerciseItem;
	private JPanel chooseExercisesPanel = new JPanel();
	private JLabel chooseExercisesLabel = new JLabel("Add Exercises");
	private DefaultListModel<String> chooseExerciseListModel;
	private JList<String> chooseExercisesList;
	private JPanel chooseExerciseWorkTimePanel;
	private JLabel chooseExerciseWorkTime = new JLabel("Work (sec)");
	private JTextField chooseExerciseWorkTimeTextField = new JTextField(5);
	private JPanel chooseExerciseBreakTimePanel;
	private JLabel chooseExerciseBreakTime = new JLabel("Break (sec)");
	private JTextField chooseExerciseBreakTimeTextField = new JTextField(5);
	private JButton chooseExerciseAddButton = new JButton(new ImageIcon(getClass().getResource("/ADD.png")));
	
	public WorkoutPanel(Workout workout) {
		this.workout = workout;
		setPreferredSize(new Dimension(400, 400));
		gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		setLayout(new GridLayout(1, 2));
		
		currentExercisesPanel.setLayout(new GridBagLayout());
		currentExercisesPanel.add(currentExercisesLabel, gbc);
		add(currentExercisesPanel);
		
		currentExerciseListModel = new DefaultListModel<>();
		for(int i = 0; i < workout.getExercises().size(); i++) {
			ExerciseItem exercise = workout.getExercises().get(i);
			currentExerciseListModel.addElement(exercise.getExercise().getName());
		}
		currentExercisesList = new JList<>(currentExerciseListModel);
		currentExercisesList.setCellRenderer(new ClickableItemRenderer());
		currentExercisesList.addMouseListener(new MouseAdapter() {
			
			@Override
            public void mouseClicked(MouseEvent e) {
                int selectedIndex = currentExercisesList.locationToIndex(e.getPoint());
                if (selectedIndex != -1) {
                    String selectedItem = workout.getExercises().get(selectedIndex).getExercise().getName();
                    System.out.println("Clicked on: " + selectedItem + " (Index: " + selectedIndex + ")");
                    
                    // Perform your action here based on the selected item or index
                    	currentWorkTimePanel.setVisible(true);
                    	System.out.println("Work Time: " + workout.getExercises().get(selectedIndex).getWorkTimeInSeconds());
                    	currentExerciseWorkTimeTextField.setText(String.valueOf(workout.getExercises().get(selectedIndex).getWorkTimeInSeconds()));
                    	
                    	currentBreakTimePanel.setVisible(true);
                    	System.out.println("Break Time: " + workout.getExercises().get(selectedIndex).getBreakTimeInSeconds());
                    	currentExerciseBreakTimeTextField.setText(String.valueOf(workout.getExercises().get(selectedIndex).getBreakTimeInSeconds()));
                
                    	currentExerciseUpdateButton.setVisible(true);
                    	currentExerciseItem = workout.getExercises().get(selectedIndex);
                    	currentExerciseItemIndex = selectedIndex;
                    	
                    	removeExistingExerciseButton.setVisible(true);
                }
            }
		});
		
		JScrollPane currentScrollPane = new JScrollPane(currentExercisesList);
		currentScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		currentScrollPane.setPreferredSize(new Dimension(200, 200));
		currentScrollPane.getVerticalScrollBar().setUnitIncrement(10);
		currentExercisesPanel.add(currentScrollPane, gbc);
		
		currentWorkTimePanel = new JPanel();
		currentWorkTimePanel.add(currentExerciseWorkTime);
		currentWorkTimePanel.add(currentExerciseWorkTimeTextField);
		currentExercisesPanel.add(currentWorkTimePanel, gbc);
		currentWorkTimePanel.setVisible(false);
		currentBreakTimePanel = new JPanel();
		currentBreakTimePanel.add(currentExerciseBreakTime);
		currentBreakTimePanel.add(currentExerciseBreakTimeTextField);
		currentExercisesPanel.add(currentBreakTimePanel, gbc);
		currentBreakTimePanel.setVisible(false);
		
		currentButtonPanel = new JPanel();
		currentButtonPanel.add(currentExerciseUpdateButton);
		currentButtonPanel.add(removeExistingExerciseButton);
		currentExercisesPanel.add(currentButtonPanel, gbc);
		
		currentExerciseUpdateButton.setVisible(false);
		currentExerciseUpdateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				currentExerciseItem.setWorkTimeInSeconds(Integer.parseInt(currentExerciseWorkTimeTextField.getText()));
				currentExerciseItem.setBreakTimeInSeconds(Integer.parseInt(currentExerciseBreakTimeTextField.getText()));
				
				currentWorkTimePanel.setVisible(false);
				currentBreakTimePanel.setVisible(false);
				currentExerciseUpdateButton.setVisible(false);
				removeExistingExerciseButton.setVisible(false);
			}
		});
		
		removeExistingExerciseButton.setVisible(false);
		removeExistingExerciseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				workout.getExercises().remove(currentExerciseItem);
				currentExerciseListModel.remove(currentExerciseItemIndex);
				currentExercisesList = new JList<>(currentExerciseListModel);
				
				repaint();
				revalidate();
				
				currentWorkTimePanel.setVisible(false);
				currentBreakTimePanel.setVisible(false);
				currentExerciseUpdateButton.setVisible(false);
				removeExistingExerciseButton.setVisible(false);
			}
		});
		
		chooseExercisesPanel.setLayout(new GridBagLayout());
		chooseExercisesPanel.add(chooseExercisesLabel, gbc);
		add(chooseExercisesPanel);
		
		List<Exercise> exercises = FitFlowIO.readExercises();
		chooseExerciseListModel = new DefaultListModel<>();
		for(int i = 0; i < exercises.size(); i++) {
			Exercise exercise = exercises.get(i);
			chooseExerciseListModel.addElement(exercise.getName());
		}
		chooseExercisesList = new JList<>(chooseExerciseListModel);
		chooseExercisesList.setCellRenderer(new ClickableItemRenderer());
		
		chooseExercisesList.addMouseListener(new MouseAdapter() {
			
			@Override
            public void mouseClicked(MouseEvent e) {
				int selectedIndex = chooseExercisesList.locationToIndex(e.getPoint());
				if (selectedIndex != -1) {
					System.out.println("Index " + selectedIndex + " selected. Belongs to \"" + exercises.get(selectedIndex).getName() + "\"");
					newExerciseItem = new ExerciseItem(exercises.get(selectedIndex));
					
					chooseExerciseWorkTimeTextField.setText("");
					chooseExerciseBreakTimeTextField.setText("");
					
					chooseExerciseWorkTimePanel.setVisible(true);
					chooseExerciseBreakTimePanel.setVisible(true);
					chooseExerciseAddButton.setVisible(true);
				}
	        }
		});
		
		JScrollPane chooseExercisesPane = new JScrollPane(chooseExercisesList);
		chooseExercisesPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		chooseExercisesPane.setPreferredSize(new Dimension(200, 200));
		chooseExercisesPane.getVerticalScrollBar().setUnitIncrement(10);
		chooseExercisesPanel.add(chooseExercisesPane, gbc);
		
		chooseExerciseWorkTimePanel = new JPanel();
		chooseExerciseWorkTimePanel.add(chooseExerciseWorkTime);
		chooseExerciseWorkTimePanel.add(chooseExerciseWorkTimeTextField);
		chooseExercisesPanel.add(chooseExerciseWorkTimePanel, gbc);
		chooseExerciseWorkTimePanel.setVisible(false);
		chooseExerciseBreakTimePanel = new JPanel();
		chooseExerciseBreakTimePanel.add(chooseExerciseBreakTime);
		chooseExerciseBreakTimePanel.add(chooseExerciseBreakTimeTextField);
		chooseExercisesPanel.add(chooseExerciseBreakTimePanel, gbc);
		chooseExerciseBreakTimePanel.setVisible(false);
		chooseExercisesPanel.add(chooseExerciseAddButton, gbc);
		
		chooseExerciseAddButton.setVisible(false);
		chooseExerciseAddButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				newExerciseItem.setWorkTimeInSeconds(Integer.parseInt(chooseExerciseWorkTimeTextField.getText()));
				newExerciseItem.setBreakTimeInSeconds(Integer.parseInt(chooseExerciseBreakTimeTextField.getText()));
				
				
				workout.getExercises().add(newExerciseItem);
				currentExerciseListModel.addElement(newExerciseItem.getExercise().getName());
				currentExercisesList = new JList<>(currentExerciseListModel);
				
				repaint();
				revalidate();
				
				chooseExerciseWorkTimePanel.setVisible(false);
				chooseExerciseBreakTimePanel.setVisible(false);
				chooseExerciseAddButton.setVisible(false);
			}
		});
	}
}

class ClickableItemRenderer extends DefaultListCellRenderer {
    private static final long serialVersionUID = -7558379762580402037L;

	@Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        // Make the item clickable
        if (renderer instanceof JLabel) {
            JLabel label = (JLabel) renderer;
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        return renderer;
    }
}

package util;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FitFlowUtils {

	public static Image getScaledImageForIcon(String filePath) {
		Image image = null;
		try {
			image = ImageIO.read(new File(filePath));
			image = image.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			System.out.println("[ManageExercisesPanel.getExerciseImage] Returning valid image for filePath: " + filePath);
		} catch (Exception e) {
			// If the file throws an error for whatever reason, assign it a default image
			try {
				image = ImageIO.read(FitFlowUtils.class.getResource("/DefaultExercise.png"));
				image = image.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			} catch (IOException e1) {
				// If this fails, we in big trouble
				e1.printStackTrace();
			}
		}
		return image;
	}
	
	public static Image getScaledImageForCurrentWorkout(String filePath) {
		Image image = null;
		try {
			image = ImageIO.read(new File(filePath));
			image = image.getScaledInstance(256, 256, Image.SCALE_SMOOTH);
			System.out.println("[ManageExercisesPanel.getExerciseImage] Returning valid image for filePath: " + filePath);
		} catch (Exception e) {
			// If the file throws an error for whatever reason, assign it a default image
			try {
				image = ImageIO.read(FitFlowUtils.class.getResource("/DefaultExercise.png"));
				image = image.getScaledInstance(256, 256, Image.SCALE_SMOOTH);
			} catch (IOException e1) {
				// If this fails, we in big trouble
				e1.printStackTrace();
			}
		}
		return image;
	}
}

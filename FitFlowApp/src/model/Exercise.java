package model;

/**
 * Model for an individual exercise. This will contain the name
 * of the exercise, along with an optional customizable image path
 * that we will lookup during runtime to use to display an image
 * to the screen.
 * 
 * @author narlock
 *
 */
public class Exercise {
	private String name;
	private String imagePath;
	
	public Exercise(String name, String imagePath) {
		this.name = name;
		this.imagePath = imagePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
}

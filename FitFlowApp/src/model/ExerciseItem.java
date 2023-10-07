package model;

/**
 * Model for an exercise item. The item that is being added to a work out.
 * This contains the exercise itself, along with the selected work time
 * and break time.
 * 
 * @author narlock
 *
 */
public class ExerciseItem {
	private Exercise exercise;
	private int workTimeInSeconds;
	private int breakTimeInSeconds;
	
	public ExerciseItem(Exercise exercise, int workTimeInSeconds, int breakTimeInSeconds) {
		this.exercise = exercise;
		this.workTimeInSeconds = workTimeInSeconds;
		this.breakTimeInSeconds = breakTimeInSeconds;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	public int getWorkTimeInSeconds() {
		return workTimeInSeconds;
	}

	public void setWorkTimeInSeconds(int workTimeInSeconds) {
		this.workTimeInSeconds = workTimeInSeconds;
	}

	public int getBreakTimeInSeconds() {
		return breakTimeInSeconds;
	}

	public void setBreakTimeInSeconds(int breakTimeInSeconds) {
		this.breakTimeInSeconds = breakTimeInSeconds;
	}
	
}

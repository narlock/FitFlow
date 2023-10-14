package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for an actual work out that will be used within FitFlow. Contains a
 * given name, alongside a list of exercises that will be completed during the
 * work out.
 * 
 * @author narlock
 *
 */
public class Workout {
	private String name;
	private List<ExerciseItem> exercises;

	public Workout() {
		this.name = "null";
		this.exercises = new ArrayList<>();
	}

	public Workout(String name) {
		this.name = name;
		this.exercises = new ArrayList<>();
	}

	public Workout(String name, List<ExerciseItem> exercises) {
		this.name = name;
		this.exercises = exercises;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ExerciseItem> getExercises() {
		return exercises;
	}

	public void setExercises(List<ExerciseItem> exercises) {
		this.exercises = exercises;
	}

}

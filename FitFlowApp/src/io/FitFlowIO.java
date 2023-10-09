package io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import model.Exercise;
import model.ExerciseItem;
import model.Workout;
import util.FitFlowUtils;

public class FitFlowIO {
	
	public static final String DOCUMENTS_PATH = System.getProperty("user.home") + File.separatorChar + "Documents";
	public static final String DIRECTORY_PATH = DOCUMENTS_PATH + File.separatorChar + "FitFlow";
	public static final String EXERCISES_PATH = DIRECTORY_PATH + File.separatorChar + "exercises.json";
	public static final String WORKOUTS_PATH = DIRECTORY_PATH + File.separatorChar + "workouts.json";
	
	/**
	 * Reads the `Documents/FitFlow/exercises.json` file. If any directory or file within this path
	 * does not exist, it will be created alongside a default list of sample exercises. If this file
	 * does exist, the JSONArray will be parsed from the file and returned.
	 * 
	 * @return Parsed JSONArray from `exercises.json` file.
	 */
	public static final JSONArray readExercisesJsonArray() {
		File jsonFile = new File(EXERCISES_PATH);
		
		if(jsonFile.exists()) {
			System.out.println("[FitFlowIO.readExercisesJsonArray]: File Exists, parsing...");
			// Read and parse to JSON Array
			JSONParser parser = new JSONParser();
			try {
				Reader reader = new FileReader(EXERCISES_PATH);
				return (JSONArray) parser.parse(reader);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("[FitFlowIO.readExercisesJsonArray]: File does NOT exist, creating directory and returning new array.");
			
			// Create directories and file that do not exist
			File documentsDirectory = new File(DOCUMENTS_PATH);
			documentsDirectory.mkdir();
			
			File fitFlowDirectory = new File(DIRECTORY_PATH);
			fitFlowDirectory.mkdir();
			
			try {
				jsonFile.createNewFile();
				JSONArray sampleExerciseJsonArray = sampleExerciseJsonArray();
				writeExercisesJsonArrayToExercisesFile(sampleExerciseJsonArray);
				return sampleExerciseJsonArray;
			} catch (IOException proOsuGamer) {
				proOsuGamer.printStackTrace();
			}
		}
		
		return sampleExerciseJsonArray();
	}
	
	/**
	 * Writes the given exercisesArray to the `exercises.json` file.
	 * 
	 * @param exercisesArray
	 * @throws IOException if initialization of FileWriter fails
	 */
	public static void writeExercisesJsonArrayToExercisesFile(JSONArray exercisesArray) throws IOException {
		File jsonFile = new File(EXERCISES_PATH);
		FileWriter fileWriter = new FileWriter(jsonFile);
		
		// If for some reason this is empty, we need to handle it
		if(exercisesArray.isEmpty()) {
			try {
				fileWriter.write(new JSONArray().toJSONString());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fileWriter.flush();
					fileWriter.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
			}
		} else {
			try {
				fileWriter.write(exercisesArray.toJSONString());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fileWriter.flush();
					fileWriter.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
			}
		}
	}
	
	/**
	 * Checks if the `FitFlow/exercises.json` file exists in the user's documents directory.
	 * If it does not, a new `exercises.json` file will be created alongside the directory and
	 * a default set of exercises will be created for the user.
	 * If the file does exist, the exercises will be loaded into the exercises list and
	 * returned.
	 * 
	 * An example structure of the `exercises.json` will look like the following:
	 * 
	 	[
		    {
		        "name": "Pushups",
		        "imagePath": "/Pictures/pushup.png"
		    },
		    {
		        "name": "Abdominal Crunches",
		        "imagePath": "/Pictures/abcrunch.png"
		    },
		    {
		        "name": "Plank",
		        "imagePath": "/Pictures/plank.png"
		    }
		]
	 * 
	 * This represents a list of objects in the JSON format. FitFlow utilizes JSON simple to read
	 * and parse the JSON. After reading, we will iterate through each JSONObject and construct
	 * its respective Exercise object.
	 * 
	 * @return List of exercises parsed into a List<Exercise> from the JSON file
	 */
	public static List<Exercise> readExercises() {
		JSONArray exercisesJsonArray = readExercisesJsonArray();
		List<Exercise> exercises = new ArrayList<>();
		
		for(int i = 0; i < exercisesJsonArray.size(); i++) {
			JSONObject exerciseJsonObject = (JSONObject) exercisesJsonArray.get(i);
			Exercise exercise = new Exercise(
						(String) exerciseJsonObject.get("name"),
						(String) exerciseJsonObject.get("imagePath")
					);
			exercises.add(exercise);
		}
		
		return exercises;
	}
	
	/**
	 * Operation to fully replace or insert an exercises list to the exercises JSON file.
	 * 
	 * @param exercises
	 */
	@SuppressWarnings("unchecked")
	public static void upsertExercises(List<Exercise> exercises) {
		// Construct the JSONArray for the exercises list
		JSONArray exercisesJsonArray = new JSONArray();
		for(Exercise exercise : exercises) {
			JSONObject exerciseJsonObject = new JSONObject();
			exerciseJsonObject.put("name", exercise.getName());
			exerciseJsonObject.put("imagePath", exercise.getImagePath());
			exercisesJsonArray.add(exerciseJsonObject);
		}
		
		// Call the writeExercisesJsonArrayToExercisesFile method with constructed array
		try {
			writeExercisesJsonArrayToExercisesFile(exercisesJsonArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static final JSONArray readWorkoutsJsonArray() {
		File jsonFile = new File(WORKOUTS_PATH);
		
		if(jsonFile.exists()) {
			System.out.println("[FitFlowIO.readWorkoutsJsonArray]: File Exists, parsing...");
			// Read and parse to JSON Array
			JSONParser parser = new JSONParser();
			try {
				Reader reader = new FileReader(WORKOUTS_PATH);
				return (JSONArray) parser.parse(reader);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("[FitFlowIO.readWorkoutsJsonArray]: File does NOT exist, creating directory and returning new array.");
			
			// Create directories and file that do not exist
			File documentsDirectory = new File(DOCUMENTS_PATH);
			documentsDirectory.mkdir();
			
			File fitFlowDirectory = new File(DIRECTORY_PATH);
			fitFlowDirectory.mkdir();
			
			try {
				jsonFile.createNewFile();
				JSONArray sampleWorkoutsJsonArray = sampleWorkoutJsonArray();
				writeExercisesJsonArrayToExercisesFile(sampleWorkoutsJsonArray);
				return sampleWorkoutsJsonArray;
			} catch (IOException proOsuGamer) {
				proOsuGamer.printStackTrace();
			}
		}
		
		return sampleWorkoutJsonArray();
	}
	
	public static void writeWorkoutsJsonArrayToWorkoutsFile(JSONArray workoutsArray) throws IOException {
		File jsonFile = new File(WORKOUTS_PATH);
		FileWriter fileWriter = new FileWriter(jsonFile);
		
		// If for some reason this is empty, we need to handle it
		if(workoutsArray.isEmpty()) {
			try {
				fileWriter.write(new JSONArray().toJSONString());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fileWriter.flush();
					fileWriter.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
			}
		} else {
			try {
				fileWriter.write(workoutsArray.toJSONString());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fileWriter.flush();
					fileWriter.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
			}
		}
	}
	
	public static List<Workout> readWorkouts() {
		List<Exercise> exercises = readExercises();
		// Read "Workouts" file and create individual work out
		// A work out will contain an ordered list of Exercises,
		// where each exercise has a work and break time.
		// In the JSON, it will be stored as the index of the
		// exercise, alongside the work and break time in seconds.
		
		/*
		 * An example structure of the JSON is the following:
			[
			    {
			        "name": "Morning Stretch",
			        "exercises": [
			            {
			                "index": 0,
			                "work": 60,
			                "break": 60
			            },
			            {
			                "index": 1,
			                "work": 60,
			                "break": 60
			            },
			            {
			
			                "index": 2,
			                "work": 60,
			                "break": 60
			            }
			        ]
			    },
			    {
			        "name": "Abdominal Workout",
			        "exercises": [
			            {
			                "index": 3,
			                "work": 60,
			                "break": 60
			            },
			            {
			                "index": 4,
			                "work": 60,
			                "break": 60
			            },
			            {
			
			                "index": 5,
			                "work": 60,
			                "break": 60
			            }
			        ]
			    }
			]
		 */
		
		// READ File and obtain JSON structure
		List<Workout> workouts = new ArrayList<Workout>();
		
		// For each workout stored in the json, let's create the items
		JSONArray workoutsJsonArray = readWorkoutsJsonArray();
		for(int i = 0; i < workoutsJsonArray.size(); i++) {
			JSONObject workoutJsonObject = (JSONObject) workoutsJsonArray.get(i);
			
			Workout workout = new Workout((String) workoutJsonObject.get("name"));
			List<ExerciseItem> exerciseItems = new ArrayList<ExerciseItem>();
			JSONArray exercisesJsonArray = (JSONArray) workoutJsonObject.get("exercises");
			
			for(int j = 0; j < exercisesJsonArray.size(); j++) {
				JSONObject exerciseJsonObject = (JSONObject) exercisesJsonArray.get(i);
				ExerciseItem exerciseItem = new ExerciseItem(
						exercises.get((int) (long) exerciseJsonObject.get("index")), 
						(long) exerciseJsonObject.get("work"), 
						(long) exerciseJsonObject.get("break")
					);
				exerciseItems.add(exerciseItem);
			}
			workout.setExercises(exerciseItems);
			workouts.add(workout);
		}
		
		return workouts;
	}
	
	@SuppressWarnings("unchecked")
	public static void upsertWorkouts(List<Workout> workouts) {
		// Construct the JSONArray for the workouts list
		JSONArray workoutsJsonArray = new JSONArray();
		
		for(Workout workout : workouts) {
			JSONObject workoutJsonObject = new JSONObject();
			workoutJsonObject.put("name", workout.getName());
			
			JSONArray exercisesJsonArray = new JSONArray();
			for(ExerciseItem exerciseItem : workout.getExercises()) {
				JSONObject exerciseJsonObject = new JSONObject();
				exerciseJsonObject.put("index", (long) FitFlowUtils.getExerciseIndexFromExerciseName(exerciseItem.getExercise().getName()));
				exerciseJsonObject.put("work", (long) exerciseItem.getWorkTimeInSeconds());
				exerciseJsonObject.put("break", (long) exerciseItem.getBreakTimeInSeconds());
				exercisesJsonArray.add(exerciseJsonObject);
			}
			workoutJsonObject.put("exercises", exercisesJsonArray);
			
			workoutsJsonArray.add(workoutJsonObject);
		}
		
		// Call the writeExercisesJsonArrayToExercisesFile method with constructed array
		try {
			writeWorkoutsJsonArrayToWorkoutsFile(workoutsJsonArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private static JSONArray sampleExerciseJsonArray() {
		JSONArray jsonArray = new JSONArray();
		
		JSONObject pushupsObject = new JSONObject();
		pushupsObject.put("name", "Pushups");
		pushupsObject.put("imagePath", "/Pushups.png");
		jsonArray.add(pushupsObject);
		
		JSONObject abdominalCrunchesObject = new JSONObject();
		abdominalCrunchesObject.put("name", "Abdominal Crunches");
		abdominalCrunchesObject.put("imagePath", "/AbCrunch.png");
		jsonArray.add(abdominalCrunchesObject);
		
		JSONObject plankObject = new JSONObject();
		plankObject.put("name", "Plank");
		plankObject.put("imagePath", "/Plank.png");
		jsonArray.add(plankObject);
		
		return jsonArray;
	}
	
	@SuppressWarnings("unchecked")
	private static JSONArray sampleWorkoutJsonArray() {
		JSONArray jsonArray = new JSONArray();
		
		JSONObject simpleWorkoutObject = new JSONObject();
		simpleWorkoutObject.put("name", "Simple Workout");
		JSONArray simpleWorkoutObjectExercises = new JSONArray();
		JSONObject simpleWorkoutObjectExercise_Pushups = new JSONObject();
		simpleWorkoutObjectExercise_Pushups.put("index", 0);
		simpleWorkoutObjectExercise_Pushups.put("work", 25);
		simpleWorkoutObjectExercise_Pushups.put("break", 10);
		JSONObject simpleWorkoutObjectExercise_Crunches = new JSONObject();
		simpleWorkoutObjectExercise_Crunches.put("index", 1);
		simpleWorkoutObjectExercise_Crunches.put("work", 25);
		simpleWorkoutObjectExercise_Crunches.put("break", 10);
		JSONObject simpleWorkoutObjectExercise_Plank = new JSONObject();
		simpleWorkoutObjectExercise_Crunches.put("index", 2);
		simpleWorkoutObjectExercise_Crunches.put("work", 25);
		simpleWorkoutObjectExercise_Crunches.put("break", 10);
		simpleWorkoutObjectExercises.add(simpleWorkoutObjectExercise_Pushups);
		simpleWorkoutObjectExercises.add(simpleWorkoutObjectExercise_Crunches);
		simpleWorkoutObjectExercises.add(simpleWorkoutObjectExercise_Plank);
		simpleWorkoutObject.put("exercises", simpleWorkoutObjectExercises);
		
		JSONObject advancedWorkoutObject = new JSONObject();
		advancedWorkoutObject.put("name", "Simple Workout");
		JSONArray advancedWorkoutObjectExercises = new JSONArray();
		JSONObject advancedWorkoutObjectExercise_Pushups = new JSONObject();
		advancedWorkoutObjectExercise_Pushups.put("index", 0);
		advancedWorkoutObjectExercise_Pushups.put("work", 25);
		advancedWorkoutObjectExercise_Pushups.put("break", 10);
		JSONObject advancedWorkoutObjectExercise_Crunches = new JSONObject();
		advancedWorkoutObjectExercise_Crunches.put("index", 1);
		advancedWorkoutObjectExercise_Crunches.put("work", 25);
		advancedWorkoutObjectExercise_Crunches.put("break", 10);
		JSONObject advancedWorkoutObjectExercise_Plank = new JSONObject();
		advancedWorkoutObjectExercise_Crunches.put("index", 2);
		advancedWorkoutObjectExercise_Crunches.put("work", 25);
		advancedWorkoutObjectExercise_Crunches.put("break", 10);
		advancedWorkoutObjectExercises.add(advancedWorkoutObjectExercise_Pushups);
		advancedWorkoutObjectExercises.add(advancedWorkoutObjectExercise_Crunches);
		advancedWorkoutObjectExercises.add(advancedWorkoutObjectExercise_Plank);
		advancedWorkoutObjectExercises.add(advancedWorkoutObjectExercise_Pushups);
		advancedWorkoutObjectExercises.add(advancedWorkoutObjectExercise_Crunches);
		advancedWorkoutObjectExercises.add(advancedWorkoutObjectExercise_Plank);
		advancedWorkoutObjectExercises.add(advancedWorkoutObjectExercise_Pushups);
		advancedWorkoutObjectExercises.add(advancedWorkoutObjectExercise_Crunches);
		advancedWorkoutObjectExercises.add(advancedWorkoutObjectExercise_Plank);
		advancedWorkoutObject.put("exercises", advancedWorkoutObjectExercises);
		
		jsonArray.add(simpleWorkoutObject);
		jsonArray.add(advancedWorkoutObject);
		
		return jsonArray;
	}
}

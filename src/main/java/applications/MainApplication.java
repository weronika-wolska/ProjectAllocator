package applications;

// javafx imports
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.*;
import javafx.scene.control.TextField;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

import javax.lang.model.util.ElementScanner6;

// package imports
import entities.*;
import repositories.*;
import interfaces.ApplicationInterfaceImplementation;
import exceptions.*;
import windows.InvalidArgumentErrorBox;
import windows.*;

public class MainApplication extends Application {

    Button studentTemplateDownload;
    Button projectTemplateDownload;
    Button staffTemplateDownload;
    Button geneticAlgorithm;
    Button simulatedAnnealing;
    Button hillClimbing;
    Button goToGPA, setWeight, downloadSolution;
    TextField getStudents, getStaff, getProjects, getPreferences;
    Slider getGpaWeight;
    TableView solution = null;
    CandidateSolution bestSolutionFound;
    ApplicationInterfaceImplementation appInterface = new ApplicationInterfaceImplementation();
    ProjectRepository projects;
    StudentRepository students;
    StaffRepository staff;
    InvalidArgumentErrorBox errorBox = new InvalidArgumentErrorBox();
    PathInputBox pathInput = new PathInputBox();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        displayApplication(primaryStage);
        primaryStage.show();

    }

    public void displayApplication(Stage primaryStage)throws Exception{
         // final Stage stage = primaryStage;
         primaryStage.setTitle("Team Partial");
         Label emptyLabel = new Label("  ");
         Label downloadOption = new Label("Download Templates: ");
         studentTemplateDownload = new Button("Students");
         projectTemplateDownload = new Button("Projects");
         staffTemplateDownload = new Button("Staff");
         Label inputFiles = new Label("Input your files: ");
 
         VBox window = new VBox(30);
         window.setAlignment(Pos.TOP_CENTER);
         // window.getChildren().add(emptyLabel);
         window.getChildren().add(emptyLabel);
         window.getChildren().add(downloadOption);
 
         // display the buttons beside each other rather than underneath
         HBox downloadButtons = new HBox(20);
         downloadButtons.setAlignment(Pos.CENTER);
         downloadButtons.getChildren().add(studentTemplateDownload);
         downloadButtons.getChildren().add(projectTemplateDownload);
         downloadButtons.getChildren().add(staffTemplateDownload);
         window.getChildren().add(downloadButtons);
         window.getChildren().add(inputFiles);
 
         // input boxes
         HBox staffInput = new HBox(10);
         staffInput.setAlignment(Pos.CENTER);
         Label staffLabel = new Label("Staff:  ");
         TextField getStaff = new TextField();
         getStaff.setMaxWidth(500);
         getStaff.setText("src/main/resources/staff.xlsx");
         staffInput.getChildren().addAll(staffLabel, getStaff);
 
         HBox projectInput = new HBox(10);
         projectInput.setAlignment(Pos.CENTER);
         Label projectsLabel = new Label("Projects:  ");
         TextField getProjects = new TextField();
         getProjects.setMaxWidth(500);
         getProjects.setText("src/main/resources/projects60.xlsx");
         projectInput.getChildren().addAll(projectsLabel, getProjects);
 
         HBox studentInput = new HBox();
         studentInput.setAlignment(Pos.CENTER);
         Label studentLabel = new Label("Students:  ");
         TextField getStudents = new TextField();
         getStudents.setMaxWidth(500);
         getStudents.setText("src/main/resources/students.xlsx");
         studentInput.getChildren().addAll(studentLabel, getStudents);
 
         HBox preferencesInput = new HBox(10);
         preferencesInput.setAlignment(Pos.CENTER);
         Label studentPreferencesLabel = new Label("Student Preferences:  ");
         TextField getPreferences = new TextField();
         getPreferences.setMaxWidth(500);
         getPreferences.setText("src/main/resources/studentPreferences60.xlsx");
         preferencesInput.getChildren().addAll(studentPreferencesLabel, getPreferences);
         Button goToAlgorithms = new Button("Continue");
         goToGPA = new Button("Continue");
 
         window.getChildren().addAll(staffInput, projectInput, studentInput, preferencesInput, goToGPA);
 
         Scene scene = new Scene(window, 800, 500);
         primaryStage.setScene(scene);
         try{
             if(students.getSize()==projects.getSize()){
                 bestSolutionFound = new CandidateSolution(students.getStudents(), projects.getProjects());
             } else{
                 ArrayList<Project> p = new ArrayList<>();
                 for(int i=0;i<students.getSize();i++){
                     p.add(projects.getProject(i));
                 }
                 bestSolutionFound = new CandidateSolution(students.getStudents(), p);
             }
         } catch(NullPointerException e){
             errorBox.displayErrorBox();
             primaryStage.setScene(scene);
         }
         // set GPA weight window
         
         Label gpa = new Label("Set GPA weight: ");
         Button setWeight = new Button("Continue");
         getGpaWeight = new Slider(0, 1, 0.5);
         getGpaWeight.setMaxWidth(500);
         getGpaWeight.setShowTickLabels(true);
         getGpaWeight.setShowTickMarks(true);
         getGpaWeight.setMajorTickUnit(0.25f);
         getGpaWeight.setBlockIncrement(0.1f);
 
         VBox gpaBox = new VBox(30);
         gpaBox.getChildren().addAll(gpa, getGpaWeight, setWeight);
         gpaBox.setAlignment(Pos.CENTER);
 
         Scene gpaScene = new Scene(gpaBox, 800, 500);
 
         // choose algorithms window
         Label chooseAlgorithm = new Label("Choose an algorithm: ");
         Button simulatedAnnealing = new Button("Simulated Annealing");
         Button geneticAlgorithm = new Button("Genetic Algorithm");
         Button hillClimbing = new Button("Hill Climbing");
 
         VBox algorithms = new VBox(40);
         algorithms.setAlignment(Pos.TOP_CENTER);
         algorithms.getChildren().addAll(emptyLabel, chooseAlgorithm, simulatedAnnealing, hillClimbing,
                 geneticAlgorithm);
         Scene chooseAlgorithmScene = new Scene(algorithms, 800, 500);
        
         // display candidate solution window
         Label outcome = new Label("This is the best solution we found");
         solution = appInterface.showCandidateSolution(bestSolutionFound);
         Label averageSatisfaction = new Label(appInterface.getAverageStudentSatisfaction(bestSolutionFound) + " / 10");
         Button downloadSolution = new Button("Download");
         VBox displaySolution = new VBox(20);
         displaySolution.setAlignment(Pos.CENTER);
         displaySolution.getChildren().addAll(outcome, downloadSolution);
         Scene displaySolutionScene = new Scene(displaySolution, 800, 500);
         geneticAlgorithm.setOnAction(e -> primaryStage.setScene(displaySolutionScene));
         simulatedAnnealing.setOnAction(e -> primaryStage.setScene(displaySolutionScene));
         hillClimbing.setOnAction(e -> primaryStage.setScene(displaySolutionScene));
 
         // event handler
         studentTemplateDownload.setOnAction(e -> {
             String path = pathInput.displayBox();
             appInterface.downloadStudentTemplate(path);
         });
         projectTemplateDownload.setOnAction(e -> {
             String path = pathInput.displayBox();
             appInterface.downloadProjectTemplate(path);
         });
         staffTemplateDownload.setOnAction(e -> {
             String path = pathInput.displayBox();
             appInterface.downloadStaffTemplate(path);
         });
         goToGPA.setOnAction(e -> {
             
             
             String staffPath = getStaff.getText();
             System.out.println("Staff Path: " +staffPath);
             //staff = appInterface.readStaffInput("src/main/resources/staff.xlsx");
             String projectPath = getProjects.getText();
             System.out.println("Project Path: "+projectPath);
             //projects = appInterface.readProjectInput("src/main/resources/projects500.xlsx", staff);
             String studentPath = getStudents.getText();
             System.out.println("Student Path: " + studentPath);
             //students = appInterface.readStrudentInput("src/main/resources/students.xlsx", projects);
             String preferencesPath = getPreferences.getText(); 
             System.out.println("Preferences Path: " + preferencesPath);
             //students = appInterface.readStudentPreferencesInput("src/main/resources/studentPreferences60.xlsx", students, projects);
             /*try {
                 students = appInterface.readStudentPreferencesInput("src/main/resources/studentPreferences60.xlsx", students, projects);
             } catch (InvalidArgumentException e2) {
                 
                 errorBox.displayErrorBox();
                 primaryStage.setScene(scene);
             } */
             primaryStage.setScene(gpaScene);
         });
         setWeight.setOnAction(e -> {
             double weight = getGpaWeight.getValue();
             System.out.println("GPA weight: " + weight);
             appInterface.getGPAWeight(weight);
             primaryStage.setScene(chooseAlgorithmScene);
         });
         
         geneticAlgorithm.setOnAction(e -> {
             System.out.println("Genetic Algorithm was chosen");
             //bestSolutionFound = appInterface.applyGeneticAlgorithm(students, projects);
             //solution = appInterface.showCandidateSolution(bestSolutionFound);
             primaryStage.setScene(displaySolutionScene);
         });
         simulatedAnnealing.setOnAction(e -> {
             //bestSolutionFound = appInterface.applyGeneticAlgorithm(students, projects);
             //bestSolutionFound = appInterface.applySimulatedAnnealing(bestSolutionFound);
             //solution = appInterface.showCandidateSolution(bestSolutionFound);
             System.out.println("Simulated Annealing was chosen");
             primaryStage.setScene(displaySolutionScene);
         });
         hillClimbing.setOnAction(e -> {
            // bestSolutionFound = appInterface.applyGeneticAlgorithm(students, projects);
             //bestSolutionFound = appInterface.applyHillClimbing(bestSolutionFound);
             //solution = appInterface.showCandidateSolution(bestSolutionFound);
             System.out.println("Hill Climbing was chosen");
             primaryStage.setScene(displaySolutionScene);
         });
         downloadSolution.setOnAction(e -> {
             String path = pathInput.displayBox();
             System.out.println("Download solution request made ");
             //appInterface.downloadCandidateSolution(path, bestSolutionFound);
         });
 
        // primaryStage.show();
 
    }

   
}
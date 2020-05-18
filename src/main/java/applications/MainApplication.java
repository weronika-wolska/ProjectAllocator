package applications;

// javafx imports
import entities.TableRow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.*;

import javax.swing.*;

import javafx.embed.swing.JFXPanel;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

import javax.lang.model.util.ElementScanner6;

// package imports
import entities.*;
import repositories.*;
import interfaces.ApplicationInterfaceImplementation;
import exceptions.*;
import windows.InvalidArgumentErrorBox;
import windows.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MainApplication extends Application {

    Stage stage;
    Button studentTemplateDownload;
    Button projectTemplateDownload;
    Button staffTemplateDownload;
    Button geneticAlgorithm;
    Button simulatedAnnealing;
    Button hillClimbing;
    Button goToGPA, setWeight, downloadSolution, differentAlgorithm;
    TextField getStudents, getStaff, getProjects, getPreferences;
    Slider getGpaWeight;
    TableColumn<TableRow, String> projectColumn;
    @FXML
    TableView<TableRow> solution;
    ObservableList<TableRow> solutionList;
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

            stage = primaryStage;
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
        //window.setId("background-image");

        // input boxes
        HBox staffInput = new HBox(10);
        staffInput.setAlignment(Pos.CENTER);
        Label information = new Label("IMPORTANT: Please make sure the file paths you enter ar in the format C/Users/mary/...");
        Label staffLabel = new Label("Staff:  ");
        TextField getStaff = new TextField();
        getStaff.setMaxWidth(500);
        staffInput.getChildren().addAll( staffLabel, getStaff);

        HBox projectInput = new HBox(10);
        projectInput.setAlignment(Pos.CENTER);
        Label projectsLabel = new Label("Projects:  ");
        TextField getProjects = new TextField();
        getProjects.setMaxWidth(500);
        projectInput.getChildren().addAll(projectsLabel, getProjects);

        HBox studentInput = new HBox();
        studentInput.setAlignment(Pos.CENTER);
        Label studentLabel = new Label("Students:  ");
        TextField getStudents = new TextField();
        getStudents.setMaxWidth(500);

        studentInput.getChildren().addAll(studentLabel, getStudents);

        HBox preferencesInput = new HBox(10);
        preferencesInput.setAlignment(Pos.CENTER);
        Label studentPreferencesLabel = new Label("Student Preferences:  ");
        TextField getPreferences = new TextField();
        getPreferences.setMaxWidth(500);
        preferencesInput.getChildren().addAll(studentPreferencesLabel, getPreferences);
        Button goToAlgorithms = new Button("Continue");
        goToGPA = new Button("Continue");

        Label or = new Label("OR");
        or.setMinSize(20, 20);

        HBox oneFileInput = new HBox(10);
        oneFileInput.setAlignment(Pos.CENTER);
        Label oneFileLabel = new Label("Enter one file: ");
        TextField getOneFile = new TextField();
        oneFileInput.getChildren().addAll(oneFileLabel, getOneFile);


        window.getChildren().addAll(information, staffInput, projectInput, studentInput, preferencesInput, or, oneFileInput, goToGPA);
        System.out.println("1");
        Scene scene = new Scene(window, 1000, 700);
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
            //errorBox.displayErrorBox();
            primaryStage.setScene(scene);
        }

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
            try {
                if(!getStaff.getText().isEmpty()){
                    String staffPath = getStaff.getText();
                    System.out.println("Staff Path: " +staffPath);
                    this.staff = appInterface.readStaffInput(staffPath);
                    System.out.println(staff.getSize());
                    String projectPath = getProjects.getText();
                    System.out.println("Project Path: "+projectPath);
                    this.projects = appInterface.readProjectInput(projectPath, staff);
                    System.out.println(projects.getSize());
                    String studentPath = getStudents.getText();
                    System.out.println("Student Path: " + studentPath);
                    this.students = appInterface.readStudentInput(studentPath, projects);
                    System.out.println(students.getSize());
                    String preferencesPath = getPreferences.getText();
                    System.out.println("Preferences Path: " + preferencesPath);
                    students = appInterface.readStudentPreferencesInput("src/main/resources/studentPreferences60.xlsx", students, projects);
                    System.out.println(students.getSize());
                } else{
                    String filePath = getOneFile.getText();
                    try{
                        appInterface.readOneInputFile(filePath);
                        this.students = appInterface.getStudentRepository();
                        this.projects = appInterface.getProjectRepository();
                    } catch (Exception e7) {
                        errorBox.displayErrorBox(e7.getMessage());
                        e7.printStackTrace();
                    }

                }
                displayGetGpaWeightScene();
                //primaryStage.setScene(gpaScene);
            } catch (Exception e2) {

                errorBox.displayErrorBox();
                primaryStage.setScene(scene);
            }
        });
    }

    public void displayGetGpaWeightScene() {
        // set GPA weight window
        System.out.println("2");
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
        System.out.println("3");
        stage.setScene(gpaScene);

        setWeight.setOnAction(e -> {
            double weight = getGpaWeight.getValue();
            System.out.println("GPA weight: " + weight);
            appInterface.getGPAWeight(weight);
            displayChooseAlgorithmScene();
            //primaryStage.setScene(chooseAlgorithmScene);
        });
    }

    public void displayChooseAlgorithmScene() {
        solution = new TableView<>();
        projectColumn = new TableColumn<>();
        Label emptyLabel = new Label("  ");
        // choose algorithms window
        Label chooseAlgorithm = new Label("Choose an algorithm: ");
        Button simulatedAnnealing = new Button("Simulated Annealing");
        Button geneticAlgorithm = new Button("Genetic Algorithm");
        Button hillClimbing = new Button("Hill Climbing");

        VBox algorithms = new VBox(40);
        algorithms.setAlignment(Pos.TOP_CENTER);
        algorithms.getChildren().addAll(emptyLabel, chooseAlgorithm, simulatedAnnealing, hillClimbing,
                geneticAlgorithm);
        Scene chooseAlgorithmScene = new Scene(algorithms, 1000, 700);
        stage.setScene(chooseAlgorithmScene);

        geneticAlgorithm.setOnAction(e -> {
            System.out.println("Genetic Algorithm was chosen");
            try{
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new JFXPanel(); // this will prepare JavaFX toolkit and environment
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                });
                System.out.println("Hi1");
                if(this.students==null && this.projects==null){
                    System.out.println("Hi2");
                    try {
                        GeneticAlgorithm ga = new GeneticAlgorithm(this.students, this.projects, this.appInterface.getGPAWeight());
                        this.bestSolutionFound = ga.applyAlgorithm();
                        appInterface.setBestSolution(this.bestSolutionFound);
                        this.bestSolutionFound = appInterface.getBestSolution();
                    }
                    catch(Exception ee) {
                        System.out.println("Hi2.5");
                    }
                    System.out.println("Hi3");
                } else {
                    System.out.println("Hi4");
                    GeneticAlgorithm ga = new GeneticAlgorithm(this.students, this.projects, 0.5);
                    this.bestSolutionFound = ga.applyAlgorithm();
                    System.out.println("Hi5");
                }
                System.out.println("try entered");
                //this.bestSolutionFound = appInterface.applyGeneticAlgorithm(this.students, this.projects);
                if(bestSolutionFound!=null) System.out.println("solution okay");
                else System.out.println("solution is null ?????");
                this.solutionList = appInterface.showCandidateSolution(bestSolutionFound);
                if(this.solutionList==null) System.out.println("list empty tho");
                else{
                    System.out.println(this.solutionList.toString());
                }
                this.projectColumn = new TableColumn<TableRow, String>("Project ");
                this.projectColumn.setMinWidth(300);
                this.projectColumn.setCellFactory(column -> {
                    /*return new TableCell<TableRow, String>(){
                        @Override
                        protected void updateItem(String item, boolean empty){
                            if (item == null || empty) { //If the cell is empty
                                setText(null);
                                setStyle("");
                            } else {
                                setText(item);
                                TableRow row = solutionList.get(getIndex());
                                Student student = students.getStudentById(row.getId());
                                Project project = projects.getProjectByName(row.getProject());
                                try {
                                    if (!student.getPreferences().contains(project)) {
                                        setTextFill(Color.RED);
                                    } else if (student.getPreferences().get(0) == project) {
                                        setTextFill(Color.BLUE);
                                    }else if (student.getPreferences().get(1) == project || student.getPreferences().get(2) == project) {
                                        setTextFill(Color.GREEN);
                                    } else if (getTableView().getItems().contains(project) && getTableView().getItems().indexOf(project) != getIndex()) {
                                        setTextFill(Color.MAROON);
                                    } else {
                                        setTextFill(Color.BLACK);
                                    }
                                } catch(NullPointerException ne){
                                    ne.printStackTrace();
                                }

                            }
                        }
                    };*/
                    return new TableCell<TableRow, String>(){
                        @Override
                        protected void updateItem(String item, boolean empty){
                            System.out.println("THERE");
                            if (item == null || empty) { //If the cell is empty
                                setText(null);
                                setStyle("");
                                System.out.println("The cell was empty");
                            } else {
                                setText(item);
                                TableRow row = solutionList.get(getIndex());
                                System.out.println("This is the row:" + row);
                                System.out.println("This is the students:" + students.toString());
                                //Student student = students.getStudentById(row.getId());
                                Student student = appInterface.getFullStudentRepository().getStudentById(row.getId());
                                //Project project = projects.getProjectByName(row.getProject());
                                Project project = appInterface.getFullProjectRepository().getProjectByName(row.getProject());
                                System.out.println("meant to start changing colours here");
                                try {
                                    if (!student.getPreferences().contains(project)) {
                                        setTextFill(Color.RED);
                                        System.out.println("red");
                                    } else if (student.getPreferences().get(0) == project) {
                                        setTextFill(Color.BLUE);
                                        System.out.println("blue");
                                    }else if (student.getPreferences().get(1) == project || student.getPreferences().get(2) == project) {
                                        setTextFill(Color.GREEN);
                                        System.out.println("green");
                                    } else if (getTableView().getItems().contains(project) && getTableView().getItems().indexOf(project) != getIndex()) {
                                        setTextFill(Color.MAROON);
                                        System.out.println("maroon");
                                    } else {
                                        setTextFill(Color.BLACK);
                                        System.out.println("black");
                                    }
                                } catch(NullPointerException ne){
                                    ne.printStackTrace();
                                    System.out.println("colour assignment failed");
                                }
                                System.out.println("colour meant to be changed after this");
                            }
                        }
                    };
                });
                this.solution.setItems(this.solutionList);
                //if(this.bestSolutionFound!=null)System.out.println(this.bestSolutionFound.size());
                //else System.out.println("candidate solution is null");
                //for(int i=0; i<this.bestSolutionFound.size(); i++){

                // }
                //primaryStage.setScene(displaySolutionScene);
                displaySolution(this.projectColumn);
            } catch (Exception e4){
                System.out.println("error somewhere");
                //e4.getCause().getMessage();
                //primaryStage.setScene(scene);
                //stage.setScene(scene);
                displaySolution(this.projectColumn);
            }

        });
        simulatedAnnealing.setOnAction(e -> {
            System.out.println("applying algorithm");
            bestSolutionFound = appInterface.applySimulatedAnnealing();
            try {
                this.bestSolutionFound = appInterface.getBestSolution();
            } catch (InvalidArgumentException invalid){
                InvalidArgumentErrorBox.displayErrorBox();
                invalid.printStackTrace();
            }
            System.out.println("assigning value to solutionList");
            this.solutionList = appInterface.showCandidateSolution(this.bestSolutionFound);
            System.out.println("setting table as solutionList");
            this.solution.setItems(this.solutionList);

            //projectColumn = new TableColumn<>();
            /*projectColumn.setCellFactory(column -> {

            System.out.println("should enter lambda");
            this.projectColumn = new TableColumn<TableRow, String>("Project ");
            this.projectColumn.setMinWidth(300);
            this.projectColumn.setCellValueFactory(new PropertyValueFactory<>("project"));
            projectColumn.setCellFactory(column -> {

                return new TableCell<TableRow, String>(){
                    @Override
                    protected void updateItem(String item, boolean empty){
                        System.out.println("THERE");
                        if (item == null || empty) { //If the cell is empty
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item);
                            TableRow row = solutionList.get(getIndex());
                            System.out.println("This is the row:" + row);
                            System.out.println("This is the students:" + students.toString());
                            Student student = students.getStudentById(row.getId());
                            Project project = projects.getProjectByName(row.getProject());
                            System.out.println("meant to start changing colours here");
                            try {
                                if (!student.getPreferences().contains(project)) {
                                    setTextFill(Color.RED);
                                    System.out.println("red");
                                } else if (student.getPreferences().get(0) == project) {
                                    setTextFill(Color.BLUE);
                                    System.out.println("blue");
                                }else if (student.getPreferences().get(1) == project || student.getPreferences().get(2) == project) {
                                    setTextFill(Color.GREEN);
                                    System.out.println("green");
                                } else if (getTableView().getItems().contains(project) && getTableView().getItems().indexOf(project) != getIndex()) {
                                    setTextFill(Color.MAROON);
                                    System.out.println("maroon");
                                } else {
                                    setTextFill(Color.BLACK);
                                    System.out.println("black");
                                }
                            } catch(NullPointerException ne){
                                ne.printStackTrace();
                                System.out.println("colour assignment failed");
                            }
                            System.out.println("colour meant to be changed after this");
                        }
                    }
                };

            });*/
            System.out.println("Simulated Annealing was chosen");
            displaySolution(this.projectColumn);


        });
        hillClimbing.setOnAction(e -> {
            //bestSolutionFound = appInterface.applyGeneticAlgorithm(students, projects);
            System.out.println("applying algorithm");
            bestSolutionFound = appInterface.applyHillClimbing();
            System.out.println(bestSolutionFound.size());
            try {
                this.bestSolutionFound = appInterface.getBestSolution();
            } catch (Exception exc){
                InvalidArgumentErrorBox.displayErrorBox();
                exc.printStackTrace();
            }
            System.out.println(this.bestSolutionFound.size());
            System.out.println("assigning value to solutionList");
            this.solutionList = appInterface.showCandidateSolution(this.bestSolutionFound);
            System.out.println("setting table as solutionList");
            this.solution.setItems(this.solutionList);
            System.out.println("should enter lambda");
            this.projectColumn = new TableColumn<TableRow, String>("Project ");
            this.projectColumn.setMinWidth(300);
            this.projectColumn.setCellValueFactory(new PropertyValueFactory<>("project"));
            projectColumn.setCellFactory(column -> {

                return new TableCell<TableRow, String>(){
                    @Override
                    protected void updateItem(String item, boolean empty){
                        System.out.println("THERE");
                        if (item == null || empty) { //If the cell is empty
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item);
                            TableRow row = solutionList.get(getIndex());
                            System.out.println("This is the row:" + row);
                            System.out.println("This is the students:" + students.toString());
                            //Student student = students.getStudentById(row.getId());
                            Student student = appInterface.getFullStudentRepository().getStudentById(row.getId());
                            //Project project = projects.getProjectByName(row.getProject());
                            Project project = appInterface.getFullProjectRepository().getProjectByName(row.getProject());
                            System.out.println("meant to start changing colours here");
                            try {
                                if (!student.getPreferences().contains(project)) {
                                    setTextFill(Color.RED);
                                    System.out.println("red");
                                } else if (student.getPreferences().get(0) == project) {
                                    setTextFill(Color.BLUE);
                                    System.out.println("blue");
                                }else if (student.getPreferences().get(1) == project || student.getPreferences().get(2) == project) {
                                    setTextFill(Color.GREEN);
                                    System.out.println("green");
                                } else if (getTableView().getItems().contains(project) && getTableView().getItems().indexOf(project) != getIndex()) {
                                    setTextFill(Color.MAROON);
                                    System.out.println("maroon");
                                } else {
                                    setTextFill(Color.BLACK);
                                    System.out.println("black");
                                }
                            } catch(NullPointerException ne){
                                ne.printStackTrace();
                                System.out.println("colour assignment failed");
                            }
                            System.out.println("colour meant to be changed after this");

                        }
                    }
                };
            });
            //solution = appInterface.showCandidateSolution(bestSolutionFound);
            System.out.println("Hill Climbing was chosen");
            //primaryStage.setScene(displaySolutionScene);
            displaySolution(projectColumn);
        });
    }

    public void displaySolution(TableColumn<TableRow, String> projectColumn) {
        // display candidate solution window
        Label outcome = new Label("This is the best solution we found: ");

        // colour key
        HBox key = new HBox(30);
        key.setAlignment(Pos.CENTER);
        Label red = new Label("Assigned project not in preference");
        red.setTextFill(Color.RED);
        Label blue = new Label("Gets first preference");
        blue.setTextFill(Color.BLUE);
        key.getChildren().addAll(red, blue);
        HBox key2 = new HBox(30);
        key2.setAlignment(Pos.CENTER);
        Label green = new Label("Gets second or third preference");
        green.setTextFill(Color.GREEN);
        Label maroon = new Label("duplicate projects assigned");
        maroon.setTextFill(Color.MAROON);
        key2.getChildren().addAll(green, maroon);
        // prevents initialization error for TableView
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JFXPanel(); // this will prepare JavaFX toolkit and environment
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
        try{
            //this.solution = new TableView<TableRow>();
            TableColumn<TableRow, Long> studentIDColumn = new TableColumn("Student ID");
            studentIDColumn.setMinWidth(100);
            studentIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            TableColumn<TableRow, String> studentNameColumn = new TableColumn("Student name");
            studentNameColumn.setMinWidth(200);
            studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            //this.projectColumn = new TableColumn<>("Assigned Project");
            //this.projectColumn.setMinWidth(300);
            //this.projectColumn.setCellValueFactory(new PropertyValueFactory<>("project"));

            this.projectColumn = new TableColumn<>("Assigned Project");
            this.projectColumn.setMinWidth(300);
            this.projectColumn.setCellValueFactory(new PropertyValueFactory<>("project"));



                System.out.println("I should have gotten project column");
            this.solution.getColumns().addAll(studentIDColumn,studentNameColumn, projectColumn);

        } catch (Exception x){
            x.getCause();
            x.printStackTrace();
        }

        //solution = appInterface.showCandidateSolution(bestSolutionFound);
        if(bestSolutionFound == null) System.out.println("best solution is null");
        Label averageSatisfaction = new Label("Average student satisfaction: "+appInterface.getAverageStudentSatisfaction(bestSolutionFound) + " / 10");
        Button downloadSolution = new Button("Download");
        VBox displaySolution = new VBox(20);
        displaySolution.setAlignment(Pos.CENTER);
        displaySolution.getChildren().addAll(outcome, key, key2);
        try{
            displaySolution.getChildren().add(this.solution);
        } catch(Exception a){
            a.getCause();
            a.printStackTrace();
        }
        differentAlgorithm = new Button("Try different algorithm");
        displaySolution.getChildren().addAll(averageSatisfaction, downloadSolution, differentAlgorithm);
        //displaySolution.getChildren().addAll(outcome,this.solution, averageSatisfaction, downloadSolution);


        Scene displaySolutionScene = new Scene(displaySolution, 1000, 700);
        stage.setScene(displaySolutionScene);

        differentAlgorithm.setOnAction(e -> {
            //primaryStage.setScene(chooseAlgorithmScene);
            displayChooseAlgorithmScene();
            //stage.setScene(chooseAlgorithmScene);
        });

        downloadSolution.setOnAction(e -> {
            String path = pathInput.displayBox();
            System.out.println("Download solution request made ");
            try{
                appInterface.downloadCandidateSolution(path, bestSolutionFound);
            } catch (Exception ex){
                errorBox.displayErrorBox(ex.getMessage());
                ex.printStackTrace();
            }

        });
    }

}
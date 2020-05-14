package application;

import entities.*;
import exceptions.InvalidArgumentException;
import repositories.*;
import interfaces.*;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.Map;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.*;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import java.util.Map.Entry;
import javax.swing.SwingUtilities;
import javafx.embed.swing.JFXPanel;
import javafx.application.Platform;


public class ApplicationTest {
    ApplicationInterfaceImplementation appInterface = new ApplicationInterfaceImplementation();
    StudentRepository students;
    ProjectRepository projects;
    StaffRepository staff;

    class TableRow{
        private long id;
        private String name;
        private String project;
        public TableRow(Long id, String name, String project){
            this.id = id;
            this.name = name;
            this.project = project;
        }
    }

    @FXML
    private TableView<TableRow> displaySolution;
   

    @Test
    public void testReadStaffInput(){
        staff = appInterface.readStaffInput("src/main/resources/staff.xlsx");
        Assert.assertEquals(1030, staff.getSize());
    }

    @Test
    public void testReadProjectInput(){
        StaffRepository staff2 = appInterface.readStaffInput("src/main/resources/staff.xlsx");
        projects = appInterface.readProjectInput("src/main/resources/projects240.xlsx", staff2);
        Assert.assertEquals(240, projects.getSize());
    }
    @Test
    public void testStudentInputReader(){
        StaffRepository staff2 = appInterface.readStaffInput("src/main/resources/staff.xlsx");
        projects = appInterface.readProjectInput("src/main/resources/projects240.xlsx", staff2);
        students = appInterface.readStudentInput("src/main/resources/students.xlsx", projects);
        Assert.assertEquals(1000, students.getSize());
    }

    @Test
    public void testStudentPreferenceReader()throws InvalidArgumentException{
        StaffRepository staff2 = appInterface.readStaffInput("src/main/resources/staff.xlsx");
        projects = appInterface.readProjectInput("src/main/resources/projects240.xlsx", staff2);
        students = appInterface.readStudentInput("src/main/resources/students.xlsx", projects);
        students = appInterface.readStudentPreferencesInput("src/main/resources/studentPreferences60.xlsx", students, projects);

        //Assert.assertEquals(expected, actual);
    }

    @Test
    public void testApplyGeneticAlgorithm() throws InvalidArgumentException{
        Project project = new Project("the effects of Jake Paul on today's youth");
        Project project2 = new Project("Why this project is giving me mental breakdowns");
        Project project3 = new Project("Why techno is not real music");
        Project project4 = new Project("Humanity is doomed");
        Project project5 = new Project("I hate my life");
        Project project6= new Project("projectName");
        Project project7 = new Project("It's as easy as 123");
        Project project8 = new Project("Breakfast at Tiffany's by Deep Blue Something");
        Project project9 = new Project("Tiger King is not a real documentary");
        Project project10 = new Project("Mr Nobody is the best movie ever made");
        Project project11= new Project("Test");
        Project project12 = new Project("Still a test");
        Project project13 = new Project("Test again");
        Project project14 = new Project("Still a test aagain");
        Project project15 = new Project("Still testing");
        Project project16 = new Project("Hi, still testing");
        Project project17 = new Project("plz work");
        Project project18 = new Project("this is so tedious");
        Project project19 = new Project("Almost there");
        Project project20 = new Project("Finally");
        ArrayList<Project> projects = new ArrayList<>();
        projects.add(project);
        projects.add(project2);
        projects.add(project3);
        projects.add(project4);
        projects.add(project5);
        projects.add(project6);
        projects.add(project7);
        projects.add(project8);
        projects.add(project9);
        projects.add(project10);
        projects.add(project11);
        projects.add(project12);
        projects.add(project13);
        projects.add(project14);
        projects.add(project15);
        projects.add(project16);
        projects.add(project17);
        projects.add(project18);
        projects.add(project19);
        projects.add(project20);
        ProjectRepository projectRepository = new ProjectRepository(projects);
        Student student = new Student("Dolly", "Parton", (long) 12345, 3.5, Stream.CSDS, null, projectRepository);
        Student student2 = new Student("Billy", "Joel", (long) 23456, 3.2, Stream.CSDS, null, projectRepository);
        Student student3 = new Student("Don", "McLean", (long) 17462, 3.9, Stream.CSDS, null, projectRepository);
        Student student4 = new Student("Brandon", "Urie", (long) 28414, 3.4, Stream.CSDS, null, projectRepository);
        Student student5 = new Student("Stephen", "Hawking", (long) 65809, 3.4, Stream.CSDS, null, projectRepository);
        Student student6 = new Student("Albert", "Einstein", (long) 93472, 3.4, Stream.CSDS, null, projectRepository);
        Student student7 = new Student("Elon", "Musk", (long) 785326, 3.4, Stream.CSDS, null, projectRepository);
        Student student8 = new Student("Drew", "Gooden", (long) 823161, 3.4, Stream.CSDS, null, projectRepository);
        Student student9 = new Student("Danny", "Gonzalez", (long) 734291, 3.4, Stream.CSDS, null, projectRepository);
        Student student10 = new Student("Kurtis", "Connor", (long) 432781, 3.4, Stream.CSDS, null, projectRepository);
        ArrayList<Student> students = new ArrayList<>();
        students.add(student);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        students.add(student6);
        students.add(student7);
        students.add(student8);
        students.add(student9);
        students.add(student10);
        StudentRepository studentRepository = new StudentRepository(students);
        //CandidateSolution bestSolution = appInterface.applyGeneticAlgorithm(studentRepository, projectRepository);
        /*StaffReader staffReader = new StaffReader();
        StaffRepository staffRepository = staffReader.readXLSX("src/main/resources/staff.xlsx");
        ProjectRepository projectRepository = appInterface.readProjectInput("src/main/resources/projects240.xlsx", staffRepository);
        //StudentReader sReader = new StudentReader(projectRepository);
        StudentRepository studentRepository = appInterface.readStudentInput("src/main/resources/students.xlsx", projectRepository);
        studentRepository = appInterface.readStudentPreferencesInput("src/main/resources/studentPreferences60.xlsx", studentRepository, projectRepository);*/
        try{
            CandidateSolution bestSolution = appInterface.applyGeneticAlgorithm(studentRepository, projectRepository);
        } catch (Exception e){
            e.getCause();
        }
   
    }

    @Test
    public void testShowCandidateSolution()throws InvalidArgumentException{
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JFXPanel(); // this will prepare JavaFX toolkit and environment
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                       /* StageBuilder.create()
                                .scene(SceneBuilder.create()
                                        .width(320)
                                        .height(240)
                                        .root(LabelBuilder.create()
                                                .font(Font.font("Arial", 54))
                                                .text("JavaFX")
                                                .build())
                                        .build())
                                .onCloseRequest(new EventHandler<WindowEvent>() {
                                    @Override
                                    public void handle(WindowEvent windowEvent) {
                                        System.exit(0);
                                    }
                                })
                                .build()
                                .show(); */
                    }
                });
            }
        });
    
        
        StaffRepository staff2 = appInterface.readStaffInput("src/main/resources/staff.xlsx");
        projects = appInterface.readProjectInput("src/main/resources/projects240.xlsx", staff2);
        ArrayList<Student> studentList = new ArrayList<>();
        ArrayList<Project> preferences = new ArrayList<>(0);
        Student student = new Student("firstName", "surname", (long) 12345, 4.2, Stream.CS, preferences, projects);
        Student student2 = new Student("first", "last", (long) 23456, Stream.DS, preferences, projects);
        Student student3 = new Student("Mary", "Smith", (long) 34567, Stream.CS, preferences, projects);
        studentList.add(student);
        studentList.add(student2);
        studentList.add(student3);
        ArrayList<Project> projectList = new ArrayList<>();
        Project project, project2, project3;
        Random r = new Random();
        do{
            int index = r.nextInt(projects.getSize());
            project = projects.getProject(index);
        } while(project.getStream()==Stream.CS);
        projectList.add(project);
        do{
            int index = r.nextInt(projects.getSize());
            project2 = projects.getProject(index);
        } while(project.getStream()==Stream.DS);
        projectList.add(project2);
        do{
            int index = r.nextInt(projects.getSize());
            project3 = projects.getProject(index);
        } while(project.getStream()==Stream.CS && project!=project3);
        projectList.add(project3);
        Assert.assertEquals(3, studentList.size());
        Assert.assertEquals(3, projectList.size());
        CandidateSolution solution = new CandidateSolution(studentList, projectList);
        Assert.assertEquals(3, solution.getCandidateSolution().size());
        //TableView table = appInterface.showCandidateSolution(solution);

        Map<Student, Project> map = solution.getCandidateSolution();
        try{
            displaySolution = new TableView<TableRow>();
        }catch(Exception e){
            e.getCause();
        }
        displaySolution = new TableView<TableRow>();
        TableColumn<TableRow, Long> studentIDColumn = new TableColumn("Student ID");
        studentIDColumn.setMaxWidth(100);
        studentIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<TableRow, String> studentNameColumn = new TableColumn("Student name");
        studentNameColumn.setMaxWidth(200);
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<TableRow, String> projectColumn = new TableColumn<>("Assigned Project");
        projectColumn.setMaxWidth(300);
        projectColumn.setCellValueFactory(new PropertyValueFactory<>("project"));
        ObservableList<TableRow> list = FXCollections.observableArrayList();
        //ArrayList<Student> students = candidateSolution.getStudents();
        //ArrayList<Project> projects = candidateSolution.getProjects(); 
        displaySolution.getColumns().addAll(studentIDColumn, studentNameColumn, projectColumn);
        /*for(int i=0;i<candidateSolution.getStudents().size();i++){
            String name = students.get(i).getFirstName() + " " + students.get(i).getSurname();
            TableRow newRow = new TableRow(students.get(i).getStudentId(), name, projects.get(i).getProjectName());
            solution.add(newRow);
        } */
        for(Entry<Student, Project> entry: map.entrySet()){
            Student student5 = entry.getKey();
            Project project5 = entry.getValue();
            String name = student5.getFirstName() + " " + student5.getSurname();
            TableRow newRow = new TableRow(student5.getStudentId(), name, project5.getProjectName());
            list.add(newRow);
        }
        displaySolution.setItems(list);
        
        Assert.assertEquals(displaySolution.getChildrenUnmodifiable(),appInterface.showCandidateSolution(solution).getChildrenUnmodifiable());


        
    
    }

}
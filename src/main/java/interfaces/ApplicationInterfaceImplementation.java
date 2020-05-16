package interfaces;

import entities.CandidateSolution;
import entities.ProjectWriter;
import entities.StudentPreferenceWriter;
import entities.*;
import exceptions.*;
import repositories.ProjectRepository;
import repositories.StaffRepository;
import repositories.StudentRepository;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import repositories.ProjectRepository;
import repositories.StaffRepository;
import repositories.StudentRepository;
import javax.swing.SwingUtilities;
import javafx.embed.swing.JFXPanel;
import javafx.application.Platform;

import java.util.Random;
import java.util.Map.Entry;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.*;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class ApplicationInterfaceImplementation implements ApplicationInterface {
    private double GPAWeight=0.5;
    private CandidateSolution originalSolution;
    private StudentRepository studentRepository;
    private ProjectRepository projectRepository;
    private CandidateSolution solutionAlreadyAssigned;
    private boolean inputThroughOneFile = false;

    public double getGPAWeight(){
        return this.GPAWeight;
    }
    @Override
    public StaffRepository readStaffInput(String filePath) {
        StaffReader reader = new StaffReader();
        reader.readXLSX(filePath);
        return reader.getStaffRepository();
    }

    @Override
    public ProjectRepository readProjectInput(String filepath, StaffRepository staffRepository) {
        ProjectReader reader = new ProjectReader();
        ProjectRepository projectRepository = new ProjectRepository();
        try {
            projectRepository = reader.read(filepath, staffRepository);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        this.projectRepository = projectRepository;
        return projectRepository;
    }

    @Override
    public StudentRepository readStudentPreferencesInput(String filePath, StudentRepository studentRepository,
            ProjectRepository projectRepository) throws InvalidArgumentException {
        StudentPreferenceReader reader = new StudentPreferenceReader(studentRepository, projectRepository);
        reader.readXLSX(filePath);
        this.studentRepository = reader.getStudents();
        this.originalSolution = new CandidateSolution(this.studentRepository, this.projectRepository);
        return reader.getStudents();
    }

    public void readOneInputFile(String filePath) throws InvalidArgumentException, Exception {
        InputReader inputReader = new InputReader();
        inputReader.readXLSX(filePath);
        inputThroughOneFile = true;
        this.studentRepository = inputReader.getStudentRepository();
        this.projectRepository = inputReader.getProjectRepository();
        this.originalSolution = new CandidateSolution(this.studentRepository, this.projectRepository);
        solutionAlreadyAssigned = new CandidateSolution(inputReader.getStudentRepositoryAssigned(), inputReader.getProjectRepositoryAssigned());
    }

    @Override
    public double getGPAWeight(double weight) {
        this.GPAWeight = weight;
        this.originalSolution.setGpaWeight(weight);
        return weight;
    }


    public  CandidateSolution applyGeneticAlgorithm() throws  Exception{
        try{
            GeneticAlgorithm algorithm = new GeneticAlgorithm(this.studentRepository, this.projectRepository, this.GPAWeight);
            System.out.println(algorithm.getBestSolution().getGpaWeight());
            CandidateSolution bestSolution = algorithm.applyAlgorithm();

            System.out.println(algorithm.getBestSolution().size());
            return bestSolution;
        } catch (Exception exception){
            exception.getCause();
            throw new Exception();
        }
    }

    @Override
    public CandidateSolution applyGeneticAlgorithm(StudentRepository studentRepository,
            ProjectRepository projectRepository) throws Exception {
        //GeneticAlgorithm algorithm;
        try{
            GeneticAlgorithm algorithm = new GeneticAlgorithm(studentRepository, projectRepository, this.GPAWeight);
            System.out.println(algorithm.getBestSolution().getGpaWeight());
            CandidateSolution bestSolution = algorithm.applyAlgorithm();
            
            System.out.println(algorithm.getBestSolution().size());
            return bestSolution;
        } catch (Exception exception){
            exception.getCause();
            throw new Exception();
        }
            
        
        
        
    }

    @Override
    public CandidateSolution applyHillClimbing() {
        HillClimbingAlgorithm algorithm = new HillClimbingAlgorithm(this.originalSolution);
        algorithm.createAssignment();
        return algorithm.giveOutput();
    }

    @Override
    public CandidateSolution applySimulatedAnnealing() {
        SimulatedAnnealing algorithm = new SimulatedAnnealing(this.originalSolution);
        algorithm.createAssignment();
        return algorithm.giveOutput();
    }

    @Override
    public void downloadCandidateSolution(String filePath, CandidateSolution candidateSolution) throws  Exception{
        if( inputThroughOneFile) candidateSolution.combineWithAnotherSolution(solutionAlreadyAssigned);
        CandidateSolutionWriter writer = new CandidateSolutionWriter();
        writer.writeXLSX(filePath, candidateSolution);
    }

    @Override
    public double getAverageStudentSatisfaction(CandidateSolution bestSolutionFound) {
        try{
            return bestSolutionFound.getAverageStudentSatisfaction();
        }
        catch (Exception e){
            return 0;
        }
    }

    @Override
    public ObservableList<TableRow> showCandidateSolution(CandidateSolution candidateSolution) {
        // prevents initialization error
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
            Map<Student, Project> map = candidateSolution.getCandidateSolution();
            TableColumn<TableRow, Long> studentIDColumn = new TableColumn("Student ID");
            studentIDColumn.setMaxWidth(100);
            studentIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            TableColumn<TableRow, String> studentNameColumn = new TableColumn("Student name");
            studentNameColumn.setMaxWidth(200);
            studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            TableColumn<TableRow, String> projectColumn = new TableColumn<>("Assigned Project");
            projectColumn.setMaxWidth(300);
            projectColumn.setCellValueFactory(new PropertyValueFactory<>("project"));
            ObservableList<TableRow> solution = FXCollections.observableArrayList();
            //ArrayList<Student> students = candidateSolution.getStudents();
            //ArrayList<Project> projects = candidateSolution.getProjects();
            //table.getColumns().addAll(studentIDColumn, studentNameColumn, projectColumn);
            /*for(int i=0;i<candidateSolution.getStudents().size();i++){
                String name = students.get(i).getFirstName() + " " + students.get(i).getSurname();
                TableRow newRow = new TableRow(students.get(i).getStudentId(), name, projects.get(i).getProjectName());
                solution.add(newRow);
            } */
            for(Entry<Student, Project> entry: map.entrySet()){
                Student student = entry.getKey();
                Project project = entry.getValue();
                TableRow newRow = new TableRow(student.getStudentId(), student.getName(), project.getProjectName());
                solution.add(newRow);
            }
            return solution;
        } catch (NullPointerException e){
            return null;
        }

    }

    @Override
    public void downloadProjectTemplate(String filePath) {
        filePath = filePath + "/ProjectTemplate.xlsx";
        ProjectWriter projectTemplateWriter = new ProjectWriter();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Projects");
        projectTemplateWriter.createHeader(sheet);
        try {
            FileOutputStream outputFile = new FileOutputStream(new File(filePath));
            workbook.write(outputFile);
            outputFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void downloadStaffTemplate(String filePath) {
        filePath = filePath + "/StaffTemplate.xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Staff");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("Name");
        cell = row.createCell(1);
        cell.setCellValue("Research Activities");
        cell = row.createCell(2);
        cell.setCellValue("Research Areas");
        cell = row.createCell(3);
        cell.setCellValue("Stream");
        try {
            FileOutputStream outputFile = new FileOutputStream(new File(filePath));
            workbook.write(outputFile);
            outputFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void downloadStudentTemplate(String filePath) {
        String filePathStudent = filePath + "/StudentTemplate.xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Students");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("First Name");
        cell = row.createCell(1);
        cell.setCellValue("Surname");
        cell = row.createCell(2);
        cell.setCellValue("Student ID");
        cell = row.createCell(3);
        cell.setCellValue("Stream");
        try {
            FileOutputStream outputFile = new FileOutputStream(new File(filePathStudent));
            workbook.write(outputFile);
            outputFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String filePathPreferences = filePath + "/StudentPreferencesTemplate.xlsx";
        StudentPreferenceWriter preferenceWriter = new StudentPreferenceWriter();
        XSSFWorkbook workbook2 = new XSSFWorkbook();
        XSSFSheet sheet2 = workbook.createSheet("Student Preferences");
        preferenceWriter.createHeader(sheet2);
        try {
            FileOutputStream outputFile2 = new FileOutputStream(new File(filePathPreferences));
            workbook.write(outputFile2);
            outputFile2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @Override
    public StudentRepository readStudentInput(String filePath,
                                              ProjectRepository projectRepository) {
        StudentReader reader = new StudentReader(projectRepository);
        return reader.readXLSX(filePath);
    }


    
}
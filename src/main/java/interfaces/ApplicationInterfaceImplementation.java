package interfaces;

import entities.CandidateSolution;
import entities.ProjectWriter;
import entities.StudentPreferenceWriter;
import entities.*;
import exceptions.*;
import interfaces.ApplicationInterfaceImplementation.TableRow;
import repositories.ProjectRepository;
import repositories.StaffRepository;
import repositories.StudentRepository;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
import repositories.ProjectRepository;
import repositories.StaffRepository;
import repositories.StudentRepository;
import java.util.Map;
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
    @Override
    public StaffRepository readStaffInput(String filePath) {
        StaffReader reader = new StaffReader();
        reader.readXLSX(filePath);
        return reader.getStaffRepository();
    }

    @Override
    public ProjectRepository readProjectInput(String filepath, StaffRepository staffRepository) {
        ProjectReader reader = new ProjectReader();
        try {
            reader.read(filepath, staffRepository);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return reader.getProjectRepository();
    }

    @Override
    public StudentRepository readStudentPreferencesInput(String filePath, StudentRepository studentRepository,
            ProjectRepository projectRepository) throws InvalidArgumentException {
        StudentPreferenceReader reader = new StudentPreferenceReader(studentRepository, projectRepository);
        reader.readXLSX(filePath);
        return reader.getStudents();
    }

    @Override
    public double getGPAWeight(double weight) {
        return weight;
    }

    // TODO check with Weronika
    @Override
    public CandidateSolution applyGeneticAlgorithm(StudentRepository studentRepository,
            ProjectRepository projectRepository) {
        GeneticAlgorithm algorithm;
        try {
            algorithm = new GeneticAlgorithm(studentRepository, projectRepository);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
            return null;
        }
        try {
            algorithm.applyAlgorithm();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
            return null;
        }
        return algorithm.getBestSolution();
    }

    @Override
    public CandidateSolution applyHillClimbing(CandidateSolution candidateSolution) {
        HillClimbingAlgorithm algorithm = new HillClimbingAlgorithm(candidateSolution);
        algorithm.createAssignment();
        return algorithm.giveOutput();
    }

    @Override
    public CandidateSolution applySimulatedAnnealing(CandidateSolution originalSolution) {
        SimulatedAnnealing algorithm = new SimulatedAnnealing(originalSolution);
        algorithm.createAssignment();
        return algorithm.giveOutput();
    }

    @Override
    public void downloadCandidateSolution(String filePath, CandidateSolution candidateSolution) {
        // TODO implement CandidateSolutionWriter class
        // CandidateSolutionWriter writer = new CandidateSolutionWriter();
        // writer.writeXLSX(filePath, candidateSolution);

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
    public TableView showCandidateSolution(CandidateSolution candidateSolution) {
        try{
            Map<Student, Project> map = candidateSolution.getCandidateSolution();
            TableView displaySolution = new TableView<TableRow>();
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
            displaySolution.getColumns().addAll(studentIDColumn, studentNameColumn, projectColumn);
            /*for(int i=0;i<candidateSolution.getStudents().size();i++){
                String name = students.get(i).getFirstName() + " " + students.get(i).getSurname();
                TableRow newRow = new TableRow(students.get(i).getStudentId(), name, projects.get(i).getProjectName());
                solution.add(newRow);
            } */
            for(Entry<Student, Project> entry: map.entrySet()){
                Student student = entry.getKey();
                Project project = entry.getValue();
                String name = student.getFirstName() + " " + student.getSurname();
                TableRow newRow = new TableRow(student.getStudentId(), name, project.getProjectName());
                solution.add(newRow);
            }
            displaySolution.setItems(solution);
            return displaySolution;
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
    public StudentRepository readStrudentInput(String filePath, ProjectRepository projectRepository) {
        // TODO Auto-generated method stub
        return null;
    }

    class TableRow{
        private long id;
        private String name;
        private String project;
        public TableRow(Long id, String name, String project){
            this.id = id;
            this.name = name;
            this.project = project;
        }

        public long getId(){
            return this.id;
        }

        public String getName(){
            return this.name;
        }

        public String getProject(){
            return this.project;
        }

        public void setId(long newId){
            this.id = newId;
        }

        public void setName(String newName){
            this.name = newName;
        }

        public void setProject(String project){
            this.project = project;
        }
    }

}
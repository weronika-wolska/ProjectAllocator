package interfaces;

import entities.CandidateSolution;
import exceptions.InvalidArgumentException;
import repositories.ProjectRepository;
import repositories.StaffRepository;
import repositories.StudentRepository;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public interface ApplicationInterface{

    public StaffRepository readStaffInput(String filePath);

    public ProjectRepository readProjectInput(String filepath, StaffRepository staffRepository);

    public StudentRepository readStudentInput(String filePath, ProjectRepository projectRepository);

    public StudentRepository readStudentPreferencesInput(String filePath, StudentRepository studentRepository, ProjectRepository projectRepository) throws InvalidArgumentException;

    public double getGPAWeight(double weight);

    public CandidateSolution applyGeneticAlgorithm(StudentRepository studentRepository, ProjectRepository projectRepository);

    public CandidateSolution applySimulatedAnnealing(CandidateSolution originalSolution);

    public CandidateSolution applyHillClimbing(CandidateSolution candidateSolution);

    public double getAverageStudentSatisfaction(CandidateSolution bestSolutionFound);

    public TableView showCandidateSolution(CandidateSolution candidateSolution);

    public void downloadCandidateSolution(String filePath, CandidateSolution candidateSolution);

    public void downloadStudentTemplate(String filePath);

    public void downloadProjectTemplate(String filePath);

    public void downloadStaffTemplate(String filePath);


}
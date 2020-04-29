package interfaces;

import entities.CandidateSolution;
import repositories.ProjectRepository;
import repositories.StaffRepository;
import repositories.StudentRepository;

public interface ApplicationInterface{

    public StaffRepository readStaffInput(String filePath);

    public ProjectRepository readProjectInput(String filepath, StaffRepository staffRepository);

    public StudentRepository readStrudentInput(String filePath, ProjectRepository projectRepository);

    public StudentRepository readStudentPreferencesInput(String filePath, StudentRepository studentRepository, ProjectRepository projectRepository);

    public void getGPAWeight(double weight);

    public CandidateSolution applyGeneticAlgorithm(StudentRepository studentRepository, ProjectRepository projectRepository);

    public CandidateSolution applySimulatedAnnealing(CandidateSolution originalSolution);

    public CandidateSolution applyHillClimbing(CandidateSolution candidateSolution);

    public double getAverageStudentSatisfaction(CandidateSolution bestSolutionFound);

    public void showCandidateSolution(CandidateSolution candidateSolution);

    public void downloadCandidateSolution(String filePath, CandidateSolution candidateSolution);

}
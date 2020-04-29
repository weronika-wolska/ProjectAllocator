package interfaces;

import entities.CandidateSolution;
import repositories.ProjectRepository;
import repositories.StaffRepository;
import repositories.StudentRepository;

public class ApplicationInterfaceImplementation implements ApplicationInterface {

    @Override
    public CandidateSolution applyGeneticAlgorithm(StudentRepository studentRepository,
            ProjectRepository projectRepository) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CandidateSolution applyHillClimbing(CandidateSolution candidateSolution) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CandidateSolution applySimulatedAnnealing(CandidateSolution originalSolution) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void downloadCandidateSolution(String filePath, CandidateSolution candidateSolution) {
        // TODO Auto-generated method stub

    }

    @Override
    public double getAverageStudentSatisfaction(CandidateSolution bestSolutionFound) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void getGPAWeight(double weight) {
        // TODO Auto-generated method stub

    }

    @Override
    public ProjectRepository readProjectInput(String filepath, StaffRepository staffRepository) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StaffRepository readStaffInput(String filePath) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StudentRepository readStrudentInput(String filePath, ProjectRepository projectRepository) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StudentRepository readStudentPreferencesInput(String filePath, StudentRepository studentRepository,
            ProjectRepository projectRepository) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void showCandidateSolution(CandidateSolution candidateSolution) {
        // TODO Auto-generated method stub

    }

}
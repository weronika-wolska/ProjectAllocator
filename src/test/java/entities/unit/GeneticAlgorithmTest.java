package entities.unit;

import entities.Project;
import entities.StaffMember;
import entities.Stream;
import exceptions.InvalidArgumentException;
import repositories.ProjectRepository;
import repositories.StaffRepository;
import repositories.StudentRepository;
import entities.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class GeneticAlgorithmTest {
    public static void main(String[] args) throws FileNotFoundException, IOException, InvalidArgumentException{
        CandidateSolution solution = setup();
        for(int i=0;i<500;i++){

        }
    }



    public static CandidateSolution setup() throws IOException, FileNotFoundException, InvalidArgumentException{
        StaffReader staffReader = new StaffReader();
        StaffRepository staffRepository = staffReader.readXLSX(1031, "src/main/resources/staff.xlsx");
        ProjectReader projectReader = new ProjectReader();
        ProjectRepository projectRepository = projectReader.read("src/main/resources/projects500.xlsx", staffRepository);
        StudentReader studentReader = new StudentReader(projectRepository);
        StudentRepository studentRepository = studentReader.readXLSX(500, "src/main/resources/studentPreferences500.xlsx");

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(studentRepository, projectRepository);
        geneticAlgorithm.applyAlgorithm();
        return geneticAlgorithm.getBestSolution();
    }
    
}
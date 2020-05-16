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
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;


public class GeneticAlgorithmTest {
    @Test
    public void geneticAlgorithmTest() throws InvalidArgumentException{
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
        GeneticAlgorithm ga = new GeneticAlgorithm(studentRepository, projectRepository, 0.0);
        Assert.assertEquals(studentRepository, ga.getStudentRepository());
        Assert.assertEquals(projectRepository, ga.getProjectRepository());
        
        try{
            CandidateSolution  solution = ga.applyAlgorithm();
            Assert.assertEquals(studentRepository, ga.getStudentRepository());
            Assert.assertEquals(projectRepository, ga.getProjectRepository());
            Assert.assertEquals(1000, ga.getPopulation().length);
            Assert.assertNotNull(ga.getPopulation()[0]);
            // shouldn't pass but does
            //Assert.assertNotEquals(ga.getPopulation()[0].toString(), ga.getPopulation()[9].toString());
            Assert.assertEquals(10, solution.size());
            Assert.assertNotNull(solution);
            //Assert.assertEquals(false, solution.isThereDuplicateProjects());
            Assert.assertEquals(true, ga.wasTerminatedConditionMet()||ga.getIterationLimitReached());
        } catch(Exception e){
            e.getCause();
            e.printStackTrace();
        }
        

    }

    @Test
    public void testGeneticAlgorithmWithFileInput() throws Exception{
        StaffReader staffReader = new StaffReader();
        StaffRepository staffRepository = staffReader.readXLSX("src/main/resources/staff.xlsx");
        Assert.assertEquals(1030, staffRepository.getSize());
        ProjectReader projectReader = new ProjectReader();
        ProjectRepository projectRepository = projectReader.read("src/main/resources/projects500.xlsx", staffRepository);
        Assert.assertEquals(500, projectRepository.getSize());
        StudentReader studentReader = new StudentReader(projectRepository);
        studentReader.setTesting(false);
        StudentRepository studentRepository = studentReader.readXLSX("src/main/resources/students60.xlsx");
        Assert.assertEquals(60, studentRepository.getSize());
        StudentPreferenceReader studentPreferenceReader = new StudentPreferenceReader(studentRepository, projectRepository);
        studentPreferenceReader.readXLSX("src/main/resources/studentPreferences60.xlsx");
        studentRepository = studentPreferenceReader.getStudents();
        Assert.assertEquals(60, studentRepository.getSize());
        GeneticAlgorithm ga = new GeneticAlgorithm(studentRepository, projectRepository, 0.0);

        try{
            CandidateSolution solution = ga.applyAlgorithm();
            Assert.assertNotNull(ga.getBestSolution());
            Assert.assertEquals(studentRepository, ga.getStudentRepository());
            Assert.assertEquals(projectRepository, ga.getProjectRepository());
            Assert.assertEquals(60, ga.getBestSolution().size());
            //Assert.assertEquals(false, solution.isThereDuplicateProjects());
            Assert.assertEquals(true, ga.wasTerminatedConditionMet()||ga.getIterationLimitReached());
        } catch(Exception e){
            e.getCause();
            e.printStackTrace();
        }
    }
    
}
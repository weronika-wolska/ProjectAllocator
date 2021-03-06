package entities.unit;

import entities.*;
import exceptions.InvalidArgumentException;
import repositories.ProjectRepository;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class RandomAssignmentTest {
    protected ArrayList<Student> students;
    protected ArrayList<Project> projects;
    //protected ArrayList<Student> students2;

    StaffMember staffCS;
    StaffMember staffDS;

    Student student1;
    Student student2;
    Student student3;
    Student student4;
    Student student5;
    Student student6;

    Project project1;
    Project project2;
    Project project3;
    Project project4;
    Project project5;
    Project project6;
    //Project project7;
    //Project project8;
    //Project project9;
    //Project project10;

    public ProjectRepository setupRepository(){
        ProjectRepository projectRepository = new ProjectRepository();
        StaffMember supervisor = new StaffMember();
        Project project = new Project("projectName", Stream.CSDS, supervisor);
        Project project2 = new Project("projectName", Stream.CS, supervisor);
        Project project3 = new Project("project", Stream.DS, supervisor);
        Project project4 = new Project("project", Stream.DS, supervisor);
        Project project5 = new Project("project", Stream.DS, supervisor);
        Project project6 = new Project("projectName", Stream.CS, supervisor);
        Project project7 = new Project("projectName", Stream.CS, supervisor);
        Project project8 = new Project("projectName", Stream.CS, supervisor);
        Project project9 = new Project("projectName", Stream.CSDS, supervisor);
        Project project10 = new Project("projectName", Stream.CSDS, supervisor);
        Project project11 = new Project("projectName", Stream.CSDS, supervisor);
        Project project12 = new Project("projectName", Stream.CSDS, supervisor);
        Project project13 = new Project("projectName", Stream.CS, supervisor);
        Project project14 = new Project("project", Stream.DS, supervisor);
        Project project15 = new Project("project", Stream.DS, supervisor);
        projectRepository.addProject(project);
        projectRepository.addProject(project2);
        projectRepository.addProject(project3);
        projectRepository.addProject(project4);
        projectRepository.addProject(project5);
        projectRepository.addProject(project6);
        projectRepository.addProject(project7);
        projectRepository.addProject(project8);
        projectRepository.addProject(project9);
        projectRepository.addProject(project10);
        projectRepository.addProject(project11);
        projectRepository.addProject(project12);
        projectRepository.addProject(project13);
        projectRepository.addProject(project14);
        projectRepository.addProject(project15);
        return projectRepository;
    }

    public void setUp() throws InvalidArgumentException{
        students = new ArrayList<>();
        projects = new ArrayList<>();
        //students2 = new ArrayList<>();
        ProjectRepository projectRepository = setupRepository();

        student1 = new Student("Becky", "Jones", (long) 12345, Stream.CS, projectRepository);
        student2 = new Student("Jessica", "Delaney", (long) 23456, Stream.CS, projectRepository);
        student3 = new Student("Robert", "Murphy", (long) 34567, Stream.CS, projectRepository);
        student4 = new Student("Bob", "Johnson", (long) 45678, Stream.CSDS, projectRepository);
        student5 = new Student("Tracy", "Jackson", (long) 56789, Stream.DS, projectRepository);
        student6 = new Student("Bella", "Lagosi", (long) 56790, Stream.CSDS, projectRepository);

        staffCS = new StaffMember();
        staffCS.setName("Jane CS Doe");
        staffDS = new StaffMember();
        staffDS.setName("John DS Smith");

        project1 = new Project("First CS project");
        project2 = new Project("Second CS project");
        project3 = new Project("Third CS project");
        project1.setStream(Stream.CS);
        project2.setStream(Stream.CS);
        project3.setStream(Stream.CS);
        project1.setSupervisor(staffCS);
        project2.setSupervisor(staffCS);
        project3.setSupervisor(staffCS);

        project4 = new Project("First CSDS project");
        project5 = new Project("Second CSDS project");
        project6 = new Project("Third CSDS project");
        project4.setStream(Stream.DS);
        project5.setStream(Stream.CSDS);
        project6.setStream(Stream.DS);
        project4.setSupervisor(staffDS);
        project5.setSupervisor(staffDS);
        project6.setSupervisor(staffDS);

        projects.add(project1);
        projects.add(project2);
        projects.add(project3);
        projects.add(project4);
        projects.add(project5);
        projects.add(project6);
        projectRepository.addProject(project1);
        projectRepository.addProject(project2);
        projectRepository.addProject(project3);
        projectRepository.addProject(project4);
        projectRepository.addProject(project5);
        projectRepository.addProject(project6);

        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        students.add(student6);
        //project7 = new Project("do something");
        //project8 = new Project("do something else");
        //project9 = new Project("do another thing");
        //project10 = new Project("create something");
    }

    @Test
    public void testRandomize() throws InvalidArgumentException {
        setUp();
        RandomAssignment shufflingSolution = new RandomAssignment();
        shufflingSolution.randomize(students, projects);
        CandidateSolution firstRandomSolution = new CandidateSolution(students, projects);

        CandidateSolution newRandomSolution;
        boolean foundDifferentSolution = false;
        String previousSolution = firstRandomSolution.toString();
        for(int i = 0; i < 100 && !foundDifferentSolution; ++i) {
            shufflingSolution.randomize(students, projects);
            newRandomSolution = new CandidateSolution(students, projects);
            String newSolution = newRandomSolution.toString();
            //System.out.println("ith time looking for random solution,i=" + i);
            //System.out.println(previousSolution);
            //System.out.println(newSolution);
            if(!previousSolution.equals(newSolution)) foundDifferentSolution = true;
        }


        Assert.assertTrue(foundDifferentSolution);
    }
}
